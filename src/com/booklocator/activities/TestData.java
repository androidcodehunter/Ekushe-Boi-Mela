package com.booklocator.activities;

import java.util.ArrayList;
import java.util.List;

public class TestData {

	public static List<Book> books = new ArrayList<Book>();
	
	static {
		books.add(new Book("Humayun Ahmed", "হ�?মায়�?ন আহমেদ", "Voot", "ভূত", "200"));
		books.add(new Book("Jafar Iqbal", "জাফর ইকবাল", "Pret", "প�?রেত", "250"));
		books.add(new Book("Humayun Azad", "হ�?মায়�?ন আজাদ", "Bangla", "বাংলা", "300"));
		books.add(new Book("Anisul Haque", "আনিস�?ল হক", "Kishor", "কিশোর", "150"));
	}
}
