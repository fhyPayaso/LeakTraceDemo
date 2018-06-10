## View相关


### 一、基础知识

#### 1.View的位置参数

Android中X轴和Y轴的正方向分别为右和下
	
+ **left** : 左上角横坐标
+ **top** : 左上角纵坐标
+ **right** : 右下角横坐标
+ **bottom** : 右下角纵坐标

可以通过right-left、bottom-top来获取view的宽度和高度

+ **translationX** : 左上角横坐标偏移量
+ **translationY** : 左上角纵坐标偏移量
+ **x** : 左上角横坐标(加偏移量后)
+ **y** : 左上角纵坐标(加偏移量后)

首先这些坐标都是相对于父容器的坐标，并且都可以通过get/set方法获取到，几个参数的换算关系为:

	x = left + translationX	
	y = top + translationY
        
        
#### 2.MotionEvent(触摸事件)
MotionEvent代表触摸屏幕的事件，常见的事件类型有

+ **ACTION_DOWN** : 手指按压屏幕
+ **ACTION_MOVE** : 手指在屏幕滑动
+ **ACTION_UP** : 手指离开屏幕

获取事件发生位置的方法有:
	
+ **getX()** : 返回相对当前view的横坐标
+ **getY()** : 返回相对当前view的纵坐标
+ **getRawX()** : 返回相对于屏幕左上角的横坐标
+ **getRawY()** : 返回相对于屏幕左上角的纵坐标
	
#### 3.TouchSlop(最小滑动距离)

TouchSlop是系统所能识别出的最小滑动距离，获取方式为：

	ViewConfiguration.get(MainActivity.this).getScaledTouchSlop();
	
	
#### 4.VelocityTracker(速度追踪)

使用方法,先初始化，并添加一个触摸事件，然后设置时间间隔并计算:

	VelocityTracker velocityTracker = VelocityTracker.obtain();
	velocityTracker.addMovement(motionEvent);//添加触摸事件
	velocityTracker.computeCurrentVelocity(1000);//设置事件间隔并计算速度
	float xVelocity = velocityTracker.getXVelocity();//获取水平方向移动速度
	float yVelocity = velocityTracker.getYVelocity();//获取竖直方向移动速度
计算方式为: 速度 = (终点坐标-起始坐标) / 时间间隔，即在1000ms内水平滑动过的像素为100，则水平速度为100px/s,注意在
不需要的时候调用`velocityTracker.clear()`来回收内存。

#### 5.GestureDetector(手势检测)









	
	