package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.tam.pikachu.config.Config;

import android.content.Context;

public class Background extends MySprite {

	BitmapTextureAtlas bg_BTA;
	TextureRegion bg_TR;

	int total_bg = 1;

	// -----------------------------------------------
	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		ini(mContext, mEngine, mCamera);
	}

	// -----------------------------------------------
	@Override
	public void onLoadResources() {
		// Có thể total_bg < số level nên bằng cách chia lấy dư để đảm bảo ta
		// luôn có ảnh n�?n
		// nếu level mà > total_bg thì ảnh n�?n được lặp lại
		int index_bg =total_bg;
		this.bg_BTA = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.bg_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bg_BTA, mContext,
				"bg/bg/bg" + index_bg + ".png", 0, 0);
		mEngine.getTextureManager().loadTextures(this.bg_BTA);
	}

	// -----------------------------------------------
	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		SpriteBackground mBackground = new SpriteBackground(
				new Sprite(0, 0, Config.SCREENWIDTH, Config.SCREENHIEGHT, this.bg_TR));
		this.mScene.setBackground(mBackground);
	}

	// -----------------------------------------------
	public void resetBackground() {
		onLoadResources();
		onLoadScene(this.mScene);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

}
