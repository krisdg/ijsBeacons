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
 		actionBar.addTab(actionBar.newTab().setText("User").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("Koffie").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("Afstand").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("% gezien").setTabListener(this));
 		actionBar.addTab(actionBar.newTab().setText("Snelheid").setTabListener(this));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos)
        {
        	Fragment toReturn;
        	
            switch(pos)
            {
	            case 0:
	            	toReturn = UserStatistics.newInstance("UserStatistics, Instance 1");
	            	break;
	            case 1:
	            	toReturn = Toplist.newInstance("Toplist, Instance 1", "CoffeeMachine");
	            	break;
	            case 2:
	            	toReturn = Toplist.newInstance("Toplist, Instance 2", "WalkedDistance");
	            	break;
	            case 3:
	            	toReturn = Toplist.newInstance("Toplist, Instance 2", "SeenSurface");
	            	break;
	            case 4:
	            	toReturn = Toplist.newInstance("Toplist, Instance 3", "WalkingSpeed");
	            	break;
	            default:
	            	toReturn = Toplist.newInstance("Toplist, Default", "");
	            	break;
            }
            
        	return toReturn;
        }

        @Override
        public int getCount() {
            return 5;
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