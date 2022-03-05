package com.iaa.paradise_server.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.iaa.paradise_server.service.ebook.GutenbergService;

@Controller
public class RestBookController {

    @Autowired
    GutenbergService gutenbergService;

    private static final Logger logger = LoggerFactory.getLogger(RestBookController.class);

    @GetMapping("/api/gutenberg/books/{name}")
    public ResponseEntity<String> getGutenbergBooks(@PathVariable String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Starting RestBookController.getGutenbergBooks");
        logger.info("Name: " + name);
        String[] book = gutenbergService.searchBook(name);
        JSONObject bookJson = new JSONObject();
        bookJson.put("success", book != null);
        bookJson.put("feed", "Gutenberg");
        if (book != null) {
            bookJson.put("id", book[0]);
            bookJson.put("type", book[1]);
            bookJson.put("issued", book[2]);
            bookJson.put("title", book[3]);
            bookJson.put("language", book[4]);
            bookJson.put("authors", book[5]);
            bookJson.put("subjects", book[6].split("; "));
            bookJson.put("locc", book[7]);
            bookJson.put("bookshelves", book[8].split("; "));
        } else {
            bookJson.put("message", "No book found.");
        }
        return new ResponseEntity<>(bookJson.toString(), headers, HttpStatus.OK);
    }
}
