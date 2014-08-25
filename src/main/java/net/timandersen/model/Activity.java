package net.timandersen.model;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String user;

  @Column
  private String logfile;

  @Column
  private Long millisecond;

  @Column
  private String event;

  @Column
  private Long session;

  protected Activity() {
  }

  public Activity(String user, String logfile, Long millisecond, String event) {
    this.user = user;
    this.logfile = logfile;
    this.millisecond = millisecond;
    this.event = event;
  }

  public long getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  public String getLogfile() {
    return logfile;
  }

  public long getMillisecond() {
    return millisecond;
  }

  public String getEvent() {
    return event;
  }

  public Long getSession() {
    return session;
  }

  public void setSession(Long session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return "Activity{" +
      "id=" + id +
      ", user='" + user + '\'' +
      ", logfile='" + logfile + '\'' +
      ", millisecond=" + millisecond +
      ", event='" + event + '\'' +
      ", session=" + session +
      '}';
  }

}
