package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DecodedDocument;
import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;
import com.benoit.docvalidator.core.ValidationStatus;

public class PayslipValidator implements DocumentValidator {

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

        if (text.contains("holerite")) score += 10;
        if (text.contains("salário")) score += 10;
        if (text.contains("inss")) score += 10;
        if (text.contains("salario base")) score += 10;
        if (text.contains("referência")) score += 10;
        if (text.contains("pagamento")) score += 10;
        if (text.contains("descanso")) score += 10;
        if (text.contains("descontos")) score += 10;

        if (score >= 80) {
            return new ValidationResult(
                    ValidationStatus.VALID,
                    score,
                    "Documento consistente"
            );
        }

        return new ValidationResult(
                ValidationStatus.WARNING,
                score,
                "Baixa confiabilidade"
        );
    }
}