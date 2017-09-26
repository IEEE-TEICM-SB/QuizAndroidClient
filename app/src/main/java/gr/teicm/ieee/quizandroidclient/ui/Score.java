package gr.teicm.ieee.quizandroidclient.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.logic.HistoryRecord;


public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Init UI objects
        TextView lessonView = (TextView) findViewById(R.id.lessonView);
        RatingBar resultBar = (RatingBar) findViewById(R.id.scoreBar);

        LayerDrawable stars = (LayerDrawable) resultBar.getProgressDrawable();

        TextView resultView = (TextView) findViewById(R.id.resultView);
        Button okButton = (Button) findViewById(R.id.okButton);



        // Init Logic objects
        String lesson = getIntent().getStringExtra("lesson");
        int points = getIntent().getIntExtra("points", 0);
        int questions = getIntent().getIntExtra("questions", 0);

        String pointsRatio = String.valueOf(points) + "/" + String.valueOf(questions);


        float a = points * 5 / questions;
        int b = 5;

        // Set UI objects
        lessonView.append(" " + lesson);
        resultBar.setNumStars(b);
        resultBar.setRating(a);
        stars.getDrawable(2).setColorFilter(points > questions / 2 ? Color.GREEN : Color.RED, PorterDuff.Mode.SRC_ATOP);
        resultView.setText(points > questions / 2 ? getString(R.string.passed) : getString(R.string.failed));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, Menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();
                overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
            }
        });

        // Set logic objects
        GameEngine gameEngine = new GameEngine();
        try {
            if(gameEngine.load(this.openFileInput(getString(R.string.fileName)))) {
                gameEngine.addHistoryRecord(
                        new HistoryRecord(
                                lesson,
                                pointsRatio,
                                resultView.getText().toString()
                        )
                );
                gameEngine.save(this.openFileOutput(getString(R.string.fileName), Context.MODE_PRIVATE));
            } else {
                errorMsg();
            }
        } catch (IOException | ClassNotFoundException e) {
            errorMsg();
        }

    }

    private void errorMsg() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(getString(R.string.cant_add_history))
                .setMessage(getString(R.string.cant_add_history_msg))
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }
}
