package gr.teicm.ieee.quizandroidclient.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.teicm.ieee.quizandroidclient.BuildConfig;
import gr.teicm.ieee.quizandroidclient.R;


public class About extends AppCompatActivity {

    private String dataVersion;
    private List<String> aboutItems;
    private ArrayAdapter<String> aboutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        dataVersion = getIntent().getStringExtra("dataVersion");

        ListView aboutList = (ListView) findViewById(R.id.aboutList);

        aboutItems = new ArrayList<>();
        aboutAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                aboutItems
        );

        aboutList.setAdapter(aboutAdapter);


        aboutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
                boolean found;
                // Here we will save our url
                String url;

                // Ask if this is a item with url from appItems
                url = itemClickOnAppItem(aboutItems.get(i));

                if (!url.equals("")) {
                    found = true;
                } else {
                    // Ask if this is a item with url from devItems
                    url = itemClickOnDevItem(aboutItems.get(i));
                    found = !url.equals("");
                }

                if (found) {
                    if (isCorrectLink(url)) {
                        try {
                            openIntent(url);
                        } catch (Exception e) {
                            Log.w(getString(R.string.app_name_content), e.getMessage());
                        }
                    } else {
                        Log.w(getString(R.string.app_name_content), "This is not a valid url");
                    }
                }

            }
        });

        addItems();
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
    }

    @SuppressWarnings("RedundantThrows")
    private void openIntent(String url) throws Exception {
        startActivity(
                new Intent(
                        Intent.ACTION_VIEW
                ).setData(
                        Uri.parse(url)
                )
        );
    }

    private boolean isCorrectLink(String url) {
        return url.contains("http") || url.contains("mailto");
    }

    private void addItem(String Item) {
        aboutItems.add(Item);
        aboutAdapter.notifyDataSetChanged();
    }

    private void addItems() {
        addAppItems();
        addDevItems();
    }

    private void addAppItems() {
        addItem(getString(R.string.app_items_title));
        addItem(getString(R.string.app_name) + "\n" + getString(R.string.app_name_content));
        addItem(getString(R.string.app_version) + "\n" + BuildConfig.VERSION_NAME);
        addItem(getString(R.string.data_version) + "\n" + dataVersion);
        addItem(getString(R.string.app_license) + "\n" + getString(R.string.app_license_content));
        addItem(getString(R.string.app_source) + "\n" + getString(R.string.app_source_content));

    }

    private void addDevItems() {
        addItem(getString(R.string.dev_items_title));
        addItem(getString(R.string.dev_brand) + "\n" + getString(R.string.dev_brand_content));
        addItem(getString(R.string.dev_facebook) + "\n" + getString(R.string.dev_facebook_content));
        addItem(getString(R.string.dev_twitter) + "\n" + getString(R.string.dev_twitter_content));
        addItem(getString(R.string.dev_youtube) + "\n" + getString(R.string.dev_youtube_content));
        addItem(getString(R.string.dev_site) + "\n" + getString(R.string.dev_site_content));
        addItem(getString(R.string.dev_email) + "\n" + getString(R.string.dev_email_content));
    }

    private String itemClickOnAppItem(String content) {
        if (content.contains(getString(R.string.app_license_content))) {
            return getString(R.string.app_license_url_content);
        } else if (content.contains(getString(R.string.app_source_content))) {
            return getString(R.string.app_source_content);
        } else {
            return "";
        }
    }

    private String itemClickOnDevItem(String content) {
        if (content.contains(getString(R.string.dev_facebook_content))) {
            return getString(R.string.dev_facebook_content);
        } else if (content.contains(getString(R.string.dev_twitter_content))) {
            return getString(R.string.dev_twitter_content);
        } else if (content.contains(getString(R.string.dev_youtube_content))) {
            return getString(R.string.dev_youtube_content);
        } else if (content.contains(getString(R.string.dev_site_content))) {
            return getString(R.string.dev_site_content);
        } else if (content.contains(getString(R.string.dev_email_content))) {
            return "mailto://" + getString(R.string.dev_email_content);
        } else {
            return "";
        }
    }
}
