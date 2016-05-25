package com.liu.dave.stories.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by LiuDong on 2016/5/25.
 */
public class Person implements Parcelable {
    public static final ArrayList<Person> PERSON_LIST = new ArrayList<>();

    static {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String randomName = new StringBuilder("name").append(random.nextLong()).toString();
            PERSON_LIST.add(new Person(randomName, random.nextInt()));
        }
        Log.i(Person.class.getSimpleName(), PERSON_LIST.toString());
    }

    private String id = UUID.randomUUID().toString();
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.age = parcel.readInt();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(age);

    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int i) {
            return new Person[0];
        }
    };

}
