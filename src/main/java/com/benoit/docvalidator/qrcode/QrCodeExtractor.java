package com.benoit.docvalidator.qrcode;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface QrCodeExtractor {

    Optional<String> extract(BufferedImage image);

}