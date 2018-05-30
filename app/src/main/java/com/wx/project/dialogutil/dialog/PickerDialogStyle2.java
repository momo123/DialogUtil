package com.wx.project.dialogutil.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wx.project.dialogutil.R;
import com.wx.project.dialogutil.widget.PickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选
 * Created by WangXin on 2018/5/24.
 */

public class PickerDialogStyle2 extends AlertDialog {

    private PickerView pickerView;
    private Button btnPostive;
    private Button btnNegative;
    private TextView tvTitle;
    private LinearLayout layoutContent;

    private List<String> dataList = new ArrayList<>();
    private List<String> idList = new ArrayList<>();

    private String selectedText="";
    private String selectedId="";

    private Context context;


    public PickerDialogStyle2(Context context, @StyleRes int themeResId, List<String> dataList, List<String> idList) {
        super(context, themeResId);
        this.context = context;
        this.dataList = dataList;
        this.idList = idList;
    }

    public PickerDialogStyle2(Context context, @StyleRes int themeResId, List<String> dataList) {
        super(context, themeResId);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picker_style2);

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        layoutContent = (LinearLayout)findViewById(R.id.layout_content);
        layoutContent.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

        pickerView = (PickerView) this.findViewById(R.id.pickerview);
        btnPostive = (Button) this.findViewById(R.id.btn_sure);
        btnNegative = (Button) this.findViewById(R.id.btn_cancel);
        tvTitle = (TextView) this.findViewById(R.id.tv_choose_dialog_title);
        if (dataList.size() == 0){
            Toast.makeText(context,"数据为空",Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        pickerView.setData(dataList,idList);
        selectedId = idList.get(dataList.size()/2);
        selectedText = dataList.get(dataList.size()/2);
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text, String num) {
                selectedText = text;
                selectedId = num;
            }

            @Override
            public void onSelect(String text) {
                selectedText = text;
            }
        });
    }

    public void setTitle(String text){
        tvTitle.setText(text+"");
    }

    public void setBtnPostive(String text,final View.OnClickListener listener){
        btnPostive.setText(text);
        btnPostive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(new String[]{selectedText, selectedId});
                listener.onClick(v);
                dismiss();
            }
        });
    }

    public void  setBtnNegative(String text){
        btnNegative.setText(text);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
