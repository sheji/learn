# 一、Lambda表达式

## 1、概念

Lambda 是一个匿名函数，我们可以把 Lambda 表达式理解为是一段可以传递的代码(将代码像数据一样进行传递)。使用它可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。 

## 2、语法

"->"：lambda操作符 或 箭头操作符

"->"左边：lambda形参列表 （其实就是接口中的抽象方法的形参列表）

 "->"右边：lambda体 （其实就是重写的抽象方法的方法体）

## 3、Lambda表达式的使用

6种情况介绍

总结：
 *    "->"左边：lambda形参列表的参数类型可以省略(类型推断)；如果lambda形参列表只有一个参数，其一对()也可以省略
 *    "->"右边：lambda体应该使用一对{}包裹；如果lambda体只有一条执行语句（可能是return语句），省略这一对{}和return关键字

[1]、语法格式一：无参，无返回值

```java
Runnable r2 = () -> {
    System.out.println("我爱北京故宫");
};
```

[2]、语法格式二：Lambda 需要一个参数，但是没有返回值 

```java
 Consumer<String> con1 = (String s) -> {
     System.out.println(s);
 };
```

[3]、语法格式三：数据类型可以省略，因为可由编译器推断得出，称为"类型推断"

```java
Consumer<String> con2 = (s) -> {
    System.out.println(s);
};
```

[4]、语法格式四：Lambda 若只需要一个参数时，参数的小括号可以省略 

```java
Consumer<String> con2 = s -> {
    System.out.println(s);
};
```

[5]、语法格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值 

```java
Comparator<Integer> com2 = (o1,o2) -> {
    System.out.println("实现函数式接口方法");
    return o1.compareTo(o2);
};
```

[6]、语法格式六：当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略 

```java
Comparator<Integer> com2 = (o1,o2) -> o1.compareTo(o2);
```

相关的测试程序

```java
/**
 * Lambda表达式的使用
 *
 * 1.举例： (o1,o2) -> Integer.compare(o1,o2);
 * 2.格式：
 *      -> :lambda操作符 或 箭头操作符
 *      ->左边：lambda形参列表 （其实就是接口中的抽象方法的形参列表）
 *      ->右边：lambda体 （其实就是重写的抽象方法的方法体）
 *
 * 3. Lambda表达式的使用：（分为6种情况介绍）
 *
 *    总结：
 *    ->左边：lambda形参列表的参数类型可以省略(类型推断)；如果lambda形参列表只有一个参数，其一对()也可以省略
 *    ->右边：lambda体应该使用一对{}包裹；如果lambda体只有一条执行语句（可能是return语句），省略这一对{}和return关键字
 *
 * 4.Lambda表达式的本质：作为函数式接口的实例
 *
 * 5. 如果一个接口中，只声明了一个抽象方法，则此接口就称为函数式接口。我们可以在一个接口上使用 @FunctionalInterface 注解，
 *   这样做可以检查它是否是一个函数式接口。
 *
 * 6. 所以以前用匿名实现类表示的现在都可以用Lambda表达式来写。
 */
public class LambdaTest1 {
    //语法格式一：无参，无返回值
    @Test
    public void test1(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };

        r1.run();

        System.out.println("***********************");

        Runnable r2 = () -> {
            System.out.println("我爱北京故宫");
        };

        r2.run();
    }
    //语法格式二：Lambda 需要一个参数，但是没有返回值。
    @Test
    public void test2(){

        Consumer<String> con = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        con.accept("谎言和誓言的区别是什么？");

        System.out.println("*******************");

        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听得人当真了，一个是说的人当真了");

    }

    //语法格式三：数据类型可以省略，因为可由编译器推断得出，称为“类型推断”
    @Test
    public void test3(){

        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听得人当真了，一个是说的人当真了");

        System.out.println("*******************");

        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        con2.accept("一个是听得人当真了，一个是说的人当真了");

    }

    @Test
    public void test4(){

        ArrayList<String> list = new ArrayList<>();//类型推断

        int[] arr = {1,2,3};//类型推断

    }

    //语法格式四：Lambda 若只需要一个参数时，参数的小括号可以省略
    @Test
    public void test5(){
        Consumer<String> con1 = (s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听得人当真了，一个是说的人当真了");

        System.out.println("*******************");

        Consumer<String> con2 = s -> {
            System.out.println(s);
        };
        con2.accept("一个是听得人当真了，一个是说的人当真了");


    }

    //语法格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值
    @Test
    public void test6(){

        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println("实现函数式接口方法");
                return o1.compareTo(o2);
            }
        };

        System.out.println(com1.compare(12,21));

        System.out.println("*****************************");
        Comparator<Integer> com2 = (o1,o2) -> {
		System.out.println("实现函数式接口方法");
            return o1.compareTo(o2);
        };

        System.out.println(com2.compare(12,6));


    }

    //语法格式六：当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略
    @Test
    public void test7(){

        Comparator<Integer> com1 = (o1,o2) -> {
            return o1.compareTo(o2);
        };

        System.out.println(com1.compare(12,6));

        System.out.println("*****************************");

        Comparator<Integer> com2 = (o1,o2) -> o1.compareTo(o2);

        System.out.println(com2.compare(12,21));

    }

    @Test
    public void test8(){
        Consumer<String> con1 = s -> {
            System.out.println(s);
        };
        con1.accept("一个是听得人当真了，一个是说的人当真了");

        System.out.println("*****************************");

        Consumer<String> con2 = s -> System.out.println(s);

        con2.accept("一个是听得人当真了，一个是说的人当真了");

    }

}
```

