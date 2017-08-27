package com.eventbus.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eventbus.demo.event.MessageEvent;
import com.eventbus.demo.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button id_send;
    private TextView id_main_btn_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册EventBus
        EventBus.getDefault().register(this);

        doViewListener();
    }

    private void doViewListener() {
        findViewById(R.id.id_main_btn_send).setOnClickListener(this);
        findViewById(R.id.id_main_btn_sticky).setOnClickListener(this);

        id_main_btn_result = (TextView)findViewById(R.id.id_main_btn_result);
    }

        @Override
        public void onClick(View view) {
            int clickId = view.getId();
            switch (clickId){
                case R.id.id_main_btn_send:
                    startActivity(new Intent(MainActivity.this, EventbusSendActivity.class));
                    break;
                case R.id.id_main_btn_sticky:
                    EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                    startActivity(new Intent(MainActivity.this, EventbusSendActivity.class));
                    break;
                default:
                    break;
            }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent messageEvent){
        id_main_btn_result.setText(messageEvent.name);
    }


}
