package org.example.task2.scrapers;

import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@NoArgsConstructor
public class NaiveScraper implements Scraper{
    @Override
    public String scrape(String url){
        String output;
        try {
            Document doc = Jsoup.connect(url).get();
            output = doc.toString();
        } catch (IOException e) {
            output = "Errors occured, unable to scrape url: " + url;
        }
        return output;
    }
}
