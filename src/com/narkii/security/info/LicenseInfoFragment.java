package com.narkii.security.info;

import java.io.FileNotFoundException;

import com.narkii.security.R;
import com.narkii.security.common.Constants;
import com.narkii.security.common.MediaFileStorage;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class LicenseInfoFragment extends Fragment{
	private View view;
	
	private Button fileButton,captureButton,uploadButton;
	private ImageView previewImage;
	
	private ImageView testImage;
	
	private Bitmap preBitmap=null;
	private Uri fileUri;
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
		
		testImage=(ImageView) view.findViewById(R.id.image_uploaded);
		testImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageShowDialog dialog=new ImageShowDialog(((BitmapDrawable)testImage.getDrawable()).getBitmap());
				dialog.show(getActivity().getFragmentManager(), "show_image");
			}
		});
		captureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = MediaFileStorage.getOutputMediaFileUri(MediaFileStorage.MEDIA_TYPE_IMAGE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		
		fileButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setType("image/*");	//如何调用文件管理器呢？此处只是图片
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, Constants.CONTENT_GET_ACTIVITY_REQUEST_CODE);
			}
		});
		uploadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(preBitmap!=null){
					//上传证件到本地
				}
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("app", "on activity result");
		//相机结果回调
		if(requestCode==Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			Log.d("app", "on activity result camera");
			if(resultCode== Activity.RESULT_OK){
				//为什么data.getData()会报NullPointer？
//				 Toast.makeText(this, "Image saved to:\n" +
//	                     data.getData(), Toast.LENGTH_LONG).show();
//				 textView.append("Image saved to:\n" +data.getData());
//				textView.append("Image saved to:\n" +fileUri);
				 ContentResolver cr= getActivity().getContentResolver();
				 try {
					preBitmap=BitmapFactory.decodeStream(cr.openInputStream(fileUri));
					previewImage.setImageBitmap(preBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
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
				Log.d("app", "uri= "+fileUri.getPath());
				Log.d("app", "uri2= "+fileUri);
				 ContentResolver cr= getActivity().getContentResolver();
				 try {
					preBitmap=BitmapFactory.decodeStream(cr.openInputStream(fileUri));
					previewImage.setImageBitmap(preBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}else if(resultCode== Activity.RESULT_CANCELED){
				Toast.makeText(getActivity(), "cancel file select", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
			}
		}
	}
}
