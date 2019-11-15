package com.setvect.bokslportal.common;

import org.apache.commons.lang3.time.DateUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * Request에서 넘어온 Date 타입 Bind 처리
 */
public class DatePropertyEditor extends PropertyEditorSupport {

  @Override
  public void setAsText(final String value) throws IllegalArgumentException {
    try {
      if (value.length() == 10 || value.length() == 8) {
        Date date = DateUtils.parseDate(value, "yyyy-MM-dd", "yyyMMdd");
        setValue(date);
        return;
      }
    } catch (ParseException e) {
      // ignore
    }

    try {
      long date = Long.parseLong(value);
      Date time = new Date(date);
      setValue(time);
      return;
    } catch (NumberFormatException e) {
      // ignore
    }

  }
}
