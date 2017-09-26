package gr.teicm.ieee.quizandroidclient.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class Lesson implements Serializable {

    private final String name;
    private final List<Question> questions;

    Lesson(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion() {
        Question questionToReturn;

        if (questions.size() == 0) {
            questionToReturn = null;
        } else if (questions.size() == 1) {
            questionToReturn = questions.get(0);
            questions.remove(questionToReturn);
        } else {
            questionToReturn = questions.get(
                    (int) (Math.random() * questions.size())
            );
            questions.remove(questionToReturn);
        }

        return questionToReturn;
    }

    public boolean hasQuestions() {
        return !questions.isEmpty();
    }
}
