package com.tam.gamepikachu.dialog;


import com.tam.gamepikachu.MySharedPreferences;
import com.tam.gamepikachu.Play;
import com.tam.gamepikachu.R;
import com.tam.gamepikachu.Setting;
import com.tam.pikachu.util.Util;
import com.tam.pikachu.util.UtilDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class DialogSetting extends Dialog implements android.view.View.OnClickListener {

	Button yes;
	Activity activity;
	CheckBox checkBox_music, checkBox_sound;

	MySharedPreferences mySharedPreferences;

	public DialogSetting(Context context) {
		super(context);
		UtilDialog.iniDialog(this);
		activity = (Activity) context;
		setContentView(R.layout.dialog_setting);

		mySharedPreferences = new MySharedPreferences(context);
		mySharedPreferences.getIsMusic();
		mySharedPreferences.getIsSound();

		// resize dialog
		RelativeLayout linearLayout1 = (RelativeLayout) findViewById(R.id.linearLayout1);
		Util.resizeDialog(linearLayout1);

		yes = (Button) findViewById(R.id.yes);
		yes.setOnClickListener(this);

		checkBox_music = (CheckBox) findViewById(R.id.checkBox_music);
		checkBox_music.setOnClickListener(this);
		checkBox_music.setChecked(Setting.isMusic);

		checkBox_sound = (CheckBox) findViewById(R.id.checkBox_sound);
		checkBox_sound.setOnClickListener(this);
		checkBox_sound.setChecked(Setting.isSound);
	}

	@Override
	public void onClick(View v) {
		//Menu.mSound.playClick();

		switch (v.getId()) {
		case R.id.yes:
			this.dismiss();
			break;
		case R.id.checkBox_music:
			Setting.isMusic = checkBox_music.isChecked();
			if (Setting.isMusic) {
				//Play.mPlay.musicBackground.resume();
			} else {
				//Play.mPlay.musicBackground.pause();
			}
			mySharedPreferences.updateIsMusic(Setting.isMusic);
			break;
		case R.id.checkBox_sound:
			Setting.isSound = checkBox_sound.isChecked();
			mySharedPreferences.updateIsSound(Setting.isSound);
			break;

		default:
			break;
		}

	}

}
