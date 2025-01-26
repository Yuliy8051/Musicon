package com.Musicom.updater.scheduler;

import com.Musicom.spotify_client.exception.SpotifyClientException;
import com.Musicom.updater.Updater;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
public class UpdaterJob implements Job {
    private Scheduler scheduler;

    private Updater updater;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LocalDateTime startTime = LocalDateTime.now();
        try {
            log.info("UpdateJob execution has started");
            updater.updateByDate(LocalDate.now());
            log.info("UpdateJob has been executed");
        } catch (HttpClientErrorException.TooManyRequests ex) {
            LocalDateTime exceptionTime = LocalDateTime.now();

            long intervalInSecBetweenFires = Long.parseLong(
                    jobExecutionContext
                            .getJobDetail()
                            .getDescription()
            );

            long[] secondsToStopAndToWait = calculateSecondsToStopAndToWait(ex, startTime, exceptionTime, intervalInSecBetweenFires);
            long secondsToStop = secondsToStopAndToWait[0];
            long secondsToWait = secondsToStopAndToWait[1];

            if (secondsToStop == 0)
                log.warn("Update failed. Too many requests. Next update will be started in {} seconds", secondsToWait);
            else
                try {
                    TriggerKey triggerKey = jobExecutionContext
                            .getTrigger()
                            .getKey();
                    scheduler.pauseTrigger(triggerKey);
                    log.warn("Update failed. Too many requests. Trigger is stopped for {} seconds." +
                            " Next update will be started in {} seconds", secondsToStop, secondsToWait);
                    Thread.sleep(secondsToStop);
                    scheduler.resumeTrigger(triggerKey);
                } catch (Exception e) {
                    throw new SpotifyClientException.ThreadSleepException(e.getMessage());
                }
        }
    }

    private long[] calculateSecondsToStopAndToWait(HttpClientErrorException.TooManyRequests ex,
                                                   LocalDateTime startTime,
                                                   LocalDateTime exceptionTime,
                                                   long intervalInSecBetweenFires) {
        long timeDifference = Duration
                .between(startTime, exceptionTime)
                .toSeconds();

        long retryAfter = Long.parseLong(ex.
                getResponseHeaders()
                .getFirst("Retry-After")
        );

        long remainingTimeForTriggerToRun = intervalInSecBetweenFires - timeDifference;

        if (retryAfter > remainingTimeForTriggerToRun)
            return new long[]{retryAfter - remainingTimeForTriggerToRun, retryAfter};
        else
            return new long[]{0, remainingTimeForTriggerToRun};
    }
}
