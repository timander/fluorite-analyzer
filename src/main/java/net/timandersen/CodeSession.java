package net.timandersen;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class CodeSession {
  private final DateTime startTime;
  private final DateTime endTime;
  private final Duration duration;

  public CodeSession(DateTime start, DateTime end){
    this(start, end, new Duration(start, end));
  }

  public CodeSession(DateTime start, DateTime end, Duration duration) {
    this.startTime = start;
    this.endTime = end;
    this.duration = duration;
  }

  public static CodeSession combine(CodeSession daily, CodeSession codeSession) {
    return new CodeSession(daily.getStartTime(), codeSession.getEndTime(), daily.getDuration().plus(codeSession.getDuration()));
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public DateTime getEndTime() {
    return endTime;
  }

  public Duration getDuration() {
    return duration;
  }
}
