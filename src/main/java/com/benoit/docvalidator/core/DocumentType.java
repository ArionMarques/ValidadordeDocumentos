package com.benoit.docvalidator.core;

public enum DocumentType {
    PAYSLIP,
    UTILITY_BILL,
    RENT_RECEIPT;

    public static DocumentType from(String value) {
        return DocumentType.valueOf(value.toUpperCase());
    }
}