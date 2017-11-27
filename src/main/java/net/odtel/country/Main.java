/* ===========================================================================
 * Copyright (c) 2015 Comcast Corp. All rights reserved.
 * ===========================================================================
 *
 * Author: Alexander Ievstratiev
 * Created: 11/27/2017  12:55 PM
 */
package net.odtel.country;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class Main {
    private List<City> cities = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        
        main.run();
    }
    
    public List<City> getCities() {
        return cities;
    }
    
    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    
    public List<String> getD(String url) throws IOException {
        List<String> a = new ArrayList<String>();
        Document newDoc = Jsoup.connect("https://uk.wikipedia.org" + url).get();
    
        for (Element next : newDoc.select("tr")) {
            Elements td = next.getElementsByTag("th");
        
            if ("Населення".equals(td.text())) {
                Elements td1 = next.getElementsByTag("td");
                a.add(td1.text());
            }
        
            if ("Площа".equals(td.text())) {
                Elements td1 = next.getElementsByTag("td");
                a.add(td1.text());
            }
        
            if ("Густота населення".equals(td.text())) {
                a.add(next.getElementsByTag("td").text());
                if (a.size() == 2) {
                    return a;
                }
            }
        }
        
        return a;
    }
    
    public void run() throws IOException {
        Document doc = Jsoup.connect("https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)").get();
        log.info(doc.title());
        
        Element table = doc.select("table[class=wikitable sortable]").first();
        Iterator<Element> ite = table.select("td").iterator();
        while (ite.hasNext()) {
            Element cityElement = ite.next();
            
            String cityName = cityElement.text();
            Elements c = cityElement.getElementsByTag("a");
            String href = c.attr("href");
            
            List<String> d = getD(href);
            
            String aria = ite.next().text();
            
            String iq = ite.next().text();
            String year = ite.next().text();
            String population = d.size() > 0 ? d.get(0) : "";
            String square = d.size() > 1 ? d.get(1) : "";
            String populationDensity = d.size() > 2 ? d.get(2) : "";
            
            cities.add(new City(cityName, aria, iq, population, square, populationDensity));
        }
        
        cities.stream()
                .sorted(Comparator.comparing(City::getAria))
                .forEach(System.out::println);
        
    }
}
