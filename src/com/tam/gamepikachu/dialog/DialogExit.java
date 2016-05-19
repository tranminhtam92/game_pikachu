package com.tam.gamepikachu.dialog;
import com.tam.gamepikachu.R;
import com.tam.pikachu.util.Util;
import com.tam.pikachu.util.UtilDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DialogExit extends Dialog implements android.view.View.OnClickListener {

	Button yes, no;
	ImageView icon_star;
	Activity activity;

	public DialogExit(Context context) {
		super(context);
		UtilDialog.iniDialog(this);
		activity = (Activity) context;
		setContentView(R.layout.dialog_exit);

		// resize dialog
		RelativeLayout linearLayout1 = (RelativeLayout) findViewById(R.id.linearLayout1);
		Util.resizeDialog(linearLayout1);

		yes = (Button) findViewById(R.id.yes);
		yes.setOnClickListener(this);

		no = (Button) findViewById(R.id.no);
		no.setOnClickListener(this);

		icon_star = (ImageView) findViewById(R.id.icon_star);
		icon_star.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		//Menu.mSound.playClick();

		switch (v.getId()) {
		case R.id.yes:
			this.dismiss();
			activity.finish();
			break;
		case R.id.no:
			this.dismiss();
			break;
		case R.id.icon_star:
			nextRate();
			break;
		default:
			break;
		}

	}

	public void nextRate() {
		try {
			Intent marketIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=gioi.developer.pikacha_hd"));
			activity.startActivity(marketIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
