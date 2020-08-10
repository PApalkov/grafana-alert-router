package ru.grafana.alert.router.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.logging.LoggingModule.loggingModule;

public class FileLoader {

    public static File loadFile(String fileUrl, String localFileUri) {
        if (isEmpty(fileUrl)) return null;

        File downloadingFile = new File(localFileUri);
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(downloadingFile)) {
            byte[] dataBuffer = new byte[1000000];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1000000)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            return downloadingFile;
        } catch (Exception e) {
            loggingModule().getLogger().error(e);
        }

        return null;
    }
}
