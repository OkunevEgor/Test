package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id" , referencedColumnName = "pid", nullable = false)
    private Author author;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Long yearOfIssue;

    @Column
    private Long totalOfSheets;

    @Column(nullable = false, length = 100)
    private Long totalNumberOfBookInTheLibrary = 0L;

    @Column(nullable = false, length = 100)
    private Long totalOfBooks = 0L;

    public Book(Long pid, String name, Genre genre, Author author, Long yearOfIssue,
                Long totalOfSheets, Long totalNumberOfBookInTheLibrary, Long totalOfBooks) {
        this.pid = pid;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.yearOfIssue = yearOfIssue;
        this.totalOfSheets = totalOfSheets;
        this.totalNumberOfBookInTheLibrary = totalNumberOfBookInTheLibrary;
        this.totalOfBooks = totalOfBooks;
    }

    public Book() {

    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(Long yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Long getTotalOfSheets() {
        return totalOfSheets;
    }

    public void setTotalOfSheets(Long totalOfSheets) {
        this.totalOfSheets = totalOfSheets;
    }

    public Long getTotalNumberOfBookInTheLibrary() {
        return totalNumberOfBookInTheLibrary;
    }

    public void setTotalNumberOfBookInTheLibrary(Long totalNumberOfBookInTheLibrary) {
        this.totalNumberOfBookInTheLibrary = totalNumberOfBookInTheLibrary;
    }

    public Long getTotalOfBooks() {
        return totalOfBooks;
    }

    public void setTotalOfBooks(Long totalOfBooks) {
        this.totalOfBooks = totalOfBooks;
    }
}
