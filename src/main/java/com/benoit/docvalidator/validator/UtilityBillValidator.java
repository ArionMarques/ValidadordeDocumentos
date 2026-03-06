package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;

public class UtilityBillValidator implements DocumentValidator {

    @Override
    public ValidationResult validate(String text) {
        // inicia o score em 0
        int score = 0;
        // verifica se o texto contém palavras-chave relacionadas a contas de serviços públicos e aumenta o score para cada palavra encontrada
        if (text.toLowerCase().contains("energia")) score += 10;
        if (text.toLowerCase().contains("instalação")) score += 10;
        if (text.toLowerCase().contains("vencimento")) score += 10;
        if (text.toLowerCase().contains("anterior")) score += 10;
        if (text.toLowerCase().contains("leitura")) score += 10;
        if (text.toLowerCase().contains("consumo")) score += 10;
        if (text.toLowerCase().contains("kwh")) score += 10;
        // se o score for maior ou igual a 80, retorna APPROVED, caso contrário, retorna MANUAL_REVIEW
        if (score >= 80) {
            return new ValidationResult("APPROVED", score, "Conta consistente");
        }

        return new ValidationResult("MANUAL_REVIEW", score, "Baixa confiabilidade");
    }
}