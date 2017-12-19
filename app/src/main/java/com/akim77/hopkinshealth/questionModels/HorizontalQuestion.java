package com.akim77.hopkinshealth.questionModels;

/**
 * Created by Anthony Kim on 12/17/2017.
 */

public class HorizontalQuestion {
    private String questionContext;
    private String question;
    private String optionOne, optionTwo, optionThree, optionFour, optionFive, optionSix;
    private int qNumber = 0;
    private int choice = 0;
    private int multipleChoiceType = 0; //0 is default uninitialized, 5 for five choice question, 6 for six choices, so on.
    private boolean isContextSettingQuestion = true;

    public HorizontalQuestion(String questionContext, String question, String optionOne, String optionTwo, boolean isContextSettingQuestion) {
        this.questionContext = questionContext;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.isContextSettingQuestion = isContextSettingQuestion;
        multipleChoiceType = 2;
    }

    public HorizontalQuestion(String questionContext, String question, String optionOne, String optionTwo, String optionThree, boolean isContextSettingQuestion) {
        this.questionContext = questionContext;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.isContextSettingQuestion = isContextSettingQuestion;
        multipleChoiceType = 3;
    }

    public HorizontalQuestion(String questionContext, String question, String optionOne, String optionTwo, String optionThree, String optionFour, boolean isContextSettingQuestion) {
        this.questionContext = questionContext;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.isContextSettingQuestion = isContextSettingQuestion;
        multipleChoiceType = 4;
    }

    public HorizontalQuestion(String questionContext, String question, String optionOne, String optionTwo, String optionThree, String optionFour, String optionFive, boolean isContextSettingQuestion) {
        this.questionContext = questionContext;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.optionFive = optionFive;
        this.isContextSettingQuestion = isContextSettingQuestion;
        multipleChoiceType = 5;
    }

    public HorizontalQuestion(String questionContext, String question, String optionOne, String optionTwo, String optionThree, String optionFour, String optionFive, String optionSix, boolean isContextSettingQuestion) {
        this.questionContext = questionContext;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.optionFive = optionFive;
        this.optionSix = optionSix;
        this.isContextSettingQuestion = isContextSettingQuestion;
        multipleChoiceType = 6;
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

    public boolean isContextSettingQuestion() {
        return isContextSettingQuestion;
    }

    public void setContextSettingQuestion(boolean contextSettingQuestion) {
        isContextSettingQuestion = contextSettingQuestion;
    }

    public String getQuestionContext() {
        return questionContext;
    }

    public void setQuestionContext(String questionContext) {
        this.questionContext = questionContext;
    }
}
