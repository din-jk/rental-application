package com.app.rentalapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.rentalapp.models.RentalAgreement;
import com.app.rentalapp.models.Tool;
import com.app.rentalapp.requestbody.CheckOutToolRequestBody;
import com.app.rentalapp.service.RentalAppService;

@RestController
@RequestMapping("/api/v1/tool")
public class ToolController {

    RentalAppService toolRentalService;

    public ToolController(RentalAppService toolRentalService) {
        this.toolRentalService = toolRentalService;
    }

    // Retrieve all tools
    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        List<Tool> tools = toolRentalService.getAllTools();
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    // Retrieve a tool by its ID
    @GetMapping({ "/{toolId}" })
    public ResponseEntity<Tool> getToolById(@PathVariable Long toolId) {
        return new ResponseEntity<>(toolRentalService.getToolById(toolId), HttpStatus.OK);
    }

    // Retrieve a tool by its code
    @GetMapping({ "/code/{toolCode}" })
    public ResponseEntity<Tool> getToolByCode(@PathVariable String toolCode) {
        return new ResponseEntity<>(toolRentalService.getToolByCode(toolCode), HttpStatus.OK);
    }

    // Create a new tool
    @PostMapping
    public ResponseEntity<Tool> saveTool(@RequestBody Tool tool) {
        Tool createdTool = toolRentalService.saveTool(tool);
        return new ResponseEntity<>(createdTool, HttpStatus.CREATED);
    }

    // Update a tool by its ID
    @PutMapping({ "/{toolId}" })
    public ResponseEntity<Tool> updateTool(@PathVariable("toolId") Long toolId, @RequestBody Tool tool) {
        toolRentalService.updateTool(toolId, tool);
        return new ResponseEntity<>(toolRentalService.getToolById(toolId), HttpStatus.OK);
    }

    // Delete a tool by its ID
    @DeleteMapping({ "/{toolId}" })
    public ResponseEntity<Void> deleteTool(@PathVariable("toolId") Long toolId) {
        toolRentalService.deleteTool(toolId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Checkout a tool
    @PostMapping("/checkoutTool")
    public ResponseEntity<?> checkoutTool(@RequestBody final CheckOutToolRequestBody checkOutToolRequestBody) {
        String validationMessage = toolRentalService.validateInputs(checkOutToolRequestBody.getRentalDays(), checkOutToolRequestBody.getDiscountPercentage());
        if (validationMessage == null) {
            RentalAgreement agreement = toolRentalService.checkoutTool(
                    checkOutToolRequestBody.getToolCode(), 
                    checkOutToolRequestBody.getRentalDays(), 
                    checkOutToolRequestBody.getDiscountPercentage(), 
                    checkOutToolRequestBody.getCheckoutDate());
            return new ResponseEntity<>(agreement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(validationMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
