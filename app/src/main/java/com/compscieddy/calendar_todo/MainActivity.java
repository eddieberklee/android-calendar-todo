package com.compscieddy.calendar_todo;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
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

public class MainActivity extends AppCompatActivity implements AddDayItemInterface {

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
  private ScalpelFrameLayout mScalpelFrameLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mScalpelFrameLayout = (ScalpelFrameLayout) findViewById(R.id.root_scalpel_layout);
    // TODO: this option should be programmed into the onMenuItemSelected() thing
//    scalpelFrameLayout.setLayerInteractionEnabled(true);
//    scalpelFrameLayout.setDrawViews(true);

    mAddButtonMorning = (ImageView) findViewById(R.id.add_button_morning);
    mAddButtonAfternoon = (ImageView) findViewById(R.id.add_button_afternoon);
    mAddButtonEvening = (ImageView) findViewById(R.id.add_button_evening);

    mListViewMorning = (ListView) findViewById(R.id.day_section_morning_listview);
    mListViewAfternoon = (ListView) findViewById(R.id.day_section_afternoon_listview);
    mListViewEvening = (ListView) findViewById(R.id.day_section_evening_listview);

    mListViewMorning.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsMorning));
    mListViewAfternoon.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsAfternoon));
    mListViewEvening.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsEvening));

    // dummy seed
    addStringToListView(MainActivity.this, "hello there, first item", mDayItemsMorning, mListViewMorning);

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

  public static void addStringToListView(Context context, String item, ArrayList<String> arrayList, ListView listView) {
    arrayList.add(item);
    int listCount = arrayList.size();
    if (listCount < MAX_VISIBLE_LIST_COUNT) {
      listView.getLayoutParams().height = listCount * context.getResources().getDimensionPixelOffset(R.dimen.list_day_item_height);
    } else { // enforce a maximum height to listview
      // 0.5 margin is so that the list's last item gets cut off,
      listView.getLayoutParams().height = (int) Math.round((MAX_VISIBLE_LIST_COUNT - 0.5) * context.getResources().getDimensionPixelOffset(R.dimen.list_day_item_height));
    }
    ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listView.getAdapter();
    if (arrayAdapter != null) { // Will be null if added in onCreate() before adapter set
      arrayAdapter.notifyDataSetChanged();
    }
  }

  private static final String DAY_ITEM_DIALOG = "day_item_dialog";

  private View.OnClickListener mAddButtonMorningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      Fragment previousFragment = getFragmentManager().findFragmentByTag(DAY_ITEM_DIALOG);
      if (previousFragment != null) {
        fragmentTransaction.remove(previousFragment);
      }
      fragmentTransaction.addToBackStack(null);
      DialogFragment dayItemDialogFragment = new DayItemDialogFragment();
      dayItemDialogFragment.show(fragmentTransaction, DAY_ITEM_DIALOG);
//      addStringToListView("another item", mDayItemsMorning, mListViewMorning);
    }
  };
  private View.OnClickListener mAddButtonAfternoonListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      onAddAfternoonItemClick("dummy afternoon item");
    }
  };
  private View.OnClickListener mAddButtonEveningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      onAddEveningItemClick("dummy evening item");
    }
  };

  @Override
  public void onAddMorningItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsMorning, mListViewMorning);
  }
  @Override
  public void onAddAfternoonItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsAfternoon, mListViewAfternoon);
  }
  @Override
  public void onAddEveningItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsEvening, mListViewEvening);
  }

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
    } else if (id == R.id.action_scalpel) {
      boolean turnOn = !mScalpelFrameLayout.isLayerInteractionEnabled();
      mScalpelFrameLayout.setLayerInteractionEnabled(turnOn);
      mScalpelFrameLayout.setDrawViews(turnOn);
      mScalpelFrameLayout.invalidate();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
