package com.qrcode.qrcode.controller;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    @GetMapping(value = "/generate",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generate() throws IOException {
        ByteArrayOutputStream stream = QRCode.from("http://www.baidu.com")
                .to(ImageType.PNG)
                .withSize(256,256)//大小
                .withErrorCorrection(ErrorCorrectionLevel.H)//容错
                .stream();
        byte[] bytes = stream.toByteArray();

        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

        BufferedImage read = ImageIO.read(new ByteArrayInputStream(bytes));
        BufferedImage read1 = ImageIO.read(new File("warriorlogosmall.jpg"));
        BufferedImage bufferedImage = Thumbnails.of(read)
                .size(200, 200)
                .watermark(Positions.CENTER, read1, 1.0f)
                .asBufferedImage();
        ImageIO.write(bufferedImage,"jpg",stream1);
        byte[] bytes1 = stream1.toByteArray();

        return bytes1;
    }
}
