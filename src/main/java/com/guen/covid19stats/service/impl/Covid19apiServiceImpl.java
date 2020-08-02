package com.guen.covid19stats.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.service.Covid19apiService;
import com.guen.covid19stats.service.apiClient.ApiClient;
import com.guen.covid19stats.service.dto.DailyCasesResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class Covid19apiServiceImpl extends ApiClient implements Covid19apiService {
    private final Logger log = LoggerFactory.getLogger(Covid19apiServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DailyCasesResponseDTO[] getDailyCases(String countryCode) {
        log.info("requesting list of cases from day one for country {}", countryCode);
        return getMockDailyCases(countryCode);
        //return restTemplate.getForObject(getCovidApiBaseUrl() + DAY_ONE_PATH + countryCode, DailyCasesResponseDTO[].class);
    }

    public DailyCasesResponseDTO[] getMockDailyCases(String countryCode) {
        log.info("requesting list of cases from day one for country {}", countryCode);
        DailyCasesResponseDTO[] res = {};
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Reader reader;
            reader = new FileReader(new ClassPathResource("config/data/dailyCases.json").getFile());
            res = objectMapper.readValue(reader, DailyCasesResponseDTO[].class);
            List<DailyCasesResponseDTO> dailyCasesResponseDTOS = Arrays.asList(res);
            dailyCasesResponseDTOS = dailyCasesResponseDTOS.stream()
                .filter(p -> p.getCountryCode().equals(countryCode)).collect(Collectors.toList());
            res = new DailyCasesResponseDTO[]{};
            dailyCasesResponseDTOS.toArray(res); // TODO: this is not returning the array fix it
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
