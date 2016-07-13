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
import com.kun.station.db.DbManager;
import com.kun.station.model.SubMenuModel;
import com.kun.station.util.FileUtil;

import java.util.List;

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
    List<SubMenuModel> list;
    String menu;
    int selectedPosition = 0;
    SubMenuAdapter subMenuAdapter;

    int[] drawbles = {R.drawable.post_environment_ico, R.drawable.post_customization_ico, R.drawable.post_duty_ico, R.drawable.post_personlist_ico,
            R.drawable.post_environment_ico, R.drawable.post_customization_ico, R.drawable.post_duty_ico, R.drawable.post_personlist_ico, R.drawable.post_customization_ico};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
        ButterKnife.bind(this, view);
        initData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                subMenuAdapter.notifyDataSetChanged();
                if (list.get(position).getType() == 101) {
                    Bundle b = new Bundle();
                    b.putString("teamConcept", list.get(position).getTeamConcept());
                    showFragment(TeamCultureFragment.class, b);
                } else {
                    Bundle b = new Bundle();
                    b.putString(SebMenuFileFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/" + menu + "/" + list.get(position).getTitle());
                    showFragment(SebMenuFileFragment.class, b);
                }
            }
        });
        return view;
    }

    private void initData() {
        subMenuAdapter = new SubMenuAdapter();
        listview.setAdapter(subMenuAdapter);
        menu = getStringParam("menuTitle");
        list = DbManager.getInstace(getContext()).getSubMenu(getIntParam("menuId"));
        if (list.get(0).getType() == 101) {
            Bundle b = new Bundle();
            b.putString("teamConcept", list.get(0).getTeamConcept());
            showFragment(TeamCultureFragment.class, b);
        } else {
            Bundle b = new Bundle();
            b.putString(SebMenuFileFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/" + menu + "/" + list.get(0).getTitle());
            showFragment(SebMenuFileFragment.class, b);
        }
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
            if (list == null) {
                return 0;
            }
            return list.size();
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
            tv.setText(list.get(position).getTitle());
            if (position == selectedPosition) {
                view.setBackgroundResource(R.drawable.img_pda_dirselect);
            } else {
                view.setBackgroundResource(0);
            }
            return convertView;
        }
    }
}
