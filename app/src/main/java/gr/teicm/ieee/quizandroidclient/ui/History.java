package gr.teicm.ieee.quizandroidclient.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.logic.HistoryRecord;

public class History extends AppCompatActivity {

    private List<String> historyItems;
    private CustomAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        GameEngine gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");

        final ListView historyList = (ListView) findViewById(R.id.historyView);

        historyItems = new ArrayList<>();
        historyAdapter = new CustomAdapter(this, historyItems);
        historyList.setAdapter(historyAdapter);

        final List<HistoryRecord> historyRecords = gameEngine.getHistory();
        HistoryRecord tmp;
        for (int i = historyRecords.size() - 1; i >= 0; i--) {
            tmp = historyRecords.get(i);
            addItem(
                    getString(R.string.you_played_the_lesson)
                            + " "
                            + tmp.getLesson()
                            + " "
                            + getString(R.string.and_you)
                            + " "
                            + tmp.isPassed()
                            + " "
                            + getString(R.string.with_rank)
                            + " "
                            + tmp.getRank()
                            + " "
                            + getString(R.string.Exclamation)
            );
        }

        // ToDo : Set color based on result

    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
    }

    private void addItem(String Item) {
        historyItems.add(Item);
        historyAdapter.notifyDataSetChanged();
    }
}
