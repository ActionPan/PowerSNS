package com.example.powersns;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.sns.servers.RegisterNewAccount_servers;
import com.sns.servers.UpdateInfo_Service;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DataActivity extends Activity {
	EditText et_nickname, et_age, et_mood;
	RadioGroup rg_gander;
	RadioButton rb_gander_boy, rb_gander_girl;
	Button bt_confirm, bt_cancel;
	String Uid, nickname, age, mood, gander;

	private Button img_btn;
	private Button local_picture, up_load;
	private ImageView perfect_image;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_data);

		// 控件识别
		init();
		// 加载数据
		load();
		//三个按钮拍照
		bind();

		// 将修改的资料上传到服务器
		bt_confirm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rb_gander_girl.isChecked()) {
					gander = "女";
				}
				if (rb_gander_boy.isChecked()) {
					gander = "男";
				}
				RegTask(Uid, et_nickname.getText().toString(), et_age.getText()
						.toString(), gander, et_mood.getText().toString());
			}
		});
		bt_cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DataActivity.this,
						MainActivity.class);
				// intent.putExtra("Uid",Uid);
				DataActivity.this.startActivity(intent);

				DataActivity.this.finish();
			}
		});

	}

	public void init() {
		et_nickname = (EditText) findViewById(R.id.data_nickname);
		et_age = (EditText) findViewById(R.id.data_age);
		et_mood = (EditText) findViewById(R.id.data_mood);
		rg_gander = (RadioGroup) findViewById(R.id.data_gander);
		rb_gander_boy = (RadioButton) findViewById(R.id.data_gander_boy);
		rb_gander_girl = (RadioButton) findViewById(R.id.data_gander_girl);
		bt_confirm = (Button) findViewById(R.id.data_confirm);
		bt_cancel = (Button) findViewById(R.id.data_cancel);

		img_btn = (Button) findViewById(R.id.camera_btn);
		local_picture = (Button) findViewById(R.id.local_picture);
		up_load = (Button) findViewById(R.id.up_load);
		perfect_image = (ImageView) findViewById(R.id.perfect_image);
	}

	public void bind() {
		local_picture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show();
			}
		});
		img_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show1();
			}
		});
		up_load.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void show1() {

		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}

	private void show() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO://当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;

		case PHOTO_REQUEST_GALLERY://当选择从本地获取图片时
			//做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null)
				startPhotoZoom(data.getData(), 150);
			break;

		case PHOTO_REQUEST_CUT://返回的结果
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	//将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			perfect_image.setBackgroundDrawable(drawable);
		}
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	public void load() {
		Intent intent = DataActivity.this.getIntent();
		Bundle bundle = intent.getExtras();
		Uid = Global.str_UID;
		nickname = bundle.getString("nickname");
		age = bundle.getString("age");
		mood = bundle.getString("mood");
		gander = bundle.getString("gander");
		et_nickname.setText(nickname);
		et_age.setText(age);
		et_mood.setText(mood);
		if (gander.equals("女")) {
			rb_gander_girl.setChecked(true);
		}
		if (gander.equals("男")) {
			rb_gander_boy.setChecked(true);
		}

	}

	public void RegTask(String uid, String nickname, String age, String gander,
			String mood) {
		try {
			String result = new UpdateInfo_Service().execute(uid, nickname,
					age, gander, mood).get();
			if (result.equals("T")) {
				Toast.makeText(DataActivity.this, "上传成功", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(DataActivity.this, "上传失败", Toast.LENGTH_LONG)
						.show();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
