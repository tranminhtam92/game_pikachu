package com.tam.gamepikachu.components;

/**
* Tính toán số sao mà người chơi đạt được trong level đó, tính thời gian chơi cho 1 level
* @author VAN GIOI
*
*/
public class Level {
    public static int totalLevel = 15;
    public static int levelCurrent = 1;
 
    public static int getTimeLevel(){
        //Mỗi 1 cặp ăn được quy định cho phép tìm tối đa trong 3s
        //vì sao phải trừ (Level.levelCurrent - 1) * 4 sau mỗi level time sẽ giảm đi để tạo
        //nên áp lực cho người chơi, giúp người chơi phải nhanh tay hơn
        return (MT.row * MT.column * 3) - (Level.levelCurrent - 1) * 4;
    }
 
    /**
    * Tính xem level và time đạt bao nhiêu sao
    * @param level
    * @param time
    * @return
    */
    public static int getStarByLevel(int level, int time) {
        int star = 0;
        int timeLevel = getTimeLevel() / 5;
        if (time <= timeLevel * 2) {
            return 3;// 3 sao
        } else if (time <= timeLevel * 3) {
            return 2;// 2 sao
        } else if (time <= timeLevel * 4) {
            return 1;// 2 sao
        }
        return star;
    }
}