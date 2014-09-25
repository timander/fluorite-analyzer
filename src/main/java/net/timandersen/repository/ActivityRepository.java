package net.timandersen.repository;

import net.timandersen.model.Activity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ActivityRepository {

  @Autowired
  @Qualifier("sessionFactory")
  private SessionFactory sessionFactory;

  public void save(Activity activity) {
    sessionFactory.getCurrentSession().saveOrUpdate(activity);
  }

  public void save(List<Activity> activities) {
    for (Activity activity : activities) {
      save(activity);
    }
  }

  public List<Activity> findAll() {
    return sessionFactory.getCurrentSession().createQuery("from Activity").list();
  }

  public List<Activity> findActivitiesForUser(String user) {
    Query query = sessionFactory.getCurrentSession().createQuery("from Activity where user = :user order by millisecond");
    query.setString("user", user);
    return query.list();
  }
}
