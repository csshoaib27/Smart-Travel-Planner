package com.smarttravel.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@RequiredArgsConstructor
@Slf4j
public class DataFixSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        int fixed = jdbcTemplate.update(
                "UPDATE destinations SET budget_category = 'MidRange' WHERE budget_category = 'Mid-Range'");
        int empties = jdbcTemplate.update(
                "UPDATE destinations SET budget_category = 'Budget' WHERE budget_category IS NULL OR budget_category = ''");
        int total = fixed + empties;
        if (total > 0) {
            log.info("DataFixSeeder: corrected {} destination row(s) with invalid budget_category values", total);
        }
    }
}