## 4、Lambda表达式的本质

作为函数式接口的实例



# 二、函数式(Function)接口

## 1、概念

*  ==只包含一个抽象方法的接口，称为函数式接口==。 

-  你可以通过 Lambda 表达式来创建该接口的对象。(若 Lambda 表达式抛出一个受检异常(即:非运行时异常)，那么该异常需要在目标接口的抽象方法上进行声明)。 
- 我们可以在一个接口上使用 ==@FunctionalInterface== 注解，这样做可以检查它是否是一个函数式接口。同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。 
- ==在java.util.function包下定义了Java 8 的丰富的函数式接口== 

 ![image-20191103222127637](images/image-20191103222127637.png)



## 2、如何理解函数式接口 

* Java从诞生日起就是一直倡导"一切皆对象"，在Java里面面向对象(OOP) 编程是一切。但是随着python、scala 等语言的兴起和新技术的挑战，Java不得不做出调整以便支持更加广泛的技术要求，也即==java不但可以支持OOP还可以支持OOF(面向函数编程)== 

* 在函数式编程语言当中，函数被当做一等公民对待。在将函数作为一等公民的编程语言中，Lambda表达式的类型是函数。但是在Java8中，有所不同。在 Java8中，Lambda表达式是对象，而不是函数，它们必须依附于一类特别的对象类型——==函数式接口==。 

* 简单的说，在Java8中，==Lambda表达式就是一个函数式接口的实例==。这就是 Lambda表达式和函数式接口的关系。也就是说，只要一个对象是函数式接口的实例，那么该对象就可以用Lambda表达式来表示。 

* ==所以以前用匿名实现类表示的现在都可以用Lambda表达式来写==。 

## 3、自定义函数式接口 

![image-20191103222559064](images/image-20191103222559064.png)

函数式接口中使用泛型: 

![image-20191103222617079](images/image-20191103222617079.png)

## 4、Java 内置四大核心函数式接口 

| 函数式接口                | 参数类型 | 返回类型 | 用途                                                         |
| ------------------------- | -------- | -------- | ------------------------------------------------------------ |
| Consumer\<T> 消费型接口   | T        | void     | 对类型为T的对象应用操作，包含方法: ==void accept(T t)==      |
| Supplier\<T> 供给型接口   | 无       | T        | 返回类型为T的对象，包含方法:==T get()==                      |
| Function<T, R> 函数型接口 | T        | R        | 对类型为T的对象应用操作，并返回结果。结果是R类型的象。包含方法:==R apply(T t)== |
| Predicate\<T>断定型接口   | T        | boolean  | 确定类型为T的对象是否满足某约束，并返回 boolean 值。包含方法:==boolean test(T t)== |

其他接口 

| 函数式接口                                                | 参数类型        | 返回类型        | 用途                                                         |
| --------------------------------------------------------- | --------------- | --------------- | ------------------------------------------------------------ |
| BiFunction<T, U, R>                                       | T, U            | R               | 对类型为 T, U 参数应用操作，返回 R 类型的结 果。包含方法为: ==R apply(T t, U u)== |
| UnaryOperator\<T> (Function子接口)                        | T               | T               | 对类型为T的对象进行一元运算，并返回T类型的 结果。包含方法为:==T apply(T t)== |
| BinaryOperator\<T> (BiFunction 子接口)                    | T, T            | T               | 对类型为T的对象进行二元运算，并返回T类型的 结果。包含方法为: ==T apply(T t1, T t2)== |
| BiConsumer<T, U>                                          | T, U            | void            | 对类型为T, U 参数应用操作。 包含方法为: ==void accept(T t, U u)== |
| BiPredicate<T,U>                                          | T,U             | boolean         | 包含方法为: ==boolean test(T t,U u)==                        |
| ToIntFunction\<T> ToLongFunction\<T> ToDoubleFunction\<T> | T               | int long double | 分别计算int、long、double值的函数                            |
| IntFunction\<R> LongFunction\<R> DoubleFunction\<R>       | int long double | R               | 参数分别为int、long、double 类型的函数                       |

