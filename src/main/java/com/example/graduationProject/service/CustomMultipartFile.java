package com.example.graduationProject.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

public class CustomMultipartFile implements MultipartFile {

    private File file;

    public CustomMultipartFile(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        // Assuming it's a JPEG file for this example
        return "image/jpeg";
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[(int) file.length()];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (InputStream in = new FileInputStream(file);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }
    }
}
