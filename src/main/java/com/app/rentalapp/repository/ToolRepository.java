package com.app.rentalapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.rentalapp.models.Tool;

/**
 * Repository interface for accessing Tool entities in the database.
 */
@Repository
public interface ToolRepository extends CrudRepository<Tool, Long> {
    
    /**
     * Retrieves tools by their code.
     * 
     * @param code The code of the tool
     * @return List of tools with the specified code
     */
    List<Tool> findByCode(String code);

}
