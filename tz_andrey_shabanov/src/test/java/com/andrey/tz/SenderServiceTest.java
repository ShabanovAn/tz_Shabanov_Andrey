package com.andrey.tz;

import com.andrey.tz.Task_Java1.CurrentDateService;
import com.andrey.tz.Task_Java1.SenderService;
import com.andrey.tz.Task_Java1.VocationService;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SenderServiceTest {

    static final int YEAR = 2024;
    static final int MONTH = 9;
    static final int SENDING_HOUR = 18;
    static final int MINUTE = 0;
    static final int SECOND = 0;

    @Test
    void getNearestDate_SevenDay_Returns10() {
        var target = new SenderService(date(YEAR, MONTH, 7, 10, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 10, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_ThirteenDay_Returns20() {
        var target = new SenderService(date(YEAR, MONTH, 13, 10, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 20, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwentyThreeDay_Returns1() {
        var target = new SenderService(date(YEAR, 9, 23, 10, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, 10, 1, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TenDayMorning_Returns10() {
        var target = new SenderService(date(YEAR, MONTH, 10, 10, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 10, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TenDayEvening_Returns20() {
        var target = new SenderService(date(YEAR, MONTH, 10, 20, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 20, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TenDayMinuteBeforeSending_Returns10() {
        var target = new SenderService(date(YEAR, MONTH, 10, 17, 59, 59), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 10, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwentyDayAtTimeOfSending_Returns1() {
        var target = new SenderService(date(YEAR, 9, 20, 18, MINUTE, 1), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, 10, 1, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwentyThreeDayDecember_ReturnsNextYear() {
        var target = new SenderService(date(2004, 12, 23, 10, MINUTE, SECOND), noVocation());

        var nearestDate = target.getNearestDate();

        assertDate(2005, 1, 1, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwoDaySendTenDayVocation_ReturnsNineDay() {
        var target = new SenderService(date(YEAR, MONTH, 2, 10, MINUTE, SECOND), vocations(List.of(10)));

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 9, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwoDaySendTenAndNineDayVocation_ReturnsEightDay() {
        var target = new SenderService(date(YEAR, MONTH, 2, 10, MINUTE, SECOND), vocations(List.of(10, 9)));

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 8, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    @Test
    void getNearestDate_TwoEightSendTenAndNineAngEightDayVocation_ReturnsEightDay() {
        var target = new SenderService(date(YEAR, MONTH, 8, 10, MINUTE, SECOND), vocations(List.of(10, 9, 8, 20)));

        var nearestDate = target.getNearestDate();

        assertDate(YEAR, MONTH, 19, SENDING_HOUR, MINUTE, SECOND, nearestDate);
    }

    private CurrentDateService date(int year, int month, int day, int hour, int minute, int second) {
        return () -> LocalDateTime.of(year, month, day, hour, minute, second);
    }

    private VocationService noVocation() {
        return modDate -> modDate;
    }

    private VocationService vocations(List<Integer> days) {
        return modDate -> {
            var calendar = new GregorianCalendar();
            calendar.setTime(modDate);
            if (days.contains(calendar.get(Calendar.DAY_OF_MONTH))) {
                return new Date((int) (Math.random() * 100000));
            }
            return modDate;
        };
    }

    private void assertDate(int year, int month, int day, int hour, int minute, int second, Date expected) {
        var calendar = new GregorianCalendar();
        calendar.setTime(expected);

        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(month, calendar.get(Calendar.MONTH) + 1);
        assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
        assertEquals(second, calendar.get(Calendar.SECOND));
    }
}