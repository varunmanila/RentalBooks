package com.example.RentalBooks;

import com.example.RentalBooks.Entity.*;
import com.example.RentalBooks.Repocitory.BooksRepocitory;
import com.example.RentalBooks.Repocitory.UsersRepocitory;
import com.example.RentalBooks.Service.BooksService;
import com.example.RentalBooks.Service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksTest {
    @Autowired
    UsersRepocitory usersRepocitory;
    @Autowired
    BooksRepocitory booksRepocitory;
    @Autowired
    UsersService usersService;
    @Autowired
    BooksService booksService;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
//        booksRepocitory.deleteAll();
//        usersRepocitory.deleteAll();
    }

    @Test
    void testRegisterUser() {
        RegisterRequest request=new RegisterRequest();
        request.setUserName("Ram");
        request.setEmail("ram@gmail.com");
        request.setPassword("ram@1234");
        request.setFirstName("ram");
        request.setLastName("kk");
        AuthResponse response =usersService.registerUser(request);
        assertNotNull(response);
        assertEquals("Success", response.getMessage());
    }
    @Test
    void testAddBooks() {
        Books books=new Books();
        books.setName("Wings Of Fire");
        books.setAuthor("A P J Abdul Kalam");
        books.setGenere("Story");
        Books books1 =booksService.saveBooks(books);
        assertNotNull(books1);
        assertEquals("Wings Of Fire", books1.getName());
    }
    @Test
    void testRentBook(){
        Optional<Users> users=usersRepocitory.findByUserName("Ram");
        CustomerBooks customerBooks=new CustomerBooks();
        customerBooks.setCustomerId(users.get().getId());
        customerBooks.setBookIds(Arrays.asList(1));
       Object o = booksService.rentBooks(customerBooks,users.get());
       Users user1=(Users)o;
        assertNotNull(o);
        assertEquals(user1.getUserName(),"Ram");

    }



}
