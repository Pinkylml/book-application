package com.enterprise.business;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;

@Stateless
public class StatisticsEJB {

    // Runs automatically on the 1st day of every month at 5:30 AM
    @Schedule(dayOfMonth = "1", hour = "5", minute = "30", second = "0")
    public void statisticsItemsSold() {
        System.out.println("--> [Timer Service] Compiling monthly sales metrics...");
    }

    // Aggregates multiple separate cron schedules onto a single execution method
    @Schedules({
            @Schedule(hour = "2", minute = "0", second = "0"), // Everyday at 2:00 AM
            @Schedule(hour = "14", dayOfWeek = "Wed", minute = "0", second = "0") // Wednesdays at 2:00 PM
    })
    public void generateReport() {
        System.out.println("--> [Timer Service] Generating system status audit reports...");
    }

    // A fast, non-persistent interval timer running every 10 minutes
    @Schedule(minute = "*/10", hour = "*", second = "0", persistent = false) // Bypasses disk logging
    public void refreshCache() {
        System.out.println("--> [Timer Service] Flushing temporary operational caches...");
    }
}
