package net.timandersen;

import net.timandersen.model.Activity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CodeSessionTest {

  private DateTime now = DateTime.now();
  private CodeSession tenMinuteSession;

  @Before
  public void setUp() throws Exception {
    tenMinuteSession = new CodeSession(Duration.standardMinutes(10));
  }

  @Test
  public void isWithinSessionWhenPreviousActivityIsNull() {
    Activity activity = new Activity("tim", "", now.plusMinutes(10).getMillis(), "some event");
    Activity withinSessionActivity = new Activity("tim", "", now.plusMinutes(9).getMillis(), "some event");
    tenMinuteSession.start(activity);

    assertThat(tenMinuteSession.isCurrentSession(null, withinSessionActivity), is(true));
  }

  @Test
  public void isWithinSessionWhenPreviousAndCurrentActivityWithinDuration() {
    Activity startingActivity = new Activity("tim", "", now.getMillis(), "some event");
    Activity withinSessionActivity = new Activity("tim", "", now.plusMinutes(9).getMillis(), "some event");
    Activity nineteenMinutesAfterNow = new Activity("tim", "", now.plusMinutes(9+9).getMillis(), "some event");

    tenMinuteSession.start(startingActivity);

    assertThat(tenMinuteSession.isCurrentSession(startingActivity, withinSessionActivity), is(true));
    assertThat(tenMinuteSession.isCurrentSession(withinSessionActivity, nineteenMinutesAfterNow), is(true));
  }
}