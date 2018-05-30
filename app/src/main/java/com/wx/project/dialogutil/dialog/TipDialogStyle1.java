package com.wx.project.dialogutil.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wx.project.dialogutil.R;

/**
 * 确认提示框
 *
 * @author WangXin
 *         <p>
 *         1、textBuilder 与 setMsg，textShow 配套使用。 === 使用场景： textview 内容未完全显示，点击显示查看全部
 *         2、builder、setTitle，setMsg,setNegativeButton,setPositiveButton 可搭配使用。== 使用场景： 二次确认提示
 */

public class TipDialogStyle1 {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private RelativeLayout text_dialog;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public TipDialogStyle1(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public TipDialogStyle1 builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_style1, null);

        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public TipDialogStyle1 textBuilder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_text, null);
        text_dialog = (RelativeLayout) view.findViewById(R.id.text_dialog);
        txt_msg = (TextView) view.findViewById(R.id.text);
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        text_dialog.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.80), LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public TipDialogStyle1 setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    /**
     * 设置提示内容
     *
     * @param msg
     * @return
     */
    public TipDialogStyle1 setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }


    /**
     * 设置是否可以点击空白处取消  默认 true
     *
     * @param cancel
     * @return
     */
    public TipDialogStyle1 setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    /**
     * 设置确定按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public TipDialogStyle1 setPositiveButton(String text,
                                             final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public TipDialogStyle1 setNegativeButton(String text,
                                             final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 控制布局里控件展示
     */
    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.GONE);
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void textShow() {
        dialog.show();
    }
}
