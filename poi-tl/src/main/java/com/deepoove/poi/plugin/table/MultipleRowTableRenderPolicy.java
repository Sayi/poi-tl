package com.deepoove.poi.plugin.table;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.EnvModel;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.render.processor.DocumentProcessor;
import com.deepoove.poi.render.processor.EnvIterator;
import com.deepoove.poi.resolver.TemplateResolver;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.util.TableTools;

/**
 * Repeats a multi-row block of a table for every element of the bound data.
 * <p>
 * The tag marks the first cell of a reusable block; the number of template rows is
 * read from the {@code $(n)} marker inside the tag and the block is copied and
 * rendered once per element of the {@link Iterable} bound to the tag.
 * </p>
 * <p>
 * For a single-row loop use {@link LoopRowTableRenderPolicy} instead.
 * </p>
 *
 * @author llzero54
 * @author li.ming
 */
public class MultipleRowTableRenderPolicy implements RenderPolicy {

    private final static String DEFAULT_MULTIPLE_PREFIX = "$(";

    private final static String DEFAULT_MULTIPLE_SUFFIX = ")";

    private final static String DEFAULT_PREFIX = "[";

    private final static String DEFAULT_SUFFIX = "]";

    private final static int DEFAULT_MULTIPLE_ROW_NUM = 1;

    private final String regex = "\\$\\([0-9]+\\)";

    private final String multiplePrefix;

    private final String multipleSuffix;

    private final String prefix;

    private final String suffix;

    /**
     * Creates a policy with the default {@code [} and {@code ]} delimiters.
     */
    public MultipleRowTableRenderPolicy() {
        this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    /**
     * Creates a policy with custom tag delimiters.
     *
     * @param prefix the tag prefix
     * @param suffix the tag suffix
     */
    public MultipleRowTableRenderPolicy(String prefix, String suffix) {
        this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, prefix, suffix);
    }

    private MultipleRowTableRenderPolicy(String multiplePrefix, String multipleSuffix, String prefix, String suffix) {
        this.multiplePrefix = multiplePrefix;
        this.multipleSuffix = multipleSuffix;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Expands the template block once per element of the bound {@link Iterable}.
     *
     * @param eleTemplate the tag that marks the first template row
     * @param data        the {@link Iterable} whose items fill the copied blocks
     * @param template    the template instance being rendered
     * @throws RenderException if the target is not a table or rendering fails
     */
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        try {
            RunTemplate runTemplate = cast2runTemplate(eleTemplate);
            XWPFRun run = runTemplate.getRun();
            checkTargetIsTable(run,
                    "Processing [" + runTemplate.getTagName() + "] failed, the target content is not a table");
            XWPFTableCell tagCell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
            final XWPFTable table = tagCell.getTableRow().getTable();
            run.setText("", 0);
            TemplateResolver resolver = new TemplateResolver(template.getConfig().copy(prefix, suffix));
            // the first row of the template block
            int position = getRowIndex(tagCell.getTableRow());
            List<XWPFTableRow> tempRows = getAllTemplateRow(table, position);
            if (null != data && data instanceof Iterable) {
                // keep the template rows so the cursor can be resolved later
                final XWPFTableRow firstTempRow = tempRows.get(0);
                Iterator<?> dataIt = ((Iterable<?>) data).iterator();
                boolean hasNextData = dataIt.hasNext();
                int index = 0;
                while (hasNextData) {
                    Object dt = dataIt.next();
                    hasNextData = dataIt.hasNext();
                    Iterator<XWPFTableRow> rowTempIt = tempRows.iterator();
                    boolean hasNextTempRow = rowTempIt.hasNext();
                    while (hasNextTempRow) {
                        XWPFTableRow tempRow = rowTempIt.next();
                        hasNextTempRow = rowTempIt.hasNext();

                        if (!table.addRow(tempRow, position)) {
                            throw new RenderException("创建新的表格行失败");
                        }

                        // move the cursor to the target row so the copied row can be rendered
                        XmlCursor newCursor = firstTempRow.getCtRow().newCursor();
                        newCursor.toPrevSibling();
                        XmlObject object = newCursor.getObject();
                        XWPFTableRow newRow = new XWPFTableRow((CTRow) object, table);
                        newRow.getCtRow().set(object);
                        setTableRow(table, newRow, position);

                        List<XWPFTableCell> cells = newRow.getTableCells();
                        RenderDataCompute dataCompute = template.getConfig()
                                .getRenderDataComputeFactory()
                                .newCompute(EnvModel.of(dt, EnvIterator.makeEnv(index, hasNextData || hasNextTempRow)));
                        cells.forEach(tableCell -> {
                            List<MetaTemplate> metaTemplates = resolver
                                    .resolveBodyElements(tableCell.getBodyElements());
                            new DocumentProcessor(template, resolver, dataCompute).process(metaTemplates);
                        });
                        ++position;
                    }
                    ++index;
                }
            }
            removeTableRow(table, position, tempRows.size());
        } catch (Exception e) {
            throw new RenderException("failed to render table multi-row template", e);
        }
    }

