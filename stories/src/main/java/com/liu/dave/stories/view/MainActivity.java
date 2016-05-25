package com.liu.dave.stories.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.liu.dave.stories.LeftDrawerAdapter;
import com.liu.dave.stories.R;
import com.liu.dave.stories.SectionsPagerAdapter;
import com.liu.dave.stories.databinding.ActivityMainBinding;
import com.liu.dave.stories.model.Person;
import com.liu.dave.stories.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataChangeListener, LeftDrawerAdapter.OnItemClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LeftDrawerAdapter mLeftDrawerAdapter;
    private ViewPager mViewPager;
    private PagerTitleStrip mStrip;
    private DrawerLayout mDrawer;
    private RecyclerView mLeftDrawer;
    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel(this);
        mBinding.setViewModel(mViewModel);

        mStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (RecyclerView) findViewById(R.id.left_drawer);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mLeftDrawerAdapter = new LeftDrawerAdapter(this, this);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mLeftDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLeftDrawer.setAdapter(mLeftDrawerAdapter);


    }

    @Override
    public void onDataChanged(final ArrayList<Person> persons) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 15);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSectionsPagerAdapter.setPersons(persons);
                            mLeftDrawerAdapter.setPersons(persons);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void onItemClick(int position) {
        mViewPager.setCurrentItem(position);
        mDrawer.closeDrawer(mLeftDrawer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