```java

/**
 * java内置的4大核心函数式接口
 *
 * 消费型接口 Consumer<T>     void accept(T t)
 * 供给型接口 Supplier<T>     T get()
 * 函数型接口 Function<T,R>   R apply(T t)
 * 断定型接口 Predicate<T>    boolean test(T t)
 */
public class LambdaTest2 {

    @Test
    public void test1(){

        happyTime(500, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("学习太累了，去天上人间买了瓶矿泉水，价格为：" + aDouble);
            }
        });

        System.out.println("********************");

        happyTime(400,money -> System.out.println("学习太累了，去天上人间喝了口水，价格为：" + money));
    }

    public void happyTime(double money, Consumer<Double> con){
        con.accept(money);
    }


    @Test
    public void test2(){
        List<String> list = Arrays.asList("北京","南京","天津","东京","西京","普京");

        List<String> filterStrs = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });

        System.out.println(filterStrs);


        List<String> filterStrs1 = filterString(list,s -> s.contains("京"));
        System.out.println(filterStrs1);
    }

    //根据给定的规则，过滤集合中的字符串。此规则由Predicate的方法决定
    public List<String> filterString(List<String> list, Predicate<String> pre){

        ArrayList<String> filterList = new ArrayList<>();

        for(String s : list){
            if(pre.test(s)){
                filterList.add(s);
            }
        }

        return filterList;
    }

}
```



# 三、方法引用和构造器引用

## 1、方法引用

- 当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用! 
- 方法引用可以看做是Lambda表达式深层次的表达。换句话说，方法引用就 是Lambda表达式，也就是函数式接口的一个实例，通过方法的名字来指向一个方法，可以认为是Lambda表达式的一个语法糖。 
- 要求:==实现接口的抽象方法的参数列表和返回值类型，必须与方法引用的方法的参数列表和返回值类型保持一致==
- 格式:使用操作符 "::" 将类(或对象) 与 方法名分隔开来。 
- 如下三种主要使用情况
  - ==对象::实例方法名==
  - ==类::静态方法名==
  - ==类::实例方法名== 



例如：

```java
Consumer<String> con1 = str -> System.out.println(str);
```

等同于: 

```java
Consumer<String> con2 = System.out::println;
```

例如: 

```java
Comparator<Integer> com1 = (t1,t2) -> Integer.compare(t1,t2);
```

等同于：

```java
Comparator<Integer> com2 = Integer::compare;
```

例如：

```java
BiPredicate<String,String> pre1 = (s1,s2) -> s1.equals(s2);
```

等同于：

```java
BiPredicate<String,String> pre2 = String :: equals;
```

==注意:当函数式接口方法的第一个参数是需要引用方法的调用者，并且第二个参数是需要引用方法的参数(或无参数)时:ClassName::methodName== 



```java
/**
 * 方法引用的使用
 *
 * 1.使用情境：当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！
 *
 * 2.方法引用，本质上就是Lambda表达式，而Lambda表达式作为函数式接口的实例。所以
 *   方法引用，也是函数式接口的实例。
 *
 * 3. 使用格式：  类(或对象) :: 方法名
 *
 * 4. 具体分为如下的三种情况：
 *    情况1     对象 :: 非静态方法
 *    情况2     类 :: 静态方法
 *	  情况3     类 :: 非静态方法
 *
 * 5. 方法引用使用的要求：要求接口中的抽象方法的形参列表和返回值类型与方法引用的方法的
 *    形参列表和返回值类型相同！（针对于情况1和情况2）
 *
 */
public class MethodRefTest {

	// 情况一：对象 :: 实例方法
	//Consumer中的void accept(T t)
	//PrintStream中的void println(T t)
	@Test
	public void test1() {
		Consumer<String> con1 = str -> System.out.println(str);
		con1.accept("北京");

		System.out.println("*******************");
		PrintStream ps = System.out;
		Consumer<String> con2 = ps::println;
		con2.accept("beijing");
	}
	
	//Supplier中的T get()
	//Employee中的String getName()
	@Test
	public void test2() {
		Employee emp = new Employee(1001,"Tom",23,5600);

		Supplier<String> sup1 = () -> emp.getName();
		System.out.println(sup1.get());

		System.out.println("*******************");
		Supplier<String> sup2 = emp::getName;
		System.out.println(sup2.get());

	}

	// 情况二：类 :: 静态方法
	//Comparator中的int compare(T t1,T t2)
	//Integer中的int compare(T t1,T t2)
	@Test
	public void test3() {
		Comparator<Integer> com1 = (t1,t2) -> Integer.compare(t1,t2);
		System.out.println(com1.compare(12,21));

		System.out.println("*******************");

		Comparator<Integer> com2 = Integer::compare;
		System.out.println(com2.compare(12,3));

	}
	
	//Function中的R apply(T t)
	//Math中的Long round(Double d)
	@Test
	public void test4() {
		Function<Double,Long> func = new Function<Double, Long>() {
			@Override
			public Long apply(Double d) {
				return Math.round(d);
			}
		};

		System.out.println("*******************");

		Function<Double,Long> func1 = d -> Math.round(d);
		System.out.println(func1.apply(12.3));

		System.out.println("*******************");

		Function<Double,Long> func2 = Math::round;
		System.out.println(func2.apply(12.6));
	}

	// 情况三：类 :: 实例方法  (有难度)
	// Comparator中的int comapre(T t1,T t2)
	// String中的int t1.compareTo(t2)
	@Test
	public void test5() {
		Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
		System.out.println(com1.compare("abc","abd"));

		System.out.println("*******************");

		Comparator<String> com2 = String :: compareTo;
		System.out.println(com2.compare("abd","abm"));
	}

	//BiPredicate中的boolean test(T t1, T t2);
	//String中的boolean t1.equals(t2)
	@Test
	public void test6() {
		BiPredicate<String,String> pre1 = (s1,s2) -> s1.equals(s2);
		System.out.println(pre1.test("abc","abc"));

		System.out.println("*******************");
		BiPredicate<String,String> pre2 = String :: equals;
		System.out.println(pre2.test("abc","abd"));
	}
	
	// Function中的R apply(T t)
	// Employee中的String getName();
	@Test
	public void test7() {
		Employee employee = new Employee(1001, "Jerry", 23, 6000);


		Function<Employee,String> func1 = e -> e.getName();
		System.out.println(func1.apply(employee));

		System.out.println("*******************");


		Function<Employee,String> func2 = Employee::getName;
		System.out.println(func2.apply(employee));

	}

}
```



