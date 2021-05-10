函数式编程是使用像数学里面的函数来编程，函数是给定了输入值，返回结果的运算，函数应该相互独立，不会影响函数外的变量，即无副作用，对于同样的输入值，多次调用函数的结果应该是一致的，即引用透明性。

[Haskell](https://www.haskell.org/)就是这样一门语言，它用数学函数描述这个世界，被称为纯函数编程语言，我们来看看这样一段代码：
```haskell
f = 3 
```
在函数式编程中，函数是中心概念，上面这段代码其实是定义了一个常函数，它并不是一个赋值，因为Haskell中的每个函数都是数学意义上的函数（即“纯粹”），即使是副作用的IO操作也只是对纯代码生成的操作的描述，没有语句或指令。

相比于面向对象编程是在描述“如何做”(命令式编程)，函数式编程在描述“做什么”的问题(声明式编程)，Java8将函数作为一等公民，我们可以改变下自己的思想，利用函数来编程。

## 行为参数化、函数对象和策略模式
行为参数化是将行为作为参数传递，在没有函数式编程时，我们已经这样去做了：我们可以在一个对象方法内，调用参数对象的方法。我们来看看对一个List进行排序的代码：
```java
List<String> words = Arrays.asList("hello", "sayi", "hi");
words.sort(new Comparator<String>() {
    public int compare(String str1, String str2) {
        return Integer.compare(str1.length(), str2.length());
    }
});
```
排序的逻辑就是一种行为，**匿名类是面向对象设计中表示行为参数化的一种方式**，我们把这种只有一个方法的接口称为函数类型，实现称为函数对象，Comparator称为抽象策略，不同的实现方式表示了不同的具体策略，函数对象即是具体的策略实现。

这样的写法总要生成一个类的对象，然后传递对象参数，调用参数的方法，如果能直接传递函数:`Int f(x,y)`，代码不是更贴切吗？

## 表示行为：@FunctionalInterface函数式接口和lambda表达式
只有一个方法的接口(允许有默认方法)以前称之为函数类型(function types)，Java8将这些接口用注解@FunctionalInterface标注，它们有了一个正式的名字：函数接口(function interface)。Comparator正是函数式接口，所有函数式接口都可以声明函数，这个函数也可以被传递，即参数化。
```java
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```
函数使用lambda表达式来表示，我们可以通过下面代码声明一个比较根据字符串长度比较的函数：
```java
Comparator<String> func = (str1, str2) -> Integer.compare(str1.length(), str2.length());
```
lambda表达式无法单独存在，必须是一个函数式接口类型，具体是什么函数式接口，需要给出明确定义或者在参数中进行类型推导来确定，上面这个lambda表达式是无法赋值给一个Object对象的，func是一个函数的定义，它不应该被理解为对象。

接下来我们使用lambda表达式改写一下排序的代码：
```java
words.sort((str1, str2) -> Integer.compare(str1.length(), str2.length()));
```

到这里我们可以声明一个函数，并且传递函数了。可能有人有疑问，因为所有事物都是对象，它不就是用lambda表示了一个函数对象，传递的仍然是对象吗？的确，JAVA语言因为背负了面向对象的历史包袱，背后的设计逻辑可能如此，但是它给了我们函数式编程思考的能力，我觉得更应该这样理解：**它是传递了函数，而不是传递了对象，上文中的func变量，更应该理解为是个函数定义，而不是一个对象**。


## java.util.function：通用函数式接口
当我们编写代码，需要传递函数时，我们是新写一个函数式接口吗？不是的，[java.util.function](https://docs.oracle.com/javase/8/docs/api/)提供了43个常用函数定义，作为通用目的的函数式接口，主要分为六类(参见《Effective Java》)。

| 接口 | 函数签名 | 示例 |
| --- | --- | --- |
|Function<T,R>|R apply(T t)|Arrays::asList|
|Predicate<T>|boolean test(T t)|Collection::isEmpty|
|Consumer<T>|void accept(T t)|String::toLowerCase|
|Supplier<T>|T get()|Instant::now|
|UnaryOperator<T>|T apply(T t)|System.out::println|
|BinaryOperator<T>|T apply(T t1, T t2)|BigInteger::add|

`Function`是最基础的函数式接口，其余函数式接口可以用Function来表示，比如Function<T, Void>和Consumer<T>是等价的，但是Consumer的名称更能体现这个函数的含义(消费处理某个元素)，所以增加了这个函数式接口。

根据这六类通用函数定义，还有很多变种：
1. 泛型是不允许使用原生类型的，提供了很多原生类型(int、long、double)的变种，比如：IntFunction<R>、LongPredicate、DoubleConsumer、LongToDoubleFunction、ToIntFunction<T>、BooleanSupplier等。 **永远优先使用原生类型函数，而不是通过原生类型的包装类来使用通用函数，因为包装需要代价。**
2. 比如两个参数版本的：BiFunction<T, U, R>、BiPredicate<T, U>、BiConsumer<T,U>、ObjIntConsumer<T>、ToDoubleBiFunction<T,U>等。

我们在设计时应该尽量使用这些通用的函数式接口，但是也有一些极少情况需要自定义函数式接口，比如JDK的Comparator<T>，尽管和ToIntBiFunction<T,T>是等价的，但是我们有理由去定义这样一个新的接口：**比较操作是一个通用的函数，并且Comparator是个有意义的命名，我们还可以在Comparator的接口中定义相关的接口默认方法。**

## 有含义的函数式接口
Java提供了一些有含义的函数式接口。
* Comparator<T>
* Runnable
* Callable<V>
* java.io.FileFilter
* java.io.FilenameFilter
* java.util.logging.Filter

这些是极少没有使用通用函数式接口的例子，它至少满足了下面两个理由：
1. 通用的操作，用命名来表示操作含义
2. 接口实现相关的默认方法

## Method References：方法引用
方法引用作为lambda表达式更简洁的表示方式，通过有意义的方法名，我们可以更容易阅读代码：
```java
// words.sort((str1, str2) -> Integer.compare(str1.length(), str2.length()));
words.sort(Comparator.comparingInt(String::length));
```
其中Comparator.comparingInt是接口的默认实现方法：
```java
public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
    Objects.requireNonNull(keyExtractor);
    return (Comparator<T> & Serializable)
        (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c1), keyExtractor.applyAsInt(c2));
}
```

我们再来看个方法引用的简单例子，它是把两个整数相加，可以利用Integer提供的sum方法作为函数传递：
```java
process((a, b) -> a + b);

process(Integer::sum);
```

方法引用在语义上更通俗，Integer::sum就是一个函数，我们传递了一个加法的函数，**相比较lambda表达式，应该优先使用方法引用**。

方法引用是lambda的简洁表示，我们来看看方法引用的种类有哪些和它们对应的lambda表达式语法：

| 种类 | 方法引用 | lambda |
| --- | --- | --- |
|引用静态方法ContainingClass::staticMethodName|Integer::parseInt|str -> Integer.parseInt(str)|
|引用对象的某个方法containingObject::instanceMethodName|Instant.now()::isAfter|Instant then = Instant.now(); t -> then.isAfter(t)|
|引用参数类型的对象方法ContainingType::methodName|String::toLowerCase|str -> str.toLowerCase()|
|引用构造函数ClassName::new|增加元素ClassName::new|TreeMap<K,V>::new|() -> new TreeMap<K,V>

需要注意的是，**String::toLowerCase和str -> str.toLowerCase()是等价的**。

### 构造器方法引用
我们可以使用::new方式引用构造器，比如：
```java
// () -> new HashMap<String, String>()
Supplier<Map<String, String>> supplier = HashMap::new;
Map<String, String> map = supplier.get();
```

但是构造方法会被重载，比如我们希望引用参数为int类型的HashMap构造函数，该怎么办呢？

我们知道，lambda表达式函数的类型是需要明确声明或者类型推导的，所以我们可以声明一个需要函数类型为IntFunction<Map<String, String>>，这个函数类型就会对new HashMap(int)构造函数引用，代码如下：
```java
// (initialCapacity) -> new HashMap<String, String>(initialCapacity)
IntFunction<Map<String, String>> func = HashMap::new;
Map<String, String> map = func.apply(32);
```
综上，重载方法的引用，可以通过函数类型来确定具体的方法。

## 总结
使用对象思维和函数思维的实现偏差会很大，比如加减乘除，我们先来看看面向对象的写法：
```java
public enum Operation {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };
    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);
}
```

函数式编程就会将这些操作声明为函数，最后来看看函数式写法：
```java
public enum Operation {
    PLUS("+", (x, y) -> x + y), 
    MINUS("-", (x, y) -> x - y), 
    TIMES("*", (x, y) -> x * y), 
    DIVIDE("/", (x, y) -> x / y);
    
    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}
```
函数式编程，可以改变我们既往的编码习惯，比如模板设计模式中的抽象方法可以通函数参数来实现，Java的Stream编程中大量使用了函数式编程的思想。

总之，Java在面向对象编程语言里面，为函数式编程打开了大门，是时候使用函数思想来编程了。
