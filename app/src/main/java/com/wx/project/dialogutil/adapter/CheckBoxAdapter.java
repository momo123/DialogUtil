package com.wx.project.dialogutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wx.project.dialogutil.R;

import org.json.JSONObject;
import java.util.List;

/**
 * @author WangXin
 */
public class CheckBoxAdapter extends BaseAdapter {

    List<JSONObject> listData;
    Context context;

    public CheckBoxAdapter(Context context,List<JSONObject> list ) {
        listData = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_route_search_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            JSONObject bean = listData.get(position);
            viewHolder.tvContent.setText(bean.getString("content"));
            if (bean.getBoolean("isChecked")) {
                viewHolder.ivCheckState.setImageResource(R.drawable.ic_checked);
            } else {
                viewHolder.ivCheckState.setImageResource(R.drawable.ic_uncheck);
            }

            viewHolder.ivCheckState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject dataBean = listData.get(position);
                    try {
                        if (dataBean.getBoolean("isChecked")) {
                            dataBean.put("isChecked",false);
                            notifyDataSetChanged();
                        } else {
                            dataBean.put("isChecked",true);
                            notifyDataSetChanged();
                        }
                    }catch (Exception e){}

                }
            });
        }catch (Exception e){

        }
        return view;
    }

    private class ViewHolder {
        TextView tvContent;
        ImageView ivCheckState;
        ViewHolder(View view) {
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            ivCheckState = (ImageView)view.findViewById(R.id.iv_check_state);
        }
    }
}
