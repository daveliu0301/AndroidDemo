package com.liu.dave.stories.viewmodel;

import android.databinding.BaseObservable;

import com.liu.dave.stories.model.Person;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class LeftDrawerViewModel extends BaseObservable {
    private Person mPerson;

    public LeftDrawerViewModel(Person person) {
        mPerson = person;
    }

    public void setPerson(Person person) {
        this.mPerson = person;
        notifyChange();
    }

    public String getName() {
        return mPerson.getName();
    }
}
