## 老生常谈——activity生命周期


对于activity的七个声生命周期回调，总是被大家翻来覆去的说，甚至说的都有些厌烦了，这部分知识虽然基础但也很重要，谁都不想在面试的时候只说出个一知半解，下面的分析是对阅读《安卓开发艺术探索》第一章后的整理和思考。

### 一、正常情况下的生命周期分析

先来上一张大家都熟悉的流程图,来复习一遍活动的生命周期

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/73503926.jpg)


然后来简单说说这七个回调方法

+ **onCreate** : activity被创建时调用，一般在这个方法中进行活动的初始化工作，如设置布局文件、加载数据、绑定控件等。

+ **onStart** : 经历该回调后，activity由不可见变为可见，但此时处于后台可见，还不能和用户进行交互。

+ **onResume** : 已经可见的activity从后台来到前台,可以和用户进行交互。


+ **onPause** : 当用户启动了新的activity,原来的activity不再处于前台,也无法和用户进行交互，并且紧接着就会调用`onStop()`方法，但如果用户这时立刻按返回键回到原activity，就会调用`onResume()`方法让活动重新回到前台。而且在官方文档中给出了说明，不允许在`onPause()`方法中执行耗时操作，因为这会影响到新activity的启动，具体原因我们在后面分析。


+ **onStop** : 这个回调代表了activity由可见变为完全不可见，在这里可以进行一些稍微重量级的操作。需要注意的是，处于`onPause()`和`onStop()`回调后的activity优先级很低，当有优先级更高的应用需要内存时，该应用就会被杀死，那么当再次返回原activity的时候，会重新调用activity的`onCreate()`方法。

+ **onDestroy** : 来到了这个回调，说明activity即将被销毁，应该将资源的回收和释放工作在该方法中执行。

+ **onRestart** : 这个回调代表了activity由完全不可见重新变为可见的过程，当activity经历了`onStop()`回调变为完全不可见后，如果用户返回原activity，便会触发该回调，并且紧接着会触发`onStart()`来使活动重新可见。


想必大家已经对这个过程非常熟悉了，下面我们通过一些实际的场景来更加深入的理解一下活动的启动流程。


#### 1、 由活动A启动活动B时，活动A的`onPause()`与活动B的`onResume()`哪一个先执行？

下面创建两个正常的活动`MainActivity`和`FirstActivity`,在`MainActivity`中设置按钮点击进入`FirstActivity`,看看会发生什么:

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/4680139.jpg)

可以看到，是旧的Activity先执行`onPause`,新活动才开始启动。下面点击返回按钮:

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/44951459.jpg)

点击返回后，同样是新Activity先执行`onPause`，旧的活动才开始重新启动，进行`onRestart->onStart->onResume`的流程，也就是说当发生活动切换时，是原活动先执行`onPause`，然后紧接着目标活动开始创建或重新启动。

#### 2、dialog是否会对生命周期产生影响

从定义上来说，如果一个活动不在前台，也并非完全不可见，这个活动就会处在`onPause()`的暂停状态，我们来模拟一下这种情况，在`MainActivity`中设置三个按钮，第一个按钮点击后会弹出一个标准的`AlertDialog`，第二个按钮会弹出一个全屏的`AlertDialog`,第三个按钮点击会出现一个主题为`Theme.AppCompat.Dialog`的activity然后观察生命周期的变化：

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/40345760.jpg)

首先可以看到，无论是正常的dialog还是全屏的dialog，活动依然维持在`onResume()`的状态，说明单纯的dialog并不会引起生命周期的变化。下面来看dialog主题的activity:

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/48233598.jpg)

在启动`DialogActivity`后，原来的活动进入`onPause()`，新活动正常进行`onCreate->onStart->onResume`的流程，而原来的活动因为并没有完全不可见，所以也没有执行`onStop`，事实上除了dialog主题的活动，一些透明主题的活动也能达到同样的效果，接下来我们点击返回按钮:

![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/53627709.jpg)

由于`MainActivity`根本没有进入`onStop`的状态，所以返回时也不会进行`onRestart->onStart`的流程，而是直接`onResume`回到前台。



### 二、异常状态下活动的生命周期

当活动在运行过程中发生了某些异常情况时，上述所讨论的生命周期流程可能会受到影响，这里主要讨论两种异常情况。

#### 1、资源配置改变导致activity重建

最常见的一种情况就是横竖屏的切换导致资源的变化，当程序启动时，会根据不同的配置加载不同的资源，例如横竖屏两个状态对应着两张不同的资源图片。如果在应用使用过程中屏幕突然旋转，那么activity就会因为系统配置发生改变而销毁重建，加载合适的资源。

##### (1) 数据保存

对于活动重新创建，我们如何保证activity中的已有数据不丢失呢，系统为我们提供了`onSaveInstanceState`和`onRestoreInstanceState`来保存和获取数据。

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }


