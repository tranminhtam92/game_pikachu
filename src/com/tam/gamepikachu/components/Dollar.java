package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import com.tam.gamepikachu.Play;
import com.tam.pikachu.config.Config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class Dollar extends MySprite {

	BitmapTextureAtlas dollar_BTA;
	Sprite dollar_SP;
	TextureRegion dollar_TR;

	BitmapTextureAtlas mFontTexture;
	Font mFont;
	int heightFont = 30;// Chi�?u cao của text

	ChangeableText txt_dollar;

	int x_end_dollar = 0;

	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		ini(mContext, mEngine, mCamera);
	}

	// -------------------------------------------------------------
	@Override
	public void onLoadResources() {

		heightFont = (int) (heightFont * Config.getRaceHeight());

		dollar_BTA = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		dollar_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.dollar_BTA, mContext, "dollar.png", 0,
				0);
		mEngine.getTextureManager().loadTextures(this.dollar_BTA);

		mFontTexture = new BitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = new Font(this.mFontTexture, Typeface.createFromAsset(mContext.getAssets(), "font/BrushScriptStd.otf"),
				heightFont, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(this.mFontTexture);
		mEngine.getFontManager().loadFont(this.mFont);
	}

	// -------------------------------------------------------------
	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;

		int x = 5;
		int y = 0;
		int w = (int) (this.dollar_TR.getWidth() * Config.getRaceWidth());
		int h = (this.dollar_TR.getHeight() * w / this.dollar_TR.getWidth());

		this.dollar_SP = new Sprite(x, y, w, h, this.dollar_TR);
		this.mScene.attachChild(dollar_SP);

		x = x + w;
		y = y + (h / 2 - heightFont / 2);
		txt_dollar = new ChangeableText(x, y, this.mFont, "", 30);

		x_end_dollar = x + this.mFont.getStringWidth("0000000 - L.00");

		updateDollar();
		this.mScene.attachChild(txt_dollar);
	}

	// -------------------------------------------------------------
	public void addDollar(int dl) {
		Play.mPlay.dollar_current = Play.mPlay.dollar_current + dl;
		updateDollar();
	}

	// -------------------------------------------------------------
	public void updateDollar() {
		String s = String.valueOf(Play.mPlay.dollar_current);
		if (Play.mPlay.dollar_current == 0)
			s = "000";
		s = s + " - L." + String.valueOf(Level.levelCurrent);
		txt_dollar.setText(s);
	}

	// -------------------------------------------------------------
	public void reset() {
		Play.mPlay.dollar_current = Play.mPlay.total_dollar;
		updateDollar();
	}

	// -------------------------------------------------------------
	public int getXendDollar() {
		return x_end_dollar;
	}

	// -------------------------------------------------------------
	public int getEndY() {
		return (int) (dollar_SP.getY() + dollar_SP.getHeight());
	}

	// -------------------------------------------------------------
	@Override
	public void onDestroy() {

	}

	// -------------------------------------------------------------
	/**
	 * Thêm hiệu ứng text trừ điểm khi lựa ch�?n trợ giúp
	 */
	public void addTextSubDollar(String txt_tmp) {
		try {
			int x = Config.getCenterX() - mFont.getStringWidth(txt_tmp) / 2;
			int y = Config.getCenterY() - heightFont / 2;
			final ChangeableText txt = new ChangeableText(x, y, mFont, txt_tmp);
			mScene.attachChild(txt);

			MoveModifier move = new MoveModifier(2f, x, x, y, -100);
			SequenceEntityModifier modifier = new SequenceEntityModifier(
					new SequenceEntityModifier.IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							removeChangeableText(txt);
						}
					}, move);
			txt.registerEntityModifier(modifier);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// -------------------------------------------------------------
	/**
	 * Xóa b�? 1 ChangeableText
	 * 
	 * @param txt
	 */
	public void removeChangeableText(final ChangeableText txt) {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					mScene.detachChild(txt);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

}
