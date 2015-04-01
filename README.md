# poi-tl
基于word的模板渲染组件  
对docx格式的文档增加模板语法，增加渲染模板的方便性，目前支持对段落、页眉、页脚、表格的文本、图片、表单渲染。

#Change log
0.0.3  
1. 新增表单语法#  
2. 支持表单插入  
2. 渲染器支持对table动态处理DynamicTableRenderPolicy  
3. 支持单元格的合并  
4. 丰富文本样式

# 使用
    <dependency>
        <groupId>org.sayi</groupId>
        <artifactId>poi-tl</artifactId>
        <version>0.0.3-SNAPSHOT</version>
    </dependency>

# 示例
    //read template
    XWPFTemplate doc = XWPFTemplate.create("/Users/Sayi/template.docx");

    Map<String, Object> datas = new HashMap<String, Object>(){{
            put("author", new TextRenderData("e14800", "Sayi"));
            put("date", "2015-04-01");
            put("logo",  new PictureRenderData(100, 100, "/Users/Sayi/image.png"));
    }};

    //render
    RenderAPI.render(doc, datas);

    //out document
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    doc.write(bos);
    bos.close();

# 模板语法
1. {{text}}
文本渲染，对应渲染数据为String类型或者TextRenderData类型
2. {{@image}}
图片渲染，对应渲染数据为PictureRenderData
3. {{#table}}
表格渲染，对应渲染数据为TableRenderData

详细文档请参见:[poi-tl文档](http://www.sayi.org)

# 建议和完善
* 丰富表格样式
* 语法自动扩展
* 支持文本框

欢迎朋友们创建issue、pull request.

