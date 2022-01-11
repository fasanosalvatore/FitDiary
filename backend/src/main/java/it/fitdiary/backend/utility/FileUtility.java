package it.fitdiary.backend.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileUtility {

    private FileUtility() {

    }

    /**
     * funzione per creare file da MultipartFile.
     *
     * @param multiPartFile input dal form
     * @return file
     * @throws IOException eccezione in caso di errore con il file
     */
    public static File getFile(final MultipartFile multiPartFile)
            throws IOException {
        if (multiPartFile == null || multiPartFile.isEmpty()) {
            return null;
        }
        var file = new File(multiPartFile.getOriginalFilename());
        file.createNewFile();
        var fos = new FileOutputStream(file);
        fos.write(multiPartFile.getBytes());
        fos.close();
        return file;
    }
}
