# poi-tl (poi-template-language)

[![Build Status](https://app.travis-ci.com/Sayi/poi-tl.svg?branch=master)](https://app.travis-ci.com/Sayi/poi-tl)
[![Maven Central](https://img.shields.io/maven-central/v/com.deepoove/poi-tl.svg)](https://search.maven.org/artifact/com.deepoove/poi-tl)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
![JDK 1.8+](https://img.shields.io/badge/jdk-1.8%2B-orange.svg)
![Apache POI 5.2.2+](https://img.shields.io/badge/apache--poi-5.2.2%2B-blue.svg)

A better way to generate Word (`.docx`) documents from templates in Java, powered by Apache POI.

---

## What is poi-tl

FreeMarker or Velocity generates HTML pages or configuration files based on text templates and data. **poi-tl** is a Word template engine that generates **new documents** based on a **Word template** and a **data model**.

The Word template provides rich styling. poi-tl perfectly preserves all fonts, colors, layouts, and alignments from your template in the generated document. You can also configure styles directly for tags in code, allowing you to focus on template design.

poi-tl is a **logic-less** template engine. There are no complicated control structures or variable assignments in the template—only **tags**. Tags can be replaced with text, pictures, tables, and lists; certain tags can conditionally show or hide document blocks, and others loop over collections.

> "Powerful" constructs like variable assignment or conditional statements make it easy to modify the look of an application within the template system exclusively... however, at the cost of separation, turning the templates themselves into part of the application logic.
> 
> — [Google CTemplate](https://github.com/OlafvdSpek/ctemplate/blob/master/doc/guide.html)

poi-tl supports **custom functions (plugins)**. Functions can execute anywhere in the Word template—*Do anything anywhere in the document* is the ultimate goal of poi-tl.

| Feature | Description |
| :--- | :--- |
| :white_check_mark: Text | Render the tag as text |
| :white_check_mark: Picture | Render the tag as a picture |
| :white_check_mark: Table | Render the tag as a table |
| :white_check_mark: Numbering | Render the tag as a numbering |
| :white_check_mark: Chart | Bar chart (3D bar chart), column chart (3D column chart), area chart (3D area chart), line chart (3D line chart), radar chart, pie chart (3D pie Figure) and other chart rendering |
| :white_check_mark: If Condition | Hide or display certain document content (including text, paragraphs, pictures, tables, lists, charts, etc.) according to conditions |
| :white_check_mark: Foreach Loop | Loop through certain document content (including text, paragraphs, pictures, tables, lists, charts, etc.) according to the collection |
| :white_check_mark: Loop table row | Loop to copy a row of the rendered table |
| :white_check_mark: Loop table column | Loop copy and render a column of the table |
| :white_check_mark: Loop ordered list | Support the loop of ordered list, and support multi-level list at the same time |
| :white_check_mark: Highlight code | Word highlighting of code blocks, supporting 26 languages and hundreds of coloring styles |
| :white_check_mark: Markdown | Convert Markdown to a word document |
| :white_check_mark: Word attachment | Insert attachment in Word |
| :white_check_mark: Word Comments | Complete support comment, create comment, modify comment, etc. |
| :white_check_mark: Word SDT | Complete support structured document tag |
| :white_check_mark: Textbox | Tag support in text box |
| :white_check_mark: Picture replacement | Replace the original picture with another picture |
| :white_check_mark: bookmarks, anchors, hyperlinks | Support setting bookmarks, anchors and hyperlinks in documents |
| :white_check_mark: Expression Language | Fully supports SpringEL expressions and can extend more expressions: OGNL, MVEL... |
| :white_check_mark: Style | The template is the style, and the code can also set the style |
| :white_check_mark: Template nesting | The template contains sub-templates, and the sub-templates then contain sub-templates |
| :white_check_mark: Merge | Word merge Merge, you can also merge in the specified position |
| :white_check_mark: custom functions (plug-ins) | Plug-in design, execute function anywhere in the document |

---

## Quick Start

### 1. Dependency

```xml
<dependency>
  <groupId>com.deepoove</groupId>
  <artifactId>poi-tl</artifactId>
  <version>1.12.2</version>
</dependency>
```

> **Note**: poi-tl `1.12.x` requires JDK `1.8+` and Apache POI `5.2.2+`.

### 2. The TDO Pattern: Template + Data-model = Output

1. Create a Word document `template.docx` containing the tag `{{title}}`.
2. Compile, render, and write in Java:

```java
// Core API: one-liner compilation and rendering
XWPFTemplate template = XWPFTemplate.compile("template.docx")
    .render(Collections.singletonMap("title", "Hi, poi-tl Word Template Engine"));

template.writeToFile("output.docx");
```

Open `output.docx`, and `{{title}}` is seamlessly replaced with your text while retaining all template formatting.

---

## Tag Syntax & Built-in Renderers

poi-tl tags are delimited by double curly braces `{{` and `}}`. The prefix character identifies the tag type:

| Tag | Syntax | Data Model | Fluent Builder / Example |
| :--- | :--- | :--- | :--- |
| **Text** | `{{name}}` | `String`, `TextRenderData` | `Texts.of("Hello").color("000000").bold().create()` |
| **Picture** | `{{@logo}}` | `PictureRenderData` | `Pictures.ofLocal("logo.png").size(100, 100).create()` |
| **Table** | `{{#table}}` | `TableRenderData` | `Tables.of(new String[][]{{"A", "B"}, {"C", "D"}}).create()` |
| **Numbering** | `{{*list}}` | `NumberingRenderData` | `Numberings.create("Item 1", "Item 2")` |
| **Section** | `{{?sec}}...{{/sec}}` | `Boolean`, `Iterable`, `Object` | Conditional toggle (false/null hides) or foreach loop |
| **Nesting** | `{{+nested}}` | `DocxRenderData` | `Includes.ofLocal("sub.docx").setRenderModel(subData).create()` |

### Loop Built-in Variables

Inside loop sections (`{{?collection}}...{{/collection}}`), poi-tl exposes built-in contextual variables:

| Variable | Type | Description | Example |
| :--- | :--- | :--- | :--- |
| `_index` | `int` | 0-based iteration index | `{{_index + 1}}. {{name}}` |
| `_is_first` | `boolean` | `true` if current item is the first | Useful in conditional blocks |
| `_is_last` | `boolean` | `true` if current item is the last | Useful for suppressing trailing commas |
| `_has_next` | `boolean` | `true` if there are subsequent items | Useful for delimiters |
| `#this` | `Object` | References the current element itself | Use `{{=#this}}` to output text |

---

## Reference Tags: Charts & In-place Replacement

Reference tags directly reference native Word element handles. **All native styles, colors, layouts, legends, and animations configured in Word are perfectly preserved; only the underlying data is updated.**

**How to set**: Right-click any Chart or Picture in Word -> select **Edit Alt Text** (or **Format Shape -> Alt Text**) -> set the **Title** or **Description** to `{{tag}}`.

```java
// 1. Multi-Series Charts (Bar, Column, Line, Area, Radar, Scatter)
ChartMultiSeriesRenderData chart = Charts.ofMultiSeries("Sales Trend", new String[]{"Q1", "Q2"})
    .addSeries("2023", new Double[]{100.0, 150.0})
    .addSeries("2024", new Double[]{120.0, 180.0})
    .create();

// 2. Single-Series Charts (Pie, Doughnut)
ChartSingleSeriesRenderData pie = Charts.ofSingleSeries("Market Share", new String[]{"Product A", "Product B"})
    .series("Share", new Integer[]{40, 60})
    .create();

// 3. In-place Picture Replacement (retains existing dimensions and wrap style)
PictureRenderData avatar = Pictures.ofLocal("avatar.png").create();

Map<String, Object> data = new HashMap<>();
data.put("salesChart", chart);
data.put("sharePie", pie);
data.put("avatar", avatar);
XWPFTemplate.compile("report_template.docx").render(data).writeToFile("report.docx");
```

---

## Configuration & SpringEL

poi-tl supports rich configurations and full **Spring Expression Language (SpringEL)** integration:

```java
Configure config = Configure.builder()
    .useSpringEL() // Enable SpringEL expressions
    // .buildGrammar("${", "}") // Optional: customize delimiters
    .build();

XWPFTemplate.compile("template.docx", config).render(data).writeToFile("out.docx");
```

### Typical SpringEL Expressions
- **Method invocation**: `{{name.toUpperCase()}}`
- **Ternary & Elvis operators**: `{{sex ? 'Male' : 'Female'}}`, `{{desc ?: 'N/A'}}`
- **Arithmetic & Index access**: `{{price * 0.85}}`, `{{users[0].name}}`, `{{_index + 1}}`
- **Date formatting**: `{{new java.text.SimpleDateFormat('yyyy-MM-dd').format(createTime)}}`
- **Block conditions**: `{{?users != null && !users.isEmpty()}}...{{/}}` *(Note: `{{/}}` works as a closing tag in SpringEL)*

---

## Powerful Plugins

poi-tl is built on a plugin architecture. Everything is a `RenderPolicy`.

### 1. LoopRowTableRenderPolicy (Table Row Iteration)
The most common enterprise requirement: iterating dynamic list data over a stylized Word table row.

**Template (`template.docx`)**:
Place `{{goods}}` in the row above the repeating row, and use square brackets `[item.prop]` inside the repeating row:

| Item | Unit Price | Quantity | Amount |
| :--- | :--- | :--- | :--- |
| `{{goods}}` | | | |
| `[name]` | `[price]` | `[count]` | `[total]` |

**Java Code**:
```java
LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
Configure config = Configure.builder().bind("goods", policy).build();

XWPFTemplate.compile("template.docx", config).render(data).writeToFile("out_table.docx");
```

### 2. Built-in & Ecosystem Plugins Cheat Sheet

| Plugin / Extension | Artifact | Description & Usage |
| :--- | :--- | :--- |
| **`LoopColumnTableRenderPolicy`** | `poi-tl` (core) | Iterates dynamic table columns horizontally using `[]` syntax. |
| **`DynamicTableRenderPolicy`** | `poi-tl` (core) | Abstract policy exposing native `XWPFTable` for low-level cell merging and custom table layout. |
| **`CommentRenderPolicy`** | `poi-tl` (core) | Creates Word comments: `Comments.of("target").comment("note").create()`. |
| **`AttachmentRenderPolicy`** | `poi-tl` (core) | Embeds Excel/Word files into docx: `Attachments.ofLocal("data.xlsx", AttachmentType.XLSX).create()`. |
| **`HighlightRenderPolicy`** | `poi-tl-plugin-highlight` | Syntax highlighting for 26+ languages with 100+ themes (`zenburn`, `github`, `darcula`). |
| **`MarkdownRenderPolicy`** | `poi-tl-plugin-markdown` | Directly converts Markdown strings into formatted Word documents. |

---

## Custom Plugin in 5 Lines

Implement `RenderPolicy` (or extend `AbstractRenderPolicy<T>`) to do anything anywhere in the document:

```java
public class HelloRenderPolicy implements RenderPolicy {
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        XWPFRun run = ((RunTemplate) eleTemplate).getRun();
        run.setText(String.valueOf(data), 0);
    }
}

// Bind to tag {{greeting}}
Configure config = Configure.builder().bind("greeting", new HelloRenderPolicy()).build();
```

---

## Compatibility & Best Practices

### Version Compatibility Matrix

| poi-tl Version | Apache POI Version | JDK Baseline |
| :--- | :--- | :--- |
| **`1.12.x` / `1.13.x`** | **`5.2.2+`** | **JDK 1.8+** |
| `1.10.x` ~ `1.11.x` | `4.1.2` ~ `5.1.0` | JDK 1.8+ |
| `1.5.x` ~ `1.9.x` | `3.16` ~ `4.1.2` | JDK 1.6+ / 1.8+ |

### Stream Handling & Web Download
Always close resources safely using `PoitlIOUtils`:

```java
response.setContentType("application/octet-stream");
response.setHeader("Content-Disposition", "attachment;filename=\"report.docx\"");

OutputStream out = response.getOutputStream();
template.write(out);
PoitlIOUtils.closeQuietlyMulti(template, out);
```

### Common Pitfalls
1. **`NoSuchMethodError` / `ClassNotFoundException`**: Caused by dependency version collision. Ensure all transitive Apache POI dependencies are upgraded to `5.2.2+`.
2. **Tags split across multiple Runs**: When editing templates in Word, spell-checking or formatting changes can split `{{tag}}` into multiple XML Runs. Cut and re-paste the tag as plain text, or rely on poi-tl's automatic run merge scanner.

---

## Contributing & License

Pull requests, issue discussions, and documentation improvements are welcome!

poi-tl is open-sourced under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html).
