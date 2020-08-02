package com.guen.covid19stats.config.dbmigrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guen.covid19stats.domain.Authority;
import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.domain.GlobalConfigurations;
import com.guen.covid19stats.domain.User;
import com.guen.covid19stats.security.AuthoritiesConstants;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;

import static com.guen.covid19stats.service.apiClient.ApiClientInterface.*;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "005")
public class InitialSetupMigration {
    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("en");
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(Instant.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("en");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(Instant.now());
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }

    @ChangeSet(order = "03", author = "kames", id = "03-addConfig")
    public void addConfig(MongoTemplate mongoTemplate) {
        GlobalConfigurations globalConfigurations = new GlobalConfigurations();
        globalConfigurations.setCode(COVID_API_CODE);
        globalConfigurations.setDiscription("COVID19 daily cases api");
        globalConfigurations.setHost("https://api.covid19api.com");
        globalConfigurations.setName("Coronavirus COVID19 API");
        globalConfigurations.setId(COVID_API_CODE);
        mongoTemplate.save(globalConfigurations);
        globalConfigurations = new GlobalConfigurations();
        globalConfigurations.setCode(WHO_NEWS_API_CODE);
        globalConfigurations.setDiscription("WHO Disease Outbreak News");
        globalConfigurations.setHost("https://www.who.int/feeds/entity/csr/don/en/rss.xml");
        globalConfigurations.setName("WHO Disease Outbreak News");
        globalConfigurations.setId(WHO_NEWS_API_CODE);
        mongoTemplate.save(globalConfigurations);
        globalConfigurations = new GlobalConfigurations();
        globalConfigurations.setCode(CDC_NEWS_API_CODE);
        globalConfigurations.setDiscription("The Centers for Disease Control and Prevention (CDC) is closely monitoring an outbreak of respiratory illness caused by a novel (new) coronavirus first identified in Wuhan, Hubei Province, China.");
        globalConfigurations.setHost("https://tools.cdc.gov/api/v2/resources/media/403372.rss");
        globalConfigurations.setName("2019 Novel Coronavirus");
        globalConfigurations.setId(CDC_NEWS_API_CODE);
        mongoTemplate.save(globalConfigurations);

    }

    @ChangeSet(order = "09", author = "kames", id = "09-addCountries")
    public void addCountries(MongoTemplate mongoTemplate) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Reader reader = new FileReader(new ClassPathResource("config/data/countries.json").getFile());
            Country[] countries = objectMapper.readValue(reader, Country[].class);
            for (Country country : countries) {
                country.setId(country.getiSO2());
                mongoTemplate.save(country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
