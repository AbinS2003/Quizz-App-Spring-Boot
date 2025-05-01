package com.abin.quizzapp.dto;

public class QuizForm {
    private String category;
    private String title;
    private int numQ;

    // Getters and setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getNumQ() { return numQ; }
    public void setNumQ(int numQ) { this.numQ = numQ; }
}
