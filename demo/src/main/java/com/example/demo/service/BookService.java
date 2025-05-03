package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Book;

@Component
public class BookService {

	private static List<Book> books = new ArrayList<>();

	static {
		books.add(new Book(101, "book1", "author-1"));
		books.add(new Book(102, "book2", "author-2"));
		books.add(new Book(103, "book3", "author-3"));
	}

	public List<Book> getAllBooks() {
		return books;
	}

	public Book getBookById(int id) {
	        for (Book b : books) {
	            if (b.getId() == id) {
	                return b;
	            }
	        }

	    return null;
	}
	
	public Book addBook(Book b) {
		books.add(b);
		return b;
	}
	
	public void deleteBookById(int book_id) {
	    Book bookToDelete = null;

	    for (Book bk : books) {
	        if (bk.getId() == book_id) {
	            bookToDelete = bk;
	            break;
	        }
	    }

	    if (bookToDelete != null) {
	        books.remove(bookToDelete);
	    }
		System.out.println("Succcessfully deleted book id number: "+ book_id);

	}
	
	public void updateBookById(Book book, int bookId) {
	    for (Book bk : books) {
	        if (bk.getId() == bookId) {
	        	
	            bk.setTitle(book.getTitle());
	            bk.setAuthor(book.getAuthor());
	            bk.setId(book.getId());

	            break;
	        }
	    }
	}
}
