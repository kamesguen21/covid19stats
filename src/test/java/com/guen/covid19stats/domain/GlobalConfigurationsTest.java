package com.guen.covid19stats.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.guen.covid19stats.web.rest.TestUtil;

public class GlobalConfigurationsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalConfigurations.class);
        GlobalConfigurations globalConfigurations1 = new GlobalConfigurations();
        globalConfigurations1.setId("id1");
        GlobalConfigurations globalConfigurations2 = new GlobalConfigurations();
        globalConfigurations2.setId(globalConfigurations1.getId());
        assertThat(globalConfigurations1).isEqualTo(globalConfigurations2);
        globalConfigurations2.setId("id2");
        assertThat(globalConfigurations1).isNotEqualTo(globalConfigurations2);
        globalConfigurations1.setId(null);
        assertThat(globalConfigurations1).isNotEqualTo(globalConfigurations2);
    }
}
