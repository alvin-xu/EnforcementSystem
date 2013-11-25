package com.narkii.security.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class MediaFileStorage {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	public static Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}
	private static File getOutputMediaFile(int type){
		File mediaStorageFile=null;
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Log.d("APP", "sdcard is't mounted!");
			return null;
		}
		mediaStorageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");
		if(!mediaStorageFile.exists()){
			if(!mediaStorageFile.mkdirs()){
				Log.d("APP", "fail to create the directory");
				return null;
			}
		}
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CHINA).format(new Date());
		File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageFile.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageFile.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
}
