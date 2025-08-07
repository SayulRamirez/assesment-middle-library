package com.book.microservice.service;

import com.book.microservice.dto.BookResponse;
import com.book.microservice.entity.Book;
import com.book.microservice.exception.BookNotFoundException;
import com.book.microservice.reporsitory.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final ResourceLoader resourceLoader;

    @Override
    public List<BookResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getSummary(),
                        book.getAuthorId().getNames().concat(" ").concat(book.getAuthorId().getLastName()),
                        book.getLiteraryGenre().getGenre()
                )).toList();
    }

    @Override
    public Resource getById(Integer id) {

        Book book = findBookById(id, "No se encontro el libro buscado");

        return loadResource("books/book_" + book.getId() + ".txt");
    }

    @Override
    public Resource getCoverByBookId(Integer id) {

        Book book = findBookById(id, "No se encontro la caratula");

        return loadResource("covers/cover_" + book.getId() + ".jpg");
    }

    /**
     * Retrieves book by id or else throw {@link BookNotFoundException} if not found book
     * @param id {@link Integer} book id
     * @param messageException message if exception throw
     * @return Book
     */
    private Book findBookById(Integer id, String messageException) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(messageException));
    }

    /**
     * Retrieves a file.
     * @param path {@link String} of file
     * @return Optional of the file as {@link Resource} if file is present or else Optional empty
     */
    private Resource loadResource(String path) {
        return resourceLoader
                .getResource("classpath:static/" + path);
    }
}
