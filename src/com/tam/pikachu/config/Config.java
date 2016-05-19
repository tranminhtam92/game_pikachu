package com.tam.pikachu.config;

import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;

import android.app.Activity;

public class Config {
	 /**
	    * Yêu cầu màn hình nằm ngang
	    */
	    public static final ScreenOrientation ScreenOrientation_Default = ScreenOrientation.LANDSCAPE;
	 
	    /**
	    * Chi�?u rộng và chi�?u cao của màn hình hiện thị
	    */
	    public static int SCREENWIDTH = 480, SCREENHIEGHT = 800;
	 
	    /**
	    * Tị lệ, khởi tạo ban đầu tị lệ = 1
	    */
	    public static float RACE_HEIGHT = 1f;
	    public static float RACE_WIDTH = 1f;
	 
	    /**
	    * Chi�?u rộng và, cao của 1 item pikachu
	    */
	    public static int ITEM_WIDTH = 60;
	    public static int ITEM_HEIGHT = 70;
	 
	    /**
	    * Themes mặc định ban đầu là 1
	    */
	    public static int THEMES = 1;
	    public static String pathTHEME = "item/theme" + THEMES + "/";
	 
	    /**
	    * �?ịnh nghĩa số Item có trong từng themes
	    */
	    public static int[] numberItemThemes = new int[]{
	        53,
	        53,
	        35,
	        26,
	        32
	    };

	
	    //---------------------------------------------------------------
	    /**
	    * Xác định chi�?u cao, rộng của màn hình. Sao đó tính các tỉ lệ phục vụ cho
	    * việc scale các đối tượng theo các màn hình
	    * @param mActivity
	    */
	    public static void getDisplayScreen(Activity mActivity) {
	        SCREENWIDTH = mActivity.getWindowManager().getDefaultDisplay().getWidth();
	        SCREENHIEGHT = mActivity.getWindowManager().getDefaultDisplay().getHeight();
	 
	        RACE_WIDTH = (float) SCREENWIDTH / (float) 800;
	        RACE_HEIGHT = (float) SCREENHIEGHT / (float) 480; 
	     
	        if(SCREENHIEGHT >= 320 && SCREENHIEGHT < 480){
	            ITEM_WIDTH = 40;
	            ITEM_HEIGHT = 47;
	        }
	        else if(SCREENHIEGHT < 320){
	            ITEM_WIDTH = 29;
	            ITEM_HEIGHT = 34;
	        }
	    }
	    //---------------------------------------------------------------
	    /**
	    * Tỉ lệ với chi�?u rộng
	    * @return
	    */
	    public static float getRaceWidth(){
	        return RACE_WIDTH;
	    }
	    /**
	    * Tỉ lệ với chi�?u cao
	    * @return
	    */
	    public static float getRaceHeight(){
	        return RACE_HEIGHT;
	    }
	 
	    /**
	    * Lấy vị trí x ở giữa màn hình
	    *
	    * @return
	    */
	    public static int getCenterX() {
	        return SCREENWIDTH / 2;
	    }
	 
	    /**
	    * Lấy vị trí y ở giữa màn hình
	    *
	    * @return
	    */
	    public static int getCenterY() {
	        return SCREENHIEGHT / 2;
	    }
}
