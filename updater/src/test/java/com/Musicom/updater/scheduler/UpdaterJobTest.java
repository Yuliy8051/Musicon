package com.Musicom.updater.scheduler;

import com.Musicom.updater.Updater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdaterJobTest {
    @Mock
    private Scheduler scheduler;

    @Mock
    private Updater updater;

    @InjectMocks
    private UpdaterJob updaterJob;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCompleteUpdaterJob() throws Exception {
        updaterJob.execute(mock(JobExecutionContext.class));
        verify(updater).updateByDate(any());
    }
    @Test
    void shouldHandle404StopTimeIsNot0() throws Exception {
        var constructor
                = (Constructor<HttpClientErrorException.TooManyRequests>) Arrays.stream(
                        HttpClientErrorException
                                .TooManyRequests
                                .class
                                .getDeclaredConstructors()
                ).min(Comparator.comparing(Constructor::getParameterCount))
                .get();
        constructor.setAccessible(true);

        HttpClientErrorException.TooManyRequests ex = spy(constructor.newInstance(null, null, null, null));
        JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
        Trigger trigger = mock(Trigger.class);
        TriggerKey triggerKey = mock(TriggerKey.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        JobDetail jobDetail = mock(JobDetail.class);
        String description = "1";

        doThrow(ex).when(updater).updateByDate(any(LocalDate.class));
        when(ex.getResponseHeaders()).thenReturn(headers);
        when(headers.getFirst("Retry-After")).thenReturn("5");
        when(jobExecutionContext.getTrigger()).thenReturn(trigger);
        when(trigger.getKey()).thenReturn(triggerKey);
        when(jobExecutionContext.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getDescription()).thenReturn(description);

        updaterJob.execute(jobExecutionContext);

        verify(scheduler).pauseTrigger(triggerKey);
        verify(scheduler).resumeTrigger(triggerKey);
    }
    @Test
    void shouldHandle404StopTimeIs0() throws Exception {
        var constructor
                = (Constructor<HttpClientErrorException.TooManyRequests>) Arrays.stream(
                        HttpClientErrorException
                                .TooManyRequests
                                .class
                                .getDeclaredConstructors()
                ).min(Comparator.comparing(Constructor::getParameterCount))
                .get();
        constructor.setAccessible(true);

        HttpClientErrorException.TooManyRequests ex = spy(constructor.newInstance(null, null, null, null));
        JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        JobDetail jobDetail = mock(JobDetail.class);
        String description = "10";

        doThrow(ex).when(updater).updateByDate(any(LocalDate.class));
        when(ex.getResponseHeaders()).thenReturn(headers);
        when(headers.getFirst("Retry-After")).thenReturn("5");
        when(jobExecutionContext.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getDescription()).thenReturn(description);

        updaterJob.execute(jobExecutionContext);

        verify(scheduler, times(0)).pauseTrigger(any());
        verify(scheduler, times(0)).resumeTrigger(any());
    }
}
