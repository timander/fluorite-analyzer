package net.timandersen.repository;

import net.timandersen.model.Activity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/application.xml")
public class ActivityRepositoryTest {

  @Autowired
  ActivityRepository repository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Before
  public void setUp() {
    jdbcTemplate.execute("delete from Activity");
  }

  @Test
  public void saveFileDelete() {
    Activity activity = new Activity("test-user", "some-file.log", System.currentTimeMillis(), "test event");
    repository.save(activity);
    List<Activity> all = repository.findAll();
    assertEquals(1, all.size());
  }

  @Test
  public void findActivitiesForUser() {
    Activity activity = new Activity("test-user", "some-file.log", System.currentTimeMillis(), "test event");
    Activity anotherUserActivity = new Activity("another-user", "some-file.log", System.currentTimeMillis(), "test event");
    repository.save(Arrays.asList(activity, anotherUserActivity));
    List<Activity> activities = repository.findActivitiesForUser("test-user");
    assertThat(activities.size(), is(1));
  }

  @Test
  public void findActivitiesForUserNoResultsReturnsEmptyList() {
    List<Activity> activities = repository.findActivitiesForUser("NOT FOUND");
    assertThat(activities.size(), is(0));
  }

  @Test
  public void findDistinctUsers() {
      Activity activity = new Activity("test-user", "some-file.log", System.currentTimeMillis(), "test event");
      Activity anotherUserActivity = new Activity("another-user", "some-file.log", System.currentTimeMillis(), "test event");
      repository.save(Arrays.asList(activity, anotherUserActivity));
      List<String> distinctUsers = repository.findDistinctUsers();
      assertThat(distinctUsers.size(), is(2));
  }
}
