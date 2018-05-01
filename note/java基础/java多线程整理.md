
### 1、进程和线程

+ 并发:在同一个时间段内，同一个cpu上执行多个任务，但在某一时刻cpu只处理一个任务。
+ 并行:同一时间段内，多个cpu执行多个任务，在某一时刻有多个任务同时执行，并且相互之间没有干扰。

这里再详细说一下并发时任务是如何调度的，引用一下博客：


> 大部分操作系统(如Windows、Linux)的任务调度是采用时间片轮转的抢占式调度方式，也就是说一个任务执行一小段时间后强制暂停去执行下一个任务，每个任务轮流执行。任务执行的一小段时间叫做时间片，任务正在执行时的状态叫运行状态，任务执行一段时间后强制暂停去执行下一个任务，被暂停的任务就处于就绪状态等待下一个属于它的时间片的到来。这样每个任务都能得到执行，由于CPU的执行效率非常高，时间片非常短，在各个任务之间快速地切换，给人的感觉就是多个任务在“同时进行”，这也就是我们所说的并发(别觉得并发有多高深，它的实现很复杂，但它的概念很简单，就是一句话：多个任务同时执行)。


![](http://p0y1qzu73.bkt.clouddn.com/18-3-31/32283692.jpg)

在多个任务中切换也是要付出代价的,一个任务消耗完了时间片，需要根据调度策略来选取下一个任务来执行，而这一过程也需要消耗时间，叫做**切换耗时**,而这也将影响时间片大小的选取。




+ **进程**：进程代表一个运行中的程序，是资源分配与调度的基本单位。进程有三大特性：
	+ 独立性：独立的资源，私有的地址空间，进程间互不影响。
	+ 动态性：进程具有生命周期。
	+ 并发性：多进程可以在单核CPU上并发运行。

+ **线程**：线程代表进程中的一个顺序执行流，多线程就是一个进程中的多个顺序执行流。线程也被称为轻量级的进程，是系统运行的基本单位。

+ **多线程的优势**（进程线程区别）：
	+ 进程之间不能共享内存，线程之间共享内存更容易，多线程可协作完成进程工作；
	+ 创建进程进行资源分配的代价较创建线程要大得多，所以多线程在高并发环境中效率更高。



### 2、使用多线程

三种使用方法 :

+ **Thread** : 新建子类继承Thread类，重写run方法

		public class MyThread extends Thread{
		
		    @Override
		    public void run() {
		
		    }
		}
		
	在使用的时候直接创建实例并开启即可
	
		Thread myThread = new MyThread();
		myThread.start();


+ **Runnable** : 新建子类实现Runnable接口，重写run方法。实例化后作为参数传入`Thread()`的构造方法中(也可以使用匿名内部类实现)。

		//1、继承接口实现
		public class MyThread implements Runnable{
		    
		    @Override
		    public void run() {
		        
		    }
		}
		
		//使用直接传入Thread
		new Thread(new MyThread()).start();


		//2、匿名内部类实现
        new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        }).start();

+ **Callable**:实现`Callable`接口并指定返回的参数


		public class MyCallable implements Callable<String> {
		    
		    @Override
		    public String call() throws Exception {
		        Thread.sleep(1000);
		        return "hello";
		    }
		}
		
		
	使用的时候需要通过FutureTask
	
		Callable callable = new MyCallable();
		FutureTask futureTask = new FutureTask<>(callable);
		new Thread(futureTask).start();
		
		
	可以看到`Callable`接口和`Runnable`接口中实现方法的区别，
	+ call()方法中可以指定一个返回值，可以通过FutureTask对象的get()来获取call()中的返回值，此方法会阻塞线程知道获得“将来”的结果，不调用此方法，主线程不会阻塞。
	+ call()可以抛出受检查的异常


		
### 3、常用函数


+ `sleep()` : 使当前线程（即调用该方法的线程）暂停执行一段时间，让其他线程有机会继续执行，但它并不释放对象锁。也就是说如果有synchronized同步快，其他线程仍然不能访问共享数据。

