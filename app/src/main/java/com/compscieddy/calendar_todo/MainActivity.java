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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddDayItemInterface {

  private static final int MAX_VISIBLE_LIST_COUNT = 4;
  private ImageView mMorningAddButton;
  private ImageView mAfternoonAddButton;
  private ImageView mEveningAddButton;
  private ListView mMorningListView;
  private ListView mAfternoonListView;
  private ListView mEveningListView;
  private ViewGroup mMorningHeader;
  private ViewGroup mAfternoonHeader;
  private ViewGroup mEveningHeader;
  private TextView mMorningSummaryText;
  private TextView mAfternoonSummaryText;
  private TextView mEveningSummaryText;

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

    init();

    mScalpelFrameLayout = (ScalpelFrameLayout) findViewById(R.id.root_scalpel_layout);
    // TODO: this option should be programmed into the onMenuItemSelected() thing
//    scalpelFrameLayout.setLayerInteractionEnabled(true);
//    scalpelFrameLayout.setDrawViews(true);

    mMorningListView.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsMorning));
    mAfternoonListView.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsAfternoon));
    mEveningListView.setAdapter(new DayItemsAdapter(MainActivity.this, mDayItemsEvening));

    mMorningHeader.setOnClickListener(mHeaderClickListener);
    mAfternoonHeader.setOnClickListener(mHeaderClickListener);
    mEveningHeader.setOnClickListener(mHeaderClickListener);

        // dummy seed
    addStringToListView(MainActivity.this, "hello there, first item", mDayItemsMorning, mMorningListView);

    mMorningAddButton.setOnClickListener(mAddButtonMorningListener);
    mAfternoonAddButton.setOnClickListener(mAddButtonAfternoonListener);
    mEveningAddButton.setOnClickListener(mAddButtonEveningListener);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  private View.OnClickListener mHeaderClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      // v.getId()
      ListView listView;
      TextView summaryText;

      int viewId = v.getId();
      if (viewId == mMorningHeader.getId()) {
        listView = mMorningListView;
        summaryText = mMorningSummaryText;
      } else if (viewId == mAfternoonHeader.getId()) {
        listView = mAfternoonListView;
        summaryText = mAfternoonSummaryText;
      } else { // viewId == mEveningHeader
        listView = mEveningListView;
        summaryText = mEveningSummaryText;
      }

      int itemsCount = listView.getCount();

      boolean isCollapsing = listView.getVisibility() == View.VISIBLE;

      if (itemsCount != 0) {
        // TODO: move out into formatted @string
        // Html.fromHtml("<u>" +
        summaryText.setText(String.valueOf(itemsCount) + " MORE"); // todo: maybe even underline this here
        summaryText.setVisibility(!isCollapsing ? View.INVISIBLE : View.VISIBLE);
      }

      listView.setVisibility(isCollapsing ? View.GONE : View.VISIBLE);

    }
  };

  private void init() {
    mMorningAddButton = (ImageView) findViewById(R.id.add_button_morning);
    mAfternoonAddButton = (ImageView) findViewById(R.id.add_button_afternoon);
    mEveningAddButton = (ImageView) findViewById(R.id.add_button_evening);
    
    mMorningListView = (ListView) findViewById(R.id.day_section_morning_listview);
    mAfternoonListView = (ListView) findViewById(R.id.day_section_afternoon_listview);
    mEveningListView = (ListView) findViewById(R.id.day_section_evening_listview);
    
    mMorningHeader = (ViewGroup) findViewById(R.id.day_section_morning_header);
    mAfternoonHeader = (ViewGroup) findViewById(R.id.day_section_afternoon_header);
    mEveningHeader = (ViewGroup) findViewById(R.id.day_section_evening_header);
    
    mMorningSummaryText = (TextView) findViewById(R.id.morning_section_summary_text);
    mAfternoonSummaryText = (TextView) findViewById(R.id.afternoon_section_summary_text);
    mEveningSummaryText = (TextView) findViewById(R.id.evening_section_summary_text);
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

  private void showAddDayItemDialog(int daySectionId) {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment previousFragment = getFragmentManager().findFragmentByTag(DAY_ITEM_DIALOG);
    if (previousFragment != null) {
      fragmentTransaction.remove(previousFragment);
    }
    fragmentTransaction.addToBackStack(null);
    DialogFragment dayItemDialogFragment = new DayItemDialogFragment();
    Bundle dialogArgs = new Bundle();
    dialogArgs.putInt(DayItemDialogFragment.DIALOG_DAY_SECTION_KEY, daySectionId);
    dayItemDialogFragment.setArguments(dialogArgs);
    dayItemDialogFragment.show(fragmentTransaction, DAY_ITEM_DIALOG);
//      addStringToListView("another item", mDayItemsMorning, mMorningListView);
  }

  private View.OnClickListener mAddButtonMorningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      showAddDayItemDialog(DayItemDialogFragment.DIALOG_DAY_SECTION_MORNING);
    }
  };
  private View.OnClickListener mAddButtonAfternoonListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      showAddDayItemDialog(DayItemDialogFragment.DIALOG_DAY_SECTION_AFTERNOON);
    }
  };
  private View.OnClickListener mAddButtonEveningListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      showAddDayItemDialog(DayItemDialogFragment.DIALOG_DAY_SECTION_EVENING);
    }
  };

  @Override
  public void onAddMorningItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsMorning, mMorningListView);
  }
  @Override
  public void onAddAfternoonItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsAfternoon, mAfternoonListView);
  }
  @Override
  public void onAddEveningItemClick(String itemString) {
    addStringToListView(MainActivity.this, itemString, mDayItemsEvening, mEveningListView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    // what's a better lifecycle method to place this in?
    Util.applyColorFilter(mMorningAddButton.getDrawable(), getResources().getColor(R.color.white_transp_80));
    Util.applyColorFilter(mAfternoonAddButton.getDrawable(), getResources().getColor(R.color.white_transp_80));
    Util.applyColorFilter(mEveningAddButton.getDrawable(), getResources().getColor(R.color.white_transp_80));
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
