package com.eventbus.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eventbus.demo.event.MessageEvent;
import com.eventbus.demo.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventbusSendActivity extends Activity implements View.OnClickListener{

    private TextView id_eventbus_send_tv_result;
    private boolean isFistClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_send);


        doViewLIstener();
    }

    private void doViewLIstener() {
        findViewById(R.id.id_eventbus_send_btn_send).setOnClickListener(this);
        findViewById(R.id.id_eventbus_send_btn_sticky).setOnClickListener(this);
        id_eventbus_send_tv_result = (TextView)findViewById(R.id.id_eventbus_send_tv_result);
    }

    @Override
    public void onClick(View view) {
        int clickId = view.getId();
        switch (clickId){
            case R.id.id_eventbus_send_btn_send:
                EventBus.getDefault().post(new MessageEvent("这是主线程发送过来的数据"));
                EventbusSendActivity.this.finish();
                break;
            case R.id.id_eventbus_send_btn_sticky:
                //防止多次点击造成的崩溃
                if (!isFistClick){
                    EventBus.getDefault().register(EventbusSendActivity.this);
                    isFistClick = true;
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventbusSendActivity.this);
    }

    /**
     * 接收粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void stickyEventBus(StickyEvent stickyEvent){
        id_eventbus_send_tv_result.setText(stickyEvent.name);
    }

}
