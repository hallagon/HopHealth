package com.akim77.hopkinshealth.questionModels;

/**
 * Created by Anthony Kim on 12/7/2017.
 */

public class VerticalQuestion {
    private String question;
    private String optionOne, optionTwo, optionThree, optionFour, optionFive, optionSix;
    private int qNumber = 0;
    private int choice = 0;
    private int multipleChoiceType = 0; //0 is default uninitialized, 5 for five choice question, 6 for six choices, so on.

    public VerticalQuestion(String question, String optionOne, String optionTwo) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.multipleChoiceType = 2;
    }

    public VerticalQuestion(String question, String optionOne, String optionTwo, String optionThree, String optionFour, String optionFive) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.optionFive = optionFive;
        this.multipleChoiceType = 5;
    }

    public VerticalQuestion(String question, String optionOne, String optionTwo, String optionThree, String optionFour, String optionFive, String optionSix) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.optionFive = optionFive;
        this.optionSix = optionSix;
        this.multipleChoiceType = 6;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }

    public String getOptionFive() {
        return optionFive;
    }

    public void setOptionFive(String optionFive) {
        this.optionFive = optionFive;
    }

    public String getOptionSix() {
        return optionSix;
    }

    public void setOptionSix(String optionSix) {
        this.optionSix = optionSix;
    }

    public int getMultipleChoiceType() {
        return multipleChoiceType;
    }

    public void setMultipleChoiceType(int multipleChoiceType) {
        this.multipleChoiceType = multipleChoiceType;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getqNumber() {
        return qNumber;
    }

    public void setqNumber(int qNumber) {
        this.qNumber = qNumber;
    }
}
