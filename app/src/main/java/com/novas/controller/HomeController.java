package com.novas.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.novas.UI.SexSwitch;
import com.novas.UI.SwitchListener;
import com.novas.activity.ResultActivity;
import com.novas.diseasepredict.R;
import com.novas.model.PredictModel;
import com.novas.model.userinfoModel;
import com.novas.request.predictRequest;
import com.novas.utils.FileSave;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;

import nettop.BaseNetTopBusiness;
import nettop.HttpResponse;
import nettop.NetTopListener;
import nettop.firstRequest;

/**
 * Created by novas on 16/3/21.
 */
public class HomeController implements control
{
    private static HomeController homeController;
    private Activity context;
    private FragmentTransaction ft;
    //存储用户信息Model
    private userinfoModel userinfoModel=new userinfoModel();
    private Hashtable<String, Fragment> fragmentHashtable=new Hashtable<>();
    int[] ids=null;
    View[] linearLayouts=null;
    View[] textviews=null;
    List<String> namelist=new ArrayList<>();
    int currentindex;
    PredictModel predictModel=new PredictModel();
    //需要用的控件
    EditText agetext=null;
    SexSwitch sextext=null;
    Spinner cptext=null;
    EditText trestbpstext=null;
    EditText choltext=null;
    SexSwitch fbstext=null;
    Spinner restcgtext=null;
    EditText thalachtext=null;
    SexSwitch exangtext=null;
    EditText oldpeaktext=null;
    Spinner slopetext=null;
    Spinner catext=null;
    Spinner thaltext=null;
    EditText nametext=null;
    public userinfoModel getUserinfoModel()
    {
        return this.userinfoModel;
    }
    //填充userinfoModel
    public void makeModel(String data)
    {
        userinfoModel.makeModel(data);
    }
    private HomeController(Activity context)
    {
        this.context=context;
        AppCompatActivity activity=(AppCompatActivity)context;
        ft=activity.getSupportFragmentManager().beginTransaction();
    }
    public static HomeController getHomeControllerInstance(Activity context)
    {
        if(homeController==null)
        {
            homeController=new HomeController(context);
        }
        return homeController;
    }
    public void addFragment(int layoutid,String frname)
    {
        Fragment fr=null;
        try {
            Class c=Class.forName("com.novas.fragment."+frname);
            fr=(Fragment)c.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        fragmentHashtable.put(frname,fr);
        namelist.add(frname);
        ft.add(layoutid,fr);
        ft.hide(fr);
    }

    public void showFragment(String fragment)
    {
        linearLayouts=new View[fragmentHashtable.size()];
        textviews=new View[linearLayouts.length];
        ids=new int[fragmentHashtable.size()];
        linearLayouts[0]=context.findViewById(R.id.tab1);
        linearLayouts[1]=context.findViewById(R.id.tab2);
        linearLayouts[2]=context.findViewById(R.id.tab3);
        textviews[0]=context.findViewById(R.id.tab1text);
        textviews[1]=context.findViewById(R.id.tab2text);
        textviews[2]=context.findViewById(R.id.tab3text);
        ids[0]=R.id.tab1;
        ids[1]=R.id.tab2;
        ids[2]=R.id.tab3;
        tabClickListener tabClickListener=new tabClickListener();
        for(int i=0;i<linearLayouts.length;i++)
        {
            linearLayouts[i].setOnClickListener(tabClickListener);
        }
        ft.show(fragmentHashtable.get(fragment));
        TextView textView=(TextView)context.findViewById(R.id.tab2text);
        textView.setTextColor(Color.GREEN);
        currentindex=namelist.indexOf(fragment);
        ft.commit();
    }

    @Override
    public void destroy() {
        if(context!=null)
        {
            context=null;
            ft=null;
            homeController=null;
        }
    }
    public Context getContext()
    {
        return context;
    }
    public void showtab(int id)
    {
        int index=0;
        for(int i=0;i<ids.length;i++)
        {
            if(ids[i]==id)
            {
                index=i;
            }
        }
        System.out.println("currentindex="+currentindex+"index="+index);
        ((TextView)textviews[index]).setTextColor(Color.GREEN);
        ((TextView)textviews[currentindex]).setTextColor(Color.WHITE);
        if(index!=currentindex)
        {
            AppCompatActivity activity=(AppCompatActivity)context;
            ft=activity.getSupportFragmentManager().beginTransaction();
            ((TextView)textviews[index]).setTextColor(Color.GREEN);
            ((TextView)textviews[currentindex]).setTextColor(Color.WHITE);
            Fragment oldfragment=fragmentHashtable.get(namelist.get(currentindex));
            Fragment newfragment=fragmentHashtable.get(namelist.get(index));
            ft.hide(oldfragment);
            ft.show(newfragment);
            ft.commit();
            currentindex=index;
        }

    }
    class tabClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            showtab(v.getId());
        }
    }
    public void startPredict()
    {

        predictModel.setName(nametext.getText().toString());
        predictModel.setAge(agetext.getText().toString());
        predictModel.setSex(sextext.getSelected());
        predictModel.setCp(cptext.getSelectedItemPosition() + 1);
        System.out.println("fbs="+fbstext.getSelected());
        predictModel.setFbs(fbstext.getSelected());
        //静息血压，大概为145左右
        predictModel.setTrestbps(trestbpstext.getText().toString());
        //血清胆固醇 233左右
        predictModel.setChol(choltext.getText().toString());
        predictModel.setRestecg(restcgtext.getSelectedItemPosition()+1);
        //达到的最大心脏率 150左右
        predictModel.setThalach(thalachtext.getText().toString());
        predictModel.setExang(exangtext.getSelected());
        predictModel.setSlope(slopetext.getSelectedItemPosition()+1);
        //由相对运动引发的st段低 2.3左右
        predictModel.setOldpeak(oldpeaktext.getText().toString());
        predictModel.setCa(catext.getSelectedItemPosition());
        predictModel.setThal(thaltext.getSelectedItemPosition());
        predictRequest arequest=new predictRequest();
        BaseNetTopBusiness baseNetTopBusiness=new BaseNetTopBusiness(new NetTopListener() {
            @Override
            public void onSuccess(HttpResponse response) {

                System.out.println("成功");
                byte[] bytes=response.bytes;

                try
                {  System.out.println(bytes.length);

                    System.out.println(new String(bytes,"gbk"));
                    String result=new String(bytes,"gbk");
                    Intent intent=new Intent(context, ResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("result",result);
                    predictModel.setResult(result);
                    FileSave.getFileSaveInstance().saveToLocal(predictModel);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                   // textView.setText(new String(bytes, "gbk").substring(3680)+"");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {
                System.out.println("on fail");
              //  textView.setText("fail");
            }

            @Override
            public void onError() {
                System.out.println("on error");
                //textView.setText("error");
            }
        });
        baseNetTopBusiness.startRequest(arequest);

    }
    //开始进行预测
    public void initPredict()
    {
         agetext=(EditText)context.findViewById(R.id.age);
         sextext=(SexSwitch)context.findViewById(R.id.sex);
         cptext=(Spinner)context.findViewById(R.id.cp);
         trestbpstext=(EditText)context.findViewById(R.id.trestbps);
         choltext=(EditText)context.findViewById(R.id.chol);
         fbstext=(SexSwitch)context.findViewById(R.id.fbs);
         restcgtext=(Spinner)context.findViewById(R.id.restcg);
         thalachtext=(EditText)context.findViewById(R.id.thalach);
         exangtext=(SexSwitch)context.findViewById(R.id.exang);
         oldpeaktext=(EditText)context.findViewById(R.id.oldpeak);
         slopetext=(Spinner)context.findViewById(R.id.slope);
         catext=(Spinner)context.findViewById(R.id.ca);
         thaltext=(Spinner)context.findViewById(R.id.thal);
         nametext=(EditText)context.findViewById(R.id.name);
    }
}
