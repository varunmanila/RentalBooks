package com.example.RentalBooks.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Table(name = "tblusers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "user_name")
    private  String userName;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    List<Books> booksList=new ArrayList<>();

}
