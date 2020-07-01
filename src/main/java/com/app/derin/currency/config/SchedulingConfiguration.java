package com.app.derin.currency.config;

import com.app.derin.currency.ext.schedule.JobService;
import com.app.derin.currency.ext.schedule.WriteCurrencyRatesJob;
import com.app.derin.currency.repository.CurConfigRepository;
import com.app.derin.currency.service.impl.CurCurrencyDateServiceImpl;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration

public class SchedulingConfiguration  implements SchedulingConfigurer {
    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImpl.class);

    @Autowired
    CurConfigRepository curConfigRepository;

    @Autowired
    private JobService jobService;

    String cron;

    public void setCron () {
        cron = curConfigRepository.findImportTimeById(1L);
    }

    @Bean
    public WriteCurrencyRatesJob writeCurrencyRatesJob() {
        return new WriteCurrencyRatesJob();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        setCron();
        jobService.createJob(cron,"get_rate", "rates");
    }

    @Scheduled(cron = "10 0 18 * * ?" )
    public void updateCronTimeFromDb() {
        String oldCron = cron;
        setCron();
        log.debug("Old : {}, New : {}", oldCron, cron);
        if (!(oldCron.trim().equals(cron.trim()))) {
            log.debug("cron changed!");
            jobService.deleteJob("rates", "get_rate");
            jobService.createJob(cron,"get_rate", "rates");
        }
    }
}
