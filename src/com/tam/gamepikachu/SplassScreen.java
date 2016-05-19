package com.tam.gamepikachu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplassScreen extends MyApp {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splasscreen);

		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(SplassScreen.this, Menu.class);
				SplassScreen.this.startActivity(mIntent);
				SplassScreen.this.finish();
			}
		});

	}
}
