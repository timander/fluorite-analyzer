package net.timandersen;

import net.timandersen.model.Activity;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class ActivityDurationGroup {
  private boolean isStarted = false;

  private DateTime startTime;
  private final Duration sessionDuration;

  public ActivityDurationGroup(Duration sessionDuration) {
    this.sessionDuration = sessionDuration;
  }

  public boolean isCurrentSession(Activity previous, Activity current) {
    DateTime previousStartTime;
    if (previous == null) previousStartTime = startTime;
    else previousStartTime = new DateTime(previous.getMillisecond());

    DateTime endDate = previousStartTime.plus(sessionDuration);
    return endDate.isAfter(current.getMillisecond());
  }

  public void start(Activity activity) {
    startTime = new DateTime(activity.getMillisecond());
    this.isStarted = true;
  }

  public boolean isStarted() {
    return isStarted;
  }

  public CodeSession stop(Activity activity) {
    this.isStarted = false;
    DateTime endTime = new DateTime(activity.getMillisecond());
    return new CodeSession(startTime, endTime);
  }

  public static ActivityDurationGroup getDefault() {
    return new ActivityDurationGroup(Duration.standardMinutes(10));
  }

}
