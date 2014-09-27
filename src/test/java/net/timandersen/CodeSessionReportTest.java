package net.timandersen;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CodeSessionReportTest {

  @Test
  public void mapCodeSessionsToDailyDurations(){
    DateTime today = new DateTime(2014, 1, 1, 1, 1);
    DateTime tomorrow = today.plusDays(1);

    CodeSession today1HourSession = new CodeSession(today, today.plusHours(1));
    CodeSession today2HourSession = new CodeSession(today, today.plusHours(2));

    CodeSession tomorrow3HourSession = new CodeSession(tomorrow, tomorrow.plusHours(3));

    List<CodeSession> sessions = Arrays.asList(today1HourSession, today2HourSession, tomorrow3HourSession);

    List<CodeSession> dailySessions = GroupBy.day(sessions);

    assertThat(dailySessions.get(0).getDuration(), is(Duration.standardHours(3)));
    assertThat(dailySessions.get(1).getDuration(), is(Duration.standardHours(3)));
  }

  @Test
  public void printDailyDurationPerUser(){
    Map<String, List<CodeSession>> codeSessions = new HashMap<String, List<CodeSession>>();
    DateTime now = DateTime.now();
    CodeSession day1session1 = new CodeSession(now, now.plusMinutes(9));

    codeSessions.put("tim", Arrays.asList(day1session1));

    List<String> reportLines = new CodeSessionReport().formatDailyDurationPerUser(codeSessions);

    assertThat(reportLines.get(0), is("tim\t" + CodeSessionReport.formatDay(now) +"\t" + CodeSessionReport.formatDuration(day1session1.getDuration())));
  }

  @Test
  public void tabDelimitDuration(){
    Duration twoAndHalfHours = Duration.standardMinutes(150);
    String actual = CodeSessionReport.formatDuration(twoAndHalfHours);
    assertThat(actual, is("150"));
  }

  @Test
  public void formatDurationExcludesSeconds(){
    assertThat(CodeSessionReport.formatDuration(Duration.standardSeconds(59)), is("0"));
  }

}