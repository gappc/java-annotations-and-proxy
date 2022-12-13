package dev.gappc.example.proxy.service;

import dev.gappc.example.proxy.handler.Roles;

import java.time.LocalDate;

/**
 * This class implements {@link DateService} using Java LocalDate.
 */
public class LocalDateService implements DateService {

    @Override
    @Roles("DEMO_USER")
    public int getCurrentDayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

}
