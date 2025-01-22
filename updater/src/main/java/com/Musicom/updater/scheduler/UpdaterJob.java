package com.Musicom.updater.scheduler;

import com.Musicom.updater.Updater;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDate;

@AllArgsConstructor
public class UpdaterJob implements Job {
    private Updater updater;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        updater.updateByDate(LocalDate.now());
    }
}
