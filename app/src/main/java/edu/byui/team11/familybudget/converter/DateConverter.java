package edu.byui.team11.familybudget.converter;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * DateConverter converts {@link Date} from and to a UNIX timestamp (as a {@link Long}). This is
 * necessary to store them on a database.
 */
public class DateConverter {

  /**
   * Converts a timestamp to a {@link Date}
   * @param value
   * @return
   */
  @TypeConverter
  public static Date fromTimestamp(Long value) {
    if (value == null) {
      return null;
    }

    return new Date(value);
  }

  /**
   * Converts a {@link Date} to a timestamp
   * @param date
   * @return
   */
  @TypeConverter
  public static Long dateToTimestamp(Date date) {
    if (date == null) {
      return null;
    }

    return date.getTime();
  }
}
