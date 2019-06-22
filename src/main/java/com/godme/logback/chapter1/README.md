# 基本环境
- ``slf4j-api`` : java简单日志门面
- ``logback-core`` : logback核心
- ``logback-classic`` : 实现

如字面一样，``self4j-api``作为门面，定义了java日志的基本行为，属于接口行为的定义。

可以说现在统一一般都是用``self4j``进行日志打印，但是具体的动作执行，就要看具体的实现类了。
```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
       <!-- <scope>test</scope> -->
    </dependency>
</dependencies>
```
# 获取对象

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDemo {
    private static Logger log = LoggerFactory.getLogger(SimpleDemo.class);
    public static void main(String[] args) {
        log.info("this is log");
    }
}
```
获取步骤十分简单，但是要记住是哪个包下面的，现在日志的种类也有好多，而且类名都是相同的。

一般来说，普遍传入``XXX.class``进行日志对象的获取，但是底层是这样的
```java
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = getLogger(clazz.getName());
        ...
    }
```
也就是说，最终传入的还是字符串。

这样，我们就可以随便的获取到任意的日志对象，即使不知道某个类，我们依旧能够获取，甚至是不以类名为基本参数的对象。

在生产应用中，我们可以以此来进行服务日志的区分，而不是纠结于某一个类。

不用担心，底层是不会重复创建的，放心的用吧。


# 继承关系
``logback``中的继承关系，听起来很高大上，但是，只不过是如目录子文件那样的关系。
- ``com``
- ``com.godme``
- ``com.godme.dto``

这就算是层级的继承了，只要你的前缀是它，``.``了以后，你就是它儿子了。

# 级别
1. trace
2. debug
3. info
4. warn
5. error
6. off

日志级别的重要等级，正如编号一样的，后面的也就越重要， ``off``除外。

前五个都是能够``log.``进行打印调用的，最后一个完全是用作权限控制的。

# 权限
前面的是打印级别，那么现在就说说权限吧，也就是能够调用的打印级别。

| log\power | trace              | debug                    | info                     | warn                     | error                    | off                      |
| --------- | ------------------ | ------------------------ | ------------------------ | ------------------------ | ------------------------ | ------------------------ |
| ``trace`` | :heavy_check_mark: | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| ``debug`` | :heavy_check_mark: | :heavy_check_mark:       | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| ``info``  | :heavy_check_mark: | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| ``warn``  | :heavy_check_mark: | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_multiplication_x: | :heavy_multiplication_x: |
| ``error`` | :heavy_check_mark: | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_check_mark:       | :heavy_multiplication_x: |

也就是说，你的动作是需要权限的，高于权限的操作是不被允许的。

同时，子类的权限是不允许高于父类的权限的。

# 消耗
```java
       if(log.isDebugEnabled()){
           log.debug("msg : {}", "message");
       }
```
当然，``logback``会自动的进行权限校验，但是可能到权限外的操作，还是进行一下判断。

尤其是字符串拼接的话，推荐是用格式化的``{}``，仅有执行的时候才会去填充，避免了没有权限时，还不得不进行的拼接操作。

# 状态

```java
       LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
       StatusPrinter.print(lc);
```

```text
19:20:37,376 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
19:20:37,376 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
19:20:37,376 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.xml]
19:20:37,379 |-INFO in ch.qos.logback.classic.BasicConfigurator@1b8d17c - Setting up default configuration.
```
可以简单的看到，原来是可以加载配置的
- ``logback-test.xml``
- ``logback.groovy``
- ``logback.xml``

不过我们都没有，最后默认配置了。关于配置，后面学习。

重要的是，``log``的加载详情，可以在运行期进行打印。
