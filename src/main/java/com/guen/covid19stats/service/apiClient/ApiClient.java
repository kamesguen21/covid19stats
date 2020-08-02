package com.guen.covid19stats.service.apiClient;

import com.guen.covid19stats.domain.GlobalConfigurations;
import com.guen.covid19stats.service.Covid19apiService;
import com.guen.covid19stats.service.GlobalConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ApiClient implements ApiClientInterface {
    @Autowired
    GlobalConfigurationsService globalConfigurationsService;
    private String Host = null;

    protected String getCovidApiBaseUrl() {
        if (Host == null) {
            Optional<GlobalConfigurations> globalConfiguration = this.globalConfigurationsService.findOne(COVID_API_CODE);
            globalConfiguration.ifPresent(globalConfigurations -> Host = globalConfigurations.getHost());
        }
        return Host;
    }
}
