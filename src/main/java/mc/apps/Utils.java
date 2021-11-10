package mc.apps;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

    public static void Display(String title) {
        System.out.println();
        String formatted = String.format("************** %s **************", title);

        PrintLoopThenReturn(formatted);
        System.out.println(formatted);
        PrintLoopThenReturn(formatted);

    }
    public static void PrintLoopThenReturn(String formatted) {
        formatted.chars().forEach(c->System.out.print("*"));
        System.out.println();
    }

    public static void Menu(List<String> options) {

        AtomicInteger i = new AtomicInteger();
        options.stream()
                .map(o-> i.incrementAndGet() +"-"+o)
                .forEach(System.out::println);
        System.out.println(i.incrementAndGet() +"- Quitter!");
    }
    public static int ReadInt(String title) {
        System.out.print(title);

        Scanner in = new Scanner(System.in);
        String input_string = in.nextLine();

        try {
            return Integer.parseInt(input_string);
        }catch(Exception e){}

        return 0;
    }
}
