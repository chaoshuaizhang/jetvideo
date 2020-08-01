#### 改造Navagation，目的就是不想在布局文件中配置fragment，而是通过代码配置。
>目的是自定义生成NavGraph，然后与NavController进行关联。现在的NavGraph是通过在xml不会剧中配置由系统自动生成的。

1. 定义注解
2. 定义注解解析器

使用implementation 'androidx.navigation:navigation-ui-ktx:2.0.0'时，AppBarConfiguration会报一些异常，没太关注，先记一下。