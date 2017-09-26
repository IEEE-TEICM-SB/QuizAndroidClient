package gr.teicm.ieee.quizandroidclient.ui.menu_items;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */

public class ExitItem implements IMenuItem {
    @Override
    public String getName(Context context) {
        return context.getString(R.string.exit);
    }

    @Override
    public void performAction(final Context context, GameEngine gameEngine) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(context.getString(R.string.closing))
                .setMessage(context.getString(R.string.are_you_sure_close))
                .setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton(context.getString(R.string.no), null)
                .show();
    }
}
