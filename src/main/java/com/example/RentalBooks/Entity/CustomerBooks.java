package com.example.RentalBooks.Entity;

import lombok.Data;

import java.util.List;

@Data
public class CustomerBooks {
    private Integer customerId;
    private List<Integer> bookIds;
}