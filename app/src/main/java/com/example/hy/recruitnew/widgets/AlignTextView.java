package com.example.hy.recruitnew.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.example.hy.recruitnew.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

/**
 * 实现字体对齐的TextView
 * 关键点：
 * Layout：
 * getLineBaseline(int line) - 可以直接获取到各行的baseline，baseline就是每行的基准线，该行文字就是依据该baseline进行绘制
 * getLineStart，getLineEnd  - 获取每行起始结束的角标
 * StaticLayout,Layout的子类（TextView中用于绘制多行文本)
 * getDesiredWidth(CharSequence source, int start, int end, TextPaint paint) - 获取每行的宽度
 * Created by 陈健宇 at 2018/12/14
 */
public class AlignTextView extends AppCompatTextView {

    private TextPaint mPaint;

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = getPaint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.white));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence content = getText();
        if(!(content instanceof String)){
            super.onDraw(canvas);
            return;
        }
        String text = (String)getText();
        Layout layout = getLayout();
        for(int i = 0; i < layout.getLineCount(); i++){
            int lineBaseLine = layout.getLineBaseline(i) + getPaddingTop();
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            if(layout.getLineCount() == 1){//只有一行
                String line = text.substring(lineStart, lineEnd);
                float lineWidth = StaticLayout.getDesiredWidth(line, lineStart, lineEnd, mPaint);
                this.drawScaledText(canvas, text, lineBaseLine, lineWidth);
            }else if(i == layout.getLineCount() - 1){//最后一行
                canvas.drawText(text.substring(lineStart), getPaddingLeft(), lineBaseLine, mPaint);
                break;
            }else {//中间行
                String line = text.substring(lineStart, lineEnd);
                float lineWidth = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, mPaint);
                this.drawScaledText(canvas, line, lineBaseLine, lineWidth);
            }
        }
    }


    /**
     * 实现左右对齐文本
     * @param line 要对齐的文本
     * @param baseLineY 要对齐的文本的基准线
     * @param lineWidth 要对齐的文本的宽度
     */
    private void drawScaledText(Canvas canvas, String line, float baseLineY, float lineWidth) {
        if (line.length() < 1) {
            return;
        }
        float x = getPaddingLeft();
        boolean forceNextLine = line.charAt(line.length() - 1) == 10;
        int length = line.length() - 1;
        //有换行符或空行
        if (forceNextLine || length == 0) {
            canvas.drawText(line, x, baseLineY, mPaint);
            return;
        }
        //正常情况
        float d = (getMeasuredWidth() - lineWidth - getPaddingLeft() - getPaddingRight()) / length;//每行的字间隔

        for (int i = 0; i < line.length(); ++i) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, mPaint);//每个字的宽度
            canvas.drawText(c, x, baseLineY, mPaint);
            if(line.charAt(i) != 9)
                x += cw + d;
            else
                x += cw - d * 2;
        }
}

}
