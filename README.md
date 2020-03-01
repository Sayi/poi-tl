# Poi-tl(Poi-template-language)

[![Build Status](https://travis-ci.org/Sayi/poi-tl.svg?branch=master)](https://travis-ci.org/Sayi/poi-tl) ![jdk1.6+](https://img.shields.io/badge/jdk-1.6%2B-orange.svg) ![jdk1.8](https://img.shields.io/badge/jdk-1.8-orange.svg) ![poi3.16%2B](https://img.shields.io/badge/apache--poi-3.16%2B-blue.svg) ![poi4.0.0](https://img.shields.io/badge/apache--poi-4.0.0-blue.svg) [![Gitter](https://badges.gitter.im/Sayi/poi-tl.svg)](https://gitter.im/Sayi/poi-tl?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

:memo:  Word 模板引擎，基于Apache POI。

## Why Poi-tl


## Maven

```xml
<dependency>
  <groupId>com.deepoove</groupId>
  <artifactId>poi-tl</artifactId>
  <version>1.7.0</version>
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

[中文文档](http://deepoove.com/poi-tl) or [English-tutorial](https://github.com/Sayi/poi-tl/wiki/2.English-tutorial)

* [基础(图片、文本、表格、列表)示例：软件说明文档](http://deepoove.com/poi-tl/#_%E8%BD%AF%E4%BB%B6%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3)
* [表格示例：付款通知书](http://deepoove.com/poi-tl/#example-table)
* [循环模板示例：文章写作](http://deepoove.com/poi-tl/#example-article)
* [Example：个人简历](http://deepoove.com/poi-tl/#_%E4%B8%AA%E4%BA%BA%E7%AE%80%E5%8E%86)

更多的示例以及所有示例的源码参见JUnit单元测试。

![](http://deepoove.com/poi-tl/demo.png)
![](http://deepoove.com/poi-tl/demo_result.png)

## 架构设计
目标是在文档的任何地方做任何事情(*Do Anything Anywhere*)**模板和插件丰富了Poi-tl的想象力。** 整体设计采用`Template + data-model = output`模式，**Configure**提供了配置功能，**Visitor**提供了解析功能，**RenderPolicy**是渲染策略扩展点，**Render**模块提供了**RenderDataCompute**标签表达式计算扩展点，通过插件对每个标签进行渲染。

![](http://deepoove.com/poi-tl/arch.png)

## 建议和完善
参见[常见问题](http://deepoove.com/poi-tl/#_%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)，欢迎在GitHub Issue中提问和交流。

社区交流讨论群：[Gitter频道](https://gitter.im/Sayi/poi-tl)

