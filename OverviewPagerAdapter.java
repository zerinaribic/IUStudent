package ius.iustudent.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class OverviewPagerAdapter extends FragmentStatePagerAdapter {
    public OverviewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
       /* switch (position){
            case 0:
                return new TodayScheduleFragment();


        }
        return null;*/
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
