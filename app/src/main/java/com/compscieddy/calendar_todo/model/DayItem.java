package com.compscieddy.calendar_todo.model;

/**
 * Created by elee on 12/2/15.
 */
public class DayItem {

  public String title;
  public int hour; // 24 hour military time
  public int day; // MM+DD+YYYY? - but if the user changes system time I'm screwed

}
