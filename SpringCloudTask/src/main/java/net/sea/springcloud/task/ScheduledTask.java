package net.sea.springcloud.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sea
 * @Date 2018/8/10 17:52
 * @Version 1.0
 */
@Component
public class ScheduledTask {
    /**
     * 每隔1秒执行, 单位：ms。
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void testFixRate() {
        System.out.println("每隔1秒执行一次：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }

}
