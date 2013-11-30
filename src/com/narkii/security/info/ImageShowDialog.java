package com.narkii.security.info;

import com.narkii.security.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


@SuppressLint("ValidFragment")
public class ImageShowDialog extends DialogFragment{
	public static final String TAG="ImageShowDialog";
	
	Bitmap bitmap;
	Matrix matrix;
	View view;
	Button b1,b2;
	ImageView imageView;

	public ImageShowDialog(Bitmap bitmap) {
		super();
		this.bitmap = Bitmap.createBitmap(bitmap);
		matrix=new Matrix();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.koala);
	}


	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction

	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        LayoutInflater inflater=getActivity().getLayoutInflater();
	        
	        view=inflater.inflate(R.layout.dialog_license_image, null);
	        
			b1=(Button) view.findViewById(R.id.button_left_turn);
			b2=(Button) view.findViewById(R.id.button_right_turn);
			imageView=(ImageView) view.findViewById(R.id.image_show);
			
			b1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					matrix.postRotate(90);
					Bitmap reBitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageBitmap(reBitmap);
				}
			});
			b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					matrix.postRotate(-90);
					Bitmap reBitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					imageView.setImageBitmap(reBitmap);
				}
			});
	        
	        
	        
	        builder.setView(view)
	               .setPositiveButton(R.string.print, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                   }
	               })
	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
}
