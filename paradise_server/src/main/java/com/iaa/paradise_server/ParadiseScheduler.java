package com.iaa.paradise_server;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.iaa.paradise_server.utils.CSVUtils;
import com.iaa.paradise_server.utils.FileDownloaderUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ParadiseScheduler {

    @Autowired
    CSVUtils csvUtils;
    @Autowired
    FileDownloaderUtils fileDownloaderService;

    @Value("${book.guttenberg.namespace}")
    private String namespace;
    @Value("${book.guttenberg.ebookUrl}")
    private String ebookUrl;
    @Value("${book.guttenberg.metadata.url}")
    private String metadataUrl;
    @Value("${book.guttenberg.metadata.local}")
    private String metadataLocation;
    @Value("${book.guttenberg.cachedBooksDir}")
    private String cachedBooksDir;
    @Value("${book.guttenberg.cachedBooklist}")
    private String cachedBookslist;

    private static final Logger logger = LoggerFactory.getLogger(ParadiseScheduler.class);

    @Scheduled(fixedDelay = Long.MAX_VALUE, initialDelay = 1000)
    public void updateMetadataAndCache() {
        logger.info("Starting ParadiseScheduler.updateMetadataAndCache");
        Path metadataLocationPath = Paths.get("downloads", namespace, metadataLocation);
        fileDownloaderService.downloadFile(metadataUrl, metadataLocationPath);
        createCache();
    }

    public void createCache() {
        logger.info("Starting ParadiseScheduler.createCache");
        Path FILE_NAME_PATH = Paths.get("downloads", namespace, cachedBookslist);
        File FILE_NAME_DIR = new File(FILE_NAME_PATH.getParent().toString());

        if (!FILE_NAME_DIR.exists())
            FILE_NAME_DIR.mkdirs();
            
        try {
            File file = new File(FILE_NAME_PATH.toString());
            if (file.createNewFile()) {
                logger.info("File created: " + file.getName());
            }
            // else {
            // logger.info("Cache already exists.");
            // }
        } catch (Exception e) {
            logger.error(e.getClass() + " : " + e.getLocalizedMessage());
        }
    }
}
