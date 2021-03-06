package nettop;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novas on 16/3/3.
 */
public class eventcenter {
    public static eventcenter center=null;
    Hashtable<Object,NetTopListener> listenerHashtable=new Hashtable<>();
    Handler h=new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("receive");

            if(msg.what==1)
            {
                System.out.println("receive");
                HttpResponse response=(HttpResponse)msg.obj;
                NetTopListener listener=listenerHashtable.get(response);
                listener.onSuccess(response);
                listenerHashtable.remove(response);
            }
            else if(msg.what==0)
            {
                Object obj=msg.obj;
                NetTopListener listener=listenerHashtable.get(obj);
                listener.onFail();
                listenerHashtable.remove(obj);
            }
            else if(msg.what==-1)
            {
                Object obj=msg.obj;
                NetTopListener listener=listenerHashtable.get(obj);
                listener.onFail();
                listenerHashtable.remove(obj);
            }
        }
    };
    private eventcenter()
    {

    }
    public  void postError(Object object,NetTopListener listener)
    {
        listenerHashtable.put(object,listener);
        h.obtainMessage(-1,object).sendToTarget();
    }
    public  void postFail(Object object,NetTopListener listener)
    {
          listenerHashtable.put(object,listener);
          h.obtainMessage(0,object).sendToTarget();
    }
    public  void postSuccess(HttpResponse response,NetTopListener listener)
    {
       // Handler handler=new Handler();
          System.out.println("in put table");
          listenerHashtable.put(response, listener);
        Message message=Message.obtain();
        message.setTarget(h);
        message.what=1;
        message.obj=response;
        message.sendToTarget();
        //  h.obtainMessage(1,response).sendToTarget();
          System.out.println("发送消息");
    }
    public static eventcenter getCenterInstance()
    {
        if(center==null)
        {
            center=new eventcenter();
        }
        return center;
    }
    private static ExecutorService executorService= Executors.newCachedThreadPool();
    public void execute(Runnable runnable)
    {
        executorService.execute(runnable);
    }
}
