import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfTheWeek {
  public String dayOfTheWeek(int day, int month, int year) {
    LocalDate date = LocalDate.of(year, month, day);
    String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    return dayName;
  }
}
