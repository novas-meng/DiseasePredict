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
import com.novas.model.userinfoModel;

import java.util.ArrayList;

/**
 * Created by novas on 16/5/31.
 */
public class userListActivity extends Activity
{
    private HomeController homeController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_userlist);
        homeController=HomeController.getHomeControllerInstance(null);
        userinfoModel userinfoModel=homeController.getUserinfoModel();
        final ArrayList<String> data=userinfoModel.getNameList();
        ArrayAdapter simpleAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,data);
        ListView listView=(ListView)this.findViewById(R.id.userlist_listview);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=data.get(position);
                System.out.println("name=" + name);
                Bundle bundle=new Bundle();
                bundle.putString("name",name);
                Intent intent=new Intent(userListActivity.this,userTimeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
