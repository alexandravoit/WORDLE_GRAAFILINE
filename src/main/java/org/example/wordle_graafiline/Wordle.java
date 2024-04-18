package org.example.wordle_graafiline;

import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {

        // ALGVÄÄRTUSTUSED
        int guessCounter = 1;
        boolean äraArvatud = false;

        SõnaValija sõnaValija = new SõnaValija(); // sõnaValija klass, et valida suvaline sõna
        String[] sõnaJaTähendus = sõnaValija.valiSõna(); // String[] sõnast ja selle tähendusest
        String otsitavSõna = sõnaJaTähendus[0];
        String otsitavaSõnaTähendus = sõnaJaTähendus[1];
        int mituTähteSõnas = otsitavSõna.length();
        int elud = 0;
        String raskusaste = "0";

        String ANSI_RED = "\u001B[31m";

        // SCANNERI KLASS
        Scanner sk = new Scanner(System.in);

        while ((!raskusaste.equals("1")) && (!raskusaste.equals("2")) && (!raskusaste.equals("3"))) {
            System.out.print("Vali mängu raskusaste!\n(1) -> Lihtne (lõputult pakkumisi)\n(2) -> Keskmine (10 pakkumist)\n(3) -> Klassikaline (6 pakkumist)\n");
            System.out.print("\u001B[31m" + "Sisesta 1 või 2 või 3." + "\u001B[0m\n--> ");
            raskusaste = sk.nextLine();

            if (raskusaste.equals("1"))
                elud = Integer.MAX_VALUE;
            else if (raskusaste.equals("2"))
                elud = 10;
            else if (raskusaste.equals("3"))
                elud = 6;
        }

        System.out.print("Mäng algab");
        for (int i = 0; i < 3; i++) { //Laadimine tekitab põnevust :)
            System.out.print(".");
            try {
                Thread.sleep(666);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();

        //TEATAB MITU TÄHTE ARVATAVAS SÕNAS ON
        System.out.println("Sõnas on " + mituTähteSõnas + " tähte.");

        // ESIMENE PAKKUMINE LOOPIST VÄLJAS
        String arvajaSõna = "";
        while (arvajaSõna.length() != 6) {

            System.out.print("Sinu " + guessCounter + ". pakkumine: ");
            arvajaSõna = sk.nextLine().toLowerCase();
            if (arvajaSõna.length() != 6)
                System.out.println("Paku ainult 6-tähelisi sõnu!");
        }
        guessCounter++;


        // LUUAKSE KLASS
        ÕigedTähed misOnÕigedTähed = new ÕigedTähed(arvajaSõna, otsitavSõna);

        // LOON KLASSI ABIL DEFAULT VASTUSEMASSIIVI
        for (int i = 0; i < otsitavSõna.length(); i++) {
            misOnÕigedTähed.lisaTähedMassiivi('_', i);
        }

        if (arvajaSõna.equals(otsitavSõna)) { // ERIJUHT KUI KASUTAJA ARVAB ESIMESE KORRAGA SÕNA ÄRA
            System.out.println("Arvasid sõna ühe korraga ära! Guesswork or genius?!");
            System.out.println("Sõna tähendab " + "'" + otsitavaSõnaTähendus + "'");
        } else {

            // NÄITAB __A__M__ KUJUL SÕNA, KUS _ ON ARVAMATA TÄHT
            misOnÕigedTähed.lisaTähed();
            misOnÕigedTähed.väljastaOlukord();
            väljastaMänguSeis(misOnÕigedTähed);
            System.out.println();


            // SENI KUNI KASUTAJA POLE SÕNA ÄRA ARVANUD
            while (!äraArvatud && elud > 0) {
                System.out.print("Sinu " + guessCounter + ". pakkumine: ");
                arvajaSõna = sk.nextLine().toLowerCase();
                if (arvajaSõna.length() != 6) {
                    System.out.println("Paku ainult 6-tähelisi sõnu!");
                    continue;
                }

                if (arvajaSõna.equals(otsitavSõna)) { // KASUTAJA ARVAB SÕNA ÄRA
                    System.out.println("Arvasid sõna " + "'" + otsitavSõna + "'" + " ära " + guessCounter + ". korraga.");
                    äraArvatud = true;
                    System.out.println("Sõna tähendab " + "'" + otsitavaSõnaTähendus + "'");
                } else {
                    misOnÕigedTähed.setPakutudSõna(arvajaSõna); // UUENDAB SINU GUESSI
                    misOnÕigedTähed.lisaTähed();
                    misOnÕigedTähed.väljastaOlukord();
                    väljastaMänguSeis(misOnÕigedTähed);
                    System.out.println();
                    guessCounter ++;

                    if (guessCounter > elud) {
                        System.out.println("Kaotasid :( Otsitav sõna oli " + otsitavSõna + ", mis tähendab '" + otsitavaSõnaTähendus + "'");
                        break; //Elud on otsas, mäng on läbi, väljume tsüklist.

                    }
                }
            }
        }
    }

    public static void väljastaMänguSeis(ÕigedTähed sõna) {
        System.out.println("SÕNA SEIS: ");
        System.out.print(sõna.getVastuseMassiiv());
        System.out.println();
    }
}