+ `yield()` : 该方法与sleep()类似，只是不能由用户指定暂停多长时间，并且yield（）方法只能让同优先级的线程有执行的机会。

+ `isAlive()`: 当前线程没有执行完run方法，即没有进入死亡状态的时候返回ture，
否则返回fasle。

+ `currentThread()` : 返回当前正在使用CPU资源的线程。

+ `start()`: 线程调用该方法将启动线程，使之从新建状态进入就绪队列排队，一旦轮到它来享用CPU资源时，就可以脱离创建它的线程独立开始自己的生命周期了。注意一个线程不能重复调用`start()`。

+ `run()`: Thread类的run()方法与Runnable接口中的run()方法的功能和作用相同，都用来定义线程对象被调度之后所执行的操作

	> 为什么不能用run开启线程:run方法应该由系统自动调用，如果在主线程中调用就变成了同步方法。

+ `join()`:线程合并，即该线程执行完毕才能继续执行调用join()方法所在的线程。

+ `setPriority()`: 设置一个线程的优先级,范围是1~10。


### 4、线程生命周期

首先看一下下面这张较为经典的图

![](https://img-blog.csdn.net/20170328155800229?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzE4ODE0Njk=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)


+ **新建状态（New）**：当程序使用new关键字创建了一个线程之后，该线程就处于新建状态，此时它和其他Java对象一样，仅仅由Java虚拟机为其分配了内存，并初始化了其成员变量值。此时的线程对象没有表现出任何线程的动态特征，程序也不会执行线程的线程执行体。


+ **就绪状态（Runnable）**：当线程对象调用了start()方法之后，该线程处于就绪状态，Java虚拟机会为其创建方法调用栈和程序计数器，处于这个状态的线程并没有开始运行，它只是表示该线程可以运行了。至于该线程何时开始运行，取决于JVM里线程调度器的调度。


+ **运行状态（Running）**: 如果就绪状态的线程获取 CPU 资源，就可以执行 run()，此时线程便处于运行状态。处于运行状态的线程最为复杂，它可以变为阻塞状态、就绪状态和死亡状态。
+ **阻塞状态（Blocked）**:阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种


	+ **等待阻塞**：运行的线程执行wait()方法，JVM会把该线程放入等待池中。
	
	
	+ **同步阻塞**：运行的线程在获取对象的`synchronized`同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。

	
	+ **其他阻塞**：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。


+ **死亡状态（Dead）** : 线程会以以下三种方式之一结束，结束后就处于死亡状态
	+ run()方法执行完成，线程正常结束。
	+ 线程抛出一个未捕获的Exception或Error。
	+ 直接调用该线程的stop()方法来结束该线程——该方法容易导致死锁，通常不推荐使用。



### 5、线程分类

+ **用户线程(User) :** 非守护线程包括常规的用户线程或诸如用于处理GUI事件的事件调度线程，Java虚拟机在它所有非守护线程已经离开后自动离开。

+ **守护线程(Daemon) :** 守护线程则是用来服务用户线程的，如果没有其他用户线程在运行，那么就没有可服务对象，也就没有理由继续下去。举例来说，JVM的垃圾回收**(GC线程)**、内存管理等线程都是守护线程。

	>操作系统里面是没有所谓的守护线程的概念，只有守护进程一说，但是Java语言机制是构建在JVM的基础之上的，意思是Java平台把操作系统的底层给屏蔽起来，所以它可以在它自己的虚拟的平台里面构造出对 自己有利的机制，而语言或者说平台的设计者多多少少是受到Unix思想的影响，而守护线程机制又是对JVM这样的平台凑合，于是守护线程应运而生
	
	
##### 两者区别

两者的区别之处在于虚拟机的离开，当JVM中所有的线程都是守护线程的时候，JVM就可以退出了（如果User Thread全部撤离，那么Daemon Thread也就没啥线程好服务的了，所以虚拟机也就退出了）；如果还有一个或以上的非守护线程则不会退出。（以上是针对正常退出，调用System.exit则必定会退出）。


##### 开启一个守护线程

守护线程与普通线程写法上基本没什么区别，调用线程对象的方法`setDaemon(true)`，则可以将其设置为守护线程。


+ `thread.setDaemon(true)`必须在`thread.start()`之前设置,你不能把正在运行的常规线程设置为守护线程,否则会跑出一个`IllegalThreadStateException`异常，如果线程是守护线程，则`isDaemon`方法返回true。

	> 这点与守护进程有着明显的区别，守护进程是创建后，让进程摆脱原会话的控制+让进程摆脱原进程组的控制+让进程摆脱原控制终端的控制；所以说寄托于虚拟机的语言机制跟系统级语言有着本质上面的区别


+ 在Daemon线程中产生的新线程也是Daemon的。	
	> 这一点又是有着本质的区别了：守护进程fork()出来的子进程不再是守护进程，尽管它把父进程的进程相关信息复制过去了，但是子进程的进程的父进程不是init进程，所谓的守护进程本质上说就是“父进程挂掉，init收养，然后文件0,1,2都是/dev/null，当前目录到/”
	

+ 不是所有的应用都可以分配给Daemon线程来进行服务，比如读写操作或者计算逻辑。因为在Daemon Thread还没来的及进行操作时，虚拟机可能已经退出了。 


例子:

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new MyThread());
        thread.setDaemon(true);
        thread.start();
    }


    static class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test");
        }
    }
    
    
