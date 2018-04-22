### Runnable与Thread


两种传统使用方法 :

+ 新建子类继承Thread类，重写run方法
+ 新建子类实现Runnable接口，重写run方法。实例化后作为参数传入`Thread()`的构造方法中(也可以使用匿名内部类实现)。

+ `start()`:开启线程
+ `join()` :线程合并，即该线程执行完毕才能继续执行调用join()方法所在的线程。


## 线程锁


### synchronized(同步锁)

synchronized是Java中的关键字，是一种同步锁。关于加锁和解锁的对象：

+ synchronized(this)代码块：作用范围是该对象中所有被synchronized标记的变量、方法或代码块，作用对象是对象本身。

+ synchronized方法 ：同步方法，作用范围是整个方法，作用对象是调用这个方法的对象。

+ synchronized静态方法 ：同步静态方法，作用范围是整个静态方法，作用对象是调用这个类的所有对象。
	
+ synchronized(ClassName.class)代码块：作用范围是静态的方法或者静态变量，作用对象是Class对象。

+ **对象锁**：Java的所有对象都含有1个互斥锁，这个锁由JVM自动获取和释放。线程进入synchronized方法的时候获取该对象的锁，当然如果已经有线程获取了这个对象的锁，那么当前线程会等待；synchronized方法正常返回或者抛异常而终止，JVM会自动释放对象锁。这里也体现了用synchronized来加锁的好处，方法抛异常的时候，锁仍然可以由JVM来自动释放。


+ **类锁**：对象锁是用来控制实例方法之间的同步，类锁是用来控制静态方法（或静态变量互斥体）之间的同步。java类可能会有很多个对象，但是只有1个Class对象，也就是说类的不同实例之间共享该类的Class对象。Class对象其实也仅仅是1个 java对象，只不过有点特殊而已。由于每个java对象都有1个互斥锁，而类的静态方法是需要Class对象。所以所谓的类锁，不过是Class对象的锁而已。获取类的Class对象有好几种，最简单的就是MyClass.class的方式。 类锁和对象锁不是同一个东西，一个是类的Class对象的锁，一个是类的实例的锁。也就是说：一个线程访问静态synchronized的时候，允许另一个线程访问对象的实例synchronized方法。反过来也是成立的，因为他们需要的锁是不同的。


### 死锁

当两个或两个以上进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们将一直阻塞下去。

例如：线程A当前持有互斥所锁lock1，线程B当前持有互斥锁lock2。接下来，当线程A仍然持有lock1时，它试图获取lock2，因为线程B正持有lock2，因此线程A会阻塞等待线程B对lock2的释放。如果此时线程B在持有lock2的时候，也在试图获取lock1，因为线程A正持有lock1，因此线程B会阻塞等待A对lock1的释放。二者都在等待对方所持有锁的释放，而二者却又都没释放自己所持有的锁，这时二者便会一直阻塞下去。这种情形称为死锁。



产生死锁的四个必要条件：

+ 互斥条件：一个资源每次只能被一个进程使用。

+ 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。

+ 不剥夺条件：进程已获得的资源，在未使用完之前，不能强行剥夺。

+ 循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。



		public class DeadClock {
		
		
		    /**
		     * A锁
		     */
		    private static Object sLockA = new Object();
		
		    /**
		     * B锁
		     */
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

避免死锁 : 防止出现线程之间相互持有对方所需要的同步锁

### wait、notify和notifyAll

+ wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。

+ 一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。

+ 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程 


+ `wait`：使持有该对象的线程把该对象的控制权交出去，然后处于等待状态（这句话很重要，也就是说当调用wait的时候会释放锁并处于等待的状态）

+ `notify`：通知某个正在等待这个对象的控制权的线程可以继续运行（这个就是获取锁，使自己的程序开始执行，最后通过notify同样去释放锁，并唤醒正在等待的线程）

+ `notifyAll`:会通知所有等待这个对象控制权的线程继续运行(和上面一样，只不过是唤醒所有等待的线程继续执行)

这个就好了，从上面的解释我们可以看出通过wait和notify可以做线程之间的通信，当A线程处理完毕通知B线程执行，B线程执行完毕以后A线程可以继续执行。


#### 生产者消费者模型

什么是生产者/消费者模型

一种重要的模型，基于等待/通知机制。生产者/消费者模型描述的是有一块缓冲区作为仓库，生产者可将产品放入仓库，消费者可以从仓库中取出产品，生产者/消费者模型关注的是以下几个点：

+ 生产者生产的时候消费者不能消费

+ 消费者消费的时候生产者不能生产

+ 缓冲区空时消费者不能消费

+ 缓冲区满时生产者不能生产










### volatile

### threadPool





[深入理解Java并发之synchronized实现原理](https://blog.csdn.net/javazejian/article/details/72828483)

