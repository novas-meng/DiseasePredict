package com.novas.request;

import com.novas.model.PredictModel;

import nettop.Request;

/**
 * Created by novas on 16/4/17.
 */
public class predictRequest implements Request
{
    String requestUrl="http://192.168.56.1:8080/JavaWeb/Predict";
    public String name=PredictModel.name;
    public  String age=PredictModel.age;
    public  String sex=PredictModel.sex;
    public  String cp=PredictModel.cp;
    public  String trestbps=PredictModel.trestbps;
    public  String chol=PredictModel.chol;
    public  String fbs=PredictModel.fbs;
    public  String restecg=PredictModel.restecg;
    public  String thalach=PredictModel.thalach;
    public  String exang=PredictModel.exang;
    public  String oldpeak=PredictModel.oldpeak;
    public  String slope=PredictModel.slope;
    public  String ca=PredictModel.ca;
    public  String thal=PredictModel.thal;
}
