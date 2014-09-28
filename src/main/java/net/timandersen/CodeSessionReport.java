package net.timandersen;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CodeSessionReport {

  public List<String> formatCodeSessionPerUser(Map<String, List<CodeSession>> userCodeSessions) {
    List<String> printer = new ArrayList<String>();
    for (String user : userCodeSessions.keySet()) {
      printer.add(user);
      printer.add(getDivider());
      Duration totalDuration = Duration.ZERO;
      for (CodeSession codeSession : userCodeSessions.get(user)) {
        totalDuration = totalDuration.plus(codeSession.getDuration());
        printer.add("\t" + formatDateWithTime(codeSession.getStartTime()) + "\t" + formatDuration(codeSession.getDuration()));
      }
      printer.add("Duration: " + formatDuration(totalDuration));
      printer.add("");
    }
    return printer;
  }

  public List<String> formatDailyDurationPerUser(Map<String, List<CodeSession>> codeSessions) {
    List<String> printer = new ArrayList<String>();

    for (String user : codeSessions.keySet()) {
      for (CodeSession session : GroupBy.day(codeSessions.get(user))) {
        printer.add(user + "\t" + formatDay(session.getStartTime()) + "\t" + formatDuration(session.getDuration()));
      }
    }

    return printer;
  }

  public static String formatDuration(Duration value) {
    return value.toStandardMinutes().getMinutes() + "";
  }

  public static String formatDay(DateTime value) {
    return formatDate(value, "dd-MMM-yy");
  }

  private static String formatDateWithTime(DateTime value){
    return formatDate(value, "dd-MMM-yy kk:mm");
  }

  private static String formatDate(DateTime value, String pattern) {
    return DateTimeFormat
            .forPattern(pattern)
            .withLocale(Locale.US)
            .print(value);
  }

  public static String getDivider() {
    return new String(new char[20]).replace("\0", "=");
  }


}
