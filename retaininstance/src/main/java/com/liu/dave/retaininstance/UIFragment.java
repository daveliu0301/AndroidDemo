package com.liu.dave.retaininstance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by LiuDong on 2016/5/30.
 */
public class UIFragment extends Fragment {
    private RetainedFragment mWorkFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Button button = (Button) view.findViewById(R.id.restart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWorkFragment.restart();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        mWorkFragment = (RetainedFragment) fm.findFragmentByTag("work");
        if (mWorkFragment == null) {
            mWorkFragment = new RetainedFragment();
            mWorkFragment.setTargetFragment(this, 0);
            fm.beginTransaction().add(mWorkFragment, "work").commit();
        }
    }
}