运行后会发现并没有输出任何内容，证明用户线程全部离开后，无论守护线程处于什么状态都会随之退出。


### 6、线程同步

线程不安全的例子:

	public class ThreadOne implements Runnable {
	
	    public static int count = 0;
	
	    public void increase() {
	        count++;
	    }
	
	    @Override
	    public void run() {
	        for (int i = 0; i < 100000; i++) {
	            increase();
	        }
	    }
	
	    public static void main(String[] args) throws InterruptedException {
	        Thread t1 = new Thread(new ThreadOne());
	        Thread t2 = new Thread(new ThreadOne());
	        t1.start();
	        t2.start();
	        t1.join();
	        t2.join();
	        System.out.println(count);//输出结果为146066
	    }
	}

count++不是原子操作，该操作是先读取值，然后写回一个新值，相当于原来的值加上1。当线程1刚进行完第一步，线程2同样进行了第一步操作，这时获取到的count仍然是原来未增加的数值，当两者都完成第二步写入新值的时候，写入的其实是一样的值,这样就造成了数值的丢失，这时候就需要引入锁的机制，将多个线程能访问的方法变成同步方法。


#### (1)synchronized

synchronized是Java中的关键字，是一种同步锁。

+ synchronized实例方法 ：同步方法，作用于当前实例，即进入同步代码会获得当前实例的锁(对象锁)。

+ synchronized静态方法 ：同步静态方法，作用于当前类，即进入同步代码会获得当前类对象的锁(类锁)。

+ synchronized()代码块：可以指定加锁对象，既可以是实例对象也可以是类对象。


+ **对象锁**：Java的所有对象都含有1个互斥锁，这个锁由JVM自动获取和释放。线程进入synchronized方法的时候获取该对象的锁，当然如果已经有线程获取了这个对象的锁，那么当前线程会等待；synchronized方法正常返回或者抛异常而终止，JVM会自动释放对象锁。这里也体现了用synchronized来加锁的好处，方法抛异常的时候，锁仍然可以由JVM来自动释放。

+ **类锁**：对象锁是用来控制实例方法之间的同步，类锁是用来控制静态方法（或静态变量互斥体）之间的同步。java类可能会有很多个对象，但是只有1个Class对象，也就是说类的不同实例之间共享该类的Class对象。Class对象其实也仅仅是1个 java对象，只不过有点特殊而已。由于每个java对象都有1个互斥锁，而类的静态方法是需要Class对象。所以所谓的类锁，不过是Class对象的锁而已。获取类的Class对象有好几种，最简单的就是MyClass.class的方式。 

