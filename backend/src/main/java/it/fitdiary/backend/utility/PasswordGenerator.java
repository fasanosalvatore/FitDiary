package it.fitdiary.backend.utility;

import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Data
@RequiredArgsConstructor
@Generated
public class PasswordGenerator {
    /**
     * Lettere maiuscole.
     */
    private String[] lMaiusc =
            {"A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /**
     * Lettere minuscole.
     */
    private String[] lMin =
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "l", "m", "n", "o",
                    "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    /**
     * Numeri.
     */
    private String[] lNum = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    /**
     * Simboli.
     */
    private String[] lSimb = {".", "-", ",", "*"};


    /**
     * Questo metodo genera una password casuale per un nuovo utente.
     *
     * @return password generata casualmente
     */
    public String generate() {
        final int bound23 = 23;
        final int bound10 = 10;
        final int bound4 = 4;
        String pw = "";
        Random rand = new Random();

        pw = pw + lMaiusc[rand.nextInt(bound23)];
        final int v = 3;
        for (int i = 0; i < v; i++) {
            pw = pw + lMin[rand.nextInt(bound23)];
        }
        final int z = 2;
        for (int i = 0; i < z; i++) {
            pw = pw + lNum[rand.nextInt(bound10)];
        }
        pw = pw + lSimb[rand.nextInt(bound4)];
        return pw;
    }

}
