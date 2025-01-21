package com.fitoherb.fitoherb_backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class uploadUtil {

    public static boolean uploadImage(MultipartFile image, String path) {

        boolean flag = false;

        if(!image.isEmpty()) {
            String fileName = image.getOriginalFilename();

            try {
                File dir = new File(path);
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(image.getBytes());
                stream.close();
            }
            catch(Exception e) {
                System.out.println("Erro ao armazenar arquivo" + e);
            }
        }
        return flag;
    }

}
