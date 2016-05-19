package com.tam.gamepikachu.dialog;
import com.tam.gamepikachu.Play;
import com.tam.gamepikachu.R;
import com.tam.gamepikachu.database.ClassDollar;
import com.tam.gamepikachu.database.Database;
import com.tam.pikachu.config.Config;
import com.tam.pikachu.util.Util;
import com.tam.pikachu.util.UtilDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class DialogSaveDollar extends Dialog {
	Activity activity;
	EditText editText_name;

	public DialogSaveDollar(final Context context) {
		super(context);
		UtilDialog.iniDialog(this);
		activity = (Activity) context;

		setContentView(R.layout.dialog_savedollar);

		// resize dialog
		RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.linearLayout);
		Util.resizeDialog(linearLayout);

		editText_name = (EditText) findViewById(R.id.editText_name);

		Button button_yes = (Button) findViewById(R.id.button_yes);
		button_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Menu.mSound.playClick();

				// Náº¿u ngÆ°á»�i chÆ¡i khÃ´ng nháº­p tÃªn thÃ¬ láº¥y tÃªn máº·c Ä‘á»‹nh lÃ  Player
				String name = editText_name.getText().toString();
				if (name.length() == 0) {
					name = "Player";
				}

				Database mDatabase = new Database(context);
				mDatabase.openDatabase();
				mDatabase.addDollar(new ClassDollar(name, Play.mPlay.dollar_current, Config.THEMES));
				mDatabase.closeDatabase();
				DialogSaveDollar.this.dismiss();
				Util.showToast(context, "Save success.");

				Play.mPlay.finish();
			}
		});

	}

}
