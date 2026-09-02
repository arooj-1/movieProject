package com.sparta.movieproject.dto;

public class MovieDto {
    private int id;
    private String title;

    //constructor
    public MovieDto() {}

    public MovieDto(int id, String title) {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
