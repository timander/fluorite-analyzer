package net.timandersen;

import net.timandersen.model.Activity;
import net.timandersen.repository.ActivityRepository;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionGrouperTest {

  @Mock
  private ActivityRepository repository;

  @InjectMocks
  SessionGrouper sessionGrouper = new SessionGrouper();

  DateTime now = DateTime.now();
  Activity one = new Activity("tim", "", now.getMillis(), "session1");
  Activity two = new Activity("tim", "", now.plusMinutes(5).getMillis(), "session1");
  Activity three = new Activity("tim", "", now.plusMinutes(11).getMillis(), "session1");
  Activity tenMinutesAfterThree = new Activity("tim", "", now.plusMinutes(11+10).getMillis(), "session2");

  Activity brandon = new Activity("brandon", "", now.plusMinutes(11).getMillis(), "session2");

  @Test
  public void getCodeSessionsForUserMultipleSessions() {
    when(repository.findActivitiesForUser("tim")).thenReturn(Arrays.asList(one,two,three, tenMinutesAfterThree));

    List<CodeSession> actual = sessionGrouper.getCodeSessionsForUser("tim");

    assertThat(actual.size(), is(2));
  }

  @Test
  public void getCodeSessionsForUserSingleActivityHasZeroDuration(){
    when(repository.findActivitiesForUser("tim")).thenReturn(Arrays.asList(one));

    List<CodeSession> actual = sessionGrouper.getCodeSessionsForUser("tim");

    assertThat(actual.size(), is(1));
    assertThat(actual.get(0).getDuration(), is(Duration.ZERO));
  }

  @Test
  public void getUserCodeSessions(){
    when(repository.findDistinctUsers()).thenReturn(Arrays.asList("tim", "brandon"));
    when(repository.findActivitiesForUser("tim")).thenReturn(Arrays.asList(one, two, three));
    when(repository.findActivitiesForUser("brandon")).thenReturn(Arrays.asList(brandon));

    Map<String, List<CodeSession>> actual = sessionGrouper.getUserCodeSessions();

    assertThat(actual.size(), is(2));
    assertThat(actual.get("tim").size(), is(1));
    assertThat(actual.get("brandon").size(), is(1));
  }
}