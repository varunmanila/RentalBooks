package com.example.RentalBooks.Service;

import com.example.RentalBooks.Entity.Books;
import com.example.RentalBooks.Entity.CustomerBooks;
import com.example.RentalBooks.Entity.Users;
import com.example.RentalBooks.Repocitory.BooksRepocitory;
import com.example.RentalBooks.Repocitory.UsersRepocitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    @Autowired
    private BooksRepocitory booksRepocitory;
    @Autowired
    UsersRepocitory usersRepocitory;
    public Books saveBooks(Books books) {
       return booksRepocitory.save(books);
    }

    @Transactional
    public Object rentBooks(CustomerBooks customerBooks, Users users) {
        List<Books> booksList = booksRepocitory.findAllById(customerBooks.getBookIds());
        if (users.getBooksList().size() + booksList.size() > 2) {
            throw new RuntimeException("User can have a maximum of 2 books at a time");
        }
        List<Books>userBooks=users.getBooksList();
        userBooks.addAll(booksList);
        users.setBooksList(userBooks);
        return usersRepocitory.save(users);
    }



    public Object returnBooks(CustomerBooks customerBooks,Users users) {
        List<Books> booksList = booksRepocitory.findAllById(customerBooks.getBookIds());
        List<Books>userBooks=users.getBooksList();
        if(users.getBooksList().size()>0) {
            userBooks.removeIf(userBook ->
                    booksList.stream().anyMatch(book -> book.getId().equals(userBook.getId()))
            );
        }else {
            throw new RuntimeException("No Books Found Foruser "+users.getUserName());
        }
        users.setBooksList(userBooks);
        return usersRepocitory.save(users);
    }

    public Object deleteBooks(Books books) {
        booksRepocitory.delete(books);
        return "Success";
    }

    public Object updateBooks(Books books, Books books1) {
        books1.setAuthor(books.getAuthor());
        books1.setName(books.getName());
        books1.setAvailable(books.getAvailable());
        books1.setGenere(books.getGenere());
     return booksRepocitory.save(books1);
    }
}
