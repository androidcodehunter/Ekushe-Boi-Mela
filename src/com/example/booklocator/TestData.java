package com.example.booklocator;

import java.util.ArrayList;
import java.util.List;

public class TestData {

	public static List<Book> books = new ArrayList<Book>();
	
	static {
		books.add(new Book("Humayun Ahmed", "হুমায়ুন আহমেদ", "Voot", "ভূত", "200"));
		books.add(new Book("Jafar Iqbal", "জাফর ইকবাল", "Pret", "প্রেত", "250"));
		books.add(new Book("Humayun Azad", "হুমায়ুন আজাদ", "Bangla", "বাংলা", "300"));
		books.add(new Book("Anisul Haque", "আনিসুল হক", "Kishor", "কিশোর", "150"));
	}
}
