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

public class Semester implements Serializable {

    private final String name;
    private final List<Lesson> lessons;

    Semester(String name) {
        this.name = name;
        lessons = new ArrayList<>();
    }

    void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String getName() {
        return name;
    }
}
