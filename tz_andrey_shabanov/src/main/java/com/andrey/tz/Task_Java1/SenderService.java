package com.andrey.tz.Task_Java1;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

public class SenderService {

    private static final List<Integer> SENDING_DATES = List.of(1, 10, 20);
    private static final int SENDING_HOUR = 18;

    private final CurrentDateService currentDateService;
    private final VocationService vocationService;

    public SenderService(CurrentDateService currentDateService, VocationService vocationService) {
        this.currentDateService = currentDateService;
        this.vocationService = vocationService;
    }

    public Date getNearestDate() {
        var nowDateTime = currentDateService.getNow().atZone(ZoneId.systemDefault());
        var nowDate = Date.from(nowDateTime.toInstant());
        for (var i = 0; true; i++) { // пробегаемся по месяцам
            for (var sendingDateNumber : SENDING_DATES) {
                var sendingDateTime = nowDateTime
                        .plusMonths(i)
                        .withDayOfMonth(sendingDateNumber)
                        .withHour(SENDING_HOUR)
                        .withMinute(0)
                        .withSecond(0);

                var sendingDate = convertDateTimeToDate(sendingDateTime);
                while (vocationService.isVocationDate(sendingDate)) {
                    sendingDateTime = sendingDateTime.minusDays(1);
                    sendingDate = convertDateTimeToDate(sendingDateTime);
                }
                if (nowDate.before(sendingDate)) {
                    return sendingDate;
                }
            }
        }
    }

    private Date convertDateTimeToDate(ZonedDateTime sendingDateTime) {
        return convertToDate(sendingDateTime.getYear(),
                sendingDateTime.getMonthValue(),
                sendingDateTime.getDayOfMonth());
    }

    private Date convertToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, SENDING_HOUR);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return new java.sql.Date(calendar.getTimeInMillis());
    }
}