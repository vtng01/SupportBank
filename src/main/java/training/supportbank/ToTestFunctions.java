package training.supportbank;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToTestFunctions {
    public static void main(String[] args) {
        String s = "ben";
        Pattern alphaRegex = Pattern.compile("(?<name>[a-z\\s]+)");
        Matcher alphaMatcher = alphaRegex.matcher(s);
        System.out.println(alphaMatcher.find());
        System.out.println(alphaMatcher.group("name"));

        HashMap<String, Integer> myMap = new HashMap<>();
        myMap.put("hi", 1);
        myMap.put("Bye", 2 );

        System.out.println(myMap);
        myMap.remove("hi");
        System.out.println(myMap);
        myMap.remove("world");
        System.out.println(myMap);

    }


}
