package com.catostudioaruna.alphabet.db;

public class TebakHuruf {
    private Integer choice1;
    private Integer choice2;
    private Integer answer;
    private Integer question;

    public TebakHuruf(Integer choice1, Integer choice2, Integer answer, Integer question) {
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.answer = answer;
        this.question = question;
    }

    public Integer getChoice1() {
        return choice1;
    }

    public void setChoice1(Integer choice1) {
        this.choice1 = choice1;
    }

    public Integer getChoice2() {
        return choice2;
    }

    public void setChoice2(Integer choice2) {
        this.choice2 = choice2;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }
}
