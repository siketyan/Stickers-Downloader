package me.siketyan.stickers_dl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class WebClient {
    public static String downloadString(URL u) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (InputStream in = u.openStream();
             InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        return sb.toString();
    }

    public static void downloadFile(URL u, Path p) throws IOException {
        try (InputStream in = u.openStream()) {
            Files.copy(in, p, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}