    /**
     * Collects the template rows that the tag block covers.
     * <p>
     * Reads the {@code $(n)} marker from the first cell to decide how many rows form
     * the block and strips the marker from the cell text.
     * </p>
     *
     * @param table      the table that holds the tag
     * @param startIndex the index of the first template row
     * @return the template rows, in document order
     */
    protected List<XWPFTableRow> getAllTemplateRow(XWPFTable table, int startIndex) {
        List<XWPFTableRow> rows = table.getRows();
        int tempRowNum = DEFAULT_MULTIPLE_ROW_NUM;
        // strip the row count marker such as $(3)
        String text = rows.get(startIndex).getCell(0).getText();
        Matcher matcher = Pattern.compile(regex).matcher(text);
        if (matcher.find()) {
            String rowNumText = matcher.group(0);
            tempRowNum = Integer.parseInt(rowNumText.replace(multiplePrefix, "").replace(multipleSuffix, ""));
            List<XWPFParagraph> paragraphs = rows.get(startIndex).getCell(0).getParagraphs();
            paragraphs.get(0).getRuns().get(0).setText(text.replace(rowNumText, ""), 0);
            for (int i = 1; i < paragraphs.get(0).getRuns().size(); i++) {
                paragraphs.get(0).getRuns().get(i).setText("", 0);
            }
        }
        return new Vector<>(rows.subList(startIndex, startIndex + tempRowNum));
    }

    /**
     * Removes the template rows that were used to render the block.
     *
     * @param table      the table to update
     * @param startIndex the index of the first row to remove
     * @param size       the number of rows to remove
     */
    protected void removeTableRow(XWPFTable table, int startIndex, int size) {
        for (int i = 0; i < size; ++i) {
            table.removeRow(startIndex);
        }
    }

    /**
     * Casts a template to a run template.
     *
     * @param template the template to cast
     * @return the template as a {@link RunTemplate}
     * @throws ClassCastException if the template is not a run template
     */
    protected RunTemplate cast2runTemplate(MetaTemplate template) {
        if (!(template instanceof RunTemplate)) {
            throw new ClassCastException("type conversion failed, template is not of type RunTemplate");
        }
        return (RunTemplate) template;
    }

    /**
     * Checks that the tag run sits inside a table.
     *
     * @param run     the tag run
     * @param message the message of the thrown exception
     * @throws IllegalStateException if the run is {@code null} or outside a table
     */
    protected void checkTargetIsTable(XWPFRun run, String message) {
        if (Objects.isNull(run) || !TableTools.isInsideTable(run)) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Replaces the row at the given index in the POI view and in the XML tree.
     *
     * @param table the table to update
     * @param row   the row to store
     * @param pos   the row index
     */
    @SuppressWarnings("unchecked")
    protected void setTableRow(XWPFTable table, XWPFTableRow row, int pos) {
        List<XWPFTableRow> rows = (List<XWPFTableRow>) ReflectionUtils.getValue("tableRows", table);
        rows.set(pos, row);
        table.getCTTbl().setTrArray(pos, row.getCtRow());
    }

    /**
     * Returns the index of the given row in its table.
     *
     * @param row the row to locate
     * @return the zero-based row index
     */
    protected int getRowIndex(XWPFTableRow row) {
        List<XWPFTableRow> rows = row.getTable().getRows();
        return rows.indexOf(row);
    }
}