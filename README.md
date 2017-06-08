# poi-tl
基于word的模板渲染(替换)组件  
对docx格式的文档增加模板语法，增加渲染模板的方便性，目前支持对段落、页眉、页脚、表格的文本、图片、表单渲染。

#Change log
0.0.4 
1. 增加新的api:XWPFTemplate.compile  
2. 渲染数据除了支持Map以外，还支持JavaBean渲染 
3. 升级poi组件至最新版本3.16

0.0.3  
1. 新增表单语法#  
2. 支持表单插入  
2. 渲染器支持对table动态处理DynamicTableRenderPolicy  
3. 支持单元格的合并  
4. 丰富文本样式

# 使用
    <dependency>
        <groupId>com.deepoove</groupId>
        <artifactId>poi-tl</artifactId>
        <version>0.0.4</version>
    </dependency>

# 示例-Map渲染
    
    Map<String, Object> datas = new HashMap<String, Object>(){{
            put("author", new TextRenderData("000000", "Sayi"));
            put("date", "2015-04-01");
            put("logo",  new PictureRenderData(100, 100, "/Users/Sayi/image.png"));
    }};

    //render
    XWPFTemplate template = XWPFTemplate.compile("src/test/resources/PB.docx").render(datas);;

    //out document
    FileOutputStream out = new FileOutputStream("out.docx");
    template.write(out);
    template.close();
    out.close();

# 示例-JavaBean渲染

		DataSourceTest obj = new DataSourceTest();
		obj.setHeader_version("v0.0.3");
		obj.setHello("v0.0.3");
		obj.setWebsite("http://www.deepoove.com/poi-tl");
		obj.setLogo(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
		obj.setTitle(new TextRenderData("9d55b8",
				"Deeply in love with the things you love,\\n just deepoove."));
		
		
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/PB.docx").render(obj);

		FileOutputStream out = new FileOutputStream("out.docx");
		template.write(out);
		template.close();
		out.flush();
		out.close();


# 文档
详细文档请参见:[poi-tl文档](http://deepoove.com/poi-tl/)

# 建议和完善
见issue。

欢迎朋友们create issue、pull request.

