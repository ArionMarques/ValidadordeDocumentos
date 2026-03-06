package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;

public class RentReceiptValidator implements DocumentValidator {

    @Override
    public ValidationResult validate(String text) {
        // inicia o score em 0
        int score = 0;
        // verifica se o texto contém palavras-chave relacionadas a recibos de aluguel e aumenta o score para cada palavra encontrada
        if (text.toLowerCase().contains("aluguel")) score += 20;
        if (text.toLowerCase().contains("recibo")) score += 20;
        // se o score for maior ou igual a 40, retorna APPROVED, caso contrário, retorna MANUAL_REVIEW
        if (score >= 40) {
            return new ValidationResult("APPROVED", score, "Recibo consistente");
        }

        return new ValidationResult("MANUAL_REVIEW", score, "Baixa confiabilidade");
    }
}