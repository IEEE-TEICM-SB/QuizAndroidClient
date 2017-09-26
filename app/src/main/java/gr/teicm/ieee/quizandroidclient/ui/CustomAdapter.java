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
 * Date: 26/9/2017
 * License: Apache License 2.0
 */
class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> strings;

    public CustomAdapter(Activity context, List<String> strings) {
        super(context, R.layout.row_item, strings);

        this.context = context;
        this.strings = strings;
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

        viewHolder.content.setText(strings.get(position));
        viewHolder.content.setBackgroundColor(getColor(strings.get(position).contains(context.getString(R.string.passed))));

        return view;
    }

    private static class ViewHolderItem {
        TextView content;
    }

    private int getColor(boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            return ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_light
            );
        } else {
            return ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_light
            );
        }
    }

}