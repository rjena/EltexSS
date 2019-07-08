package ru.eltex.app.java;

public class Test {
    public static final String PHONE = "Phone";
    public static final String SMARTPHONE = "Smartphone";
    public static final String TABLET = "Tablet";

    public static void main(String[] args) {
        try {
            int N = Integer.valueOf(args[0]);
            Electronic[] electronic = new Electronic[N];
            switch (args[1]) {
                case PHONE:
                    for (int i = 0; i < N; i++)
                        electronic[i] = new Phone();
                    break;
                case SMARTPHONE:
                    for (int i = 0; i < N; i++)
                        electronic[i] = new Smartphone();
                    break;
                case TABLET:
                    for (int i = 0; i < N; i++)
                        electronic[i] = new Tablet();
                    break;
                default:
                    System.out.println("Error!");
                    return;
            }
            for (Electronic el : electronic) {
                el.create();
                el.read();
            }
            System.out.printf("\n\nTested. %s objects created.", N);
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
    }
}