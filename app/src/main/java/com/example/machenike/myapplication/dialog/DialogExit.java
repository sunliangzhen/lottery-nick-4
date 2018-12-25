package com.example.machenike.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.machenike.myapplication.R;


/**
 */
public class DialogExit extends Dialog {

    private Context context;
    private TextView tv_cancel;
    private TextView tv_ok;
    private DialogExitListen callPhoneListen;

    public DialogExit(Context context, DialogExitListen callPhoneListen) {
        super(context, R.style.dialog);
        this.context = context;
        this.callPhoneListen = callPhoneListen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_cancel = (TextView) findViewById(R.id.tv_cancle);
        tv_ok = (TextView)findViewById(R.id.tv_ok);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callPhoneListen != null) {
                    callPhoneListen.call();
                }
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }


    public interface DialogExitListen {
        void call();
    }

}
