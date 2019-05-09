package com.bytedance.fanhongyu.leaktrace.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;
import android.widget.TextView;
import com.bytedance.fanhongyu.leaktrace.R;


/**
 * @author fhyPayaso
 * @since 2019/2/27 3:46 PM
 */
public class TestActivity extends AppCompatActivity {


    private TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        text1 = findViewById(R.id.text_scroll);


        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append("testtesttestsssssss\n");
        }
        text1.setText(builder.toString());


    }




    private void showText(TextView textView, ReplacementSpan span) {
        String string = " 完整版完整版完整版完整版完整版完整版完整版完整版完整版完";

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) sp2px(this, 12));
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(sizeSpan, string.length() - 4, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(span, string.length() - 4, string.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }


    private void showText2(TextView textView, ReplacementSpan span) {
        String string = " 完整版完整版完整版完整版完整版完整版完整版完整版完整版完整版完整版完整版完整版";

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) sp2px(this, 12));
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(sizeSpan, string.length() - 4, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(span, string.length() - 4, string.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }

    public static float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static Drawable getResDrawable(Context context, @DrawableRes int res) {
        return context.getResources().getDrawable(res);
    }

    public static int getResColor(Context context, @ColorRes int res) {
        return context.getResources().getColor(res);
    }

}
