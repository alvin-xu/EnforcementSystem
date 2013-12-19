package com.narkii.security;

import java.util.List;

import com.narkii.security.common.Constants;
import com.narkii.security.common.MyFragmentPagerAdapter;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.InitDataBase;
import com.narkii.security.data.EnforceSysContract.Document;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements TabListener{

	public static final String TAG="MainActivity";
	private ViewPager viewPager;
	private Fragment f1,f2,f3,f4;
	
//	public Fragment getFragment(int index){
//		switch (index) {
//		case 0:
//			return f1;
//		case 1:
//			return f2;
//		case 2:
//			return f3;
//		case 3:
//			return f4;
//		}
//		return null;
//	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//当应用退出时关闭数据库
		DbOperations.getInstance(MainActivity.this).close();
		Log.d(TAG, "on destroy");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		super.onBackPressed();
		Log.d(TAG, "entry count:"+getFragmentManager().getBackStackEntryCount()+f2.getChildFragmentManager().getBackStackEntryCount());
		if(f2.getChildFragmentManager().getBackStackEntryCount()==0)
			finish();
		else 
			return;
		
		
	}

/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && getFragmentManager().getBackStackEntryCount()==1){
			
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("确定要退出？")
					.setMessage("\n请确认已保存相关信息后再退出！\n");
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}*/

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar=getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionBar.setHomeButtonEnabled(true);
//		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setLogo(R.drawable.nav_logo);
		actionBar.setDisplayShowTitleEnabled(false);
		
		MyViewPaperAdapter paperAdapter=new MyViewPaperAdapter(getSupportFragmentManager());
		viewPager=(ViewPager) findViewById(R.id.pager_main);
		viewPager.setAdapter(paperAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			//侧滑监听器
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
//				if(position==1)
//				getSupportFragmentManager().beginTransaction()
//				.replace(R.id.enforce_record, new EnforcementFragment())
//				.commit();
			}
			
		});
		
		for(int i=0;i<paperAdapter.getCount();i++){
			actionBar.addTab(actionBar.newTab().setText(paperAdapter.getPageTitle(i)).setTabListener(this));
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InitDataBase.init(MainActivity.this);
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
			case R.id.action_review://复查

			case R.id.action_exchange://换证
				Log.d(TAG, "click huanzheng");
				getActionBar().setSelectedNavigationItem(1);
				viewPager.setCurrentItem(1);
//				((EnforcementFragment)f2).reLoadData(item.getItemId());
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
	}
	
	class MyViewPaperAdapter extends MyFragmentPagerAdapter{

		public MyViewPaperAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "get fragment item "+arg0);
			switch (arg0) {
				case 0:
					if(f1==null){
						f1=new InformationFragment();
					}
					return f1;
				case 1:
					if(f2==null){
						f2=new EnforcementContainer();
					}
					return f2;
				case 2:
					if(f3==null)
						f3=new LawFragment();
					return f3;
				case 3:
					if(f4==null)
						f4=new UserFragment();
					return f4;
				default:
					break;
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Constants.MAIN_PAPER_NUMS;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return Constants.MAIN_PAPER_TITLES[position];
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// 点击tab的监听器
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
