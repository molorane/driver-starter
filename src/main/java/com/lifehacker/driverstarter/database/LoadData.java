package com.lifehacker.driverstarter.database;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class LoadData {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = Logger.getLogger(String.valueOf(LoadData.class));

    @PostConstruct
    public void init() {
        loadUsers(jdbcTemplate);
    }

    private void loadUsers(final JdbcTemplate jdbcTemplate) {
        ClassPathResource classPathResource = new ClassPathResource("data/users.csv", this.getClass().getClassLoader());
        Path path = null;
        try {
            logger.info("Reading users data..");
            path = Path.of(classPathResource.getFile().toURI());
        } catch (IOException e) {
            logger.warning(String.format("Error %s", e.getMessage()));
        }

        logger.info("Loading users..");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] user = line.split(",");
                jdbcTemplate.execute(String.format("INSERT INTO gdpuser(name,age) VALUES('%s','%s')", user[0], user[1]));
            });
            logger.info("Loading users complete..");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
