package com.narkii.security.info;


import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.Filing;
import com.narkii.security.data.EnforceSysContract.SafetyPermitType;
import com.narkii.security.data.EnforceSysContract.VarityType;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SecureDataFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="SecrueDataFragment";
	private View view;
	private Spinner safeTypeSpinner,certificateSpinner,f2TypeSpinner;
	private EditText code,issueDate,validDate,scope;
//		f1Rank,f1Office,f1No,f1IssueDate,f1ValidDate,
//		f2Varity,f2Flow,f2No,f2iDate,f2vDate,
//		f3No,f3Varity,f3Reserve,f3Rank,f3eDate,f3nDate,
//		f4FilingName,f4No,f4iDate,f4Version,f4fDate,f4rDate;
	private EditText[] textsArray =new EditText[22];
	
	private SimpleCursorAdapter safeTypeAdapter,f2TypeAdapter;
	private ArrayAdapter<CharSequence> certiAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_info_security, null);
		return view;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		
		certiAdapter= ArrayAdapter.createFromResource(
				getActivity(), R.array.spinner_certificate_situ,
				android.R.layout.simple_spinner_item);
		certiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		certificateSpinner.setAdapter(certiAdapter);
		
		safeTypeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{SafetyPermitType.COLUMN_NAME},new int[]{android.R.id.text1});
		safeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		safeTypeSpinner.setAdapter(safeTypeAdapter);
		
		f2TypeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{VarityType.COLUMN_NAME},new int[]{android.R.id.text1});
		f2TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		f2TypeSpinner.setAdapter(f2TypeAdapter);
		
		getLoaderManager().initLoader(Constants.SPINNER_SAFETY_PERMIT_TYPE_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_VARITY_TYPE_ID, null, this);
		
		long id=getActivity().getIntent().getLongExtra("id",0);
		if(id==0){
			
		}else{
			Bundle bundle=new Bundle();
			bundle.putLong("id", id);
			getLoaderManager().restartLoader(Constants.ENTERPRISE_INFO_ID, bundle, this);
			getLoaderManager().restartLoader(Constants.FILING_ID, bundle, this);
		}
	}
	
	private void initViews(){
		safeTypeSpinner=(Spinner) view.findViewById(R.id.spinner_secure_type);
		certificateSpinner=(Spinner) view.findViewById(R.id.spinner_certification_situ);
		f2TypeSpinner=(Spinner) view.findViewById(R.id.spinner_f2_varity_sort);
		
		textsArray[0]=(EditText)view.findViewById(R.id.text_f1_secure_rank);
		textsArray[1]=(EditText)view.findViewById(R.id.text_f1_office_license);
		textsArray[2]=(EditText)view.findViewById(R.id.text_f1_standard_code);
		textsArray[3]=(EditText)view.findViewById(R.id.text_f1_date_deliver);
		textsArray[4]=(EditText)view.findViewById(R.id.text_f1_date_valid);
		textsArray[5]=(EditText)view.findViewById(R.id.text_f2_varity);
		textsArray[6]=(EditText)view.findViewById(R.id.text_f2_flow);
		textsArray[7]=(EditText)view.findViewById(R.id.text_f2_standard_code);
		textsArray[8]=(EditText)view.findViewById(R.id.text_f2_date_deliver);
		textsArray[9]=(EditText)view.findViewById(R.id.text_f2_date_valid);
		textsArray[10]=(EditText)view.findViewById(R.id.text_f3_danger_code);
		textsArray[11]=(EditText)view.findViewById(R.id.text_f3_danger_varity);
		textsArray[12]=(EditText)view.findViewById(R.id.text_f3_danger_reservers);
		textsArray[13]=(EditText)view.findViewById(R.id.text_f3_danger_rank);
		textsArray[14]=(EditText)view.findViewById(R.id.text_f3_date_evaluate);
		textsArray[15]=(EditText)view.findViewById(R.id.text_f3_date_connect);
		textsArray[16]=(EditText)view.findViewById(R.id.text_f4_name_filing);
		textsArray[17]=(EditText)view.findViewById(R.id.text_f4_num_file);
		textsArray[18]=(EditText)view.findViewById(R.id.text_f4_date_deliver);
		textsArray[19]=(EditText)view.findViewById(R.id.text_f4_num_version);
		textsArray[20]=(EditText)view.findViewById(R.id.text_f4_date_filing);
		textsArray[21]=(EditText)view.findViewById(R.id.text_f4_date_review);

		code=(EditText)view.findViewById(R.id.text_code_secure);
		issueDate=(EditText)view.findViewById(R.id.text_date_deliver_license);
		validDate=(EditText)view.findViewById(R.id.text_date_valid);
		scope=(EditText)view.findViewById(R.id.text_range);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		DbCursorLoader cursorLoader=null;
		final DbOperations operations=DbOperations.getInstance(getActivity());
		
		if(id==Constants.ENTERPRISE_INFO_ID){
			final long rowId=bundle.getLong("id");
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					String columns[] = { Enterprise.COLUMN_FK_SAFETY_PERMIT_TYPE,
							Enterprise.COLUMN_SITUATION,
							Enterprise.COLUMN_SAFETY_PERMIT_NUM,
							Enterprise.COLUMN_ISSUE_DATE, Enterprise.COLUMN_VALID_DATE,
							Enterprise.COLUMN_SCOPE };
					return operations.query(Enterprise.TABLE_NAME, columns, Enterprise._ID+"=?", new String[]{""+rowId});
				}
			};
			
		}else if(id==Constants.FILING_ID){
			final long rowId=bundle.getLong("id");
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					return operations.query(Filing.TABLE_NAME, null, Filing.COLUMN_FK_ENTERPRISE_ID+"=?", new String[]{""+rowId});
				}
			};
		}else if(id==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					return operations.query(SafetyPermitType.TABLE_NAME, null, null, null);
				}
			};
		}else if(id==Constants.SPINNER_VARITY_TYPE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					return operations.query(VarityType.TABLE_NAME, null, null, null);
				}
			};
		}
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.ENTERPRISE_INFO_ID){
			if(cursor.getCount()==1){
				cursor.moveToFirst();
				safeTypeSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_FK_SAFETY_PERMIT_TYPE))-1);
				certificateSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_SITUATION)));
				code.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_SAFETY_PERMIT_NUM)));
				issueDate.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ISSUE_DATE)));
				validDate.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_VALID_DATE)));
				scope.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_SCOPE)));
			}
		}else if(loader.getId()==Constants.FILING_ID){
			if(cursor.getCount()==1){
				cursor.moveToFirst();
				//品种类别
				f2TypeSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(Filing.COLUMN_FK_VARITY_TYPE_ID))-1);
				for(int i=0;i<textsArray.length;i++){
					int t=i+1;
					if(i>=5)	t=i+2;
					textsArray[i].setText(cursor.getString(cursor.getColumnIndex(Filing.COLUMNS[t])));
				}
			}
		}else if(loader.getId()==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			safeTypeAdapter.swapCursor(cursor);
		}else if(loader.getId()==Constants.SPINNER_VARITY_TYPE_ID){
			f2TypeAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.SPINNER_SAFETY_PERMIT_TYPE_ID){
			safeTypeAdapter.swapCursor(null);
		}else if(loader.getId()==Constants.SPINNER_VARITY_TYPE_ID){
			f2TypeAdapter.swapCursor(null);
		}
	}
	
}
