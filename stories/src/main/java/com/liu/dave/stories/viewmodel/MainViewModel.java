package com.liu.dave.stories.viewmodel;

import android.databinding.BaseObservable;

import com.liu.dave.stories.model.Person;

import java.util.ArrayList;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class MainViewModel extends BaseObservable {
    private ArrayList<Person> mPersons = new ArrayList<>();
    private DataChangeListener mListener;

    public MainViewModel(DataChangeListener listener) {
        mListener = listener;
        loadData();
    }

    public void loadData() {
        mPersons = Person.PERSON_LIST;
        notifyChange();
        mListener.onDataChanged(mPersons);

    }

    public interface DataChangeListener {
        void onDataChanged(ArrayList<Person> persons);
    }

}