## 2、构造器引用

格式: ==ClassName::new== 

与函数式接口相结合，自动与函数式接口中方法兼容。 

可以把构造器引用赋值给定义的方法，要求==构造器参数列表要与接口中抽象方法的参数列表一致!且方法的返回值即为构造器对应类的对象。== 



例如 ：

```java
Function<Integer,Employee> func1 = id -> new Employee(id);
```

等同于：

```java
Function<Integer,Employee> func2 = Employee :: new;
```



## 3、数组引用 

格式: ==type[] :: new== 

例如：

```java
Function<Integer,String[]> func1 = length -> new String[length];
```

等同于

```java
Function<Integer,String[]> func2 = String[] :: new;
```



```java
/**
 * 一、构造器引用
 *      和方法引用类似，函数式接口的抽象方法的形参列表和构造器的形参列表一致。
 *      抽象方法的返回值类型即为构造器所属的类的类型
 *
 * 二、数组引用
 *     大家可以把数组看做是一个特殊的类，则写法与构造器引用一致。
 *
 * Created by shkstart
 */
public class ConstructorRefTest {
	//构造器引用
    //Supplier中的T get()
    //Employee的空参构造器：Employee()
    @Test
    public void test1(){

        Supplier<Employee> sup = new Supplier<Employee>() {
            @Override
            public Employee get() {
                return new Employee();
            }
        };
        System.out.println("*******************");

        Supplier<Employee>  sup1 = () -> new Employee();
        System.out.println(sup1.get());

        System.out.println("*******************");

        Supplier<Employee>  sup2 = Employee :: new;
        System.out.println(sup2.get());
    }

	//Function中的R apply(T t)
    @Test
    public void test2(){
        Function<Integer,Employee> func1 = id -> new Employee(id);
        Employee employee = func1.apply(1001);
        System.out.println(employee);

        System.out.println("*******************");

        Function<Integer,Employee> func2 = Employee :: new;
        Employee employee1 = func2.apply(1002);
        System.out.println(employee1);

    }

	//BiFunction中的R apply(T t,U u)
    @Test
    public void test3(){
        BiFunction<Integer,String,Employee> func1 = (id,name) -> new Employee(id,name);
        System.out.println(func1.apply(1001,"Tom"));

        System.out.println("*******************");

        BiFunction<Integer,String,Employee> func2 = Employee :: new;
        System.out.println(func2.apply(1002,"Tom"));

    }

	//数组引用
    //Function中的R apply(T t)
    @Test
    public void test4(){
        Function<Integer,String[]> func1 = length -> new String[length];
        String[] arr1 = func1.apply(5);
        System.out.println(Arrays.toString(arr1));

        System.out.println("*******************");

        Function<Integer,String[]> func2 = String[] :: new;
        String[] arr2 = func2.apply(10);
        System.out.println(Arrays.toString(arr2));

    }
}
```



# 四、强大的StreamAPI

## 1、概述

* Java8中有两大最为重要的改变。第一个是 ==Lambda 表达式==;另外一个则是 ==Stream API==。 
* ==Stream API ( java.util.stream)== 把真正的函数式编程风格引入到Java中。这 是目前为止对Java类库最好的补充，因为Stream API可以极大提供Java程 序员的生产力，让程序员写出高效率、干净、简洁的代码。 
* Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。 ==使用 Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询==。 也可以使用 Stream API 来并行执行操作。简言之，Stream API 提供了一种 高效且易于使用的处理数据的方式。 

## 2、为什么要使用Stream API 

* 实际开发中，项目中多数数据源都来自于Mysql，Oracle等。但现在数 据源可以更多了，有MongDB，Radis等，而这些NoSQL的数据就需要 Java层面去处理。 
* Stream 和 Collection 集合的区别：==Collection 是一种静态的内存数据结构，而 Stream 是有关计算的==。前者是主要面向内存，存储在内存中， 后者主要是面向 CPU，通过 CPU 实现计算。 

## 3、什么是Stream

Stream到底是什么呢? 

是数据渠道，用于操作数据源(集合、数组等)所生成的元素序列。 

=="集合讲的是数据，Stream讲的是计算!"==

==注意:== 

* Stream 自己不会存储元素。

* Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。 

* Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。 

## 4、Stream 的操作三个步骤 

[1]、创建 Stream 

一个数据源(如:集合、数组)，获取一个流 

[2]、中间操作 

一个中间操作链，对数据源的数据进行处理 

