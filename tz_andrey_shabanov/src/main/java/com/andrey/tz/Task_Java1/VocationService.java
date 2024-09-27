package com.andrey.tz.Task_Java1;

import java.sql.Date;

public interface VocationService {

    Date getVacCheck(Date modDate);

    default boolean isVocationDate(java.util.Date date) {
        return !getVacCheck(new Date(date.getTime())).equals(date);
    }
}
