package com.example.ijsbeacons;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	PagerAdapter pa;
	ViewPager pager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setTitle("ijsBeacons");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
        
        pa = new MyPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(pa);
        
    	 // Set up the action bar.
 		final ActionBar actionBar = getActionBar();
 		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

 		// When swiping between different sections, select the corresponding
 		// tab. We can also use ActionBar.Tab#select() to do this if we have
 		// a reference to the Tab.
 		pager.setOnPageChangeListener(
			new ViewPager.SimpleOnPageChangeListener()
	 		{
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);
				}
			}
		);

 		// For each of the sections in the app, add a tab to the action bar.
 		actionBar.addTab(actionBar.newTab().setText("A").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("B").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("C").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("D").setTabListener(this));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos)
            {
	            case 0: return UserStatistics.newInstance("UserStatistics, Instance 1");
	            case 1: return Toplist.newInstance("Toplist, Instance 1");
	            case 2: return Toplist.newInstance("Toplist, Instance 2");
	            case 3: return Toplist.newInstance("Toplist, Instance 3");
	            default: return Toplist.newInstance("Toplist, Default");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }       
    }

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		pager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}