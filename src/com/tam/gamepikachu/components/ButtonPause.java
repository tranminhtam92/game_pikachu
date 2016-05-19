package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import com.tam.gamepikachu.Play;
import com.tam.pikachu.config.Config;

import android.content.Context;

public class ButtonPause extends MySprite {

	BitmapTextureAtlas buttonpause_BTA;
	Sprite buttonpause_SP;
	TextureRegion buttonpause_TR;

	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		this.ini(mContext, mEngine, mCamera);
	}

	@Override
	public void onLoadResources() {
		this.buttonpause_BTA = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.buttonpause_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonpause_BTA, mContext,
				"pause.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(this.buttonpause_BTA);
	}

	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;

		// Tính toán các vị trí và chi�?u cao, rộng
		int y = 0;
		int w = (int) (this.buttonpause_TR.getWidth() * Config.getRaceWidth());
		int h = (this.buttonpause_TR.getHeight() * w / this.buttonpause_TR.getWidth());
		int x = Config.SCREENWIDTH - w - 5;

		this.buttonpause_SP = new Sprite(x, y, w, h, this.buttonpause_TR) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					//Menu.mSound.playClick();
					buttonpause_SP.setScale(1.3f);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					buttonpause_SP.setScale(1f);
					onClickButtonPause();
				}
				return true;
			}
		};
		this.mScene.registerTouchArea(buttonpause_SP);
		this.mScene.attachChild(buttonpause_SP);
	}

	/**
	 * Tạm dừng game
	 */
	public void onClickButtonPause() {
		Play.mPlay.pauseGame();
	}

	public float getYPause() {
		return this.buttonpause_SP.getY() + this.buttonpause_TR.getHeight();
	}

	public float getStartX() {
		return this.buttonpause_SP.getX() - 20;
	}

	public float getMidY() {
		return (10 + buttonpause_SP.getHeight()) / 2;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

}
