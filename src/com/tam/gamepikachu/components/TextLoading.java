package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import com.tam.pikachu.config.Config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class TextLoading extends MySprite {

	BitmapTextureAtlas mFontTexture;
	Font mFont;
	// Chi�?u cao của text ban đầu là cao 50picxel
	int heightFont = 50;
	ChangeableText txt_loading;
	boolean isStop = false;

	/**
	 * Khởi tạo
	 */
	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		ini(mContext, mEngine, mCamera);
	}

	/**
	 * Laod dữ liệu
	 */
	@Override
	public void onLoadResources() {
		mFontTexture = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = new Font(this.mFontTexture, Typeface.createFromAsset(mContext.getAssets(), "font/BrushScriptStd.otf"),
				heightFont, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(this.mFontTexture);
		mEngine.getFontManager().loadFont(this.mFont);
	}

	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		heightFont = (int) (heightFont * Config.getRaceHeight());
		int x = Config.getCenterX() - mFont.getStringWidth("Loading...") / 2;
		int y = Config.getCenterY() - heightFont / 2;
		txt_loading = new ChangeableText(x, y, this.mFont, "Loading...", 30);
		this.mScene.attachChild(txt_loading);

		// G�?i thread để tạo ra hiệu ứng loading
		threadShowTextLoading();
	}

	public void threadShowTextLoading() {
		isStop = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while (!isStop) {
					try {
						// Cứ sau 400milis thì ta đổi lại cái text để tạo ra
						// hiệu ứng text đang hiện thị loading
						Thread.sleep(400);
						if (count == 0) {
							txt_loading.setText("Loading");
							count = 1;
						} else if (count == 1) {
							txt_loading.setText("Loading.");
							count = 2;
						} else if (count == 2) {
							txt_loading.setText("Loading..");
							count = 3;
						} else if (count == 3) {
							txt_loading.setText("Loading...");
							count = 0;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	/**
	 * Hiện thị text loading
	 */
	public void showTextLoading() {
		txt_loading.setVisible(true);
		threadShowTextLoading();
	}

	/**
	 * Ẩn text loading
	 */
	public void hideTextLoading() {
		txt_loading.setVisible(false);
		isStop = true;
	}

	// Xóa b�? đối tượng text
	@Override
	public void onDestroy() {
		isStop = true;
		this.mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					// Gỡ b�? đối tượng text
					mScene.detachChild(txt_loading);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

}
