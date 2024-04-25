package com.app.rentalapp.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.rentalapp.models.Tool;
import com.app.rentalapp.repository.ToolRepository;

/**
 * Initializes tool data upon application startup.
 */
@Component
public class ToolData implements CommandLineRunner {
	
    // Tool repository for interacting with the database
	public final ToolRepository toolRepository;

    /**
     * Constructor for ToolData.
     * @param toolRepository The tool repository to use for database interaction.
     */
    public ToolData(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    /**
     * Method called upon application startup.
     */
    @Override
    public void run(String... args) throws Exception {
        loadTools();
    }

    /**
     * Loads sample tools into the database if no tools exist.
     */
    private void loadTools() {
        if (toolRepository.count() == 0) {
            // Load sample tools if the database is empty
        	toolRepository.save(
                    Tool.builder()
                            .code("CHNS")
                            .type("Chainsaw")
                            .brand("Stihl")
                            .dailyCharge(1.49)
                            .isWeekdayChargeable(true)
                            .isWeekendChargeable(false)
                            .isHolidayChargeable(true)
                            .build()
            );
        	toolRepository.save(
                    Tool.builder()
                            .code("LADW")
                            .type("Ladder")
                            .brand("Werner")
                            .dailyCharge(1.99)
                            .isWeekdayChargeable(true)
                            .isWeekendChargeable(true)
                            .isHolidayChargeable(false)
                            .build()
            );
        	toolRepository.save(
                    Tool.builder()
                            .code("JAKD")
                            .type("Jackhammer")
                            .brand("DeWalt")
                            .dailyCharge(2.99)
                            .isWeekdayChargeable(true)
                            .isWeekendChargeable(false)
                            .isHolidayChargeable(false)
                            .build()
            );
        	toolRepository.save(
                    Tool.builder()
                            .code("JAKR")
                            .type("Ridgid")
                            .brand("DeWalt")
                            .dailyCharge(2.99)
                            .isWeekdayChargeable(true)
                            .isWeekendChargeable(false)
                            .isHolidayChargeable(false)
                            .build()
            );
            System.out.println("Tools Data Loaded Successfully!");
        }
    }

}
