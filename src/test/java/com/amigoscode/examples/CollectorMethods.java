package com.amigoscode.examples;

import com.amigoscode.beans.Car;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.amigoscode.mockdata.MockData.getCars;

public class CollectorMethods {

    @Test
    void averaging() {
        List<String> numbers = List.of("1", "2", "3", "4");
        val averaging = numbers.stream().collect(Collectors.averagingLong(Long::parseLong));
        System.out.println(averaging);
    }

    @Test
    void countingCarsInMapWithCarIdKey() throws IOException {
        Map<Integer, Car> cars = getCars().stream().collect(Collectors.toMap(
                Car::getId,
                Function.identity()
        ));

        val countingCars = cars.entrySet().stream().collect(Collectors.counting());
        System.out.println(countingCars);
    }

    @Test
    void collectingAndThenCountingCarsInMapWithCarIdKey() throws IOException {
        Map<Integer, Car> cars = getCars().stream().collect(Collectors.toMap(
                Car::getId,
                Function.identity()
        ));

        val countingCars = getCars()
                .stream()
                .skip(100)
                .limit(10)
                .collect(Collectors.collectingAndThen(
                        Collectors.summingDouble(Car::getPrice),
                        sum -> sum / 100
                ));
        System.out.println(countingCars);
    }

    @Test
    void filtering() throws IOException {
        val onlyIds = getCars()
                .stream()
                .limit(10)
                .map(Car::getId)
                .collect(Collectors
                        .filtering(id -> id % 2 != 0, Collectors.toSet())
                );
        System.out.println(onlyIds);
    }

    @Test
    void mapping() throws IOException {
        val onlyIds = getCars()
                .stream()
                .limit(10)
                .collect(Collectors
                        .mapping(Car::getId, Collectors.toSet())
                );
        System.out.println(onlyIds);
    }

    @Test
    void flatMapping() throws IOException {
        val onlyIds = getCars()
                .stream()
                .limit(10)
                .collect(Collectors
                        .flatMapping(car -> Stream.concat(Stream.of(car.getId()), Stream.of(car.getYear())),
                                Collectors.toSet())
                );
        System.out.println(onlyIds);
    }

    @Test
    void joining() throws IOException {
        val onlyIds = getCars()
                .stream()
                .limit(10)
                .map(Car::getColor)
                .collect(Collectors.joining("_"));
        System.out.println(onlyIds);

        val onlyColors = getCars()
                .stream()
                .limit(10)
                .map(Car::getColor)
                .collect(Collectors.joining());
        System.out.println(onlyColors);

        val onlyColorsWithPrefixAndSuffix = getCars()
                .stream()
                .limit(10)
                .map(Car::getColor)
                .collect(Collectors.joining("_", "pre_", "_post"));
        System.out.println(onlyColorsWithPrefixAndSuffix);
    }

    @Test
    void groupingBy() throws IOException {
        Map<String, List<Car>> byColor = getCars()
                .stream()
                .limit(5)
                .collect(Collectors.groupingBy(
                        Car::getColor
                ));
        System.out.println(byColor);

        Map<String, Set<Double>> byColorAndSetOfPrice = getCars()
                .stream()
                .limit(5)
                .collect(Collectors.groupingBy(
                        Car::getColor,
                        Collectors.mapping(Car::getPrice, Collectors.toSet())
                ));
        System.out.println(byColorAndSetOfPrice);
    }

    @Test
    void reducing() throws IOException {
        val oldestCar = getCars()
                .stream()
                .limit(5)
                .collect(Collectors
                        .reducing((car1, car2) -> car1.getYear() > car2.getYear() ? car1 : car2)
                );
        System.out.println(oldestCar.get());

        val sum = getCars()
                .stream()
                .limit(10)
                .map(car -> car.getYear() - 2000)
                .collect(Collectors.reducing(
                        0, Integer::sum
                ));
        System.out.println(sum);
    }

    @Test
    void maxBy() throws IOException {
        val oldestCar = getCars()
                .stream()
                .limit(50)
                .collect(Collectors.maxBy(Comparator.comparingInt(Car::getId)));
        System.out.println(oldestCar);
    }

    @Test
    void minBy() throws IOException {
        val cars = getCars();
        Collections.reverse(cars);
        val oldestCar = cars
                .stream()
                .limit(50)
                .collect(Collectors.minBy(Comparator.comparingInt(Car::getId)));
        System.out.println(oldestCar);
    }

    @Test
    void partitioningByGreen() throws IOException {
        val isGreen = getCars()
                .stream()
                .limit(50)
                .map(Car::getColor)
                .collect(Collectors.partitioningBy(
                        color -> color.equals("Green")
                ));
        System.out.println(isGreen);

        val oldestCar = getCars()
                .stream()
                .limit(50)
                .map(Car::getColor)
                .collect(Collectors.partitioningBy(
                        color -> color.equals("Green"),
                        Collectors.counting()
                ));
        System.out.println(oldestCar);
    }
}
