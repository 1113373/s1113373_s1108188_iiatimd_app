package com.example.learnkanji;

public class ProgressItem {
    private int mMistakesTotal;
    private double mMistakesPercentage;
    private int mDateQuiz;

    public ProgressItem(int mistakesTotal, double mistakesPercentage, int dateQuiz) {
        mMistakesTotal = mistakesTotal;
        mMistakesPercentage = mistakesPercentage;
        mDateQuiz = dateQuiz;

    }

    public int getMistakesTotal() { return mMistakesTotal; }

    public double getMistakesPercentage() {
        return mMistakesPercentage;
    }

    public int getDateQuiz() {
        return mDateQuiz;
    }

}
