package gr.teicm.ieee.quizandroidclient.ui.menu_items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.ui.Play;


/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class PlayItem implements IMenuItem {
    @Override
    public String getName(Context context) {
        return context.getString(R.string.play);
    }

    @Override
    public void performAction(Context context, GameEngine gameEngine) {
        context.startActivity(
                new Intent(
                        context,
                        Play.class
                ).putExtra("gameEngine", gameEngine)
        );
        ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
