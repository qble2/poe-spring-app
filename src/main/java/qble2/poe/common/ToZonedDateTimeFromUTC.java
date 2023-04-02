package qble2.poe.common;

import java.time.ZonedDateTime;
import org.mapstruct.Named;

public interface ToZonedDateTimeFromUTC {

  @Named("toZonedDateTimeFromUTC")
  default ZonedDateTime toZonedDateTimeFromUTC(String utc) {
    if (utc == null) {
      return null;
    }

    return ZonedDateTime.parse(utc);
  }

}