> 一个是类的Class对象的锁，一个是类的实例的锁。也就是说：一个线程访问静态synchronized的时候，允许另一个线程访问对象的实例synchronized方法。反过来也是成立的，因为他们需要的锁是不同的。



#### (2)死锁

当两个或两个以上进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们将一直阻塞下去。

例如：线程A当前持有互斥所锁lock1，线程B当前持有互斥锁lock2。接下来，当线程A仍然持有lock1时，它试图获取lock2，因为线程B正持有lock2，因此线程A会阻塞等待线程B对lock2的释放。如果此时线程B在持有lock2的时候，也在试图获取lock1，因为线程A正持有lock1，因此线程B会阻塞等待A对lock1的释放。二者都在等待对方所持有锁的释放，而二者却又都没释放自己所持有的锁，这时二者便会一直阻塞下去。这种情形称为死锁。



**产生死锁的四个必要条件**：

+ 互斥条件：一个资源每次只能被一个进程使用。

+ 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。

+ 不剥夺条件：进程已获得的资源，在未使用完之前，不能强行剥夺。

+ 循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。

写一个死锁的例子:

	public class DeadClock {
	
	    private static Object sLockA = new Object();
	
	    private static Object sLockB = new Object();
	
	    public static void main(String[] args) throws InterruptedException {
	        new DeadClock().deadLock();
	    }
	
	    @SuppressWarnings("all")
	    public void deadLock() {
	
	        Thread t1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                synchronized (sLockA) {
	                    System.out.println("线程1获取A锁");
	                    try {
	                        Thread.sleep(2000);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	
	                    System.out.println("线程1等待B锁释放");
	
	                    synchronized (sLockB) {
	                        System.out.println("thread1...");
	                    }
	                }
	            }
	        });
	
	        Thread t2 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                synchronized (sLockB) {
	                    System.out.println("线程2获取B锁，等待A锁释放");
	                    synchronized (sLockA) {
	                        System.out.println("thread2...");
	                    }
	                }
	            }
	        });
	
	        t1.start();
	        t2.start();
	    }
	}


运行后程序出现死锁

