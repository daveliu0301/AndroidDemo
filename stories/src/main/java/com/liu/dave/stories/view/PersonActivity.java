package com.liu.dave.stories.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.liu.dave.stories.LeftDrawerAdapter;
import com.liu.dave.stories.R;
import com.liu.dave.stories.SectionsPagerAdapter;
import com.liu.dave.stories.databinding.ActivityPersonBinding;
import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.viewmodel.MainViewModel;

import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity implements MainViewModel.DataChangeListener, LeftDrawerAdapter.OnItemClickListener {
    private static final String EXTRA_PERSON_LIST = "EXTRA_PERSON_LIST";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LeftDrawerAdapter mLeftDrawerAdapter;
    private ViewPager mViewPager;
    private PagerTitleStrip mStrip;
    private DrawerLayout mDrawer;
    private RecyclerView mLeftDrawer;
    private ActivityPersonBinding mBinding;
    private MainViewModel mViewModel;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    private ArrayList<Person> mPersons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_person);
        mViewModel = new MainViewModel(this);
        mBinding.setViewModel(mViewModel);

        mActionBar = getSupportActionBar();

        showNotification();

        mStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (RecyclerView) findViewById(R.id.left_drawer);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mLeftDrawerAdapter = new LeftDrawerAdapter(this, this);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mLeftDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLeftDrawer.setAdapter(mLeftDrawerAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActionBar.setTitle(R.string.drawer_opened_title);
                invalidateOptionsMenu();// creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mActionBar.setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    private void showNotification() {
        Intent detailIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(detailIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setContentIntent(pendingIntent)
                .setContentTitle("title")
                .setContentText("text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);

    }

    @Override
    public void onDataChanged(final ArrayList<Person> persons) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 1 );

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonActivity.this, "load data success", Toast.LENGTH_SHORT).show();
                            setData(persons);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void setData(ArrayList<Person> persons) {
        mPersons = persons;
        mSectionsPagerAdapter.setPersons(mPersons);
        mLeftDrawerAdapter.setPersons(mPersons);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_PERSON_LIST, mPersons);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Person> persons = savedInstanceState.getParcelableArrayList(EXTRA_PERSON_LIST);
        setData(persons);
    }

    @Override
    public void onItemClick(int position) {
        mViewPager.setCurrentItem(position);
        mDrawer.closeDrawer(mLeftDrawer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawer.removeDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawer.isDrawerOpen(mLeftDrawer);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
