package com.guen.covid19stats.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.guen.covid19stats.web.rest.TestUtil;

public class DailyCasesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyCases.class);
        DailyCases dailyCases1 = new DailyCases();
        dailyCases1.setId("id1");
        DailyCases dailyCases2 = new DailyCases();
        dailyCases2.setId(dailyCases1.getId());
        assertThat(dailyCases1).isEqualTo(dailyCases2);
        dailyCases2.setId("id2");
        assertThat(dailyCases1).isNotEqualTo(dailyCases2);
        dailyCases1.setId(null);
        assertThat(dailyCases1).isNotEqualTo(dailyCases2);
    }
}
