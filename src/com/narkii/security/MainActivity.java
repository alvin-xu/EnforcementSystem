package com.narkii.security;

import com.narkii.security.common.FragmentsFactory;
import com.narkii.security.common.Constants;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

public class MainActivity extends FragmentActivity implements TabListener{

	public static final String TAG="MAIN";
	private ViewPager viewPager;
	
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private ViewStub informationVS,enforcementVS,lawVS,userVS;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar=getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		MyViewPaperAdapter paperAdapter=new MyViewPaperAdapter(getSupportFragmentManager());
		viewPager=(ViewPager) findViewById(R.id.pager_main);
		viewPager.setAdapter(paperAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			//侧滑监听器
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
			}
			
		});
		
		for(int i=0;i<paperAdapter.getCount();i++){
			actionBar.addTab(actionBar.newTab().setText(paperAdapter.getPageTitle(i)).setTabListener(this));
		}
		
		drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer){
			//当drawer打开和关闭时，用于改变actionbar中的菜单选项
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();			
			}
			
		};
		drawerLayout.setDrawerListener(drawerToggle);
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
		if(drawerToggle.onOptionsItemSelected(item)){
			//点击actionbar中的应用图标时可以调用此处，实现侧滑菜单滑出！
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}
	
	class MyViewPaperAdapter extends FragmentPagerAdapter{

		public MyViewPaperAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return FragmentsFactory.getMainFragment(arg0);
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
		switch (tab.getPosition()) {
		case 0:
			initInformationDrawer();
			informationVS.setVisibility(View.VISIBLE);
			break;
		case 1:
			initEnforcementDrawer();
			enforcementVS.setVisibility(View.VISIBLE);
			break;
		case 2:
			initLawDrawer();
			lawVS.setVisibility(View.VISIBLE);
			break;
		case 3:
			initUserDrawer();
			userVS.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		switch (tab.getPosition()) {
		case 0:
			informationVS.setVisibility(View.GONE);
			break;
		case 1:
			enforcementVS.setVisibility(View.GONE);
			break;
		case 2:
			lawVS.setVisibility(View.GONE);
			break;
		case 3:
			userVS.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	void initInformationDrawer(){
		if(informationVS==null){
			informationVS=(ViewStub) findViewById(R.id.vs_information);
			View view=informationVS.inflate();
		}
	}
	void initEnforcementDrawer(){
		if(enforcementVS==null){
			enforcementVS=(ViewStub) findViewById(R.id.vs_enforcement);
			View view=enforcementVS.inflate();
		}
	}
	void initLawDrawer(){
		if(lawVS==null){
			lawVS=(ViewStub) findViewById(R.id.vs_law);
			View view=lawVS.inflate();
		}
	}
	void initUserDrawer(){
		if(userVS==null){
			userVS=(ViewStub) findViewById(R.id.vs_user);
			View view=userVS.inflate();
		}
	}
}
