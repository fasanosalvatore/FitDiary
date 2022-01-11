package it.fitdiary.backend.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilityTest {

    private File fileSchedaAlimentare;

    @BeforeEach
    public void setUp() {
        fileSchedaAlimentare = new File(
                Objects.requireNonNull(getClass().getClassLoader()
                                .getResource("schedaAllenamento.csv"))
                        .getFile());
    }

    @Test
    public void getFileSuccess() throws IOException {
        MockMultipartFile mockMultipartFile= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        assertEquals(fileSchedaAlimentare, FileUtility.getFile(mockMultipartFile));
    }

    @Test
    public void getFileError() throws IOException {
        MockMultipartFile mockMultipartFile= new MockMultipartFile("schedaAlimentare", new byte[0]);
        assertEquals(null, FileUtility.getFile(mockMultipartFile));
    }
}
