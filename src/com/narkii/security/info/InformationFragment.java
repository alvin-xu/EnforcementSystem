package com.narkii.security.info;

import java.util.ArrayList;
import java.util.List;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Area;
import com.narkii.security.data.EnforceSysContract.Document;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterprisePerson;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;
import com.narkii.security.data.EnforceSysContract.Filing;
import com.narkii.security.data.EnforceSysContract.SafetyPermitType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class InformationFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	public static final String TAG = "InformationFragment";
	private View view;
	private ListView companyList;
	private Button addButton, searchButton,deleteButton;
	private CompanyDataAdapter listInfoAdapter;
	private Spinner enterpriseTypeSpin, permitTypeSpin, certificateSituSpin,
			 timeSpin,areaSpin;
	private SimpleCursorAdapter enterpriseTypeAdapter, permitTypeAdapter,areaAdapter;
	private TextView nameText, daysText;

	private Bundle bundleLog;//用于记录查询参数
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.INFO_DELETE_ITEM_OK_MSG:
				deleteButton.setClickable(false);
				deleteButton.setBackgroundResource(R.drawable.button_delete_unable);
				getLoaderManager().restartLoader(Constants.LIST_ENTERPRISE_INFO_ID, bundleLog, InformationFragment.this);
				Log.d(TAG, "reloader the list data");
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "on activity result");
		Fragment fragment=getFragmentManager().findFragmentByTag("info_detail_tag");
		if(fragment!=null)
			fragment.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_information, null);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initAdapter();
		initListener();
		
		getLoaderManager().initLoader(Constants.LIST_ENTERPRISE_INFO_ID, null,
				this);
		getLoaderManager().restartLoader(Constants.SPINNER_ENTERPRISE_TYPE_ID,
				null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_SAFETY_PERMIT_TYPE_ID, null, this);
		getLoaderManager().restartLoader(Constants.SPINNER_AREA_ID, null, this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		switch (item.getItemId()) {
		
		case R.id.action_exchange:	//换证
			Log.d(TAG, "换证");
			bundle.putInt("type", 2);//换证
			getLoaderManager().restartLoader(Constants.LIST_ENTERPRISE_INFO_ID, bundle,
					this);
			break;
		case R.id.action_rectify:	//重点整改
			Log.d(TAG, "重点整改");
			bundle.putInt("type", 3);
			getLoaderManager().restartLoader(Constants.LIST_ENTERPRISE_INFO_ID, bundle,
					this);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void initView() {
		companyList = (ListView) view.findViewById(R.id.info_list_company);
		enterpriseTypeSpin = (Spinner) view
				.findViewById(R.id.info_spin_company_type);
		permitTypeSpin = (Spinner) view
				.findViewById(R.id.info_spin_permission_type);
		areaSpin=(Spinner) view.findViewById(R.id.info_spin_company_area);
		certificateSituSpin = (Spinner) view
				.findViewById(R.id.info_spin_certificate_situ);
		timeSpin = (Spinner) view.findViewById(R.id.info_spin_time);

		addButton = (Button) view.findViewById(R.id.info_button_add);
		searchButton = (Button) view.findViewById(R.id.info_button_search);
		deleteButton=(Button) view.findViewById(R.id.info_button_delete);

		nameText = (TextView) view.findViewById(R.id.info_text_name);
		daysText = (TextView) view.findViewById(R.id.info_text_day);
	}

	@SuppressWarnings("deprecation")
	private void initAdapter() {
		listInfoAdapter = new CompanyDataAdapter(getActivity(), null, false);
		companyList.setAdapter(listInfoAdapter);

		enterpriseTypeAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null,
				new String[] { EnterpriseType.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		enterpriseTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		enterpriseTypeSpin.setAdapter(enterpriseTypeAdapter);

		permitTypeAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null,
				new String[] { SafetyPermitType.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		permitTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		permitTypeSpin.setAdapter(permitTypeAdapter);
		
		areaAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null,
				new String[] { Area.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSpin.setAdapter(areaAdapter);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spinner_certificate_situ,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		certificateSituSpin.setAdapter(adapter);

		adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.spinner_time, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpin.setAdapter(adapter);
	}

	private void initListener() {
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "add button");
				Fragment infoFragment=getFragmentManager().findFragmentByTag("info_detail_tag");
				if(infoFragment==null){
					Log.d(TAG, "new info Fragment");
					infoFragment=new InfoDetailFragment();
				}
				Bundle bundle=new Bundle();
				infoFragment.setArguments(bundle);
				getFragmentManager().beginTransaction()
					.hide(getFragmentManager().findFragmentByTag("info_search_tag"))
					.replace(R.id.information, infoFragment, "info_detail_tag")
					.addToBackStack(null)
					.commit();
			}
		});
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						DbOperations operations=DbOperations.getInstance(getActivity());
						List<Long> lists=listInfoAdapter.getCheckIds();
						if(lists.size()==0) return;
						Log.d(TAG, "list size:"+lists.size());
						String[][] args=new String[lists.size()][];
						for(int i=0;i<lists.size();i++){
							args[i]=new String[]{""+lists.get(i)};
							Log.d(TAG, "list "+i+": "+lists.get(i));
						}
						int result=operations.delete(Enterprise.TABLE_NAME, Enterprise._ID+"=?", args);
						if(result>0){
							Message msg=new Message();
							msg.what=Constants.INFO_DELETE_ITEM_OK_MSG;
							handler.sendMessage(msg);
							Log.d(TAG, "send the message "+result);
							listInfoAdapter.clearChecks();
						}
					}
				}).start();
				
			}
		});
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("name", nameText.getText().toString());
				if (nameText.getText().toString() == null)
					Log.d(TAG, "name null:" + nameText.getText().toString());
				else if (nameText.getText().toString().equals("")) {
					Log.d(TAG, "name: is space");
				}
				bundle.putLong("area", areaSpin.getSelectedItemId());
				bundle.putLong("enterpriseType",
						enterpriseTypeSpin.getSelectedItemId());
				Log.d(TAG,
						"enterprisetype select: "
								+ enterpriseTypeSpin.getSelectedItemId());
				bundle.putLong("permitType", permitTypeSpin.getSelectedItemId());
				Log.d(TAG, "permit type :" + permitTypeSpin.getSelectedItemId());

				bundle.putInt("timeType", timeSpin.getSelectedItemPosition());
				Log.d(TAG, "time spin:" + timeSpin.getSelectedItemPosition());
