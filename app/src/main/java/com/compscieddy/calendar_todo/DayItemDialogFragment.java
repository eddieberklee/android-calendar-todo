package com.compscieddy.calendar_todo;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    mActivity = (AddDayItemInterface) getActivity();

    okButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mActivity != null) {
          mActivity.onAddMorningItemClick(mDayItemEditText.getText().toString()); // TODO: this has to be dynamic actually
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
