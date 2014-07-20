package com.example.powersns;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoAddActivity extends Activity implements OnClickListener {
	Button photo_add_back;

	private Button local_photo, camera_photo, upload_photo;
	private ImageView imageview_photo;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	// ����һ���Ե�ǰʱ��Ϊ���Ƶ��ļ�
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_list_add);

		init();
	}

	public void init() {
		local_photo = (Button) this.findViewById(R.id.local_photo);
		camera_photo = (Button) this.findViewById(R.id.camera_photo);
		upload_photo = (Button) this.findViewById(R.id.upload_photo);
		imageview_photo = (ImageView) this.findViewById(R.id.imageview_photo);

		camera_photo.setOnClickListener(this);
		local_photo.setOnClickListener(this);
		upload_photo.setOnClickListener(this);
		photo_add_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.camera_photo:
			show1();
			break;

		case R.id.local_photo:
			show();
			break;

		case R.id.upload_photo:
			show2();
			break;

		case R.id.photo_add_back:
			show3();
			break;
		}
	}

	private void show1() {

		// ����ϵͳ�����չ���
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// ָ������������պ���Ƭ�Ĵ���·��
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}

	private void show() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

	}

	private void show2() {

	}

	private void show3() {
		Intent intent=new Intent(PhotoAddActivity.this,PhotoListActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ��ѡ������ʱ����
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;

		case PHOTO_REQUEST_GALLERY:// ��ѡ��ӱ��ػ�ȡͼƬʱ
			// ���ǿ��жϣ������Ǿ��ò����������¼��õ�ʱ��㲻�ᱨ�쳣����ͬ
			if (data != null)
				startPhotoZoom(data.getData(), 150);
			break;

		case PHOTO_REQUEST_CUT:// ���صĽ��
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intent.putExtra("crop", "true");

		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// �����м��ú��ͼƬ��ʾ��UI������
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			imageview_photo.setImageDrawable(drawable);
		}
	}

	// ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

}
