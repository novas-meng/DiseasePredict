package com.novas.model;

/**
 * Created by novas on 16/4/17.
 */
public class PredictResult
{
    public  String name=null;
    public  String age=null;
    //0表示男
    public  String sex=null;
    //cp 胸痛类型
    public  String cp=null;
    //trestbps 静息血压
    public  String trestbps=null;
    //chol 血清胆固醇
    public  String chol=null;
    //fbs 空腹血糖
    public  String fbs=null;
    //休息心电图结果
    public  String restecg=null;
    //达到的最大心脏率
    public  String thalach=null;
    //运动诱发心绞痛
    public  String exang=null;
    //由运动相对的休息诱发的ST段压低
    public  String oldpeak=null;
    //在运动高峰ST段的斜率
    public  String slope=null;
    //用荧光标记的大血管
    public  String ca=null;
    //缺陷类型
    public  String thal=null;
    //预测结果
    public  String result=null;
    //预测时间
    public String predicttime=null;
    public void setResult(String result)
    {
        this.result=result;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setAge(String age)
    {
        double age_d=Double.parseDouble(age);
        this.age=age_d/100+"";
    }
    public void setSex(int option)
    {
        // if(op)
        this.sex=option+"";
    }
    public void setCp(double cp)
    {
        this.cp=cp/10+"";
    }
    //除1000归一化
    public void setTrestbps(String trestbps)
    {
        this.trestbps=Double.valueOf(trestbps)/1000+"";
    }
    //除1000归一化
    public void setChol(String chol)
    {
        this.chol=Double.valueOf(chol)/1000+"";
    }
    public void setFbs(int option)
    {
        //  this.fbs=
        this.fbs=option+"";
    }
    public void setRestecg(double option)
    {
        this.restecg=option/10+"";
    }
    //除1000归一化
    public void setThalach(String thalach)
    {
        double thalach_d=Double.parseDouble(thalach)/1000;
        this.thalach=thalach_d+"";
    }
    public void setExang(int option)
    {
        this.exang=option+"";
    }
    //除10归一化
    public void setOldpeak(String oldpeak)
    {
        double oldpeak_d=Double.parseDouble(oldpeak)/10;
        this.oldpeak=oldpeak_d+"";
    }
    public void setSlope(double option)
    {
        this.slope=option/10+"";
    }
    public void setCa(double option)
    {
        this.ca=option/10+"";
    }
    public void setThal(double option)
    {
        if(option==0)
        {
            this.thal=0.3+"";
        }
        else if(option==1)
        {
            this.thal=0.6+"";
        }
        else
        {
            this.thal=0.7+"";
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
