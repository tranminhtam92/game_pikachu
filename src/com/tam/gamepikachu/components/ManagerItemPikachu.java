package com.tam.gamepikachu.components;

 
import java.util.ArrayList;
import java.util.HashMap;
 
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.tam.gamepikachu.Play;
import com.tam.gamepikachu.log.MyLog;
import com.tam.pikachu.config.Config;
import com.tam.pikachu.util.Util;

import android.content.Context;
import android.graphics.Point;
/**
* Quản lý các đối tượng pikachu
* @author VAN GIOI
*
*/
public class ManagerItemPikachu {
    //Các thuộc tính
    public Camera mCamera = null;
    public Context mContext = null;
    public Engine mEngine = null;
    public Scene mScene = null;
    public Play mPlay = null;
    /**
    * Phương thức khỏi dựng
    * @param mContext
    * @param mEngine
    * @param mCamera
    */
    public ManagerItemPikachu(Context mContext, Engine mEngine, Camera mCamera) {
        this.mContext = mContext;
        this.mEngine = mEngine;
        this.mCamera = mCamera;
        this.mPlay = (Play) mContext;
        map_Resources.clear();
        list_itemPikachu.clear();
    }
 
    public void setScene(Scene mScene) {
        this.mScene = mScene;
    }
    //list_itemPikachu quản lý các đối tượng
    ArrayList<ItemPikachu> list_itemPikachu = new ArrayList<ItemPikachu>();
    //Quán lý tài nguyên
    public static HashMap<Integer, TextureRegion> map_Resources = new HashMap<Integer, TextureRegion>();
 
    // ----------------------------------------
    /**
    * Thêm các đối tượng pikachu vào list_itemPikachu để quản lý
    */
    public void addItem() {
        int ystart = MT.YSTART;
        for (int i = 0; i < MT.row; i++) {
            int xstart = MT.XSTART;
            for (int j = 0; j < MT.column; j++) {
                if (MT.mt[i][j] != -1) {
                    ItemPikachu mItemPikachu = new ItemPikachu();
                    mItemPikachu.onCreate(mContext, mEngine, mCamera);
                    mItemPikachu.newItemPikachu(mScene, i, j, MT.mt[i][j],
                            xstart, ystart);
                    list_itemPikachu.add(mItemPikachu);
                }
                xstart = xstart + Config.ITEM_WIDTH;
            }
            ystart = ystart + Config.ITEM_HEIGHT;
        }
    }
 
