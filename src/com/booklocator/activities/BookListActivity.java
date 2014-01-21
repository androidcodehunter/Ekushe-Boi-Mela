package com.booklocator.activities;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.booklocator.adapter.BookAdapter;
import com.booklocator.db.BookDatabaseHelper;
import com.booklocator.interfaces.JsonTaskCompleteListener;
import com.booklocator.model.Book;
import com.booklocator.utilities.VolleyHelper;

public class BookListActivity extends ActionBarActivity implements
		JsonTaskCompleteListener<JSONObject> {

	ActionBar actionBar;
	SpinnerAdapter spinnerAdapter;
	EditText searchBox;
	Spinner priceRange;
	MenuItem searchItem;
	private ArrayList<Book> books;
	private BookDatabaseHelper bookDb;
	private ListView listView;
	private BookAdapter adapter;

	private static final String MOVIE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=atqyn9symqb65f3vps4tkv3z&page_limit=20";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		listView = (ListView) findViewById(R.id.listView);

		spinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.search_types,
				android.R.layout.simple_spinner_dropdown_item);

		OnNavigationListener navigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {

				if (spinnerAdapter.getItem(position).toString()
						.equals(getString(R.string.price))) {
					searchItem.setVisible(false);
					priceRange.setVisibility(View.VISIBLE);
				} else {
					searchItem.setVisible(true);
					priceRange.setVisibility(View.INVISIBLE);
				}
				return true;
			}
		};

		actionBar = getSupportActionBar();

		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar
				.setListNavigationCallbacks(spinnerAdapter, navigationListener);
		actionBar.setCustomView(R.layout.actionbar_search);
		priceRange = (Spinner) actionBar.getCustomView().findViewById(
				R.id.spinner_price_range);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
		getOverflowMenu();

		VolleyHelper volleyHelper = VolleyHelper.getInstance(this, this);
		volleyHelper.getJsonObject(MOVIE_URL);

		// bookDb = (BookDatabaseHelper) getApplication();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);

		searchItem = menu.findItem(R.id.action_search);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			onSearchRequested();
			return true;
		}
		return super.onOptionsItemSelected(item);
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

	public void findBestMatch(String queryText) {
		double max = 0.0;
		int bookIndex = 0;

		for (Book book : books) {
			double sim = EditDistance.similarity(book.getAuthor(), queryText);
			if (sim > max) {
				max = sim;
				bookIndex = books.indexOf(book);
			}
		}
		
		Log.d("best match", "" + books.get(bookIndex).getAuthor());
	}

	@Override
	public void onJsonObject(JSONObject result) {

		books = new ArrayList<Book>();
		JSONArray jArray;
		try {
			jArray = result.getJSONArray("movies");
			long isbn = 0;
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

			refreshMovieList(books);

			// bookDb.insertData(books);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void refreshMovieList(ArrayList<Book> books) {
		adapter = new BookAdapter(books, this);
		listView.setAdapter(adapter);
		adapter.forceReload();
	}
}
