package com.narkii.security.enforce;

import java.util.ArrayList;
import java.util.List;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Document;
import com.narkii.security.data.EnforceSysContract.DocumentType;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EnforceRecordFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="EnforceRecordFragment";
	private View view;
	private ListView enforceRecordsList;
	private EnforceRecordsAdapter enforceRecordAdapter;
	private Button deleteButton;
	private Spinner paperSpin;
	private SimpleCursorAdapter paperAdapter;
	private Bundle bundleLog;	//用于记录当前选中的企业id，在删除文书后从新加载文书
	private boolean isAddPaper=false;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.INFO_DELETE_ITEM_OK_MSG:
				deleteButton.setClickable(false);
				deleteButton.setBackgroundResource(R.drawable.button_delete_unable);
				getLoaderManager().restartLoader(Constants.LIST_ENFORCE_RECORD_ID, bundleLog, EnforceRecordFragment.this);
				Log.d(TAG, "reloader the list data");
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		paperSpin = (Spinner) view.findViewById(R.id.enforce_spinner_paper1);
		deleteButton = (Button) view.findViewById(R.id.enforce_button_delete);
		enforceRecordsList = (ListView) view
				.findViewById(R.id.enforce_list_record);
		enforceRecordAdapter = new EnforceRecordsAdapter(getActivity(), null,
				false);
		enforceRecordsList.setAdapter(enforceRecordAdapter);
		
		paperAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null,
				new String[] { DocumentType.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		paperSpin.setAdapter(paperAdapter);
	
		initListener();
		
		bundleLog=getArguments();
		
		getLoaderManager().initLoader(Constants.SPINNER_DOCUMENT_TYPE_ID,
				null, this);
		
		getLoaderManager().restartLoader(
				Constants.LIST_ENFORCE_RECORD_ID, bundleLog,
				this);
		
	}
	private void initListener(){
	
		paperSpin.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d(TAG, "on paperspin click");
				isAddPaper=true;
				return false;
			}
		});
		paperSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "select item"+position);
				((TextView)view).setText("");
				if(!isAddPaper) return ;
				Fragment fragment=null;
				switch (position) {
					case 0:
						fragment=new InspectFragment();
						fragment.setArguments(bundleLog);
						getFragmentManager().beginTransaction()
							.hide(getFragmentManager().findFragmentByTag("enforce_record_tag"))
							.replace(R.id.enforce_record, fragment, "enforce_paper_tag")
							.addToBackStack(null)
							.commit();
						break;
					case 1:
						break;
					case 2:
						break;
					default:
						break;
				}
				
				isAddPaper=false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				//当下拉列表选的内容跟原先没有变化时，回调此方法！
			}
		});

		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
				builder.setTitle("确定要删除？")
						.setMessage("删除选中的信息后将无法复原！请谨慎操作！");
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								DbOperations operations=DbOperations.getInstance(getActivity());
								List<Long> lists=enforceRecordAdapter.getCheckIds();
								if(lists.size()==0) return;
								Log.d(TAG, "list size:"+lists.size());
								String[][] args=new String[lists.size()][];
								for(int i=0;i<lists.size();i++){
									args[i]=new String[]{""+lists.get(i)};
									Log.d(TAG, "list "+i+": "+lists.get(i));
								}
								int result=operations.delete(Document.TABLE_NAME, Document._ID+"=?", args);
								if(result>0){
									Message msg=new Message();
									msg.what=Constants.INFO_DELETE_ITEM_OK_MSG;
									handler.sendMessage(msg);
									Log.d(TAG, "send the message "+result);
									enforceRecordAdapter.clearChecks();
									
								}
							}
						}).start();
					}
				});
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				builder.create().show();
			}
			
		});
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.enforce_record, null);
		return view;
	}



	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		DbCursorLoader dbLoader = null;
		if (id == Constants.SPINNER_DOCUMENT_TYPE_ID) {
			dbLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					Cursor cursor = operations.query(DocumentType.TABLE_NAME,
							null, null, null);
					return cursor;
				}
			};
		} else if (id == Constants.LIST_ENFORCE_RECORD_ID) {
			final String args[] = { "" + bundle.getLong("enterpriseId") };
			Log.d(TAG, "get params:" + args[0]);
			
			dbLoader = new DbCursorLoader(getActivity()) {

				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					String wheres = Document.COLUMN_FK_ENTERPRISE_ID + "=?";
					
					String tables = Document.TABLE_NAME + " LEFT JOIN "
							+ DocumentType.TABLE_NAME + " ON ("
							+ Document.COLUMN_FK_DOCUMENT_TYPE + "="
							+ DocumentType.TABLE_NAME + "." + DocumentType._ID
							+ ")";

					String[] columns={Document.TABLE_NAME + "." + Document._ID
							,Document.COLUMN_CREATE_DATE ,
							DocumentType.COLUMN_NAME ,
							 Document.COLUMN_FK_DOCUMENT_TYPE,
							Document.COLUMN_NO,
							Document.COLUMN_OFFICER1 ,
							Document.COLUMN_CERTIFICATE_NO1 ,
							Document.COLUMN_OFFICER2 ,
							Document.COLUMN_CERTIFICATE_NO2};
					
					DbOperations operations = DbOperations
							.getInstance(getActivity());
					
					Cursor cursor=operations.joinQuery(tables, columns, wheres, args);
					Log.d(EnforceRecordFragment.TAG,
							"paper count " + cursor.getCount());

					return cursor;
				}
			};
		}
		return dbLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		if (loader.getId() == Constants.SPINNER_DOCUMENT_TYPE_ID) {
			paperAdapter.swapCursor(cursor);
		} else if (loader.getId() == Constants.LIST_ENFORCE_RECORD_ID) {
			enforceRecordAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		if (loader.getId() == Constants.SPINNER_DOCUMENT_TYPE_ID) {
			paperAdapter.swapCursor(null);
		} else if (loader.getId() == Constants.LIST_ENFORCE_RECORD_ID) {
			enforceRecordAdapter.swapCursor(null);
		}
	}
	class EnforceRecordsAdapter extends CursorAdapter {
		private List<Long> checkIds;
		
		public List<Long> getCheckIds() {
			return checkIds;
		}
		public void clearChecks(){
			checkIds.clear();
		}

		class ViewHoler {
			TextView timeText, paperText, paperNoText, officer1Text,
					officerId1Text,  remarkText;
			int idIndex,timeIndex, paperIndex,paperTypeIndex, paperNoIndex, officer1Index,
					officerId1Index, remarkIndex;
			CheckBox checkBox;
		}

		public EnforceRecordsAdapter(Context context, Cursor c,
				boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
			checkIds=new ArrayList<Long>();
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			final ViewHoler holer = (ViewHoler) view.getTag();
			holer.timeText.setText(cursor.getString(holer.timeIndex));
			holer.paperText.setText(cursor.getString(holer.paperIndex));
			holer.paperText.setTag(cursor.getInt(holer.paperTypeIndex));
			
			Log.d(TAG, "index 2:" + cursor.getString(holer.paperIndex));

			holer.paperNoText.setText(cursor.getString(holer.paperNoIndex));
			holer.officer1Text.setText(cursor.getString(holer.officer1Index));
			holer.officerId1Text.setText(cursor
					.getString(holer.officerId1Index));
			holer.checkBox.setTag(cursor.getLong(holer.idIndex));
			holer.checkBox.setChecked(false);
			holer.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
						checkIds.add((Long)buttonView.getTag());
					}else{
						checkIds.remove((Long)buttonView.getTag());
					}
					if(checkIds.size()>0){
						deleteButton.setClickable(true);
						deleteButton.setBackgroundResource(R.drawable.enforce_button_delete);
					}else{
						deleteButton.setClickable(false);
						deleteButton.setBackgroundResource(R.drawable.button_delete_unable);
					}
				}
			});
			holer.paperText.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int paperType=(Integer)v.getTag();
					Fragment fragment=null;
					switch (paperType) {
						case 1:
							fragment=new InspectFragment();
							Bundle bundle=new Bundle();
							bundle.putLong("enterpriseId", bundleLog.getLong("enterpriseId"));
							bundle.putLong("recordId", (Long)holer.checkBox.getTag());
							fragment.setArguments(bundle);
							getFragmentManager().beginTransaction()
								.hide(getFragmentManager().findFragmentByTag("enforce_record_tag"))
								.replace(R.id.enforce_record, fragment, "enforce_paper_tag")
								.addToBackStack(null)
								.commit();
							Log.d(TAG,"entry count:"+getFragmentManager().getBackStackEntryCount());
							break;
						case 2:
							break;
						case 3:
							break;
						default:
							break;
					}
					
				}
			});
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			View view = LayoutInflater.from(context).inflate(
					R.layout.enforce_record_item, null);
			ViewHoler holer = new ViewHoler();
			holer.timeText = (TextView) view.findViewById(R.id.text_time);
			holer.timeIndex = cursor
					.getColumnIndex(Document.COLUMN_CREATE_DATE);
			holer.paperText = (TextView) view.findViewById(R.id.text_paper);
			holer.paperIndex = cursor.getColumnIndex(DocumentType.COLUMN_NAME);
			holer.paperTypeIndex=cursor.getColumnIndex( Document.COLUMN_FK_DOCUMENT_TYPE);
			Log.d(TAG, "paper index:" + holer.paperIndex);
			holer.paperNoText = (TextView) view
					.findViewById(R.id.text_paper_no);
			holer.paperNoIndex = cursor.getColumnIndex(Document.COLUMN_NO);

			holer.officer1Text = (TextView) view
					.findViewById(R.id.text_officer);
			holer.officer1Index = cursor
					.getColumnIndex(Document.COLUMN_OFFICER1);
			
			holer.officerId1Text = (TextView) view
					.findViewById(R.id.text_officer_id);
			holer.officerId1Index = cursor
					.getColumnIndex(Document.COLUMN_CERTIFICATE_NO1);
			
			holer.checkBox=(CheckBox) view.findViewById(R.id.checkbox);
			holer.idIndex=cursor.getColumnIndex(Document._ID);
			
			view.setTag(holer);
			return view;
		}

	}
}
