package org.example.task2;

import org.example.task2.scrapers.ProxyScraper;

public class Main {
    public static void main(String[] args){
        ProxyScraper sc = new ProxyScraper();

        String contents = sc.scrape("https://www.newhomesource.com/communities/in/indianapolis-area");

        System.out.println(contents);
    }
}