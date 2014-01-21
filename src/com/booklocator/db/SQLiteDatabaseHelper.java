package com.booklocator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "book_db.sqlite";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "book";
	public static final String BOOK_ISBN = "isbn";
	public static final String BOOK_TITLE = "title";
	public static final String BOOK_TITLE_ENGLISH = "title_english";
	public static final String AUTHOR = "author";
	public static final String AUTHOR_ENGLISH = "author_english";
	public static final String CATEGORY = "category";
	public static final String PUBLISHER = "publisher";
	public static final String PUBLISHER_ENGLISH = "publisher_english";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";

	public SQLiteDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createMovieTable(db);
	}

	private void createMovieTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + BOOK_ISBN
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + BOOK_TITLE
				+ " TEXT, " + BOOK_TITLE_ENGLISH + " TEXT, " + AUTHOR
				+ " TEXT, " + AUTHOR_ENGLISH + " TEXT, " + PUBLISHER
				+ " TEXT, " + PUBLISHER_ENGLISH + " TEXT, " + PRICE
				+ " REAL, " + DESCRIPTION + " TEXT " + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
