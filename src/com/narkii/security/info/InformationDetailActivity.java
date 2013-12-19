package com.narkii.security.info;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.common.MyFragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

public class InformationDetailActivity extends FragmentActivity implements TabListener{
	public static final String TAG="InfoDetailActivity";
//	private TabHost tabHost;
	private ViewPager viewPager;
	private Fragment f1,f2,f3,f4,f5;
	
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
		
		Log.d(TAG, "receiver id:"+getIntent().getLongExtra("id",0));
	}
	
	class MyViewAdapter extends MyFragmentPagerAdapter{

		public MyViewAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
			Log.d(TAG, "adpter destroy "+position);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			Log.d(TAG, "adapter instantiate "+position);
			return super.instantiateItem(container, position);
			
		}

		/**只有在初次加载Fragment时才调用*/
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "get item "+arg0);
			switch (arg0) {
				case 0:
					if(f1==null)
						f1=new BaseDataFragment();
					return f1; 
				case 1:
					if(f2==null)
						f2=new SecureDataFragment();
					return  f2;
				case 2:
					if(f3==null)
						f3=new LicenseInfoFragment();
					return f3;
				case 3:
					if(f4==null)
						f4=new RecordFragment();
					return f4;
				case 4:
					if(f5==null)
						f5=new RemarkFragment();
					return f5; 
				default:
					return null;
			}
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
