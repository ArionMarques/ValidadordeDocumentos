package com.benoit.docvalidator.core;

public record ValidationResult(
        String status,
        int score,
        String message
) {}