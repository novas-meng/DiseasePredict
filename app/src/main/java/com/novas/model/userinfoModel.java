package com.novas.model;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by novas on 16/5/30.
 */
public class userinfoModel {
    ArrayList<PredictResult> predictModelArrayList=new ArrayList<>();
    //保存着人名
    ArrayList<String> nameList=new ArrayList<>();
    //Integer指的是位置
    LinkedHashMap<String,ArrayList<Integer>> predictResultHashMap=new LinkedHashMap<>();
    public PredictResult getPredictResult(int index)
    {
        return predictModelArrayList.get(index);
    }
    public ArrayList<Integer> getNamePredictHistory(String name)
    {
        return predictResultHashMap.get(name);
    }
    public int getNameLength()
    {
        return nameList.size();
    }
    public ArrayList<String> getNameList()
    {
        return this.nameList;
    }
    public void makeModel(String data)
    {
        predictModelArrayList.clear();
        System.out.println("调用makemodel");
        try {
            JSONArray jsonArray=new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Class cls=PredictResult.class;
                Constructor constructor=cls.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object obj=constructor.newInstance();
                //System.out.println("obj="+obj);
                Field[] fields=cls.getDeclaredFields();
                for(int j=0;j<fields.length;j++)
                {
                    fields[j].set(obj, jsonObject.get(fields[j].getName()));
                }
                predictModelArrayList.add((PredictResult)obj);
            }
            for (int i=0;i<predictModelArrayList.size();i++)
            {
                PredictResult predictResult=predictModelArrayList.get(i);
                System.out.println("i="+predictResult.toString());
                if(nameList.contains(predictResult.name))
                {
                    predictResultHashMap.get(predictResult.name).add(i);
                }
                else
                {
                    nameList.add(predictResult.name);
                    ArrayList<Integer> integers=new ArrayList<>();
                    integers.add(i);
                    predictResultHashMap.put(predictResult.name,integers);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
