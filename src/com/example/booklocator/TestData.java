package com.example.booklocator;

import java.util.ArrayList;
import java.util.List;

public class TestData {

	public static List<Book> books = new ArrayList<Book>();
	
	public static void populate()
	{
		books.add(new Book("Humayun", "হুমায়ুন", "Voot", "ভূত", "200"));
		books.add(new Book("Jafar", "জাফর", "Pret", "প্রেত", "250"));
		books.add(new Book("Azad", "আজাদ", "Bangla", "বাংলা", "300"));
		books.add(new Book("Anisul", "আনিসুল হক", "Kishor", "কিশোর", "150"));
	}
}
