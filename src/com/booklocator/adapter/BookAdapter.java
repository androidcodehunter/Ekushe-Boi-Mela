package com.booklocator.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.booklocator.activities.R;
import com.booklocator.model.Book;

public class BookAdapter extends BaseAdapter {

	private ArrayList<Book> books;
	private Context context;

	public BookAdapter(ArrayList<Book> books, Context context) {
		super();
		this.books = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Book getItem(int position) {
		return books == null ? null : books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView title;
		TextView author;
		TextView publisher;
		TextView price;
		TextView description;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Book book = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.book_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);

			holder.author = (TextView) convertView.findViewById(R.id.author);

			holder.publisher = (TextView) convertView
					.findViewById(R.id.publisher);

			holder.price = (TextView) convertView.findViewById(R.id.price);

			holder.description = (TextView) convertView
					.findViewById(R.id.description);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.title.setText(book.getTitle());
		holder.author.setText(book.getAuthor());
		holder.publisher.setText(book.getPublisher());
		holder.price.setText("" + book.getPrice());
		holder.description.setText(book.getDescription());

		return convertView;
	}

	public void forceReload() {
		notifyDataSetChanged();
	}

}
