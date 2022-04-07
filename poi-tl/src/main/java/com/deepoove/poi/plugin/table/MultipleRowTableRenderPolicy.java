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
 * word模板替换，多行表格复用渲染
 * <p>
 * 该插件旨在替换多行表格内容
 * </p>
 * <p>
 * 单行表格循环可以使用{@link LoopRowTableRenderPolicy}
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

    public MultipleRowTableRenderPolicy() {
        this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public MultipleRowTableRenderPolicy(String prefix, String suffix) {
        this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, prefix, suffix);
    }

    private MultipleRowTableRenderPolicy(String multiplePrefix, String multipleSuffix, String prefix, String suffix) {
        this.multiplePrefix = multiplePrefix;
        this.multipleSuffix = multipleSuffix;
        this.prefix = prefix;
        this.suffix = suffix;
    }

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
            // 获取模板所在的起始行
            int position = getRowIndex(tagCell.getTableRow());
            List<XWPFTableRow> tempRows = getAllTemplateRow(table, position);
            if (null != data && data instanceof Iterable) {
                // 保存第行模板，以便在后续操作中获取光标
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

                        // 光标操作，移动光标到目标行，以便后续的模板渲染
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

    protected List<XWPFTableRow> getAllTemplateRow(XWPFTable table, int startIndex) {
        List<XWPFTableRow> rows = table.getRows();
        int tempRowNum = DEFAULT_MULTIPLE_ROW_NUM;
        // 去除模板行数标记 如：$(3)
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

    protected void removeTableRow(XWPFTable table, int startIndex, int size) {
        for (int i = 0; i < size; ++i) {
            table.removeRow(startIndex);
        }
    }

    protected RunTemplate cast2runTemplate(MetaTemplate template) {
        if (!(template instanceof RunTemplate)) {
            throw new ClassCastException("type conversion failed, template is not of type RunTemplate");
        }
        return (RunTemplate) template;
    }

    protected void checkTargetIsTable(XWPFRun run, String message) {
        if (Objects.isNull(run) || !TableTools.isInsideTable(run)) {
            throw new IllegalStateException(message);
        }
    }

    @SuppressWarnings("unchecked")
    protected void setTableRow(XWPFTable table, XWPFTableRow row, int pos) {
        List<XWPFTableRow> rows = (List<XWPFTableRow>) ReflectionUtils.getValue("tableRows", table);
        rows.set(pos, row);
        table.getCTTbl().setTrArray(pos, row.getCtRow());
    }

    protected int getRowIndex(XWPFTableRow row) {
        List<XWPFTableRow> rows = row.getTable().getRows();
        return rows.indexOf(row);
    }
}