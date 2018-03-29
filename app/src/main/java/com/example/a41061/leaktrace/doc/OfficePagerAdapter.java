package com.example.a41061.leaktrace.doc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * @author FanHongyu.
 * @since 18/3/28 14:56.
 * email fanhongyu@hrsoft.net.
 */

public class OfficePagerAdapter extends FragmentPagerAdapter {


    public static final int FLAG_DOC = 0;
    public static final int FLAG_EXL = 1;
    public static final int FLAG_PPT = 2;
    public static final int FLAG_PDF = 3;
    public static final int FLAG_TXT = 4;


    private String[] mOfficeTitle = {"DOC", "EXL", "PPT", "PDF", "TXT"};
    private OfficeFragment mDocFragment, mExlFragment, mPptFragment, mPdfFragment, mTxtFragment;
    private Fragment mCurrentFragment;


    /**
     * 构造函数，不传参数一般不用
     *
     * @param fm
     */
    public OfficePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * 最重要的方法 ，根据位置显示不同的fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FLAG_DOC:
                initFragment(mDocFragment, FLAG_DOC);
                break;
            case FLAG_EXL:
                initFragment(mExlFragment, FLAG_EXL);
                break;
            case FLAG_PPT:
                initFragment(mPptFragment, FLAG_PPT);
                break;
            case FLAG_PDF:
                initFragment(mPdfFragment, FLAG_PDF);
                break;
            case FLAG_TXT:
                initFragment(mTxtFragment, FLAG_TXT);
                break;
            default:
                break;
        }
        return mCurrentFragment;
    }


    /**
     * 获取pager总数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mOfficeTitle.length;
    }


    /**
     * 为不同位置的fragment设置标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mOfficeTitle[position];
    }


    /**
     * 新建fragment
     *
     * @param officeFragment
     */
    private void initFragment(OfficeFragment officeFragment, int fileType) {
        if (officeFragment == null) {
            officeFragment = new OfficeFragment();
        }
        officeFragment.setFileType(fileType);
        mCurrentFragment = officeFragment;
    }
}
