package net.timandersen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;
import java.util.List;
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

    CodeSessionReport report = new CodeSessionReport();
    List<String> reportLines = report.formatCodeSessionPerUser(userCodeSessions);

    for (String reportLine : reportLines) {
      System.out.println(reportLine);
    }
  }

  @Test
  public void printDailySessions(){
    Map<String, List<CodeSession>> userCodeSessions = sessionGrouper.getUserCodeSessions();

    CodeSessionReport report = new CodeSessionReport();
    List<String> reportLines = report.formatDailyDurationPerUser(userCodeSessions);

    for (String reportLine : reportLines) {
      System.out.println(reportLine);
    }
  }

}
