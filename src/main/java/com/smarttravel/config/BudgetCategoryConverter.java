package com.smarttravel.config;

import com.smarttravel.model.Destination;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BudgetCategoryConverter implements AttributeConverter<Destination.BudgetCategory, String> {

    @Override
    public String convertToDatabaseColumn(Destination.BudgetCategory attribute) {
        return attribute != null ? attribute.name() : null;
    }

    @Override
    public Destination.BudgetCategory convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        // Normalize: strip hyphens/underscores/spaces then match case-insensitively
        String normalized = dbData.replace("-", "").replace("_", "").replace(" ", "");
        for (Destination.BudgetCategory cat : Destination.BudgetCategory.values()) {
            if (cat.name().equalsIgnoreCase(normalized)) return cat;
        }
        return null;
    }
}
