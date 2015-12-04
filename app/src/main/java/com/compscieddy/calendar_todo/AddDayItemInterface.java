package com.compscieddy.calendar_todo;

/**
 * Created by elee on 11/19/15.
 */
public interface AddDayItemInterface {
  // TODO: refactor this shit, do u even program bro?
  void onAddMorningItemClick(String itemString);
  void onAddAfternoonItemClick(String itemString);
  void onAddEveningItemClick(String itemString);
  void updateMorningSummaryCount();
  void updateAfternoonSummaryCount();
  void updateEveningSummaryCount();
}
