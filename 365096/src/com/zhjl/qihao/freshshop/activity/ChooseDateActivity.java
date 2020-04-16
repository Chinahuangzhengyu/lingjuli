package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.CalendarView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.util.NewHeaderBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseDateActivity extends VolleyBaseActivity {
    @BindView(R.id.img_date_left)
    ImageView imgDateLeft;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.img_date_right)
    ImageView imgDateRight;
    @BindView(R.id.gv_time)
    GridViewForScrollView gvTime;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.cv_date)
    CalendarView cvDate;
    private int CurrentMonth;
    public static final int TIME_DATA_RESULT = 0x558;


    private String[] timerLists = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00","21:00"};
    private TimerChooseAdapter timerChooseAdapter;
    private String currentCheckTime;
    private String currentYear;
    private String currentMonth;
    private String currentDay;
    private long timeInMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "时间选择", this);
        timerChooseAdapter = new TimerChooseAdapter();
        gvTime.setAdapter(timerChooseAdapter);
        currentCheckTime = timerLists[0];
        gvTime.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < timerChooseAdapter.getCount(); i++) {
                if (i == position) {
                    TextView textView = (TextView) parent.getChildAt(i);
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_19));
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    currentCheckTime = timerLists[i];
                } else {
                    TextView textView = (TextView) parent.getChildAt(i);
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_white_19));
                    textView.setTextColor(Color.parseColor("#1F1F1F"));
                }
            }
        });


        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        final int currMouth = calendar.get(Calendar.MONTH) + 1;
        tvDate.setText(currYear + "年" + currMouth + "月");

        cvDate.setOnYearChangeListener(new CalendarView.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {
                tvDate.setText(year + "年" + CurrentMonth + "月");
            }
        });
        cvDate.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                CurrentMonth = month;
                tvDate.setText(year + "年" + month + "月");
            }
        });

        currentDay = String.valueOf(cvDate.getCurDay());
        currentMonth = String.valueOf(cvDate.getCurMonth());
        currentYear = String.valueOf(cvDate.getCurYear());
        cvDate.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
                currentDay = String.valueOf(calendar.getDay());
                currentMonth = String.valueOf(calendar.getMonth());
                currentYear = String.valueOf(calendar.getYear());
                timeInMillis = calendar.getTimeInMillis();
            }
        });
    }


    @OnClick({R.id.img_date_left, R.id.img_date_right, R.id.tv_cancel, R.id.tv_sure, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_date_left:
                cvDate.getMonthViewPager().setCurrentItem(cvDate.getMonthViewPager().getCurrentItem() - 1);
                break;
            case R.id.img_date_right:
                cvDate.getMonthViewPager().setCurrentItem(cvDate.getMonthViewPager().getCurrentItem() + 1);
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_sure:
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                try {
                    String checkTime = currentYear + "-" + currentMonth + "-" + currentDay + " " + currentCheckTime;
                    Date date = format.parse(checkTime);
                    long time = date.getTime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR_OF_DAY,1);
                    long afterData = calendar.getTime().getTime();
                    if (time > afterData) {
                        Intent intent = new Intent();
                        intent.putExtra("checkTime", checkTime);
                        setResult(TIME_DATA_RESULT, intent);
                        finish();
                    } else {
                        Toast.makeText(mContext, "请选择当前时间后1小时！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class TimerChooseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return timerLists.length;
        }

        @Override
        public Object getItem(int position) {
            return timerLists[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.time_choose_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTimeChoose.setText(timerLists[position]);
            if (position == 0) {
                holder.tvTimeChoose.setTextColor(Color.parseColor("#FFFFFF"));
                holder.tvTimeChoose.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_19));
            } else {
                holder.tvTimeChoose.setTextColor(Color.parseColor("#1F1F1F"));
                holder.tvTimeChoose.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_white_19));
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_time_choose)
            TextView tvTimeChoose;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
