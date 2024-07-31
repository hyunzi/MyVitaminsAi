package com.mva.api.myvitamin.service.impl;


import com.mva.api.myvitamin.service.Base64ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class Base64ImageServiceImpl implements Base64ImageService {
    public String getBase64Image(String imgUrl) {
        String base64Image = "";
        try {
            URL url = new URL(imgUrl);

            BufferedImage image = ImageIO.read(url);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();

            base64Image = Base64.getEncoder().encodeToString(imageInBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64Image;
    }
}
