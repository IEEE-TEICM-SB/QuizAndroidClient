package gr.teicm.ieee.quizandroidclient.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gr.teicm.ieee.quizandroidclient.BuildConfig;
import gr.teicm.ieee.quizandroidclient.R;
import gr.teicm.ieee.quizandroidclient.logic.GameEngine;
import gr.teicm.ieee.quizandroidclient.ui.menu_items.AboutItem;
import gr.teicm.ieee.quizandroidclient.ui.menu_items.ExitItem;
import gr.teicm.ieee.quizandroidclient.ui.menu_items.HistoryItem;
import gr.teicm.ieee.quizandroidclient.ui.menu_items.IMenuItem;
import gr.teicm.ieee.quizandroidclient.ui.menu_items.PlayItem;


@SuppressWarnings("FieldCanBeLocal")
public class Menu extends AppCompatActivity {

    // Define UI objects
    private ImageView gameLogo;
    private ListView menuList;
    private TextView miniAbout;

    // Define Logic objects
    private GameEngine gameEngine;
    private List<IMenuItem> supportedIMenuItems;
    private List<String> menuItems;
    private ArrayAdapter<String> menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Init UI objects
        gameLogo = (ImageView) findViewById(R.id.gameLogo);
        menuList = (ListView) findViewById(R.id.menuList);
        miniAbout = (TextView) findViewById(R.id.miniAboutField);

        // Init Logic objects
        gameEngine = new GameEngine();
        try {
            gameEngine.load(this.openFileInput(getString(R.string.fileName)));
        } catch (IOException | ClassNotFoundException e) {
            showError();
        }
        supportedIMenuItems = new ArrayList<>();
        menuItems = new ArrayList<>();
        menuAdapter = new ArrayAdapter<>(
                this.getApplicationContext(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        // Set UI handlers
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuListItemClick(i);
            }
        });

        // Set UI objects
        gameLogo.setImageResource(R.drawable.logo);
        menuList.setAdapter(menuAdapter);
        miniAbout.setText(getString(R.string.build) + " " + BuildConfig.VERSION_NAME);

        // Load supported menu items
        supportedIMenuItems.add(new PlayItem());
        supportedIMenuItems.add(new HistoryItem());
        supportedIMenuItems.add(new AboutItem());
        supportedIMenuItems.add(new ExitItem());

        // Add menu items
        for (int i = 0; i < supportedIMenuItems.size(); i++) {
            addItemOnMenuList(supportedIMenuItems.get(i).getName(this));
        }

    }

    private void showError() {
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

    @Override
    public void onBackPressed() {
        new ExitItem().performAction(this, null);
    }

    private void addItemOnMenuList(String item) {
        menuItems.add(item);
        menuAdapter.notifyDataSetChanged();
    }

    private void menuListItemClick(int i) {
        if (i < supportedIMenuItems.size()) {
            supportedIMenuItems.get(i).performAction(this, gameEngine);
        }
    }
}
