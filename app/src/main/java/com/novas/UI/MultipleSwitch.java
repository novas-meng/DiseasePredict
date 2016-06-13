
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

public class MultipleSwitch extends View
{
    //默认是两个选择按钮
    int optionsCount=3;
    int currentSelectelected=0;
    String[] values=null;
    int DedaultTextSize=30;
    int width;
    int height;
    int left;
    int top;
    int right;
    int bottom;
    int selectedcolor;
    int unselectedcolor;
    int textsize=DedaultTextSize;
    SwitchListener listener;
    public void setOnSwitchListener(SwitchListener listener)
    {
        this.listener=listener;
    }
    public MultipleSwitch(Context context) {
        super(context);
        System.out.println("this is aa ");
        selectedcolor=Color.parseColor("#00cc00");
        unselectedcolor=Color.GREEN;
    }
    public void setOptions(String[] values)
    {
        this.values=values;
    }
    public int getFontDimension(int textsize)
    {
        return (int)(1.3*textsize+2);
    }
    public MultipleSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("this is a aa");
        selectedcolor=Color.parseColor("#00cc00");
        unselectedcolor=Color.GREEN;

    }

    public MultipleSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("this is aaaa ");
        selectedcolor=Color.parseColor("#00cc00");
        unselectedcolor=Color.GREEN;
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
        Rect[] rects=new Rect[optionsCount];
        //抗锯齿标志位
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(textsize);
        for(int i=0;i<rects.length;i++)
        {
           if(i==currentSelectelected)
           {
               paint.setColor(selectedcolor);
           }
            else
           {
               paint.setColor(unselectedcolor);
           }
            canvas.drawRect(width/optionsCount*i,0,width/optionsCount*(i+1),height,paint);
        }
        /*
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
        */
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // return super.onTouchEvent(event);
        System.out.println(event.getRawX()+"  "+width+"  left="+left);
        System.out.println(event.getRawY()+"  "+height+"  top="+top+"  "+this.getTop());
            System.out.println("Id=" + this.getId());
            if(event.getRawX()<width/2+left)
            {
                if(listener!=null)
                {
                    System.out.println("左");
                   // listener.onSelect(LEFT_SELECTED);
                }
              //  leftcolor=Color.parseColor("#00cc00");
              //  rightcolor=Color.GREEN;
            }
            else
            {
                if(listener!=null)
                {
                //    listener.onSelect(RIGHT_SELECTED);
                }
               // rightcolor=Color.parseColor("#00cc00");
               // leftcolor=Color.GREEN;
            }
        this.postInvalidate();
        return true;
    }

}
class onSwitchListener implements SwitchListener
{
    @Override
    public void onSelect(int select) {
        System.out.println("select="+select);
    }
}
