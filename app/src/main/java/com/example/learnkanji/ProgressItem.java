package com.example.learnkanji;

public class ProgressItem {
    private String mMistakesTotal;
    private String mMistakesPercentage;
    private String mDateQuiz;

    public ProgressItem(String mistakesTotal, String mistakesPercentage, String dateQuiz) {
        mMistakesTotal = mistakesTotal;
        mMistakesPercentage = mistakesPercentage;
        mDateQuiz = dateQuiz;

    }

    public String getMistakesTotal() { return mMistakesTotal; }

    public String getMistakesPercentage() {
        return mMistakesPercentage;
    }

    public String getDateQuiz() {
        return mDateQuiz;
    }

}
