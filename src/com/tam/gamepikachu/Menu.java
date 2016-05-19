package com.tam.gamepikachu;

import com.tam.gamepikachu.dialog.DialogExit;
import com.tam.pikachu.config.Config;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends MyApp implements OnClickListener {
 
    Button play, help, setting, more, exit, top10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Config.getDisplayScreen(this);
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(this);
 
        help = (Button) findViewById(R.id.help);
        help.setOnClickListener(this);
 
        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(this);
 
        more = (Button) findViewById(R.id.more);
        more.setOnClickListener(this);
 
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);
 
        top10 = (Button) findViewById(R.id.top10);
        top10.setOnClickListener(this);
    }
 
    // ------------------------------------------------------
    @Override
    public void onClick(View v) {
        // Add sound click
        //mSound.playClick();
 
        switch (v.getId()) {
        case R.id.play:
            clickPlay();
            break;
        case R.id.help:
            clickHelp();
            break;
        case R.id.setting:
            clickSetting();
            break;
        case R.id.more:
            nextApplicationOther();
            break;
        case R.id.exit:
            clickExit();
            break;
        case R.id.top10:
            clickTop10();
            break;
        default:
            break;
        }
    }
 
    // ------------------------------------------------------
   public void clickPlay() {
        try {
            Intent intent = new Intent(this, Play.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickHelp() {
        try {
            Intent intent = new Intent(this, Help.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickExit() {
        try {
            DialogExit dialogExit = new DialogExit(this);
            dialogExit.show();
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickSetting() {
        try {
            Intent intent = new Intent(this, Setting.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickTop10() {
        try {
            Intent intent = new Intent(this, HeightDollar.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
    // ------------------------------------------------------
    public void nextApplicationOther() {
        try {
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:Truong531.developer+inc"));
            startActivity(marketIntent);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    // ------------------------------------------------------
    @Override
    public void onBackPressed() {
        //mSound.playClick();
        //clickExit();
    }
}
