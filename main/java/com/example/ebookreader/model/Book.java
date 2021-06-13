package com.example.ebookreader.model;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String title;
    private int pages;
    private String author;
    private String coverArt;
    private List<String> pagesList;

    /**
     * Necessary to fetch as Object from firebase
     */
    public Book() {

    }

    /**
     * @param title     title of the book
     * @param pages     number of pages of the book
     * @param author    author of the book
     * @param coverArt  cover art of the book
     * @param pagesList list of URLs of pages of the book
     */
    public Book(String title, int pages, String author, String coverArt, List<String> pagesList) {
        this.title = title;
        this.pages = pages;
        this.pagesList = pagesList;
        this.author = author;
        this.coverArt = coverArt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverPage() {
        return coverArt;
    }

    public void setCoverPage(String coverArt) {
        this.coverArt = coverArt;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<String> getPagesList() {
        return pagesList;
    }

    public void setPagesList(ArrayList<String> pagesList) {
        this.pagesList = pagesList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
