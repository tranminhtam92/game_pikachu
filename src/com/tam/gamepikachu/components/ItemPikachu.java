package com.tam.gamepikachu.components;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import com.tam.gamepikachu.Play;
import com.tam.pikachu.config.Config;

import android.content.Context;
import android.os.Handler;

/**
 * Định nghĩa 1 đối tượng pikachu
 * 
 * @author VAN GIOI
 *
 */
public class ItemPikachu extends MySprite {
	/**
	 * I : Vị trí hàng J : Vị trí cột GT_ITEM : Xác định xem pikachu này là bức
	 * ảnh nào, dùng ánh xạ hiện thị ảnh. X : Vị trí X trên màn hình Y : Vị trí
	 * Y trên màn hình
	 */
	int I = -1, J = -1, GT_ITEM = -1, X = -1, Y = -1;
	BitmapTextureAtlas item_BTA;
	Sprite item_SP;
	TextureRegion item_TR;

	// ------------------------------------------
	@Override
	public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
		ini(mContext, mEngine, mCamera);
	}

	// ------------------------------------------
	@Override
	public void onLoadResources() {
		if (!ManagerItemPikachu.map_Resources.containsKey(GT_ITEM)) {
			// Load ảnh theo từng theme và item
			this.item_BTA = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.item_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.item_BTA, mContext,
					Config.pathTHEME + "item" + GT_ITEM + ".png", 0, 0);
			this.mEngine.getTextureManager().loadTextures(this.item_BTA);
			// Thêm vào phần quản lý
			ManagerItemPikachu.map_Resources.put(GT_ITEM, this.item_TR);
		} else {
			this.item_TR = ManagerItemPikachu.map_Resources.get(GT_ITEM);
		}

	}

	// ------------------------------------------
	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		// Ban đầu tạo ra các đối tượng pikachu nào ở ngoài màn hình
		this.item_SP = new Sprite(-100, -100, Config.ITEM_WIDTH, Config.ITEM_HEIGHT, this.item_TR) {
			// Bắt sự kiện khi bạn chạm vào đối tượng pikachu
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// Cho phép chọn đối tượng
				if (ControllOnclick.isOnClickItem) {
					if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						//Menu.mSound.playClick();
						item_SP.setScale(0.8f);
						item_SP.setRotation(30);
					} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
						ControllOnclick.click(ItemPikachu.this);
					}
				}
				return true;
			}
		};
		// Mặc định là ẩn
		setVisiable(false);
		this.mScene.registerTouchArea(item_SP);
		this.mScene.attachChild(item_SP);
	}

	// ------------------------------------------
	public void newItemPikachu(Scene mScene, int i, int j, int gt_item, int x, int y) {
		this.I = i;
		this.J = j;
		this.GT_ITEM = gt_item;
		this.X = x;
		this.Y = y;

		onLoadResources();
		onLoadScene(mScene);
	}

	/**
	 * Tạo ra hiệu ứng di chuyển từ vị trí hiện tại của đối tượng piakchu tới vị
	 * trí x, y
	 *
	 * effect_type = 1 => IN : Di chuyển từ ngoài vào effect_type != 1 => Out :
	 * Di chuyển từ trong ra
	 * 
	 * @param x
	 * @param y
	 * @param effect_type
	 */
	public void move(int x, int y, int effect_type) {
		MoveModifier move = null;
		if (effect_type == 1) {
			// Thời gian di chuyển là 1.5 giây
			move = new MoveModifier(1.5f, x, this.X, y, this.Y);
		} else {
			move = new MoveModifier(1.5f, this.X, x, this.Y, y);
		}
		setVisiable(true);
		SequenceEntityModifier modifier = new SequenceEntityModifier(move);
		item_SP.registerEntityModifier(modifier);
	}

	/**
	 * Di chuyển 1 đối tượng pikachu tới vị trí x,y
	 * 
	 * @param x
	 * @param y
	 */
	public void moveXY(int x, int y) {
		MoveModifier move = new MoveModifier(0.5f, this.X, x, this.Y, y);
		SequenceEntityModifier modifier = new SequenceEntityModifier(
				new SequenceEntityModifier.IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						Play.mPlay.mManagerItemPikachu.ActiveSearchHint();
					}
				}, move);
		item_SP.registerEntityModifier(modifier);
		this.X = x;
		this.Y = y;
	}

	// ------------------------------------------
	public void setScale(float pScale) {
		item_SP.setScale(pScale);
	}

	public void setRotation(int re) {
		item_SP.setRotation(re);
	}

	public void setPosition(int x, int y) {
		this.X = x;
		this.Y = y;
		item_SP.setPosition(x, y);
	}

	public void setIJ(int i, int j) {
		this.I = i;
		this.J = j;
	}

	public void setVisiable(boolean visiable) {
		item_SP.setVisible(visiable);
	}

	// ------------------------------------------
	public int getI() {
		return I;
	}

	public void setI(int i) {
		I = i;
	}

	public int getJ() {
		return J;
	}

	public void setJ(int j) {
		J = j;
	}

	public int getGT_ITEM() {
		return GT_ITEM;
	}

	public void setGT_ITEM(int gT_ITEM) {
		GT_ITEM = gT_ITEM;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	// ------------------------------------------
	@Override
	public void onDestroy() {
		mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScene.detachChild(item_SP);
				mScene.unregisterTouchArea(item_SP);
			}
		});
	}

	// ------------------------------------------
	public void removeItem() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mEngine.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mScene.detachChild(item_SP);
						mScene.unregisterTouchArea(item_SP);
					}
				});
			}
		}, 300);
	}
}
