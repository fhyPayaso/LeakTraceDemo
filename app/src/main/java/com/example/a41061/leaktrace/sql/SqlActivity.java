package com.example.a41061.leaktrace.sql;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author FanHongyu.
 * @since 18/6/18 15:11.
 * email fanhongyu@hrsoft.net.
 */

public class SqlActivity extends AppCompatActivity {

    @BindView(R.id.btn_init)
    Button btnInit;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_select)
    Button btnSelect;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.txt_phone)
    TextView txtPhone;
    SQLiteDbHelper mSQLiteDbHelper;
    SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                100);


        ButterKnife.bind(this);
        mSQLiteDbHelper = new SQLiteDbHelper(getApplicationContext());
        mSQLiteDatabase = mSQLiteDbHelper.getWritableDatabase();
    }


    @OnClick(R.id.btn_add)
    public void onBtnAddClicked() {
        for (int i = 0; i < 5; i++) {
            ContentValues values = studentToContentValues(mockStudent(i));
            mSQLiteDatabase.insert(SQLiteDbHelper.TABLE_NAME, null, values);
        }
        ToastUtil.showToast("插入完成");
    }


    private Student mockStudent(int i) {
        Student student = new Student();
        student.setId(i);
        student.setName("student_" + i);
        student.setAge(i + 20);
        return student;
    }

    private ContentValues studentToContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("age", student.getAge());
        return contentValues;
    }


    @OnClick(R.id.btn_select)
    public void onViewClicked() {
        Cursor cursor = mSQLiteDatabase.query(SQLiteDbHelper.TABLE_NAME,
                null,
                "id >= 0",
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(2);
            Log.i("sql", "onViewClicked: " + "id : " + id + " ,name : " + name + ",age : " + age);

        }
        cursor.close();

        Log.i("sql", "onViewClicked: ======================================");
        ToastUtil.showToast("查询完成");

    }

    @OnClick(R.id.btn_delete)
    public void onBtnDeleteClicked() {
        mSQLiteDatabase.delete(SQLiteDbHelper.TABLE_NAME, "id = ?", new String[]{"1"});
        ToastUtil.showToast("删除完成");
    }

    @OnClick(R.id.btn_update)
    public void onBtnUpdateClicked() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "123123123123123123");
        mSQLiteDatabase.update(SQLiteDbHelper.TABLE_NAME, contentValues, "id = ?", new String[]{"4"});
        ToastUtil.showToast("更新完成");
    }


    /**
     * 获取联系人信息
     */
    @OnClick(R.id.btn_init)
    public void onBtnInitClicked() {

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {

            stringBuilder.append("\n\n");
            stringBuilder.append("ID：");
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            stringBuilder.append(contactId);

            stringBuilder.append("\t\t");
            stringBuilder.append("名字：");

            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            stringBuilder.append(contactName);
        }
        cursor.close();

        txtPhone.setText(stringBuilder.toString());
    }


}