![](http://p0y1qzu73.bkt.clouddn.com/18-4-1/71262516.jpg)

在活动异常销毁之前，系统会调用`onSaveInstanceState`，我们可以在`Bundle`类型的参数中保存想要的信息，之后这个`Bundle`对象会作为参数传递给`onRestoreInstanceState`和`onCreat`方法，这样在重新创建时就可以获取数据了。关于这两个方法，有几点需要注意的地方：

+ `onSaveInstanceState`方法的调用时机是在`onStop`之前，与`onPause`没有固定的时序关系。而`onRestoreInstanceState`方法则是在`onStart`之后调用。

+ 正常情况下的活动销毁并不会调用这两个方法，只有当活动异常销毁并且有机会重新展示的时候才会进行调用，除了资源配置的改变外，activity因内存不足被销毁也是通过这两个方法保存数据。

+ 在`onRestoreInstanceState`和`onCreat`都可以进行数据恢复工作，但是根据官方文档建议采用在`onRestoreInstanceState`中去恢复。

+  在`onRestoreInstanceState`和`onRestoreInstanceState`这两个方法中，系统会默认为我们进行一定的恢复工作，例如`EditText`中的文本信息、`ListView`中的滚动位置等，下面对一些控件观察实际保存效果。

	+ `EditText`:个人在对`EditText`实验的时候，发现转屏后文本信息并没有被保存，经过查询，发现了这样一句话：

		> "Note: In order for the Android system to restore the state of the views in your activity, each view must have a unique ID, supplied by the android:id attribute."
Android系统存储和还原View的状态必须有一个唯一的ID

		果然加上id之后`EditText`中的信息可以被自动保存了。
		
	+ `TextView`:这里指的是通过`setText`方法动态设置文本内容，在这种情况下即使加了id也无法自动保存，这种情况可以通过给`TextView`设置`freezesText`属性才能自动保存，当然这条属性对`EditText`也同样适用。

##### (2) 防止重建

我们已经知道默认情况下，资源配置改变会导致活动的重新创建，但我们可以通过对活动`android:configChanges`属性的设置使活动防止重新被创建，我们来看看这个属性中有哪些内容:

| 属性值     | 含义   |
| :--- | :------ |
| mcc | SIM卡唯一标识IMSI(国际移动用户标识码)中的国家代码，由三位数字组成，中国为：460 这里标识mcc代码发生了改变|
| mnc | SIM卡唯一标识IMSI(国际移动用户标识码)中的运营商代码，有两位数字组成，中国移动TD系统为00，中国联通为01,电信为03,此项标识mnc发生了改变 |
| locale | 设备的本地位置发生了改变，一般指的是切换了系统语言 |
| touchscreen | 触摸屏发生了改变 |
| keyboard | 键盘类型发生了改变，比如用户使用了外接键盘 |
| keyboardHidden | 键盘的可访问性发生了改变，比如用户调出了键盘 |
| navigation | 系统导航方式发生了改变 |
| screenLayout | 屏幕布局发生了改变，很可能是用户激活了另外一个显示设备 |
| fontScale | 系统字体缩放比例发生了改变，比如用户选择了个新的字号 |
| uiMode | 用户界面模式发生了改变，比如开启夜间模式-API8新添加 |
| orientation | 屏幕方向发生改变，比如旋转了手机屏幕 |
| screenSize | 当屏幕尺寸信息发生改变(当编译选项中的minSdkVersion和targeSdkVersion均低于13时不会导致Activity重启)API13新添加 |
| smallestScreenSize | 设备的物理屏幕尺寸发生改变，这个和屏幕方向没关系，比如切换到外部显示设备-API13新添加 |
| layoutDirection | 当布局方向发生改变的时候，正常情况下无法修改布局的layoutDirection的属性-API17新添加 |


如果不希望某些资源配置改变时活动被重建，只需在`manifest`中为相应活动添加属性即可，例如           `configChanges="orientation"`可以防止横竖屏引发的重启，然而事实上单加这条属性并没有什么效果，因为在api13之后，新添加的属性`screenSize`属性也会跟着设备的横竖切换而改变,所以正确的配置应该是`configChanges="orientation|screenSize"`;而在api13之前，正确的配置应该是`configChanges="orientation|keyboardHidden"`


这里还要介绍一个重写方法`onConfigurationChanged`,用来监听资源配置的改变，这个方法只有在设置了`configChanges`并且相应的属性发生了变化时才会被调用，

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
		//监听横竖屏的变化
        Log.i(TAG, "onConfigurationChanged: "+newConfig.orientation);
    }





#### 2、低优先级的activity由于内存不足被杀死

这种情况的数据保存方法和上一种情况相同，在这里简单说一下系统回收进程的优先级:

##### (1) 前台进程：

+ 持有用户正在交互的activity，即生命周期处于`onResume`状态的活动。
+ 该进程有绑定到正在交互的Activity的service或前台service。

##### (2) 可见进程:

这种进程虽然不在前台，但是仍然可见。

+ 该进程持有的Activity执行了`onPause`但未执行`onStop`。例如原活动启动了一个 dialog主题的activity，但此时原活动并非完全不可见。
+ 该进程有service绑定到可见的或前台Activity。

##### (3) 服务进程：
进程中持有一个service，同时不属于上面两种情况。

##### (4) 后台进程：
不属于上面三种情况，但进程持有一个不可见的`activity`,即执行了`onStop`但未执行`onDestroy`的状态。

##### (5) 空进程:
不包含任何活跃的应用组件，作用是加快下次启动这个进程中组件所需要的时间，优先级最低。









#### 参考文章

+ [Android系统回收Activity的优先级](https://blog.csdn.net/lygsust/article/details/52777537)
+ [Android configChanges的属性值和含义](https://blog.csdn.net/qq_33544860/article/details/54863895)








