package com.iaa.paradise_server.Service;

import org.asynchttpclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FileDownloaderSvc {
    @Value("${spring.guttenberg.cachedBookList}")
    private Path cachedLoc;

    @Value("${spring.guttenberg.cachedBooksDir}")
    private String cachedBooksDir;
    @Autowired
    CsvUtils cutils;
    AsyncHttpClient client = Dsl.asyncHttpClient();
    public void downloadFile(String FILE_URL,Path FILE_NAME_PATH){
        String FILE_NAME=String.valueOf(FILE_NAME_PATH);
        System.out.println("Downloading this file"+FILE_NAME);
        try {

            File myObj = new File(String.valueOf(FILE_NAME));
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            System.out.println("Here");
            FileOutputStream stream = new FileOutputStream(FILE_NAME);
            client.prepareGet(FILE_URL).addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36" +
                    "").execute(new AsyncCompletionHandler<FileOutputStream>() {

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                        throws Exception {
                    System.out.println("Writing");
                    stream.getChannel().write(bodyPart.getBodyByteBuffer());
                    return State.CONTINUE;
                }

                @Override
                public FileOutputStream onCompleted(Response response)
                        throws Exception {
                    System.out.println("File Downloaded");
                    System.out.println(FILE_NAME+FILE_URL);
                    String title = FILE_NAME.substring(FILE_NAME.lastIndexOf('\\') + 1);
                    String id = FILE_URL.substring(FILE_URL.lastIndexOf('/') + 1);
                    id=id.replace(".txt","");
                    title=title.replace(".txt","");
                    System.out.println(id+title);
                    List<String[]> arr=new ArrayList<>();
                    arr.add(new String[]{id,title});
                    cutils.csvWriterOneByOne((List<String[]>) arr,cachedLoc);
                    System.out.println("Cache Updated");
                    return stream;
                }
            });

        }
        catch(FileNotFoundException e){

            try {
                File yourFile = new File(FILE_NAME);
                yourFile.createNewFile();
                System.out.println("File Created");
            }
            catch(Exception e1){
                System.out.println(e1);
            }
            downloadFile(FILE_URL, Path.of(FILE_NAME));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public boolean checkCachedBooks(String bookName){
        try{
            String[] arr=cutils.findBookByName(cachedLoc,bookName);
            return !(Arrays.asList(arr).isEmpty());
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
