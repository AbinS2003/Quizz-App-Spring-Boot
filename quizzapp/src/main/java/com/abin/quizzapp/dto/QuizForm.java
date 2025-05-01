package com.abin.quizzapp.dto;

public class QuizForm {
    private String category;
    private String title;
    private int numQ;
    private String level;

    // Getters and setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getNumQ() { return numQ; }
    public void setNumQ(int numQ) { this.numQ = numQ; }

}
