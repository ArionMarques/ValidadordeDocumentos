package com.benoit.docvalidator.qrcode;

import java.awt.image.BufferedImage;
import java.util.Optional;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

public class ZxingQrCodeExtractor implements QrCodeExtractor {

    @Override
    public Optional<String> extract(BufferedImage image) {

        try {

            LuminanceSource source =
                    new BufferedImageLuminanceSource(image);

            BinaryBitmap bitmap =
                    new BinaryBitmap(new HybridBinarizer(source));

            Result result =
                    new MultiFormatReader().decode(bitmap);

            byte[] raw = result.getRawBytes();

            if (raw == null || raw.length == 0) {
                return Optional.empty();
            }

            StringBuilder hex = new StringBuilder();

            for (byte b : raw) {
                hex.append(String.format("%02x", b));
            }

            String payload = hex.toString();

            /*
             * ZXing pode incluir bytes extras antes do payload real.
             * O payload da CNH começa com ASN.1 DER: 63 82 ...
             */
            int start = payload.indexOf("6382");

            if (start > 0) {
                payload = payload.substring(start);
            }

            /*
             * Remove padding padrão do QRCode (0xEC 0x11).
             * Muitos scanners removem automaticamente.
             */
            while (payload.endsWith("ec11")) {
                payload = payload.substring(0, payload.length() - 4);
            }

            return Optional.of(payload);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}