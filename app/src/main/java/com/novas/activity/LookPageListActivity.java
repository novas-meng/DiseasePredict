package com.novas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.novas.diseasepredict.R;
import com.novas.model.page;
import com.novas.utils.FileSave;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by novas on 16/6/1.
 */
public class LookPageListActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lookpage);
        FileSave fileSave=FileSave.getFileSaveInstance();
        final ArrayList<page> pageArrayList=fileSave.getLookHistoryArray();
        ArrayList<String> titleArrayList=new ArrayList<>();
        for(int i=0;i<pageArrayList.size();i++)
        {
            titleArrayList.add(pageArrayList.get(i).title);
        }
        ArrayAdapter simpleAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,titleArrayList);
        ListView listView=(ListView)this.findViewById(R.id.pagelist_listview);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page p=pageArrayList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("title",p.title);
                bundle.putString("content",p.content);
                Intent intent=new Intent(LookPageListActivity.this,PageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
