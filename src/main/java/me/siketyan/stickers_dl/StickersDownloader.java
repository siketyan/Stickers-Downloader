package me.siketyan.stickers_dl;

import me.siketyan.stickers_dl.enums.APIStatus;
import me.siketyan.stickers_dl.objects.APIResponse;
import me.siketyan.stickers_dl.objects.Sticker;
import me.siketyan.stickers_dl.utils.WebClient;
import net.arnx.jsonic.JSON;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StickersDownloader {
    private static final String API_URL = "https://siketyan.azurewebsites.net/sticker/api/";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar " + new File(System.getProperty("java.class.path")).getAbsoluteFile().getName() + " [ Sticker Set ID | Store URL ]");
            return;
        }

        List<String> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);

        long id = formatId(args[0]);
        if (id < 0) {
            System.out.println("Invalid ID was specified.");
            return;
        }

        System.out.println("Getting the stickers' information.");
        String json;
        try {
            URL url = new URL(API_URL + id);
            json = WebClient.downloadString(url);
        } catch (Exception e) {
            System.out.println("Failed to get the information.");
            return;
        }

        System.out.println(json);

        APIResponse res = JSON.decode(json, APIResponse.class);
        if (res.getStatus() != APIStatus.ok) {
            System.out.println("An error occurred on API: " + res.getError());
            return;
        }

        System.out.println();
        System.out.println("Stickers' information:");
        System.out.println("Title: " + res.getTitle());
        System.out.println("Author: " + res.getAuthor());
        System.out.println("Description: " + res.getDescription());
        System.out.println("Price: \\" + res.getPrice());
        System.out.println("Stickers Count: " + res.getStickers().length);

        if (res.hasSound()) System.out.print("[Sound] ");
        System.out.println((res.isOfficial() ? "[Official]" : "[Creator's]") + "\n");

        if (!argsList.contains("-y")) {
            System.out.println("Continue? - (Y)es / (N)o [Default]");

            char key = (char)-1;
            try {
                key = (char)System.in.read();
            } catch (Exception e) {
                System.out.println("Failed to get a inputted key.");
            }

            if (key != 'y' && key != 'Y') {
                System.out.println("Aborted.");
                return;
            }

            System.out.print("\n");
        }

        File imagesPath = new File("stickers/" + id + "/images");
        if (!imagesPath.exists() || !imagesPath.isDirectory()) {
            imagesPath.mkdirs();
        }

        File audioPath = new File("stickers/" + id + "/audio");
        if (res.hasSound() && (!audioPath.exists() || !audioPath.isDirectory())) {
            audioPath.mkdirs();
        }

        for (Sticker s : res.getStickers()) {
            String stickerId = Long.toString(s.getId());

            System.out.append("Downloading Sticker: ").print(stickerId);

            System.out.print("   Image...");
            File image = new File(imagesPath, stickerId.concat(".png"));
            if (image.exists()) {
                System.out.print("Skipped");
            } else {
                try {
                    WebClient.downloadFile(new URL(s.getImageUrl()), image.toPath());
                } catch (MalformedURLException e) {
                    System.out.println("Invalid URL was provided.");
                    return;
                } catch (IOException e) {
                    System.out.println("Failed to download the file.");
                    return;
                }

                System.out.print("OK");
            }

            if (s.getAudioUrl() != null) {
                System.out.print("   Audio...");
                File audio = new File(audioPath, stickerId.concat(".m4a"));
                if (audio.exists()) {
                    System.out.print("Skipped");
                } else {
                    try {
                        WebClient.downloadFile(new URL(s.getAudioUrl()), audio.toPath());
                    } catch (MalformedURLException e) {
                        System.out.println("Invalid URL was provided.");
                        return;
                    } catch (IOException e) {
                        System.out.println("Failed to download the file.");
                        return;
                    }

                    System.out.print("OK");
                }
            }

            System.out.print("\n");
        }

        System.out.println("Downloaded all stickers.");
    }

    private static long formatId(String src) {
        try {
            return Long.parseLong(src);
        } catch (NumberFormatException e) {
            Pattern p = Pattern.compile("^https?://store.line.me/stickershop/product/([0-9]+)");
            Matcher m = p.matcher(src);
            if (m.matches()) {
                String strID = m.toMatchResult().group(1);
                return Long.parseLong(strID);
            }
        }

        return -1;
    }
}
