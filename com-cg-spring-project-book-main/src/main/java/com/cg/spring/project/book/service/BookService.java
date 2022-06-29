package com.cg.spring.project.book.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cg.spring.project.book.exception.BookNotFoundException;
import com.cg.spring.project.book.model.Book;
import com.cg.spring.project.book.repository.BookRepository;
import com.cg.spring.project.book.repository.UserRepository;

@Service
public class BookService{

	@Autowired
	BookRepository bookRepository;

	@Autowired
    UserRepository userRepository;

	@Autowired
	UserService userService;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public List<Book> getAllBooks() {
		LOG.info("getAllBooks");
		return bookRepository.findAll();
	}

	public Book getBookById(int bookId) {
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		Book book = null;
		if (bookOptional.isPresent()) {
			book =bookOptional.get();
			LOG.info(book.toString());
			return book;
		} else {
			String errorMessage = "Book with id " + bookId + " does not exist.";
			LOG.error(errorMessage);
			throw new BookNotFoundException(errorMessage);
		}
	}

	public List<Book> getBookByName(String Name) {
		LOG.info(Name);
		List<Book> bookList = bookRepository.findByNameIgnoreCase(Name);
		if (null != bookList)
			return bookList;
		String errorMessage = "Book with Name " + Name + " does not exist.";
		throw new BookNotFoundException(errorMessage);
	}

	public Book addBook(Book book) {
		LOG.info(Book.toString());
		if(book.getAuthor()!= null)
			
		authorService.getAuthorByBookName(book.getAuthor().getBookName());
	
		return bookRepository.save(book);
	}

	public Book updateBook(Book book) {
		LOG.info(Book.toString());
		this.getBookById(Book.getBookId());
		
		return bookRepository.save(book);
	}

	public Book deleteBook(int id) { 
		LOG.info(Integer.toString(id));
		Book bookToDelete = this.getBookById(id);
		bookRepository.delete(bookToDelete);
		return bookToDelete;
	}

}

