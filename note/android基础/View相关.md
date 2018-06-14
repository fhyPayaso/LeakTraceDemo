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

GestureDetector主要用于检测用户的手势动作，使用的时候可以让被监听的view实现两个接口：`OnGestureListener`和`OnDoubleTapListener`,接下来看看两个接口中的几个回调方法:

+ **OnGestureListener**

	+ onDown(MotionEvent e) : 手指触摸屏幕瞬间触发
	+ onShowPress(MotionEvent e) : 手指触摸屏幕但未松开的状态
	+ onSingleTapUp(MotionEvent e) : 手指离开屏幕时触发
	+ onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) : 手指按下屏幕并拖动
	+ onLongPress(MotionEvent e) : 手指长按屏幕事件
	+ onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) : 快速滑动事件

+ **OnDoubleTapListener**

	+ onSingleTapConfirmed(MotionEvent e) : 严格的单击事件，不是双击事件中的一个点击
	+ onDoubleTap(MotionEvent e) : 双击，由两次单击组成，不能和onSingleTapConfirmed共存
	+ onDoubleTapEvent(MotionEvent e) : 表示发生了双击事件

### 二、view的分发机制

view的分发机制是面试中经常会问到的问题，也是后面解决view滑动冲突的理论基础，接下来会从源码角度对整个事件的分发机制进行简单分析。
事件分发实际上就是把一个MotionEvent事件传递给一个具体的view的过程，其中涉及到三个很重要的方法 : 

+ **boolean dispatchTouchEvent(MotionEvent ev)** : 该方法主要用于事件的分发，即事件传递到当前view时被调用，返回值受当前view的`onTouchEvent`和子view的`dispatchTouchEvent`返回值影响，true表示事件已经被消耗，false表示事件未被消耗。


+ **boolean onInterceptTouchEvent(MotionEvent ev)** : 在`dispatchTouchEvent`中被调用，表示是否拦截当前事件，只有ViewGroup才有，并且默认返回false。如果一个View决定拦截事件，那么整个事件序列都将交给这个view去处理，并且后续的事件都不会再调用`onInterceptTouchEvent`方法。


+ **boolean onTouchEvent(MotionEvent event)** : 在`dispatchTouchEvent`中被调用，表示消耗事件，返回结果表示是否消耗成功，如果一个view消耗了一个`ACTION_DOWN`事件，那么后续的事件序列都会交给这个view去处理，否则该view将不会处理后续事件。

三个方法的关系可以用如下伪代码来表示 : 

	public boolean dispatchTouchEvent(MotionEvent ev) {
	
	        boolean consume = false;
	
	        //首先判断是否需要拦截该事件
	        if (onInterceptTouchEvent(ev)) {
	            //如果拦截交给自己处理
	            consume = onTouchEvent(ev);
	        } else {
	            //不拦截则交给子view处理
	            consume = child.dispatchTouchEvent(ev);
	        }
	        return consume;
	    }
	
下面从事件分发的顺序对源码进行简单分析 : 

#### 1.activity对事件的分发

首先来看看activity里面的`dispatchTouchEvent`方法具体做了哪些事

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }













	
	