[3]、终止操作(终端操作) 

一旦执行终止操作，就执行中间操作链，并产生结果。之后，不会再被使用 

![image-20191104102853808](images/image-20191104102853808.png)

## 5、创建 Stream方式 

### [1]、通过集合 

Java8 中的 Collection 接口被扩展，提供了两个获取流的方法:

```java
default Stream<E> stream();//返回一个顺序流
default Stream<E> parallelStream();//返回一个并行流
```

### [2]、通过数组

Java8 中的 Arrays 的静态方法 stream() 可以获取数组流:

```java
static <T> Stream<T> stream(T[] array);//返回一个流 
```

重载形式，能够处理对应基本类型的数组:

```java
public static IntStream stream(int[] array)
public static LongStream stream(long[] array)
public static DoubleStream stream(double[] array) 
```

### [3]、通过Stream的of() 

可以调用Stream类静态方法 of(), 通过显示值创建一个 流。它可以接收任意数量的参数。 

```java
public static<T> Stream<T> of(T... values);//返回一个流
```

### [4]、创建无限流 

可以使用静态方法 `Stream.iterate()` 和 `Stream.generate()`, 创建无限流。

```java
public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f);//迭代
public static<T> Stream<T> generate(Supplier<T> s);//生成
```

```java
/**
 * 1. Stream关注的是对数据的运算，与CPU打交道
 *    集合关注的是数据的存储，与内存打交道
 *
 * 2.
 * ①Stream 自己不会存储元素。
 * ②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
 * ③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行
 *
 * 3.Stream 执行流程
 * ① Stream的实例化
 * ② 一系列的中间操作（过滤、映射、...)
 * ③ 终止操作
 *
 * 4.说明：
 * 4.1 一个中间操作链，对数据源的数据进行处理
 * 4.2 一旦执行终止操作，就执行中间操作链，并产生结果。之后，不会再被使用
 *
 *
 *  测试Stream的实例化
 */
public class StreamAPITest {

    //创建 Stream方式一：通过集合
    @Test
    public void test1(){
        List<Employee> employees = EmployeeData.getEmployees();

//        default Stream<E> stream() : 返回一个顺序流
        Stream<Employee> stream = employees.stream();

//        default Stream<E> parallelStream() : 返回一个并行流
        Stream<Employee> parallelStream = employees.parallelStream();

    }

    //创建 Stream方式二：通过数组
    @Test
    public void test2(){
        int[] arr = new int[]{1,2,3,4,5,6};
        //调用Arrays类的static <T> Stream<T> stream(T[] array): 返回一个流
        IntStream stream = Arrays.stream(arr);

        Employee e1 = new Employee(1001,"Tom");
        Employee e2 = new Employee(1002,"Jerry");
        Employee[] arr1 = new Employee[]{e1,e2};
        Stream<Employee> stream1 = Arrays.stream(arr1);

    }
    //创建 Stream方式三：通过Stream的of()
    @Test
    public void test3(){

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

    }

    //创建 Stream方式四：创建无限流
    @Test
    public void test4(){

//      迭代
//      public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
        //遍历前10个偶数
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);//偶数0-18


//      生成
//      public static<T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);//生成10个随机数

    }

}

```

## 6、Stream的中间操作 

 多个**中间操作**可以连接起来形成一个**流水线**，除非流水线上触发终止操作，否则**中间操作不会执行任何的处理!而在终止操作时一次性全部处理，称为"惰性求值"**。 

###  [1]、筛选与切片 

| 方法                | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| filter(Predicate p) | 接收 Lambda ， 从流中排除某些元素                            |
| distinct()          | 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素 |
| limit(long maxSize) | 截断流，使其元素不超过给定数量                               |
| skip(long n)        | 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补 |

```java
//1-筛选与切片
@Test
public void test1(){
    List<Employee> list = EmployeeData.getEmployees();
    //filter(Predicate p)——接收 Lambda ， 从流中排除某些元素。
    Stream<Employee> stream = list.stream();
    //练习：查询员工表中薪资大于7000的员工信息
    stream.filter(e -> e.getSalary() > 7000).forEach(System.out::println);

    System.out.println();
    //limit(n)——截断流，使其元素不超过给定数量。
    list.stream().limit(3).forEach(System.out::println);
    System.out.println();

    //skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
    list.stream().skip(3).forEach(System.out::println);

    System.out.println();

    //distinct()——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
    list.add(new Employee(1010,"刘强东",40,8000));
    list.add(new Employee(1010,"刘强东",41,8000));
    list.add(new Employee(1010,"刘强东",40,8000));

    //System.out.println(list);

    list.stream().distinct().forEach(System.out::println);
}
```



### [2]、映射 

| 方法                            | 描述                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| ==map(Function f)==             | 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。 |
| mapToDouble(ToDoubleFunction f) | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。 |
| mapToInt(ToIntFunction f)       | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。 |
| mapToLong(ToLongFunction f)     | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。 |
| ==flatMap(Function f)==         | 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流 |



