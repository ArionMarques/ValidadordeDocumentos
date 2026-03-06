package com.benoit.docvalidator.validator;

import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.ValidationResult;

public class PayslipValidator implements DocumentValidator {

    @Override
    public ValidationResult validate(String text) {
        // inicia o score em 0
        int score = 0;
        // verifica se o texto contém palavras-chave relacionadas a holerites e aumenta o score para cada palavra encontrada
        if (text.toLowerCase().contains("holerite")) score += 10;
        if (text.toLowerCase().contains("salário")) score += 10;
        if (text.toLowerCase().contains("inss")) score += 10;
        if (text.toLowerCase().contains("salario base")) score += 10;
        if (text.toLowerCase().contains("referência")) score += 10;
        if (text.toLowerCase().contains("pagamento")) score += 10;
        if (text.toLowerCase().contains("descanso")) score += 10;
        if (text.toLowerCase().contains("inss")) score += 10;
        if (text.toLowerCase().contains("descanso")) score += 10;
        if (text.toLowerCase().contains("descontos")) score += 10;
        // se o score for maior ou igual a 80, retorna APPROVED, caso contrário, retorna MANUAL_REVIEW
        if (score >= 80) {
            return new ValidationResult("APPROVED", score, "Documento consistente");
        }

        return new ValidationResult("MANUAL_REVIEW", score, "Baixa confiabilidade");
    }
}