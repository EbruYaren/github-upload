package com.app.derin.currency.ext.schedule;

import com.app.derin.currency.service.impl.CurCurrencyDateServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.quartz.JobKey.jobKey;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final Scheduler scheduler;

    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImpl.class);

    public JobService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void createJob(String cron, String name, String group) {

        TriggerDescriptor getjob  = new TriggerDescriptor();
        getjob.setName(name);
        getjob.setGroup(group);
        getjob.setCron(cron);

        JobDetail jobDetail = getjob.buildJobDetail();
        Trigger triggersForJob = getjob.buildTrigger();
        log.info("About to save job with key - {}", triggersForJob);
        try {
            scheduler.scheduleJob(jobDetail,triggersForJob);
            log.info("Job with key - {} saved sucessfully", jobDetail.getKey());
        } catch (SchedulerException e) {
            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    public void updateJob(String cron, String name, String group) {
        try {
            JobDetail oldJobDetail = scheduler.getJobDetail(jobKey(name, group));
            if(Objects.nonNull(oldJobDetail)) {
                JobBuilder jb = oldJobDetail.getJobBuilder();
                JobDetail newJobDetail = jb.storeDurably().build();
                scheduler.addJob(newJobDetail, true);
                log.info("Updated job with key - {}", newJobDetail.getKey());
                return;
            }
            log.warn("Could not find job with key - {}.{} to update", group, name);
        } catch (SchedulerException e) {
            log.error("Could not find job with key - {}.{} to update due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void deleteJob(String group, String name) {
        try {
            scheduler.deleteJob(jobKey(name, group));
            log.info("Deleted job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not delete job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void pauseJob(String group, String name) {
        try {
            scheduler.pauseJob(jobKey(name, group));
            log.info("Paused job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not pause job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void resumeJob(String group, String name) {
        try {
            scheduler.resumeJob(jobKey(name, group));
            log.info("Resumed job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not resume job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }
}
