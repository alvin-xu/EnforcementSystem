package com.narkii.security.enforce;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Document;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class EnforcementFragment extends Fragment implements LoaderCallbacks<Cursor> {
	public static final String TAG = "EnforcementFragment";
	private View view;
	private ListView companyNameList; 
	private TextView dayText,companyNameText;
	private Spinner enterpriseTypeSpin, timeSpin;
	private Button searchButton;
	private SimpleCursorAdapter  enterpriseTypeAdapter,
			companyNameAdapter;
	
	private int mType = 0;// 0复查，1换证

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on option item selected "+item.getItemId());
		switch (item.getItemId()) {
			case R.id.action_review:// 复查
				Log.d(TAG, "复查");
				mType = 0;
				break;
			case R.id.action_exchange:// 换证
				Log.d(TAG, "换证");
				mType = 1;
				break;
			default:
				break;
		}
		getLoaderManager().restartLoader(
				Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID, null, this);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on create view");
		setHasOptionsMenu(true);
		view = inflater.inflate(R.layout.fragment_enforcement, null);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "on resume");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "on activity created");
		initView();
		initAdapter();
		initListener();

		getLoaderManager().initLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID,
				null, this);
		
		getLoaderManager().restartLoader(
				Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID, null, this);
	}

	private void initView() {
		
		companyNameList = (ListView) view
				.findViewById(R.id.enforce_list_company_name);
		
		enterpriseTypeSpin = (Spinner) view
				.findViewById(R.id.enforce_spinner_company_type);
		
		timeSpin = (Spinner) view.findViewById(R.id.enforce_spinner_time);
	
		
		searchButton = (Button) view.findViewById(R.id.enforce_button_search);
		
		
		companyNameText=(TextView) view.findViewById(R.id.enforce_text_name);
		dayText=(TextView) view.findViewById(R.id.enforce_text_day);
	}

	@SuppressWarnings("deprecation")
	private void initAdapter() {
		
		companyNameAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.company_name_item, null,
				new String[] { Enterprise.COLUMN_NAME },
				new int[] {R.id.text1 });
		companyNameList.setAdapter(companyNameAdapter);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spinner_time,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpin.setAdapter(adapter);

		enterpriseTypeAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null,
				new String[] { EnterpriseType.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		enterpriseTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		enterpriseTypeSpin.setAdapter(enterpriseTypeAdapter);
	}

	private void initListener() {

		companyNameList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putLong("enterpriseId", id);
				EnforceRecordFragment fragment=new EnforceRecordFragment();
				fragment.setArguments(bundle);
				
				getFragmentManager().beginTransaction()
						.hide(getFragmentManager().findFragmentByTag("enforce_search_tag"))
						.replace(R.id.enforce_record, fragment,"enforce_record_tag")
						.addToBackStack(null)
						.commit();
				Log.d(TAG, "entry count:"+getChildFragmentManager().getBackStackEntryCount());
			}

		});

		
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("name", companyNameText.getText().toString());
				bundle.putString("day", dayText.getText().toString());
				bundle.putLong("enterpriseType", enterpriseTypeSpin.getSelectedItemId());
				bundle.putInt("dayType", timeSpin.getSelectedItemPosition());
				getLoaderManager().restartLoader(Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID, bundle, EnforcementFragment.this);
