package com.benoit.docvalidator.core;

import java.io.File;
import java.io.FileWriter;

public class OcrTextSaver {

    // Método estático para salvar o texto OCR em um arquivo
    public static void saveOcrText(String originalFilePath, String text) throws Exception {

        // Cria um objeto File para o arquivo original
        File originalFile = new File(originalFilePath);

        // Obtém o nome base do arquivo, removendo a extensão
        String baseName = originalFile
                .getName()
                .replaceFirst("[.][^.]+$", "");

        // Cria um novo arquivo de saída no mesmo diretório, com "_ocr.txt" adicionado ao nome base
        File outputFile = new File(
                originalFile.getParent(),
                baseName + "_ocr.txt"
        );

        // Usa try-with-resources para escrever o texto no arquivo de saída
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(text);
        }

        // Imprime o caminho absoluto do arquivo salvo
        System.out.println("OCR salvo em: " + outputFile.getAbsolutePath());
    }
}