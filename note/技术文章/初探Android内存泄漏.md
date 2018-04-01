# 初探Android内存泄漏

标签 ： Android 


> 作者：fhyPayaso


### **1. Java中对象和对象引用的区别** 

先看一个例子 : 

    Person person;
    person = new Person("name");


上面两行代码中，第一行person属于声明了一个Person类的引用，而第二行的new才是真正创建了一个对象。


### **2.Java中的内存环境**

Java在JVM所虚拟的内存环境中运行，内存可以分为三个部分：栈，堆，方法区

+ **栈(stack)**
    每一个线程都拥有一个栈，在这里只保存基本数据类型和对象的引用(注意和对象的区别)，例如在方法中定义一个局部变量，就会在栈中给这个变量分配内存空间，超过该变量的作用域时，这部分内存就会被回收掉。

+ **堆(heap)**
    被所有进程所共享，只存放数组、对象以及对象的成员变量，不包括基本类型和引用，这部分内存空间由java中的垃圾回收机制自动管理。

+ **方法区(method)**
    又叫静态区，同样被所有进程所共享，存放class和static变量。

举个例子：

    public class Person {
    
        int age = 18;
        Person personA = new Person();
        
        public void method() {
        
            int height = 180;
            Person personB = new Person();
            
        }
    }
    
    Person personC = new Person();


在上面的例子中，height以及引用变量personB都属于局部变量，所以它们的内存应存放在栈中，在method方法之外的作用域，这部分内存就会被回收，而personB所指向的对象的内存存放在堆上。
引用变量personC所指向的对象同理也存放在堆上，当然也包括Person类的成员变量age和personA，但引用变量personC本身则是存放在栈中。

### **3.垃圾回收机制(GC)**

在Java中，内存的分配是由程序来完成的，而内存的回收则是由GC完成的，GC为了能正确的释放对象，必须监控每一个对象的运行状态，包括对象的申请、引用、赋值等。对象通过引用的方式来使用，而当一个对象没有任何引用的时候，称为**不可到达对象**，GC用于回收不可到达对象所占用的内存。

### **4.安卓中的内存泄露(Memory Leak)**

虽然java中有垃圾回收的机制，可以自动管理内存分配，但是仍有可能出现内存泄露的情况。Java作为Android的主要开发语言，当存在内存泄漏时，APP就会浪费大量内存，当内存不够时会导致频繁的内存回收，然而内存回收是十分耗时的操作，这样就会导致APP的卡顿，严重的可能会导致内存溢出，退出程序。

- **内存泄露的原因**
安卓中主要出现内存泄漏的原因本质上就是，一个对象已经不会再被使用，但其内存却没有被系统所回收，而无法被回收的原因一般是由于该对象被一个生命周期更长的对象所引用。

- **如何知道程序中存在内存泄漏**
在开发的过程中，可能已经产生了内存泄露但却没有被注意到，这时候就需要用到LeakCanary，一款由Square公司生产的内存泄露检测库，简单使用方法如下：
    + 在build.gradle中添加：
    
            debugCompile 'com.squareup.leakcanary:leakcanary-android:1.5'
            releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.5'

    + 在Application类添加
    
            public class TestApplication extends Application {
              @Override public void onCreate() {
                super.onCreate();
                LeakCanary.install(this);
              }
            }
            
    这样当程序出现内存泄露的情况时，会在系统通知栏出现提醒，点击后可以查看详细的泄露信息。

    
- **常见的内存泄漏**

    + static关键字所导致的内存泄露
    
        举个例子：
        
            public  class MainActivity extends Activity{  
   
                private static Context context;  
       
                @Override  
                 protected void onCreate(Bundle savedInstanceState){  
                      super.onCreate(savedInstanceState);                   
                      setContentView(R,layout.main);
                      
                      context = this;  
                }  
            }
            
        context为静态变量，生命周期较长，导致当前activity被销毁后无法被系统回收，产生内存泄漏。LeakCanary的详细泄露信息：
        
       ![](https://ws1.sinaimg.cn/large/006yrKRely1fmgcyggl6qj30a60gumxj.jpg)
        
        解决办法：在Activity的onDestroy方法里把静态变量设为null
        
            protected void onDestroy() {  
            
                super.onDestroy();  
                
                if(context != null) {  
                    context = null;  
                }  
            }
            
            
本文先初步探究android中的内存泄露的基本原理，其他具体可能引起内存泄露的情景和解决方法会在后续文章中探讨。
        
参考文章:

[《Android 中内存泄漏的原因和解决方案》](http://m.blog.csdn.net/lib739449500/article/details/78741263)

[《Android常见内存泄露，学会这六招大大优化APP性能》](https://www.douban.com/note/609777758/)
        