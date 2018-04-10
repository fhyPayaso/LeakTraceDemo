package com.example.a41061.leaktrace.doc;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.FileUtil;
import com.example.a41061.leaktrace.until.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;


//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.hslf.HSLFSlideShow;
//import org.apache.poi.hslf.usermodel.SlideShow;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.apache.poi.xslf.usermodel.XMLSlideShow;
//import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.example.a41061.leaktrace.chat.activity.TestActivity.TAG;

/**
 * @author FanHongyu.
 * @since 18/3/29 10:51.
 * email fanhongyu@hrsoft.net.
 */

public class ShowOfficeActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {


    private TbsReaderView mTbsReaderView;
    private OfficeModel mOfficeModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_office);
        mOfficeModel = (OfficeModel) getIntent().getSerializableExtra("office");

        //tbsWebView初始化
        mTbsReaderView = new TbsReaderView(this, this);
        RelativeLayout rootRl = (RelativeLayout) findViewById(R.id.rl_show_office);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));


        //初始化PdfBox
        PDFBoxResourceLoader.init(getApplicationContext());

        //展示文件
        displayFile();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }


    private void displayFile() {


        initTbs();

        if (mOfficeModel != null) {

            try {
                showPageNum(mOfficeModel);
            } catch (Exception e) {
                e.printStackTrace();
            }


            boolean result = mTbsReaderView.preOpen(mOfficeModel.getType(), false);
            if (result) {
                Bundle bundle = new Bundle();
                bundle.putString("filePath", mOfficeModel.getPath());
                bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
                mTbsReaderView.openFile(bundle);
            }
        }
    }


    private void initTbs() {

        String tbsReaderPath = "/storage/emulated/0/TbsReaderTemp";
        File tbsReaderFile = new File(tbsReaderPath);

        if (!tbsReaderFile.exists()) {
            boolean isOK = tbsReaderFile.mkdir();

            Log.i("tbs", "initTbs: " + "创建成功" + isOK);
        }

    }


    public static void startActivity(Context context, OfficeModel officeModel) {
        Intent intent = new Intent(context, ShowOfficeActivity.class);

        intent.putExtra("office", officeModel);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

    public void showPageNum(OfficeModel model) throws Exception {

        int pages = 0;
        String path = model.getPath();

        switch (model.getType()) {


//            case OfficeFragment.SUFFIX_DOC:
//                WordExtractor doc = new WordExtractor(new FileInputStream(path));
//                pages = doc.getSummaryInformation().getPageCount();
//                break;
//
//            case OfficeFragment.SUFFIX_DOCX:
//                XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(path));
//                pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
//                break;
//
//            case OfficeFragment.SUFFIX_XLS:
//                HSSFWorkbook xls = new HSSFWorkbook(new FileInputStream(path));
//                pages = xls.getNumberOfSheets();
//                break;
//
//            case OfficeFragment.SUFFIX_XLSX:
//                XSSFWorkbook xlsx = new XSSFWorkbook(new FileInputStream(path));
//                pages = xlsx.getNumberOfSheets();
//                break;
//
//            case OfficeFragment.SUFFIX_PPT:
//                SlideShow ppt = new SlideShow(new HSLFSlideShow(new FileInputStream(path)));
//                pages = ppt.getSlides().length;
//
//
//                break;
//
//            case OfficeFragment.SUFFIX_PPTX:
//                XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(path));
//                pages = pptx.getSlides().length;
//                break;
//
//            case OfficeFragment.SUFFIX_PDF:
//                PDDocument document = PDDocument.load(new File(path));
//                pages = document.getNumberOfPages();
//                break;
//
//            default:
//                break;


        }


        ToastUtil.showToast("总页码为" + pages);
    }

}
