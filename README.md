# poi-tl

[![Build Status](https://travis-ci.org/Sayi/poi-tl.svg?branch=master)](https://travis-ci.org/Sayi/poi-tl)  

[详细中文文档 Wiki](https://github.com/Sayi/poi-tl/wiki/1.%E4%B8%AD%E6%96%87%E6%96%87%E6%A1%A3)

[English-tutorial Wiki](https://github.com/Sayi/poi-tl/wiki/2.English-tutorial)

Java word的模板引擎，对docx格式的文档增加模板语法，简化样式处理。目前支持对段落、页眉、页脚、表格的文本、图片、表单渲染。

# Why poi-tl

对于word模板替换，我们不仅要考虑复杂的模板格式，还要考虑字体，颜色，处理页眉页脚，使用稍显复杂的poi的API等，现实项目中又有许多需求需要后台动态生成数据然后替换word模板，供前台下载或者打印，为了避免：
* java操作word使用apache poi的复杂性
* 使用freemarker，转化为xml操作word的难度
* 依赖服务器上安装软件openoffice来调用转化
* 依赖windows的word lib库，不具有跨平台性

因此基于poi开发了一套拥有简洁API的跨平台的模板引擎：poi-tl。核心API只需要一行代码：

```java
XWPFTemplate template = XWPFTemplate.compile("~/file.docx").render(datas);
```

**打个广告：我们正在招人，坐标杭州，阿里系公司，E轮融资，行业独角兽(almost)，欢迎联系我 adasai90@gmail.com**

# 依赖

```xml
<dependency>
    <groupId>com.deepoove</groupId>
    <artifactId>poi-tl</artifactId>
    <version>1.2.0</version>
</dependency>
```

# 语法
所有的语法结构都是以 {{ 开始，以 }} 结束。

* {{template}}

普通文本，渲染数据为：String或者TextRenderData

* {{@template}}

图片,渲染数据为：PictureRenderData

* {{#template}}

表格，渲染数据为：TableRenderData

[文章：poi-tl处理Word表格(Table)的最佳实践](https://github.com/Sayi/sayi.github.com/issues/21)

* {{*template}}

列表，渲染数据为：NumbericRenderData

# 样式
文档的样式继承模板标签的样式，这样我们只需要提前设计好模板样式即可，即如果模板{{L}}是红色微软雅黑加粗四号字体，则替换后的文本也是红色微软雅黑加粗四号字体。
![](dist/style.png)

也可以在渲染数据中指定,实现了样式的最大自由化，通过代码设置样式的方法，具体参见com.deepoove.poi.data.style.Style类。
* 颜色
* 字体
* 字号
* 粗体
* 斜体
* 删除线

# Usage
 
 ```java
Map<String, Object> datas = new HashMap<String, Object>(){{

        put("author", new TextRenderData("000000", "Sayi"));
        //文本模板
        put("date", "2015-04-01");

        //表格模板
        put("changeLog", new TableRenderData(new ArrayList<RenderData>(){{
            add(new TextRenderData("d0d0d0", ""));
            add(new TextRenderData("d0d0d0", "introduce"));
        }},new ArrayList<Object>(){{
            add("1;add new # gramer");
            add("2;support insert table");
            add("3;support more style");
        }}, "no datas", 10600));

        //列表 1. 2. 3.
        put("number123", new NumbericRenderData(FMT_DECIMAL, new ArrayList<TextRenderData>() {{
            add(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."));
            add(new TextRenderData("Deeply in love with the things you love, just deepoove."));
            add(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."));
        }}));
        
        //图片模板
        put("logo",  new PictureRenderData(100, 100, "/Users/Sayi/image.png"));
}};

//render
XWPFTemplate template = XWPFTemplate.compile("src/test/resources/PB.docx").render(datas);

//out document
FileOutputStream out = new FileOutputStream("out.docx");
template.write(out);
template.close();
out.close();
```

# Change log

v1.2.0 2017-10-12
1. 新增api：`XWPFTemplate compile(InputStream inputStream)`
2. **不兼容升级：文本模板换行符由原先的\\\n替换成更符合语言的\n**

v1.1.0 2017-09-15
1. 修复老版本office打开表格模板时出错
2. 新增列表字符样式：设置编号颜色、字体、粗体、斜体等

v1.0.0
1. 以插件的思想进行了重新设计
2. **高度扩展性：语法即插件，像新增插件一样新增语法**
3. 新增工具类BytePictureUtils，便于操作图片的byte[]数据
4. 新增Annotation @Name
5. NiceXWPFDocument新增插入段落insertNewParagraph方法
6. 新增代码生成工具类CodeGenUtils 

V0.0.5 
1. bugfix: 解决0.0.4版本解析模板时CTSignedTwips类加载不到的问题  
2. new feature: 新增列表语法*，支持对有序列表和无序列表的插入 

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


# 示例图
* 示例一 

![](dist/tempv3.png)
![](dist/temp3.png)
* 示例二  

![](dist/demo.png)
![](dist/demo_result.png)

# License
Apache License 2.0

# 建议和完善
问题、BUG可以在issue中提问，feature可以pull request。

