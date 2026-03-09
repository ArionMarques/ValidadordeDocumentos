package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DecodedDocument;
import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;
import com.benoit.docvalidator.core.ValidationStatus;

public class RentReceiptValidator implements DocumentValidator {

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

        if (text.contains("aluguel")) score += 20;
        if (text.contains("recibo")) score += 20;

        if (score >= 40) {
            return new ValidationResult(
                    ValidationStatus.VALID,
                    score,
                    "Recibo consistente"
            );
        }

        return new ValidationResult(
                ValidationStatus.WARNING,
                score,
                "Baixa confiabilidade"
        );
    }
}