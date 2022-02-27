package com.iaa.paradise_server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@EnableScheduling
@Service
public class BookCollectorSvc {
    @Autowired
    CsvUtils csvUtils;
    @Autowired
    FileDownloaderSvc fdownloader;
    @Value("${spring.guttenberg.metaData.url}")
    private String metadataUrl;
    @Value("${spring.guttenberg.metaData.local}")
    private Path metadataLocation;
    @Value("${spring.guttenberg.ebookUrl}")
    private String ebookUrl;

    @Value("${spring.guttenberg.cachedBooksDir}")
    private String cachedBooksDir;
    @Value("${spring.guttenberg.cachedBookList}")
    private String cachedBooksList;

    @Scheduled(fixedDelay = 9999999, initialDelay = 1000)
    public void updateMetaDataAndcache() {
        fdownloader.downloadFile(metadataUrl,metadataLocation);
        createCache();
    }
    @Scheduled(fixedDelay=Long.MAX_VALUE, initialDelay = 1000)
    public void createCache() {
        try {
            File myObj = new File(cachedBooksList);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("Cache already exists.");
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }
    public void downloadBook(String bookname){
        try {
            System.out.println(bookname);
            if (!fdownloader.checkCachedBooks(bookname)) {
                String[] s = csvUtils.findBookByName(metadataLocation,bookname);
                System.out.println("Book not present preparing to download");
                System.out.println(Arrays.toString(s));
                String url=String.format(ebookUrl,s[0],s[0]);
                String path=String.format(cachedBooksDir,s[3]).replace("\n"," ");
                fdownloader.downloadFile(url, Path.of(path));

            }
            else{
                System.out.println("Book already present");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
