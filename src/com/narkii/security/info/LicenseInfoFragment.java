package com.narkii.security.info;

import java.io.FileNotFoundException;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.common.MediaFileStorage;
import com.narkii.security.data.DbCursorLoader;
import com.narkii.security.data.DbOperations;
import com.narkii.security.data.EnforceSysContract.Permission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LicenseInfoFragment extends Fragment implements LoaderCallbacks<Cursor>{
	public static final String TAG="LicenseInfoFragment";
	
	private View view;
	
	private EditText fileName;
	private Button fileButton,captureButton,uploadButton;
	private ImageView previewImage;
	private GridView gridView;
	private GridViewAdapter gridViewAdapter;
	
	private Bitmap preBitmap=null;
	private Uri fileUri;
	private boolean isImage=true;
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.INSERT_UPLOADED_OK_MSG:
				Log.d(TAG, "handle msg");
				long id=getArguments().getLong("enterpriseId",0);
				Bundle bundle=new Bundle();
				bundle.putLong("id", id);
				getLoaderManager().restartLoader(Constants.PERMISSION_IMAGE_ID, bundle, LicenseInfoFragment.this);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_info_license, null);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		fileButton=(Button) view.findViewById(R.id.button_add_local);
		captureButton=(Button) view.findViewById(R.id.button_add_capture);
		uploadButton=(Button) view.findViewById(R.id.button_upload);
		previewImage=(ImageView) view.findViewById(R.id.image_preview);
		gridView=(GridView) view.findViewById(R.id.image_uploaded);
		fileName=(EditText) view.findViewById(R.id.text_file_name);
		
		gridViewAdapter=new GridViewAdapter(getActivity(), null, false);
		gridView.setAdapter(gridViewAdapter);
		

		captureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = MediaFileStorage.getOutputMediaFileUri(MediaFileStorage.MEDIA_TYPE_IMAGE);
				Log.d(TAG, fileUri.toString());
				Log.d(TAG, fileUri.getPath());
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				getParentFragment().startActivityForResult(i, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		
		fileButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setType("*/*");	//如何调用文件管理器呢？此处只是图片("image/*")
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				//因为使用了嵌入的Fragment（子Fragment），所以必须调用父Fragment的该方法，才能使得父Fragment得到Result，从而手动传给本Fragment
				getParentFragment().startActivityForResult(intent, Constants.CONTENT_GET_ACTIVITY_REQUEST_CODE);
			}
		});
		uploadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(preBitmap!=null && !fileName.getText().toString().equals("")){
					//上传证件到本地
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							DbOperations operations=DbOperations.getInstance(getActivity());
							ContentValues values=new ContentValues();
							values.put(Permission.COLUMN_FK_ENTERPRISE_ID, getArguments().getLong("enterpriseId",0));
							Log.d(TAG, "storage file:"+fileUri.toString());
							Log.d(TAG, "storage file:"+fileUri.getPath());
							values.put(Permission.COLUMN_URL, fileUri.toString()); 
							values.put(Permission.COLUMN_CERTIFICATE_NAME, fileName.getText().toString());
							if(isImage)	values.put(Permission.COLUMN_TYPE, 1);
							else 	values.put(Permission.COLUMN_TYPE, 2);
							long result=operations.insert(Permission.TABLE_NAME, values);
							if(result>0){
								Message msg=new Message();
								msg.what=Constants.INSERT_UPLOADED_OK_MSG;
								handler.sendMessage(msg);
							}
						}
					}).start();
				}else{
					Toast.makeText(getActivity(), "please select file and input name", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		long id=getArguments().getLong("enterpriseId",0);
		Bundle bundle=new Bundle();
		bundle.putLong("id", id);
		getLoaderManager().initLoader(Constants.PERMISSION_IMAGE_ID, bundle, this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "on activity result");
		//相机结果回调
		if(requestCode==Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			Log.d(TAG, "on activity result camera");
			if(resultCode== Activity.RESULT_OK){
				//为什么data.getData()会报NullPointer？
//				 Toast.makeText(this, "Image saved to:\n" +
//	                     data.getData(), Toast.LENGTH_LONG).show();
				 ContentResolver cr= getActivity().getContentResolver();
				 try {
					preBitmap=BitmapFactory.decodeStream(cr.openInputStream(fileUri));
					previewImage.setImageBitmap(preBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isImage=true; 
			}else if(resultCode== Activity.RESULT_CANCELED){
				Toast.makeText(getActivity(), "cancel camera", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
			}
		}
		//本地文件结果回调
		if(requestCode==Constants.CONTENT_GET_ACTIVITY_REQUEST_CODE){
			//如何实现文件和图片的区分？？？
			if(resultCode== Activity.RESULT_OK){
				fileUri=data.getData();
				String path=fileUri.getPath();
				Log.d(TAG, "uri= "+fileUri.getPath());
				Log.d(TAG, "uri2= "+fileUri);
				if(path.contains("/external/images/media")){//图片
					isImage=true;
					ContentResolver cr= getActivity().getContentResolver();
					 try {
						preBitmap=BitmapFactory.decodeStream(cr.openInputStream(fileUri));
						previewImage.setImageBitmap(preBitmap);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{//其他类型，当做文件处理
					isImage=false;
				}
			}else if(resultCode== Activity.RESULT_CANCELED){
				Toast.makeText(getActivity(), "cancel file select", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class GridViewAdapter extends CursorAdapter{
		ContentResolver cr=getActivity().getContentResolver();
		class ViewHolder{
			TextView title;
			ImageView image;
			int titleIndex,imageIndex;
		}
		public GridViewAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=LayoutInflater.from(context).inflate(R.layout.uploaded_item, null);
			ViewHolder holder=new ViewHolder();
			holder.title=(TextView) view.findViewById(R.id.item_textview);
			holder.image=(ImageView) view.findViewById(R.id.item_image);
			
			holder.imageIndex=cursor.getColumnIndex(Permission.COLUMN_URL);
			holder.titleIndex=cursor.getColumnIndex(Permission.COLUMN_CERTIFICATE_NAME);
			
			view.setTag(holder);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			final ViewHolder holder=(ViewHolder) view.getTag();
			holder.title.setText(cursor.getString(holder.titleIndex));
			String path=cursor.getString(holder.imageIndex);

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds=false;
			options.inSampleSize=10;
			
			Bitmap bitmap=null;
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(path)), null, options);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(bitmap==null)	Log.d(TAG, "bitmap is null :"+path);
			
			holder.image.setImageBitmap(bitmap);
			Log.d(TAG,path);
			holder.image.setTag(path);
			
			holder.image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageShowDialog dialog=new ImageShowDialog((String) v.getTag(),getActivity());
					dialog.show(getActivity().getFragmentManager(), "show_image");
				}
			});
		}
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		DbCursorLoader cursorLoader= null;
		final DbOperations operations=DbOperations.getInstance(getActivity());
		final long rowId=bundle.getLong("id");
		if(id==Constants.PERMISSION_IMAGE_ID){
			cursorLoader=new DbCursorLoader(getActivity()) {
				
				@Override
				public Cursor getDbCursor() {
					// TODO Auto-generated method stub
					Log.d(TAG, "query the images");
					Cursor cursor=operations.query(Permission.TABLE_NAME, null, Permission.COLUMN_FK_ENTERPRISE_ID+"=? AND "+Permission.COLUMN_TYPE+"=?", new String[]{""+rowId,"1"});
					Log.d(TAG, "result: "+cursor.getCount());
					return cursor;
				}
			};
		}else if(id==Constants.PERMISSION_FILE_ID){
			
		}
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		if(loader.getId()==Constants.PERMISSION_IMAGE_ID){
			
			int counts=data.getCount();
			 DisplayMetrics dm = new DisplayMetrics();  
		     getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);  
		     float density=dm.density;
		     int allWidth = (int) (210 * counts * density);  
	        int itemWidth = (int) (200 * density);  
	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(  
	                allWidth, LinearLayout.LayoutParams.WRAP_CONTENT);  
	        gridView.setLayoutParams(params);  
	        gridView.setColumnWidth(itemWidth);  
	        gridView.setHorizontalSpacing(10);  
	        gridView.setStretchMode(GridView.NO_STRETCH);  
	        gridView.setNumColumns(counts);  
	        gridViewAdapter.swapCursor(data);
		}else if(loader.getId()==Constants.PERMISSION_FILE_ID){
			
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
}
