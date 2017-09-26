package gr.teicm.ieee.quizandroidclient.logic;

import java.io.Serializable;
import java.util.List;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class Question implements Serializable {
    private final String question;
    private final List<String> answers;
    private final String correctAnswer;

    Question(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
