package gr.teicm.ieee.quizandroidclient.logic;

import java.io.Serializable;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */
public class HistoryRecord implements Serializable {
    private final String lesson;
    private final String rank;
    private final String passed;

    public HistoryRecord(String lesson, String rank, String passed) {
        this.lesson = lesson;
        this.rank = rank;
        this.passed = passed;
    }

    public String getLesson() {
        return lesson;
    }

    public String getRank() {
        return rank;
    }

    public String isPassed() {
        return passed;
    }
}