//				companyListTitle.setText("查询结果");
			}
		});
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, final Bundle bundle) {
		// TODO Auto-generated method stub
		Log.d("APP", " id+" + id);
		DbCursorLoader dbLoader = null;
		if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			dbLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					Cursor cursor = operations.query(EnterpriseType.TABLE_NAME,
							null, null, null);
					return cursor;
				}
			};
		} else if (id == Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID) {
			dbLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor=null;
					DbOperations operations = DbOperations.getInstance(getActivity());
					//条件查询，同时列出公司列表和默认选中的公司的执法记录
					if(bundle!=null){
						SQLiteQueryBuilder builder =new SQLiteQueryBuilder();
						String tables=null;
						boolean mid=false;
						//整改到期，需要连接Document查询
						if(bundle.getInt("dayType")==0 && !bundle.getString("day").equals("")){
							tables=Document.TABLE_NAME+" JOIN "+Enterprise.TABLE_NAME+
									" ON ("+Enterprise.TABLE_NAME+"."+Enterprise._ID+"="+Document.COLUMN_FK_ENTERPRISE_ID+")";
							if(!mid)	mid=true;
							else	builder.appendWhere(" AND ");
							builder.appendWhere(Document.COLUMN_FK_DOCUMENT_TYPE+"=2 AND "+
									"date("+Document.COLUMN_MATURITY_DATE+")<=date('now','+"+bundle.getString("day")+" day')");
						}else{
							//非整改到期
							tables=Enterprise.TABLE_NAME;
						}
						
						//企业名称
						if(!bundle.getString("name").equals("")){
							if(!mid)	mid=true;
							else	builder.appendWhere(" AND ");
							builder.appendWhere(Enterprise.COLUMN_NAME+" LIKE '%"+bundle.getString("name")+"%'");
						}
						// 企业类型
						if (bundle.getLong("enterpriseType") != 1) {
							if (!mid)		mid = true;
							else		builder.appendWhere(" AND ");
							builder.appendWhere(Enterprise.COLUMN_FK_ENTERPRISE_TYPE
									+ "=" + bundle.getLong("enterpriseType"));
						}
						//许可到期
						if(bundle.getInt("dayType")==1 && !bundle.getString("day").equals("")){
							if (!mid)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere("date("
									+ Enterprise.COLUMN_VALID_DATE + ")"
									+ "<=date('now','+"
									+ bundle.getString("day") + " day')");
						}
						
						String[] columns= new String[]{Enterprise.TABLE_NAME+"."+Enterprise._ID,Enterprise.COLUMN_NAME};
						builder.setTables(tables);
						cursor=builder.query(operations.getDatabase(), columns, null, null, null, null, null);
						
					}else{
						//默认复查、换证查询
						if (mType == 1) {	//换证
							String sql = "date(" + Enterprise.COLUMN_VALID_DATE + ")"
									+ "<=date('now','+3 month')";
							cursor = operations.query(Enterprise.TABLE_NAME,
									new String[] { Enterprise._ID,
											Enterprise.COLUMN_NAME }, sql, null);
							Log.d(TAG, "database :" + sql);
							Log.d(TAG, "换证database :" + cursor.getCount());
						}else{	//复查
							String tables=Document.TABLE_NAME+" JOIN "+Enterprise.TABLE_NAME+
									" ON ("+Enterprise.TABLE_NAME+"."+Enterprise._ID+"="+Document.COLUMN_FK_ENTERPRISE_ID+")";
							String where=Document.COLUMN_FK_DOCUMENT_TYPE+"=2 AND "+
									"date("+Document.COLUMN_MATURITY_DATE+")<=date('now','+2 day')";
							String[] columns= new String[]{Enterprise.TABLE_NAME+"."+Enterprise._ID,Enterprise.COLUMN_NAME};
							cursor=operations.joinQuery(tables,columns, where, null);
							Log.d(TAG, tables+where);
							Log.d(TAG, "复查database :" + cursor.getCount());
						}
					}

					return cursor;
				}
			};
		} 
		return dbLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			enterpriseTypeAdapter.swapCursor(cursor);
		} else if (id == Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID) {
			companyNameAdapter.swapCursor(cursor);
			
//			if(cursor.getCount()!=0){
//				cursor.moveToFirst();
//				long id2=cursor.getLong(cursor.getColumnIndex(Enterprise._ID));
//				Bundle b=new Bundle();
//				b.putLong("id", id2);
//				bundleLog=b;
//				getLoaderManager().restartLoader(Constants.LIST_ENFORCE_RECORD_ID, b, EnforcementFragment.this);
//			}
		} 
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			enterpriseTypeAdapter.swapCursor(null);
		} else if (id == Constants.LIST_ENFORCE_ENTERPRISE_NAME_ID) {
			companyNameAdapter.swapCursor(null);
		} 
	}

	
}
