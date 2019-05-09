package com.bytedance.fanhongyu.leaktrace.test;

import android.content.Context;
import android.graphics.Color;
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
import com.bytedance.fanhongyu.leaktrace.widget.TestSpan;


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



//        showText(text1, addTailSpan());
//        showText2(text2, addRoundSpan());
//        showText(text3, addRoundSpan());
//        showText(text3, addTestSpan());
    }

//
//    private TailSpan addTailSpan() {
//        return new TailSpan(
//                this,
//                R.drawable.icon_karaoke_play_whole,
//                getResColor(this, R.color.bg_video_detail_desc_tail),
//                (int) dip2Px(this, 4));
//    }
//
//
//    private RoundIconSpan addRoundSpan() {
//        final int padding = 4;
//        final int iconSize = 16;
//        RoundIconSpan.Builder builder = new RoundIconSpan.Builder();
//        builder.setBackgroud(getResDrawable(this, R.drawable.bg_span))
//                .setIcon(getResDrawable(this, R.drawable.icon_karaoke_play_whole))
//                .setPaddingLeft((int) dip2Px(this, padding))
//                .setPaddingRight((int) dip2Px(this, padding))
//                .setIconHeight((int) dip2Px(this, iconSize))
//                .setIconWidth((int) dip2Px(this, iconSize));
//        return builder.build();
//    }


    private TestSpan addTestSpan() {
        final int padding = 4;
        final int iconSize = 16;
        TestSpan.Builder builder = new TestSpan.Builder(this);
        builder.setBackground(R.drawable.bg_span)
                .setIcon(R.drawable.icon_karaoke_play_whole)
                .setIHeight(iconSize)
                .setIWidth(iconSize)
                .setPLeft(2)
                .setPRight(4)
                .setPBottom(3)
                .setPTop(3);
        return builder.build();
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