    // ----------------------------------------
    /**
    * Hiệu ứng khi bắt đầu hiện thị các đối tượng pikachu
    * @param effects
    * @param effects_type
    */
    public void showItemEffect(int effects, int type) {
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(i);
 
            int effects_in = Util.getRandomIndex(0, 7);
 
            if (effects != -1)
                effects_in = effects;
 
            switch (effects_in) {
            // In fLeft
            case 0:
                mItemPikachu.move(-100, mItemPikachu.Y, type);
                break;
            // In right
            case 1:
                mItemPikachu.move(Config.SCREENWIDTH + 100, mItemPikachu.Y,
                        type);
                break;
            // In top
            case 2:
                mItemPikachu.move(mItemPikachu.X, -100, type);
                break;
            // In bottom
            case 3:
                mItemPikachu.move(mItemPikachu.X, Config.SCREENHIEGHT + 100,
                        type);
                break;
            // In left-top
            case 4:
                mItemPikachu.move(-100, -100, type);
                break;
            // In right-top
            case 5:
                mItemPikachu.move(Config.SCREENWIDTH + 100, -100, type);
                break;
            // In left-bottom
            case 6:
                mItemPikachu.move(-100, Config.SCREENHIEGHT + 100, type);
                break;
            // In right-bottom
            case 7:
                mItemPikachu.move(Config.SCREENWIDTH + 100,
                        Config.SCREENHIEGHT + 100, type);
                break;
            default:
                break;
            }
 
        }
    }
 
    // ----------------------------------------
    /**
    * Xóa 1 item khỏi list theo vị trí i, j
    *
    * @param i_item
    * @param j_item
    */
    public void removeItem(int i_item, int j_item) {
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            try {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                if (mItemPikachu.getI() == i_item
                        && mItemPikachu.getJ() == j_item) {
                    list_itemPikachu.remove(i);
                    MyLog.LogInfo("removeItem list_itemPikachu.size() = "
                            + list_itemPikachu.size());
                    return;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
 
    // ----------------------------------------
    /**
    * Hoán đổi các vị trí khi không còn nước ăn
    */
    public void swapItem() {
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            int index = Util.getRandomIndex(0, list_itemPikachu.size() - 1);
            if (index != i) {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                ItemPikachu mItemPikachu_index = list_itemPikachu.get(index);
 
                swap(mItemPikachu, mItemPikachu_index);
            }
        }
    }
    /**
    * Hoán đổi vị trí của 2 đối tượng pikachu
    * @param mItemPikachu1
    * @param mItemPikachu2
    */
    public void swap(ItemPikachu mItemPikachu1, ItemPikachu mItemPikachu2) {
        final int i = mItemPikachu1.I;
        final int j = mItemPikachu1.J;
        final int x = mItemPikachu1.X;
        final int y = mItemPikachu1.Y;
 
        mItemPikachu1.setIJ(mItemPikachu2.I, mItemPikachu2.J);
        mItemPikachu1.setPosition(mItemPikachu2.X, mItemPikachu2.Y);
        MT.mt[mItemPikachu2.I][mItemPikachu2.J] = mItemPikachu1.GT_ITEM;
 
        mItemPikachu2.setIJ(i, j);
        mItemPikachu2.setPosition(x, y);
        MT.mt[i][j] = mItemPikachu2.GT_ITEM;
    }
 
    // ----------------------------------------
    /**
    *
    */
    public void reset() {
        while (list_itemPikachu.size() != 0) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(0);
            mItemPikachu.setVisiable(false);
            list_itemPikachu.remove(0);
            mItemPikachu.onDestroy();
        }
        list_itemPikachu.clear();
    }
 
    // ----------------------------------------
    /**
    * Di chuyển đối tượng pikachu khi ăn để tạo ra hiệu ứng
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveItem(int i1, int j1, int i2, int j2) {
        switch (Level.levelCurrent % 9) {
        case 1:
            ControllOnclick.activeSearhHint();
            break;
        case 2:
            moveLevel2(i1, j1, i2, j2);
            break;
        case 3:
            moveLevel3(i1, j1, i2, j2);
            break;
        case 4:
            moveLevel4(i1, j1, i2, j2);
            break;
        case 5:
            moveLevel5(i1, j1, i2, j2);
            break;
        case 6:
            moveLevel67(i1, j1, i2, j2);
            break;
        case 7:
            moveLevel67(i2, j2, i1, j1);
            break;
        case 8:
            moveLevel89(i1, j1, i2, j2);
            break;         
        case 9:
            moveLevel89(i2, j2, i1, j1);
            break;
        default:
            ControllOnclick.activeSearhHint();
            break;
        }
    }
 
    // ----------------------------------------
    /**
    * Di chuyển từ trên xuống dưới
    *  Move top - down
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel2(int i1, int j1, int i2, int j2) {
        int down = 1;
        if (j1 == j2)
            down = 2;
 
        HashMap<Integer, ItemPikachu> map1 = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map2 = new HashMap<Integer, ItemPikachu>();
 
        HashMap<Integer, Integer> map1_down = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2_down = new HashMap<Integer, Integer>();
 
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(i);
 
            if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                continue;
            if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                continue;
 
            int tmp_d = down;
            if (mItemPikachu.J == j1 && mItemPikachu.I < i1) {
                if (down == 2) {
                    if (mItemPikachu.I > i2)
                        tmp_d = 1;
                }
                map1_down.put(mItemPikachu.I, tmp_d);
                map1.put(mItemPikachu.I, mItemPikachu);
            } else if (mItemPikachu.J == j2 && mItemPikachu.I < i2) {
                if (down == 2) {
                    if (mItemPikachu.I > i1)
                        tmp_d = 1;
                }
                map2_down.put(mItemPikachu.I, tmp_d);
                map2.put(mItemPikachu.I, mItemPikachu);
            }
 
        }
 
        total_activesearchhint = map1.size();
        total_activesearchhint = total_activesearchhint + map2.size();
        count_total_activesearchhint = 0;
 
        for (int i = MT.row - 1; i > -1; i--) {
            ItemPikachu mItemPikachu = map1.get(i);
            int tmp_d = -1;
            if (mItemPikachu != null) {
                tmp_d = map1_down.get(i);
                move2(mItemPikachu, tmp_d);
            }
 
            mItemPikachu = map2.get(i);
            if (mItemPikachu != null) {
                tmp_d = map2_down.get(i);
                move2(mItemPikachu, tmp_d);
            }
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move2(ItemPikachu mItemPikachu, int down) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
        MT.mt[I + down][J] = mItemPikachu.GT_ITEM;
        mItemPikachu.setIJ(I + down, J);
        Point p = MT.getXYByIJ(I + down, J);
 
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    /**Di chuyển từ dưới lên trên
    * Move down - top
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel3(int i1, int j1, int i2, int j2) {
        int down = 1;
        if (j1 == j2)
            down = 2;
 
        HashMap<Integer, ItemPikachu> map1 = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map2 = new HashMap<Integer, ItemPikachu>();
 
        HashMap<Integer, Integer> map1_down = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2_down = new HashMap<Integer, Integer>();
 
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(i);
 
            if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                continue;
            if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                continue;
 
            int tmp_d = down;
            if (mItemPikachu.J == j1 && mItemPikachu.I > i1) {
                if (down == 2) {
                    if (mItemPikachu.I < i2)
                        tmp_d = 1;
                }
                map1_down.put(mItemPikachu.I, tmp_d);
                map1.put(mItemPikachu.I, mItemPikachu);
            } else if (mItemPikachu.J == j2 && mItemPikachu.I > i2) {
                if (down == 2) {
                    if (mItemPikachu.I < i1)
                        tmp_d = 1;
                }
                map2_down.put(mItemPikachu.I, tmp_d);
                map2.put(mItemPikachu.I, mItemPikachu);
            }
 
        }
 
        total_activesearchhint = map1.size();
        total_activesearchhint = total_activesearchhint + map2.size();
        count_total_activesearchhint = 0;
 
        for (int i = 0; i < MT.row; i++) {
            ItemPikachu mItemPikachu = map1.get(i);
            int tmp_d = -1;
            if (mItemPikachu != null) {
                tmp_d = map1_down.get(i);
                move3(mItemPikachu, tmp_d);
            }
 
            mItemPikachu = map2.get(i);
            if (mItemPikachu != null) {
                tmp_d = map2_down.get(i);
                move3(mItemPikachu, tmp_d);
            }
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move3(ItemPikachu mItemPikachu, int down) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
        MT.mt[I - down][J] = mItemPikachu.GT_ITEM;
        mItemPikachu.setIJ(I - down, J);
        Point p = MT.getXYByIJ(I - down, J);
 
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    /**
    * Di chuyển từ trái qua phải
    *  Move left - right
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel4(int i1, int j1, int i2, int j2) {
        int down = 1;
        if (i1 == i2)
            down = 2;
 
        HashMap<Integer, ItemPikachu> map1 = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map2 = new HashMap<Integer, ItemPikachu>();
 
        HashMap<Integer, Integer> map1_down = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2_down = new HashMap<Integer, Integer>();
 
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(i);
 
            if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                continue;
            if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                continue;
 
            int tmp_d = down;
            if (mItemPikachu.I == i1 && mItemPikachu.J < j1) {
                if (down == 2) {
                    if (mItemPikachu.J > j2)
                        tmp_d = 1;
                }
                map1_down.put(mItemPikachu.J, tmp_d);
                map1.put(mItemPikachu.J, mItemPikachu);
            } else if (mItemPikachu.I == i2 && mItemPikachu.J < j2) {
                if (down == 2) {
                    if (mItemPikachu.J > j1)
                        tmp_d = 1;
                }
                map2_down.put(mItemPikachu.J, tmp_d);
                map2.put(mItemPikachu.J, mItemPikachu);
            }
 
        }
 
        total_activesearchhint = map1.size();
        total_activesearchhint = total_activesearchhint + map2.size();
        count_total_activesearchhint = 0;
 
        for (int i = MT.column - 1; i > -1; i--) {
            ItemPikachu mItemPikachu = map1.get(i);
            int tmp_d = -1;
            if (mItemPikachu != null) {
                tmp_d = map1_down.get(i);
                move4(mItemPikachu, tmp_d);
            }
 
            mItemPikachu = map2.get(i);
            if (mItemPikachu != null) {
                tmp_d = map2_down.get(i);
                move4(mItemPikachu, tmp_d);
            }
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move4(ItemPikachu mItemPikachu, int down) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
        MT.mt[I][J + down] = mItemPikachu.GT_ITEM;
        mItemPikachu.setIJ(I, J + down);
        Point p = MT.getXYByIJ(I, J + down);
 
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    /**
    * Di chuyển từ phải qua trái
    * Move right - left
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel5(int i1, int j1, int i2, int j2) {
        int down = 1;
        if (i1 == i2)
            down = 2;
 
        HashMap<Integer, ItemPikachu> map1 = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map2 = new HashMap<Integer, ItemPikachu>();
 
        HashMap<Integer, Integer> map1_down = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2_down = new HashMap<Integer, Integer>();
 
        for (int i = 0; i < list_itemPikachu.size(); i++) {
            ItemPikachu mItemPikachu = list_itemPikachu.get(i);
 
            if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                continue;
            if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                continue;
 
            int tmp_d = down;
            if (mItemPikachu.I == i1 && mItemPikachu.J > j1) {
                if (down == 2) {
                    if (mItemPikachu.J < j2)
                        tmp_d = 1;
                }
                map1_down.put(mItemPikachu.J, tmp_d);
                map1.put(mItemPikachu.J, mItemPikachu);
            } else if (mItemPikachu.I == i2 && mItemPikachu.J > j2) {
                if (down == 2) {
                    if (mItemPikachu.J < j1)
                        tmp_d = 1;
                }
                map2_down.put(mItemPikachu.J, tmp_d);
                map2.put(mItemPikachu.J, mItemPikachu);
            }
 
        }
 
        total_activesearchhint = map1.size();
        total_activesearchhint = total_activesearchhint + map2.size();
        count_total_activesearchhint = 0;
 
        for (int i = 0; i < MT.column; i++) {
            ItemPikachu mItemPikachu = map1.get(i);
            int tmp_d = -1;
            if (mItemPikachu != null) {
                tmp_d = map1_down.get(i);
                move5(mItemPikachu, tmp_d);
            }
 
            mItemPikachu = map2.get(i);
            if (mItemPikachu != null) {
                tmp_d = map2_down.get(i);
                move5(mItemPikachu, tmp_d);
            }
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move5(ItemPikachu mItemPikachu, int down) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
        MT.mt[I][J - down] = mItemPikachu.GT_ITEM;
        mItemPikachu.setIJ(I, J - down);
        Point p = MT.getXYByIJ(I, J - down);
 
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    /**
    * Move in - column (Di chuyển đối tượng vào giữa theo các cột)
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel67(int i1, int j1, int i2, int j2) {
        HashMap<Integer, ItemPikachu> map_left = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map_right = new HashMap<Integer, ItemPikachu>();
 
        if (j1 < j2) {
            for (int i = 0; i < list_itemPikachu.size(); i++) {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                    continue;
                if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                    continue;
 
                // Tìm bên trái j1
                if (mItemPikachu.I == i1 && mItemPikachu.J < j1) {
                    map_left.put(mItemPikachu.J, mItemPikachu);
                }
 
                // Tìm bên phải j2
                if (mItemPikachu.I == i2 && mItemPikachu.J > j2) {
                    map_right.put(mItemPikachu.J, mItemPikachu);
                }
            }
        } else {
            for (int i = 0; i < list_itemPikachu.size(); i++) {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                    continue;
                if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                    continue;
 
                // Tìm bên trái j2
                if (mItemPikachu.I == i2 && mItemPikachu.J < j2) {
                    map_left.put(mItemPikachu.J, mItemPikachu);
                }
 
                // Tìm bên phải j1
                if (mItemPikachu.I == i1 && mItemPikachu.J > j1) {
                    map_right.put(mItemPikachu.J, mItemPikachu);
                }
            }
        }
 
        total_activesearchhint = map_left.size();
        total_activesearchhint = total_activesearchhint + map_right.size();
        count_total_activesearchhint = 0;
 
        // Di chuyển map_left
        for (int j = j1 - 1; j > -1; j--) {
            ItemPikachu mItemPikachu = map_left.get(j);
            if (mItemPikachu != null)
                move67(mItemPikachu, 0);
        }
        // Di chuyển map_right
        for (int j = j1 + 1; j < MT.column; j++) {
            ItemPikachu mItemPikachu = map_right.get(j);
            if (mItemPikachu != null)
                move67(mItemPikachu, 1);
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move67(ItemPikachu mItemPikachu, int type) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
 
        Point p = null;
 
        // Left - right
        if (type == 0) {
            MT.mt[I][J + 1] = mItemPikachu.GT_ITEM;
            mItemPikachu.setIJ(I, J + 1);
            p = MT.getXYByIJ(I, J + 1);
        }
        // Right - left
        else {
            MT.mt[I][J - 1] = mItemPikachu.GT_ITEM;
            mItemPikachu.setIJ(I, J - 1);
            p = MT.getXYByIJ(I, J - 1);
        }
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    /**
    * Move in - row (Di chuyển đối tượng vào giữa theo các hàng)
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void moveLevel89(int i1, int j1, int i2, int j2) {
        HashMap<Integer, ItemPikachu> map_down = new HashMap<Integer, ItemPikachu>();
        HashMap<Integer, ItemPikachu> map_top = new HashMap<Integer, ItemPikachu>();
 
        if (i1 < i2) {
            for (int i = 0; i < list_itemPikachu.size(); i++) {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                    continue;
                if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                    continue;
 
                // Tìm bên trên i1
                if (mItemPikachu.J == j1 && mItemPikachu.I < i1) {
                    map_down.put(mItemPikachu.I, mItemPikachu);
                }
 
                // Tìm bên dưới i2
                if (mItemPikachu.J == j2 && mItemPikachu.I > i2) {
                    map_top.put(mItemPikachu.I, mItemPikachu);
                }
            }
        } else {
            for (int i = 0; i < list_itemPikachu.size(); i++) {
                ItemPikachu mItemPikachu = list_itemPikachu.get(i);
                if (mItemPikachu.I == i1 && mItemPikachu.J == j1)
                    continue;
                if (mItemPikachu.I == i2 && mItemPikachu.J == j2)
                    continue;
 
                // Tìm bên trên i2
                if (mItemPikachu.J == j2 && mItemPikachu.I < i2) {
                    map_down.put(mItemPikachu.I, mItemPikachu);
                }
 
                // Tìm bên dưới i1
                if (mItemPikachu.J == j1 && mItemPikachu.I > i1) {
                    map_top.put(mItemPikachu.I, mItemPikachu);
                }
            }
        }
 
        total_activesearchhint = map_down.size();
        total_activesearchhint = total_activesearchhint + map_top.size();
        count_total_activesearchhint = 0;
 
        // Di chuyển map_down
        for (int i = i1 - 1; i > -1; i--) {
            ItemPikachu mItemPikachu = map_down.get(i);
            if (mItemPikachu != null)
                move89(mItemPikachu, 0);
        }
        // Di chuyển map_top
        for (int i = i1 + 1; i < MT.row; i++) {
            ItemPikachu mItemPikachu = map_top.get(i);
            if (mItemPikachu != null)
                move89(mItemPikachu, 1);
        }
 
        // Nếu không có item nào cần di chuyển thì thực hiện tìm kiếm xem có
        // item nào ăn được
        // tiếp theo hay không
        if (total_activesearchhint == 0) {
            ControllOnclick.activeSearhHint();
        }
    }
 
    public void move89(ItemPikachu mItemPikachu, int type) {
        final int I = mItemPikachu.I;
        final int J = mItemPikachu.J;
        MT.mt[I][J] = -1;
 
        Point p = null;
 
        // top - down
        if (type == 0) {
            MT.mt[I + 1][J] = mItemPikachu.GT_ITEM;
            mItemPikachu.setIJ(I + 1, J);
            p = MT.getXYByIJ(I + 1, J);         
        }
        // down - top
        else {
            MT.mt[I - 1][J] = mItemPikachu.GT_ITEM;
            mItemPikachu.setIJ(I - 1, J);
            p = MT.getXYByIJ(I - 1, J);
        }
        mItemPikachu.moveXY(p.x, p.y);
    }
 
    // ----------------------------------------
    int total_activesearchhint = -1;
    int count_total_activesearchhint = -1;
    /**
    * Đồng bộ quá trình searh kiểm tra
    */
    public synchronized void ActiveSearchHint() {
        count_total_activesearchhint++;
        if (count_total_activesearchhint >= total_activesearchhint) {
            count_total_activesearchhint = 0;
            total_activesearchhint = 0;
            ControllOnclick.activeSearhHint();
        }
    }
}
