package com.kun.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    String[] arr = {"系统首页", "岗位建设", "应急制度", "作业标准", "运输生产", "应急制度"};

    @Bind(R.id.left_menu_list)
    ListView leftMenuList;
    @Bind(R.id.detail_layout)
    FrameLayout detailLayout;

    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new ListAdapter();
        leftMenuList.setAdapter(mAdapter);
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectPosition(position);
                if (position == 2) {
                    showFragment(FileFragment.class);
                }
                if (position == 1) {
                    showFragment(GanWeiFragment.class);
                }
                if (position == 0) {
                    showFragment(HomeFragemnt.class);
                }
            }
        });
        showFragment(HomeFragemnt.class);

    }

    private void showFragment(Class<?> clss) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.detail_layout, Fragment.instantiate(this, clss.getName(), null));

        ft.commitAllowingStateLoss();
    }

    class ListAdapter extends BaseAdapter {
        private int selectPosition = 0;

        @Override
        public int getCount() {
            return arr.length;
        }

        @Override
        public String getItem(int position) {
            return arr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelectPosition(int position){
            selectPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_menu, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.menu_txt);
            tv.setText(getItem(position));
            if (position == selectPosition){
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(R.drawable.shape_button_select);
            } else {
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.GONE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(R.drawable.shape_button_normal);
            }
            return convertView;
        }
    }
}
