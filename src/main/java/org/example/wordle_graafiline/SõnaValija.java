package org.example.wordle_graafiline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
public class SõnaValija {
    private static final String FILE_PATH = "sõnad.txt"; //failitee
    public String[] valiSõna() {
        String[][] sõnadJaTähendused = new String[100][0];
        String[] sõnaJaTähendus = new String[2];
        int i = 0; //ridade arv failis, random jaoks vajalik

        try (Scanner myReader = new Scanner(new File(FILE_PATH))) {
            while (myReader.hasNextLine()) {
                sõnadJaTähendused[i] = myReader.nextLine().split(";");
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Midagi läks katki!");
            e.printStackTrace();
        }
        Random random = new Random();
        int randomNumber = random.nextInt(i);
        sõnaJaTähendus[0] = sõnadJaTähendused[randomNumber][0];
        sõnaJaTähendus[1] = sõnadJaTähendused[randomNumber][1];
        return sõnaJaTähendus;
    } //sõnaValija
}//class