package com.compscieddy.calendar_todo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

/**
 * Created by elee on 11/8/15.
 */
public class Util {

  public static void applyColorFilter(Drawable drawable, int color) {
    applyColorFilter(drawable, color, false);
  }

  public static void applyColorFilter(Drawable drawable, int color, boolean mutate) {
    drawable.clearColorFilter();
    PorterDuff.Mode mode = (color == Color.TRANSPARENT ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.SRC_IN);
    if (mutate) {
      drawable.mutate().setColorFilter(color, mode);
    } else {
      drawable.setColorFilter(color, mode);
    }
  }

  public static void showToast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  /** Time-Related */
  public static String militaryTimeToAMPM(int militaryTime) {
    boolean isAM = (militaryTime / 12) < 1;
    String amPM = isAM ? "am" : "pm";
    int adjustedTime = militaryTime % 12;
    if (adjustedTime == 0) {
      if (militaryTime / 12 == 1) {
        adjustedTime = 12;
      } else if (militaryTime / 12 == 1) {
        adjustedTime = 24;
      }
    }
    return String.valueOf(adjustedTime) + amPM;
  }

  public static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  public static int pxToDp(int px) {
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
  }

}
