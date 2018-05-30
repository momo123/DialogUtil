package com.wx.project.dialogutil.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wx.project.dialogutil.adapter.CheckBoxAdapter;
import com.wx.project.dialogutil.adapter.TextAdapter;
import com.wx.project.dialogutil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选
 * Created by WangXin on 2018/5/24.
 */

public class CheckBoxDialog extends AlertDialog {

    private LinearLayout layoutContent;
    private Context context;
    private TextView tvTitle;
    private Button btnNeg;
    private Button btnPos;
    private ListView listView;
    private List<JSONObject> dataList = new ArrayList<>();
    private CheckBoxAdapter adapter;

    public CheckBoxDialog(Context context, @StyleRes int themeResId,List<JSONObject> dataList) {
        super(context, themeResId);
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkbox);

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();

        layoutContent = (LinearLayout)findViewById(R.id.layout_content);
        layoutContent.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));

        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        btnNeg = (Button)this.findViewById(R.id.btn_neg);
        btnPos = (Button)this.findViewById(R.id.btn_pos);
        listView = (ListView)this.findViewById(R.id.listview);
        adapter = new CheckBoxAdapter(context,dataList);
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
        if (tvTitle == null){
            return;
        }
        tvTitle.setText(text+"");
    }

    public void setNegBtn(String text){
        if (btnNeg == null){
            return;
        }
        btnNeg.setText(text);
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setPosBtn(String text, final View.OnClickListener listener){
        if (btnPos == null){
            return;
        }
        btnPos.setText(text);
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
    }

}
