package com.liu.dave.stories.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.view.MainActivity;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class PersonDetailViewModel {
    private Context mContext;
    private Person mPerson;

    public PersonDetailViewModel(Context context, Person person) {
        mContext = context;
        mPerson = person;
    }

    public void jumpToNext(View view) {
        mContext.startActivity(new Intent(mContext, MainActivity.class));
    }

    public String getName() {
        return mPerson.getName();
    }

    public int getAge() {
        return mPerson.getAge();
    }

    public String getId() {
        return mPerson.getId().toString();
    }
}
