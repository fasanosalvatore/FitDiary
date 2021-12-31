package it.fitdiary.backend.utility;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Data
@RequiredArgsConstructor
public class PasswordGenerator {
    private String[] lMaiusc={"A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private String[] lMin={"a","b","c","d","e","f","g","h","i","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};;
    private String[] lNum={"1","2","3","4","5","6","7","8","9","0"};
    private String[] lSimb={".","-",",","*"};


    /**
     * Questo metodo genera una password casuale per un nuovo utente
     * @return password generata casualmente
     */
    public String generate(){
        String pw="";
        Random rand= new Random();
        pw=pw+lMaiusc[rand.nextInt(23)];
        for(int i=0; i<3; i++){
            pw=pw+lMin[rand.nextInt(23)];
        }
        for(int i=0; i<2; i++){
            pw=pw+lNum[rand.nextInt(10)];
        }
        pw=pw+lSimb[rand.nextInt(4)];
        return pw;
    }

}
