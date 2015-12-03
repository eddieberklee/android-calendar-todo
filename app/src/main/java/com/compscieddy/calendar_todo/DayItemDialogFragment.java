package com.compscieddy.calendar_todo;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by elee on 11/9/15.
 */
public class DayItemDialogFragment extends DialogFragment {

  private static final String TAG = DayItemDialogFragment.class.getSimpleName();

  private int mDaySectionId;

  public static final int DIALOG_DAY_SECTION_MORNING = 0;
  public static final int DIALOG_DAY_SECTION_AFTERNOON = 1;
  public static final int DIALOG_DAY_SECTION_EVENING = 2;

  public static final String DIALOG_DAY_SECTION_KEY = "dialog_day_section_arg";

  View.OnClickListener cancelButtonClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      getDialog().dismiss();
    }
  };
  private AddDayItemInterface mActivity;
  private EditText mDayItemEditText;
  private ImageView mOkButton;
  private ImageView mCancelButton;
  private View mRootView;
  private Button mDayButton1;
  private Button mDayButton2;
  private Button mDayButton3;
  private Button mDayButton4;
  private Button mDayButton5;
  private Button mDayButton6;
  private Button[] mDayButtons;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    Bundle args = getArguments();
    if (args == null) {
      Log.e(TAG, "DayItemDialogFragment needs to be passed in a bundle with the day section id (morning, afternoon, etc)");
    }
    mDaySectionId = args.getInt(DIALOG_DAY_SECTION_KEY);

    mRootView = inflater.inflate(R.layout.dialog_day_item, container, false);

    init();
    setListeners();

    Drawable cancelButtonBackground = mCancelButton.getBackground();
    Drawable cancelButtonSrc = mCancelButton.getDrawable();
    Util.applyColorFilter(cancelButtonBackground, getResources().getColor(R.color.flatui_red_1));
    Util.applyColorFilter(cancelButtonSrc, getResources().getColor(android.R.color.white));

    Drawable okButtonBackground = mOkButton.getBackground();
    Drawable okButtonSrc = mOkButton.getDrawable();
    Util.applyColorFilter(okButtonBackground, getResources().getColor(R.color.flatui_green_1));
    Util.applyColorFilter(okButtonSrc, getResources().getColor(android.R.color.white));

    populateCorrectDates();

    mCancelButton.setOnClickListener(cancelButtonClickListener);

    mActivity = (AddDayItemInterface) getActivity(); // TODO: enforce

    mOkButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addItem();
      }
    });

    return mRootView;
  }

  private void init() {
    mCancelButton = (ImageView) mRootView.findViewById(R.id.dialog_cancel_button);
    mOkButton = (ImageView) mRootView.findViewById(R.id.dialog_ok_button);
    mDayItemEditText = (EditText) mRootView.findViewById(R.id.dialog_title_edit_text);
    mDayButton1 = (Button) mRootView.findViewById(R.id.dialog_time_1_button);
    mDayButton2 = (Button) mRootView.findViewById(R.id.dialog_time_2_button);
    mDayButton3 = (Button) mRootView.findViewById(R.id.dialog_time_3_button);
    mDayButton4 = (Button) mRootView.findViewById(R.id.dialog_time_4_button);
    mDayButton5 = (Button) mRootView.findViewById(R.id.dialog_time_5_button);
    mDayButton6 = (Button) mRootView.findViewById(R.id.dialog_time_6_button);
    mDayButtons = new Button[] { mDayButton1, mDayButton2, mDayButton3, mDayButton4, mDayButton5, mDayButton6 };
  }

  private View.OnKeyListener mDayItemKeyListener = new View.OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_ENTER) {
        addItem();
        return true;
      }
      return false;
    }
  };

  private void addItem() {
    if (mActivity != null) {
      String dayItemTitle = mDayItemEditText.getText().toString();
      switch (mDaySectionId) {
        case DIALOG_DAY_SECTION_MORNING:
          mActivity.onAddMorningItemClick(dayItemTitle); // TODO: think about better solutions (better than blindly casting at least)
          break;
        case DIALOG_DAY_SECTION_AFTERNOON:
          mActivity.onAddAfternoonItemClick(dayItemTitle); // TODO: think about better solutions (better than blindly casting at least)
          break;
        case DIALOG_DAY_SECTION_EVENING:
          mActivity.onAddEveningItemClick(dayItemTitle); // TODO: think about better solutions (better than blindly casting at least)
          break;
      }
      getDialog().dismiss();
    }
  }

  private void setListeners() {
    mDayItemEditText.setOnKeyListener(mDayItemKeyListener);
  }

  private void populateCorrectDates() {
    int startTime = -1, endTime = -1;
    switch (mDaySectionId) { // TODO: cleanup the logic here once all 4 sections are in
      case DIALOG_DAY_SECTION_MORNING:
        startTime = 6;
        endTime = 12;
        break;
      case DIALOG_DAY_SECTION_AFTERNOON:
        startTime = 12;
        endTime = 18;
        break;
      case DIALOG_DAY_SECTION_EVENING:
        startTime = 18;
        endTime = 24;
        break;
    }
    for (int time = startTime; time < endTime; time++) {
      mDayButtons[time - startTime].setText(Util.militaryTimeToAMPM(time));
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
  }

}
