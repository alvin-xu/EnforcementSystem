package com.narkii.security.info;

import java.util.ArrayList;
import java.util.List;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Area;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterprisePersion;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class BaseDataFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="BaseDataFragment";
	private View view;
	private Button addResponButton,addManagerButton;
	private Spinner areaSpinner,typeSpinner;
	private EditText enameText,addressText,orgaText,fileText,ephoneText,efaxText,emailText;
	private List<CheckBox> specialBox=new ArrayList<CheckBox>();
	
	private SimpleCursorAdapter  areAdapter,typeAdapter;
	
	private long id=0;
	private List<View> personViews=new ArrayList<View>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_info_base, null);
		Log.d(TAG, "on createview");
		return view;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		areAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{Area.COLUMN_NAME},new int[]{android.R.id.text1});
		areAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSpinner.setAdapter(areAdapter);
		
		typeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{EnterpriseType.COLUMN_NAME},new int[]{android.R.id.text1});
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeAdapter);
		
		addResponButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPersonInfoView(addResponButton,null);
			}
		});
		addManagerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPersonInfoView(addManagerButton,null);
			}
		});
		getLoaderManager().initLoader(Constants.SPINNER_AREA_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID, null, this);
		
		Log.d(TAG, "id:"+getActivity().getIntent().getLongExtra("id",0));
		id=getActivity().getIntent().getLongExtra("id",0);
		if(id==0){//增加信息
			
		}else{//查看信息
			Bundle bundle=new Bundle();
			bundle.putLong("id", id);
			getLoaderManager().restartLoader(Constants.ENTERPRISE_INFO_ID, bundle, this);
			getLoaderManager().restartLoader(Constants.ENTERPRISE_PERSON_ID, bundle, this);
		}
	}
	private  void initViews(){
		addResponButton=(Button) view.findViewById(R.id.button_info_add_responser);
		addManagerButton=(Button) view.findViewById(R.id.button_info_add_manager);
		
		areaSpinner=(Spinner) view.findViewById(R.id.spinner_company_area);
		typeSpinner=(Spinner) view.findViewById(R.id.spinner_type);
		
		enameText=(EditText) view.findViewById(R.id.text_company_name);
		addressText=(EditText) view.findViewById(R.id.text_company_address);
		orgaText=(EditText) view.findViewById(R.id.text_code_organization);
		fileText=(EditText) view.findViewById(R.id.text_num_file);
		ephoneText=(EditText) view.findViewById(R.id.text_info_phone);
		efaxText=(EditText) view.findViewById(R.id.text_info_fax);
		emailText=(EditText) view.findViewById(R.id.text_info_email);
		
		specialBox.add((CheckBox)view.findViewById(R.id.check_info_shutdown));
		specialBox.add((CheckBox)view.findViewById(R.id.check_info_rectify));
		specialBox.add((CheckBox)view.findViewById(R.id.check_info_expend));
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		//不可以保存与Activity相关的view等，否则可能导致资源泄露。应该保存的是用户信息，此处需要保存负责人和管理员的信息！
		super.onSaveInstanceState(outState);
		
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	
	/**
	 * 用于动态增加布局，是在view的父布局的父布局LinearLayout中添加module_responser_manager_info布局！
	 * @param view
	 */
	private void addPersonInfoView(View view,Cursor cursor){
		final LinearLayout parent=(LinearLayout) view.getParent().getParent();
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		View subView=inflater.inflate(R.layout.module_responser_manager_info, null);
		
		EditText name=(EditText) subView.findViewById(R.id.text_person_name);
		EditText phone=(EditText) subView.findViewById(R.id.text_person_phone);
		EditText emial=(EditText) subView.findViewById(R.id.text_person_email);
		EditText safe=(EditText) subView.findViewById(R.id.text_license_secure);
		EditText issueDate=(EditText) subView.findViewById(R.id.text_date_deliver);
		EditText validDate=(EditText) subView.findViewById(R.id.text_date_validate);
		if(cursor!=null){
			name.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_NAME)));
			phone.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_PHONE)));
			emial.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_EMAIL)));
			safe.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_SAFE_PAPER)));
			issueDate.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_ISSUE_DATE)));
			validDate.setText(cursor.getString(cursor.getColumnIndex(EnterprisePersion.COLUMN_VALID_DATE)));
		}
		
		parent.addView(subView);
		final Button delButton=(Button) subView.findViewById(R.id.delete_person);
		delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//删除对应数据
				Log.d(TAG, "delete person");
				parent.removeView((View)v.getParent());
			}
		});
		personViews.add(subView);
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
					
					String[] columns = { Enterprise.COLUMN_NAME,
							Enterprise.COLUMN_ADDRESS,
							Enterprise.COLUMN_ORGANIZATION_CODE,
							Enterprise.COLUMN_FILE_NUMBER,
							Enterprise.COLUMN_AREA,
							Enterprise.COLUMN_FK_ENTERPRISE_TYPE,
							Enterprise.COLUMN_TELEPHONE, Enterprise.COLUMN_FAX,
							Enterprise.COLUMN_EMAIL, Enterprise.COLUMN_SPECIAL };
					Cursor cursor =operations.query(Enterprise.TABLE_NAME, columns, Enterprise._ID+"=?", new String[]{""+rowId});
					return cursor;
				}
			};
		}else if(id==Constants.ENTERPRISE_PERSON_ID){
			final long rowId=bundle.getLong("id");
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor=operations.query(EnterprisePersion.TABLE_NAME, null, EnterprisePersion.COLUMN_FK_ENTERPRISE_ID+"=?", new String[]{""+rowId});
					return cursor;
				}
			};
		}else if(id==Constants.SPINNER_AREA_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					
					return operations.query(Area.TABLE_NAME, null, null, null);
				}
			};
		}else if(id==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					
					return operations.query(EnterpriseType.TABLE_NAME, null, null, null);
				}
			};
		}
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		if(cursor.getCount()==0){
//			Toast.makeText(getActivity(), "load data error", Toast.LENGTH_LONG).show();
			return;
		}
		if(loader.getId()==Constants.ENTERPRISE_INFO_ID){
			
			if(cursor.getCount()==1){
				cursor.moveToFirst();
				enameText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_NAME)));
				addressText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ADDRESS)));
				orgaText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ORGANIZATION_CODE)));
				fileText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FILE_NUMBER)));
				ephoneText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_TELEPHONE)));
				efaxText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FAX)));
				emailText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_EMAIL)));
				specialBox.get(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_SPECIAL))-1).setChecked(true);
				areaSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_AREA))-1);
				typeSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_FK_ENTERPRISE_TYPE))-1);
				Log.d(TAG, "init company info area "+cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_AREA))+" type: "+cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_FK_ENTERPRISE_TYPE)));
			}
		}else if(loader.getId()==Constants.ENTERPRISE_PERSON_ID){
			cursor.moveToFirst();
			do{
				Log.d(TAG, "init person ");
				int type=cursor.getInt(cursor.getColumnIndex(EnterprisePersion.COLUMN_TYPE));
				if(type==1){
					addPersonInfoView(addResponButton, cursor);
				}else if(type==2){
					addPersonInfoView(addManagerButton, cursor);
				}
				cursor.moveToNext();
			}while(!cursor.isAfterLast());
		}else if(loader.getId()==Constants.SPINNER_AREA_ID){
			areAdapter.swapCursor(cursor);
		}else if(loader.getId()==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			typeAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.SPINNER_AREA_ID){
			areAdapter.swapCursor(null);
		}else if(loader.getId()==Constants.SPINNER_ENTERPRISE_TYPE_ID){
			typeAdapter.swapCursor(null);
		}
	}
}
