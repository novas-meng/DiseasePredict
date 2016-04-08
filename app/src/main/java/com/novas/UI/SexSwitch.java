package com.novas.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.novas.diseasepredict.R;


/**
 * Created by novas on 16/3/22.
 */
public class SexSwitch extends View
{
    public static int LEFT_SELECTED=0;
    public static int RIGHT_SELECTED=1;
    int DedaultTextSize=30;
    String leftoption;
    String rightoption;
    int width;
    int height;
    int left;
    int top;
    int right;
    int bottom;
    int leftcolor;
    int rightcolor;
    int textsize=DedaultTextSize;
    SwitchListener listener;
    public void setOnSwitchListener(SwitchListener listener)
    {
        this.listener=listener;
    }
    public SexSwitch(Context context) {
        super(context);
        System.out.println("this is aa ");
        rightcolor=Color.parseColor("#00cc00");
        leftcolor=Color.GREEN;
    }
    public void setOptions(String leftoption,String rightoption)
    {
        this.leftoption=leftoption;
        this.rightoption=rightoption;
    }
    public int getFontDimension(int textsize)
    {
        return (int)(1.3*textsize+2);
    }
    public SexSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("this is a aa");
        rightcolor=Color.parseColor("#00cc00");
        leftcolor=Color.GREEN;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SexSwitch);
        leftoption=a.getString(R.styleable.SexSwitch_leftoptiontext);
        rightoption=a.getString(R.styleable.SexSwitch_rightoptiontext);
    }

    public SexSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("this is aaaa ");
        rightcolor=Color.parseColor("#00cc00");
        leftcolor=Color.GREEN;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        System.out.println("in layout" + left + "  " + top + "   " + right + "   " + bottom);
        width=right-left;
        height=bottom-top;
        this.left=left;
        this.top=top;
        this.bottom=bottom;
        this.right=right;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode=MeasureSpec.getMode(widthMeasureSpec);
        if(mode==MeasureSpec.EXACTLY)
        {
           System.out.println("exactly");
        }
        else if(mode==MeasureSpec.AT_MOST)
        {
            System.out.println("at most");
            int width=getFontDimension(textsize);
            int widthSpec=MeasureSpec.makeMeasureSpec(2*(width+20),MeasureSpec.EXACTLY);
            int heightSpec=MeasureSpec.makeMeasureSpec(width+20,MeasureSpec.EXACTLY);
            setMeasuredDimension(widthSpec,heightSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("on draw");
        canvas.save();
        Rect leftRect = new Rect(0, 0, width/2, height);
        Rect rightRect = new Rect(width/2, 0, width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(textsize);
        System.out.println("leftcolor="+leftcolor);
        paint.setColor(leftcolor);
        canvas.drawRect(leftRect, paint);
        paint.setColor(rightcolor);
        canvas.drawRect(rightRect, paint);
        paint.setColor(Color.WHITE);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
       // int baseline = leftRect.top + (leftRect.bottom - leftRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
       int baseline=height/2-fontMetrics.bottom/2-fontMetrics.top/2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        System.out.println("in draw="+fontMetrics.bottom+"   "+fontMetrics.top);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(leftoption, width / 4, baseline, paint);
        canvas.drawText(rightoption, width/4*3,baseline, paint);
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // return super.onTouchEvent(event);
        System.out.println(event.getRawX()+"  "+width);
        if(event.getRawX()<width/2+left)
        {
            if(listener!=null)
            {
                listener.onSelect(LEFT_SELECTED);
            }
            leftcolor=Color.parseColor("#00cc00");
            rightcolor=Color.GREEN;
        }
        else
        {
            if(listener!=null)
            {
                listener.onSelect(RIGHT_SELECTED);
            }
            rightcolor=Color.parseColor("#00cc00");
            leftcolor=Color.GREEN;
        }
        this.postInvalidate();
        return true;
    }

}
class onSwitchListener implements SwitchListener
{
    @Override
    public void onSelect(int select) {

    }
}
//0
interface SwitchListener
{
    public void onSelect(int select);
}