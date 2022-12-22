package org.example.task2.scrapers;

import org.example.task2.Database;

public class ProxyScraper implements Scraper {
    private final Database db;
    private final NaiveScraper scraper;
    public ProxyScraper() {
        this.db = Database.getInstance();
        this.scraper = new NaiveScraper();
    }

    @Override
    public String scrape(String url) {
        String scraped;
        scraped = db.getScrapedByUrl(url);

        if (scraped == null) {
            scraped = scraper.scrape(url);
            db.addScrapedPage(url, scraped);
        }

        return scraped;
    }

}
