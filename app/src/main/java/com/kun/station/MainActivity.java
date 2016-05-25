package com.kun.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    String[] arr = {"系统首页", "岗位建设", "应急制度", "作业标准", "运输生产", "应急制度"};

    @Bind(R.id.left_menu_list)
    ListView leftMenuList;
    @Bind(R.id.detail_layout)
    FrameLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        leftMenuList.setAdapter(new ListAdapter());
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_menu, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.menu_txt);
            tv.setText(getItem(position));
            return convertView;
        }
    }
}
