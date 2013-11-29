package com.narkii.security;
 
import com.narkii.security.enforce.InspectActivity;
import com.narkii.security.enforce.RectifyActivity;
import com.narkii.security.enforce.ReviewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EnforcementFragment extends Fragment implements OnItemSelectedListener{
	private View view;
	private ListView enforceRecordsList;
	private ListView companyNameList;
	private TextView companyListTitle;
	private Spinner paperSpinner;
	private Button addPaperButton;
	
	private String [] temps={
			"晋江AAA公司",
			"晋江AAA公司",
			"晋江AAA公司",
			"晋江AAA公司",
			"晋江AAA公司",
			"晋江AAA公司",
			"晋江AAA公司"
	};
	private int selectedPaper=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_enforcement, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		enforceRecordsList=(ListView) view.findViewById(R.id.enforce_record_list);
//		enforceRecordsList.mListHead=(LinearLayout) view.findViewById(R.id.enforce_record_header);
		enforceRecordsList.setAdapter(new EnforceRecordsAdapter());
		
		companyNameList=(ListView) view.findViewById(R.id.list_company_name);
		companyListTitle=(TextView) view.findViewById(R.id.company_list_title);
		companyNameList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, temps));
		companyNameList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "You have selected "+temps[arg2], Toast.LENGTH_SHORT).show();
			}
			
		});
		
		paperSpinner=(Spinner) view.findViewById(R.id.enforce_paper_select);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(), R.array.spinner_paper_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		paperSpinner.setAdapter(adapter);
		paperSpinner.setOnItemSelectedListener(this);
		
		addPaperButton=(Button) view.findViewById(R.id.button_paper_add);
		addPaperButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (selectedPaper) {
				case 0:
					Intent inspect=new Intent(getActivity(), InspectActivity.class);
					startActivity(inspect);
					break;
				case 1:
					Intent rectify=new Intent(getActivity(), RectifyActivity.class);
					startActivity(rectify);
					break;
				case 2:
					Intent review=new Intent(getActivity(), ReviewActivity.class);
					startActivity(review);
					break;
				default:
					break;
				}
			}
		});
	}
	
	class EnforceRecordsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 40;
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
				convertView=getActivity().getLayoutInflater().inflate(R.layout.enforce_record_item,null);
			}
			//初始化数据。
//			for(int i=0;i<9;i++){
//				((TextView)((ViewGroup)((ViewGroup)convertView).getChildAt(1)).getChildAt(i)).setText("textttttttttttttttttttttt555555555 "+i);
//			}
			//校正（处理同时上下和左右滚动出现错位情况）
//			View child = ((ViewGroup) convertView).getChildAt(2);//头2列不动，从第3列开始。
//			int head = enforceRecordsList.getHeadScrollX();
//			if (child.getScrollX() != head) {
//				child.scrollTo(enforceRecordsList.getHeadScrollX(), 0);
//			}
			return convertView;
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		selectedPaper=arg2;
		Toast.makeText(getActivity(), "You have selected ***"+arg2, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
