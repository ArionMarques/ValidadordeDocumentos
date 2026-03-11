package com.benoit.docvalidator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import com.benoit.docvalidator.core.DecodedDocument;
import com.benoit.docvalidator.core.DocumentType;
import com.benoit.docvalidator.core.DocumentValidator;
import com.benoit.docvalidator.core.OcrTextSaver;
import com.benoit.docvalidator.core.QrCodeBinSaver;
import com.benoit.docvalidator.core.ValidationResult;
import com.benoit.docvalidator.ocr.OcrService;
import com.benoit.docvalidator.pdf.PdfImageExtractor;
import com.benoit.docvalidator.qrcode.QrCodeExtractor;
import com.benoit.docvalidator.qrcode.ZxingQrCodeExtractor;
import com.benoit.docvalidator.validator.PayslipValidator;
import com.benoit.docvalidator.validator.RentReceiptValidator;
import com.benoit.docvalidator.validator.UtilityBillValidator;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Uso: java -jar document-validator.jar <arquivo> <tipo>");
            return;
        }

        String filePath = args[0];
        DocumentType type = DocumentType.from(args[1]);

        File file = new File(filePath);

        // ===== 1️⃣ Tenta ler QRCode se documento dor CNH =====

        if (type == DocumentType.CNH) {

            PdfImageExtractor pdfExtractor = new PdfImageExtractor();
            BufferedImage image = pdfExtractor.extract(file);

            QrCodeExtractor qrExtractor = new ZxingQrCodeExtractor();
            Optional<String> payload = qrExtractor.extract(image);

           if (payload.isPresent()) {

    String qrPayload = payload.get();

    System.out.println("QR Code encontrado no documento:");
    System.out.println(qrPayload);

    // gerar arquivo .bin para uso no VIO
    QrCodeBinSaver.save(qrPayload, filePath);

    return;

} else {
    System.out.println("QR Code não encontrado, tentando OCR...");
}
        }

        // ===== 2️⃣ OCR =====

        OcrService ocrService = new OcrService("C:/dev/softwares/Tesseract-OCR/tessdata");
        String text = ocrService.extractText(file);
        // salva o resultado do OCR em TXT
        OcrTextSaver.saveOcrText(filePath, text);

        // ===== 3️⃣ Documento =====

        DecodedDocument document = new DecodedDocument();
        document.setType(type);
        document.setRawData(text);

        // ===== 4️⃣ Validator =====

        DocumentValidator validator = switch (type) {
            case PAYSLIP -> new PayslipValidator();
            case UTILITY_BILL -> new UtilityBillValidator();
            case RENT_RECEIPT -> new RentReceiptValidator();
            default -> throw new IllegalArgumentException("Tipo de documento não suportado: " + type);
        };

        ValidationResult result = validator.validate(document);

        // ===== 5️⃣ Resultado =====

        System.out.println("Status: " + result.status());
        System.out.println("Score: " + result.score());
        System.out.println("Mensagem: " + result.message());
    }
}