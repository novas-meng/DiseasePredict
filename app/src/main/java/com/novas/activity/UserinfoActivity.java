package com.novas.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.novas.controller.HomeController;
import com.novas.diseasepredict.R;
import com.novas.model.PredictResult;

import org.w3c.dom.Text;

import java.security.AccessControlContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by novas on 16/4/27.
 */
public class UserinfoActivity extends Activity
{
    ColorTemplate mCt;
    private PieChart mChart;
    double result;
    HomeController homeController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        Intent intent=getIntent();
        int index=intent.getExtras().getInt("index");
        System.out.println("index="+index);
        homeController=HomeController.getHomeControllerInstance(null);
        PredictResult predictResult=homeController.getUserinfoModel().getPredictResult(index);

        TextView predicttimetext=(TextView)this.findViewById(R.id.userinfo_predicttime);
        TextView nametext=(TextView)this.findViewById(R.id.userinfo_name);
        TextView agetext=(TextView)this.findViewById(R.id.userinfo_age);
        TextView sextext=(TextView)this.findViewById(R.id.userinfo_sex);
        TextView cptext=(TextView)this.findViewById(R.id.userinfo_cp);
        TextView trestbpstext=(TextView)this.findViewById(R.id.userinfo_trestbps);
        TextView choltext=(TextView)this.findViewById(R.id.userinfo_chol);
        TextView fbstext=(TextView)this.findViewById(R.id.userinfo_fbs);
        TextView restecgtext=(TextView)this.findViewById(R.id.userinfo_restecg);
        TextView thalachtext=(TextView)this.findViewById(R.id.userinfo_thalach);
        TextView exangtext=(TextView)this.findViewById(R.id.userinfo_exang);
        TextView oldpeaktext=(TextView)this.findViewById(R.id.userinfo_oldpeak);
        TextView slopetext=(TextView)this.findViewById(R.id.userinfo_slope);
        TextView catext=(TextView)this.findViewById(R.id.userinfo_ca);
        TextView thaltext=(TextView)this.findViewById(R.id.userinfo_thal);
        predicttimetext.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Long.parseLong(predictResult.predicttime)));
        nametext.setText(predictResult.name);
        agetext.setText(Double.parseDouble(predictResult.age)*100+"");
        sextext.setText(predictResult.sex.equals("0")?"男":"女");
        cptext.setText(this.getResources().getStringArray(R.array.cp)[(int)Double.parseDouble(predictResult.cp)*10]);
        trestbpstext.setText(Double.parseDouble(predictResult.trestbps)*1000+"");
        choltext.setText(Double.parseDouble(predictResult.chol)*1000+"");
        fbstext.setText(predictResult.fbs.equals("0")?"是":"否");
        restecgtext.setText(this.getResources().getStringArray(R.array.restecg)[(int)Double.parseDouble(predictResult.restecg)*10]);
        thalachtext.setText(Double.parseDouble(predictResult.thalach)*1000+"");
        exangtext.setText(predictResult.exang.equals("0")?"是":"否");
        oldpeaktext.setText(Double.parseDouble(predictResult.oldpeak)*10+"");
        slopetext.setText(this.getResources().getStringArray(R.array.slope)[(int)Double.parseDouble(predictResult.slope)*10]);
        catext.setText(this.getResources().getStringArray(R.array.ca)[(int)Double.parseDouble(predictResult.ca)*10]);
        int d=(int)Double.parseDouble(predictResult.restecg)*10;

        if(d==3)
        {
            thaltext.setText(this.getResources().getStringArray(R.array.thal)[0]);
        }
        else if(d==6)
        {
            thaltext.setText(this.getResources().getStringArray(R.array.thal)[1]);
        }
        else  if(d==7)
        {
            thaltext.setText(this.getResources().getStringArray(R.array.thal)[2]);

        }
        //填充其他字段
        TextView textView=(TextView)this.findViewById(R.id.userinforesult);
        textView.setText("该患者患病的可能性为"+predictResult.result);
        mChart = (PieChart) findViewById(R.id.userinfo_chart);
        PieData mPieData = getPieData(2,Double.parseDouble(predictResult.result));
        showChart(mChart, mPieData);
    }
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        pieChart.setDescription("概率分布图");
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setCenterText("概率分布图");  //饼状图中间的文字
        //设置数据
        pieChart.setData(pieData);
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        pieChart.animateXY(1000, 1000);  //设置动画
    }

    /**
     *
     * @param count 分成几部分
     * @param result
     */
    private PieData getPieData(int count, double result) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        xValues.add("患心脏病概率");  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        xValues.add("不患心脏病概率");
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        yValues.add(new Entry((float)result, 0));
        yValues.add(new Entry((float)(1-result), 1));
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        pieDataSet.setColors(colors);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }
}
