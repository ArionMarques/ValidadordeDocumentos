package com.benoit.docvalidator.qrcode;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ZxingQrCodeExtractor implements QrCodeExtractor {

    @Override
    public Optional<String> extract(BufferedImage image) {
        // usar ZXing para ler o QR Code e extrair o payload em formato HEX
        try {
            // criar um BinaryBitmap a partir da imagem
            LuminanceSource source =
                    new BufferedImageLuminanceSource(image);
            // usar HybridBinarizer para melhorar a leitura do QR Code
            BinaryBitmap bitmap =
                    new BinaryBitmap(new HybridBinarizer(source));
            // tentar decodificar o QR Code
            Result result =
                    new MultiFormatReader().decode(bitmap);
            // extrair os segmentos de bytes do resultado
            Map<ResultMetadataType, Object> metadata =
                    result.getResultMetadata();
            // verificar se os segmentos de bytes estão presentes
            if (metadata == null) {
                return Optional.empty();
            }
            // os segmentos de bytes devem conter o payload do QR Code em formato binário
            @SuppressWarnings("unchecked")
            List<byte[]> segments =
                    (List<byte[]>) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
            // garantir que os segmentos de bytes estejam presentes e não estejam vazios
            if (segments == null || segments.isEmpty()) {
                return Optional.empty();
            }
            // o payload do QR Code é o primeiro segmento de bytes
            byte[] payload = segments.get(0);
            // converter o payload binário para uma string hexadecimal
            StringBuilder hex = new StringBuilder();
            // iterar sobre os bytes do payload e convertê-los para hexadecimal
            for (byte b : payload) {
                hex.append(String.format("%02x", b));
            }
            // retornar o payload em formato hexadecimal
            return Optional.of(hex.toString());

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}