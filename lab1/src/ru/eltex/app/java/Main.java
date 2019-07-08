package ru.eltex.app.java;

import java.util.Scanner;

public class Main {
    static private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int type = 0;
        while (type < 1 || type > 3) {
            System.out.println("Presentation types:\n1. Phone\n2. Smartphone\n3. Tablet");
            type = doubleOrIntegerInputWithMessage("Choose electronic type");
        }

        int N = doubleOrIntegerInputWithMessage("Input the count");
        Electronic[] electronics;
        switch (type) {
            case 1:
                electronics = new Phone[N];
                for (int i = 0; i < N; i++) {
                    electronics[i] = new Phone();
                    System.out.printf("================Creating No%s================\n", i + 1);
                    electronics[i].create();
                    electronics[i].update();
                }
                break;
            case 2:
                electronics = new Smartphone[N];
                for (int i = 0; i < N; i++) {
                    electronics[i] = new Smartphone();
                    System.out.printf("================Creating No%s================\n", i + 1);
                    electronics[i].create();
                    electronics[i].update();
                }
                break;
            default:
                electronics = new Tablet[N];
                for (int i = 0; i < N; i++) {
                    electronics[i] = new Tablet();
                    System.out.printf("================Creating No%s================\n", i + 1);
                    electronics[i].create();
                    electronics[i].update();
                }
                break;
        }

        for (Electronic el : electronics)
            el.read();
        System.out.printf("\n\nAll electronics count = %s", electronics[0].getCount());
    }

    static private int doubleOrIntegerInputWithMessage(String msg) {
        int d = 2;
        System.out.printf(msg + "[default: %s]: ", d);
        String line = new Scanner(System.in).nextLine();
        return line.matches("\\d+") ? Integer.valueOf(line) : d;
    }
}