package com.booklocator.db;

import java.util.ArrayList;

import com.booklocator.model.Book;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import static com.booklocator.db.SQLiteDatabaseHelper.*;

public class BookDatabaseHelper extends Application {
	private SQLiteDatabaseHelper helper;
	private SQLiteDatabase database;

	private BookDatabaseHelper() {
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		helper = new SQLiteDatabaseHelper(this);
		database = helper.getWritableDatabase();
	}

	public void insertData(ArrayList<Book> books) {
		for (Book book : books) {
			ContentValues values = new ContentValues();
			book.setIsbn(book.getIsbn());
			values.put(BOOK_TITLE, book.getTitle());
			values.put(BOOK_TITLE_ENGLISH, book.getTitleInEnglish());
			values.put(AUTHOR, book.getAuthor());
			values.put(AUTHOR_ENGLISH, book.getAuthorInEnglish());
			values.put(CATEGORY, book.getCategory());
			values.put(PUBLISHER, book.getPublisher());
			values.put(PUBLISHER_ENGLISH, book.getPublisherInEnglish());
			values.put(PRICE, book.getPrice());
			values.put(DESCRIPTION, book.getDescription());
			database.insert(TABLE_NAME, null, values);			
		}

	}

}
