package iaa.paradise.paradise_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ParadiseApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ParadiseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String serverPort = environment.getProperty("server.port", "8080");
        String serverHostname = environment.getProperty("server.address", "localhost");
        logger.info("Paradise Server Started at http://{}:{}", serverHostname, serverPort);
    }

}
