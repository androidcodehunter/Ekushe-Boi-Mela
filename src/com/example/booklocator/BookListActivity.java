package com.example.booklocator;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TwoLineListItem;

public class BookListActivity extends ActionBarActivity {

	ActionBar actionBar;
	SpinnerAdapter spinnerAdapter;
	EditText searchBox;
	Spinner priceRange;
	ListView testBookList;
	List<Book> bookList;
	MenuItem searchItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		testBookList = (ListView) findViewById(R.id.lv_data_test);

		bookList = TestData.books;

		ArrayAdapter<Book> bookDataAdapter = new ArrayAdapter<Book>(this,
				android.R.layout.simple_list_item_2, bookList) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TwoLineListItem row;
				if (convertView == null) {
					LayoutInflater inflater = (LayoutInflater) getApplicationContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = (TwoLineListItem) inflater.inflate(
							android.R.layout.simple_list_item_2, null);
				} else {
					row = (TwoLineListItem) convertView;
				}
				Book data = bookList.get(position);
				row.getText1().setText(data.authorBang);
				row.getText2().setText(data.titleBang);

				return row;
			}
		};

		testBookList.setAdapter(bookDataAdapter);

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
		actionBar.setListNavigationCallbacks(spinnerAdapter, navigationListener);
		actionBar.setCustomView(R.layout.actionbar_search);
		priceRange = (Spinner) actionBar.getCustomView().findViewById(
				R.id.spinner_price_range);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
		getOverflowMenu();
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

		for (Book book : bookList) {
			double sim = EditDistance.similarity(book.authorEng, queryText);
			if (sim > max) {
				max = sim;
				bookIndex = bookList.indexOf(book);
			}
		}

		Log.d("best match", "" + bookList.get(bookIndex).authorBang);
	}
}
