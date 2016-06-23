package com.app.gaolonglong.fragmenttabhost;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-23 9:31
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class YinDaoActivity extends AppCompatActivity {
    PopupWindow popup;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);

    }

    private void newbieGuide() {

        popup = new PopupWindow(YinDaoActivity.this);
        View popView = LayoutInflater.from(YinDaoActivity.this).inflate(R.layout.home_popup_window, null);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popup != null) {
                    popup.dismiss();
                }
            }
        });
        popup.setContentView(popView);
        popup.setFocusable(true);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）

        popup.setWidth(Integer.parseInt(new DecimalFormat("0").format(width)) + 10);
        popup.setHeight(Integer.parseInt(new DecimalFormat("0").format(height)) + 10);
        popup.setBackgroundDrawable(new ColorDrawable(R.color.colorAccent));
        popup.setFocusable(true);
        popup.setTouchable(true);

        popup.showAtLocation(YinDaoActivity.this.findViewById(R.id.text), Gravity.CENTER, 0, 0);

        Animation myAnimation = AnimationUtils.loadAnimation(YinDaoActivity.this, R.anim.home_translate_ani);
        myAnimation.setRepeatCount(Integer.MAX_VALUE);
        myAnimation.setRepeatMode(Animation.RESTART);
        myAnimation.setStartTime(0);

        ImageView handView = (ImageView) popView.findViewById(R.id.home_ani_hand);
        handView.startAnimation(myAnimation);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Message message = new Message();
                message.what = 10000;

                // mHandler.sendMessage(message);

                // popupwindow要依附于一个activity，而activity的onCreate()还没执行完，哪来的popup让你弹出来嘛。
                // 所以加了延时
                mHandler.sendMessageDelayed(message, 1000);

            }
        };

        timer.schedule(task, 8000);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 10000) {
                    if (popup != null) {
                        popup.dismiss();
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    public void onClick(View view) {
        newbieGuide();
    }
}
