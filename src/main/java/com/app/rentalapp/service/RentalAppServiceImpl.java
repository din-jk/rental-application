package com.app.rentalapp.service;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.app.rentalapp.models.RentalAgreement;
import com.app.rentalapp.models.Tool;
import com.app.rentalapp.repository.ToolRepository;

@Service
public class RentalAppServiceImpl implements RentalAppService {
    
    ToolRepository toolRepository;

    public RentalAppServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    /**
     * Retrieves all tools from the database.
     */
    @Override
    public List<Tool> getAllTools() {
        List<Tool> tools = new ArrayList<>();
        toolRepository.findAll().forEach(tools::add);
        return tools;
    }
    
    /**
     * Retrieves a tool by its ID.
     */
    @Override
    public Tool getToolById(Long id) {
        return toolRepository.findById(id).get();
    }
    
    /**
     * Retrieves a tool by its code.
     */
    @Override
    public Tool getToolByCode(String code) {
        List<Tool> tools = toolRepository.findByCode(code);
        if(tools.size() > 0) {
            return tools.get(0);
        }
        return null;        
    }
    
    /**
     * Inserts a new tool into the database.
     */
    @Override
    public Tool saveTool(Tool tool) {
        tool.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return toolRepository.save(tool);
    }
    
    /**
     * Updates an existing tool in the database.
     */
    @Override
    public void updateTool(Long id, Tool tool) {
        Tool toolFromDb = toolRepository.findById(id).get();
        toolFromDb.setBrand(tool.getBrand());
        toolFromDb.setType(tool.getType());
        toolFromDb.setDailyCharge(tool.getDailyCharge());
        toolFromDb.setWeekendChargeable(tool.isWeekendChargeable());
        toolFromDb.setWeekdayChargeable(tool.isWeekdayChargeable());
        toolFromDb.setHolidayChargeable(tool.isHolidayChargeable());
        toolFromDb.setLastModified(new Timestamp(System.currentTimeMillis()));
        toolRepository.save(toolFromDb);
    }
    
    /**
     * Deletes a tool from the database by its ID.
     */
    @Override
    public void deleteTool(Long toolId) {
        toolRepository.deleteById(toolId);
    }

    // Private methods for internal use in checkoutTool

    /**
     * Checks if a given date is a holiday.
     */
    private boolean isHoliday(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4); // Independence Day
        LocalDate nextDay = date.plusDays(1);
        LocalDate previousDay = date.minusDays(1);
        boolean isAdditionalHoliday = false;
        if (nextDay.equals(independenceDay) && independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            isAdditionalHoliday = true;
        }
        
        if (previousDay.equals(independenceDay) && independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            isAdditionalHoliday = true;
        }
        
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1).with(firstInMonth(DayOfWeek.MONDAY));
        return date.equals(independenceDay) || date.equals(laborDay) || isAdditionalHoliday;
    }

    /**
     * Checks if a given date is a weekend.
     */
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * Checks if a date is chargeable based on tool's settings.
     */
    private boolean isDateChargeable(LocalDate date, Tool tool) {       
        if (isWeekend(date) && !tool.isWeekendChargeable()) {
            return false;
        }
        if (isHoliday(date) && !tool.isHolidayChargeable()) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the number of chargeable days between two dates.
     */
    private int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool tool) {
        int chargeDays = 0;
        for (LocalDate date = checkoutDate.plusDays(1); date.isBefore(dueDate.plusDays(1)); date = date.plusDays(1)) {
            if (isDateChargeable(date, tool)) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    /**
     * Calculates the rental charge before discount.
     */
    private double calculatePreDiscountCharge(int noOfChargeDays, double dailyRentalCharge) {
        return noOfChargeDays * dailyRentalCharge;
    }

    /**
     * Calculates the discount amount based on pre-discount charge and discount percentage.
     */
    private double calculateDiscountAmount(int discountPercentage, double preDiscountCharge) {
        return (discountPercentage / 100.0) * preDiscountCharge;
    }

    /**
     * Calculates the final charge after applying discounts.
     */
    private double calculateFinalCharge(double preDiscountCharge, double discountAmount) {
        return preDiscountCharge - discountAmount;
    }

    /**
     * Checks out a tool, calculates rental charges, applies discounts, and prints the rental agreement.
     */
    @Override
    public RentalAgreement checkoutTool(String toolCode, int rentalDays, int discountPercentage, LocalDate checkoutDate) {
            Tool tool = getToolByCode(toolCode);
            if(!ObjectUtils.isEmpty(tool)) {
                LocalDate dueDate = checkoutDate.plusDays(rentalDays);
                int noOfChargeDays = calculateChargeDays(checkoutDate, dueDate, tool);
                double preDiscountCharge = calculatePreDiscountCharge(noOfChargeDays, tool.getDailyCharge());
                double discountAmount = calculateDiscountAmount(discountPercentage, preDiscountCharge);
                double finalCharge = calculateFinalCharge(preDiscountCharge, discountAmount);
                
                RentalAgreement rentalAgreement = new RentalAgreement(tool.getCode(), tool.getType(), tool.getBrand(), 
                        rentalDays, discountPercentage, checkoutDate, dueDate, tool.getDailyCharge(), noOfChargeDays,
                        preDiscountCharge, discountAmount, finalCharge);
                rentalAgreement.printAgreement();
                return rentalAgreement;
                
            } else {
                System.out.println("Tool not found for the given code");
            }
        return null;
    }

    /**
     * Validates the inputs for checkoutTool method.
     */
    @Override
    public String validateInputs(int rentalDays, int discountPercentage) {
         if (rentalDays < 1) { // Rental day should be greater than 0
                return "Please rent the tool for at least one day";
            }
            if (discountPercentage < 0 || discountPercentage > 100) { // Discount % should be between 0 & 100
                return "Discount percentage should be between the range of 0% and 100%";
            }
            return null;
    }

}
