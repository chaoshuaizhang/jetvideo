md风格的图标：https://material.io/resources/icons/?icon=thumb_down_off_alt&style=outline

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


style="@style/Widget.MaterialComponents.Chip.Action"
// 上述使用的style，可以在中看到
<style name="Widget.MaterialComponents.Chip.Action" parent="Base.Widget.MaterialComponents.Chip">
    <item name="closeIconVisible">false</item>
</style>

databinding使用的坑：
自定义的view通过binding设置属性时，需要给设置一个对应的set方法，否则你在init方法里设置了typedarray，
但是网络加载完成后，没办法再给对应的自定义view设置属性了，参照IconTextView中的：
```
fun setTextStr(str: String) {
    text.text = str
}

// 在布局中设置的是 resid类型，但是对应的set方法要设置参数为Drawable类型的方法
fun setIcon(resId: Drawable) {
    imageView.setImageDrawable(resId)
}
```

用原生而不用Databinding进行数据并绑定的原因是：
图片和视频区域都是需要计算的，dataBinding的执行默认是延迟一帧的。
当列表上下滑动的时候 ，会明显的看到宽高尺寸不对称的问题

MediatorLiveData:
>这个类和MutableLiveData<T>一样都继承自LiveData<T>. 主要是帮助我们解决这样一种case：我们有
多个LiveData实例，这些LiveData的value发生变更时都需要通知某一个相同的LiveData。

Transformations.map:
容器A监听B内容的变化，变化时将B内容转化为相应的内容并通知A监听器
原理是利用MediatorLiveData的addSource对其他容器内容监听，在Observer中再对MediatorLiveData修改内容为转化了的相应的数据，来通知自己的监听器内容发生变化


Transformations.switchMap:
容器A监听B内容的变化，变化时从B内容获取相应的容器C，添加到A的监听列表里，即现在A同时监听B跟C，而B内容的变化只会(switch)更换A监听列表里的C，C内容的变化才会通知A监听器
例子1：B为衣服品牌，C为衣服品牌对应的衣服价格，B与C一一对应关系，那么效果就是A监听的是B对应的C(衣服品牌对应的价格)，衣服品牌变化为B2就监听变化了的品牌B2对应的价格C2，而不再监听之前的C1
例子2：业务上可能存在多个B对应一个C，比如B1,B2都是对应同一个C1，那么B1变成B2，A依然还是监听着C1

setPageSize：每次从dataSource中获取的page的数量，第一次用InitialLoadSizeHint，接下来用这个PageSize
setInitialLoadSizeHint：初始加载的数量，如果大于接口请求的paggCunt，则会请求多次接口
setPrefetchDistance：当距离当前加载的数据末尾还有几个元素时，去加载下一页，默认是PageSize，也就是第一次加载了PageSize，
也就是说，第一次加载了10条，那么默认PrefetchDistance是10，

Databinding的一些注解：
https://developer.android.google.cn/reference/android/databinding/Bindable.html

* BindingConversion注解，当有多个方法名不一样，但是参数和返回值一样的会怎么办？
>会进行覆盖，下面的方法覆盖上面的方法，也就是只会取一个，取后边的
@BindingConversion
fun convertBool2String(liked: Boolean): String {
    return if (liked) return "icon_cell_liked" else "icon_cell_like"
}
@BindingConversion
fun convertBool2String2(liked: Boolean): String {
    return if (liked) return "icon_cell_liked222" else "icon_cell_like222"
}

* BindingMethod
>有的属性我们的view属性的setter方法跟属性的名称并不相匹配（因为data-bing是通
过setAttr的形式去寻找对应的setter方法的）。比如说“android:tint”这个属性对应
的setter方法名称是 setImageTintList(ColorStateList) ，而不是 setTint()
方法。这时候使用 BindingMethod 注解可以帮助我们重新命名view属性对应的setter方
法名称。
