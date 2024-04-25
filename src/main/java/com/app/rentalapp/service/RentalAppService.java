package com.app.rentalapp.service;

import java.time.LocalDate;
import java.util.List;

import com.app.rentalapp.models.RentalAgreement;
import com.app.rentalapp.models.Tool;

/**
 * Service interface defining operations related to tool rental management.
 */
public interface RentalAppService {
    
    List<Tool> getAllTools();

    Tool getToolById(Long id);
    
    Tool getToolByCode(String code);

    Tool saveTool(Tool tool);

    void updateTool(Long id, Tool tool);

    void deleteTool(Long toolId);

    RentalAgreement checkoutTool(String toolCode, int rentalDays, int discountPercentage, LocalDate checkoutDate);
    
    String validateInputs(int rentalDays, int discountPercentage);

}
