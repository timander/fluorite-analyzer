package net.timandersen;

import net.timandersen.model.Activity;
import net.timandersen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SessionGrouper {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ActivityRepository activityRepository;

  public static final long FIFTEEN_MINUTES = 15 * 60 * 1000L;

  public void assignGroups() {
    for (String user : findDistinctUsers()) {
      List<Activity> activitiesForUser = activityRepository.findActivitiesForUser(user);
      Collections.sort(activitiesForUser, new ActivityComparator());

      Activity previousActivity = null;
      long sessionNumber = 1L;
      for (Activity activity : activitiesForUser) {
        if (previousActivity == null) previousActivity = activity;
        long durationBetweenEvents = activity.getMillisecond() - previousActivity.getMillisecond();
        if (durationBetweenEvents > FIFTEEN_MINUTES) {
          sessionNumber++;
        }
        previousActivity = activity;
        activity.setSession(sessionNumber);
      }
      activityRepository.save(activitiesForUser);
    }
  }

  private List<String> findDistinctUsers() {
    List<String> users = new ArrayList<String>();
    List<Map<String, Object>> rows = jdbcTemplate.queryForList("select distinct user from activity");
    for (Map row : rows) {
      users.add((String) row.get("user"));
    }
    return users;
  }

  public List<CodeSession> getCodeSessionsForUser(String user) {
    List<Activity> activitiesForUser = activityRepository.findActivitiesForUser(user);
    List<CodeSession> codeSessions = new ArrayList<CodeSession>();
    ActivityDurationGroup currentActivityDurationGroup = ActivityDurationGroup.getDefault();
    Activity previousActivity = null;
    for (Activity activity : activitiesForUser) {
      if (!currentActivityDurationGroup.isStarted()) currentActivityDurationGroup.start(activity);
      if (!currentActivityDurationGroup.isCurrentSession(previousActivity, activity)) {
        codeSessions.add(currentActivityDurationGroup.stop(previousActivity));
        currentActivityDurationGroup = ActivityDurationGroup.getDefault();
        currentActivityDurationGroup.start(activity);
      }
      previousActivity = activity;
    }
    codeSessions.add(currentActivityDurationGroup.stop(previousActivity));
    return codeSessions;
  }

  public Map<String, List<CodeSession>> getUserCodeSessions() {
    Map<String, List<CodeSession>> authorSessions = new HashMap<String, List<CodeSession>>();
    List<String> distinctUsers = activityRepository.findDistinctUsers();
    for (String user : distinctUsers) {
      authorSessions.put(user, getCodeSessionsForUser(user));
    }
    return authorSessions;
  }
}
