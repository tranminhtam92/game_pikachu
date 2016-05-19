package com.tam.gamepikachu.database;

import java.util.ArrayList;

import com.tam.gamepikachu.log.MyLog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	/**
	 * Tên csdl
	 */
	private static final String NAME_DB = "pikachu_hd.db";
	/**
	 * Phiên bản
	 */
	private static final int VERSION = 1;
	private SQLiteDatabase mSQLiteDatabase = null;

	/**
	 * Tên bảng và các trư�?ng
	 */
	public final String TABLE_DOLLAR = "TABLE_DOLLAR";
	public final String ID = "ID";
	public final String NAME = "NAME"; // Tên ngư�?i chơi
	public final String DOLLAR = "DOLLAR";// Số ti�?n mà ngư�?i đó dành được
	public final String THEME = "THEME";// Loại theme mà ngư�?i đo chơi

	public Database(Context context) {
		super(context, NAME_DB, null, VERSION);
	}

	/**
	 * Tạo bảng
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_DOLLAR + " ( " + ID + " INTEGER PRIMARY KEY " + "," + NAME + " TEXT NOT NULL"
				+ "," + DOLLAR + " INTEGER NOT NULL" + "," + THEME + " INTEGER NOT NULL " + ");");
		MyLog.LogInfo("Database onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOLLAR);
		onCreate(db);
		MyLog.LogInfo("Database onUpgrade");
	}

	// ---------------------------------------------------------------------------
	/**
	 * Mở Database. Nếu csdl chưa có sẽ được tự tạo ra
	 */
	public void openDatabase() {
		mSQLiteDatabase = this.getWritableDatabase();
	}

	// ---------------------------------------------------------------------------
	/**
	 * �?óng Database
	 */
	public void closeDatabase() {
		this.close();
	}

	// ---------------------------------------------------------------------------
	/**
	 * Thực hiện 1 câu lệnh sql
	 *
	 * @param sql
	 */
	public void execSQL(String sql) {
		this.execSQL(sql);
	}

	// ---------------------------------------------------------------------------
	/**
	 * Thực hiện 1 câu lệnh query
	 *
	 * @param query
	 * @return Cursor
	 */
	public Cursor getCursorQuery(String table, String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		return mSQLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	// ===========================================================================
	/**
	 * Thêm 1 ngư�?i vào bảng điểm
	 * 
	 * @param mClassDollar
	 */
	public void addDollar(ClassDollar mClassDollar) {
		if (mSQLiteDatabase.isOpen()) {
			int id = checkIsInsert(mClassDollar);
			if (id == -10) {
				ContentValues cv = new ContentValues();
				cv.put(NAME, mClassDollar.getName());
				cv.put(DOLLAR, mClassDollar.getDollar());
				cv.put(THEME, mClassDollar.getTheme());

				mSQLiteDatabase.insert(TABLE_DOLLAR, null, cv);
			} else {
				updateDollar(mClassDollar, id);
			}

			logList(mClassDollar.getTheme());
		}
	}

	/**
	 * cập nhật lại tên và điểm của ngư�?i chơi
	 * 
	 * @param mClassDollar
	 * @param id
	 */
	public void updateDollar(ClassDollar mClassDollar, int id) {
		if (mSQLiteDatabase.isOpen()) {

			ContentValues cv = new ContentValues();
			cv.put(NAME, mClassDollar.getName());
			cv.put(DOLLAR, mClassDollar.getDollar());
			cv.put(THEME, mClassDollar.getTheme());
			mSQLiteDatabase.update(TABLE_DOLLAR, cv, ID + "=" + id, null);
		}
	}

	// ---------------------------------------------------------------------------
	/**
	 * Kiểm tra xem số điểm ngư�?i chơi có nằm trong tốp 10 hay không
	 * 
	 * @param name
	 * @return
	 */
	public int checkIsInsert(ClassDollar mClassDollar) {
		if (mSQLiteDatabase.isOpen()) {
			Cursor mCursor = getCursorQuery(TABLE_DOLLAR, null, THEME + "=" + mClassDollar.getTheme(), null, null, null,
					DOLLAR + " DESC");
			if (mCursor.getCount() < 10) {
				mCursor.close();
				return -10;
			} else {
				mCursor.moveToLast();
				int dollar = mCursor.getInt(mCursor.getColumnIndex(DOLLAR));
				int id = mCursor.getInt(mCursor.getColumnIndex(ID));
				if (mClassDollar.getDollar() > dollar)
					return id;
			}
			mCursor.close();
		}
		return -1;
	}

	// ---------------------------------------------------------------------------
	/**
	 * Lấy danh sách ngư�?i chơi chiến thắng lưu trong csdl
	 * 
	 * @return
	 */
	public ArrayList<ClassDollar> getListDollar(int theme) {
		ArrayList<ClassDollar> listData = new ArrayList<ClassDollar>();
		if (mSQLiteDatabase.isOpen()) {
			Cursor mCursor = getCursorQuery(TABLE_DOLLAR, null, THEME + "=" + theme, null, null, null,
					DOLLAR + " DESC");
			while (mCursor.moveToNext()) {
				int dollar = mCursor.getInt(mCursor.getColumnIndex(DOLLAR));
				String name = mCursor.getString(mCursor.getColumnIndex(NAME));
				int theme_tmp = mCursor.getInt(mCursor.getColumnIndex(THEME));
				listData.add(new ClassDollar(name, dollar, theme_tmp));
			}
			mCursor.close();
		}
		return listData;
	} 

	public void logList(int theme) {
		ArrayList<ClassDollar> listData = getListDollar(theme);
		for (int i = 0; i < listData.size(); i++) {
			ClassDollar mClassDollar = listData.get(i);
			MyLog.LogInfo("Name " + mClassDollar.getName() + " - Dollar = " + mClassDollar.getDollar() + " - Theme = "
					+ mClassDollar.getTheme());
		}
	}

}
