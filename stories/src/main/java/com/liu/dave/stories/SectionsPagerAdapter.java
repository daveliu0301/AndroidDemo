package com.liu.dave.stories;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.view.PersonDetailFragment;

import java.util.ArrayList;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Person> mPersons = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return PersonDetailFragment.newInstance(mPersons.get(position));
    }

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPersons.get(position).getName();
    }

    public void setPersons(ArrayList<Person> persons) {
        mPersons = persons;
        notifyDataSetChanged();
    }
}