```java
//映射
@Test
public void test2(){
    //map(Function f)——接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
    List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
    list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

    //练习1：获取员工姓名长度大于3的员工的姓名。
    List<Employee> employees = EmployeeData.getEmployees();
    Stream<String> namesStream = employees.stream().map(Employee::getName);
    namesStream.filter(name -> name.length() > 3).forEach(System.out::println);
    System.out.println();
    //练习2：
    Stream<Stream<Character>> streamStream = list.stream().map(StreamAPITest1::fromStringToStream);
    streamStream.forEach(s ->{
        s.forEach(System.out::println);
    });
    System.out.println();
    //flatMap(Function f)——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
    Stream<Character> characterStream = list.stream().flatMap(StreamAPITest1::fromStringToStream);
    characterStream.forEach(System.out::println);

}

//将字符串中的多个字符构成的集合转换为对应的Stream的实例
public static Stream<Character> fromStringToStream(String str){//aa
    ArrayList<Character> list = new ArrayList<>();
    for(Character c : str.toCharArray()){
        list.add(c);
    }
    return list.stream();

}


@Test
public void test3(){
    ArrayList list1 = new ArrayList();
    list1.add(1);
    list1.add(2);
    list1.add(3);

    ArrayList list2 = new ArrayList();
    list2.add(4);
    list2.add(5);
    list2.add(6);

    //list1.add(list2);
    list1.addAll(list2);
    System.out.println(list1);

}
```



### [3]、排序 

| 方法                   | 描述                               |
| ---------------------- | ---------------------------------- |
| sorted()               | 产生一个新流，其中按自然顺序排序   |
| sorted(Comparator com) | 产生一个新流，其中按比较器顺序排序 |



```java
//3-排序
@Test
public void test4(){
    //sorted()——自然排序
    List<Integer> list = Arrays.asList(12, 43, 65, 34, 87, 0, -98, 7);
    list.stream().sorted().forEach(System.out::println);
    //抛异常，原因:Employee没有实现Comparable接口
    //List<Employee> employees = EmployeeData.getEmployees();
    //employees.stream().sorted().forEach(System.out::println);


    //sorted(Comparator com)——定制排序

    List<Employee> employees = EmployeeData.getEmployees();
    employees.stream().sorted( (e1,e2) -> {

        int ageValue = Integer.compare(e1.getAge(),e2.getAge());
        if(ageValue != 0){
            return ageValue;
        }else{
            return -Double.compare(e1.getSalary(),e2.getSalary());
        }

    }).forEach(System.out::println);
}
```



## 7、Stream 的终止操作 

* 终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例 如:List、Integer，甚至是 void 。 
* 流进行了终止操作后，不能再次使用。 

### [1]、匹配与查找 

| 方法                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| allMatch(Predicate p)  | 检查是否匹配所有元素                                         |
| anyMatch(Predicate p)  | 检查是否至少匹配一个元素                                     |
| noneMatch(Predicate p) | 检查是否没有匹配所有元素                                     |
| findFirst()            | 返回第一个元素                                               |
| findAny()              | 返回当前流中的任意元素                                       |
| count()                | 返回流中元素总数                                             |
| max(Comparator c)      | 返回流中最大值                                               |
| min(Comparator c)      | 返回流中最小值                                               |
| forEach(Consumer c)    | ==内部迭代==(使用 Collection 接口需要用户去做迭代称为**外部迭代**。相反，Stream API 使用内部迭代——它帮你把迭代做了) |



```java
//1-匹配与查找
@Test
public void test1(){
    List<Employee> employees = EmployeeData.getEmployees();

    //allMatch(Predicate p)——检查是否匹配所有元素。
    //练习：是否所有的员工的年龄都大于18
    boolean allMatch = employees.stream().allMatch(e -> e.getAge() > 18);
    System.out.println(allMatch);

    //anyMatch(Predicate p)——检查是否至少匹配一个元素。
    //练习：是否存在员工的工资大于 10000
    boolean anyMatch = employees.stream().anyMatch(e -> e.getSalary() > 10000);
    System.out.println(anyMatch);

    //noneMatch(Predicate p)——检查是否没有匹配的元素。
    //练习：是否存在员工姓“雷”
    boolean noneMatch = employees.stream().noneMatch(e -> e.getName().startsWith("雷"));
    System.out.println(noneMatch);
    //findFirst——返回第一个元素
    Optional<Employee> employee = employees.stream().findFirst();
    System.out.println(employee);
    //findAny——返回当前流中的任意元素
    Optional<Employee> employee1 = employees.parallelStream().findAny();
    System.out.println(employee1);

}

@Test
public void test2(){
    List<Employee> employees = EmployeeData.getEmployees();
    // count——返回流中元素的总个数
    long count = employees.stream().filter(e -> e.getSalary() > 5000).count();
    System.out.println(count);
    //max(Comparator c)——返回流中最大值
    //练习：返回最高的工资：
    Stream<Double> salaryStream = employees.stream().map(e -> e.getSalary());
    Optional<Double> maxSalary = salaryStream.max(Double::compare);
    System.out.println(maxSalary);
    //min(Comparator c)——返回流中最小值
    //练习：返回最低工资的员工
    Optional<Employee> employee = employees.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
    System.out.println(employee);
    System.out.println();
    //forEach(Consumer c)——内部迭代
    employees.stream().forEach(System.out::println);

    //使用集合的遍历操作
    employees.forEach(System.out::println);
}
```



