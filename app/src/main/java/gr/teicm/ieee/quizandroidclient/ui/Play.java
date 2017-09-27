package gr.teicm.ieee.quizandroidclient.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.logic.Semester;


@SuppressWarnings("FieldCanBeLocal")
public class Play extends AppCompatActivity {

    // Define UI objects
    private Spinner semesters;
    private Spinner lessons;
    // Define Logic objects
    private GameEngine gameEngine;
    private Semester selectedSemester;
    private ArrayList<String> semestersList;
    private ArrayList<String> lessonsList;
    private ArrayAdapter lessonsAdapter;
    private ArrayAdapter semestersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setTitle(
                    getString(R.string.select_semester_and_lesson)
            );
        }

        // Init UI objects
        semesters = (Spinner) findViewById(R.id.semesterSelector);
        lessons = (Spinner) findViewById(R.id.lessonSelector);
        Button playButton = (Button) findViewById(R.id.PlayButton);

        // Init Logic objects
        gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");

        semestersList = new ArrayList<>();
        lessonsList = new ArrayList<>();

        semestersAdapter = new ArrayAdapter<>(
                this.getBaseContext(),
                android.R.layout.simple_spinner_item,
                semestersList
        );
        lessonsAdapter = new ArrayAdapter<>(
                this.getBaseContext(),
                android.R.layout.simple_spinner_item,
                lessonsList
        );

        for (int i = 0; i < gameEngine.getSemesters().size(); i++) {
            semestersList.add(
                    gameEngine.getSemesters().get(i).getName()
            );
            semestersAdapter.notifyDataSetChanged();
        }

        semesters.setAdapter(semestersAdapter);
        lessons.setAdapter(lessonsAdapter);

        semesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSemester = gameEngine.getSemesters().get(i);
                lessonsList.clear();
                lessonsAdapter.clear();
                lessonsAdapter.notifyDataSetChanged();
                for (int j = 0; j < selectedSemester.getLessons().size(); j++) {
                    lessonsList.add(selectedSemester.getLessons().get(j).getName());
                    lessonsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedSemester.getLessons().get(lessons.getSelectedItemPosition()).hasQuestions()) {
                    Intent intent = new Intent(
                            Play.this,
                            Game.class
                    );
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(
                            "lesson",
                            selectedSemester.getLessons().get(lessons.getSelectedItemPosition())
                    );
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    new AlertDialog.Builder(view.getContext())
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setTitle(view.getContext().getString(R.string.no_questions))
                            .setMessage(view.getContext().getString(R.string.no_questions_message))
                            .setPositiveButton(view.getContext().getString(R.string.ok), null)
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
    }

}
