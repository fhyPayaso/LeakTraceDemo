package com.example.a41061.leaktrace;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a41061.leaktrace.until.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FanHongyu.
 * @since 18/3/16 20:43.
 * email fanhongyu@hrsoft.net.
 */

public class DocActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);


        FileUtil.verifyStoragePermissions(this);
        //getWantFile(this,new String[]{".doc","."});


    }


//    public List<DocumentsContract.Document> getDocumentListData() {
//
//        String[] columns = new String[]{MediaStore.Files.FileColumns._ID
//                , MediaStore.Files.FileColumns.MIME_TYPE
//                , MediaStore.Files.FileColumns.SIZE
//                , MediaStore.Files.FileColumns.DATE_MODIFIED
//                , MediaStore.Files.FileColumns.DATA };
//
//
//        String select = "(" +
//                MediaStore.Files.FileColumns.DATA +
//                " LIKE '%.doc'" + " or " +
//                MediaStore.Files.FileColumns.DATA +
//                " LIKE '%.docx'" + " or " +
//                MediaStore.Files.FileColumns.DATA +
//                " LIKE '%.xls'" + " or " +
//                MediaStore.Files.FileColumns.DATA +
//                " LIKE '%.ppt'" + " or " +
//                MediaStore.Files.FileColumns.DATA +
//                " LIKE '%.txt'" + ")";
//
//        List<DocumentsContract.Document> dataList = new ArrayList<DocumentsContract.Document>();
//        ContentResolver contentResolver = mContext.getContentResolver();
//        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null);
//
//        int columnIndexOrThrow_ID = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
//        int columnIndexOrThrow_MIME_TYPE = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
//        int columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
//        int columnIndexOrThrow_SIZE = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
//        int columnIndexOrThrow_DATE_MODIFIED = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED); // 创建时间，更改时间
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//
//                String path = cursor.getString(columnIndexOrThrow_DATA);
//                int position_do = path.lastIndexOf(".");
//                if (position_do == -1) continue;
//                String type = path.substring(position_do + 1, path.length());
//                int doc_type = 0;
//                if (type.contains("doc")) {
//                    doc_type = FileTypeConfig.DOC;
//                } else if (type.contains("xls")) {
//                    doc_type = FileTypeConfig.XLS;
//                } else if (type.contains("ppt")) {
//                    doc_type = FileTypeConfig.PPT;
//                } else if (type.contains("txt")) {
//                    doc_type = FileTypeConfig.TXT;
//                } else {
//                    continue;
//                }
//                int position_x = path.lastIndexOf(File.separator);
//                if (position_x == -1) continue;
//                String displayName = path.substring(position_x + 1, path.length());
//                int id = cursor.getInt(columnIndexOrThrow_ID);
//                String mimeType = cursor.getString(columnIndexOrThrow_MIME_TYPE);
//                long size = cursor.getLong(columnIndexOrThrow_SIZE);
//                long modified_date = cursor.getLong(columnIndexOrThrow_DATE_MODIFIED);
//
//                DocumentsContract.Document document = new DocumentsContract.Document(id, displayName, mimeType, path,
//                        size, modified_date, doc_type);
//                document.setSelected(false);
//                dataList.add(document);
//            }
//        }
//        return dataList;
//    }


}
