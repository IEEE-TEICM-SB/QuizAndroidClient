package gr.teicm.ieee.quizandroidclient.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gr.teicm.ieee.quizandroidclient.R;

/**
 * Owner: JNK Software
 * Developer: Jordan Kostelidis
 * Date: 27/9/2017
 * License: Apache License 2.0
 */
class CustomAboutAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> aboutRecords;

    public CustomAboutAdapter(Activity context, List<String> aboutRecords) {
        super(context, R.layout.row_item, aboutRecords);

        this.context = context;
        this.aboutRecords = aboutRecords;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        ViewHolderItem viewHolder;

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.row_item, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.content = view.findViewById(R.id.itemContent);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.content.setText(aboutRecords.get(position));
        viewHolder.content.setBackgroundColor(getColor());

        return view;
    }

    private static class ViewHolderItem {
        TextView content;
    }

    private int getColor() {
        return ContextCompat.getColor(
                context,
                R.color.listBackground
        );
    }
}

