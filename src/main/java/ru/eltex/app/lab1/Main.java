package ru.eltex.app.lab1;

import java.util.Scanner;

public class Main {
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
                break;
            case 2:
                electronics = new Smartphone[N];
                break;
            default:
                electronics = new Tablet[N];
                break;
        }
        for (int i = 0; i < N; i++) {
            switch (type) {
                case 1:
                    electronics[i] = new Phone();
                    break;
                case 2:
                    electronics[i] = new Smartphone();
                    break;
                default:
                    electronics[i] = new Tablet();
                    break;
            }
            System.out.printf("================Creating No%s================\n", i + 1);
            electronics[i].create();
            electronics[i].update();
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