package net.timandersen;

import net.timandersen.model.Activity;
import net.timandersen.repository.ActivityRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataImporter {

  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private SessionGrouper sessionGrouper;

  public void importCsvFile(File file) {
    try {
      List<String> lines = FileUtils.readLines(file);
      List<Activity> activities = convertLinesToActivities(lines);
      activityRepository.save(activities);

      System.out.println("Imported: " + activities.size());

      sessionGrouper.assignGroups();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private List<Activity> convertLinesToActivities(List<String> lines) {
    List<Activity> activities = new ArrayList<Activity>();
    List<String> errorLines = new ArrayList<String>();
    for (String line : lines) {
      try {
        Activity activity = convertLineToActivity(line);
        activities.add(activity);
      } catch (Exception ex) {
        errorLines.add(line);
      }
    }
    System.out.println("Errors: " + errorLines.size());
    return activities;
  }

  private Activity convertLineToActivity(String line) {
    String[] parts = line.split("\t");
    String user = parts[0];
    String logfile = parts[1];
    Long millisecond = Long.valueOf(parts[3]);
    String event = parts[4];
    return new Activity(user, logfile, millisecond, event);
  }

}
