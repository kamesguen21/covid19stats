package com.guen.covid19stats.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.guen.covid19stats.web.rest.TestUtil;

public class CdcNewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CdcNews.class);
        CdcNews cdcNews1 = new CdcNews();
        cdcNews1.setId("id1");
        CdcNews cdcNews2 = new CdcNews();
        cdcNews2.setId(cdcNews1.getId());
        assertThat(cdcNews1).isEqualTo(cdcNews2);
        cdcNews2.setId("id2");
        assertThat(cdcNews1).isNotEqualTo(cdcNews2);
        cdcNews1.setId(null);
        assertThat(cdcNews1).isNotEqualTo(cdcNews2);
    }
}
