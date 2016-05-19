package com.tam.gamepikachu.components;
import java.util.ArrayList;

import com.tam.gamepikachu.log.MyLog;
import com.tam.pikachu.config.Config;
import com.tam.pikachu.util.Util;

import android.graphics.Point;

/**
 * - Dựa vào màn hình tính toán xem có bao nhiêu hàng, cột. - Tạo ra ma trận
 * ngẫu nhiên ban đầu - Tạo ma trận test khi cần
 * 
 * @author VAN GIOI
 *
 */
public class MT {
	public static int row = 0, column = 0;
	public static int[][] mt;

	public static int YSTART = 0, XSTART = 0;

	/**
	 * Tính tổng số hàng và số cột
	 *
	 * @param ystart
	 */
	public static void getTotalRowColumn(int ystart) {
		int height_content = Config.SCREENHIEGHT - ystart - Config.ITEM_HEIGHT / 2;
		int width_content = Config.SCREENWIDTH - Config.ITEM_WIDTH;
		row = height_content / Config.ITEM_HEIGHT;
		column = width_content / Config.ITEM_WIDTH;

		// Nếu số tổng số quân mà không chia hết cho 2 thì cần phải trừ đi 1
		// cột,
		// điều này đảm bảo luôn tồn tại cặp ăn được
		if ((row * column) % 2 != 0)
			column = column - 1;

		YSTART = ystart + (height_content - row * Config.ITEM_HEIGHT) / 2;
		XSTART = (Config.SCREENWIDTH - column * Config.ITEM_WIDTH) / 2 - Config.ITEM_WIDTH / 2;

		MyLog.LogInfo("row = " + row);
		MyLog.LogInfo("column = " + column);
		MyLog.LogInfo("YSTART = " + YSTART);
		MyLog.LogInfo("XSTART = " + XSTART);

		// Khởi tạo ma trận
		// MTTest();
		reRandomMT();
	}

	public static void reRandomMT() {
		mt = new int[row][column];
		while (true) {
			randomMT();
			ArrayList<Point> return_point = new ArrayList<Point>();
			ControllOnclick.search(return_point);
			if (return_point.size() == 2)
				break;
		}
	}

	public static void iniMaxItem(ArrayList<Integer> maxItem) {
		for (int i = 0; i < Config.numberItemThemes[Config.THEMES - 1]; i++) {
			maxItem.add(i);
		}
	}

	/**
	 * Tạo ra ma trận ngẫu nhiên
	 */
	public static void randomMT() {
		ArrayList<Integer> maxItem = new ArrayList<Integer>();
		iniMaxItem(maxItem);

		ArrayList<Point> list_point = new ArrayList<Point>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				list_point.add(new Point(i, j));
			}
		}

		int mid = row * column / 2;

		for (int i = 0; i < mid; i++) {
			int random_maxItem = Util.getRandomIndex(0, maxItem.size() - 1);
			int random_maxItem_tmp = maxItem.get(random_maxItem);
			maxItem.remove(random_maxItem);
			addIJ(random_maxItem_tmp, list_point);
			addIJ(random_maxItem_tmp, list_point);

			if (maxItem.size() == 0) {
				iniMaxItem(maxItem);
			}
		}

		showMT();
	}

	public static void addIJ(int random_maxItem_tmp, ArrayList<Point> list_point) {
		int p1 = Util.getRandomIndex(0, list_point.size() - 1);
		Point p = list_point.get(p1);
		mt[p.x][p.y] = random_maxItem_tmp;
		list_point.remove(p1);
	}

	/**
	 * Log ma trận để kiểm tra
	 */
	public static void showMT() {
		if (MyLog.islog) {
			MyLog.LogInfo("---------------------------");
			MyLog.LogInfo("------------MT-------------");
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					if (mt[i][j] > 9)
						System.out.print(mt[i][j] + " ");
					else
						System.out.print(mt[i][j] + "  ");
				}
				System.out.println();
			}
			MyLog.LogInfo("---------------------------");
		}
	}

	public static Point getXYByIJ(int i, int j) {
		int Px = XSTART + (j * Config.ITEM_WIDTH);
		int Py = YSTART + (i * Config.ITEM_HEIGHT);

		return new Point(Px, Py);
	}

	/**
	 * Tạo ma trận test
	 */
	public static void MTTest() {
		mt = new int[][] { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1 }, };
	}
}
