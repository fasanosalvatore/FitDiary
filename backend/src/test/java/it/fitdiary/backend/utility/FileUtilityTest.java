package it.fitdiary.backend.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilityTest {

    private File fileSchedaAlimentare;

    @BeforeEach
    public void setUp() {
        fileSchedaAlimentare = new File(
                Objects.requireNonNull(getClass().getClassLoader()
                                .getResource("schedaAlimentare.csv"))
                        .getFile());
    }

    @Test
    public void getFileError() throws IOException {
        MockMultipartFile mockMultipartFile= new MockMultipartFile("schedaAlimentare", new byte[0]);
        assertEquals(null, FileUtility.getFile(mockMultipartFile));
    }
}
