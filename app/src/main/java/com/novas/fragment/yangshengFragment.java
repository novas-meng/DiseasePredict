package com.novas.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.novas.activity.PageActivity;
import com.novas.controller.HomeController;
import com.novas.diseasepredict.R;
import com.novas.model.ListAdapter;
import com.novas.model.page;
import com.novas.utils.FileSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nettop.BaseNetTopBusiness;
import nettop.HttpResponse;
import nettop.NetTopListener;
import com.novas.request.PageRequest;

/**
 * Created by novas on 16/3/19.
 */

public class yangshengFragment extends Fragment
{
    ArrayList<page> pageArrayList=new ArrayList<>();
    ListView pagelistview=null;
    ListAdapter listAdapter=null;
    HomeController homeController=null;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_yangsheng,container,false);
        homeController=HomeController.getHomeControllerInstance(null);
        pagelistview=(ListView)view.findViewById(R.id.fragment_yangsheng_page_listview);
        pagelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page page=pageArrayList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("title",page.title);
                bundle.putString("content",page.content);
                FileSave fileSave=FileSave.getFileSaveInstance();
                fileSave.savePageToLocal(page);
                Intent intent=new Intent(homeController.getContext(), PageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        System.out.println("onViewCreates");
      //  Thread thread=new Thread(new Runnable() {
      //      @Override
      ///      public void run() {
                final long m=System.currentTimeMillis();
                String[] params=FileSave.getFileSaveInstance().getParams();
                PageRequest pageRequest=new PageRequest();
              //  pageRequest.category="老年";
                pageRequest.sex=params[1];
              //  System.out.println(params[0]+"   "+params[1]);
                BaseNetTopBusiness baseNetTopBusiness=new BaseNetTopBusiness(new NetTopListener() {
                    @Override
                    public void onSuccess(HttpResponse response) {
                        long n=System.currentTimeMillis();
                      //  System.out.println("开始进行网络访问 "+( n-m));
                     //   System.out.println("需要时间为="+(n-m));
                     //   System.out.println("成功");
                        byte[] bytes=response.bytes;
                        try
                        {
                           // System.out.println(bytes.length);
                            String result=new String(bytes,"gbk");
                            JSONArray jsonArray=new JSONArray(result);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                             //   System.out.println(jsonObject.get("content"));
                                page page=new page();
                                page.title=jsonObject.getString("title");
                                page.content=jsonObject.getString("content");
                                page._id=jsonObject.getString("_id");
                                pageArrayList.add(page);
                            }
                            listAdapter=new ListAdapter(pageArrayList,homeController.getContext());
                            pagelistview.setAdapter(listAdapter);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail() {
                        System.out.println("on fail");
                        // textView.setText("fail");
                    }

                    @Override
                    public void onError() {
                        System.out.println("on error");
                        //textView.setText("error");
                    }
                });
                baseNetTopBusiness.startRequest(pageRequest);

        //    }
     //   });
     //   thread.start();
    }
}
