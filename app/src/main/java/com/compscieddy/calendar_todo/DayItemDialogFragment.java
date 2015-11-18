package com.compscieddy.calendar_todo;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.dialog_day_item, container, false);
    View cancelButton = rootView.findViewById(R.id.dialog_cancel_button);
    View okButton = rootView.findViewById(R.id.dialog_ok_button);

    cancelButton.setOnClickListener(cancelButtonClickListener);

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
