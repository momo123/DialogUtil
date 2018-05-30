package com.wx.project.dialogutil.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wx.project.dialogutil.R;

/**
 * @author WangXin
 *         通用提示框
 */
public class TipDialogStyle2 extends AlertDialog{


    private TextView tvDialogConfirm;
    private TextView tvDialogCancel;
    private Context context;
    private TextView tvFirstLineTip;
    private TextView tvSecondLineTip;
    private TextView tvDialogTitle;
    private LinearLayout layoutContent;


    public TipDialogStyle2(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_style2);
        initView();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        layoutContent.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));
    }


    private void initView() {
        tvDialogTitle = (TextView)this.findViewById(R.id.tv_title);
        tvDialogConfirm = (TextView) this.findViewById(R.id.tv_dialog_confirm);
        tvDialogCancel = (TextView) this.findViewById(R.id.tv_dialog_cancel);
        tvFirstLineTip = (TextView) this.findViewById(R.id.tv_first_line_tip);
        tvSecondLineTip = (TextView) this.findViewById(R.id.tv_second_line_tip);
        layoutContent = (LinearLayout) this.findViewById(R.id.layout_content);
    }

    public void setTitle(String text) {
        tvDialogTitle.setText(text+"");
    }

    public void setFirstLineTip(String text) {
        tvFirstLineTip.setVisibility(View.VISIBLE);
        tvFirstLineTip.setText(text + "");
    }

    public void setSecondLineTip(String text) {
        tvSecondLineTip.setVisibility(View.VISIBLE);
        tvSecondLineTip.setText(text + "");
    }

    public void setPositiveShow(String text,final View.OnClickListener listener) {
        tvDialogConfirm.setVisibility(View.VISIBLE);
        tvDialogConfirm.setText(text);
        tvDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
    }

    public TipDialogStyle2 setNegativeBtnShow(String text, final View.OnClickListener listener) {
        tvDialogCancel.setVisibility(View.VISIBLE);
        tvDialogCancel.setText(text);
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public void setCancel(boolean isCanCancel) {
        this.setCancelable(isCanCancel);
    }


}
