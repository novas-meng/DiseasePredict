package com.novas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.novas.activity.UserinfoActivity;
import com.novas.activity.userListActivity;
import com.novas.controller.HomeController;
import com.novas.diseasepredict.R;
import com.novas.request.userInfoRequest;

import java.io.UnsupportedEncodingException;

import nettop.BaseNetTopBusiness;
import nettop.HttpResponse;
import nettop.NetTopListener;

/**
 * Created by novas on 16/4/8.
 */
public class danganfragment extends Fragment
{
    HomeController homeController=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dangan,container,false);
        homeController=HomeController.getHomeControllerInstance(null);
        //浏览记录

        //用户档案
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.dangan_userinfo_linearlayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启网络请求
                final userInfoRequest request=new userInfoRequest();
                BaseNetTopBusiness baseNetTopBusiness=new BaseNetTopBusiness(new NetTopListener() {
                    @Override
                    public void onSuccess(HttpResponse response) {
                        byte[] bytes=response.bytes;
                        try {
                            String result=new String(bytes,"gbk");
                            homeController.makeModel(result);
                            System.out.println("result="+result);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(homeController.getContext(), userListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onError() {

                    }
                });
                baseNetTopBusiness.startRequest(request);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