![](http://p0y1qzu73.bkt.clouddn.com/18-4-21/4196162.jpg)


避免出现死锁的方式:

+ 避免一个线程同时获取多个锁

+ 避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源

+ 尝试使用定时锁，使用lock.tryLock来代替使用内置锁。


#### (3)wait、notify和notifyAll

+ wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。

+ 一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。

+ 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程 


+ `wait`：使持有该对象的线程把该对象的控制权交出去，然后处于等待状态（也就是说当调用wait的时候会释放锁并处于等待的状态）

+ `notify`：通知某个正在等待这个对象的控制权的线程可以继续运行（这个就是获取锁，使自己的程序开始执行，最后通过notify同样去释放锁，并唤醒正在等待的线程）

+ `notifyAll`:会通知所有等待这个对象控制权的线程继续运行(和上面一样，只不过是唤醒所有等待的线程继续执行)

这个就好了，从上面的解释我们可以看出通过wait和notify可以做线程之间的通信，当A线程处理完毕通知B线程执行，B线程执行完毕以后A线程可以继续执行。


**实例 : 生产者消费者模型**

基于等待/通知机制。生产者/消费者模型描述的是有一块缓冲区作为仓库，生产者可将产品放入仓库，消费者可以从仓库中取出产品，生产者/消费者模型关注的是以下几个点：

+ 一个生产者或消费者工作的时候其他人不能工作。

+ 缓冲区空时消费者不能消费

+ 缓冲区满时生产者不能生产


![](http://p0y1qzu73.bkt.clouddn.com/18-4-21/5146865.jpg)

![](http://p0y1qzu73.bkt.clouddn.com/18-4-21/14097141.jpg)


#### (4)volatile

volatile是java中一种轻量级的同步机制，它的作用是保证线程之间共享变量的可见性，当线程A修改了一个volatile修饰的共享变量之后，线程B会读取该变量时，会获得一个修改后的正确值。

**(1)如何保证内存可见性**: 在java虚拟机的内存模型中，有主内存和工作内存的概念，每个线程对应一个工作内存，并共享主内存的数据。

+ 对于普通变量：读操作会优先读取工作内存的数据，如果工作内存中不存在，则从主内存中拷贝一份数据到工作内存中；写操作只会修改工作内存的副本数据，这种情况下，其它线程就无法读取变量的最新值。

+ 对于volatile变量，读操作时JMM会把工作内存中对应的值设为无效，要求线程从主内存中读取数据；写操作时JMM会把工作内存中对应的数据刷新到主内存中，这种情况下，其它线程就可以读取变量的最新值。


**(2)不能保证原子性**: volatile关键字虽然能保证变量在线程间的可见性，但是并不能保证原子性。

原子性指的是一个操作具有在执行过程中不会被任何因素打断的性质。在Java中，对基本数据类型的变量的读取和赋值操作是原子性操作，即这些操作是不可被中断的，要么执行，要么不执行。例如 `i=0` 这个操作是不可分割的，所以是原子操作，但是i++则不是，他的操作分为三步：

+ 从主内存中取出i的值
+ i自增1
+ 将i存到主内存

原子性的线程不安全在介绍synchronized时已经说过，下面来看看volatile为什么不能保证原子操作的线程安全:




	public class Test {
	
	    public volatile int inc = 0;
	
	    public void increase() {
	        inc++;
	    }
	
	    public static void main(String[] args) {
	        final Test test = new Test();
	        for (int i = 0; i < 10; i++) {
	
	            new Thread(new Runnable() {
	                @Override
	                public void run() {
	                    for (int j = 0; j < 1000; j++) {
	                        test.increase();
	                    }
	                }
	            }).start();
	
	        }
	        while (Thread.activeCount() > 1) {
	            Thread.yield();
	        }
	
	        System.out.println(test.inc);//输出值为9933
	    }
	}


虽然volatile可以让每个线程读取的变量都是最新的，但是可以看到，依然不能保证线程安全,下面进行一下模拟:

+ 目前inc的值为10，线程1从主工作区中获取最新值，但是还没有来得及进行自增操作就线程1就进入阻塞状态
+ 此时线程2开始进入运行状态，从主工作区中获取inc的值依然为10，自增后写入主工作区，之后进入阻塞状态。
+ 线程1重新进入运行状态，完成自增并写入工作区内存。

这时候inc的值为11，说明volatile无法保证原子操作的线程安全。



**(3)保证有序性**：


**什么是指令重排** : 指令重排序是JVM为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，尽可能地提高并行度。编译器、处理器也遵循这样一个目标。注意是单线程。多线程的情况下指令重排序就会给程序员带来问题。

不同的指令间可能存在数据依赖。比如下面计算圆的面积的语句：

	double r = 2.3d;//(1)
	
	double pi =3.1415926; //(2)
	
	double area = pi* r * r; //(3)

area的计算依赖于r与pi两个变量的赋值指令。而r与pi无依赖关系，即计算顺序(1)(2)(3)与(2)(1)(3) 对于r、pi、area变量的结果并无区别。编译器、Runtime在优化时可以根据情况重排序（1）与（2），而丝毫不影响程序的结果。但要注意的是指令重排必须要在不影响最终结果的前提下进行。



volatile的另一个特点便是能够禁止指令重排，从而在一定程度上保证程序的有序性:

+ 当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作一定还未进行
+ 在进行指令优化时，不能将在对volatile变量的读操作或者写操作的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。


	    public static int a,b;
	    public static volatile boolean c;
	
	    public static void main(String[] args) {
	
	        a = 1; //语句1
	        b = 2; //语句2
	        c = true; //语句3
	        a = 3; //语句4
	        b = 4; //语句5
	    }

在指令重排的过程中，语句3不会在语句1或者语句2的前面执行，也不会在语句4或者语句5的后面执行，但是1和2之间以及4和5之间的顺序依然是不能保证的。同时当语句1、2执行完之后的结果对语句3、4、5都是可见的。


### 7、线程局部变量ThreadLocal

ThreadLocal是一个关于创建线程局部变量的类。ThreadLocal会为每一个线程提供一个独立的变量副本，从而隔离了多个线程对数据的访问冲突。因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。

具体的实现原理我们来看一看这两个类的get()和set()方法源码

	public void set(T value) {  
	       Thread t = Thread.currentThread();  
	       ThreadLocalMap map = getMap(t);  
	       if (map != null)  
	           map.set(this, value);  
	       else  
	           createMap(t, value);  
	}  
	
	
set方法首先通过`Thread.currentThread()`方法来获取当前线程，然后取出当前线程对象的成员变量`ThreadLocalMap`,如果存在就设置键值对，其中key为ThreadLocal本身，value为想要设置的值，如果不存在就创建一个


	public T get() {  
        Thread t = Thread.currentThread();  
        ThreadLocalMap map = getMap(t);  
        if (map != null) {  
            ThreadLocalMap.Entry e = map.getEntry(this);  
            if (e != null) {  
                T result = (T)e.value;  
                return result;  
            }  
        }  
        return setInitialValue();  
    }
    
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }  

然后来看看get方法，从代码中可以看出ThreadLocalMap中存放的就是Entry，Entry的KEY就是ThreadLocal，VALUE就是值。而map是从Thread t中获得的。也就是说map虽然是在threadlocal的内部类，但是却是保存在Thread中的。


下面来看一下使用场景:

![](http://p0y1qzu73.bkt.clouddn.com/18-4-22/40318473.jpg)


![](http://p0y1qzu73.bkt.clouddn.com/18-4-22/82361554.jpg)


可以看到在不同的线程中创建了独立的变量副本。



### 8、线程池

在开发过程中，不推荐显示的创建新线程，而是推荐使用线程池的方法进行线程的处理，这样可以避免频繁的创建销毁线程带来的资源的损耗，
在java中线程池的实现类为`ThreadPoolExecutor` ，其构造方法的参数为:


	ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(corePoolSize,// 核心线程数  
	                        maximumPoolSize, // 最大线程数  
	                        keepAliveTime, // 闲置线程存活时间  
	                        TimeUnit.MILLISECONDS,// 时间单位  
	                        new LinkedBlockingDeque<Runnable>(),// 线程队列  
	                        Executors.defaultThreadFactory(),// 线程工厂  
	                        new AbortPolicy()// 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略  
	                );  


可以直接创建java中四种配置好参数的线程池:

+ **FixedThreadPool** : 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，新添加的会被加入到任务队列中。如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。


+ **CachedThreadPool**: 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

	+ 工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger. MAX_VALUE), 这样可灵活的往线程池中添加线程。
	+ 如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。
	+ 在使用CachedThreadPool时，一定要注意控制任务的数量，否则，由于大量线程同时运行，很有会造成系统瘫痪。

+ **ScheduledThreadPool**:一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

+ **SingleThreadExecutor**: 创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。




学习博客:

[Java 之 volatile 详解](https://juejin.im/post/5ad1adea6fb9a028c67624e9)

[深入理解Java并发之synchronized实现原理](https://blog.csdn.net/javazejian/article/details/72828483)

[java volatile关键字解惑](https://www.jianshu.com/p/195ae7c77afe)

[ThreadLocal用法和实现原理](https://www.cnblogs.com/WuXuanKun/p/5827060.html)

[编程思想之多线程与多进程](https://blog.csdn.net/luoweifu/article/details/46595285)

[深入理解并发/并行，阻塞/非阻塞，同步/异步](https://www.jianshu.com/p/2116fff869b6)




