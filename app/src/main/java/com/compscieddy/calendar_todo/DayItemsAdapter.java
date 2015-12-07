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

  public DayItemsAdapter(Context context, ArrayList<String> values) {
    super(context, 0, values);
    mContext = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = layoutInflater.inflate(R.layout.day_item_layout, parent, false);

    ViewGroup undergroundContainer = (ViewGroup) rowView.findViewById(R.id.underground_container);

    TextView titleText = (TextView) rowView.findViewById(R.id.day_item_title);
    titleText.setText(values.get(position));
    titleText.setOnTouchListener(new OnSwipeTouchListener(mContext, titleText, undergroundContainer));

    return rowView;
  }

  // TODO: let's turn this into a fucking library yah?
  public class OnSwipeTouchListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private GestureListener mGestureListener;
    private Context mContext;

    public OnSwipeTouchListener(Context context, View titleView, ViewGroup undergroundContainer) { // isn't there a way to directly get the view from the listener?
      mGestureListener = new GestureListener(titleView, undergroundContainer);
      mGestureDetector = new GestureDetector(context, mGestureListener);
      mContext = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_UP
          || event.getAction() == MotionEvent.ACTION_CANCEL) {
        v.animate().x(mGestureListener.getActionUpX());
      }
      return mGestureDetector.onTouchEvent(event);
    }

    /** Gesture Listener Code */

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
      private static final int START_SWIPE_THRESHOLD = 40; // amount to go before animating views for scrolling
      private static final float SWIPE_OPEN_THRESHOLD = 0.35f;

      private Context mContext;
      private View mView;
      private ViewGroup mUndergroundContainer;
      private int mActionUpX;
      private boolean mIsPastSwipeOpenThreshold;
      private int mStartingX; // necessary for when the row item is swiped open

      public GestureListener(View mainView, ViewGroup undergroundContainer) {
        mView = mainView;
        mContext = mainView.getContext();
        mUndergroundContainer = undergroundContainer;
        mActionUpX = mContext.getResources().getDimensionPixelOffset(R.dimen.day_item_list_margin_left);
      }

      @Override
      public boolean onDown(MotionEvent e) {
        mStartingX = Math.round(mView.getX());
        return true;
      }

      // TODO: add onFling() that does similar shit to onScroll() but maybe with some more specific optimizations/thresholds

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float rawDistanceX = e2.getRawX() - e1.getRawX(); // TODO: incorrect to assume drag when the view is starting from 0
        if (rawDistanceX < 0) {
          // Log.e(TAG, "Going left <-- x:" + `rawDistanceX);
          // TODO: if it's already closed, you shouldn't be able to close anymore!
          mIsPastSwipeOpenThreshold = false;
          mView.setX(rawDistanceX + mStartingX);
        } else {
          // Log.e(TAG, "Going right -->x:" + rawDistanceX);
          mView.setX(rawDistanceX + mStartingX);

          mIsPastSwipeOpenThreshold = (e2.getRawX() / mView.getWidth() >= SWIPE_OPEN_THRESHOLD);
        }

        return true; // this might fuck up onFling() shit
      }

      public int getActionUpX() {
        if (mIsPastSwipeOpenThreshold) {
          return mUndergroundContainer.getWidth();
        }
        return mActionUpX;
      }
    }
  }
}
