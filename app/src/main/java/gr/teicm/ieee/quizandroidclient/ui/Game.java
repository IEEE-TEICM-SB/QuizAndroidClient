package gr.teicm.ieee.quizandroidclient.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.Lesson;
import gr.teicm.ieee.quizandroidclient.logic.Question;


@SuppressWarnings("FieldCanBeLocal")
public class Game extends AppCompatActivity {

    // Define UI objects
    private TextView questionView;
    private ListView answersView;
    // Define Logic objects
    private ArrayList<String> arrayList;
    private Lesson lesson;
    private Question question;
    private int points;
    private int questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Init UI objects
        questionView = (TextView) findViewById(R.id.questionView);
        answersView = (ListView) findViewById(R.id.answersView);

        arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                arrayList
        );

        answersView.setAdapter(arrayAdapter);

        points = getIntent().getIntExtra("points", 0);
        questions = getIntent().getIntExtra("questions", 0);
        lesson = (Lesson) getIntent().getSerializableExtra("lesson");
        question = lesson.getQuestion();

        if (question == null) {
            openScore();
        } else {
            questions++;
            questionView.setText(question.getQuestion());

            for (int i = 0; i < question.getAnswers().size(); i++) {
                arrayList.add(question.getAnswers().get(i));
                arrayAdapter.notifyDataSetChanged();
            }

            Collections.shuffle(arrayList);
            arrayAdapter.notifyDataSetChanged();

            answersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    answersView.setClickable(false);

                    String selectedAnswer = (String) answersView.getItemAtPosition(i);

                    if (question.isCorrect(selectedAnswer)) {
                        view.setBackgroundColor(getColor(true));
                        points++;
                    } else {
                        view.setBackgroundColor(getColor(false));
                        String correctAnswer = question.getCorrectAnswer();
                        for (int x = 0; x < arrayList.size(); x++) {
                            if (arrayList.get(x).contains(correctAnswer)) {
                                getViewByPosition(
                                        x,
                                        answersView
                                ).setBackgroundColor(
                                        getColor(true)
                                );
                                break;
                            }
                        }
                    }

                    goToNextQuestion();
                }
            });
        }

    }

    private void openScore() {
        Intent intent = new Intent(Game.this, Score.class);
        intent.putExtra("points", points);
        intent.putExtra("questions", questions);
        intent.putExtra("lesson",lesson.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    private void goToNextQuestion() {
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Game.this, Game.class);

                intent.putExtra("lesson", lesson);
                intent.putExtra("points", points);
                intent.putExtra("questions", questions);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
       // ToDo : Notify user if he wants to close the game, he will count this game all lose
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(getString(R.string.give_up))
                .setMessage(getString(R.string.give_up_message))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openScore();
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }

    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private int getColor(boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            return ContextCompat.getColor(
                    getBaseContext(),
                    R.color.green
            );
        } else {
            return ContextCompat.getColor(
                    getBaseContext(),
                    android.R.color.holo_red_light
            );
        }
    }
}
