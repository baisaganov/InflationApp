package kz.inflation.InflationApp.scripts;

import kz.inflation.InflationApp.enums.OfficialBidsEnumeration;
import kz.inflation.InflationApp.services.MRPService;
import kz.inflation.InflationApp.services.MZPService;
import kz.inflation.InflationApp.services.RefinancingBidsService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Component @Slf4j
public class CreditMRPParser {
    private final MZPService mzpService;
    private final MRPService mrpService;
    private final RefinancingBidsService refinancingBidsService;

    @Autowired
    public CreditMRPParser(MZPService mzpService, MRPService mrpService, RefinancingBidsService refinancingBidsService) {
        this.mzpService = mzpService;
        this.mrpService = mrpService;
        this.refinancingBidsService = refinancingBidsService;
    }

    public void parsingBids(OfficialBidsEnumeration bids){
        /*
         * Method to parse official MRP, MZP using JSOUP
         */
        try {
            Document doc = Jsoup.connect("https://uchet.kz/stavki/"+ bids.getNameEn()).get();
            List<Element> list = doc.select("table").select("td").stream().filter(element -> element.text().startsWith(bids.getNameRu())).toList();

            HashMap<Integer, Integer> yearPriceMap = new HashMap<>();


            for (Element e : list) {
                Integer value = Integer.valueOf(e.text().split("-")[1].replaceAll("[^0-9]", ""));
                if (value>999999)
                    value = Integer.valueOf(value.toString().substring(0, value.toString().length()/2));
                yearPriceMap.put(Integer.valueOf(e.text().split(" ")[4]), value
                        );
            }
            switch (bids){
                case MZP -> mzpService.updateData(yearPriceMap);
                case MRP -> mrpService.updateData(yearPriceMap);
            }
        } catch (IOException e) {
            log.error("Jsoup " + bids.getNameEn() + " getting error: ", e);
        }
    }

    public void refinancingBids(){
        try {
            Document doc = Jsoup.connect("https://uchet.kz/stavki/index.php?ELEMENT_ID=17913").get();
            List<Element> list = doc.select("table").select("td");

            HashMap<LocalDate, Float> yearPriceMap = new HashMap<>();

            for (int i = 252; i < list.size(); i+=2) {
                if(!list.get(i).text().isEmpty() && list.get(i).text().matches("\\d{2}\\.\\d{2}\\.(\\d{4}|\\d{4}.+)")){
                    String date = list.get(i).text().substring(0, 10);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
                    yearPriceMap.put(LocalDate.parse(date, formatter),
                        Float.valueOf(list.get(i+2).text()
                                .replace("%", "")
                                .replace(",", ".")));
                }
            }
            refinancingBidsService.updateData(yearPriceMap);
        } catch (IOException e) {
            log.error("Jsoup refinancing bids throw error: ", e);
        }
    }

    @Scheduled(initialDelay = 2000000, fixedDelay = 1000 * 60 * 60 * 24 * 7 )
    public void updateAll(){
        parsingBids(OfficialBidsEnumeration.MRP);
        parsingBids(OfficialBidsEnumeration.MZP);
        refinancingBids();
    }
}
