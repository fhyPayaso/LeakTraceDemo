# 初识MVP框架


## Contract

+ 作用：连接P层和V层，本身是一个接口
+ 内容：两个接口Presenter和View

		public interface BaseContract {
		
		
		    interface Presenter {
		
		        /**
		         * 销毁
		         */
		        void destroy();
		
		    }
		
		    interface View<T extends Presenter> {
		
		        /**
		         * V层和P层的基础绑定
		         * @param presenter
		         */
		        void setPresenter(T presenter);
		    }
		
		}
		
	+ Presenter接口：只有一个`destroy()`方法，之后V层和P的双向解绑操作就在`destroy()`的实现方法中进行
	+ View接口：首先，View接口中传入了一个继承于Presenter的泛型接口。只有一个`setPresenter()`方法，作用是V层销毁或持有对P层的引用。


## Presenter

+ 作用：将V层的逻辑抽离出来，只将运行的结果通知V层，达到松耦合的目的
+ 内容：

		public class BasePresenter <T extends BaseContract.View> implements BaseContract.Presenter{
		
		
		    protected T mView;
		
		    /**
		     * P层构造方法;
		     * 创建P层时就将P和V进行双向绑定
		     * @param view V层的引用
		     */
		    //忽略 unchecked 警告信息
		    @SuppressWarnings("unchecked")
		    public BasePresenter(T view) {
		
		        //将V层的引用捆绑到P层
		        mView = view;
		        //将P层自身捆绑到V层
		        mView.setPresenter(this);
		    }
		
		
		    /**
		     * 销毁P层,销毁V与P之间的引用,双向解绑
		     */
		    @SuppressWarnings("unchecked")
		    @Override
		    public void destroy() {
		
		        T view = mView;
		        
		        //销毁V层对P层的引用
		        if(view != null) {
		            view.setPresenter(null);
		        }
	
		        //销毁P层对V层的引用
		        mView = null;
		        
		    }
		}

	+ `BasePresenter`的泛型是一个继承了`BaseContract.View`的接口，在构造方法中就要进行双向绑定，先获取传入的V层的引用，再通过`mView.setPresenter()`让V层持有对P层的引用。

	
	+ `BasePresenter`还需要实现`BaseContract.Presenter`接口，重写`destroy`方法：如果当前的P层持有对V层的引用，那么就将P和V的相互引用置空

## View

#### PresenterActivity

因为并不是所有的V层都有大量的逻辑需要处理，所以我们可以选择性的继承，`PresenterActivity`就代表有和P层交互的V层

	public abstract class BaseNoBarPresenterActivity<Presenter extends BaseContract.Presenter> extends BaseActivity
	        implements BaseContract.View<Presenter> {
	
	    protected Presenter mPresenter;
	
	    @Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        //创建时就绑定P层
	        initPresenter();
	        super.onCreate(savedInstanceState);
	    }
	
	    /**
	     * V层创建 与之绑定的P层对象
	     *
	     * @return 与之绑定的P层对象
	     */
	    protected abstract Presenter initPresenter();
	
	    @Override
	    public void setPresenter(Presenter presenter) {
	        this.mPresenter = presenter;
	    }
	
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        mPresenter.destroy();
	    }
	}

## Model