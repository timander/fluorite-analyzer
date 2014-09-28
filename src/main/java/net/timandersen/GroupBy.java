package net.timandersen;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

public class GroupBy {
  public static List<CodeSession> day(List<CodeSession> codeSessions) {
    ArrayList<CodeSession> dailySessions = new ArrayList<CodeSession>();

    CodeSession daily = null;
    for (CodeSession codeSession : codeSessions) {
      if(daily == null){
        daily = codeSession;
      }
      else if(duringSameDay(daily, codeSession)){
        daily = CodeSession.combine(daily, codeSession);
      }
      else{
        dailySessions.add(daily);
        daily = codeSession;
      }
    }
    dailySessions.add(daily);
    return dailySessions;
  }

  private static boolean duringSameDay(CodeSession daily, CodeSession codeSession) {
    DateTime start = daily.getStartTime().withTimeAtStartOfDay();
    DateTime stop = start.plusDays( 1 ).withTimeAtStartOfDay();
    Interval interval = new Interval( start, stop );
    return interval.contains( codeSession.getStartTime() );
  }


}
