package gr.teicm.ieee.quizandroidclient.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.logic.InputStreamToString;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            backgroundWork();
        } catch (IOException e) {
            Log.i("QuizAndroidClient", "We can't save the object");
        }
    }

    private boolean hasLocalData(GameEngine gameEngine) {
        boolean has = false;
        try {
            if(gameEngine.load(this.openFileInput(getString(R.string.fileName)))) {
                has = true;
            }
        } catch (IOException | ClassNotFoundException e) {
            has = false;
        }
        return has;
    }

    private void backgroundWork() throws IOException {
        GameEngine gameEngine = new GameEngine();

        // Check if we have local data
        if(hasLocalData(gameEngine)) {
            // We have local data
            Log.i("QuizAndroidClient", "We have local data");
            gameEngine.update(
                    getString(R.string.service_url),
                    getString(R.string.service_url_version)
            );
        } else {
            // We don't have local data
            Log.i("QuizAndroidClient", "We don't have local data");
            try {
                gameEngine.init(
                        getString(R.string.service_url),
                        new InputStreamToString().getString(getResources().openRawResource(R.raw.document))
                );
            } catch (JSONException ex) {
                Log.i("QuizAndroidClient", "We can't load the xml data");
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle(getString(R.string.fatal_error))
                        .setMessage(getString(R.string.fatal_error_msg))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        }

        // Save the local data
        gameEngine.save(this.openFileOutput(
                getString(R.string.fileName),
                Context.MODE_PRIVATE
                )
        );

        openMenuActivity();

    }

    private void openMenuActivity() {
        Intent intent = new Intent(this, Menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
