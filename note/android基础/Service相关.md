## 从Service到IPC

Service属于四大组件之一，是一种不需要用户界面的计算类型组件，可以在后台执行长时间操作，例如网络请求、播放音乐、文件IO操作等。但实际上service并不是一个进程，除非注册时特殊指定，否则它和其他组件依然处于同一个进程，同时service也不是一个线程，其内部逻辑依然运行在主线程，所以当需要进行耗时操作时依然需要创建子线程。service一般有两种状态:**启动状态**和**绑定状态**。


### Service生命周期

#### 1、启动状态

这种状态是通过startService方式来启动一个service，一旦通过这种方式启动，service就会脱离启动它的组件的生命周期，在后台一直运行，所以在service执行完毕时要在其他组件调用stopService或者在service自身中调用stopSelf方法来停止service。

以这种方式启动的service会经历三个生命周期:

+ **onCreate** : service第一次创建的时候，会调用这个方法，代表服务创建完成，可以在这里进行一些初始化的操作，该方法仅在初次启动的时候调用一次。

+ **onStartCommand** : 代表服务开始启动，如果服务已经启动，再调用startService不会重新创建，而是直接调用onStartCommand方法。启动后服务会在后台一直运行，即使开启它的组件已经被销毁，直到调用了stopService或者stopSelf方法服务才会终止。也因为这种模式下的service不依赖其他组件，所以也不好进行组件间的通讯

+ **onDestroy** : 代表服务即将被销毁，在可以进行一些资源的释放操作。


#### 2、绑定状态

通过bindService方法可以将一个服务绑定到一个或多个组件上，其运行状态与其绑定组件的生命周期有关，当一个service绑定的所有组件都被销毁时，该服务也会相应停止。先来看看绑定状态下service的使用方法:


	public class TestService extends Service {
	
	    @Nullable
	    @Override
	    public IBinder onBind(Intent intent) {
	        return new MyBinder();
	    }
	
	    
	    public class MyBinder extends Binder {
	
	        public String getString() {
	            return "连接成功";
	        }
	    }
	}

首先继承service并重写内部的onBind方法，然后定义一个继承自Binder的内部类，里面定义好通信时所需要的方法，然后再onBind中新建一个Binder实例并返回。

	public class ServiceActivity extends AppCompatActivity {
	
	    private TestService.MyBinder mBinder;
	    
	    private ServiceConnection mConnection = new ServiceConnection() {
	        
	        @Override
	        public void onServiceConnected(ComponentName name, IBinder service) {
	        	// 获取binder对象
	            mBinder = (TestService.MyBinder) service;
	            String str = mBinder.getString();
	            ToastUtil.showToast(str);
	        }
	        
	        @Override
	        public void onServiceDisconnected(ComponentName name) {
	        	// 在解除绑定时将binder的引用置空
	            mBinder = null;
	        }
	    };
	    
	    @Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_service);
	        Intent intent = new Intent(ServiceActivity.this, TestService.class);
	        // BIND_AUTO_CREATE表示绑定后自动创建service
	        findViewById(R.id.btn_start).setOnClickListener(v -> bindService(intent, mConnection, Context.BIND_AUTO_CREATE));
	        findViewById(R.id.btn_end).setOnClickListener(v -> unbindService(mConnection));
	    }
	}

在activity中创建一个ServiceConnection类型的匿名内部类，里面的两个回调方法分别对应service绑定成功和取消绑定时的状态，在成功绑定的回调中有一个IBinder类型的参数，该参数就对应着service中onbind方法中返回的Binder实例，获取该实例后就可以在任意地方调用之前定义好的方法来进行组件之间的通信了。


通过这种方式开启的service声明周期与之前有所不同，会经历 : onCreate -> onBind -> unBind -> onDestroy四个生命周期，我们具体来看看中间两个

+ **onBind** : 当其他组件通过bindService方式启动服务的时候会回调该方法，onBind会返回一个IBinder类型的实例用于和其他组件进行交互，如果不需要绑定其他组件，默认返回null。


+ **unBind** : 当service绑定的组件全部与其解绑后调用。


#### 3、两种情况混合

如果我们既想让service能够和activity进行通信，又想让activity被销毁后service不停止，我们便可以采用两种情况的混合，即先startService，再bindService


##### (1)混合启动

+ 如果先startService，再bindService其

##### (2)混合退出

##### (3)onRebind


在这种情况下需要注意一个**onRebind**方法，如果service对应的activity退出后，会调用unBind方法，service不会被销毁，当重新进入该活动再次进行绑定操作时，onBind方法并不会调用，而会调用onRebind方法，但是有一个前提: **service中unBind方法的返回值不能是默认值，需要手动改成true**，这样一来只要service不被杀死，onBind方法只会执行一次，之后都会回调onRebind和onUnbind方法。


