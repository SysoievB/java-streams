package com.amigoscode.examples;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

class GPTTasks {

    //Find Maximum Element: Given a list of integers, use a stream to find the maximum element.
    @Test
    void maxElement() {
        val maxNumber = IntStream.rangeClosed(1, 10)
                .max()
                .orElse(0);
        System.out.println(maxNumber);
    }

    //Filter and Map: Given a list of Strings, filter out Strings that have length less than 4
    // and convert the remaining Strings to uppercase.
    @Test
    void filterLength() {
        val lengthLessThan4 = List.of("filter", "out", "and", "less", "convert")
                .stream()
                .filter(str -> str.length() < 4)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(lengthLessThan4);
    }

    //First N Squares: Generate a stream of the first n square numbers (1, 4, 9, 16, ...).
    @Test
    void firstNSquares() {
        val numbers = DoubleStream.of(1, 2, 3, 4, 5, 6, 7)
                .limit(3)
                .mapToInt(val -> (int) Math.pow(val, 2))
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(numbers);
    }

    //Unique Characters: Given a string, use streams to find the unique characters in it.
    @Test
    void uniqueCharacters() {
        val str = "Unique Characters: Given a string, use streams to find the";
        val unique = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        unique.forEach(System.out::print);
    }

    //Concatenate Strings: Given a list of Strings, concatenate
    // them into a single String separated by a comma.
    @Test
    void concatenation() {
        val strings = List.of("filter", "out", "and", "less", "convert")
                .stream()
                .collect(Collectors.joining(", "));
        System.out.println(strings);
    }

    //Group By Length: Given a list of Strings, group them by their length.
    @Test
    void groupBy() {
        val strings = List.of("filter", "out", "and", "less", "convert")
                .stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.toList()
                ));
        System.out.println(strings);
    }

    //Partition By Even: Given a list of integers, partition them into two groups: even and odd.
    @Test
    void partitioningBy() {
        val numbers = IntStream.range(1, 21)
                .boxed()
                .collect(Collectors.partitioningBy(value -> value % 2 == 0));
        System.out.println(numbers);
    }

    //Frequency Count: Given a list of integers, find the frequency of each unique integer.
    @Test
    void frequency() {
        val numbers = IntStream.of(1, 3, 4, 5, 5, 5, 6, 3, 6, 6, 7)
                .boxed()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        System.out.println(numbers);
    }

    //Average of Numbers: Given a list of integers, find their average.
    @Test
    void average() {
        val numbers = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(numbers);
    }

    //Joining Elements: Given a list of Strings, join them into a single String with elements separated by a semicolon.
    @Test
    void joining() {
        val strings = List.of("filter", "out", "and", "less", "convert")
                .stream()
                .collect(Collectors.joining("; "));
        System.out.println(strings);
    }
}