/*				bundle.putInt("searchType",
						searchTypeSpin.getSelectedItemPosition());*/
				bundle.putInt("permitSitu",
						certificateSituSpin.getSelectedItemPosition());

				bundle.putString("day", daysText.getText().toString());
				bundle.putInt("type", 1);//搜索
				
				bundleLog=bundle;
				getLoaderManager().restartLoader(
						Constants.LIST_ENTERPRISE_INFO_ID, bundle,
						InformationFragment.this);
			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, final Bundle bundle) {
		// TODO Auto-generated method stub
		DbCursorLoader cursorLoader = null;

		if (id == Constants.LIST_ENTERPRISE_INFO_ID) {
			cursorLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Cursor cursor = null;
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					String[] columns=new String[] {
							Enterprise.TABLE_NAME + "."	+ Enterprise._ID,
							Enterprise.COLUMN_NAME,
							Area.COLUMN_NAME,
							Enterprise.COLUMN_ADDRESS,
							EnterprisePerson.COLUMN_NAME,
							EnterprisePerson.COLUMN_PHONE,
							Filing.COLUMN_VALID_DATE };
					String tables = Enterprise.TABLE_NAME +

							" LEFT JOIN " + Area.TABLE_NAME + " ON ("
									+ Enterprise.COLUMN_AREA + "="
									+ Area.TABLE_NAME + "." + Area._ID + ")" +

									" LEFT JOIN " + Filing.TABLE_NAME + " ON ("
									+ Enterprise.TABLE_NAME + "."
									+ Enterprise._ID + "="
									+ Filing.COLUMN_FK_ENTERPRISE_ID + ")" +

									" LEFT JOIN " + EnterprisePerson.TABLE_NAME
									+ " ON (" + Enterprise.TABLE_NAME + "."
									+ Enterprise._ID + "="
									+ EnterprisePerson.COLUMN_FK_ENTERPRISE_ID
									+ ")";
					
					if (bundle != null && bundle.getInt("type")==1) { // 条件查询
						Log.d(TAG, "conditon search");
						boolean mid = false;
						SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

						// 责令整改，需要连接Document查询（只有这个连接查询是可选的，所以预先判断）
						if (bundle.getInt("timeType") == 0
								&& !bundle.getString("day").equals("")) {
							tables = Enterprise.TABLE_NAME +

							" LEFT JOIN " + Document.TABLE_NAME + " ON ("
									+ Enterprise.TABLE_NAME + "."
									+ Enterprise._ID + "="
									+ Document.COLUMN_FK_ENTERPRISE_ID + ")" +

									" LEFT JOIN " + Area.TABLE_NAME + " ON ("
									+ Enterprise.COLUMN_AREA + "="
									+ Area.TABLE_NAME + "." + Area._ID + ")" +

									" LEFT JOIN " + Filing.TABLE_NAME + " ON ("
									+ Enterprise.TABLE_NAME + "."
									+ Enterprise._ID + "="
									+ Filing.COLUMN_FK_ENTERPRISE_ID + ")" +

									" LEFT JOIN " + EnterprisePerson.TABLE_NAME
									+ " ON (" + Enterprise.TABLE_NAME + "."
									+ Enterprise._ID + "="
									+ EnterprisePerson.COLUMN_FK_ENTERPRISE_ID
									+ ")";
							if (!mid)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Document.COLUMN_FK_DOCUMENT_TYPE
									+ "=2 AND "
									+ "date("
									+ Document.COLUMN_MATURITY_DATE
									+ ")<=date('now','+"
									+ bundle.getString("day") + " day')");
						} else {
							// 非整改到期,默认的tables
						}
						builder.setTables(tables);
						Log.d(TAG,tables);
						// 企业名称
						if (!bundle.getString("name").equals("")) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Enterprise.COLUMN_NAME
									+ " LIKE '%" + bundle.getString("name")
									+ "%'");
							Log.d(TAG, "name:" + bundle.getString("name"));
						}
						// 镇别
						if (bundle.getLong("area") != 1) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Enterprise.COLUMN_AREA + "="
									+ bundle.getLong("area"));
						}
						// 企业类型
						if (bundle.getLong("enterpriseType") != 1) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Enterprise.COLUMN_FK_ENTERPRISE_TYPE
									+ "=" + bundle.getLong("enterpriseType"));
						}

						// 持证情况
						if (bundle.getInt("permitSitu") != 0) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Filing.COLUMN_SITUATION + "="
									+ bundle.getInt("permitSitu"));
						}
						// 许可类型
						if (bundle.getLong("permitType") != 1) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere(Filing.COLUMN_FK_SAFETY_PERMIT_TYPE
									+ "=" + bundle.getLong("permitType"));
						}
						// 许可到期
						if (bundle.getInt("timeType") == 1
								&& !bundle.getString("day").equals("")) {
							if (mid == false)
								mid = true;
							else
								builder.appendWhere(" AND ");
							builder.appendWhere("date("
									+ Filing.COLUMN_VALID_DATE + ")"
									+ "<=date('now','+"
									+ bundle.getString("day") + " day')");
						}
						if (mid == false)
							mid = true;
						else
							builder.appendWhere(" AND ");
						//一个企业对多个负责人时，取第一个
						builder.appendWhere(EnterprisePerson.TABLE_NAME + "."+ EnterprisePerson._ID + 
								" IN (SELECT "
								+ EnterprisePerson.TABLE_NAME + "."+ EnterprisePerson._ID + 
								" FROM "+ EnterprisePerson.TABLE_NAME + 
								" WHERE "+ EnterprisePerson.COLUMN_TYPE + "=1 AND "
								+EnterprisePerson.COLUMN_FK_ENTERPRISE_ID+"="+Enterprise.TABLE_NAME + "."+ Enterprise._ID+" LIMIT 1 )");
						if (mid == false)
							mid = true;
						else
							builder.appendWhere(" OR ");
						//一个企业对0个负责人时，返回true,与上面的情况是“并”关系
						builder.appendWhere("not exists ( select 1 from "+ EnterprisePerson.TABLE_NAME + 
								" WHERE "+ EnterprisePerson.COLUMN_TYPE + "=1 AND "
								+EnterprisePerson.COLUMN_FK_ENTERPRISE_ID+"="+Enterprise.TABLE_NAME + "."+ Enterprise._ID+" LIMIT 1 )");
						cursor = builder.query(operations.getDatabase(),
								columns, null, null,null, null, null);
					}else if(bundle!=null && bundle.getInt("type")==2){//换证
						
						String where = "date(" + Filing.COLUMN_VALID_DATE + ")"
								+ "<=date('now','+3 month')";
						cursor=operations.joinQuery(tables, columns, where, null);

						Log.d(TAG, "database :" + where);
						Log.d(TAG, "换证database :" + cursor.getCount());
					}else if(bundle!=null && bundle.getInt("type")==2){//重点整改
						String where=Enterprise.COLUMN_SPECIAL+"=2";
						cursor=operations.joinQuery(tables, columns, where, null);
						Log.d(TAG, "重点整改database :" + cursor.getCount());
					}
					else {
						// 默认显示
						/*
						 * String tables = Enterprise.TABLE_NAME + " LEFT JOIN "
						 * + Area.TABLE_NAME + " ON (" + Enterprise.COLUMN_AREA
						 * + "=" + Area.TABLE_NAME + "." + Area._ID + ")";
						 * String[] columns = new String[] {
						 * Enterprise.TABLE_NAME + "." + Enterprise._ID,
						 * Enterprise.COLUMN_NAME, Area.COLUMN_NAME,
						 * Enterprise.COLUMN_ADDRESS, Enterprise.COLUMN_CHARGE,
						 * Enterprise.COLUMN_CHARGE_PHONE,
						 * Enterprise.COLUMN_VALID_DATE }; cursor =
						 * operations.joinQuery(tables, columns, null, null);
						 */
					}
					return cursor;
				}
			};
		} else if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			cursorLoader = new DbCursorLoader(getActivity()) {

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
		} else if (id == Constants.SPINNER_SAFETY_PERMIT_TYPE_ID) {
			cursorLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					Cursor cursor = operations.query(
							SafetyPermitType.TABLE_NAME, null, null, null);
					return cursor;
				}
			};
		}else if (id == Constants.SPINNER_AREA_ID) {
			cursorLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					Cursor cursor = operations.query(
							Area.TABLE_NAME, null, null, null);
					return cursor;
				}
			};
		}

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id == Constants.LIST_ENTERPRISE_INFO_ID) {
			listInfoAdapter.swapCursor(cursor);
		} else if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			enterpriseTypeAdapter.swapCursor(cursor);
		} else if (id == Constants.SPINNER_SAFETY_PERMIT_TYPE_ID) {
			permitTypeAdapter.swapCursor(cursor);
		}else if (id == Constants.SPINNER_AREA_ID) {
			areaAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id == Constants.LIST_ENTERPRISE_INFO_ID) {
			listInfoAdapter.swapCursor(null);
		} else if (id == Constants.SPINNER_ENTERPRISE_TYPE_ID) {
			enterpriseTypeAdapter.swapCursor(null);
		} else if (id == Constants.SPINNER_SAFETY_PERMIT_TYPE_ID) {
			permitTypeAdapter.swapCursor(null);
		}else if (id == Constants.SPINNER_AREA_ID) {
			areaAdapter.swapCursor(null);
		}
	}

	class CompanyDataAdapter extends CursorAdapter {
		private List<Long> checkIds;
		
		public List<Long> getCheckIds() {
			return checkIds;
		}
		public void clearChecks(){
			checkIds.clear();
		}
		private class ViewHolder {
			TextView name, area, address, responser, phone, date;
			int idIndex,nameIndex, areaIndex, addressIndex, responserIndex, phoneIndex,
					dateIndex;
			CheckBox checkBox;
		}

		public CompanyDataAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
			checkIds=new ArrayList<Long>();
		}
		
		@Override
		public void bindView(View arg0, Context arg1, Cursor cursor) {
			// 此处将视图绑定数据！注意视图的恢复，如checkBok默认是没有选中的！！！
			// 因为绑定的是之前的视图，所以不设置的话，状态是跟之前的视图一样的！！！
			final ViewHolder holder = (ViewHolder) arg0.getTag();
			holder.name.setText(cursor.getString(holder.nameIndex));
			holder.area.setText(cursor.getString(holder.areaIndex));
			holder.address.setText(cursor.getString(holder.addressIndex));
			holder.responser.setText(cursor.getString(holder.responserIndex));
			holder.phone.setText(cursor.getString(holder.phoneIndex));
			holder.date.setText(cursor.getString(holder.dateIndex));
			holder.checkBox.setTag(cursor.getLong(holder.idIndex));
			holder.checkBox.setChecked(false);
			
			holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				//注意不能在这里面访问Cursor！因为此处访问的所有cursor不是每个view对应的cursor，而是最后一个view的cursor！
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					Log.d(TAG, "checked "+isChecked);
					if(isChecked)	{
						checkIds.add((Long)holder.checkBox.getTag());
//						Log.d(TAG, "add"+cursor.getLong(holder.idIndex));
						Log.d(TAG, "add"+(Long)holder.checkBox.getTag());
					}
					else{
						checkIds.remove((Long)holder.checkBox.getTag());
//						Log.d(TAG, "remove"+cursor.getLong(holder.idIndex));
						Log.d(TAG, "remove"+(Long)holder.checkBox.getTag());
					}
					if(checkIds.isEmpty()){
						deleteButton.setClickable(false);
						deleteButton.setBackgroundResource(R.drawable.button_delete_unable);
					}else{
						deleteButton.setClickable(true);
						deleteButton.setBackgroundResource(R.drawable.enforce_button_delete);
					}
				}
			});
			holder.name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Fragment infoFragment=getFragmentManager().findFragmentByTag("info_detail_tag");
					if(infoFragment==null){
						Log.d(TAG, "new info Fragment");
						infoFragment=new InfoDetailFragment();
					}
					Bundle bundle=new Bundle();
					bundle.putLong("enterpriseId", (Long)holder.checkBox.getTag());
					Log.d(TAG, "put param "+(Long)holder.checkBox.getTag());
					infoFragment.setArguments(bundle);
					
					getFragmentManager().beginTransaction()
						.hide(getFragmentManager().findFragmentByTag("info_search_tag"))
						.replace(R.id.information, infoFragment, "info_detail_tag")
						.addToBackStack(null)
						.commit();
