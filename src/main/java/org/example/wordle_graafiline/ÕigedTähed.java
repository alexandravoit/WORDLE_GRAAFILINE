package org.example.wordle_graafiline;

import java.util.ArrayList;
import java.util.List;

public class ÕigedTähed {
    private String pakutudSõna;
    private String õigeSõna;
    private char[] vastuseMassiiv; // MÄNGU SEISU JÄLGIMISEKS MASSIIV
    private List<Character> valesKohas = new ArrayList<>();
    private List<Character> poleSõnas = new ArrayList<>();



    //KONSTRUKTOR
    public ÕigedTähed(String pakutudSõna, String õigeSõna) {
        this.pakutudSõna = pakutudSõna;
        this.õigeSõna = õigeSõna;
        this.vastuseMassiiv = new char[õigeSõna.length()];
    }

    public void lisaTähed() {

        //LISA TÄHED MASSIIVI EHK TÄIENDA VASTUST
        for (int i = 0; i < pakutudSõna.length(); i++) {
            char täht = pakutudSõna.charAt(i);
            if (!õigeSõna.contains(String.valueOf(täht)) && !poleSõnas.contains(täht)) {
                poleSõnas.add(täht);
            } else {
                if (õigeSõna.charAt(i) == täht) {
                    lisaTähedMassiivi(täht, i);

                } else{
                    if (!valesKohas.contains(täht) && !poleSõnas.contains(täht)) {
                        valesKohas.add(täht);
                    }

                }

            }

        }

        //VAATA, ET TÄHT POLEKS SAMAL AJAL VASTUSES JA "VALEL KOHAL"
        for (char täht : vastuseMassiiv) {
            if (valesKohas.contains(täht)) valesKohas.remove(Character.valueOf(täht));
        }

    }

    public void väljastaOlukord() {

        if (poleSõnas.isEmpty()) {
            System.out.println("Kõik siiani pakutud tähed on sõnas olemas!");
        } else {
            if (!valesKohas.isEmpty()) {
                System.out.print("Tähed, mis on sõnas olemas, kuid pakkusid valele kohale: ");
                for (char täht : valesKohas) {
                    System.out.print(täht + " ");
                }
                System.out.println();
            }


            System.out.print("Tähed, mida sõnas pole: ");
            for (char täht : poleSõnas) {
                System.out.print(täht + " ");
            }
            System.out.println();
        }


    }

    public char[] lisaTähedMassiivi(char x, int misKohale) {
        vastuseMassiiv[misKohale] = x;
        return vastuseMassiiv;
    }

    public char[] getVastuseMassiiv() {
        return vastuseMassiiv;
    }

    public void setPakutudSõna(String pakutudSõna) {
        this.pakutudSõna = pakutudSõna;
    }
}