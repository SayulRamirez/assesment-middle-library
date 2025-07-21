package com.book.microservice.service;

import com.book.microservice.dto.BookResponse;
import com.book.microservice.entity.Book;
import com.book.microservice.exception.BookNotFoundException;
import com.book.microservice.exception.ResourceNotFoundException;
import com.book.microservice.reporsitory.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Value("${storage.base.path}")
    private String path;

    private final BookRepository repository;

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

        return loadResource(path + "books/book_" + book.getId() + ".txt")
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el libro: " + book.getTitle()));
    }

    @Override
    public Resource getCoverByBookId(Integer id) {

        Book book = findBookById(id, "No se encontro la caratula");
        String pathCover = path + "covers/cover_" + book.getId() + ".jpg";

        return loadResource(pathCover)
                .orElseGet(() -> loadResource(path + "covers/cover_0.jpg")
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontro la caratula del libro")));
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
    private Optional<Resource> loadResource(String path) {
        File file = new File(path);
        return file.exists() ? Optional.of(new FileSystemResource(file)) : Optional.empty();
    }
}
