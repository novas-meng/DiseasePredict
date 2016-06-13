package com.novas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.novas.diseasepredict.R;

import org.w3c.dom.Text;

/**
 * Created by novas on 16/4/26.
 */
public class PageActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        TextView titletextview=(TextView)this.findViewById(R.id.page_title_textview);
        TextView contenttextview=(TextView)this.findViewById(R.id.page_content_textview);
        contenttextview.setMovementMethod(LinkMovementMethod.getInstance());
        System.out.println("title=" + bundle.getString("title"));
        System.out.println("title="+bundle.getString("content"));
        titletextview.setText(bundle.getString("title"));
        contenttextview.setText(Html.fromHtml(bundle.getString("content")));
    }
}
