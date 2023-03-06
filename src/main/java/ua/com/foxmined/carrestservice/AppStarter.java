package ua.com.foxmined.carrestservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.com.foxmined.carrestservice.utils.CarDBInitializer;

@Log4j2
@Component
public class AppStarter {

    @Autowired
    private CarDBInitializer carDBInitializer;

    @EventListener(ApplicationReadyEvent.class)
    public void AppStarter() {
        log.info("ApplicationRunner has started");
        carDBInitializer.createRowsInDb();
    }
}
