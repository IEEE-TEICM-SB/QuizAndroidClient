package gr.teicm.ieee.quizandroidclient.ui.menu_items;

import android.content.Context;

import gr.teicm.ieee.quizandroidclient.logic.GameEngine;


/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public interface IMenuItem {
    String getName(Context context);

    void performAction(Context context, GameEngine gameEngine);
}
