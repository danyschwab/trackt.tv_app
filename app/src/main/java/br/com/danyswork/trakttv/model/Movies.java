package br.com.danyswork.trakttv.model;

import java.util.List;

public class Movies {

    private String title;
    private int year;
    private Ids ids;
    private String tagline;
    private String overview;
    private String released;
    private int runtime;
    private String trailer;
    private String homepage;
    private double rating;
    private int votes;
    private String updated_at;
    private String language;
    private List<String> available_translations;
    private List<String> genres;
    private String certification;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
