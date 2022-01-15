package com.iaa.paradise_server.Service;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;
@Service
public class WebCrawlerSvc extends WebCrawler {
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private final static Pattern BOOKS = Pattern.compile("https.*ebooks\\/[0-9]*");
    private final static Pattern BOOKSFILES = Pattern.compile("https.*files\\/[0-9]*\\/[0-9]*.*htm");
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        boolean b=BOOKS.matcher(urlString).matches();

        if(b) {
            System.out.println(urlString);
        }
        return (!EXCLUSIONS.matcher(urlString).matches() && (BOOKS.matcher(urlString).matches()) || BOOKSFILES.matcher(urlString).matches());

    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println(url);
        if(BOOKSFILES.matcher(url).matches() && page.getParseData() instanceof HtmlParseData){
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            System.out.println(title);
        }
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String title = htmlParseData.getTitle();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//            System.out.println("Here");
//            System.out.println(links.toString());
//            Document doc = Jsoup.parse(html);
//            Element content = doc.getElementById("content");
//
//
//        }
    }
    public void startCrawling(String url){
        url="https://"+url;
        File crawlStorage = new File("src/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());

        int numCrawlers = 1;

        config.setMaxDepthOfCrawling(3);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer= new RobotstxtServer(robotstxtConfig, pageFetcher);
        try{
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
            CrawlController.WebCrawlerFactory<WebCrawlerSvc> factory = WebCrawlerSvc::new;
            controller.addSeed(url);
            System.out.println("Here1"+numCrawlers);
            controller.start(factory, numCrawlers);}
        catch(Exception e){
            System.out.println(e);
        }
    }

}
