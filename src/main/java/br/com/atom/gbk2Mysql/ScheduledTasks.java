package br.com.atom.gbk2Mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private DatabaseConversionService conversionService;

    @Scheduled(cron = "0 0 0 * * *") // Executa todos os dias à meia-noite
    public void scheduleDatabaseConversion() {
        String firebirdBackupFilePath = "/caminho/para/backup_fb.sql";
        conversionService.convertFirebirdToMySQL(firebirdBackupFilePath);
    }
}