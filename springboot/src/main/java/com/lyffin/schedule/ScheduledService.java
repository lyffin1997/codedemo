package com.lyffin.schedule;

//@Service
public class ScheduledService {



    /**
     * cron: 秒 分 时 日 月 周
     * 30 0/5 10,18 * * ?  每天10点和18点，每隔5分钟执行一次
     * 0 15 10 ? * 1-6   每月周一到周六10点15分执行一次
     */
    //@Scheduled(cron = "0/2 * * * * ?")
    public void scheduledJob() {
        System.out.println("定时任务执行成功！");
    }
}
