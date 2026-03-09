package com.benoit.docvalidator.pdf;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfImageExtractor {

    public BufferedImage extract(File pdf) {

        try (PDDocument document = Loader.loadPDF(pdf);) {  

            PDFRenderer renderer = new PDFRenderer(document);

            return renderer.renderImageWithDPI(0, 300);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}