package gr.teicm.ieee.quizandroidclient.ui.menu_items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.ui.About;


/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class AboutItem implements IMenuItem {
    @Override
    public String getName(Context context) {
        return context.getString(R.string.about);
    }

    @Override
    public void performAction(Context context, GameEngine gameEngine) {
        context.startActivity(
                new Intent(
                        context,
                        About.class
                ).putExtra("dataVersion", gameEngine.getDataVersion())
        );
        ((Activity)context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
