# poi-tl
Java对word的模板进行渲染(替换)的跨平台组件，对docx格式的文档增加模板语法，增加渲染模板的方便性，目前支持对段落、页眉、页脚、表格的文本、图片、表单渲染。

对于word模板替换，我们不仅要考虑复杂的模板格式，还要考虑字体，颜色，处理页眉页脚，使用稍显复杂的poi的API等，现实项目中又有许多需求需要后台动态生成数据然后替换word模板，供前台下载或者打印，为了避免：
* java操作word使用poi的复杂性
* 转化为xml操作word的难度
* 依赖服务器上安装软件openoffice来调用转化
* 依赖windows的word库，不具有跨平台性

因此基于poi开发了一套拥有简洁API的跨平台的模板引擎：poi-tl。核心API只需要一行代码：

	XWPFTemplate template = XWPFTemplate.compile("~/file.docx").render(datas);

**PS：此版本是开源版本，本项目在国内某大型垂直行业互联网公司已稳定运行一年以上，负责动态渲染样式超级复杂的word报告的下载和打印。**


# Change log

V0.0.4 
1. 增加新的api:XWPFTemplate.compile  
2. 渲染数据除了支持Map以外，还支持JavaBean渲染 
3. 升级poi组件至最新版本3.16

V0.0.3  
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
    XWPFTemplate template = XWPFTemplate.compile("src/test/resources/PB.docx").render(datas);

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

# 渲染图
* word模板文件  
![](src/test/resources/temp3.png)
* word渲染后生成的文件  
![](src/test/resources/tempv3.png)
* word模板文件  
![](src/test/resources/temp4.png)
* word渲染后生成的文件  
![](src/test/resources/tempv4.png)

# 文档
详细文档请参见:[poi-tl文档](http://deepoove.com/poi-tl/)

# 建议和完善
问题可以在issue中提问，任何bug可以直接pull request。

