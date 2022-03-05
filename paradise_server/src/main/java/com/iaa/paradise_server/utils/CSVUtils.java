package com.iaa.paradise_server.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVUtils {
    public List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public List<String[]> readAllByFile(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        return readAll(reader);
    }

    public String[] findBookByName(Path path, String name) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        System.out.println(String.valueOf(path));
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                System.out.println(Arrays.toString(line));
                if (Arrays.stream(line).anyMatch(n -> n.strip().replace("\n", " ").equals(name))) {
                    System.out.println("match");
                    return line;
                }
            }
        }
        return new String[] {};

    }

    public String csvWriterOneByOne(List<String[]> stringArray, Path path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), true));
        for (String[] array : stringArray) {
            writer.writeNext(array);
        }

        writer.close();
        return "CSV Updated";
    }

    public String csvWriterAll(List<String[]> stringArray, Path path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), true));
        writer.writeAll(stringArray);
        writer.close();
        return "CSV Updated";
    }
}
