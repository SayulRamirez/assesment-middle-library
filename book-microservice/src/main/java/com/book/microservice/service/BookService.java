package com.book.microservice.service;

import com.book.microservice.dto.BookResponse;
import org.springframework.core.io.Resource;

import java.util.List;

public interface BookService {

    /**
     * Retrieves all books.
     * @return a List of {@link BookResponse}
     */
    List<BookResponse> getAll();

    /**
     * Load the full content of a book by its ID.
     * @param id {@link Integer} of the book
     * @return the file as a {@link Resource}
     */
    Resource getById(Integer id);

    /**
     * Load the cover image of a book by its ID.
     * @param id {@link Integer} of the book
     * @return the file as a {@link Resource}
     */
    Resource getCoverByBookId(Integer id);
}