### [2]、归约 

| 方法                             | 描述                                                      |
| -------------------------------- | --------------------------------------------------------- |
| reduce(T iden, BinaryOperator b) | 可以将流中元素反复结合起来，得到一个值。返回 T            |
| reduce(BinaryOperator b)         | 可以将流中元素反复结合起来，得到一个值。返回 Optional\<T> |

备注:map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名。 



```java
//2-归约
@Test
public void test3(){
    //reduce(T identity, BinaryOperator)——可以将流中元素反复结合起来，得到一个值。返回T
    //练习1：计算1-10的自然数的和
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    Integer sum = list.stream().reduce(0, Integer::sum);
    System.out.println(sum);


    //reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
    //练习2：计算公司所有员工工资的总和
    List<Employee> employees = EmployeeData.getEmployees();
    Stream<Double> salaryStream = employees.stream().map(Employee::getSalary);
    //Optional<Double> sumMoney = salaryStream.reduce(Double::sum);
    Optional<Double> sumMoney = salaryStream.reduce((d1,d2) -> d1 + d2);
    System.out.println(sumMoney.get());

}
```



### [3]、收集 

| 方法                 | 描述                                                         |
| -------------------- | ------------------------------------------------------------ |
| collect(Collector c) | 将流转换为其他形式。接收一个 Collector 接口的实现，用于给Stream中元素做汇总的方法 |

Collector 接口中方法的实现决定了如何对流执行收集的操作(如收集到 List、Set、 Map)。 

另外， Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例， 具体方法与实例如下表: 

<table>
    <tr>
        <td>方法</td>
        <td>返回类型</td>
		<td>作用</td>
    </tr>
     <tr>
        <td style="color:red">toList</td>
        <td>List&lt;T&gt;</T></td>
		<td>把流中元素收集到List</td>
    </tr>
    <tr>
        <td colspan="3">List&lt;Employee&gt; emps= list.stream().collect(Collectors.toList());</td>
    </tr>
    <tr>
        <td style="color:red">toSet</td>
        <td>Set&lt;T&gt;</td>
		<td>把流中元素收集到Set</td>
    </tr>
    <tr>
        <td colspan="3">Set&lt;Employee&gt; emps= list.stream().collect(Collectors.toSet());</td>
    </tr>
	<tr>
        <td style="color:red">toCollection</td>
        <td>Collection<T></td>
		<td>把流中元素收集到创建的集合</td>
    </tr>
    <tr>
        <td colspan="3">Collection&lt;Employee&gt; emps =list.stream().collect(Collectors.toCollection(ArrayList::new));</td>
    </tr>
    <tr>
        <td>counting</td>
        <td>Long</td>
		<td>计算流中元素的个数</td>
    </tr>
    <tr>
        <td colspan="3">long count = list.stream().collect(Collectors.counting());</td>
    </tr>
    <tr>
        <td>summingInt</td>
        <td>Integer</td>
		<td>对流中元素的整数属性求和</td>
    </tr>
    <tr>
        <td colspan="3">int total=list.stream().collect(Collectors.summingInt(Employee::getSalary));</td>
    </tr>
    <tr>
        <td>averagingInt</td>
        <td>Double</td>
		<td>计算流中元素Integer属性的平均值</td>
    </tr>
    <tr>
        <td colspan="3">double avg = list.stream().collect(Collectors.averagingInt(Employee::getSalary));</td>
    </tr>    
    <tr>
        <td>summarizingInt</td>
        <td>IntSummaryStatistics</td>
		<td>收集流中Integer属性的统计值。如:平均值</td>
    </tr>
    <tr>
        <td colspan="3">int SummaryStatisticsiss= list.stream().collect(Collectors.summarizingInt(Employee::getSalary));</td>
    </tr>      
    <tr>
        <td>joining</td>
        <td>String</td>
		<td>连接流中每个字符串</td>
    </tr>
    <tr>
        <td colspan="3">String str= list.stream().map(Employee::getName).collect(Collectors.joining());</td>
    </tr>        
    <tr>
        <td>maxBy</td>
        <td>Optional&lt;T&gt;</td>
		<td>根据比较器选择最大值</td>
    </tr>
    <tr>
        <td colspan="3">Optional&lt;Emp&gt;max= list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary)));</td>
    </tr>   
    <tr>
        <td>minBy</td>
        <td>Optional&lt;T&gt;</td>
		<td>根据比较器选择最小值</td>
    </tr>
    <tr>
        <td colspan="3">Optional&lt;Emp&gt; min = list.stream().collect(Collectors.minBy(comparingInt(Employee::getSalary)));</td>
    </tr>       
    <tr>
        <td>reducing</td>
        <td>归约产生的类型&lt;T&gt;</td>
		<td>从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值</td>
    </tr>
    <tr>
        <td colspan="3">int total=list.stream().collect(Collectors.reducing(0, Employee::getSalar, Integer::sum));</td>
    </tr>  
    <tr>
        <td>collectingAndThen</td>
        <td>转换函数返回的类型</td>
		<td>包裹另一个收集器，对其结果转换函数</td>
    </tr>
    <tr>
        <td colspan="3">int how= list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));</td>
    </tr> 
    <tr>
        <td>groupingBy</td>
        <td>Map&lt;K, List&lt;T&gt;&gt;</td>
		<td>根据某属性值对流分组，属性为K,结果为V</td>
    </tr>
    <tr>
        <td colspan="3">Map&lt;Emp.Status, List&lt;Emp&gt;&gt; map= list.stream() .collect(Collectors.groupingBy(Employee::getStatus));</td>
    </tr>
    <tr>
        <td>partitioningBy</td>
        <td>Map&lt;Boolean, List&lt;T&gt;&gt;</td>
		<td>根据true或false进行分区</td>
    </tr>
    <tr>
        <td colspan="3">Map&lt;Boolean,List&lt;Emp&gt;&gt; vd = list.stream().collect(Collectors.partitioningBy(Employee::getManage));</td>
    </tr>
