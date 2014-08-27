package net.timandersen;

import net.timandersen.model.Activity;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SessionGrouperTest {
  private static Long SECOND = 1000L;
  private static Long MINUTE = 60 * SECOND;
  private static Long FIVE_MINUTES = 5 * MINUTE;
  private static Long HOUR = 60 * MINUTE;
  private static Long NOW = System.currentTimeMillis();

  @Test
  public void groupSessionsByEventsWithinFiveMinutes() {
    SessionGrouper grouper = new SessionGrouper();
    List<Activity> activities = Arrays.asList(
      makeActivityWithTimestamp(NOW, "event1"),
      makeActivityWithTimestamp(NOW + FIVE_MINUTES, "event2"),
      makeActivityWithTimestamp(NOW + HOUR, "event3"));
    List<Activity> modifiedActivities = grouper.assignGroups(activities);
    assertEquals(new Long(1), modifiedActivities.get(0).getSession());
    assertEquals(new Long(1), modifiedActivities.get(1).getSession());
    assertEquals(new Long(2), modifiedActivities.get(2).getSession());
  }

  private Activity makeActivityWithTimestamp(long millisecond, String event) {
    return new Activity("user1", "file1.xml", millisecond, event);
  }

}
