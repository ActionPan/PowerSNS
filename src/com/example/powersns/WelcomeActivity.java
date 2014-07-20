package com.example.powersns;

import java.util.zip.Inflater;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class WelcomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.welcome_layout);
		LayoutInflater inflater=LayoutInflater.from(WelcomeActivity.this);
		View view=inflater.inflate(R.layout.welcome_layout,null);
		setContentView(view);
		AlphaAnimation animation =new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(3000);
		view.setAnimation(animation);
		animation.startNow();
		animation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				WelcomeActivity.this.finish();
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
