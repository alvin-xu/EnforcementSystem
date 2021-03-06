package com.narkii.security.enforce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Document;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.Member;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class InspectFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="InspectActivity";
	private View view,paperContent;
	LinearLayout paperContainer;
	private Button sealButton,saveButton,printButton,attachButton,detachButton;
	
	private EditText fromDate,fromTime,toDate,toTime,
					 officerNo1,officerNo2;
	private EditText[] tests=new EditText[9];
	private ImageView sealImage;
	private Spinner officerSpin1,officerSpin2;
	private SimpleCursorAdapter officerAdapter;
	
	private EditText datePicker;
	private long enterpriseId,recordId; //企业id，文书id
	List<String> officersList=null;	//执法人员列表
	private boolean showSeal=false;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
				case Constants.INSERT_OK_MSG:
					Toast.makeText(getActivity(), "insert success", Toast.LENGTH_LONG).show();
					break;
				case Constants.UPDATE_OK_MSG:
					Toast.makeText(getActivity(), "update success", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		initListener();
		
		officerAdapter=new NameCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null, new String[]{Member.COLUMN_NAME},new int[]{android.R.id.text1});
		officerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		officerSpin1.setAdapter(officerAdapter);
		officerSpin2.setAdapter(officerAdapter);
		
		getLoaderManager().initLoader(Constants.SPINNER_MEMBER_NAME_ID,	null, this);
		
		enterpriseId=getArguments().getLong("enterpriseId", 0);
		recordId=getArguments().getLong("recordId", 0);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_paper_inspect, null);
		return view;
	}
	
	private void initViews(){
		paperContainer=(LinearLayout) view.findViewById(R.id.paper_container);
		
		paperContent=view.findViewById(R.id.linear_paper_content);
		paperContent.setDrawingCacheEnabled(true);
		
		sealImage=(ImageView) view.findViewById(R.id.paper_seal);
		datePicker=(EditText) view.findViewById(R.id.inspect_date_sign);
		
		sealButton=(Button) view.findViewById(R.id.inspect_seal);
		saveButton=(Button) view.findViewById(R.id.inspect_save);
		printButton=(Button) view.findViewById(R.id.inspect_print);
		attachButton=(Button) view.findViewById(R.id.button_attach);
		detachButton=(Button) view.findViewById(R.id.button_detach);
		
		officerSpin1=(Spinner) view.findViewById(R.id.inspect_lawer1);
		officerSpin2=(Spinner) view.findViewById(R.id.inspect_lawer2);
		
		officerNo1=(EditText) view.findViewById(R.id.inspect_id1);
		officerNo2=(EditText) view.findViewById(R.id.inspect_id2);
		
		fromDate=(EditText) view.findViewById(R.id.inspect_date_from);
		fromTime=(EditText) view.findViewById(R.id.inspect_time_from);
		toDate=(EditText) view.findViewById(R.id.inspect_date_to);
		toTime=(EditText) view.findViewById(R.id.inspect_time_to);
		
		tests[0]=(EditText) view.findViewById(R.id.inspect_file_no);
		tests[1]=(EditText) view.findViewById(R.id.inspect_company_name);
		tests[2]=(EditText) view.findViewById(R.id.inspect_address);
		tests[3]=(EditText) view.findViewById(R.id.inspect_responser);
		tests[4]=(EditText) view.findViewById(R.id.inspect_job);
		tests[5]=(EditText) view.findViewById(R.id.inspect_phone);
		tests[6]=(EditText) view.findViewById(R.id.inspect_location);
		tests[7]=(EditText) view.findViewById(R.id.inspect_city);
		tests[8]=(EditText) view.findViewById(R.id.inspect_situ);
	}
	private void initListener(){
		attachButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on button attach");
				
				LayoutInflater inflater=LayoutInflater.from(getActivity());
				LinearLayout attach=(LinearLayout) inflater.inflate(R.layout.paper_inspect_attach, null);
				if(attach==null)	Log.d(TAG, "attach null");
				
				//必须动态设置margin才能生效，在xml中定义无效？？？！！！
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(930, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 0, 0, 20);
				attach.setLayoutParams(lp);
				
				paperContainer.addView(attach);
			}
		});
		detachButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(paperContainer.getChildCount()>1){
					//数据删除
					paperContainer.removeViewAt(paperContainer.getChildCount()-1);
				}
			}
		});
		printButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				paperContent.measure(  
//		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),  
//		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
//				paperContent.layout(0, 0, paperContent.getMeasuredWidth(),  
//						paperContent.getMeasuredHeight());  
//		  
//				paperContent.buildDrawingCache();
				
				Bitmap bitmap=paperContent.getDrawingCache();
				File outputFile=new File(Environment.getExternalStorageDirectory()+"/Pictures/EnforcementSys/temp.png");
				if(outputFile.exists()){
					outputFile.delete();
				}
				try {
					if(outputFile.createNewFile()){
						FileOutputStream outputStream=new FileOutputStream(outputFile);
						bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
						outputStream.flush();
						outputStream.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent();
				ComponentName component=new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPictures");
				//				ComponentName component=new ComponentName("com.dynamixsoftware.printhand.premium","com.dynamixsoftware.printhand.ui.ActivityPreviewImages");
				intent.setComponent(component);
				intent.setAction("android.intent.action.VIEW");
				intent.setType("image/png");
				intent.setData(Uri.fromFile(outputFile));
//				intent.setData(Uri.fromFile(new File("/storage/sdcard0/Pictures/EnforcementSys/temp")));
				startActivity(intent);
			}
		});
		sealButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!showSeal){
					sealImage.setVisibility(View.VISIBLE);
				}else{
					sealImage.setVisibility(View.INVISIBLE);
				}
				showSeal=!showSeal;
			}
		});
		datePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "click");
				DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.CHINA_FORMAT);
				dialog.show(getFragmentManager(), "date picker");
			}
		});
		datePicker.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on focus change "+hasFocus);
				if(hasFocus){
					DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.CHINA_FORMAT);
					dialog.show(getFragmentManager(), "date picker");
				}
			}
		});
		fromDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "click");
				DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.NUMBER_FORMAT);
				dialog.show(getActivity().getSupportFragmentManager(), "date picker");
			}
		});
		fromTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "click");
				TimePickerFragment dialog=new TimePickerFragment((EditText) v);
				dialog.show(getFragmentManager(), "time picker");
			}
		});
		toDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "click");
				DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.NUMBER_FORMAT);
				dialog.show(getFragmentManager(), "date picker");
			}
		});
		toTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "click");
				TimePickerFragment dialog=new TimePickerFragment((EditText) v);
				dialog.show(getFragmentManager(), "time picker");
			}
		});
		fromDate.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on focus change "+hasFocus);
				if(hasFocus){
					DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.NUMBER_FORMAT);
					dialog.show(getFragmentManager(), "date picker");
				}
			}
		});
		fromTime.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on focus change "+hasFocus);
				if(hasFocus){
					TimePickerFragment dialog=new TimePickerFragment((EditText) v);
					dialog.show(getFragmentManager(), "time picker");
				}
			}
		});
		toDate.setOnFocusChangeListener(new OnFocusChangeListener() {
	
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on focus change "+hasFocus);
				if(hasFocus){
					DatePickerFragment dialog=new DatePickerFragment((EditText) v,DatePickerFragment.NUMBER_FORMAT);
					dialog.show(getFragmentManager(), "date picker");
				}
			}
		});
		toTime.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on focus change "+hasFocus);
				if(hasFocus){
					TimePickerFragment dialog=new TimePickerFragment((EditText) v);
					dialog.show(getFragmentManager(), "time picker");
				}
			}
		});
		officerSpin1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on item selected");
				officerNo1.setText((CharSequence)view.getTag());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Log.d(TAG, "nothing selected");
			}
			
		});
		officerSpin2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on item selected");
				officerNo2.setText((CharSequence)view.getTag());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Log.d(TAG, "nothing selected");
			}
			
		});
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isOk()){
					final StringBuilder content=new StringBuilder();
					for(int i=0;i<tests.length;i++){
						content.append(tests[i].getText().toString()+"|");
						if(i==6){
							content.append(fromDate.getText().toString()+"|");
							content.append(fromTime.getText().toString()+"|");
							content.append(toDate.getText().toString()+"|");
							content.append(toTime.getText().toString()+"|");
						}
					}
					final DbOperations operations=DbOperations.getInstance(getActivity());
					final ContentValues values=new ContentValues();
					
					values.put(Document.COLUMN_FK_ENTERPRISE_ID, enterpriseId);
					values.put(Document.COLUMN_NO, tests[0].getText().toString());
					values.put(Document.COLUMN_FK_DOCUMENT_TYPE, 1);
					Cursor cursor=(Cursor)officerSpin1.getSelectedItem();
					values.put(Document.COLUMN_OFFICER1,cursor.getString(cursor.getColumnIndex(Member.COLUMN_NAME)));
					cursor=(Cursor)officerSpin2.getSelectedItem();
					values.put(Document.COLUMN_OFFICER2, cursor.getString(cursor.getColumnIndex(Member.COLUMN_NAME)));
					Log.d(TAG, "spinner 1:"+ cursor.getString(cursor.getColumnIndex(Member.COLUMN_NAME)));
					values.put(Document.COLUMN_CERTIFICATE_NO1, officerNo1.getText().toString());
					values.put(Document.COLUMN_CERTIFICATE_NO2, officerNo2.getText().toString());
					values.put(Document.COLUMN_CONTENT, content.toString());
					
//					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
//					String date=dateFormat.format(new Date(datePicker.getCalendarView().getDate()));
					String date=(String) datePicker.getTag();
					Log.d(TAG, "!!!!!date:"+date);
					
					values.put(Document.COLUMN_CREATE_DATE, date);
					Log.d(TAG, content.toString());
				
					if(showSeal){
						values.put(Document.COLUMN_SEAL, "seal");
					}else{
						values.put(Document.COLUMN_SEAL, "notseal");
					}
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg=new Message();
							if(recordId==0){	//新增执法记录
								long result=operations.insert(Document.TABLE_NAME, values);
								Log.d(TAG, "insert result"+result);
								if(result>0){
									msg.what=Constants.INSERT_OK_MSG;
									handler.sendMessage(msg);
								}
									
							}else{	//修改执法记录
								int result=operations.update(Document.TABLE_NAME, values, Document._ID+"=?", new String[]{""+recordId});
								Log.d(TAG, "update result"+result);
								if(result>0){
									msg.what=Constants.UPDATE_OK_MSG;
									handler.sendMessage(msg);
								}
							}
						}
					}).start();
				
				}else{
					
					Toast.makeText(getActivity(), "please fill the content", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 判断页面内容是否填写完整
	 */
	private boolean isOk(){
		for(int i=0;i<tests.length;i++)
			if(tests[i].getText().toString().equals(""))	return false;
		if(fromDate.getText().toString().equals("")||fromTime.getText().toString().equals("")||
				toDate.getText().toString().equals("")||toTime.getText().toString().equals(""))
			return false;
		return true;
	}
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		DbCursorLoader dbCursorLoader=null;
		final DbOperations operations=DbOperations.getInstance(getActivity());
		if(id==Constants.SPINNER_MEMBER_NAME_ID){
			dbCursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor=operations.query(Member.TABLE_NAME, new String[]{Member._ID,Member.COLUMN_NAME,Member.COLUMN_CERTIFICATE_NO}, null, null);
					Log.d(TAG, "count:"+cursor.getCount());
					return cursor;
				}
			};
		}else if(id==Constants.ENTERPRISE_INFO_ID){
			final long rowId=bundle.getLong("id");
			dbCursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor=operations.query(Enterprise.TABLE_NAME, new String[]{Enterprise.COLUMN_NAME,Enterprise.COLUMN_ADDRESS}, Enterprise._ID+"=?", new String[]{""+rowId});
					Log.d(TAG, "enterprise info :"+cursor.getCount());
					return cursor;
				}
			};
		}else if(id==Constants.LIST_ENFORCE_RECORD_ID){
			final long rowId=bundle.getLong("id");
			dbCursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor=operations.query(Document.TABLE_NAME,null, Document._ID+"=?", new String[]{""+rowId});
					Log.d(TAG, "document info :"+cursor.getCount());
					return cursor;
				}
			};
		}
		return dbCursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.SPINNER_MEMBER_NAME_ID){
			if(officersList==null){
				officersList=new ArrayList<String>();
				data.moveToFirst();
				while(!data.isAfterLast()){
					officersList.add(data.getString(data.getColumnIndex(Member.COLUMN_NAME)));
					data.moveToNext();
				}
				Log.d(TAG, "member list size :"+officersList.size());
				data.moveToFirst();
			}
			officerAdapter.swapCursor(data);
			Bundle bundle=new Bundle();
			//必须在officerlist初始化之后加载，保证文书信息可以正常显示。其实就是为了显示执法人员。
			if(recordId==0){	//新增文书，自动载入企业相关信息
				bundle.putLong("id", enterpriseId);
				getLoaderManager().restartLoader(Constants.ENTERPRISE_INFO_ID, bundle, this);
			}else{	//查看文书，载入文书信息
				bundle.putLong("id", recordId);
				getLoaderManager().restartLoader(Constants.LIST_ENFORCE_RECORD_ID, bundle, this);
			}
		}else if(loader.getId()==Constants.ENTERPRISE_INFO_ID){
			if(data.getCount()==1){
				data.moveToFirst();
				tests[1].setText(data.getString(data.getColumnIndex(Enterprise.COLUMN_NAME)));
				tests[2].setText(data.getString(data.getColumnIndex(Enterprise.COLUMN_ADDRESS)));
			}
		}else if(loader.getId()==Constants.LIST_ENFORCE_RECORD_ID){
			if(data.getCount()==1){
				data.moveToFirst();
				String content=data.getString(data.getColumnIndex(Document.COLUMN_CONTENT));
				String[] contents=content.split("\\|");
				Log.d(TAG, "content length:"+contents.length);
				for(int i=0,t=0;i<tests.length;i++,t++){
					tests[i].setText(contents[t]);
					if(i==6){
						fromDate.setText(contents[++t]);
						fromTime.setText(contents[++t]);
						toDate.setText(contents[++t]);
						toTime.setText(contents[++t]);
					}
				}
				
				String[] date=data.getString(data.getColumnIndex(Document.COLUMN_CREATE_DATE)).split("-");
//				Integer[] date2=new Integer[3];
//				date2[0]=Integer.parseInt(date[0]);
//				date2[1]=Integer.parseInt(date[1]);
//				date2[2]=Integer.parseInt(date[2]);
//				datePicker.init(date2[0], date2[1], date2[2], null);
				datePicker.setText(date[0]+" 年  "+date[1]+" 月  "+date[2]+" 日  ");
				datePicker.setTag(data.getString(data.getColumnIndex(Document.COLUMN_CREATE_DATE)));
				if(officersList!=null){
					officerSpin1.setSelection(officersList.indexOf(data.getString(data.getColumnIndex(Document.COLUMN_OFFICER1))));
					officerSpin2.setSelection(officersList.indexOf(data.getString(data.getColumnIndex(Document.COLUMN_OFFICER2))));
				}else{
					Log.d(TAG, "officer list is null");
				}
				//印章
				String isSeal=data.getString(data.getColumnIndex(Document.COLUMN_SEAL));
				Log.d(TAG, "is seal?:"+isSeal);
				if(isSeal!=null && isSeal.equals("seal")){
					sealImage.setVisibility(View.VISIBLE);
					showSeal=true;
				}
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.SPINNER_MEMBER_NAME_ID){
			officerAdapter.swapCursor(null);
		}
	}
	class NameCursorAdapter extends SimpleCursorAdapter{

		@SuppressWarnings("deprecation")
		public NameCursorAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			view.setTag(cursor.getString(cursor.getColumnIndex(Member.COLUMN_CERTIFICATE_NO)));
			super.bindView(view, context, cursor);
			
		}
		
	}
}
