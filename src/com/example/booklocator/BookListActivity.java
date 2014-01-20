package com.example.booklocator;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TwoLineListItem;

public class BookListActivity extends ActionBarActivity {

	ActionBar actionBar;
	SpinnerAdapter spinnerAdapter;
	// SearchView searchView;
	EditText searchBox;
	Spinner priceRange;
	ListView testBookList;
	List<Book> bookList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		testBookList = (ListView) findViewById(R.id.lv_data_test);

		TestData.populate();
		bookList = TestData.books;
		
		ArrayAdapter<Book> bookDataAdapter = new ArrayAdapter<Book>(this,
				android.R.layout.simple_list_item_2, bookList){

			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				TwoLineListItem row;            
		        if(convertView == null){
		            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);                    
		        }else{
		            row = (TwoLineListItem)convertView;
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
					searchBox.setVisibility(View.INVISIBLE);
					priceRange.setVisibility(View.VISIBLE);
				} else {
					searchBox.setVisibility(View.VISIBLE);
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
		searchBox = (EditText) actionBar.getCustomView().findViewById(
				R.id.et_search_field);
		searchBox.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView tv, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					searchBox.clearFocus();
					
					String queryText = searchBox.getText().toString();
					if(!TextUtils.isEmpty(queryText))
						search(queryText);
				}
				return false;
			}
		});
		searchBox.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
		getOverflowMenu();
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present. MenuInflater
	 * inflater = getMenuInflater(); inflater.inflate(R.menu.search, menu);
	 * 
	 * // Associate searchable configuration with the SearchView MenuItem
	 * searchItem = menu.findItem(R.id.action_search);
	 * 
	 * SearchManager searchManager = (SearchManager)
	 * getSystemService(Context.SEARCH_SERVICE); searchView = (SearchView)
	 * MenuItemCompat .getActionView(searchItem);
	 * searchView.setIconifiedByDefault(true);
	 * searchView.setSearchableInfo(searchManager
	 * .getSearchableInfo(getComponentName()));
	 * 
	 * return super.onCreateOptionsMenu(menu); }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			Log.d("search", "requested");
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
	
	public void search(String queryText)
	{
		double max = 0.0;
		int bookIndex = 0;
		
		for(Book book : bookList)
		{
			double sim = EditDistance.similarity(book.authorEng, queryText);
			if(sim > max)
			{
				max = sim;
				bookIndex = bookList.indexOf(book);
			} 
		}			
		
		Log.d("best match", "" + bookList.get(bookIndex).authorBang);
	}
}
