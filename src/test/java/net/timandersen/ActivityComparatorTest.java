package net.timandersen;

import net.timandersen.model.Activity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActivityComparatorTest {

  public ActivityComparator comparator;

  @Before
  public void setUp() {
    comparator = new ActivityComparator();
  }

  @Test
  public void sameEvent() {
    int result = comparator.compare(
      new Activity("user1", "file1.log", 1L, "event1"),
      new Activity("user1", "file1.log", 1L, "event1")
    );
    assertEquals(0, result);
  }

  @Test
  public void lessThanEvent() {
    int result = comparator.compare(
      new Activity("user1", "file1.log", 1L, "event1"),
      new Activity("user1", "file1.log", 2L, "event1")
    );
    assertEquals(-1, result);
  }

  @Test
  public void greaterThanEvent() {
    int result = comparator.compare(
      new Activity("user1", "file1.log", 2L, "event1"),
      new Activity("user1", "file1.log", 1L, "event1")
    );
    assertEquals(1, result);
  }
}
