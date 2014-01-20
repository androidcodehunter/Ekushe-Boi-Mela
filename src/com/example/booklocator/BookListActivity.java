package com.example.booklocator;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewConfiguration;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.booklocator.db.BookDatabaseHelper;
import com.booklocator.interfaces.JsonTaskCompleteListener;
import com.booklocator.model.Book;
import com.booklocator.utilities.VolleyHelper;

public class BookListActivity extends Activity implements OnQueryTextListener,
		JsonTaskCompleteListener<JSONObject> {
	private static final String MOVIE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=atqyn9symqb65f3vps4tkv3z&page_limit=20";
	private ArrayList<Book> books;
	private BookDatabaseHelper bookDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		getOverflowMenu();
		TextView tv = (TextView) findViewById(R.id.two);
		tv.setText("I am hero");

		VolleyHelper volleyHelper = VolleyHelper.getInstance(this, this);
		volleyHelper.getJsonObject(MOVIE_URL);

		bookDb = (BookDatabaseHelper) getApplication();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		searchView.setOnQueryTextListener(this);

		return super.onCreateOptionsMenu(menu);
	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		Log.d("inside", "onQueryTextChanged");
		return true;
	}

	@Override
	public void onJsonObject(JSONObject result) {

		books = new ArrayList<Book>();
		JSONArray jArray;
		try {
			jArray = result.getJSONArray("movies");
			long isbn=0;
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonBooks = jArray.getJSONObject(i);
				Book book = new Book();
				book.setIsbn(isbn++);
				book.setAuthor(jsonBooks.getString("title"));
				book.setAuthorInEnglish(jsonBooks.getString("title"));
				book.setCategory(jsonBooks.getString("title"));
				book.setPrice(10);
				book.setPublisher(jsonBooks.getString("title"));
				book.setPublisherInEnglish(jsonBooks.getString("title"));
				book.setDescription(jsonBooks.getString("synopsis"));
				books.add(book);
			}

			bookDb.insertData(books);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
