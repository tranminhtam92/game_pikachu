package com.tam.gamepikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.tam.gamepikachu.Play;
import com.tam.pikachu.config.Config;

import android.content.Context;

public class ProgressBar extends MySprite {

	Rectangle mRectangle;
	float width_rect = 0;

	BitmapTextureAtlas prb_BTA;
	Sprite prb_SP;
	TextureRegion prb_TR;

	/**
	 * Tổng thời gian của level
	 */
	int total_time = 0;

	/**
	 * Thời gian hiện tại đang chạy
	 */
	int current_time = 0;

	/**
	 * Tạm dừng hoặc tiếp tục
	 */
	boolean isPause = false;
	boolean isStop = false;
	// Lưu lại các giá trị
	float pX_start = 0, pX_end = 0, midYButtonPause = 0;

	// -----------------------------------------------
	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		this.ini(mContext, mEngine, mCamera);
	}

	// -----------------------------------------------
	@Override
	public void onLoadResources() {
		this.prb_BTA = new BitmapTextureAtlas(512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.prb_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.prb_BTA, mContext, "progressbar.png",
				0, 0);
		this.mEngine.getTextureManager().loadTextures(this.prb_BTA);
	}

	// -----------------------------------------------
	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		// tính toán chiều cao, vị trí hiện thị thanh progressbar
		int h = (int) (prb_TR.getHeight() * Config.getRaceHeight());
		int y = (int) (midYButtonPause - prb_TR.getHeight() / 2) + 5;
		int w = (int) (pX_end - pX_start);
		// lưu lại chiều rộng
		width_rect = w - 6;

		this.mRectangle = new Rectangle(pX_start + 3, y + 3, width_rect, h - 6);
		this.mRectangle.setColor(1f, 0.01f, 0.02f);
		this.mScene.attachChild(mRectangle);

		this.prb_SP = new Sprite(pX_start, y, w, h, this.prb_TR);
		this.mScene.attachChild(prb_SP);
	}

	// -----------------------------------------------
	float w = -1;

	/**
	 * Bắt đầu chạy thời gian đếm ngược
	 */
	public void start() {
		if (total_time < 0)
			return;

		current_time = total_time;
		// Tính xem sao 1s thì cần phải thu nhỏ cái hình chữ nhật 1 khoảng bao
		// nhiêu
		w = width_rect / current_time;
		mRectangle.setVisible(true);
		mRectangle.setWidth(width_rect);
		isStop = false;
		isPause = false;

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isStop && current_time > 0) {
					if (!isPause) {
						try {
							Thread.sleep(1000);
							current_time--;
							updateProgressBar(w);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				// Hết thời gian
				if (current_time <= 0) {
					Play.mPlay.gameOver();
				}
			}
		}).start();

	}

	// -----------------------------------------------
	/**
	 * Thay đổi kích thước hình chữ nhật
	 * 
	 * @param w
	 */
	public void updateProgressBar(float w) {
		if (current_time > 0 && !this.isStop) {
			mRectangle.setWidth(mRectangle.getWidth() - w);
		} else {
			mRectangle.setVisible(false);
		}
	}

	// -----------------------------------------------
	public void setTotalTime(int total_time) {
		this.total_time = total_time;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	/**
	 * Thời gian được tính bẳng tổng thời gian trừ đi thời gian đã trôi qua
	 *
	 * @return
	 */
	public int getTimeEnd() {
		return Math.abs(this.current_time - this.total_time);
	}

	public void setXStartEnd(float pX_start, float pX_end) {
		this.pX_start = pX_start;
		this.pX_end = pX_end;
	}

	public void setMidYButtonPause(float midYButtonPause) {
		this.midYButtonPause = midYButtonPause;
	}

	// -----------------------------------------------
	@Override
	public void onDestroy() {
		this.isStop = true;
	}

}
