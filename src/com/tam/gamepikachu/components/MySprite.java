package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;

import com.tam.gamepikachu.Play;

import android.content.Context;

public abstract class MySprite {
	public Camera mCamera = null;

	public Context mContext = null;

	public Engine mEngine = null;

	public Scene mScene = null;

	public Play mPlay = null;

	// Có 4 method luôn cần như dưới đây

	// + Khởi tạo

	public abstract void onCreate(Context mContext, Engine mEngine, Camera mCamera);

	// + Load tài nguyên

	public abstract void onLoadResources();

	// + Add vào màn hình

	public abstract void onLoadScene(Scene mScene);

	// + Hủy b�? đối tượng

	public abstract void onDestroy();

	/**
	 * 
	 * Khởi tạo
	 * 
	 * @param mContext
	 * 
	 * @param mEngine
	 * 
	 * @param mCamera
	 * 
	 */

	public void ini(Context mContext, Engine mEngine, Camera mCamera) {

		this.mContext = mContext;

		this.mEngine = mEngine;

		this.mCamera = mCamera;

		this.mPlay = (Play) mContext;

	}
}
