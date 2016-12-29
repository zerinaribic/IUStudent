package ius.iustudent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ius.iustudent.adapters.OverviewPagerAdapter;

public class OverviewFragment extends Fragment {
    public OverviewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_layout, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new OverviewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.calendar_today_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.a_plus_white);
        return view;
    }
}
