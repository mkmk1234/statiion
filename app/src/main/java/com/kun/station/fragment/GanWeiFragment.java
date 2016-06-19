package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.util.FileUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class GanWeiFragment extends BaseFragment {


    @Bind(R.id.left)
    LinearLayout left;
    @Bind(R.id.layout)
    FrameLayout layout;
    @Bind(R.id.listview)
    ListView listview;
    int selectedPosition = 0;
    SubMenuAdapter subMenuAdapter;
    //android:background="@drawable/img_pda_dirselect"

    String[] data = {"车站状况", "车站精神", "安全文化", "经营文化", "廉政文化", "车间文化", "班组文化基本原则", "车间班组文化理念", "上海铁路局员工守则"};
    int[] drawbles = {R.drawable.post_environment_ico, R.drawable.post_customization_ico, R.drawable.post_duty_ico, R.drawable.post_personlist_ico,
            R.drawable.post_environment_ico, R.drawable.post_customization_ico, R.drawable.post_duty_ico, R.drawable.post_personlist_ico, R.drawable.post_customization_ico};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
        ButterKnife.bind(this, view);
        subMenuAdapter = new SubMenuAdapter();
        listview.setAdapter(subMenuAdapter);
        Bundle b = new Bundle();
        b.putString(SebMenuFileFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/企业简介/" + data[0]);
        showFragment(SebMenuFileFragment.class, b);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                subMenuAdapter.notifyDataSetChanged();
                if (position == 7) {
                    showFragment(TeamCultureFragment.class, null);
                } else {
                    Bundle b = new Bundle();
                    b.putString(SebMenuFileFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/企业简介/" + data[position]);
                    showFragment(SebMenuFileFragment.class, b);
                }
            }
        });
        return view;
    }

    private void showFragment(Class<?> clss, Bundle b) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.layout, Fragment.instantiate(getActivity(), clss.getName(), b));

        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class SubMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_submenu, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.tv_submenu);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_submenu);
            View view = convertView.findViewById(R.id.layout_submenu);
            iv.setImageResource(drawbles[position]);
            tv.setText(data[position]);
            if (position == selectedPosition) {
                view.setBackgroundResource(R.drawable.img_pda_dirselect);
            } else {
                view.setBackgroundResource(0);
            }
            return convertView;
        }
    }
}
