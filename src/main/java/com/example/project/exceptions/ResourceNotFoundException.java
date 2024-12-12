package com.example.project.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String field;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(resourceName + " not found with " + field + ": " + fieldName );
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(resourceName + " not found with " + field + ": " + fieldId );
        this.resourceName = resourceName;
        this.fieldId = fieldId;
        this.field = field;
    }
}
