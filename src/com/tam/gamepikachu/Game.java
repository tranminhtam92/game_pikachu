package com.tam.gamepikachu;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.tam.pikachu.config.Config;

import android.content.Context;
import android.os.Bundle;
/**
* Cài đặt 1 số phương thức cần thiết
* @author VAN GIOI
*
*/
public class Game extends BaseGameActivity {
    //Các thuộc tính
    public Camera mCamera = null;
    public Context mContext;
    public Engine mEngine = null;
    public Scene mScene = null;
 
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        mContext = this;
        //Đặt hiệu ứng chuyển các activity
        this.getWindow().getAttributes().windowAnimations = R.style.Animations_Activity;
    }
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
 
    @Override
    public void onLoadComplete() {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public Engine onLoadEngine() {
        this.mCamera = new Camera(0, 0, Config.SCREENWIDTH, Config.SCREENHIEGHT);
        this.mEngine = new Engine(new EngineOptions(true,
                Config.ScreenOrientation_Default, new RatioResolutionPolicy(
                        Config.SCREENWIDTH, Config.SCREENHIEGHT), this.mCamera)
                .setNeedsSound(true).setNeedsMusic(true));
 
        return mEngine;
    }
 
    @Override
    public void onLoadResources() {
 
    }
 
    @Override
    public Scene onLoadScene() {
        mScene = new Scene();
        mScene.setTouchAreaBindingEnabled(true);
        return this.mScene;
    }
 
}
