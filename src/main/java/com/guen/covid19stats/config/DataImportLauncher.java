package com.guen.covid19stats.config;

import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.domain.DailyCases;
import com.guen.covid19stats.service.CountryService;
import com.guen.covid19stats.service.Covid19apiService;
import com.guen.covid19stats.service.DailyCasesService;
import com.guen.covid19stats.service.dto.DailyCasesResponseDTO;
import com.guen.covid19stats.service.impl.DailyCasesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class DataImportLauncher {

    public static final String DAILY_CASE_IMPORT_EXPRESSION = "*/5 * * * * *";
    private final Logger log = LoggerFactory.getLogger(DataImportLauncher.class);

    @Autowired
    Covid19apiService covid19apiService;
    @Autowired
    CountryService countryService;
    @Autowired
    DailyCasesService dailyCasesService;

    @Scheduled(fixedRate = 50000)
    public void getDailyCases() {
        log.info("cron started at {}", Instant.now());
        List<Country> countries = countryService.findAll();
     for (Country country : countries) {
            DailyCasesResponseDTO[] dailyCasesResponseDTOS = covid19apiService.getDailyCases(country.getiSO2());
            DailyCases lastDailyCases = dailyCasesService.findLastByCountry(country);
            List<DailyCasesResponseDTO> list = Arrays.asList(dailyCasesResponseDTOS);
            List<DailyCases> dailyCases = DailyCasesService.mapDtosToEntities(list, country);
            if (lastDailyCases != null) {
                dailyCases = dailyCases.stream()
                    .filter(p -> p.getDate().compareTo(lastDailyCases.getDate()) > 0).collect(Collectors.toList());
                dailyCasesService.saveAll(dailyCases);
            } else {
                dailyCasesService.saveAll(dailyCases);
            }
        }
    }

}
