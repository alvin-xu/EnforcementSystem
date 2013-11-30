package com.narkii.security;
 
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.DocumentType;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;
import com.narkii.security.enforce.InspectActivity;
import com.narkii.security.enforce.RectifyActivity;
import com.narkii.security.enforce.ReviewActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
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

public class EnforcementFragment extends Fragment implements OnItemSelectedListener, LoaderCallbacks<Cursor>{
	public static final String TAG="EnforcementFragment";
	private View view;
	private ListView enforceRecordsList;
	private ListView companyNameList;
	private TextView companyListTitle;
	private Spinner paperSpinner,enterpriseTypeSpinner;
	private Button addPaperButton;
	private SimpleCursorAdapter paperAdapter,enterpriseTypeAdapter;
	
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		enforceRecordsList=(ListView) view.findViewById(R.id.enforce_record_list);
		companyNameList=(ListView) view.findViewById(R.id.list_company_name);
		companyListTitle=(TextView) view.findViewById(R.id.company_list_title);
		enterpriseTypeSpinner=(Spinner) view.findViewById(R.id.spinner_company_type);
		paperSpinner=(Spinner) view.findViewById(R.id.enforce_paper_select);
		
		enforceRecordsList.setAdapter(new EnforceRecordsAdapter());
		companyNameList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, temps));
		companyNameList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "You have selected "+temps[arg2], Toast.LENGTH_SHORT).show();
			}
			
		});
		
//		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(), R.array.spinner_paper_type, android.R.layout.simple_spinner_item);
//	
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		paperSpinner.setAdapter(adapter);
		
		paperAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{DocumentType.COLUMN_NAME},new int[]{android.R.id.text1});
		paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		paperSpinner.setAdapter(paperAdapter);
		paperSpinner.setOnItemSelectedListener(this);
		
		enterpriseTypeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{EnterpriseType.COLUMN_NAME},new int[]{android.R.id.text1});
		enterpriseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		enterpriseTypeSpinner.setAdapter(enterpriseTypeAdapter);
		
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
		
		getLoaderManager().initLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_DOCUMENT_TYPE_ID, null, this);
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		// TODO Auto-generated method stub
		Log.d("APP", " id+"+id);
		DbCursorLoader dbLoader=null;
		if(id==Constants.SPINNER_DOCUMENT_TYPE_ID){
			dbLoader=new DbCursorLoader(getActivity()) {
			
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations=DbOperations.getInstance(getActivity());
					Cursor cursor=operations.query(DocumentType.TABLE_NAME, null, null, null);
					Log.d("APP", "paper count "+cursor.getCount());
					return cursor;
				}
			};
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			dbLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations=DbOperations.getInstance(getActivity());
					Cursor cursor=operations.query(EnterpriseType.TABLE_NAME, null, null, null);
					Log.d("APP", "paper count "+cursor.getCount());
					return cursor;
				}
			};
		}
		return dbLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int id=loader.getId();
		if(id==Constants.SPINNER_DOCUMENT_TYPE_ID){
			paperAdapter.swapCursor(cursor);
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			enterpriseTypeAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		int id=loader.getId();
		if(id==Constants.SPINNER_DOCUMENT_TYPE_ID){
			paperAdapter.swapCursor(null);
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			enterpriseTypeAdapter.swapCursor(null);
		}
	}
}
