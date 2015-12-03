package com.compscieddy.calendar_todo;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    Bundle args = getArguments();
    if (args == null) {
      Log.e(TAG, "DayItemDialogFragment needs to be passed in a bundle with the day section id (morning, afternoon, etc)");
    }
    mDaySectionId = args.getInt(DIALOG_DAY_SECTION_KEY);

    View rootView = inflater.inflate(R.layout.dialog_day_item, container, false);
    ImageView cancelButton = (ImageView) rootView.findViewById(R.id.dialog_cancel_button);
    ImageView okButton = (ImageView) rootView.findViewById(R.id.dialog_ok_button);
    mDayItemEditText = (EditText) rootView.findViewById(R.id.dialog_title_edit_text);

    Drawable cancelButtonBackground = cancelButton.getBackground();
    Drawable cancelButtonSrc = cancelButton.getDrawable();
    Util.applyColorFilter(cancelButtonBackground, getResources().getColor(R.color.flatui_red_1));
    Util.applyColorFilter(cancelButtonSrc, getResources().getColor(android.R.color.white));

    Drawable okButtonBackground = okButton.getBackground();
    Drawable okButtonSrc = okButton.getDrawable();
    Util.applyColorFilter(okButtonBackground, getResources().getColor(R.color.flatui_green_1));
    Util.applyColorFilter(okButtonSrc, getResources().getColor(android.R.color.white));

    cancelButton.setOnClickListener(cancelButtonClickListener);

    mActivity = (AddDayItemInterface) getActivity(); // TODO: enforce

    okButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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
    });

    return rootView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
  }

}
