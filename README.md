# Poi-tl(Poi-template-language)

[![Build Status](https://travis-ci.org/Sayi/poi-tl.svg?branch=master)](https://travis-ci.org/Sayi/poi-tl) ![jdk1.6+](https://img.shields.io/badge/jdk-1.6%2B-orange.svg) ![jdk1.8](https://img.shields.io/badge/jdk-1.8-orange.svg) ![poi3.16%2B](https://img.shields.io/badge/apache--poi-3.16%2B-blue.svg) ![poi4.0.0](https://img.shields.io/badge/apache--poi-4.0.0-blue.svg) [![Gitter](https://badges.gitter.im/Sayi/poi-tl.svg)](https://gitter.im/Sayi/poi-tl?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

:memo:  Word 模板引擎，基于Apache Poi，目标是在文档的任何地方做任何事情(*Do Anything Anywhere*)。

下表对一些处理Word的解决方案作了一些比较：

| 方案 | 跨平台 | 样式处理  | 易用性
| --- | --- | --- | --- |
| **Poi-tl** | 纯Java组件，跨平台 | :white_check_mark: 不需要编码，模板即样式 | :white_check_mark: 简单：模板引擎，对POI进行封装，支持Word文档合并、表格处理等
| Apache POI | 纯Java组件，跨平台 | 编码 | 简单，没有模板引擎功能
| Freemarker | XML操作，跨平台 | 无 | 复杂，需要理解XML结构，基于XML构造模板
| OpenOffice | 需要安装OpenOffice软件 | 编码 | 复杂，需要了解OpenOffice的API
| Jacob、winlib | Windows平台 | 编码 | 复杂，不推荐使用

## Maven

```xml
<dependency>
  <groupId>com.deepoove</groupId>
  <artifactId>poi-tl</artifactId>
  <version>1.5.1</version>
</dependency>
```

## 2分钟快速入门
从一个超级简单的例子开始：把{{title}}替换成"Poi-tl 模板引擎"。

1. 新建文档template.docx，包含文本{{title}}
2. TDO模式：Template + data-model = output

```java
//核心API采用了极简设计，只需要一行代码
XWPFTemplate.compile("template.docx").render(new HashMap<String, Object>(){{
        put("title", "Poi-tl 模板引擎");
}}).writeToFile("out_template.docx");
```

## 详细文档与示例

[poi-tl中文文档](http://deepoove.com/poi-tl/1.5.x/) or [English-tutorial Wiki](https://github.com/Sayi/poi-tl/wiki/2.English-tutorial)

* [基础(图片、文本、表格、列表)示例：软件说明文档](http://deepoove.com/poi-tl/#_%E8%BD%AF%E4%BB%B6%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3)
* [表格示例：付款通知书](http://deepoove.com/poi-tl/#example-table)
* [循环模板示例：文章写作](http://deepoove.com/poi-tl/#example-article)
* [Example：个人简历](http://deepoove.com/poi-tl/#_%E4%B8%AA%E4%BA%BA%E7%AE%80%E5%8E%86)

关于Apache POI的使用，这里有个入门教程：[Apache POI Word(docx) 入门示例教程](http://deepoove.com/poi-tl/apache-poi-guide.html)，更多的示例以及所有示例的源码参见JUnit单元测试。

![](http://deepoove.com/poi-tl/demo.png)
![](http://deepoove.com/poi-tl/demo_result.png)

## 架构设计
**模板和插件构建了整个Poi-tl的核心。** 整体设计采用了`Template + data-model = output`模式，**Configure**提供了模板配置功能，比如语法配置和插件配置，**Visitor**提供了模板解析功能，**RenderPolicy**是渲染策略扩展点，**Render**模块提供了**RenderDataCompute**表达式计算扩展点，通过**RenderPolicy**对每个标签进行渲染。

![](http://deepoove.com/poi-tl/arch.png)

## 建议和完善
参见[常见问题](http://deepoove.com/poi-tl/#_%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)，欢迎在GitHub Issue中提问和交流。

社区交流讨论群：[Gitter频道](https://gitter.im/Sayi/poi-tl)

