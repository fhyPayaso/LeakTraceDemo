


### thread


## 线程加锁

### synchronized(同步锁)

synchronized是Java中的关键字，是一种同步锁。关于加锁和解锁的对象：

+ synchronized代码块 ：同步代码块，作用范围是整个代码块，作用对象是调用这个代码块的对象。
+ synchronized方法 ：同步方法，作用范围是整个方法，作用对象是调用这个方法的对象。
+ synchronized静态方法 ：同步静态方法，作用范围是整个静态方法，作用对象是调用这个类的所有对象。
+ synchronized(this)：作用范围是该对象中所有被synchronized标记的变量、方法或代码块，作用对象是对象本身。
+ synchronized(ClassName.class) ：作用范围是静态的方法或者静态变量，作用对象是Class对象。

synchronized(this)添加的是对象锁，synchronized(ClassName.class)添加的是类锁，它们的区别如下：

+ **对象锁**：Java的所有对象都含有1个互斥锁，这个锁由JVM自动获取和释放。线程进入synchronized方法的时候获取该对象的锁，当然如果已经有线程获取了这个对象的锁，那么当前线 程会等待；synchronized方法正常返回或者抛异常而终止，JVM会自动释放对象锁。这里也体现了用synchronized来加锁的好处，方法抛异常的时候，锁仍然可以由JVM来自动释放。

+ **类锁**：对象锁是用来控制实例方法之间的同步，类锁是用来控制静态方法（或静态变量互斥体）之间的同步。其实类锁只是一个概念上的东西，并不是真实存在的，它只是用来帮助我们理 解锁定实例方法和静态方法的区别的。我们都知道，java类可能会有很多个对象，但是只有1个Class对象，也就是说类的不同实例之间共享该类的Class对象。Class对象其实也仅仅是1个 java对象，只不过有点特殊而已。由于每个java对象都有1个互斥锁，而类的静态方法是需要Class对象。所以所谓的类锁，不过是Class对象的锁而已。获取类的Class对象有好几种，最简 单的就是MyClass.class的方式。 类锁和对象锁不是同一个东西，一个是类的Class对象的锁，一个是类的实例的锁。也就是说：一个线程访问静态synchronized的时候，允许另一个线程访 问对象的实例synchronized方法。反过来也是成立的，因为他们需要的锁是不同的。


### 互斥锁

### 重入锁(ReentrantLock)


### volatile

### threadPool