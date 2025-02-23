package com.example.RentalBooks.Repocitory;

import com.example.RentalBooks.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepocitory extends JpaRepository<Users,Integer> {
  Optional<Users> findByUserName(String username);
}
