package com.example.RentalBooks.Controller;

import com.example.RentalBooks.Entity.Books;
import com.example.RentalBooks.Entity.CustomerBooks;
import com.example.RentalBooks.Entity.Users;
import com.example.RentalBooks.Repocitory.BooksRepocitory;
import com.example.RentalBooks.Repocitory.UsersRepocitory;
import com.example.RentalBooks.Service.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/books")
@Slf4j
public class BooksController {
    @Autowired
    private BooksService booksService;
    @Autowired
    private BooksRepocitory booksRepocitory;
    @Autowired
    private UsersRepocitory usersRepocitory;
    public  static  final Logger logger=  LoggerFactory.getLogger("BooksController.class");

    @PostMapping("/saveBook")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity saveBook(@RequestBody Books books, Authentication authentication){
        try {
            if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return new ResponseEntity<>("{\"error\": \"You do not have permission to access this resource\"}", HttpStatus.FORBIDDEN);
            }
            if(Objects.nonNull(books)){
                logger.info("Books added Successfully");
              return new ResponseEntity<>(booksService.saveBooks(books),HttpStatus.OK);
            }else {
                throw new RuntimeException("Books Cannot be null");
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{booksId}")
    public ResponseEntity saveBook(@PathVariable Integer booksId,@RequestBody Books books,Authentication authentication){
        try {
            if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return new ResponseEntity<>("{\"error\": \"You do not have permission to access this resource\"}", HttpStatus.FORBIDDEN);
            }
            if(Objects.nonNull(booksId)){
                Optional<Books> books1=booksRepocitory.findById(booksId);
                if(Objects.nonNull(books1)){
                    return new ResponseEntity<>(booksService.updateBooks(books,books1.get()),HttpStatus.OK);
                }else {
                    throw new RuntimeException("No Books Found");
                }
            }else {

                return new ResponseEntity<>("Books Not Found with id"+booksId,HttpStatus.NOT_FOUND);
            }


        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/rent/{usersId}")
    public ResponseEntity rentBook(@PathVariable Integer usersId, @RequestBody CustomerBooks customerBooks){
        try {
            if(Objects.nonNull(usersId)){
                Optional<Users> users=usersRepocitory.findById(usersId);
                if(users.isPresent()) {
                    if (Objects.nonNull(customerBooks)) {
                        return new ResponseEntity<>(booksService.rentBooks(customerBooks,users.get()), HttpStatus.OK);
                    } else {
                        throw new RuntimeException("Books Cannot be null");
                    }
                }else {
                    logger.error("User Not Found with id"+usersId);
                    return new ResponseEntity<>("User Not Found id"+usersId,HttpStatus.NOT_FOUND);
                }
            }else {
                logger.error("User Not Found with id"+usersId);
                return new ResponseEntity<>("User Not Found with id"+usersId,HttpStatus.NOT_FOUND);
            }


        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/return/{usersId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity returnBook(@PathVariable Integer usersId,@RequestBody CustomerBooks customerBooks){
        try {
            if(Objects.nonNull(usersId)){
                Optional<Users> users=usersRepocitory.findById(usersId);
                if(users.isPresent()) {
                if(Objects.nonNull(customerBooks)){
                    return new ResponseEntity<>(booksService.returnBooks(customerBooks,users.get()),HttpStatus.OK);
                } else {
                    throw new RuntimeException("Books Cannot be null");
                }
            }else {
                    logger.error("User Not Found with id"+usersId);
                return new ResponseEntity<>("User Not Found id"+usersId,HttpStatus.NOT_FOUND);
            }
        }else {
                logger.error("User Not Found with id"+usersId);
            return new ResponseEntity<>("User Not Found with id"+usersId,HttpStatus.NOT_FOUND);
        }


        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{booksId}")
    public ResponseEntity deleteBook(@PathVariable Integer booksId,Authentication authentication){
        try {
            if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return new ResponseEntity<>("{\"error\": \"You do not have permission to access this resource\"}", HttpStatus.FORBIDDEN);
            }
            if(Objects.nonNull(booksId)){
                Optional<Books>books=booksRepocitory.findById(booksId);
                if(Objects.nonNull(books)){
                    return new ResponseEntity<>(booksService.deleteBooks(books.get()),HttpStatus.OK);
                }else {
                    throw new RuntimeException("Books Cannot be null");
                }
            }else {

                return new ResponseEntity<>("Books Not Found with id"+booksId,HttpStatus.NOT_FOUND);
            }


        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
}
