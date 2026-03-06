package com.benoit.docvalidator;

import java.io.File;

import com.benoit.docvalidator.core.DocumentType;
import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.OcrTextSaver;
import com.benoit.docvalidator.core.ValidationResult;
import com.benoit.docvalidator.ocr.OcrService;
import com.benoit.docvalidator.validator.PayslipValidator;
import com.benoit.docvalidator.validator.RentReceiptValidator;
import com.benoit.docvalidator.validator.UtilityBillValidator;

public class Main {

    public static void main(String[] args) throws Exception {
        // Verifica se os argumentos necessários foram fornecidos
        if (args.length < 2) {
            System.out.println("Uso: java -jar document-validator.jar <arquivo> <tipo>");
            return;
        }
        // Obtém o caminho do arquivo e o tipo de documento a partir dos argumentos
        String filePath = args[0];
        DocumentType type = DocumentType.from(args[1]);
        // Cria uma instância do serviço OCR e extrai o texto do arquivo fornecido
        OcrService ocrService = new OcrService("C:/dev/softwares/Tesseract-OCR/tessdata");
        String text = ocrService.extractText(new File(filePath));
        OcrTextSaver.saveOcrText(filePath, text);
        
        // Seleciona o validador apropriado com base no tipo de documento e valida o texto extraído
        DocumentValidator validator = switch (type) {
            case PAYSLIP -> new PayslipValidator();
            case UTILITY_BILL -> new UtilityBillValidator();
            case RENT_RECEIPT -> new RentReceiptValidator();
        };
        // Realiza a validação e imprime os resultados
        ValidationResult result = validator.validate(text);

        System.out.println("Status: " + result.status());
        System.out.println("Score: " + result.score());
        System.out.println("Mensagem: " + result.message());
    }
}