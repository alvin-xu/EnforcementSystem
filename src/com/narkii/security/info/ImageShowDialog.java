package com.narkii.security.info;

import java.io.FileNotFoundException;

import com.narkii.security.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


@SuppressLint("ValidFragment")
public class ImageShowDialog extends DialogFragment{
	public static final String TAG="ImageShowDialog";
	
	Bitmap bitmap;
//	Matrix matrix;
	View view;
	Button b1,b2;
	ImageView imageView;

	public ImageShowDialog(String path,Context context) {
		super();
		Log.d(TAG, "image show dialog:"+path);
		try {
//			如下调用会报nullpointer，因为构造函数中还没有上下文。
//			ContentResolver cr=getActivity().getContentResolver();
			ContentResolver cr=context.getContentResolver();
			this.bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		matrix=new Matrix();
	}


	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction

	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ImageDialogStyle);
	        LayoutInflater inflater=getActivity().getLayoutInflater();
	        
	        view=inflater.inflate(R.layout.dialog_license_image, null);
	        
			b1=(Button) view.findViewById(R.id.button_left_turn);
			b2=(Button) view.findViewById(R.id.button_right_turn);
			imageView=(ImageView) view.findViewById(R.id.image_show);
			imageView.setImageBitmap(bitmap);
			
			b1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Matrix matrix=new Matrix();
					matrix.postRotate(90);//顺时针
//					Bitmap reBitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageMatrix(matrix);
				}
			});
			b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Matrix matrix=new Matrix();
					matrix.postRotate(-90);
//					Bitmap reBitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageMatrix(matrix);
				}
			});
	        
	        
	        builder.setView(view)
	        		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               })
	               .setPositiveButton(R.string.print, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                   }
	               });
	        Dialog dialog=builder.create();
//	        WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//			lp.width=bitmap.getWidth();
//			lp.height=bitmap.getHeight();
//			dialog.getWindow().setAttributes(lp);
	        // Create the AlertDialog object and return it
	        return dialog;
	    }
}
