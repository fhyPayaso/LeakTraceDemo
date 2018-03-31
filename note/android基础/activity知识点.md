# Activity和Fragment回顾


### 1、Activity生命周期
		
	/**
     * 在活动第一次被创建时被调用
     * 每个Activity都必须重写，在这里完成布局的渲染和数据绑定
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest = (Button) findViewById(R.id.btn1);
        Button btnDialog = (Button) findViewById(R.id.btn2);


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
            }
        });
        Log.d(TAG, "onCreate: ");
    }

    /**
     * 活动由不可见变为可见时调用
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    /**
     * 准备好和用户进行交互时调用
     * 这时活动位于栈顶，处于运行状态
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    /**
     * 活动准备启动另一个页面的时候调用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    /**
     * 活动变为完全不可见时调用
     * 活动准备启动另一个页面或者被其他可见的组件阻塞的时候调用
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
    
    /*
     * 处于onPause和onStop状态下的活动在内存不足时可能会被回收,
     * 回收后下一次启动不会调用oReStart而是调用onCreate重新创建活动
     */
    

    /**
     * 活动被销毁时调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    /**
     * 由停止状态变为运行状态时调用
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }




### 2、活动的四种启动模式
在注册活动时设置`android:launchMode`的属性可以改变活动的启动模式。

+ `standard`:活动默认的启动模式，以这种模式启动会重新创建一个活动并放置在返回栈的栈顶，尽管该活动可能已经在栈顶存在。

+ `singleTop`:在启动活动时如果发现该活动已经置于返回栈的栈顶，便不会创建新活动，而是直接使用栈顶活动；若活动已经存在，但是没有置于栈顶，依旧会重新创建活动。

+ `singleTask`:只允许返回栈中存在一个该活动，如果启动时发现已经存在该活动的实例，会直接使用这个实例，然后将该活动返回栈上方的所有活动全部出栈。

+ `singleInstance`:创建一个新的返回栈，将新创建的该活动的实例入栈，目的是当另一个程序想和本程序共享一个活动实例时



### 3、fragment生命周期以及返回栈

+ 返回栈：和activity的返回栈类似，如果你希望这个fragment在退出时不会被直接销毁，可以通过`addToBackStack`方法加入返回栈，执行`onDestroyView`方法后不会继续销毁，返回栈中的内容需要再次展示时从`onCreateView`方法开始调用。不加入返回栈的fragment根据add的先后顺序层叠显示(但不会完全覆盖)，fragment同样可以通过`onSaveInstanceState`方法来保存当前界面的数据。Fragment的返回栈由Activity管理；而Activity的返回栈由系统管理。
+ 生命周期

	```
	/**
     * fragment和activity开始建立联系的时候调用
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 创建视图(加载布局)时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 确保fragment相关联的activity创建完毕时调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        //一个Fragment成功创建会调用以上六个方法
    }


    @Override
    public void onPause() {
        super.onPause();

        //点击返回按键或者fragment被remove/replace，调用onPause、onStop、onDestroyView

    }


    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * 销毁视图的时候调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*
        如果该fragment被添加到了返回栈，则下次重现时从onCreateView方法开始调用
        否则的话会调用接下来的onDestroy、onDetach方法，被真正销毁
         */

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * fragment和activity解除关联的时候调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

	```




### 4、一些重写方法

+ `bonCreateOptionsMenu`:**控制是否显示memu菜单**

	```
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

	```
	
+ `onOptionsItemSelected`:**菜单内部item点击事件**

	```
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            default:
                break;
        }
        return true;
    }
	```
	
+ `onBackPressed()`:**点击返回调事件**

+ `onActivityResult(int requestCode, int resultCode, Intent data)`:**得到上一个活动所返回的信息**，具体过程如下

	+ 打开活动时添加请求码参数
	```
	startActivityForResult(new Intent(MainActivity.this,TestActivity.class),0);
	```
	
	+ 在下一个活动销毁时设置返回的数据

	```
	Intent intent= new Intent();
	intent.putExtra("returnData","hello world");
	setResult(RESULT_OK,intent);
	finish();
	```
	
	+ 在上一个活动中处理返回结果

	```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断结果码
        if(resultCode == RESULT_OK) {
            //根据不同请求码确定返回的数据来源
            switch (requestCode) {
                case 0:
                    String returnData = data.getStringExtra("returnData");
                    break;
                default:
                    break;
            }
        }
    }
    ```


+ `onSaveInstanceState(Bundle outState)`:在活动销毁前一定会调用该方法，当处于暂停或者停止状态的活动因为内存不足而被回收时，返回该活动时会重新调用onCreat方法而不是onReStart(),但是之前页面的数据会因此消失，通过该方法的Bundle类型参数可以进行数据的保存。 

### 5、Activiy在Manifest中的注册(待补)
 
+ 指定主活动的标签

	```
	<intent-filter>
		<action android:name="android.intent.action.MAIN"/>
		<category android:name="android.intent.category.LAUNCHER"/>
	</intent-filter>
	
	```

+ **隐式启动Intent**:通过添加参数信息的方式启动

	+ action
	+ category
	+ data

	```
	<intent-filter>
		<action android:name=""/>
		<category android:name=""/>
		<data android:scheme=""/>
	</intent-filter>
	
	```






   
    















































    
    
    
    
    
    