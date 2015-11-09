package com.compscieddy.calendar_todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static final int MAX_VISIBLE_LIST_COUNT = 4;
  private ImageView mAddButtonMorning;
  private ImageView mAddButtonAfternoon;
  private ImageView mAddButtonEvening;
  private ListView mListViewMorning;
  private ListView mListViewAfternoon;
  private ListView mListViewEvening;
  private ArrayList<String> mDayItemsMorning = new ArrayList<>();
  private ArrayList<String> mDayItemsAfternoon = new ArrayList<>();
  private ArrayList<String> mDayItemsEvening = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ScalpelFrameLayout scalpelFrameLayout = (ScalpelFrameLayout) findViewById(R.id.root_scalpel_layout);
    // TODO: this option should be programmed into the onMenuItemSelected() thing
//    scalpelFrameLayout.setLayerInteractionEnabled(true);
//    scalpelFrameLayout.setDrawViews(true);

    mAddButtonMorning = (ImageView) findViewById(R.id.add_button_morning);
    mAddButtonAfternoon = (ImageView) findViewById(R.id.add_button_afternoon);
    mAddButtonEvening = (ImageView) findViewById(R.id.add_button_evening);

    mListViewMorning = (ListView) findViewById(R.id.day_section_morning_listview);
    mListViewAfternoon = (ListView) findViewById(R.id.day_section_afternoon_listview);
    mListViewEvening = (ListView) findViewById(R.id.day_section_evening_listview);
    addStringToListView("hello there, first item", mDayItemsMorning, mListViewMorning);
    mListViewMorning.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsMorning));
    mListViewAfternoon.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsAfternoon));
    mListViewEvening.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsEvening));

    mAddButtonMorning.setOnClickListener(mAddButtonMorningListener);
    mAddButtonAfternoon.setOnClickListener(mAddButtonAfternoonListener);
    mAddButtonEvening.setOnClickListener(mAddButtonEveningListener);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  private void addStringToListView(String item, ArrayList<String> arrayList, ListView listView) {
    arrayList.add(item);
    int listCount = arrayList.size();
    if (listCount < MAX_VISIBLE_LIST_COUNT) {
      listView.getLayoutParams().height = listCount * getResources().getDimensionPixelOffset(R.dimen.list_day_item_height);
    } else { // enforce a maximum height to listview
      // 0.5 margin is so that the list's last item gets cut off,
      listView.getLayoutParams().height = (int) Math.round((MAX_VISIBLE_LIST_COUNT - 0.5) * getResources().getDimensionPixelOffset(R.dimen.list_day_item_height));
    }
    ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) mListViewMorning.getAdapter();
    if (arrayAdapter != null) { // Will be null if added in onCreate() before adapter set
      arrayAdapter.notifyDataSetChanged();
    }
  }

  private View.OnClickListener mAddButtonMorningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      addStringToListView("another item", mDayItemsMorning, mListViewMorning);
    }
  };
  private View.OnClickListener mAddButtonAfternoonListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      addStringToListView("another item", mDayItemsAfternoon, mListViewAfternoon);
    }
  };
  private View.OnClickListener mAddButtonEveningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      addStringToListView("another item", mDayItemsEvening, mListViewEvening);
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    // what's a better lifecycle method to place this in?
    Util.applyColorFilter(mAddButtonMorning.getDrawable(), getResources().getColor(R.color.white_transp_80));
    Util.applyColorFilter(mAddButtonAfternoon.getDrawable(), getResources().getColor(R.color.white_transp_80));
    Util.applyColorFilter(mAddButtonEvening.getDrawable(), getResources().getColor(R.color.white_transp_80));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
