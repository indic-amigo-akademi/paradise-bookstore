package com.iaa.paradise_server.utils;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.handler.codec.http.HttpHeaders;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Arrays;

import com.iaa.paradise_server.configuration.Constants;

@Service
public class FileDownloaderUtils {
    @Autowired
    CSVUtils csvUtils;
    AsyncHttpClient client = Dsl.asyncHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(FileDownloaderUtils.class);

    public void downloadFile(String FILE_URL, Path FILE_NAME_PATH) {
        try {
            File FILE_NAME_DIR = new File(FILE_NAME_PATH.getParent().toString());
            String FILE_NAME = String.valueOf(FILE_NAME_PATH);

            if (!FILE_NAME_DIR.exists())
                FILE_NAME_DIR.mkdirs();

            File file = new File(FILE_NAME);

            logger.info("Downloading file: " + FILE_NAME);
            if (file.createNewFile())
                ;
            client.prepareGet(FILE_URL).addHeader("User-Agent", Constants.USER_AGENT)
                    .execute(new AsyncCompletionHandler<Void>() {
                        double total_file_size;

                        @Override
                        public State onHeadersReceived(HttpHeaders headers) throws Exception {
                            total_file_size = headers.getInt("Content-Length");
                            logger.info("Filesize received: " + total_file_size / 1000000 + " MB");
                            logger.info("Filesize of saved file: " + ((double) file.length()) / 1000000 + " MB");
                            if (total_file_size <= file.length()) {
                                logger.info("File already downloaded.");
                                return State.ABORT;
                            }
                            return State.CONTINUE;
                        }

                        @Override
                        public Void onCompleted(Response response) throws Exception {
                            if (total_file_size <= file.length()) {
                                return null;
                            }
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(response.getResponseBodyAsBytes());
                            fos.close();
                            logger.info("File downloaded: " + FILE_NAME);
                            return null;
                        }
                    });
        } catch (Exception e) {
            logger.error(e.getClass() + " : " + e.getLocalizedMessage());
        }

    }

    public boolean checkCachedBooks(Path path, String bookName) {
        try {
            String[] arr = csvUtils.findBookByName(path, bookName);
            return !(Arrays.asList(arr).isEmpty());
        } catch (Exception e) {
            logger.error(e.getClass() + " : " + e.getLocalizedMessage());
            return false;
        }
    }
}
