package com.amigoscode.examples;

import com.amigoscode.beans.Car;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.amigoscode.mockdata.MockData.getCars;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CollectorMethods {

    @Test
    void averaging() {
        List<String> numbers = List.of("1", "2", "3", "4");
        Double averaging = numbers.stream().collect(Collectors.averagingLong(Long::parseLong));
        System.out.println(averaging);
    }

    @Test
    void countingCarsInMapWithCarIdKey() throws IOException {
        Map<Integer, Car> cars = getCars().stream().collect(Collectors.toMap(
                Car::getId,
                Function.identity()
        ));

        Long countingCars = cars.entrySet().stream().collect(Collectors.counting());
        System.out.println(countingCars);
    }

    @Test
    void collectingAndThenCountingCarsInMapWithCarIdKey() throws IOException {
        Map<Integer, Car> cars = getCars().stream().collect(Collectors.toMap(
                Car::getId,
                Function.identity()
        ));

        Double countingCars = cars.values()
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
        Set<Integer> onlyIds = getCars()
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
        Set<Integer> onlyIds = getCars()
                .stream()
                .limit(10)
                .collect(Collectors
                        .mapping(Car::getId, Collectors.toSet())
                );
        System.out.println(onlyIds);
    }

    @Test
    void flatMapping() throws IOException {
        Set<Integer> onlyIds = getCars()
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
        String onlyIds = getCars()
                .stream()
                .limit(10)
                .map(Car::getColor)
                .collect(Collectors.joining("_"));
        System.out.println(onlyIds);

        String onlyColors = getCars()
                .stream()
                .limit(10)
                .map(Car::getColor)
                .collect(Collectors.joining());
        System.out.println(onlyColors);

        String onlyColorsWithPrefixAndSuffix = getCars()
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
        Optional<Car> oldestCar = getCars()
                .stream()
                .limit(5)
                .collect(Collectors
                        .reducing((car1, car2) -> car1.getYear() > car2.getYear() ? car1 : car2)
                );
        System.out.println(oldestCar.get());

        Integer sum = getCars()
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
        Optional<Car> oldestCar = getCars()
                .stream()
                .limit(50)
                .collect(Collectors.maxBy(Comparator.comparingInt(Car::getId)));
        System.out.println(oldestCar);
    }

    @Test
    void minBy() throws IOException {
        List<Car> cars = getCars();
        Collections.reverse(cars);
        Optional<Car> oldestCar = cars
                .stream()
                .limit(50)
                .collect(Collectors.minBy(Comparator.comparingInt(Car::getId)));
        System.out.println(oldestCar.get());
    }

    @Test
    void partitioningByGreen() throws IOException {
        Map<Boolean, List<String>> isGreen = getCars()
                .stream()
                .limit(50)
                .map(Car::getColor)
                .collect(Collectors.partitioningBy(
                        color -> color.equals("Green")
                ));
        System.out.println(isGreen);

        Map<Boolean, Long> oldestCar = getCars()
                .stream()
                .limit(50)
                .map(Car::getColor)
                .collect(Collectors.partitioningBy(
                        color -> color.equals("Green"),
                        Collectors.counting()
                ));
        System.out.println(oldestCar);
    }

    @Test
    void toMap() throws IOException {
        List<Car> cars = getCars();

        Map<Integer, Double> yearAndPrice = cars.stream()
                .collect(Collectors.toMap(
                        Car::getYear,
                        Car::getPrice,
                        Double::sum,
                        TreeMap::new
                ));
        System.out.println(yearAndPrice);
    }

    @Test
    void toLinkedList() throws IOException {
        List<String> carNames = getCars().stream()
                .map(Car::getModel)
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println(carNames);
    }

    @Test
    void partitioningBy() throws IOException {
        Map<Boolean, List<String>> blackAndWhiteOnly = getCars().stream()
                .map(Car::getColor)
                .filter(color -> color.equals("Pink") || color.equals("Blue"))
                .collect(Collectors.partitioningBy(
                        color -> color.equals("Pink"),
                        toList()
                ));
        System.out.println(blackAndWhiteOnly);
    }

    @Disabled
    @Test
    void teeing() throws IOException {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // Using Collectors.teeing to calculate sum and product of a List<Integer>
        var result = numbers.stream().collect(
                Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.reducing(1, (a, b) -> a * b),
                        (sum, product) -> "Sum: " + sum + ", Product: " + product
                )
        );

        System.out.println(result);
    }

    @Test
    void summarizing() throws IOException {
        val statistics = getCars().stream()
                .collect(Collectors.summarizingDouble(Car::getPrice));

        assertThat(statistics)
                .satisfies(result -> {
                    assertThat(result.getAverage()).isEqualTo(52693.19979);
                    assertThat(result.getCount()).isEqualTo(1000);
                    assertThat(result.getMax()).isEqualTo(99932.82);
                    assertThat(result.getMin()).isEqualTo(5005.16);
                    assertThat(result.getSum()).isEqualTo(5.269319979E7);
                });
    }

    @Test
    void reduction() throws IOException {
        val reducing = getCars().stream()
                .map(Car::getPrice)
                .collect(Collectors.reducing(Double::sum))
                .orElse(0.0);
        System.out.println(reducing);
    }
}
