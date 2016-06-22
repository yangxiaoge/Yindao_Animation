package com.app.gaolonglong.fragmenttabhost;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class HomeFragment extends Fragment {

    private View rootView;

    PopupWindow popup;
    Context context;
    Handler mHandler;

    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        if (rootView == null) {
            Log.e("666", "HomeFragment");
            rootView = inflater.inflate(R.layout.home_fragment, container, false);

            initViews();
            // 开启手势指引
            newbieGuide();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initViews() {
        btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮也可以开启动画
                newbieGuide();
            }
        });
    }

    private void newbieGuide() {

        popup = new PopupWindow(context);
        View popView = LayoutInflater.from(context).inflate(R.layout.home_popup_window, null);
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
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）

        popup.setWidth(Integer.parseInt(new DecimalFormat("0").format(width)) + 10);
        popup.setHeight(Integer.parseInt(new DecimalFormat("0").format(height)) + 10);
        popup.setBackgroundDrawable(new ColorDrawable(R.color.titleColorSelected));
        popup.setFocusable(true);
        popup.setTouchable(true);

        popup.showAtLocation(rootView.findViewById(R.id.home_toolbar), Gravity.CENTER, 0, 0);

        Animation myAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.home_translate_ani);
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
                mHandler.sendMessage(message);

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
}
