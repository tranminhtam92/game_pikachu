package com.tam.gamepikachu.components;

import java.util.ArrayList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;

import com.tam.gamepikachu.log.MyLog;
import com.tam.pikachu.config.Config;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;


public class Drawline extends MySprite {
	// Chiều cao của đường thẳng
	int heightLine = 20;

	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		ini(mContext, mEngine, mCamera);
	}

	@Override
	public void onLoadResources() {
	}

	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		// Dựa theo kích thước màn hình để tính toán chiều cao của đường thẳng
		if (Config.SCREENHIEGHT >= 320 && Config.SCREENHIEGHT < 480) {
			heightLine = 10;
		} else if (Config.SCREENHIEGHT < 320) {
			heightLine = 6;
		}
	}

	@Override
	public void onDestroy() {
	}

	/**
	 * thêm 1 đường thẳng, sẽ vẽ đường thẳng từ vị trí (x1,y1)tới (x2,y2)
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param line_point:
	 *            xác định xem cần vẽ loại nào.
	 *
	 *            nếu line_point.size == 2: vẽ đường thẳng nếu line_point.size
	 *            == 3: vẽ kiểu chữ L nếu line_point.size == 4: vẽ kiểu chữ U, Z
	 */
	public void addLine(int x1, int y1, int x2, int y2, ArrayList<Point> line_point) {

		Point p1 = MT.getXYByIJ(x1, y1);
		Point p2 = MT.getXYByIJ(x2, y2);

		// Đường thẳng
		if (line_point.size() == 2) {
			addLine(p1.x, p1.y, p2.x, p2.y);
		}
		// Chữ L
		else if (line_point.size() == 3) {
			Point p_mid = null;
			// Tìm mid
			for (int i = 0; i < line_point.size(); i++) {
				p_mid = line_point.get(i);
				if ((p_mid.x == x1 && p_mid.y == y1) || (p_mid.x == x2 && p_mid.y == y2))
					continue;
				else
					break;
			}
			p_mid = MT.getXYByIJ(p_mid.x, p_mid.y);
			addLine(p1.x, p1.y, p_mid.x, p_mid.y);
			addLine(p_mid.x, p_mid.y, p2.x, p2.y);
		}
		// Chữ U-Z
		else if (line_point.size() == 4) {
			Point p_mid1 = null, p_mid2 = null;
			// Tìm mid
			for (int i = 0; i < line_point.size(); i++) {
				Point p_mid = line_point.get(i);
				if ((p_mid.x == x1 && p_mid.y == y1) || (p_mid.x == x2 && p_mid.y == y2))
					continue;
				else {
					if (p_mid1 == null && (p_mid.x == x1 || p_mid.y == y1))
						p_mid1 = line_point.get(i);
					else
						p_mid2 = line_point.get(i);
				}

				MyLog.LogInfo("----------------------------");
				MyLog.LogInfo(" i = " + i);
				MyLog.LogInfo("p_mid.x = " + p_mid.x);
				MyLog.LogInfo("p_mid.y = " + p_mid.y);
			}

			p_mid1 = MT.getXYByIJ(p_mid1.x, p_mid1.y);
			p_mid2 = MT.getXYByIJ(p_mid2.x, p_mid2.y);

			addLine(p1.x, p1.y, p_mid1.x, p_mid1.y);
			addLine(p_mid1.x, p_mid1.y, p_mid2.x, p_mid2.y);
			addLine(p_mid2.x, p_mid2.y, p2.x, p2.y);
		}
	}

	/**
	 * Vẽ 1 đoạn thẳng
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void addLine(int x1, int y1, int x2, int y2) {
		int tmp = 4;
		if (x1 == x2) {
			if (y1 < y2) {
				y1 = y1 - tmp;
				y2 = y2 + tmp;
			} else {
				y1 = y1 + tmp;
				y2 = y2 - tmp;
			}

		} else if (y1 == y2) {
			if (x1 < x2) {
				x1 = x1 - tmp;
				x2 = x2 + tmp;
			} else {
				x1 = x1 + tmp;
				x2 = x2 - tmp;
			}
		}

		x1 = x1 + Config.ITEM_WIDTH / 2;
		y1 = y1 + Config.ITEM_HEIGHT / 2;
		x2 = x2 + Config.ITEM_WIDTH / 2;
		y2 = y2 + Config.ITEM_HEIGHT / 2;

		Line line = new Line(x1, y1, x2, y2, heightLine);
		line.setColor(1f, 1f, 1f);
		mScene.attachChild(line);
		removeLine(line);
	}

	/**
	 * Xóa 1 đường thẳng
	 * 
	 * @param line
	 */
	public void removeLine(final Line line) {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mEngine.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mScene.detachChild(line);
					}
				});
			}
		}, 300);
	}

}
