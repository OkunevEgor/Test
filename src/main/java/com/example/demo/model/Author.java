package com.example.demo.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false, length = 100, unique = true)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    @Column
    private String lastName;

    @Column
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book>  listOfBooks;

    public Author() {
    }

    public Author(Long pid, String firstName, String middleName, String lastName,
                   List<Book> listOfBooks) {
        this.pid = pid;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.listOfBooks = listOfBooks;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public List<Book> getListOfBooks() {
        return listOfBooks;
    }

    public void setListOfBooks(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }

}
