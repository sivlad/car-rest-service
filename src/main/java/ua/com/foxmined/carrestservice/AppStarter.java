package ua.com.foxmined.carrestservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foxmined.carrestservice.utils.CarDBInitializer;

@Log4j2
@Configuration
public class AppStarter {

    @Autowired
    private CarDBInitializer carDBInitializer;

    @Bean
    public ApplicationRunner init() {
        log.info("ApplicationRunner has started");
        return args -> {
            carDBInitializer.createRowsInDb();
        };
    }

}
