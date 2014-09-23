package net.timandersen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/application.xml")
public class SessionGrouperIntegrationTest {


  @Autowired
  SessionGrouper sessionGrouper;

  @Test
  public void groupActivitiesBySession() {
    sessionGrouper.assignGroups();
  }

}
