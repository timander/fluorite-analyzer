package net.timandersen;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/application.xml")
public class SessionGrouperIntegrationTest {

  @Autowired
  DataImporter dataImporter;

  @Autowired
  SessionGrouper sessionGrouper;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Test
  public void groupActivitiesBySession() {
    sessionGrouper.assignGroups();
  }

  @Test
  public void printCodeSessions() {
//    jdbcTemplate.execute("delete from Activity");
//
//    URL resource = getClass().getClassLoader().getResource("aggregate-log.csv");
//    dataImporter.importCsvFile(new File(resource.getFile()));

    Map<String, List<CodeSession>> userCodeSessions = sessionGrouper.getUserCodeSessions();
    for (String user : userCodeSessions.keySet()) {
      System.out.println(user);
      System.out.println(getDivider());
      Duration totalDuration = Duration.ZERO;
      for (CodeSession codeSession : userCodeSessions.get(user)) {
        totalDuration = totalDuration.plus(codeSession.getDuration());
        System.out.println("\t" + formatDate(codeSession.getStartDate()) + "\t" + formatDuration(codeSession.getDuration()));
      }
      System.out.println("Duration: " + formatDuration(totalDuration));
      System.out.println();
    }
  }

  public String formatDuration(Duration value) {
    return PeriodFormat.getDefault().print(value.toPeriod());
  }

  public String formatDate(DateTime value){
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yy kk:mm")
            .withLocale(Locale.US);
    return formatter.print(value);
  }

  public String getDivider() {
    return new String(new char[20]).replace("\0", "=");
  }
}
