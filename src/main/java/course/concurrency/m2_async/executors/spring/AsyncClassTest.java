package course.concurrency.m2_async.executors.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class AsyncClassTest {

    @Autowired
    public AsyncRun asyncRun;

    @EventListener(ApplicationReadyEvent.class)
    public void actionAfterStartup() {
        for(int i = 0; i < 5; i++) {
            asyncRun.runAsyncTask();
        }
    }
}
