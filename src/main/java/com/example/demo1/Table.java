/*
Kevin Wang
3 May 2023
AP Computer Science A
2nd Period
Master Project
Question Table POCO Class
 */

package com.example.demo1;

public class Table {

    //Fields of the class
    private int id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String category;
    private int difficulty;
    private int power;

    //Default Constructor
    public Table() {
        this.id = 1;
        this.question = "";
        this.answer1 = "";
        this.answer2 = "";
        this.answer3 = "";
        this.category = "";
        this.difficulty = 1;
        this.power = 1;
    }

    //Full Constructor
    public Table(int id, String question, String answer1, String answer2, String answer3, String category, int difficulty, int power) {
        this.id = id;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.category = category;
        this.difficulty = difficulty;
        this.power = power;
    }

    //Getter for question ID
    public int getId() {
        return id;
    }

    //Setter for question ID
    public void setId(int id) {
        this.id = id;
    }

    //Getter for question
    public String getQuestion() {
        return question;
    }

    //Setter for question ID
    public void setQuestion(String question) {
        this.question = question;
    }

    //Getter for answer 1
    public String getAnswer1() {
        return answer1;
    }

    //Setter for answer 1
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    //Getter for answer 2
    public String getAnswer2() {
        return answer2;
    }

    //Setter for answer 2
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    //Getter for answer 3
    public String getAnswer3() {
        return answer3;
    }

    //Setter for answer 1
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    //Getter for question category
    public String getCategory() {
        return category;
    }

    //Setter for question category
    public void setCategory(String category) {
        this.category = category;
    }

    //Getter for question difficulty
    public int getDifficulty() {
        return difficulty;
    }

    //Setter for question difficulty
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //Getter for word length at which power is applicable
    public int getPower() {
        return power;
    }

    //Setter for word length at which power is applicable
    public void setPower(int power) {
        this.power = power;
    }

}
