package com.tam.gamepikachu.components;

 
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tam.gamepikachu.Play;
import com.tam.pikachu.config.Config;

import android.content.Context;
import android.graphics.Point;
/**
* Khi bạn chọn gợi ý thì 2 đối tượng sẽ hiện lên tạo hiệu ứng
* giúp người chơi có thể nhận biết sự gợi ý
* @author VAN GIOI
*
*/
public class Hint extends MySprite {
    BitmapTextureAtlas mBTA;
    TiledTextureRegion mTTR;
    AnimatedSprite hint1_AS, hint2_AS;
 
    @Override
    public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
        ini(mContext, mEngine, mCamera);
    }
 
    @Override
    public void onLoadResources() {
        this.mBTA = new BitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR);
        this.mTTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBTA, mContext, "animation_hint.png", 0, 0, 3, 1);
        this.mEngine.getTextureManager().loadTexture(this.mBTA);
    }
 
    @Override
    public void onLoadScene(Scene mScene) {
        this.mScene = mScene;
        int w = Config.ITEM_WIDTH;
        int h = Config.ITEM_HEIGHT;
     
        hint1_AS = new AnimatedSprite(-100, -100, w, h, this.mTTR.deepCopy());
        hint1_AS.animate(150);
     
        hint2_AS = new AnimatedSprite(-100, -100, w, h, this.mTTR.deepCopy());
        hint2_AS.animate(150);
     
        setVisiable(false);
     
        hint1_AS.setZIndex(200);
        hint2_AS.setZIndex(200);
     
            this.mScene.attachChild(hint1_AS);
            this.mScene.attachChild(hint2_AS);
    }
    /**
    * Khi nào cần hiện thị gợi ý thì chỉ cần gọi
    * dựa vào i,j ta xác định được tọa độ hiện thị
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void setHint(int i1, int j1, int i2, int j2) {
        Point p = MT.getXYByIJ(i1, j1);
        hint1_AS.setPosition(p.x, p.y);
        p = MT.getXYByIJ(i2, j2);
        hint2_AS.setPosition(p.x, p.y);
    }
    /**
    * ẩn hay hiện gợi ý
    * @param visiable
    */
    public void setVisiable(boolean visiable) {
        if(Play.mPlay.GameOver)
            return;
        hint1_AS.setVisible(visiable);
        hint2_AS.setVisible(visiable);
    }
 
    public boolean visiable(){
        return hint1_AS.isVisible();
    }
 
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
     
    }
}
