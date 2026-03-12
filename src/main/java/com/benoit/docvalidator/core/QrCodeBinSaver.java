package com.benoit.docvalidator.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QrCodeBinSaver {

    public static void save(String hexPayload, String originalFile) throws IOException {

         // garantir que o HEX tenha tamanho par
        if (hexPayload.length() % 2 != 0) {
            hexPayload = hexPayload.substring(0, hexPayload.length() - 1);
        }

        // converter a string hexadecimal de volta para bytes
        byte[] bytes = hexStringToByteArray(hexPayload);
        // gerar um nome de arquivo de saída
        String outputName = "Claudio.bin";
        // criar diretório de saída se não existir
        File outputDir = new File("qrcode-output");
        outputDir.mkdirs();
        // criar o arquivo de saída
        File outputFile = new File(outputDir, outputName);
        //  escrever os bytes no arquivo de saída
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(bytes);
        }
        // informar o usuário sobre o arquivo gerado
        System.out.println("Arquivo BIN gerado: " + outputFile.getAbsolutePath());
    }
// método auxiliar para converter uma string hexadecimal em um array de bytes
    private static byte[] hexStringToByteArray(String s) {

        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte) ((Character.digit(s.charAt(i), 16) << 4)
                            + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }
}