package com.benoit.docvalidator.core;

public record ValidationResult(
        ValidationStatus status,
        int score,
        String message
) {}