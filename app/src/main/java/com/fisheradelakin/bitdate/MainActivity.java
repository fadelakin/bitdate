package com.fisheradelakin.bitdate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final int SIGN_IN_REQUEST = 10;

    private ImageView mChoosingIcon;
    private ImageView mMatchesIcon;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        mChoosingIcon = (ImageView) findViewById(R.id.logo_icon);
        mChoosingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mMatchesIcon = (ImageView) findViewById(R.id.chat_icon);
        mMatchesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        mChoosingIcon.setSelected(true);
        toggleColor(mChoosingIcon);
        toggleColor(mMatchesIcon);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(UserDataSource.getCurrentUser() == null) {
            Intent i = new Intent(this, SignInActivity.class);
            startActivityForResult(i, SIGN_IN_REQUEST);
        }

        updateDrawer();
    }

    private void updateDrawer() {
        ImageView userPhoto = (ImageView) findViewById(R.id.user_photo);
        Picasso.with(this).load(UserDataSource.getCurrentUser().getLargePictureURL()).into(userPhoto);
        TextView userName = (TextView) findViewById(R.id.user_name);
        userName.setText(UserDataSource.getCurrentUser().getFirstName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST && resultCode == RESULT_OK) {
            updateDrawer();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mChoosingIcon.setSelected(!mChoosingIcon.isSelected());
        mMatchesIcon.setSelected(!mMatchesIcon.isSelected());
        toggleColor(mChoosingIcon);
        toggleColor(mMatchesIcon);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void toggleColor(ImageView v) {
        if(v.isSelected()) {
            v.setColorFilter(Color.WHITE);
        } else {
            v.setColorFilter(getResources().getColor(R.color.primary_dark_blue));
        }
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new ChoosingFragment();
                case 1:
                    return new MatchesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
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
