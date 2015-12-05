package com.compscieddy.calendar_todo;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by elee on 11/9/15.
 */
public class DayItemsAdapter extends ArrayAdapter<String> {

  private static final String TAG = DayItemsAdapter.class.getSimpleName();

  private final Context mContext;
  private final ArrayList<String> values;
  private int mStartingX; // for resetting the swipe

  public DayItemsAdapter(Context context, ArrayList<String> values) {
    super(context, 0, values);
    mContext = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = layoutInflater.inflate(R.layout.day_item_layout, parent, false);

    mStartingX = mContext.getResources().getDimensionPixelOffset(R.dimen.day_item_list_margin_left);

    TextView textView = (TextView) rowView.findViewById(R.id.day_item_title);
    textView.setText(values.get(position));
    textView.setOnTouchListener(new OnSwipeTouchListener(mContext, textView));

    return rowView;
  }

  // TODO: let's turn this into a fucking library yah?
  public class OnSwipeTouchListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private Context mContext;

    public OnSwipeTouchListener(Context context, View view) { // isn't there a way to directly get the view from the listener?
      mGestureDetector = new GestureDetector(context, new GestureListener(view));
      mContext = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_UP
          || event.getAction() == MotionEvent.ACTION_CANCEL) {
        v.animate().x(mStartingX);
      }
      return mGestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
      private static final int START_SWIPE_THRESHOLD = 40; // amount to go before animating views for scrolling

      private View mView;

      public GestureListener(View view) {
        mView = view;
      }

      @Override
      public boolean onDown(MotionEvent e) {
        return true;
      }

      // TODO: add onFling() that does similar shit to onScroll() but maybe with some more specific optimizations/thresholds

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float rawDistanceX = e2.getRawX() - e1.getRawX();
        if (rawDistanceX < 0) {
          // Log.e(TAG, "Going left <-- x:" + rawDistanceX);
          // TODO: need left for closing up
        } else {
          // Log.e(TAG, "Going right -->x:" + rawDistanceX);
          if (rawDistanceX > START_SWIPE_THRESHOLD) {
            mView.setX(rawDistanceX);
          }
        }

        return false;
      }
    }
  }
}
