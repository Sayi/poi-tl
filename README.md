# poi-tl(poi-template-language)

[![Build Status](https://travis-ci.org/Sayi/poi-tl.svg?branch=master)](https://travis-ci.org/Sayi/poi-tl) ![jdk1.6+](https://img.shields.io/badge/jdk-1.6%2B-orange.svg) ![jdk1.8](https://img.shields.io/badge/jdk-1.8-orange.svg) ![poi3.16%2B](https://img.shields.io/badge/apache--poi-3.16%2B-blue.svg) ![poi4.0.0](https://img.shields.io/badge/apache--poi-4.0.0-blue.svg) [![Gitter](https://badges.gitter.im/Sayi/poi-tl.svg)](https://gitter.im/Sayi/poi-tl?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Word 模板引擎，基于Apache POI - the Java API for Microsoft Documents。

## What is poi-tl
FreeMarker、Velocity基于文本模板和数据生成新的HTML页面、配置文件等，poi-tl是Word模板引擎，基于**Microsoft Word模板**和数据生成**新的文档**。

Word模板拥有丰富的样式，poi-tl在生成的文档中会完美保留模板中的样式，还可以为标签设置样式，标签的样式会被应用到替换后的文本上，因此你可以专注于模板设计。

poi-tl是一种 *"logic-less"* 模板引擎，没有复杂的控制结构和变量赋值，只有**标签**，一些标签可以被替换为文本、图片、表格等，一些标签会隐藏某些文档内容，而另一些标签则会将一系列文档内容循环渲染。

> "Powerful" constructs like variable assignment or conditional statements make it easy to modify the look of an application within the template system exclusively... however, at the cost of separation, turning the templates themselves into part of the application logic.
> 
> [《Google CTemplate》](https://github.com/OlafvdSpek/ctemplate/blob/master/doc/guide.html)

poi-tl支持自定义函数(插件)，函数可以在Word模板的任何位置执行，在文档的任何地方做任何事情(*Do Anything Anywhere*)是poi-tl的星辰大海。

## Maven

```xml
<dependency>
  <groupId>com.deepoove</groupId>
  <artifactId>poi-tl</artifactId>
  <version>1.9.1</version>
</dependency>
```

## 2分钟快速入门
从一个超级简单的例子开始：把`{{title}}`替换成"poi-tl 模板引擎"。

1. 新建文档模板`template.docx`，包含标签`{{title}}`
2. TDO模式：Template + data-model = output

```java
//核心API采用了极简设计，只需要一行代码
XWPFTemplate.compile("template.docx").render(new HashMap<String, Object>(){{
        put("title", "poi-tl 模板引擎");
}}).writeToFile("out_template.docx");
```
打开`out_template.docx`文档吧，一切如你所愿。

## 标签
标签由前后两个大括号组成，`{{title}}`是标签，`{{?title}}`也是标签，`title`是这个标签的名称，`?`标识了标签类型，接下来我们来看看有哪些标签类型。

### 文本
文本标签是Word模板中最基本的标签类型，`{{name}}`会被数据模型中key为`name`的值替换，如果找不到默认会清空标签，可以配置是保留还是抛出异常。

文本标签的样式会应用到替换后的文本上，正如下面的例子所示。

数据:
```json
{
  "name": "Mama",
  "thing": "chocolates"
}
```

Word模板:

**{{name}}** always said life was like a box of {{thing}}.  
~~{{name}}~~ always said life was like a box of {{thing}}.

输出:

**Mama** always said life was like a box of chocolates.  
~~Mama~~ always said life was like a box of chocolates.

### 图片
图片标签以`@`开始，如`{{@logo}}`会在数据中寻找key为`logo`的值，然后将标签替换成图片。由于Word文档中图片不是由字符串表示(在文本型模板中，比如HTML网页图片是由字符串`<img src="" />`表示)，所以图片标签对应的数据有一定的结构要求，这些结构都会有相应的Java类对应。

数据:
```json
{
  "watermelon": {
    "image": "assets/watermelon.png",
    "pictureType" : "PNG"
  },
  "lemon": {
    "image": "http://xxx/lemon.png",
    "pictureType" : "PNG"
  },
  "banana": {
    "image": "sob.png",
    "pictureType" : "PNG",
    "width": 24,
    "height": 24
  }
}
```

Word模板:

```
Fruit Logo:
watermelon {{@watermelon}}
lemon {{@lemon}}
banana {{@banana}}
```

输出:

```
Fruit Logo:
watermelon 🍉
lemon 🍋
banana 🍌
```

### 表格
表格标签以`#`开始，如`{{#table}}`，它会被渲染成N行N列的Word表格，N的值取决于`table`标签的值。

数据:
```json
{
  "rows": [
    {
      "cells": [
        {
          "paragraphs": [
            {
              "contents": [
                {
                  "text": "Song name"
                }
              ]
            }
          ]
        },
        {
          "paragraphs": [
            {
              "contents": [
                {
                  "text": "Artist"
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}
```

Word模板:

```
{{#song}}
```

输出:

<table>
<tr><td>Song name</td><td>Artist</td></tr>
</table>

### 列表
列表标签对应Word的符号列表或者编号列表，以`*`开始，如`{{*number}}`。

数据:
```json
{
  "format" : {
    "lvlText" : "●"
  },
  "items" : [ {
    "contents" : [ {
      "text" : "Plug-in grammar, add new grammar by yourself"
    } ]
  }, {
    "contents" : [ {
      "text" : "Supports word text, local pictures, web pictures, table, list, header, footer..."
    } ]
  }, {
    "contents" : [ {
      "text" : "Templates, not just templates, but also style templates"
    } ]
  } ]
}
```

Word模板:

```
{{*feature}}
```

输出:

```
● Plug-in function, define your own function
● Supports text, pictures, table, list, if, foreach...
● Templates, not just templates, but also style templates
```

### 区块对
区块对由前后两个标签组成，开始标签以`?`标识，结束标签以`/`标识，如`{{?sections}}`作为sections区块的起始标签，`{{/sections}}`为结束标签，sections是这个区块对的名称。

区块对在处理一系列文档元素的时候非常有用，位于区块对中的文档元素(文本、图片、表格等)可以被渲染零次，一次或N次，这取决于区块对的取值。

#### False或空集合
如果区块对的值是`null`、`false`或者空的集合，位于区块中的所有文档元素将**不会显示**，类似于if语句的条件为`false`。

数据:
```json
{
  "announce": false
}
```

Word模板:

```
Made it,Ma!{{?announce}}Top of the world!{{/announce}}
Made it,Ma!
{{?announce}}
Top of the world!🎋
{{/announce}}
```

输出:

```
Made it,Ma!
Made it,Ma!
```

#### 非False且不是集合
如果区块对的值不为`null`、`false`，且不是集合，位于区块中的所有文档元素会被**渲染一次**，if语句的条件为`true`。

数据:
```json
{
  "person": { "name": "Sayi" }
}
```

Word模板:

```
{{?person}}
  Hi {{name}}!
{{/person}}
```

输出:

```
  Hi Sayi!
```

#### 非空集合
如果区块对的值是一个非空集合，区块中的文档元素会被迭代渲染**一次或者N次**，这取决于集合的大小，类似于foreach语法。

数据:
```json
{
  "songs": [
    { "name": "Memories" },
    { "name": "Sugar" },
    { "name": "Last Dance(伍佰)" }
  ]
}
```

Word模板:

```
{{?songs}}
{{name}}
{{/songs}}
```

输出:

```
Memories
Sugar
Last Dance(伍佰)
```

在循环中可以通过一个特殊的标签`{{=#this}}`直接引用当前迭代的对象。

数据:
```json
{
  "produces": [
    "application/json",
    "application/xml"
  ]
}
```

Word模板:

```
{{?produces}}
{{=#this}}
{{/produces}}
```

输出:

```
application/json
application/xml
```

### 嵌套
嵌套是在Word模板中引入另一个Word模板，可以理解为import、include或者word文档合并，以`+`标识，如`{{+nested}}`。

数据:
```json
{
  "nested": {
    "file": "template/sub.docx",
    "dataModels": [
      {
        "addr": "Hangzhou,China"
      },
      {
        "addr": "Shanghai,China"
      }
    ]
  }
}
```

给定两个WordWord模板:

```
main.docx:
Hello, World
{{+nested}}

template/sub.docx:
Address: {{addr}}
```

输出:

```
Hello, World
Address: Hangzhou,China
Address: Shanghai,China
```

## 详细文档与示例

[中文文档Documentation](http://deepoove.com/poi-tl)

* [基础(图片、文本、表格、列表)示例：软件说明文档](http://deepoove.com/poi-tl/#_%E8%BD%AF%E4%BB%B6%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3)
* [表格示例：付款通知书](http://deepoove.com/poi-tl/#example-table)
* [循环和图表示例：野生动物现状](http://deepoove.com/poi-tl/#example-animal)
* [文本框示例：证书奖状](http://deepoove.com/poi-tl/#example-certificate)
* [Example：个人简历创作](http://deepoove.com/poi-tl/#example-resume)
* [Example：Swagger文档](http://deepoove.com/poi-tl/#example-swagger)

更多的示例以及所有示例的源码参见JUnit单元测试。

![](http://deepoove.com/poi-tl/demo.png)
![](http://deepoove.com/poi-tl/demo_result.png)

## Contributing贡献
你可以有很多途径加入这个项目，不限于以下方式：
* 反馈使用中遇到的问题
* 分享成功的喜悦
* 更新和完善文档
* 解决和讨论Issue

## 建议和完善
参见[常见问题](http://deepoove.com/poi-tl/#_%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)，欢迎在GitHub Issue中提问和交流。

社区交流讨论群：[Gitter频道](https://gitter.im/Sayi/poi-tl)

