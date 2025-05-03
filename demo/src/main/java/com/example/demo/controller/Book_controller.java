package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@RestController
public class Book_controller {

	@Autowired
	private BookService bookservice;

	@GetMapping("/books")
	public List<Book> getAllBooks() {

		return this.bookservice.getAllBooks();
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
		Book book = bookservice.getBookById(id);

		if (book == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(book);
	}



	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
	    try {
	        return ResponseEntity.ok(bookservice.addBook(book));
	    } catch (Exception e) {
	        e.printStackTrace(); // Optional: Log for debugging
	        return ResponseEntity.internalServerError().build();
	    }
	}


	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<Void> deleteBookById(@PathVariable("book_id") int book_id) {
		try {
			bookservice.deleteBookById(book_id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/books/{bookId}")
	public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable("bookId") int bookId) {
		try {
			bookservice.updateBookById(book, bookId);
			return ResponseEntity.ok(book);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

}
