package gr.teicm.ieee.quizandroidclient.logic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class GameEngine implements Serializable {
    private String dataVersion;
    private List<Semester> semesters;
    private List<HistoryRecord> history;

    public GameEngine() {
        semesters = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void init(String serviceURL, String offlineContent) throws JSONException {
        if(new InternetChecker().isInternetWorking(serviceURL)) {
            Log.i("QuizAndroidClient", "We have internet and we try download questions");
           try {
               String serversJSON = new HTTPHandler().getContent(serviceURL);
               Log.i("QuizAndroidClient", "We have download the questions and we try load them");
               loadFromJSON(serversJSON);
               Log.i("QuizAndroidClient", "We have load the questions");
           } catch (Exception ex) {
               Log.i("QuizAndroidClient", "We can't download questions. We try load them from the source's xml");
               loadFromJSON(offlineContent);
               Log.i("QuizAndroidClient", "We have load the questions");
           }
        } else {
            Log.i("QuizAndroidClient", "We don't have internet. We try load questions from the source's xml");
            loadFromJSON(offlineContent);
            Log.i("QuizAndroidClient", "We have load the questions");
        }
    }

    public void update(String serviceURL, String serviceVersionURL) {
        try {
            if(new InternetChecker().isInternetWorking(serviceURL)) {
                Log.i("QuizAndroidClient", "We have internet and we try update questions");
                String serversVersion = new HTTPHandler().getContent(serviceVersionURL);
                JSONObject jsonObject = new JSONObject(serversVersion);

                if(!getDataVersion().equals(jsonObject.getString("ver"))) {
                    Log.i("QuizAndroidClient", "Local questions is old, we try download the new questions");
                    String serversJSON = new HTTPHandler().getContent(serviceURL);
                    Log.i("QuizAndroidClient", "We have download the questions and we try load them");
                    loadFromJSON(serversJSON);
                    Log.i("QuizAndroidClient", "We have load the questions");
                } else {
                    Log.i("QuizAndroidClient", "Local questions is up-to-date");
                }

            } else {
                Log.i("QuizAndroidClient", "We don't have internet.");
            }
        } catch (Exception ex) {
            Log.d("QuizAndroidClient", ex.getMessage());
        }
    }

    private void loadFromJSON(String jsonData) throws JSONException {

        // Clear the semesters
        semesters = new ArrayList<>();

        // Load the full JSON
        JSONObject jsonObject = new JSONObject(jsonData);

        dataVersion = jsonObject.getString("ver");

        // Load only semesters [array]
        JSONArray jsonSemesters = new JSONArray(jsonObject.get("semesters").toString());

        JSONObject tmpJsonSemester;
        for (int i = 0; i < jsonSemesters.length(); i++) {
            tmpJsonSemester = jsonSemesters.getJSONObject(i);
            Semester tmpSemester = new Semester(tmpJsonSemester.get("title").toString());

            if (tmpJsonSemester.has("lessons")) {
                // Load only lessons [array]
                JSONArray jsonLessons = new JSONArray(tmpJsonSemester.get("lessons").toString());

                JSONObject tmpJsonLesson;
                for (int j = 0; j < jsonLessons.length(); j++) {
                    tmpJsonLesson = jsonLessons.getJSONObject(j);
                    Lesson tmpLesson = new Lesson(tmpJsonLesson.get("title").toString());

                    if (tmpJsonLesson.has("questions")) {
                        // Load only questions [array]
                        JSONArray jsonQuestions = new JSONArray(tmpJsonLesson.get("questions").toString());

                        JSONObject tmpJsonQuestion;
                        for (int x = 0; x < jsonQuestions.length(); x++) {
                            tmpJsonQuestion = jsonQuestions.getJSONObject(x);
                            Question tmpQuestion = new Question(
                                    tmpJsonQuestion.get("question").toString(),
                                    getArrayListFromJSONAnswers(tmpJsonQuestion.getJSONArray("answers")),
                                    tmpJsonQuestion.get("correct").toString()
                            );
                            tmpLesson.addQuestion(tmpQuestion);
                        }
                    }

                    tmpSemester.addLesson(tmpLesson);
                }
                this.addSemester(tmpSemester);
            }

        }

    }

    public String getDataVersion() {
        return dataVersion;
    }

    private List<String> getArrayListFromJSONAnswers(JSONArray answers) throws JSONException {
        ArrayList<String> tmpList = new ArrayList<>();

        for (int i = 0; i < answers.length(); i++) {
            JSONObject tmpJsonObject = answers.getJSONObject(i);
            tmpList.add(tmpJsonObject.get("answer").toString());
        }

        return tmpList;
    }

    private void addSemester(Semester semester) {
        semesters.add(semester);
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void addHistoryRecord(HistoryRecord historyRecord) {
        history.add(historyRecord);
    }

    public List<HistoryRecord> getHistory() {
        return history;
    }

    public void save(FileOutputStream fileOutputStream) throws IOException {
        new FileHandler().saveFile(this, fileOutputStream);
    }

    public boolean load(FileInputStream fileInputStream) throws IOException, ClassNotFoundException {
        GameEngine gameEngine = (GameEngine) new FileHandler().readFile(fileInputStream);

        if(gameEngine == null) {
            return false;
        } else {
            dataVersion = gameEngine.dataVersion;
            semesters = gameEngine.semesters;
            history = gameEngine.history;
            return true;
        }

    }
}
