package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DecodedDocument;
import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;
import com.benoit.docvalidator.core.ValidationStatus;

public class UtilityBillValidator implements DocumentValidator {

    @Override
    public ValidationResult validate(DecodedDocument document) {

        String text = document.getRawData();

        if (text == null || text.isBlank()) {
            return new ValidationResult(
                    ValidationStatus.ERROR,
                    0,
                    "Texto OCR vazio"
            );
        }

        text = text.toLowerCase();

        int score = 0;

        if (text.contains("energia")) score += 10;
        if (text.contains("instalação")) score += 10;
        if (text.contains("vencimento")) score += 10;
        if (text.contains("anterior")) score += 10;
        if (text.contains("leitura")) score += 10;
        if (text.contains("consumo")) score += 10;
        if (text.contains("kwh")) score += 10;

        if (score >= 80) {
            return new ValidationResult(
                    ValidationStatus.VALID,
                    score,
                    "Conta consistente"
            );
        }

        return new ValidationResult(
                ValidationStatus.WARNING,
                score,
                "Baixa confiabilidade"
        );
    }
}