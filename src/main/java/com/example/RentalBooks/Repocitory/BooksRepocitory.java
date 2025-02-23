package com.example.RentalBooks.Repocitory;

import com.example.RentalBooks.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepocitory extends JpaRepository<Books,Integer> {
}
