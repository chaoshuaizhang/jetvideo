[接口文档](http://123.56.232.18:8080/serverdemo/swagger-ui.html)

#### 改造Navagation，目的就是不想在布局文件中配置fragment，而是通过代码配置。
>目的是自定义生成NavGraph，然后与NavController进行关联。现在的NavGraph是通过在xml不会剧中配置由系统自动生成的。

1. 定义注解
2. 定义注解解析器

使用implementation 'androidx.navigation:navigation-ui-ktx:2.0.0'时，AppBarConfiguration会报一些异常，没太关注，先记一下。

关于泛型擦除：
因为我们需要解析，所以对应的类型不能被擦除

room的学习：
1. 定义子类（需要是抽象类或者是接口）继承自RoomDatabase
2. 创建数据库（可以设置一些基本操作：日志、线程池） @Database
3. 数据库升级，备份--恢复
4. 定义对应的表
5. 定义对表的操作类 @Dao

注解学习
@Embedded 嵌套注解
```
public class Coordinates {
  double latitude;
  double longitude;
}
public class Address {
  String street;
  @Embedded Coordinates coordinates;
}
// 此时，Coordinates的属性名就会嵌套在address这个表里，作为列
```

Room疑问
1. update时为什么可能会有冲突？
2. 报错：Execution failed for task ':libnetwork:kaptDebugKotlin'.
   > A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptExecution
      > java.lang.reflect.InvocationTargetException (no error message)
原因如下：

@delete注解默认是传入一个具体的一行“对象”的，如果只传入一个字段，则需要用entity标注一下具体是哪个表
因为你只是在CacheDao这个接口类里，但是并没有任何信息表明：你操作的就是 Cache 这个表.
room坑爹，一些错误根本不给提示
    @Delete(entity = Cache::class)
    fun delete(key: String)

    @Delete
    fun delete(cache: Cache)



BottomNavigator的问题：
1. 图标着色问题
2. 文字、图标点击变大问题

使用MaterialButton时，需要更改一下系统的主题
iconGravity设置为textStart的原因是：保持文字和图标是相邻的

Space布局的使用：是一个没有背景的view，只是充当占位布局使用

当组件的宽高不确定，需要在运行时动态计算时，就不建议使用databinding进行绑定

-----------------------------------------------------------------

## 自定义BottomAppBar风格的BottomNavigationView
自定义obtainStyledAttributes时，获取数值类型的方法：
```
// 获取到的是小数，是像素 eg:26.25
attrs.getDimension(R.styleable.BottomWithFloatingNavView_fabCradleMargin, 0f)
// 获取到的是整数，也是像素 eg:26
attrs.getDimensionPixelOffset(R.styleable.BottomWithFloatingNavView_fabCradleMargin, 0)
```
>所以，getDimension获取到的也是像素大小，不是dp

## 封装