//					Intent detailIntent=new Intent(getActivity(), InformationDetailActivity.class);
//					detailIntent.putExtra("id", (Long)holder.checkBox.getTag());
//					startActivity(detailIntent);
					
				}
			});
			holder.address.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri=Uri.parse("geo:24.801744,118.569492?q=晋江市青阳文华路182号");
					startActivity(new Intent(Intent.ACTION_VIEW, uri));
				}
			});
		}

		@Override
		public View newView(Context arg0, Cursor cursor, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = LayoutInflater.from(arg0).inflate(
					R.layout.company_info_item, null);
			ViewHolder holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.text_company_name);
			holder.area = (TextView) view.findViewById(R.id.text_company_area);
			holder.address = (TextView) view
					.findViewById(R.id.text_company_address);
			holder.responser = (TextView) view
					.findViewById(R.id.text_responsor);
			holder.phone = (TextView) view.findViewById(R.id.text_phone1);
			holder.date = (TextView) view.findViewById(R.id.text_valid_date);
			holder.checkBox=(CheckBox) view.findViewById(R.id.check_company_select);

			holder.nameIndex = cursor
					.getColumnIndexOrThrow(Enterprise.COLUMN_NAME);
			holder.areaIndex = cursor.getColumnIndexOrThrow(Area.COLUMN_NAME);
			holder.addressIndex = cursor
					.getColumnIndexOrThrow(Enterprise.COLUMN_ADDRESS);
			holder.responserIndex = cursor
					.getColumnIndexOrThrow(EnterprisePerson.COLUMN_NAME);
			Log.d(TAG, "responserindex:" + holder.responserIndex);
			holder.phoneIndex = cursor
					.getColumnIndexOrThrow(EnterprisePerson.COLUMN_PHONE);
			holder.dateIndex = cursor
					.getColumnIndexOrThrow(Filing.COLUMN_VALID_DATE);
			holder.idIndex = cursor
					.getColumnIndexOrThrow(Enterprise._ID);
			
			view.setTag(holder);

			return view;
		}

	}
}
