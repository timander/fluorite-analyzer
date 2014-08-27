package net.timandersen;

import net.timandersen.model.Activity;

import java.util.Comparator;

public class ActivityComparator implements Comparator<Activity> {
  @Override
  public int compare(Activity activity1, Activity activity2) {
    Long a1ms = activity1.getMillisecond();
    Long a2ms = activity2.getMillisecond();
    return a1ms.compareTo(a2ms);
  }
}
