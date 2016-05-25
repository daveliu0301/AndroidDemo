package com.liu.dave.stories.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liu.dave.stories.R;
import com.liu.dave.stories.databinding.FragmentMainBinding;
import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.viewmodel.PersonDetailViewModel;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class PersonDetailFragment extends Fragment {
    private static final String EXTRA_PERSON = "EXTRA_PERSON";

    public static PersonDetailFragment newInstance(Person person) {
        PersonDetailFragment fragment = new PersonDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PERSON, person);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_main, container, false);
        binding.setViewModel(new PersonDetailViewModel((Person) getArguments().getParcelable(EXTRA_PERSON)));
        return binding.getRoot();
    }
}
