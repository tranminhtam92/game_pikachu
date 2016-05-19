package com.tam.gamepikachu;
import com.tam.pikachu.config.Config;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
/**
* Hiện thị phần cài đặt. Chọn theme, tắt bật nhạc nền, âm thanh
* @author VAN GIOI
*
*/
public class Setting extends MyApp implements OnClickListener {
    public static boolean isSound = true;
    public static boolean isMusic = true;
 
    Button button_back;
    CheckBox checkBox_music, checkBox_sound, checkBox_animal, checkBox_fruits,
            checkBox_emotions, checkBox_classic, checkBox_Birzzle;
 
    public static String[] nameTheme = new String[] { "Animal", "Emotions",
            "Classic", "Birzzle", "Fruits" };

    MySharedPreferences mySharedPreferences;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_setting);
 
        mySharedPreferences = new MySharedPreferences(this);
        mySharedPreferences.getIsSound();
        mySharedPreferences.getIsMusic();
        mySharedPreferences.getThemes();
 
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(this);
 
        checkBox_music = (CheckBox) findViewById(R.id.checkBox_music);
        checkBox_music.setOnClickListener(this);
        checkBox_music.setChecked(isMusic);
 
        checkBox_sound = (CheckBox) findViewById(R.id.checkBox_sound);
        checkBox_sound.setOnClickListener(this);
        checkBox_sound.setChecked(isSound);
 
        // Select themes
        checkBox_fruits = (CheckBox) findViewById(R.id.checkBox_fruits);
        checkBox_fruits.setOnClickListener(this);
 
        checkBox_animal = (CheckBox) findViewById(R.id.checkBox_animal);
        checkBox_animal.setOnClickListener(this);
 
        checkBox_emotions = (CheckBox) findViewById(R.id.checkBox_emotions);
        checkBox_emotions.setOnClickListener(this);
 
        checkBox_classic = (CheckBox) findViewById(R.id.checkBox_classic);
        checkBox_classic.setOnClickListener(this);
 
        checkBox_Birzzle = (CheckBox) findViewById(R.id.checkBox_Birzzle);
        checkBox_Birzzle.setOnClickListener(this);
 
        setThemes(Config.THEMES);
    }
 
    @Override
    public void onClick(View v) {
        //Menu.mSound.playClick();
 
        switch (v.getId()) {
        case R.id.button_back:
            this.finish();
            break;
        case R.id.checkBox_music:
            isMusic = checkBox_music.isChecked();
            mySharedPreferences.updateIsSound(isMusic);
            break;
        case R.id.checkBox_sound:
            isSound = checkBox_sound.isChecked();
            mySharedPreferences.updateIsSound(isSound);
            break;
 
        // Set themes
        case R.id.checkBox_animal:
            setThemes(1);
            break;
        case R.id.checkBox_emotions:
            setThemes(2);
            break;
        case R.id.checkBox_classic:
            setThemes(3);
            break;
        case R.id.checkBox_Birzzle:
            setThemes(4);
            break;
        case R.id.checkBox_fruits:
            setThemes(5);
            break;
        default:
            break;
        }
    }
 
    public void setThemes(int themes) {
        Config.THEMES = themes;
        if (Config.THEMES == 1) {
            checkBox_animal.setChecked(true);
        } else {
            checkBox_animal.setChecked(false);
        }
 
        if (Config.THEMES == 2) {
            checkBox_emotions.setChecked(true);
        } else {
            checkBox_emotions.setChecked(false);
        }
 
        if (Config.THEMES == 3) {
            checkBox_classic.setChecked(true);
        } else {
            checkBox_classic.setChecked(false);
        }
 
        if (Config.THEMES == 4) {
            checkBox_Birzzle.setChecked(true);
        } else {
            checkBox_Birzzle.setChecked(false);
        }
 
        if (Config.THEMES == 5) {
            checkBox_fruits.setChecked(true);
        } else {
            checkBox_fruits.setChecked(false);
        }
     
        mySharedPreferences.updateThemes(themes);
 
        Config.pathTHEME = "item/theme" + Config.THEMES + "/";
    }
 
    @Override
    public void onBackPressed() {
        //Menu.mSound.playClick();
        this.finish();
    }
 
}
