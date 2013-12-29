package com.narkii.security.info;

import java.util.ArrayList;
import java.util.List;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Area;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterprisePerson;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;

import android.content.ContentValues;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class BaseDataFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="BaseDataFragment";
	private View view;
	private Button addResponButton,addManagerButton,saveButton;
	private Spinner areaSpinner,typeSpinner;
	private EditText enameText,addressText,orgaText,fileText,ephoneText,efaxText,emailText;
	private List<CheckBox> specialBox=new ArrayList<CheckBox>();
	
	private SimpleCursorAdapter  areAdapter,typeAdapter;
	
	private long enterpriseId=0;
	private List<View> personViews=new ArrayList<View>();		//已有企业人列表
	private List<View> addPersonViews=new ArrayList<View>();	//新增企业人列表
	private List<Long> deleteViews=new ArrayList<Long>();	//删除列表
	
	private	int specialId=-1; 
	private	int preSpecialId;//用于记录加载的原数据：特别属性 
	private long areaType=0,enterpriseType=0;//分别用于记录 区域、企业类型 的选择。
	
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
		initListener();
		areAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{Area.COLUMN_NAME},new int[]{android.R.id.text1});
		areAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSpinner.setAdapter(areAdapter);
		
		typeAdapter=new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{EnterpriseType.COLUMN_NAME},new int[]{android.R.id.text1});
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeAdapter);
		
		
		getLoaderManager().initLoader(Constants.SPINNER_AREA_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID, null, this);
		
		Log.d(TAG, "id:"+getArguments().getLong("enterpriseId",0));
		enterpriseId=getArguments().getLong("enterpriseId",0);
		if(enterpriseId==0){//增加信息
			
		}else{//查看信息
			Bundle bundle=new Bundle();
			bundle.putLong("id", enterpriseId);
			getLoaderManager().restartLoader(Constants.ENTERPRISE_INFO_ID, bundle, this);
			getLoaderManager().restartLoader(Constants.ENTERPRISE_PERSON_ID, bundle, this);
		}
	}


	private  void initViews(){
		addResponButton=(Button) view.findViewById(R.id.button_info_add_responser);
		addManagerButton=(Button) view.findViewById(R.id.button_info_add_manager);
		saveButton=(Button) view.findViewById(R.id.info_button_save);
		
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
	private void initListener(){
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
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "save button");
				boolean add=isEnterpriseAdd(),change=isEnterpriseChanged();
				Bundle bundle=new Bundle();
				if((enterpriseId==0 && add) ||(enterpriseId!=0 && change)){
					ContentValues values=new ContentValues();
					values.put(Enterprise.COLUMN_NAME, enameText.getText().toString());
					values.put(Enterprise.COLUMN_ADDRESS, addressText.getText().toString());
					values.put(Enterprise.COLUMN_ORGANIZATION_CODE, orgaText.getText().toString());
					values.put(Enterprise.COLUMN_FILE_NUMBER, fileText.getText().toString());
					values.put(Enterprise.COLUMN_TELEPHONE, ephoneText.getText().toString());
					values.put(Enterprise.COLUMN_FAX, efaxText.getText().toString());
					values.put(Enterprise.COLUMN_EMAIL, emailText.getText().toString());
					values.put(Enterprise.COLUMN_SPECIAL,specialId+1);
					
					values.put(Enterprise.COLUMN_AREA,areaSpinner.getSelectedItemId());
					values.put(Enterprise.COLUMN_FK_ENTERPRISE_TYPE,typeSpinner.getSelectedItemId());
					
					if(enterpriseId==0){//新增信息
						enterpriseId=DbOperations.getInstance(getActivity()).insert(Enterprise.TABLE_NAME, values);
						Log.d(TAG, "add enterprise message rowId:"+enterpriseId);
						bundle.putLong("id", enterpriseId);
					}else{//修改信息
						int result=DbOperations.getInstance(getActivity()).update(Enterprise.TABLE_NAME, values, Enterprise._ID+"=?", new String[]{""+enterpriseId});
						Log.d(TAG, "update enterprise message result:"+result);
						bundle.putLong("id", enterpriseId);
					}
					getLoaderManager().restartLoader(Constants.ENTERPRISE_INFO_ID, bundle, BaseDataFragment.this);
				}
				//删除person
				if(deleteViews.size()>0){
					String[][] param=new String[deleteViews.size()][];
					for(int i=0;i<deleteViews.size();i++){
						param[i]=new String[]{""+deleteViews.get(i)};
					}
					int result=DbOperations.getInstance(getActivity()).delete(EnterprisePerson.TABLE_NAME, EnterprisePerson._ID+"=?", param);
					Log.d(TAG, "delete person :"+result);
					if(result>0)
						deleteViews.clear();
				}
				int total=0;
				//编辑过的person，update
				for(View person:personViews){
					int result=savePersonMessage(person);
					total+=result;
					Log.d(TAG, "update Person: "+result);
				}
				//新添加的person，add
				for(View person:addPersonViews){
					int result=savePersonMessage(person);
					total+=result;
					Log.d(TAG, "add person save: "+result);
				}
				if(total>0){
					bundle.putLong("id", enterpriseId);
					getLoaderManager().restartLoader(Constants.ENTERPRISE_PERSON_ID, bundle, BaseDataFragment.this);
				}
			}
		});
		for(CheckBox checkBox:specialBox){
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						Log.d(TAG, "checked change "+isChecked);
						for(int i=0;i<specialBox.size();i++)
							if(!specialBox.get(i).equals(buttonView)){
								specialBox.get(i).setChecked(false);
							}else{
								specialId=i;
							}
					}
					//什么都没选
					int t=0;
					for(int i=0;i<specialBox.size();i++){
						if(specialBox.get(i).isChecked()) t++;
					}
					if(t==0)	specialId=-1;
					
				}
			});
		}
	}
	
	/**
	 * 返回对应view下的 企业人信息
	 * @param person View
	 * @return null：未修改； ContentValues：新添加的 or 修改的
	 */
	private int savePersonMessage(View person){
		EditText name=(EditText) person.findViewById(R.id.text_person_name);
		EditText phone=(EditText) person.findViewById(R.id.text_person_phone);
		EditText emial=(EditText) person.findViewById(R.id.text_person_email);
		EditText safe=(EditText) person.findViewById(R.id.text_license_secure);
		EditText issueDate=(EditText) person.findViewById(R.id.text_date_deliver);
		EditText validDate=(EditText) person.findViewById(R.id.text_date_validate);
		Button delButton=(Button) person.findViewById(R.id.delete_person);
		if(delButton.getTag()==null){//添加
			if(enterpriseId>0){	//企业基本信息添加成功了才添加企业人
				if(!name.getText().toString().trim().equals("")&&
						!phone.getText().toString().trim().equals("")&&
						!emial.getText().toString().trim().equals("")&&
						!safe.getText().toString().trim().equals("")&&
						!issueDate.getText().toString().trim().equals("")&&
						!validDate.getText().toString().trim().equals("")){
					ContentValues values=new ContentValues();
					values.put(EnterprisePerson.COLUMN_NAME, name.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_EMAIL, emial.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_PHONE, phone.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_SAFE_PAPER, safe.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_VALID_DATE, validDate.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_ISSUE_DATE, issueDate.getText().toString().trim());
					values.put(EnterprisePerson.COLUMN_FK_ENTERPRISE_ID, enterpriseId);
					values.put(EnterprisePerson.COLUMN_TYPE, ""+person.getTag());
					long result=DbOperations.getInstance(getActivity()).insert(EnterprisePerson.TABLE_NAME, values);
					return (int) result;
				}
			}
		}else{//更新
			if(name.getText().toString().trim().equals(name.getTag())&&
					phone.getText().toString().trim().equals(phone.getTag())&&
					emial.getText().toString().trim().equals(emial.getTag())&&
					safe.getText().toString().trim().equals(safe.getTag())&&
					issueDate.getText().toString().trim().equals(issueDate.getTag())&&
					validDate.getText().toString().trim().equals(validDate.getTag())){
				return 0;
			}else{
				ContentValues values=new ContentValues();
				values.put(EnterprisePerson.COLUMN_NAME, name.getText().toString().trim());
				values.put(EnterprisePerson.COLUMN_EMAIL, emial.getText().toString().trim());
				values.put(EnterprisePerson.COLUMN_PHONE, phone.getText().toString().trim());
				values.put(EnterprisePerson.COLUMN_SAFE_PAPER, safe.getText().toString().trim());
				values.put(EnterprisePerson.COLUMN_VALID_DATE, validDate.getText().toString().trim());
				values.put(EnterprisePerson.COLUMN_ISSUE_DATE, issueDate.getText().toString().trim());
//				values.put(EnterprisePersion.COLUMN_FK_ENTERPRISE_ID, enterpriseId);
//				values.put(EnterprisePersion.COLUMN_TYPE, ""+person.getTag());
				int result=DbOperations.getInstance(getActivity()).update(EnterprisePerson.TABLE_NAME, values, EnterprisePerson._ID+"=?", new String[]{""+delButton.getTag()});
				return result;
			}
		}
		return 0;
	}
	/**
	 *	判断基本信息是否填写完整 
	 */
	private boolean isEnterpriseAdd(){
		if(!enameText.getText().toString().equals("")&&
				!addressText.getText().toString().equals("")&&
				!orgaText.getText().toString().equals("")&&
				!fileText.getText().toString().equals("")&&
				!ephoneText.getText().toString().equals("")&&
				!efaxText.getText().toString().equals("")&&
				!enameText.getText().toString().equals(""))
			return true;
		else
			return false;
	}
	/**
	 * 判断基本信息是否有修改
	 * @return
	 */
	private boolean isEnterpriseChanged(){
		if(!enameText.getText().toString().trim().equals(enameText.getTag())||
				!addressText.getText().toString().trim().equals(addressText.getTag())||
				!orgaText.getText().toString().trim().equals(orgaText.getTag())||
				!fileText.getText().toString().trim().equals(fileText.getTag())||
				!ephoneText.getText().toString().trim().equals(ephoneText.getTag())||
				!efaxText.getText().toString().trim().equals(efaxText.getTag())||
				!emailText.getText().toString().trim().equals(emailText.getTag()))
			return true;
		//特别属性改变
		if(specialId!=preSpecialId){
			return true;
		}
		//区域 或者 类型 改变
		if(areaType!=areaSpinner.getSelectedItemId() || enterpriseType!=typeSpinner.getSelectedItemId()){
			return true;
		}
//		for(int i=0;i<specialBox.size();i++){
//			if(specialBox.get(i).isChecked() && i!=preSpecialId){
//				return true;
//			}
//		}
		
		return false;
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
	 * 清空企业人的视图信息
	 * @param view 要清空的企业人相关的view
	 */
	private void clearPersonInfoView(View view){
		final LinearLayout parent=(LinearLayout) view.getParent().getParent();
		Log.d(TAG, "remove person view: "+parent.getChildCount());
		if(parent.getChildCount()>1){
			parent.removeViews(1, parent.getChildCount()-1);
		}
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
		final Button delButton=(Button) subView.findViewById(R.id.delete_person);
		if(cursor!=null){
			name.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_NAME)));
			name.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_NAME)));
			phone.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_PHONE)));
			phone.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_PHONE)));
			emial.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_EMAIL)));
			emial.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_EMAIL)));
			safe.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_SAFE_PAPER)));
			safe.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_SAFE_PAPER)));
			issueDate.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_ISSUE_DATE)));
			issueDate.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_ISSUE_DATE)));
			validDate.setText(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_VALID_DATE)));
			validDate.setTag(cursor.getString(cursor.getColumnIndex(EnterprisePerson.COLUMN_VALID_DATE)));
			
			delButton.setTag(cursor.getLong(cursor.getColumnIndex(EnterprisePerson._ID)));
			
			personViews.add(subView);
		}else{
			addPersonViews.add(subView);
		}
		
		//person类型保存在ui中
		if(view.equals(addResponButton))	subView.setTag(1);
		else 	subView.setTag(2);
		
		parent.addView(subView);
		
		delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "delete person id:"+v.getTag());
				if(v.getTag()!=null){
					deleteViews.add((Long) v.getTag());
					personViews.remove((View)v.getParent().getParent());
				}else{
					addPersonViews.remove((View)v.getParent().getParent());
				}
				parent.removeView((View)v.getParent().getParent());
			}
		});
		
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
					Cursor cursor=operations.query(EnterprisePerson.TABLE_NAME, null, EnterprisePerson.COLUMN_FK_ENTERPRISE_ID+"=?", new String[]{""+rowId});
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
				enameText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_NAME)));
				addressText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ADDRESS)));
				addressText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ADDRESS)));
				orgaText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ORGANIZATION_CODE)));
				orgaText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_ORGANIZATION_CODE)));
				fileText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FILE_NUMBER)));
				fileText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FILE_NUMBER)));
				ephoneText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_TELEPHONE)));
				ephoneText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_TELEPHONE)));
				efaxText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FAX)));
				efaxText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_FAX)));
				emailText.setText(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_EMAIL)));
				emailText.setTag(cursor.getString(cursor.getColumnIndex(Enterprise.COLUMN_EMAIL)));
				
				specialId=cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_SPECIAL))-1;
				Log.d(TAG, "get null special id:"+specialId);
				preSpecialId=specialId;
				if(specialId>=0)
					specialBox.get(cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_SPECIAL))-1).setChecked(true);
				areaType=cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_AREA));
				areaSpinner.setSelection((int) (areaType-1));
				enterpriseType=cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_FK_ENTERPRISE_TYPE));
				typeSpinner.setSelection((int) (enterpriseType-1));
				
				Log.d(TAG, "init company info area "+cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_AREA))+" type: "+cursor.getInt(cursor.getColumnIndex(Enterprise.COLUMN_FK_ENTERPRISE_TYPE)));
			}
		}else if(loader.getId()==Constants.ENTERPRISE_PERSON_ID){
			clearPersonInfoView(addResponButton);
			clearPersonInfoView(addManagerButton);
			if(personViews.size()>0) personViews.clear();
			if(addPersonViews.size()>0) addPersonViews.clear();
			cursor.moveToFirst();
			do{
				Log.d(TAG, "init person ");
				int type=cursor.getInt(cursor.getColumnIndex(EnterprisePerson.COLUMN_TYPE));
				if(type==1){//负责人
					addPersonInfoView(addResponButton, cursor);
				}else if(type==2){//管理员
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
