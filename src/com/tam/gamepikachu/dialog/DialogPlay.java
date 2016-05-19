package com.tam.gamepikachu.dialog;
import com.tam.gamepikachu.Play;
import com.tam.gamepikachu.R;

import com.tam.pikachu.util.Util;
import com.tam.pikachu.util.UtilDialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class DialogPlay extends Dialog {
	Activity activity;

	/**
	 *
	 * @param context
	 * @param playnext:
	 *            Xác định là reset game hay là bắt đầu chơi mới
	 */
	public DialogPlay(Context context, final int playnext) {
		super(context);
		UtilDialog.iniDialog(this);
		activity = (Activity) context;

		setContentView(R.layout.dialog_play);

		// resize dialog
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		Util.resizeDialog(linearLayout1);

		Button play = (Button) findViewById(R.id.play);
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Menu.mSound.playClick();
				if (playnext == 0)
					Play.mPlay.startGame();
				else
					Play.mPlay.resetGame();
				DialogPlay.this.dismiss();
			}
		});

	

	}

}
