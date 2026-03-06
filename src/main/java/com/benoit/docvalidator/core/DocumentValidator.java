package com.benoit.docvalidator.core;

public interface DocumentValidator {
    ValidationResult validate(String text);
}