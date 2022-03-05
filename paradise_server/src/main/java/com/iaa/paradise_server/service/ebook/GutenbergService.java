package com.iaa.paradise_server.service.ebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.iaa.paradise_server.utils.CSVUtils;
import com.iaa.paradise_server.utils.FileDownloaderUtils;

@EnableScheduling
@Service
public class GutenbergService {

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

    private static final Logger logger = LoggerFactory.getLogger(GutenbergService.class);

    public void downloadBook(String bookname) {
        Path cachedBookslistPath = Paths.get("downloads", namespace, cachedBookslist),
                metadataLocationPath = Paths.get("downloads", namespace, metadataLocation),
                cachedBooksDirPath = Paths.get("downloads", namespace, cachedBooksDir);
        try {
            logger.info("Downloading " + bookname + "...");
            if (!fileDownloaderService.checkCachedBooks(cachedBookslistPath, bookname)) {
                String[] s = csvUtils.findBookByName(metadataLocationPath, bookname);
                logger.info("Book not present preparing to download");
                String url = String.format(ebookUrl, s[0], s[0]);
                String path = String.format(cachedBooksDirPath.toString(), s[3]).replace("\n", " ");
                fileDownloaderService.downloadFile(url, Path.of(path));

            } else {
                logger.warn("Book already present");
            }
        } catch (Exception e) {
            logger.error(e.getClass() + " : " + e.getLocalizedMessage());
        }
    }

    public String[] searchBook(String bookname) {
        Path metadataLocationPath = Paths.get("downloads", namespace, metadataLocation);
        try {
            logger.info("Searching " + bookname + "...");
            List<String[]> bookList = csvUtils.readAllByFile(metadataLocationPath);
            for (String[] s : bookList) {
                if (Arrays.stream(s).anyMatch(n -> n.strip().replace("\n", " ").equalsIgnoreCase(bookname))) {
                    logger.info("Book found: " + Arrays.toString(s));
                    return s;
                }
            }
        } catch (Exception e) {
            logger.error(e.getClass() + " : " + e.getLocalizedMessage());
        }
        return null;
    }
}
