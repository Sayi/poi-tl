# poi-tl

[![Build Status](https://travis-ci.org/Sayi/poi-tl.svg?branch=master)](https://travis-ci.org/Sayi/poi-tl)  

| Word处理方案 | 跨平台 | 样式处理  | 易用性
| --- | --- | --- | --- |
| **Poi-tl** | 纯Java组件，跨平台 | 不需要编码，模板即样式 | :white_check_mark: 简单：模板引擎，对POI进行封装
| Apache POI | 纯Java组件，跨平台 | 编码 | :white_check_mark: 简单, 没有模板引擎功能
| Freemarker | XML操作，跨平台 | 无 | 复杂，需要理解XML结构，基于XML构造模板
| OpenOffice | 需要安装OpenOffice软件 | 编码 | 复杂，需要了解OpenOffice的API
| Jacob、winlib | Windows平台 | 编码 | 复杂，不推荐使用

**Poi-tl(poi template language)** 的增强和特性主要有以下几点：
* **Word模板引擎**
* 多个Word文档合并
* 合并单元格
* Word指定位置插入表格、图片、段落
* 简化样式处理
* 图片处理

核心API也采用了极简设计，只需要一行代码：

```java
XWPFTemplate template = XWPFTemplate.compile("~/file.docx").render(datas);
```

**打个广告：我们正在招人，坐标杭州，阿里系公司，E轮融资，行业独角兽(almost)，我相信你一定能找到我的联系方式，欢迎投递简历。**

# Maven

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
* 下划线

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

# 文档

[详细中文文档 Wiki](https://github.com/Sayi/poi-tl/wiki/1.%E4%B8%AD%E6%96%87%E6%96%87%E6%A1%A3)

[English-tutorial Wiki](https://github.com/Sayi/poi-tl/wiki/2.English-tutorial)

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

