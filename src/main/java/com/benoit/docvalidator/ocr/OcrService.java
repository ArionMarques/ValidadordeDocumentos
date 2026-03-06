package com.benoit.docvalidator.ocr;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class OcrService {

    private final ITesseract tesseract;

    public OcrService(String tessdataPath) {
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath(tessdataPath);
        this.tesseract.setLanguage("por");
    }

    public String extractText(File file) throws Exception {
        return tesseract.doOCR(file);
    }
}