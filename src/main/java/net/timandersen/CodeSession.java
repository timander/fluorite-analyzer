package net.timandersen;

import net.timandersen.model.Activity;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class CodeSession {
  private boolean isStarted = false;
  private DateTime startTime = null;
  private final Duration sessionDuration;
  private DateTime endTime;

  public CodeSession(Duration sessionDuration) {
    this.sessionDuration = sessionDuration;
  }

  public Duration getDuration() {
    return new Duration(startTime, endTime);
  }

  public boolean isCurrentSession(Activity previous, Activity current) {
    DateTime previousStartTime;
    if (previous == null) previousStartTime = startTime;
    else previousStartTime = new DateTime(previous.getMillisecond());

    DateTime endDate = previousStartTime.plus(sessionDuration);
    return endDate.isAfter(current.getMillisecond());
  }

  public void start(Activity activity) {
    this.startTime = new DateTime(activity.getMillisecond());
    this.isStarted = true;
  }

  public boolean isStarted() {
    return isStarted;
  }

  public void stop(Activity activity) {
    this.endTime = new DateTime(activity.getMillisecond());
    this.isStarted = false;
  }

  public DateTime getStartDate() {
    return startTime;
  }

  public static CodeSession getDefault() {
    return new CodeSession(Duration.standardMinutes(10));
  }
}
