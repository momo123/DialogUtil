package com.wx.project.dialogutil.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import com.wx.project.dialogutil.R;
import com.wx.project.dialogutil.widget.DateWheelView;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * 时间选择器
 * @author WangXin
 */

public class PickDateDialog extends Dialog {

    public final String TAG = "PickDateDialog";
    private String ms_wheel_Year;
    private String ms_wheel_Month;
    private String ms_wheel_Day;
    private String ms_wheel_hour;
    private String ms_wheel_minute;
    private Button btn_sure;
    private Button btn_cancel;
    private OnTimePickedListener listener;
    private int flag;
    private int showStyle;

    public static class ShowStyle{
        public static final int SHOW_DATA = 100;
        public static final int SHOW_TIME = 101;
        public static final int SHOW_DATA_TIME = 102;
    }

    /**
     *
     * @param context
     * @param flags 回调标识
     * @param showStyle  显示风格
     */
    public PickDateDialog(Context context, int flags, int showStyle) {
        super(context, R.style.AlertDialogStyle);
        setContentView(R.layout.dialog_date_picker);
        this.flag = flags;
        this.showStyle = showStyle;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT ;
        window.setAttributes(params);
    }

    public PickDateDialog buliders(){
        DateWheelView mWheelYear = (DateWheelView) //年
                findViewById(R.id.year_wheel_v);
        DateWheelView mWheelMonth = (DateWheelView) //月
                findViewById(R.id.month_wheel_v);
        final DateWheelView mWheelDay = (DateWheelView) //日
                findViewById(R.id.day_wheel_v);

        DateWheelView mWheelHour = (DateWheelView) //小时
                findViewById(R.id.hour_wheel_v);
        final DateWheelView mWheelMinute = (DateWheelView) //分钟
                findViewById(R.id.minute_wheel_v);

        btn_sure = (Button)findViewById(R.id.btn_sure);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        final Calendar c=Calendar.getInstance();
        ArrayList<String> yearlist = new ArrayList<String>();
        ArrayList<String> monthlist = new ArrayList<String>();
        ArrayList<String> daylist = new ArrayList<String>();
        ArrayList<String> hourlist = new ArrayList<String>();
        ArrayList<String> minutelist = new ArrayList<String>();
        ms_wheel_Year = c.get(Calendar.YEAR) +"";
        ms_wheel_Month = (c.get(Calendar.MONTH)+1)+"";

        mWheelYear.setVisibility(View.GONE);
        mWheelMonth.setVisibility(View.GONE);
        mWheelDay.setVisibility(View.GONE);
        mWheelHour.setVisibility(View.GONE);
        mWheelMinute.setVisibility(View.GONE);

        switch (showStyle){
            case ShowStyle.SHOW_DATA://显示日期
                mWheelYear.setVisibility(View.VISIBLE);
                mWheelMonth.setVisibility(View.VISIBLE);
                mWheelDay.setVisibility(View.VISIBLE);
                yearlist = setYears();
                monthlist = setMonth();
                daylist = setDay();
                break;
            case ShowStyle.SHOW_TIME://显示时间
                mWheelHour.setVisibility(View.VISIBLE);
                mWheelMinute.setVisibility(View.VISIBLE);
                hourlist = setHour();
                minutelist = setMinute();
                break;
            case ShowStyle.SHOW_DATA_TIME://显示 日期和时间
                mWheelYear.setVisibility(View.VISIBLE);
                mWheelMonth.setVisibility(View.VISIBLE);
                mWheelDay.setVisibility(View.VISIBLE);
                mWheelHour.setVisibility(View.VISIBLE);
                mWheelMinute.setVisibility(View.VISIBLE);
                yearlist = setYears();
                monthlist = setMonth();
                daylist = setDay();
                hourlist = setHour();
                minutelist = setMinute();
                break;
        }

        mWheelYear.setData(yearlist, DateWheelView.WheelType.YEAR);
        mWheelYear.setDefault(c.get(Calendar.YEAR) - 2000);


        mWheelMonth.setData(monthlist,DateWheelView.WheelType.MONTH);
        mWheelMonth.setDefault(c.get(Calendar.MONTH));


        mWheelDay.setData(daylist,DateWheelView.WheelType.DAY);
        mWheelDay.setDefault(c.get(Calendar.DAY_OF_MONTH)-1);

        mWheelHour.setData(hourlist, DateWheelView.WheelType.HOUR);
        mWheelHour.setDefault(c.get(Calendar.HOUR_OF_DAY));

        mWheelMinute.setData(minutelist, DateWheelView.WheelType.MINUTE);
        mWheelMinute.setDefault(c.get(Calendar.MINUTE));

        ms_wheel_hour = (c.get(Calendar.HOUR_OF_DAY))<10?"0"+(c.get(Calendar.HOUR)):(c.get(Calendar.HOUR_OF_DAY))+"";
        ms_wheel_minute = c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)+"";
        ms_wheel_Day = (c.get(Calendar.DAY_OF_MONTH))+"";


