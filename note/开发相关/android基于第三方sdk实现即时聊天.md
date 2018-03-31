# android基于第三方sdk实现即时聊天

在一些小项目的开发过程中，需求里可能会有实现聊天功能的要求，这里我们通过集成环信SDK来实现简单的聊天功能。




## 一、前期准备

+ 首先我们需要在环信官网上注册开发者账号并创建应用，获取到AppKey，具体流程请见[环信开发文档](http://docs.easemob.com/im/000quickstart/10register)


## 二、集成sdk
+ 在biuld.gradle中添加如下依赖
 
    	compile 'com.google.android.gms:play-services-gcm:9.4.0'
    	compile 'com.hyphenate:hyphenate-sdk:3.3.0'
    	
+ 在Manifest中添加需要的权限，记得在 `android:value`添加你之前注册好的AppKey

		<uses-permission android:name="android.permission.VIBRATE" />
	    <uses-permission android:name="android.permission.INTERNET" />
	    <uses-permission android:name="android.permission.RECORD_AUDIO" />
	    <uses-permission android:name="android.permission.CAMERA" />
	    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	    <uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION" />
	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>  
	    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
	    <uses-permission android:name="android.permission.GET_TASKS" />
	    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
	    <uses-permission android:name="android.permission.WAKE_LOCK" />
	    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
	    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
		<application
	        android:icon="@drawable/ic_launcher"
	        android:label="@string/app_name"
	        android:name="Your Application">
		   	<!-- 设置环信应用的AppKey -->
	        <meta-data
	            android:name="EASEMOB_APPKEY"
	            android:value="Your AppKey"/>
	        <!-- 声明SDK所需的service SDK核心功能-->
	        <service
	            android:name="com.hyphenate.chat.EMChatService"
	            android:exported="true"/>
	        <service
	            android:name="com.hyphenate.chat.EMJobService"
	            android:exported="true"
	            android:permission="android.permission.BIND_JOB_SERVICE"
	            />
	        <!-- 声明SDK所需的receiver -->
	        <receiver android:name="com.hyphenate.chat.EMMonitorReceiver">
	            <intent-filter>
	                <action android:name="android.intent.action.PACKAGE_REMOVED"/>
	                <data android:scheme="package"/>
	            </intent-filter>
	            <!-- 可选filter -->
	            <intent-filter>
	                <action android:name="android.intent.action.BOOT_COMPLETED"/>
	                <action android:name="android.intent.action.USER_PRESENT"/>
	            </intent-filter>
	        </receiver>
		</application>
		
## 三、初始化sdk

+ 首先要在Application的onCreate方法中初始化SDK的设置

		EMOptions options = new EMOptions();
		
		// 在这里可以进行一些初始化设置，例如默认添加好友时，是不需要验证的，可以改成需要验证
		options.setAcceptInvitationAlways(false);
		...
		
		EMClient.getInstance().init(applicationContext, options);


+ 如果你的应用中包含第三方登录功能，为了防止SDK重复初始化，需要在初始化sdk`EMClient.getInstance().init(applicationContext, options)`方法前添加判断：

		appContext = this;
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果APP启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
		if (processAppName == null ||!processAppName.equalsIgnoreCase(appContext.getPackageName())) {
		    Log.e(TAG, "enter the service process!");
		    
		    // 则此application::onCreate 是被service 调用的，直接返回
		    return;
		}


	其中的`getAppName()`具体实现为:
	
		private String getAppName(int pID) {
		
			String processName = null;
	    	ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
	    	List l = am.getRunningAppProcesses();
	    	Iterator i = l.iterator();
	    	PackageManager pm = this.getPackageManager();
	    	
	    	while (i.hasNext()) {
	    		ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
	    		try {
	    			if (info.pid == pID) {
	    				processName = info.processName;
	    				return processName;
	            	}
	        	} catch (Exception e) {
	            // Log.d("Process", "Error>> :"+ e.toString());
	        	}
	    	}
	    	return processName;
		}
		
## 四、登录注册

### 注册
注册模式分为两种，开放注册和授权注册。

+ 开放注册：可以直接在客户端进行注册，可以在测试中使用，在正式使用环境中不推荐。
+ 授权注册：授权注册需要后端通过环信提供的 REST API 注册，之后将信息保存到服务器并返回客户端。

具体方法如下：




