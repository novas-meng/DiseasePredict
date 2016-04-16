package com.novas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Window;

import com.novas.controller.HomeController;
import com.novas.diseasepredict.R;
import com.novas.fragment.yangshengFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by novas on 16/3/19.
 */
public class HomeActivity extends AppCompatActivity
{
    private HomeController homeController;
    private Activity mContext;
    private String mJarPath;
    private String mClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeController=HomeController.getHomeControllerInstance(this);
        homeController.addFragment(R.id.home_tab_container,"yangshengFragment");
        homeController.addFragment(R.id.home_tab_container,"yuceFragment");
        homeController.addFragment(R.id.home_tab_container, "danganfragment");
        homeController.showFragment("yuceFragment");
     //   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}