package com.compscieddy.calendar_todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by elee on 11/9/15.
 */
public class DayItemsAdapter extends ArrayAdapter<String> {

  private final Context context;
  private final ArrayList<String> values;

  public DayItemsAdapter(Context context, ArrayList<String> values) {
    super(context, 0, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rowView = layoutInflater.inflate(R.layout.day_item_layout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.day_item_title);
    textView.setText(values.get(position));

    return rowView;
  }
}
