package ru.chabanov.jdbc.entity;

import lombok.Data;



@Data
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

}