        //年
        mWheelYear.setOnSelectListener(new DateWheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                Log.d("selecting Wheel", text + "");
            }

            @Override
            public void endSelect(int id, String text) {
                Log.d("endSelect Wheel", text + "");
                ms_wheel_Year = text;
                //动态修改day
                mWheelDay.setData(setDay(),DateWheelView.WheelType.DAY);
                int a = getDaysByYearMonth(Integer.parseInt(ms_wheel_Year), Integer.parseInt(ms_wheel_Month));
                if (a < Integer.parseInt(ms_wheel_Day)){
                    mWheelDay.setDefault(a-1);
                    ms_wheel_Day = setDay().get(a-1);
                }else{
                    mWheelDay.setDefault(Integer.parseInt(ms_wheel_Day)-1);
                    ms_wheel_Day = setDay().get(Integer.parseInt(ms_wheel_Day)-1);
                }
            }
        });

        //月
        mWheelMonth.setOnSelectListener(new DateWheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                Log.d("selecting Wheel", text + "");
            }

            @Override
            public void endSelect(int id, String text) {
                Log.d("endSelect Wheel", text + "");
                ms_wheel_Month = text;
                //动态修改day
                mWheelDay.setData(setDay(),DateWheelView.WheelType.DAY);
                int a = getDaysByYearMonth(Integer.parseInt(ms_wheel_Year), Integer.parseInt(ms_wheel_Month));
                if (a < Integer.parseInt(ms_wheel_Day)){
                    mWheelDay.setDefault(a-1);
                    ms_wheel_Day = setDay().get(a-1);
                }else{
                    mWheelDay.setDefault(Integer.parseInt(ms_wheel_Day)-1);
                    ms_wheel_Day = setDay().get(Integer.parseInt(ms_wheel_Day)-1);
                }

            }
        });

        //日
        mWheelDay.setOnSelectListener(new DateWheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                Log.d("selecting Wheel", text + "");
            }

            @Override
            public void endSelect(int id, String text) {
                Log.d("endSelect Wheel", text + "");
                ms_wheel_Day = text;

            }
        });

        //小时
        mWheelHour.setOnSelectListener(new DateWheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                Log.d("selecting Wheel", text + "");
            }

            @Override
            public void endSelect(int id, String text) {
                Log.d("endSelect Wheel", text + "");
                ms_wheel_hour = text;
            }
        });

        //分钟
        mWheelMinute.setOnSelectListener(new DateWheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                Log.d("selecting Wheel", text + "");
            }

            @Override
            public void endSelect(int id, String text) {
                Log.d("endSelect Wheel", text + "");
                ms_wheel_minute = text;

            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(ms_wheel_Month) < 10) {
                    ms_wheel_Month = "0" + ms_wheel_Month;
                }
                if (Integer.parseInt(ms_wheel_Day) < 10) {
                    ms_wheel_Day = "0" + ms_wheel_Day;
                }
                switch (showStyle){
                    case ShowStyle.SHOW_DATA://显示日期
                        listener.onPicked(flag, ms_wheel_Year + "-" + ms_wheel_Month+"-" + ms_wheel_Day);
                        break;
                    case ShowStyle.SHOW_TIME://显示时间
                        listener.onPicked(flag, ms_wheel_hour + ":" + ms_wheel_minute);
                        break;
                    case ShowStyle.SHOW_DATA_TIME://显示 日期和时间
                        listener.onPicked(flag, ms_wheel_Year + "-" + ms_wheel_Month+"-" + ms_wheel_Day +" "+ms_wheel_hour + ":" + ms_wheel_minute);
                        break;
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

    public PickDateDialog(Context context) {
        super(context);
    }

    protected PickDateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<String> setMinute() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=0;i<60;i++){
            if (i<10) {
                list.add(String.valueOf("0"+i));
            }else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    private ArrayList<String> setHour() {
        ArrayList<String> list = new ArrayList<String>();
        int start = 0;
        for (int i = start; i <= 23; i++) {
            if (i < 10){
                list.add(String.valueOf("0"+i));
            }else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    private ArrayList<String> setMonth() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private ArrayList<String> setDay() {
        ArrayList<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        for (int i = 1; i <= getDaysByYearMonth(Integer.parseInt(ms_wheel_Year), Integer.parseInt(ms_wheel_Month)); i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private ArrayList<String> setYears() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 2000; i <= 2030; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private int paserDefault(String text, ArrayList<String> yearlist) {
        int result = 0;
        for (int i = 0; i < yearlist.size(); i++) {
            if (text.equalsIgnoreCase(yearlist.get(i))) {
                result = i;
            }
        }
        return result;
    }

    public void setOnPickerListener(OnTimePickedListener onTimePickedListener){
        this.listener = onTimePickedListener;
    }

    public interface OnTimePickedListener{
        public void onPicked(int flag, String time);
    }

    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
 }
