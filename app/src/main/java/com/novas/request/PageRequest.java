package com.novas.request;

import nettop.Request;

/**
 * Created by novas on 16/4/26.
 */
public class PageRequest implements Request
{
    public String requestUrl="http://192.168.56.1:8080/JavaWeb/getPage";
    // public String user="novas";
   // public String proID="1";
    //表示青年 老年 婴儿 中年
    public String category="老年";
    public String name="novas";
    //表示性别
    public String sex;
}
