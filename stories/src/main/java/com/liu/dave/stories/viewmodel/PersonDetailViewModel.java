package com.liu.dave.stories.viewmodel;

import com.liu.dave.stories.model.Person;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class PersonDetailViewModel {
    private Person mPerson;

    public PersonDetailViewModel(Person person) {
        mPerson = person;
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
