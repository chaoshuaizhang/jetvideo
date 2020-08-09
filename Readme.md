#### 改造Navagation，目的就是不想在布局文件中配置fragment，而是通过代码配置。
>目的是自定义生成NavGraph，然后与NavController进行关联。现在的NavGraph是通过在xml不会剧中配置由系统自动生成的。

1. 定义注解
2. 定义注解解析器

使用implementation 'androidx.navigation:navigation-ui-ktx:2.0.0'时，AppBarConfiguration会报一些异常，没太关注，先记一下。

关于泛型擦除：
因为我们需要解析，所以对应的类型不能被擦除

room的学习：
1. 定义子类继承自RoomDatabase
2. 创建数据库（可以设置一些基本操作：日志、线程池） @Database
3. 数据库升级，备份--恢复
4. 定义对应的表
5. 定义对表的操作类 @Dao

BottomNavigator的问题：
1. 图标着色问题
2. 文字、图标点击变大问题

使用MaterialButton时，需要更改一下系统的主题