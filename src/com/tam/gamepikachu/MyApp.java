package com.tam.gamepikachu;

import com.tam.pikachu.util.UtilActivity;

import android.app.Activity;
import android.os.Bundle;

public class MyApp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UtilActivity.requestWindowFeature(this);
		this.getWindow().getAttributes().windowAnimations = R.style.Animations_Activity;
	}
}
