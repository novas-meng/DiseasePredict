package com.novas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.novas.controller.HomeController;
import com.novas.diseasepredict.R;
import com.novas.model.PredictResult;
import com.novas.model.userinfoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by novas on 16/5/31.
 */
public class userTimeActivity extends Activity
{
    private HomeController homeController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_usertime);
        homeController=HomeController.getHomeControllerInstance(null);
        userinfoModel userinfoModel=homeController.getUserinfoModel();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString("name");
        final ArrayList<Integer> arrayList=userinfoModel.getNamePredictHistory(name);
        ArrayList<String> predicttimeList=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        for(int i=0;i<arrayList.size();i++)
        {
            String time=userinfoModel.getPredictResult(arrayList.get(i)).predicttime;
            long timeD=Long.parseLong(time);
            calendar.setTimeInMillis(timeD);
            String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(timeD);
            predicttimeList.add(str);
        }
        ArrayAdapter simpleAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,predicttimeList);
        ListView listView=(ListView)this.findViewById(R.id.usertime_listview);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index=arrayList.get(position);
                System.out.println("index=" + index);
                Bundle bundle=new Bundle();
                bundle.putInt("index",index);
                Intent intent=new Intent(userTimeActivity.this,UserinfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
