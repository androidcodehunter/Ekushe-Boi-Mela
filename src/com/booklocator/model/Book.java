package com.booklocator.model;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	private long isbn;
	private String title;
	private String titleInEnglish;
	private String author;
	private String authorInEnglish;
	private String category;
	private String publisher;
	private String publisherInEnglish;
	private double price;
	private String description;
	private Byte[] coverImage;

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleInEnglish() {
		return titleInEnglish;
	}

	public void setTitleInEnglish(String titleInEnglish) {
		this.titleInEnglish = titleInEnglish;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorInEnglish() {
		return authorInEnglish;
	}

	public void setAuthorInEnglish(String authorInEnglish) {
		this.authorInEnglish = authorInEnglish;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherInEnglish() {
		return publisherInEnglish;
	}

	public void setPublisherInEnglish(String publisherInEnglish) {
		this.publisherInEnglish = publisherInEnglish;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte[] getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(Byte[] coverImage) {
		this.coverImage = coverImage;
	}

}
