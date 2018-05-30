package com.wx.project.dialogutil.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wx.project.dialogutil.R;
import com.wx.project.dialogutil.adapter.TextAdapter;
import com.wx.project.dialogutil.dialog.CheckBoxDialog;
import com.wx.project.dialogutil.dialog.PickDateDialog;
import com.wx.project.dialogutil.dialog.PickerDialogStyle1;
import com.wx.project.dialogutil.dialog.PickerDialogStyle2;
import com.wx.project.dialogutil.dialog.TipDialogStyle1;
import com.wx.project.dialogutil.dialog.TipDialogStyle2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangXin
 *  自定义dialog  实现各种不同弹框样式
 */

public class MainActivity extends AppCompatActivity implements PickDateDialog.OnTimePickedListener{

    private PickDateDialog pickDateDialog;
    private ListView listView;
    private List<JSONObject> dataList = new ArrayList<>();
    private TextAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        listView = (ListView)this.findViewById(R.id.listview);
        adapter = new TextAdapter(this,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://提示样式1
                        showTipDialogStytle1();
                        break;
                    case 1://提示样式2
                        showTipDialogStyle2();
                        break;
                    case 2://提示样式3
                        showTipDialogStyle3();
                        break;
                    case 3://单选样式1
                        showPickerDialogStyle1();
                        break;
                    case 4://单选样式2
                        showPickerDialogStyle2();
                        break;
                    case 5://多选
                        showCheckBoxDialog();
                        break;
                    case 6://选择日期
                        showDatePickerDialog(1, PickDateDialog.ShowStyle.SHOW_DATA);
                        break;
                }
            }
        });
    }

    /**
     * 初始化要显示的数据
     */
    private void initData(){
        String[] content = new String[]{"提示样式1","提示样式2","提示样式3","单选样式1","单选样式2","多选","选择日期"};
        try {
            for (int i=0;i<content.length;i++){
                JSONObject object = new JSONObject();
                object.put("content",content[i]);
                dataList.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showTipDialogStytle1() {
        new TipDialogStyle1(MainActivity.this)
                .builder()
                .setTitle("提示")
                .setMsg("确认离开当前页并放弃已编辑的内容")
                .setPositiveButton("继续编辑", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setNegativeButton("放弃", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                 })
                .show();
    }

    private void showTipDialogStyle2(){
        TipDialogStyle2 dialogStyle2 = new TipDialogStyle2(this,R.style.AlertDialogStyle);
        dialogStyle2.show();
        dialogStyle2.setTitle("提示");
        dialogStyle2.setFirstLineTip("第一行内容");
        dialogStyle2.setSecondLineTip("第二行内容");
        dialogStyle2.setNegativeBtnShow("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        dialogStyle2.setPositiveShow("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    private void showTipDialogStyle3(){
        new TipDialogStyle1(MainActivity.this)
                .textBuilder()
                .setMsg("确认离开当前页并放弃已编辑的内容")
                .textShow();
    }

    private void showPickerDialogStyle2(){
        List<String> dataList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (int i=0;i<5;i++){
            dataList.add("数据"+(i+1));
            idList.add(i+"");
        }
        PickerDialogStyle2 pickerDialog = new PickerDialogStyle2(this,R.style.AlertDialogStyle,dataList,idList);
        pickerDialog.show();
        pickerDialog.setTitle("选择数据");
        pickerDialog.setBtnNegative("取消");
        pickerDialog.setBtnPostive("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] pickerData = (String[]) v.getTag();
                Toast.makeText(MainActivity.this,"已选择"+pickerData[0]+" id为"+pickerData[1],Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPickerDialogStyle1(){
        final List<JSONObject> list = new ArrayList<>();
        try {
            for (int i=0;i<5;i++){
                JSONObject object = new JSONObject();
                object.put("content","数据"+(i+1));
                list.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PickerDialogStyle1 dialogStyle1 = new PickerDialogStyle1(this,R.style.AlertDialogStyle,list);
        dialogStyle1.show();
        dialogStyle1.setTitle("请选择");
        dialogStyle1.setOnItemClickEvent(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject object = list.get(position);
                    if(object.has("content")) {
                        Toast.makeText(MainActivity.this, "选择"+object.getString("content"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showCheckBoxDialog(){
        final List<JSONObject> list = new ArrayList<>();
        try {
            for (int i=0;i<5;i++){
                JSONObject object = new JSONObject();
                object.put("content","数据"+(i+1));
                object.put("isChecked",false);
                list.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CheckBoxDialog dialog = new CheckBoxDialog(this,R.style.AlertDialogStyle,list);
        dialog.show();
        dialog.setTitle("请选择");
        dialog.setNegBtn("取消");
        dialog.setPosBtn("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip = "选择了";
                for (int i=0;i<list.size();i++){
                    try {
                        JSONObject object = list.get(i);
                        if (object.getBoolean("isChecked")) {
                              tip = tip+" "+object.get("content");
                        }
                    }catch (Exception e){}
                }
                Toast.makeText(MainActivity.this,tip,Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 显示日期dialog
     *
     * @param flag  监听标识
     * @param showStyle 显示风格
     */
    public void showDatePickerDialog(int flag, int showStyle) {
        pickDateDialog = new PickDateDialog(this, flag, showStyle).buliders();
        pickDateDialog.setOnPickerListener(this);
        pickDateDialog.show();
    }

    @Override
    public void onPicked(int flag, String time) {
        pickDateDialog.dismiss();
        switch (flag){
            case 1:
                Toast.makeText(MainActivity.this,time,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
