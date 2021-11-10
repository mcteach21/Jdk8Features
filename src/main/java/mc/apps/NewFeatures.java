package mc.apps;

import org.apache.commons.collections4.MapUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mc.apps.Utils.Display;

class Jdk8Demo{
    public Jdk8Demo() {
        System.out.println("Class constructor demo!");
    }
    public void doInstanceThing() { System.out.println("instance method : something!"); }
    public static void doStaticThing() { System.out.println("static method : something!"); }
}
@FunctionalInterface
interface DemoInterface{
    void run();
}

class User{
    private static int _id=0;
    private int id;
    private String firstname;
    private String lastname;
    public User(String firstname, String lastname) {
        this.id = ++_id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName() {
        return firstname+" "+lastname;
    }
    @Override
    public String toString() {
        return "[" + id + "] " + firstname + " " + lastname ;
    }

}

public class NewFeatures {
    /**
     * Nouvelles fonctionnalités JDK 8 : Lambda, Stream, ...
     */

    @FunctionalInterface
    interface MyFunctionnalInterface{
        public int op(int x, int y);
    }

    public static void LambdaExamples() {
        //Syntax of lambda expression : (parameter_list) -> {function_body}
        Display("Lambda Expressions");

        Arrays.asList("James Bond","Jason Bourne")
                //.forEach(name->System.out.println(name));
                .forEach(System.out::println);

        MyFunctionnalInterface el = (a, b) -> a*b;
        System.out.println("result : " + el.op(10, 25));
    }

    public static void StreamExamples() {
        Display("Stream API");

        List<String> list = Arrays.asList("Java", "Kotlin", "Python", "C#");
        list.stream().filter(l->l.length()>2).forEach(l->System.out.println(l));

        Stream.concat(
                Arrays.asList("Homer", "Marge").stream(),
                Arrays.asList("Bart", "Lisa").stream()
        ).forEach(System.out::println);
        Stream.iterate(1, nb -> nb*2).limit(5).forEach(System.out::println);


        Predicate<? super String> predicate = (x)->x.contains("H");
        List<String> family = Arrays.asList("Homer", "Marge", "Bart", "Lisa");

        boolean any_contains_letter = family.stream().anyMatch(predicate);
        boolean all_contains_letter = family.stream().allMatch(predicate);

        System.out.println(any_contains_letter+" "+all_contains_letter);
    }

    public static void MethodReferencesExamples() {
        Display("Method References");

        DemoInterface method1 = Jdk8Demo::doStaticThing;
        DemoInterface method2 = new Jdk8Demo()::doInstanceThing;
        DemoInterface method3 = Jdk8Demo::new;

        method1.run();
        method2.run();
        method3.run();
    }

    public static void OptionalClassExamples() {
        Display("Optional Class");

        String[] tablo = new String[5];
        tablo[0] = "hello";

        System.out.println(String.format("tablo : %s.", Arrays.deepToString(tablo)));
        //System.out.println(tablo[1].toUpperCase()); // => java.lang.NullPointerException !

        Optional<String> item = Optional.ofNullable(tablo[0]);
        System.out.println(String.format("Optional<String> - tablo[0] : %s. ", item.isPresent()?item.get():"Item 0 is Null!"));
        //System.out.println(item.orElse("Item 0 is null!"));

        item = Optional.ofNullable(tablo[1]);
        System.out.println(String.format("Optional<String> - tablo[1] : %s. ", item.isPresent()?item.get():"Item 1 is Null!"));
        //System.out.println(item.orElse("Item 1 is null!"));

        Optional<String> empty = Optional.empty();
        System.out.println(String.format("Optional.empty() : %s. ", empty.orElse("Empty!")));

        //transform with map
        //		List<String> languages = Arrays.asList("Java","Kotlin", "Python", "C#");
        //		Optional<List<String>> optionalList = Optional.of(languages);
        //
        //		int count = optionalList.map(List::size).orElse(0);
        //		System.out.println(count);

        System.out.println();
        String filtered = Optional.of("Hello from Java!").filter(x->x.contains("Java")).map(x->x.toUpperCase()).orElse("Nothing!");
        System.out.println("Optional.of(\"Hello from Java!\").filter(x->x.contains(\"Java\")).map(x->x.toUpperCase()).orElse(\"Nothing!\")");
        System.out.println("filtered : "+filtered);
    }

    static int key=1;
    static List<String> langages = Arrays.asList("Java", "Kotlin", "Python", "CSharp");
    public static void forEachExamples() {
        Display("forEach - Collections");

        langages.forEach(System.out::println);

        Map<Integer, String> map = new HashMap<Integer, String>();
        langages.forEach(x -> map.put(key++, x));

        System.out.println(map);
        map.forEach((k,v) -> System.out.println(k+" : "+v));

        // MapUtils : (org.apache.commons) commons-collections4
        MapUtils.populateMap(map, langages, String::length);
        System.out.println(map);

        //langages.stream().collect(Collectors.toMap());

        System.out.println("forEach :");
        langages.stream().filter(i->i.length()>4)
                .parallel()
                .forEach(System.out::println);

        System.out.println("forEachOrdered :");
        langages.stream().filter(i->i.length()>4)
                .parallel()
                .forEachOrdered(System.out::println);

    }

    public static void StringJoinerExamples() {
        Display("StringJoiner Class");

        StringJoiner stringJoiner = new StringJoiner(" - ","{ "," }");
        langages.forEach(l->stringJoiner.add(l));

        System.out.println("StringJoiner stringJoiner = new StringJoiner(\" - \",\"{ \",\" }\");");
        System.out.println("langages.forEach(l->stringJoiner.add(l));");

        System.out.println();
        System.out.println(stringJoiner);
    }

    public static void CollectorsExamples() {
        List<String> langages = Arrays.asList("Java", "Kotlin", "Python", "CSharp");

        // récupérer elements (users) et stocker dans map
        Map<Integer, String> map =	langages.stream().collect( Collectors.toMap(s -> key++, s -> s.toUpperCase()));
        System.out.println(map);

        Map<String, Integer> map2 = langages.stream()
                .collect(Collectors.toMap(Function.identity(), String::length, (item, identicalItem) -> item));
        System.out.println(map2);

        // récupérer noms elements (users) et stocker dans list
        List<User> users = Arrays.asList(new User("James","Bond"), new User("Jason","Bourne"));
        List<String> names = users.stream().map(User::getName).collect(Collectors.toList());

        System.out.println(names);

        // accumuler dans un set
        Set<String> set = users.stream().map(User::getName).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);

        // Convertir elements en chaine de caractèrres et les concatener sparés par une virgule
        String concatenes = users.stream().map(Object::toString).collect(Collectors.joining(", "));
        System.out.println(concatenes);


    }

    public static void AtomicLongExamples() {
        Display("AtomicLong");

        AtomicLong al = new AtomicLong();
        System.out.println(" AtomicLong al = new AtomicLong() ");
        for (int i = 0; i < 5; i++)
            System.out.println("al.getAndIncrement() : "+al.getAndIncrement());
    }

}