</table>



```java
//3-收集
@Test
public void test4(){
    //collect(Collector c)——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    //练习1：查找工资大于6000的员工，结果返回为一个List或Set

    List<Employee> employees = EmployeeData.getEmployees();
    List<Employee> employeeList = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toList());

    employeeList.forEach(System.out::println);
    System.out.println();
    Set<Employee> employeeSet = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toSet());

    employeeSet.forEach(System.out::println);
}
```



# 五、Optional类

## 1、概述

* 到目前为止，臭名昭著的空指针异常是导致Java应用程序失败的最常见原因。 以前，为了解决空指针异常，Google公司著名的Guava项目引入了Optional类， Guava通过使用检查空值的方式来防止代码污染，它鼓励程序员写更干净的代码。受到Google Guava的启发，Optional类已经成为Java 8类库的一部分。 
* ==Optional\<T> 类(java.util.Optional) 是一个容器类，它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常==。 
* Optional类的Javadoc描述如下:这是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。 
* ==Optional提供很多有用的方法，这样我们就不用显式进行空值检测。== 

## 2、常用方法

### [1]、创建Optional类对象的方法

```java
Optional.of(T t);//创建一个 Optional 实例，t必须非空;
Optional.empty();//创建一个空的 Optional 实例
Optional.ofNullable(T t);//t可以为null
```

### [2]、判断Optional容器中是否包含对象 

```java
boolean isPresent();//判断是否包含对象
void ifPresent(Consumer<? super T> consumer);//如果有值，就执行Consumer接口的实现代码，并且该值会作为参数传给它。
```

### [3]、获取Optional容器对象

```java
T get();//如果调用对象包含值，返回该值，否则抛异常
T orElse(T other);//如果有值则将其返回，否则返回指定的other对象。
T orElseGet(Supplier<? extends T> other);//如果有值则将其返回，否则返回由Supplier接口实现提供的对象。
T orElseThrow(Supplier<? extends X> exceptionSupplier);//如果有值则将其返回，否则抛出由Supplier接口实现提供的异常。
```



```java
@Test
public void test1(){
    Girl girl = new Girl();
    //girl = null;
    //of(T t):保证t是非空的
    Optional<Girl> optionalGirl = Optional.of(girl);

}

@Test
public void test2(){
    Girl girl = new Girl();
    //girl = null;
    //ofNullable(T t)：t可以为null
    Optional<Girl> optionalGirl = Optional.ofNullable(girl);
    System.out.println(optionalGirl);
    //orElse(T t1):如果单前的Optional内部封装的t是非空的，则返回内部的t.
    //如果内部的t是空的，则返回orElse()方法中的参数t1.
    Girl girl1 = optionalGirl.orElse(new Girl("赵丽颖"));
    System.out.println(girl1);

}


public String getGirlName(Boy boy){
    return boy.getGirl().getName();
}

@Test
public void test3(){
    Boy boy = new Boy();
    boy = null;
    String girlName = getGirlName(boy);
    System.out.println(girlName);

}
//优化以后的getGirlName():
public String getGirlName1(Boy boy){
    if(boy != null){
        Girl girl = boy.getGirl();
        if(girl != null){
            return girl.getName();
        }
    }

    return null;
}

@Test
public void test4(){
    Boy boy = new Boy();
    boy = null;
    String girlName = getGirlName1(boy);
    System.out.println(girlName);
}

//使用Optional类的getGirlName():
public String getGirlName2(Boy boy){

    Optional<Boy> boyOptional = Optional.ofNullable(boy);
    //此时的boy1一定非空
    Boy boy1 = boyOptional.orElse(new Boy(new Girl("迪丽热巴")));

    Girl girl = boy1.getGirl();

    Optional<Girl> girlOptional = Optional.ofNullable(girl);
    //girl1一定非空
    Girl girl1 = girlOptional.orElse(new Girl("古力娜扎"));

    return girl1.getName();
}

@Test
public void test5(){
    Boy boy = null;
    boy = new Boy();
    boy = new Boy(new Girl("苍老师"));
    String girlName = getGirlName2(boy);
    System.out.println(girlName);

}
```



