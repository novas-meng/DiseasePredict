package com.novas.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.novas.controller.HomeController;
import com.novas.model.PredictModel;
import com.novas.model.page;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by novas on 16/5/28.
 */
public class FileSave
{
    Context context;
    private static FileSave fileSave;
    private FileSave()
    {
        context=HomeController.getHomeControllerInstance(null).getContext();
    }
    public  static FileSave getFileSaveInstance()
    {
        if(fileSave==null)
        {
            fileSave=new FileSave();
        }
        return fileSave;
    }
    public String[] getParams()
    {
        File file=new File("/data/data/com.novas.diseasepredict/shared_prefs");
        String[] res=new String[2];
        res[0]="老年";
        res[1]="女人";
        if(!file.exists())
        {
            return res;
        }
        File[] files=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(!pathname.getName().startsWith("look"))
                {
                    return true;
                }
                return false;
            }
        });
        Hashtable<String,Integer> params=new Hashtable<>();
        params.put("青年",0);
        params.put("婴儿",0);
        params.put("老年",0);
        params.put("中年",0);
        int sexmancount=0;
        int sexwomancount=0;
        for(int i=0;i<files.length;i++)
        {
            System.out.println("files[i]="+files[i].getName());
            String filename=files[i].getName();
            filename=filename.substring(0,filename.length()-4);
            SharedPreferences sharedPreferences=context.getSharedPreferences(filename,context.MODE_PRIVATE);
            if(sharedPreferences.getString("sex","0").equals("0"))
            {
                sexmancount++;
            }
            else
            {
                sexwomancount++;
            }
            String age=sharedPreferences.getString("age", "-1");
            double _age=Double.parseDouble(age)*100;
            System.out.println("age="+_age);
            if(_age!=-1)
            {
                if(_age<7)
                {
                    params.put("婴儿",params.get("婴儿")+1);
                }
                else if(_age<20)
                {
                    params.put("青年",params.get("青年")+1);
                }
                else if(_age<45)
                {
                    params.put("中年",params.get("中年")+1);
                }
                else
                {
                    params.put("老年",params.get("老年")+1);
                }
            }
        }
        System.out.println(params);
        int max=0;
        for(Map.Entry<String,Integer> entry:params.entrySet())
        {
            if(entry.getValue()>max)
            {
                res[0]=entry.getKey();
                max=entry.getValue();
            }
        }
        if(sexmancount>sexwomancount)
        {
            res[1]="男";
        }
        else {
            res[1] = "女";
        }
        return res;
    }
    public void savePageToLocal(page page)
    {
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        double time=date.getTime();
        SharedPreferences sp=context.getSharedPreferences("look_history_" +time, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
       // editor.putString(time + "", page._id);
        editor.putString("title", page.title);
        editor.putString("content",page.content);
        editor.commit();
        //df.format()
    }

    //key为时间,value为对应的page
    public HashMap<Double,page> getLookHistory()
    {
        File file=new File("/data/data/com.novas.diseasepredict/shared_prefs");
        if(!file.exists())
        {
            return null;
        }
        File[] files=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.getName().startsWith("look"))
                {
                    return true;
                }
                return false;
            }
        });
        HashMap<Double,page> res=new HashMap<>();
        for(int i=0;i<files.length;i++) {
            //System.out.println("files[i]=" + files[i].getName());
            String filename = files[i].getName();
            filename = filename.substring(0, filename.length() - 4);
            SharedPreferences sharedPreferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
            page p=new page();
            p.title=sharedPreferences.getString("title",null);
            p.content=sharedPreferences.getString("content",null);
            String time=filename.split("_")[2];
            res.put(Double.parseDouble(time),p);
        }
        return res;
    }
    //key为时间,value为对应的page
    public ArrayList<page> getLookHistoryArray()
    {
        File file=new File("/data/data/com.novas.diseasepredict/shared_prefs");
        if(!file.exists())
        {
            return null;
        }
        File[] files=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.getName().startsWith("look"))
                {
                    return true;
                }
                return false;
            }
        });
        ArrayList<page> res=new ArrayList<>();
        for(int i=0;i<files.length;i++) {
            String filename = files[i].getName();
            filename = filename.substring(0, filename.length() - 4);
            SharedPreferences sharedPreferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
            page p=new page();
            p.title=sharedPreferences.getString("title",null);
            p.content=sharedPreferences.getString("content",null);
            res.add(p);
        }
        return res;
    }
    public void saveToLocal(PredictModel model)
    {
        SharedPreferences sp=context.getSharedPreferences("predict_history_" + model.hashCode(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Field[] fields=PredictModel.class.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            fields[i].setAccessible(true);
            try {
                editor.putString(fields[i].getName(),fields[i].get(model).toString());
            }
            catch (Exception e)
            {

            }
        }
        editor.commit();
    }

}
