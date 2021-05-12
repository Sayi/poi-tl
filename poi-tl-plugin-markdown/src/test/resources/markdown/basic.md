# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5

## *italic* and **bold** and `code`

This is the first paragraph.

This is the second paragraph. 

This is a\
line break.

*Italics*
**Bold**
**`code`Bold**

> quote First line
> Another line *Italics* and **Bold** and [text](http://a.com)
>
> > Nested line *Italics* and **Bold** and [text](http://a.com)
>
> Last line and `one code`

---

* bullet Apples link:[text](http://a.com)

  By default, the latest Apache release package will be pulled.
* Oranges

```
Or use 3 backticks
```
* Pears

1. orderd First
2. Second
3. Third

- `one`
- `two`

* Item1
    1. First Subitem and [text](http://a.com)
    2. Second Subitem and `onecode`
* Item2
    - Subitem
    - Subitem
* Item3

1. Item1
    1. First Subitem and [text](http://a.com)
    2. Second Subitem and `onecode`
2. Item2
    - Subitem
    - Subitem
3. Item3


[text link](http://a.com)
![alt](src/test/resources/logo.png)

Inline code: `code`

    indent 4 spaces
    indent 4 spaces

```java
/**
 * @author John Smith <john.smith@example.com>
*/
package l2f.gameserver.model;

public abstract strictfp class L2Char extends L2Object {
  public static final Short ERROR = 0x0001;

  public void moveTo(int x, int y, int z) {
    _ai = null;
    log("Should not be called");
    if (1 > 5) { // wtf!?
      return;
    }
  }
}
```

| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| `git status` | List all *new or modified* files |
| `git diff` | Show file [text](http://a.com) differences that **haven't been** staged |

| Left-aligned | Center-aligned | Right-aligned |
| :---         |     :---:      |          ---: |
| git status   | git status     | git status    |
| git diff     | git diff       | git diff      |

- [ ] foo
- [x] bar