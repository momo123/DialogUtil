package com.wx.project.dialogutil.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wx.project.dialogutil.adapter.TextAdapter;
import com.wx.project.dialogutil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选
 * Created by WangXin on 2018/5/24.
 */

public class PickerDialogStyle1 extends AlertDialog {

    private LinearLayout layoutContent;
    private Context context;
    private TextView tvTitle;
    private ListView listView;
    private List<JSONObject> dataList = new ArrayList<>();
    private TextAdapter adapter;

    public PickerDialogStyle1(Context context, @StyleRes int themeResId,List<JSONObject> dataList) {
        super(context, themeResId);
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picker_style1);

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();

        layoutContent = (LinearLayout)findViewById(R.id.layout_content);
        layoutContent.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));

        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        listView = (ListView)this.findViewById(R.id.listview);
        adapter = new TextAdapter(context,dataList);
        listView.setAdapter(adapter);
    }

    public void setOnItemClickEvent(final AdapterView.OnItemClickListener listener){
        if (listView == null){
            return;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(parent,view,position,id);
                dismiss();
            }
        });
    }

    public void setTitle(String text){
        tvTitle.setText(text+"");
    }

}
