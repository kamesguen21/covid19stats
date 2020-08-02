package com.guen.covid19stats.service;

import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.service.dto.DailyCasesResponseDTO;

public interface Covid19apiService {
    DailyCasesResponseDTO[] getDailyCases(String countryCode);
}
