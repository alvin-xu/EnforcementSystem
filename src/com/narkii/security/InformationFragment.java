package com.narkii.security;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class InformationFragment extends Fragment{
	private View view;
	private HVListView companyList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_information, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		companyList=(HVListView) getActivity().findViewById(android.R.id.list);
		companyList.mListHead=(LinearLayout) view.findViewById(R.id.company_header11);
		companyList.setAdapter(new CompanyDataAdapter());
	}

	class CompanyDataAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 30;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView=getActivity().getLayoutInflater().inflate(R.layout.company_info_item,null);
			}
			//初始化数据。
//			for(int i=0;i<9;i++){
//				((TextView)((ViewGroup)((ViewGroup)convertView).getChildAt(1)).getChildAt(i)).setText("textttttttttttttttttttttt555555555 "+i);
//			}
			//校正（处理同时上下和左右滚动出现错位情况）
			View child = ((ViewGroup) convertView).getChildAt(2);//头2列不动，从第3列开始。
			int head = companyList.getHeadScrollX();
			if (child.getScrollX() != head) {
				child.scrollTo(companyList.getHeadScrollX(), 0);
			}
			return convertView;
		}
		
	}
}


