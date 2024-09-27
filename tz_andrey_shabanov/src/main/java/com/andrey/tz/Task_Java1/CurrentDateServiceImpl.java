package com.andrey.tz.Task_Java1;

import java.time.LocalDateTime;

public class CurrentDateServiceImpl implements CurrentDateService {

    @Override
    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
