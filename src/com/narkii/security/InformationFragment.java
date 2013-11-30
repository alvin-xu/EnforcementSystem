package com.narkii.security;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;
import com.narkii.security.data.EnforceSysContract.SafetyPermitType;
import com.narkii.security.info.InformationDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class InformationFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="InformationFragment";
	private View view;
	private ListView companyList;
	private Button addButton;
	private CompanyDataAdapter listInfoAdapter;
	private Spinner enterpriseTypeSpinner,permitTypeSpinner;
	private SimpleCursorAdapter enterpriseTypeAdapter,permitTypeAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_information, null);
		return view;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		companyList=(ListView) view.findViewById(R.id.company_info_list);
		enterpriseTypeSpinner=(Spinner) view.findViewById(R.id.spinner_company_type);
		permitTypeSpinner=(Spinner) view.findViewById(R.id.spinner_permission_type);
		addButton=(Button) view.findViewById(R.id.button_add);
		
		listInfoAdapter=new CompanyDataAdapter(getActivity(), null, false);
		companyList.setAdapter(listInfoAdapter);
		
		enterpriseTypeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item,null , new String[]{EnterpriseType.COLUMN_NAME}, new int[]{android.R.id.text1});
		enterpriseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		enterpriseTypeSpinner.setAdapter(enterpriseTypeAdapter);
		
		permitTypeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item,null , new String[]{SafetyPermitType.COLUMN_NAME}, new int[]{android.R.id.text1});
		permitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		permitTypeSpinner.setAdapter(permitTypeAdapter);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent infoIntent=new Intent(getActivity(), InformationDetailActivity.class);
				startActivity(infoIntent);
			}
		});
		getLoaderManager().initLoader(Constants.LIST_ENTERPRISE_INFO_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_SAFETY_PERMIT_TYPE_ID, null, this);
	}
	class CompanyDataAdapter extends CursorAdapter{
		private  class ViewHolder{
			TextView name;
			TextView area;
			TextView address;
			TextView responser;
			TextView phone;
			TextView date;
			int nameIndex;
			int areaIndex;
			int addressIndex;
			int responserIndex;
			int phoneIndex;
			int dateIndex;
		}
		public CompanyDataAdapter(Context context, Cursor c,
				boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void bindView(View arg0, Context arg1, Cursor cursor) {
			// TODO Auto-generated method stub
			ViewHolder holder=(ViewHolder) arg0.getTag();
			holder.name.setText(cursor.getString(holder.nameIndex));
			holder.area.setText(cursor.getString(holder.areaIndex));
			holder.address.setText(cursor.getString(holder.addressIndex));
			holder.responser.setText(cursor.getString(holder.responserIndex));
			holder.phone.setText(cursor.getString(holder.phoneIndex));
			holder.date.setText(cursor.getString(holder.dateIndex));
		}

		@Override
		public View newView(Context arg0, Cursor cursor, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view=LayoutInflater.from(arg0).inflate(R.layout.company_info_item,null);
			ViewHolder holder=new ViewHolder();
			
			holder.name=(TextView) view.findViewById(R.id.text_company_name);
			holder.area=(TextView) view.findViewById(R.id.text_company_area);
			holder.address=(TextView) view.findViewById(R.id.text_company_address);
			holder.responser=(TextView) view.findViewById(R.id.text_responsor);
			holder.phone=(TextView) view.findViewById(R.id.text_phone1);
			holder.date=(TextView) view.findViewById(R.id.text_valid_date);
			
			holder.nameIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_NAME);
			holder.areaIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_AREA);
			holder.addressIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_ADDRESS);
			holder.responserIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_CHARGE);
			holder.phoneIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_CHARGE_PHONE);
			holder.dateIndex=cursor.getColumnIndexOrThrow(Enterprise.COLUMN_VALID_DATE);
			
			view.setTag(holder);
			
			return view;
		}
		
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		// TODO Auto-generated method stub
		DbCursorLoader cursorLoader = null;
		
		if(id==Constants.LIST_ENTERPRISE_INFO_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations=DbOperations.getInstance(getActivity());
					Cursor cursor=operations.query(Enterprise.TABLE_NAME, null, null, null);
					return cursor;
				}
			};
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations=DbOperations.getInstance(getActivity());
					Cursor cursor=operations.query(EnterpriseType.TABLE_NAME, null, null, null);
					return cursor;
				}
			};
		}else if(id==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations=DbOperations.getInstance(getActivity());
					Cursor cursor=operations.query(SafetyPermitType.TABLE_NAME, null, null, null);
					return cursor;
				}
			};
		}
		
		Log.d("APPPP", "on create load");
		
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int id=loader.getId();
		if(id==Constants.LIST_ENTERPRISE_INFO_ID){
			listInfoAdapter.swapCursor(cursor);
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			enterpriseTypeAdapter.swapCursor(cursor);
		}else if(id==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			permitTypeAdapter.swapCursor(cursor);
		}
		Log.d("APPPP", "on load finished");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		int id=loader.getId();
		if(id==Constants.LIST_ENTERPRISE_INFO_ID){
			listInfoAdapter.swapCursor(null);
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			enterpriseTypeAdapter.swapCursor(null);
		}else if(id==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			permitTypeAdapter.swapCursor(null);
		}
	}
}


