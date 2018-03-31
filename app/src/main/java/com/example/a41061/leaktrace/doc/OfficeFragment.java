package com.example.a41061.leaktrace.doc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.a41061.leaktrace.chat.activity.TestActivity.TAG;

/**
 * @author FanHongyu.
 * @since 18/3/28 14:56.
 * email fanhongyu@hrsoft.net.
 */

public class OfficeFragment extends Fragment implements TbsReaderView.ReaderCallback {


    public static final String SUFFIX_DOC = "doc";
    public static final String SUFFIX_DOCX = "docx";
    public static final String SUFFIX_XLS = "xls";
    public static final String SUFFIX_XLSX = "xlsx";
    public static final String SUFFIX_PPT = "ppt";
    public static final String SUFFIX_PPTX = "pptx";
    public static final String SUFFIX_PDF = "pdf";
    public static final String SUFFIX_TXT = "txt";


    @BindView(R.id.rec_office)
    RecyclerView recOffice;
    Unbinder unbinder;
    private int fileType;
    private OfficeRecAdapter mOfficeRecAdapter;
    private List<OfficeModel> mOfficeList;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office, container, false);
        unbinder = ButterKnife.bind(this, view);

        mProgressDialog = new ProgressDialog(getContext());

        initData();
        initRecyclerView();
        return view;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initData() {

        mProgressDialog.onStart();

        if (mOfficeList == null) {
            mOfficeList = new ArrayList<>();
        } else {
            mOfficeList.clear();
        }

        switch (fileType) {
            case OfficePagerAdapter.FLAG_DOC:
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_DOC);
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_DOCX);
                break;
            case OfficePagerAdapter.FLAG_EXL:
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_XLS);
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_XLSX);
                break;
            case OfficePagerAdapter.FLAG_PPT:
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_PPT);
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_PPTX);
                break;
            case OfficePagerAdapter.FLAG_PDF:
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_PDF);
                break;
            case OfficePagerAdapter.FLAG_TXT:
                OfficeHelper.queryFiles(getContext(), mOfficeList, SUFFIX_TXT);
                break;
            default:
                break;
        }
    }






    private void initRecyclerView() {

        mOfficeRecAdapter = new OfficeRecAdapter(getContext(), mOfficeList);
        recOffice.setAdapter(mOfficeRecAdapter);
        recOffice.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mOfficeRecAdapter.setOnItemClickListener(new OfficeRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {



                ShowOfficeActivity.startActivity(getContext(), mOfficeList.get(position));
            }
        });
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }








}
