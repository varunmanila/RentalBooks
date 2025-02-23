package com.example.RentalBooks.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tblbooks")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "genere")
    private String genere;

    @Column(name = "is_available")
    private Boolean available = true;

    @JsonBackReference
    @ManyToMany(mappedBy = "booksList", fetch = FetchType.EAGER)
    private List<Users> users;

}
