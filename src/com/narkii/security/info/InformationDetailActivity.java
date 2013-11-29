package com.narkii.security.info;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.common.FragmentsFactory;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class InformationDetailActivity extends FragmentActivity implements TabListener{
	public static final String TAG="InfoDetail";
//	private TabHost tabHost;
	private ViewPager viewPager;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_detail);
	
		final ActionBar actionBar=getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		MyViewAdapter viewAdapter=new MyViewAdapter(getSupportFragmentManager());
		viewPager=(ViewPager) findViewById(R.id.pager_info);
		viewPager.setAdapter(viewAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
			}
			
		});
		for(int i=0;i<viewAdapter.getCount();i++){
			actionBar.addTab(actionBar.newTab().setText(viewAdapter.getPageTitle(i)).setTabListener(this));
		}
		
	}
	
	class MyViewAdapter extends FragmentPagerAdapter{

		public MyViewAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return FragmentsFactory.getInfoFragment(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Constants.INFO_PAPER_NUMS;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return Constants.INFO_PAPER_TITLES[position];
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.info, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_info_save:
			Toast.makeText(this, "save the information", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_info_sync:
			Toast.makeText(this, "sync the information", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
