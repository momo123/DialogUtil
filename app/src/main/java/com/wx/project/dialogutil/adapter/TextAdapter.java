package com.wx.project.dialogutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wx.project.dialogutil.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by WangXin on 2018/5/28.
 */

public class TextAdapter extends BaseAdapter{

    private List<JSONObject> dataList;
    private Context mContext;

    public TextAdapter(Context context, List<JSONObject> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_text_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
             JSONObject obj = dataList.get(position);
            if (obj.has("content")) {
                viewHolder.tvContent.setText(obj.get("content")+"");
            }else{
                viewHolder.tvContent.setText("未进行塞值处理");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder{
        private TextView tvContent;

        public ViewHolder(View view) {
            tvContent = (TextView)view.findViewById(R.id.tv_content);
        }
    }


}