#### IntentService

IntentService是Service的子类，它默认为我们开启了一个工作线程，当任务执行完毕之后会自动停止。并且使用方式也较为简单，只需要重写`onHandleIntent(@Nullable Intent intent)`方法并根据传递进来的intent进行相应操作即可。由于IntentService属于服务，所以它的优先级比单纯的线程要高很多，所以可以在这里进行一些优先级较高的耗时操作。那么IntentService具体是如何实现的呢，让我们来看看源码:

	public abstract class IntentService extends Service {
	    private volatile Looper mServiceLooper;
	    private volatile ServiceHandler mServiceHandler;
	    private String mName;
	    private boolean mRedelivery;
	
	    private final class ServiceHandler extends Handler {
	        public ServiceHandler(Looper looper) {
	            super(looper);
	        }
	
	        @Override
	        public void handleMessage(Message msg) {
	            //在工作线程执行onHandleIntent中的内容
	            onHandleIntent((Intent)msg.obj);
	            // 执行完成后自行停止
	            stopSelf(msg.arg1);
	        }
	    }
	    
	    public IntentService(String name) {
	        super();
	        mName = name;
	    }
	    
	
	    @Override
	    public void onCreate() {
	
	        super.onCreate();
	        // 创建一个HandlerThread类型的工作线程，在HandlerThread中默认为我们完成了looper的初始化和开启循环的工作
	        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
	        thread.start();
	        // 用工作线程中的Looper去实例化ServiceHandler对象，这样handleMessage中的内容全都在工作线程中执行
	        mServiceLooper = thread.getLooper();
	        mServiceHandler = new ServiceHandler(mServiceLooper);
	    }
	
	    @Override
	    public void onStart(@Nullable Intent intent, int startId) {
	        Message msg = mServiceHandler.obtainMessage();
	        msg.arg1 = startId;
	        msg.obj = intent;
	        // 把传进来的intent对象交给handler去处理
	        mServiceHandler.sendMessage(msg);
	    }
	
	    @Override
	    public void onDestroy() {
	        //service销毁时停止线程，即让线程中的looper停止循环
	        //HandlerThread的quit方法内部其实也是停止looper的循环
	        mServiceLooper.quit();
	    }
	    
	    @Override
	    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
	        // 在onStartCommand中直接调用onStart方法
	        onStart(intent, startId);
	        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
	    }
	
	    /**
	     * 最后就是子类需要重写的方法，其调用位置在handleMessage
	     * @param intent
	     */
	    @WorkerThread
	    protected abstract void onHandleIntent(@Nullable Intent intent);
	}


通过源码我们可以看到，IntentService其实就相当于在Service中通过Handler把所要执行的任务交给HandlerThread去执行，然后当任务执行完毕之后调用stopSelf将Service自行进行退出，同时结束线程。



### Service保活

如果我们希望service能够一直在后台运行，不希望被用户或系统杀死，就需要采取一些特殊的方法，下面将从几个角度来介绍一下常用的service保活方法,当然有些方法只是理论上可行，需要根据具体情况来使用。

#### 1、修改onStartCommand方法的返回值

在`onStartCommand`方法中，可以选择返回系统提供的几个返回值 :

+ **START_STICKY** : 该返回值表示当service被杀掉之后，系统会重新尝试创建该service，并执行`onStartCommand`回调方法，但是该回调方法中的Intent参数为null。
+ **START\_NOT\_STICKY** : 该返回值表示在默认情况下，service被销毁后不会主动重新创建，只有当接收到新的intent对象时，该服务才会重新创建，这种方式可以避免在不需要的时候运行服务。
+ **START\_REDELIVER\_INTENT** : 该返回值与**START_STICKY**相似，同样会在service销毁后重新创建，但区别是service销毁前会将最后一次传入的intent的参数保存，等到新service创建的时候再重新传入。

理论上我们可以根据修改这些返回值来达到保活的目的，但实际上这种方式的重启效果并不理想。



#### 2、设置前台service

通过调用`setForeground`方法将本来运行在后台的service提升到前台，但这样会在系统的通知栏生成一个Notification来让用户知道这个应用正在运行着，具体的使用方法为:

	startForeground(1,new Notification());
      
在service的onCreate方法中调用`startForeground`方法，其中第一个参数是通知的唯一标识，第二个参数就是给用户展示的通知消息。当使用的通知ID一致时，只会对原有的通知进行更新。当想要停止前台服务的时候调用`stopForeground`方法即可。

但是这样做的问题在于，用户始终都能看见消息栏的通知内容，所以很容易的会选择手动关闭应用，那么有没有不让用户看见通知消息的办法呢





通过设置前台service，提高了应用的优先级，减少了被回收的概率。但是在内存极低的情况下，service依然有被杀死的可能。

#### 3、双service拉活
    
    
    
    








