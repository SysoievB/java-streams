## Stream notes

### IntStreams and other Stream Types
#### IntStream.of()
```
IntStream.of(10);     //10
IntStream.of(1, 2, 3);  //1,2,3
```
#### IntStream.range() and IntStream.rangeClosed()

```
        System.out.println("with int stream exclusive");
        IntStream.range(0, 10).forEach(System.out::print);//0123456789

        System.out.println("with int stream inclusive");
        IntStream.rangeClosed(0, 10).forEach(System.out::print);//012345678910
```
#### IntStream.iterate()    
```
        IntStream.iterate(0, value -> value + 1)
                .limit(11)
                .forEach(System.out::print);//012345678910
```
#### IntStream.generate()
generate() looks a lot like iterator(), but differ by not calculating the int values by increment the previous value. Rather a IntSupplier is provided which is a functional interface is used to generate an infinite sequential unordered stream of int values.

Following example create stream of 10 random numbers and then print them in console.
```
        IntStream stream = IntStream.generate(() 
            -> { return (int)(Math.random() * 10000); }); 
 
        stream.limit(10).forEach(System.out::println); 
```
#### IntStream to array & to list
Use IntStream.toArray() method to convert from int stream to array.
Using boxed() method of IntStream, we can get stream of wrapper objects which can be collected by Collectors methods.
```
IntStream stream = IntStream.range(1, 100); 
     
    List<Integer> primes = stream.filter(FilterExample::isPrime)
                  .boxed()
                  .collect(Collectors.toList());
                  
int[] intArray = IntStream.of(1, 2, 3, 4, 5).toArray();

List<Integer> ints = IntStream.of(1,2,3,4,5)
            .boxed()
            .collect(Collectors.toList());
```
### Min and Max
To find min and max number from stream of numbers, use Comparator.comparing( Integer::valueOf ) like comparators. Below example is for stream of Integers.
```
// Get Min or Max Number
Integer maxNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
          .max(Comparator.comparing(Integer::valueOf))
          .get();
 
Integer minNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
          .min(Comparator.comparing(Integer::valueOf))
          .get();
 
System.out.println("maxNumber = " + maxNumber);//9
System.out.println("minNumber = " + minNumber);//1
```
Or 
```
List<Integer> numbers = List.of(1, 2, 3, 100, 23, 93, 99);
        Integer min = numbers.stream().min(Comparator.naturalOrder()).get();
        System.out.println(min);//1
        
        Integer max = numbers.stream().max(Comparator.naturalOrder()).get();
        System.out.println(max);//100
```
To find min and max string or char from stream of chars, use Comparator.comparing( String::valueOf ) like comparators.
```
// Get Min or Max String/Char
String maxChar = Stream.of("H", "T", "D", "I", "J")
            .max(Comparator.comparing(String::valueOf))
            .get();
 
String minChar = Stream.of("H", "T", "D", "I", "J")
            .min(Comparator.comparing(String::valueOf))
            .get();
 
System.out.println("maxChar = " + maxChar);//maxChar = T
System.out.println("minChar = " + minChar);//minChar = D
```
### Removing Duplicates
```
        List<Integer> numbers = List.of(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9);
        List<Integer> distinct = numbers.stream().distinct().collect(Collectors.toList());
        assertThat(distinct).hasSize(9);
        System.out.println(distinct);//[1, 2, 3, 4, 5, 6, 7, 8, 9]

        Set<Integer> distinct = numbers.stream().collect(Collectors.toSet());
        assertThat(distinct).hasSize(9);
        System.out.println(distinct);//[1, 2, 3, 4, 5, 6, 7, 8, 9]
```
### Filtering Data
filter()
```
@Test
    public void filter() throws Exception {
        List<Car> cars = MockData.getCars();

        Predicate<Car> carPredicate = car -> car.getPrice() < 20_000.00;
        Predicate<Car> yellow = car -> car.getColor().equals("Yellow");

        List<Car> carsLessThan20k = cars.stream()
                .filter(carPredicate)
                .filter(yellow)
                .collect(Collectors.toList());

        carsLessThan20k.forEach(System.out::println);
        //Car{id=902, make='Mazda', model='Protege5', color='Yellow', year=2002, price=6600.59}
    }
```
dropWhile() - method of Stream API drops all values until it matches the predicate
```
@Test
    public void dropWhile() throws Exception {
        System.out.println("using filter");
        Stream.of(2, 4, 6, 8, 9, 10, 12).filter(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));
        System.out.println();
        System.out.println("using dropWhile");
        Stream.of(2, 4, 6, 8, 9, 10, 12).dropWhile(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));
/*
using filter
2 4 6 8 10 12 
using dropWhile
9 10 12 */
    }
```
The takeWhile() method of Stream API accepts all values until predicate returns false
```
@Test
    public void takeWhile() throws Exception {
        // using filter
        System.out.println("using filter");
        Stream.of(2, 4, 6, 8, 9, 10, 12).filter(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));

        System.out.println();
        System.out.println("using take while");
        Stream.of(2, 4, 6, 8, 9, 10, 12).takeWhile(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));
    }
    /*
    using filter
    2 4 6 8 10 12 
    using take while
    2 4 6 8 
    */
```
findFirst()
```
@Test
    public void findFirst() throws Exception {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = Arrays.stream(numbers).filter(n -> n == 50)
                .findFirst()
                .orElse(-1);
        System.out.println(result);//-1
    }
```
findAny()
```
@Test
    public void findAny() throws Exception {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10};
        int result = Arrays.stream(numbers).filter(n -> n == 9)
                .findAny()
                .orElse(-1);
        System.out.println(result);//9
    }
```
allMatch()
```
@Test
    public void allMatch() throws Exception {
        int[] even = {2, 4, 6, 8, 10};
        boolean allMatch = Arrays.stream(even).allMatch(n -> n % 2 == 0);
        System.out.println(allMatch);//true
    }
```
anyMatch()
```
@Test
    public void anyMatch() throws Exception {
        int[] evenAndOneOdd = {2, 4, 6, 8, 10, 11};
        boolean anyMatch = Arrays.stream(evenAndOneOdd).anyMatch(n -> !(n % 2 == 0));
        System.out.println(anyMatch);//true
    }
```
### Transformations
map() - lets you convert an object to something else.
```
@Test
    void yourFirstTransformationWithMap() throws IOException {
        List<Person> people = MockData.getPeople();

        Function<Person, PersonDTO> personPersonDTOFunction = person ->
                new PersonDTO(
                        person.getId(),
                        person.getFirstName(),
                        person.getAge());

        List<PersonDTO> dtos = people.stream()
                .filter(person -> person.getAge() > 20)
                .map(PersonDTO::map)//converts Person to PersonDTO
                .collect(Collectors.toList());

        dtos.forEach(System.out::println);
    }
```
mapToInteger() - converts objects to int values
mapToDouble() - converts objects to double values 
```
@Test
    void mapToDoubleAndFindAverageCarPrice() throws IOException {
            List<Car> cars = MockData.getCars();
        double avg = cars.stream()
        .mapToDouble(Car::getPrice)
        .average()
        .orElse(0);
        System.out.println(avg);
        }
```
#### **reduce()** - combine elements of a stream and produces a single value.

The Key Concepts: Identity, Accumulator and Combiner
- Identity – an element that is the initial value of the reduction operation and the default result if the stream is empty
- Accumulator – a function that takes two parameters: a partial result of the reduction operation and the next element of the stream
- Combiner – a function used to combine the partial result of the reduction operation when the reduction is parallelized or when there's a mismatch between the types of the accumulator arguments and the types of the accumulator implementation

```
//A simple sum operation using a for loop.
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  int sum = 0;
  for (int i : numbers) {
      sum += i;
  }
  System.out.println("sum : " + sum); // 55
  
//The equivalent in Stream.reduce()
// 1st argument, init value = 0
  int sum = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);

  System.out.println("sum : " + sum); // 55
  
//or method reference with Integer::sum
int sum = Arrays.stream(numbers).reduce(0, Integer::sum); // 55
```
1. Method Signature
```
Stream.java
T reduce(T identity, BinaryOperator<T> accumulator);

IntStream.java
int reduce(int identity, IntBinaryOperator op);

LongStream.java
long reduce(int identity, LongBinaryOperator op);
```
- identity = default or initial value.
- BinaryOperator = functional interface, take two values and produces a new value.

if the identity argument is missing, there is no default or initial value, and it returns an optional.
```
Stream.java

Optional<T> reduce(BinaryOperator<T> accumulator);
```
2. Math operations
```
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

int sum = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);    // 55
int sum2 = Arrays.stream(numbers).reduce(0, Integer::sum);      // 55

int sum3 = Arrays.stream(numbers).reduce(0, (a, b) -> a - b);   // -55
int sum4 = Arrays.stream(numbers).reduce(0, (a, b) -> a * b);   // 0, initial is 0, 0 * whatever = 0
int sum5 = Arrays.stream(numbers).reduce(0, (a, b) -> a / b);   // 0
```
3. Max and Min.
```
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

int max = Arrays.stream(numbers).reduce(0, (a, b) -> a > b ? a : b);  // 10
int max1 = Arrays.stream(numbers).reduce(0, Integer::max);            // 10

int min = Arrays.stream(numbers).reduce(0, (a, b) -> a < b ? a : b);  // 0
int min1 = Arrays.stream(numbers).reduce(0, Integer::min); 
```
4. Join String.
```
String[] strings = {"a", "b", "c", "d", "e"};

  // |a|b|c|d|e , the initial | join is not what we want
  String reduce = Arrays.stream(strings).reduce("", (a, b) -> a + "|" + b);

  // a|b|c|d|e, filter the initial "" empty string
  String reduce2 = Arrays.stream(strings).reduce("", (a, b) -> {
      if (!"".equals(a)) {
          return a + "|" + b;
      } else {
          return b;
      }
  });

  // a|b|c|d|e , better uses the Java 8 String.join :)
  String join = String.join("|", strings);
```
5. Map & Reduce

   A simple map and reduce example to sum BigDecimal from a list of invoices.
```
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class JavaReduce {

    public static void main(String[] args) {

        List<Invoice> invoices = Arrays.asList(
                new Invoice("A01", BigDecimal.valueOf(9.99), BigDecimal.valueOf(1)),
                new Invoice("A02", BigDecimal.valueOf(19.99), BigDecimal.valueOf(1.5)),
                new Invoice("A03", BigDecimal.valueOf(4.99), BigDecimal.valueOf(2))
        );

        BigDecimal sum = invoices.stream()
                .map(x -> x.getQty().multiply(x.getPrice()))    // map
                .reduce(BigDecimal.ZERO, BigDecimal::add);      // reduce

        System.out.println(sum);    // 49.955
        System.out.println(sum.setScale(2, RoundingMode.HALF_UP));  // 49.96

    }

}

class Invoice {

    String invoiceNo;
    BigDecimal price;
    BigDecimal qty;

    // getters, stters n constructor
}
```
flatMap() - we can use the flatMap to convert the above 2 levels Stream into one Stream level or a 2d array into a 1d array.
```
 private static final List<List<String>> arrayListOfNames = List.of(
            List.of("Mariam", "Alex", "Ismail"),
            List.of("John", "Alesha", "Andre"),
            List.of("Susy", "Ali")
    );

    @BeforeEach
    void setUp() {
        System.out.println(arrayListOfNames);
    }

    @Test
    public void withoutFlatMap() throws Exception {
        // [Mariam, Alex, Ismail, John, Alesha, Andre, Susy, Ali]
        List<String> names = new ArrayList<>();
        for (List<String> strings : arrayListOfNames) {
            names.addAll(strings);
        }
        System.out.println(names);
    }

    @Test
    public void withFlatMap() throws Exception {
        // [Mariam, Alex, Ismail, John, Alesha, Andre, Susy, Ali]
        List<String> names = arrayListOfNames.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    public void flatMapWithOptionals() {
        List<Optional<String>> optionals = List.of(
                Optional.of("Amigos"),
                Optional.of("Code")
        );

        List<String> list = optionals.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println(list);
    }
```
This example uses .stream() to convert a List into a stream of objects, and each object contains a set of books, and we can use flatMap to produces a stream containing all the book in all the objects.

In the end, we also filter out the book containing the word python and collect a Set to remove the duplicated book.
```
public class Developer {

    private Integer id;
    private String name;
    private Set<String> book;

    //getters, setters, toString

    public void addBook(String book) {
        if (this.book == null) {
            this.book = new HashSet<>();
        }
        this.book.add(book);
    }
}

Developer o1 = new Developer();
        o1.setName("mkyong");
        o1.addBook("Java 8 in Action");
        o1.addBook("Spring Boot in Action");
        o1.addBook("Effective Java (3nd Edition)");

        Developer o2 = new Developer();
        o2.setName("zilap");
        o2.addBook("Learning Python, 5th Edition");
        o2.addBook("Effective Java (3nd Edition)");

        List<Developer> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);

       Set<String> collect = list.stream()
                        .map(x -> x.getBook())                              //  Stream<Set<String>>
                        .flatMap(x -> x.stream())                           //  Stream<String>
                        .filter(x -> !x.toLowerCase().contains("python"))   //  filter python book
                        .collect(Collectors.toSet());                       //  remove duplicated

        collect.forEach(System.out::println);
        
Spring Boot in Action
Effective Java (3nd Edition)
Java 8 in Action
```
### Aggregate Functions
count() - returns values quantity 
```
 @Test
    public void count() throws Exception {
        List<Car> cars = MockData.getCars();
        long count = cars.stream()
                .filter(car -> car.getMake().equalsIgnoreCase("Ford"))
                .filter(car -> car.getYear() > 2010)
                .count();
        System.out.println(count);//10
    }
```
min()
```
@Test
    public void min() throws Exception {
        List<Car> cars = MockData.getCars();
        double min = cars.stream()
                .mapToDouble(Car::getPrice)
                .min()
                .orElse(0);
        System.out.println(min);
    }
```
max()
```
@Test
    public void max() throws Exception {
        List<Car> cars = MockData.getCars();
        double max = cars.stream()
                .mapToDouble(Car::getPrice)
                .max()
                .orElse(0);
        System.out.println(max);
    }
```
average()
```
@Test
    public void average() throws Exception {
        List<Car> cars = MockData.getCars();
        double average = cars.stream()
                .mapToDouble(Car::getPrice)
                .average()
                .orElse(0);
        System.out.println(average);
    }
```
sum()
```
@Test
    public void sum() throws Exception {
        List<Car> cars = MockData.getCars();
        double sum = cars.stream()
                .mapToDouble(Car::getPrice)
                .sum();
        System.out.println(BigDecimal.valueOf(sum));
    }
```
summaryStatistics() - method that can be used to get multiple statistical properties of a stream (minimum value, average value, etc.).
```
@Test
    public void statistics() throws Exception {
        List<Car> cars = MockData.getCars();
        DoubleSummaryStatistics statistics = cars.stream()
                .mapToDouble(Car::getPrice)
                .summaryStatistics();
        System.out.println(statistics.getCount());
        System.out.println(statistics.getMin());
        System.out.println(statistics.getMax());
        System.out.println(statistics.getAverage());
        System.out.println(BigDecimal.valueOf(statistics.getSum()));
    }
```
Assume we have a list of people. Our goal is to get the minimum and maximum age of the people in the list using streams.

The problem here is that the computation of the minimum and maximum values are terminal stream operations. So we need to come up with our own reduction implementation or create a new stream for every computation. A naive implementation might look like this:
```
List<Person> list = Arrays.asList(
new Person("John Blue", 28),
new Person("Anna Brown", 53),
new Person("Paul Black", 47)
);

int min = list.stream()
.mapToInt(Person::getAge)
.min()
.orElseThrow(NoSuchElementException::new);

int max = list.stream()
.mapToInt(Person::getAge)
.max()
.orElseThrow(NoSuchElementException::new);
```
Luckily Java provides a much simpler way to do this using the summaryStatistics() method:
```
IntSummaryStatistics statistics = list.stream()
.mapToInt(Person::getAge)
.summaryStatistics();

int min = statistics.getMin();
int max = statistics.getMax();
```
IntSummaryStatistics also provides methods to obtain the count and sum of the stream elements.

#### groupingBy()
1. Group by a List and display the total count of it.
```
public static void main(String[] args) {
        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        System.out.println(result);//papaya=1, orange=1, banana=2, apple=3
    }
```
2. Add sorting.
```
public static void main(String[] args) {
        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        Map<String, Long> finalMap = new LinkedHashMap<>();

        //Sort a map and add to finalMap
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

        System.out.println(finalMap);//apple=3, banana=2, papaya=1, orange=1
    }
```
3. Group by the name + Count or Sum the Qty.
POJO
```
public class Item {

    private String name;
    private int qty;
    private BigDecimal price;

    //constructors, getter/setters 
}
```
```
public static void main(String[] args) {
        //3 apple, 2 banana, others 1
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
        );

        Map<String, Long> counting = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.counting()));

        System.out.println(counting);

        Map<String, Integer> sum = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

        System.out.println(sum);

    }
}
//Group by + Count
{
	papaya=1, banana=2, apple=3, orang=1, watermelon=1
}

//Group by + Sum qty
{
	papaya=20, banana=30, apple=40, orang=10, watermelon=10
}
```
4. Group by Price – Collectors.groupingBy and Collectors.mapping() example.
```
public static void main(String[] args) {
        //3 apple, 2 banana, others 1
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
                );

		//group by price
        Map<BigDecimal, List<Item>> groupByPriceMap = 
			items.stream().collect(Collectors.groupingBy(Item::getPrice));

        System.out.println(groupByPriceMap);

		// group by price, uses 'mapping' to convert List<Item> to Set<String>
        Map<BigDecimal, Set<String>> result =
                items.stream().collect(
                        Collectors.groupingBy(Item::getPrice,
                                Collectors.mapping(Item::getName, Collectors.toSet())
                        )
                );
        System.out.println(result);
    }
//group by price 
    19.99=[
			Item{name='banana', qty=20, price=19.99}, 
			Item{name='banana', qty=10, price=19.99}
		], 
	29.99=[
			Item{name='orang', qty=10, price=29.99}, 
			Item{name='watermelon', qty=10, price=29.99}
		], 
	9.99=[
			Item{name='apple', qty=10, price=9.99}, 
			Item{name='papaya', qty=20, price=9.99}, 
			Item{name='apple', qty=10, price=9.99}, 
			Item{name='apple', qty=20, price=9.99}
		]
}

//group by + mapping to Set
{
	19.99=[banana], 
	29.99=[orang, watermelon], 
	9.99=[papaya, apple]
}
```
### Sorting Elements and Objects
```
    @Test
    public void sortingStreamOfElements() throws IOException {
        List<Person> people = MockData.getPeople();
        List<String> sorted = people.stream()
                .map(Person::getFirstName)
                .sorted()
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    @Test
    public void sortingStreamOfElementsReverse() throws IOException {
        List<Person> people = MockData.getPeople();

        List<String> sorted = people.stream()
                .map(Person::getFirstName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    @Test
    public void sortingStreamOfObjets() throws IOException {
        List<Person> people = MockData.getPeople();

        Comparator<Person> comparing = Comparator
                .comparing(Person::getEmail)
                .reversed()
                .thenComparing(Person::getFirstName);

        List<Person> sort = people.stream()
                .sorted(comparing)
                .collect(Collectors.toList());
        sort.forEach(System.out::println);
    }

    @Test
    public void topTenMostExpensiveBlueCars() throws IOException {
        List<Car> cars = MockData.getCars();
        List<Car> topTen = cars.stream()
                .filter(car -> car.getColor().equalsIgnoreCase("blue"))
                .sorted(Comparator.comparing(Car::getPrice).reversed())
                .limit(10)
                .collect(Collectors.toList());
        topTen.forEach(System.out::println);
    }

}
```
### Joining Strings
without stream
```
 @Test
    public void joiningStrings() throws Exception {
        List<String> names = List.of("anna", "john", "marcos", "helena", "yasmin");
        // "Anna, John, Marcos, Helena, Yasmin"
        StringBuilder join = new StringBuilder();

        for (String name : names) {
            join.append(name.substring(0, 1).toUpperCase())
                    .append(name.substring(1))
                    .append(", ");
        }

        System.out.println(join);//Anna, John, Marcos, Helena, Yasmin, 
        System.out.println(join.substring(0, join.length() - 2));//Anna, John, Marcos, Helena, Yasmin
    }
```
with stream
```
 @Test
    public void joiningStringsWithStream() throws Exception {
        List<String> names = List.of("anna", "john", "marcos", "helena", "yasmin");
        // "Anna, John, Marcos, Helena, Yasmin"
        String join = names.stream()
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .collect(Collectors.joining(", "));
        System.out.println(join);//Anna, John, Marcos, Helena, Yasmin
    }
```
### Collectors
Stream.**collect()** is one of the Java 8's Stream API‘s terminal methods. It allows us to perform mutable fold operations (repackaging elements to some data structures and applying some additional logic, concatenating them, etc.) on data elements held in a Stream instance.

The strategy for this operation is provided via the Collector interface implementation.

1. **Collectors.toList()**
```
List<String> result = givenList.stream()
  .collect(Collectors.toList());
```

2. **Collectors.toUnmodifiableList()**
```
List<String> result = givenList.stream()
  .collect(Collectors.toUnmodifiableList());
```
Now if we try to modify the result List, we'll get an UnsupportedOperationException

3. **Collectors.toSet()**
```
Set<String> result = givenList.stream()
  .collect(Collectors.toSet());
```

4. **Collectors.toUnmodifiableSet()**
```
Set<String> result = givenList.stream()
  .collect(Collectors.toUnmodifiableSet());
```

5. **Collectors.toCollection()**
```
List<String> result = givenList.stream()
  .collect(Collectors.toCollection(LinkedList::new));
```

6. **Collectors.toMap()**
   The toMap collector can be used to collect Stream elements into a Map instance. To do this, we need to provide two functions:
- keyMapper
- valueMapper

   We'll use keyMapper to extract a Map key from a Stream element, and valueMapper to extract a value associated with a given key.

Let's collect those elements into a Map that stores strings as keys and their lengths as values:
```
Map<String, Integer> result = givenList.stream()
  .collect(Collectors.toMap(Function.identity(), String::length));
```

7. **Collectors.collectingAndThen()**

   CollectingAndThen is a special collector that allows us to perform another action on a result straight after collecting ends.
   Let's collect Stream elements to a List instance, and then convert the result into an ImmutableList instance:
```
List<String> result = givenList.stream()
  .collect(Collectors.collectingAndThen(toList(), ImmutableList::copyOf));
```

8. **Collectors.joining()**

Joining collector can be used for joining Stream<String> elements.

We can join them together by doing:
```
List<String> listWithDuplicates = Arrays.asList("a", "bb", "c", "d", "bb");

String result = givenList.stream()
  .collect(Collectors.joining());

//abbcccdd
```
We can also specify custom separators, prefixes, postfixes:
```
String result = givenList.stream()
  .collect(joining(" "));//a bb ccc dd
```
We can also write:
```
String result = givenList.stream()
  .collect(joining(" ", "PRE-", "-POST"));//PRE-a bb ccc dd-POST
```
9. **Collectors.counting()**

    Counting is a simple collector that allows for the counting of all Stream elements.

Now we can write:
```
Long result = givenList.stream()
  .collect(counting());
```
10. **Collectors.summarizingDouble/Long/Int()**

SummarizingDouble/Long/Int is a collector that returns a special class containing statistical information about numerical data in a Stream of extracted elements.

We can obtain information about string lengths by doing:
```
DoubleSummaryStatistics result = givenList.stream()
  .collect(summarizingDouble(String::length));
  
assertThat(result.getAverage()).isEqualTo(2);//true
assertThat(result.getCount()).isEqualTo(4);//true
assertThat(result.getMax()).isEqualTo(3);//true
assertThat(result.getMin()).isEqualTo(1);//true
assertThat(result.getSum()).isEqualTo(8);//true
```

11. **Collectors.averagingDouble/Long/Int()**

AveragingDouble/Long/Int is a collector that simply returns an average of extracted elements.

We can get the average string length by doing:

```
Double result = givenList.stream()
.collect(averagingDouble(String::length));
```

12. **Collectors.summingDouble/Long/Int()**

SummingDouble/Long/Int is a collector that simply returns a sum of extracted elements

We can get the sum of all string lengths by doing:
```
Double result = givenList.stream()
  .collect(summingDouble(String::length));
```

13. **Collectors.maxBy()/minBy()**

MaxBy/MinBy collectors return the biggest/smallest element of a Stream according to a provided Comparator instance.

We can pick the biggest element by doing:
```
Optional<String> result = givenList.stream()
.collect(maxBy(Comparator.naturalOrder()));
```

We can see that the returned value is wrapped in an Optional instance. This forces users to rethink the empty collection corner case.

14. **Collectors.groupingBy()**

GroupingBy collector is used for grouping objects by some property, and then storing the results in a Map instance.

We can group them by string length, and store the grouping results in Set instances:
```
Map<Integer, Set<String>> result = givenList.stream()
.collect(groupingBy(String::length, toSet()));
```

15. **Collectors.partitioningBy()**

PartitioningBy is a specialized case of groupingBy that accepts a Predicate instance, and then collects Stream elements into a Map instance that stores Boolean values as keys and collections as values. Under the “true” key, we can find a collection of elements matching the given Predicate, and under the “false” key, we can find a collection of elements not matching the given Predicate.

We can write:

```
Map<Boolean, List<String>> result = givenList.stream()
  .collect(partitioningBy(s -> s.length() > 2));//{false=["a", "bb", "dd"], true=["ccc"]}
```

16. **Collectors.teeing()**

```
List<Integer> numbers = Arrays.asList(42, 4, 2, 24);
Optional<Integer> min = numbers.stream().collect(minBy(Integer::compareTo));
Optional<Integer> max = numbers.stream().collect(maxBy(Integer::compareTo));
// do something useful with min and max
```

Here we're using two different collectors, and then combining the results of those two to create something meaningful. Before Java 12, in order to cover such use cases, we had to operate on the given Stream twice, store the intermediate results into temporary variables, and then combine those results afterwards.

Fortunately, Java 12 offers a built-in collector that takes care of these steps on our behalf; all we have to do is provide the two collectors and the combiner function.

Since this new collector tees the given stream towards two different directions, it's called teeing:

```
numbers.stream().collect(teeing(
minBy(Integer::compareTo), // The first collector
maxBy(Integer::compareTo), // The second collector
(min, max) -> // Receives the result from those collectors and combines them
));
```

17. **Custom collectors**

If we want to write our own Collector implementation, we need to implement the Collector interface, and specify its three generic parameters:

```
public interface Collector<T, A, R> {...}
```

- T – the type of objects that will be available for collection
- A – the type of a mutable accumulator object
- R – the type of a final result

Let's write an example Collector for collecting elements into an ImmutableSet instance. We start by specifying the right types:

```
private class ImmutableSetCollector<T>
implements Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> {...}
```

Since we need a mutable collection for internal collection operation handling, we can't use ImmutableSet. Instead, we need to use some other mutable collection, or any other class that could temporarily accumulate objects for us. In this case, we will go with an ImmutableSet.Builder and now we need to implement 5 methods:

```
Supplier<ImmutableSet.Builder<T>> supplier()
BiConsumer<ImmutableSet.Builder<T>, T> accumulator()
BinaryOperator<ImmutableSet.Builder<T>> combiner()
Function<ImmutableSet.Builder<T>, ImmutableSet<T>> finisher()
Set<Characteristics> characteristics()
```

The supplier() method returns a Supplier instance that generates an empty accumulator instance. So in this case, we can simply write:

```
@Override
public Supplier<ImmutableSet.Builder<T>> supplier() {
return ImmutableSet::builder;
}
```

The accumulator() method returns a function that is used for adding a new element to an existing accumulator object. So let's just use the Builder‘s add method:

```
@Override
public BiConsumer<ImmutableSet.Builder<T>, T> accumulator() {
return ImmutableSet.Builder::add;
}
```

The combiner() method returns a function that is used for merging two accumulators together:

```
@Override
public BinaryOperator<ImmutableSet.Builder<T>> combiner() {
return (left, right) -> left.addAll(right.build());
}
```

The finisher() method returns a function that is used for converting an accumulator to final result type. So in this case, we'll just use Builder‘s build method:

```
@Override
public Function<ImmutableSet.Builder<T>, ImmutableSet<T>> finisher() {
return ImmutableSet.Builder::build;
}
```

The characteristics() method is used to provide Stream with some additional information that will be used for internal optimizations. In this case, we don't pay attention to the elements order in a Set because we'll use Characteristics.UNORDERED. To obtain more information regarding this subject, check Characteristics‘ JavaDoc:

```
@Override 
public Set<Characteristics> characteristics() {
return Sets.immutableEnumSet(Characteristics.UNORDERED);
}
```

Here is the complete implementation along with the usage:

```
public class ImmutableSetCollector<T> 
implements Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> {

@Override
public Supplier<ImmutableSet.Builder<T>> supplier() {
return ImmutableSet::builder;
}

@Override
public BiConsumer<ImmutableSet.Builder<T>, T> accumulator() {
return ImmutableSet.Builder::add;
}

@Override
public BinaryOperator<ImmutableSet.Builder<T>> combiner() {
return (left, right) -> left.addAll(right.build());
}

@Override
public Function<ImmutableSet.Builder<T>, ImmutableSet<T>> finisher() {
return ImmutableSet.Builder::build;
}

@Override
public Set<Characteristics> characteristics() {
return Sets.immutableEnumSet(Characteristics.UNORDERED);
}

public static <T> ImmutableSetCollector<T> toImmutableSet() {
return new ImmutableSetCollector<>();
}
```

Finally, here in action:

```
List<String> givenList = Arrays.asList("a", "bb", "ccc", "dddd");

ImmutableSet<String> result = givenList.stream()
.collect(toImmutableSet());
```
	
map() -> Function

forEach() -> Consumer
	
filter() -> Predicate

reduce() -> Optional

	
#### concat()
```java
	Stream<Integer> stream1 = Stream.of(3, 4, 5, 6, 7);
        Stream<Integer> stream2 = Stream.of(33, 44, 55, 66, 77);
        Stream.concat(stream1, stream2).forEach(System.out::println);
```
	
#### count()
```java
	Stream<Integer> stream = Stream.of(33, 44, 55, 66, 66, 77, 77);
        System.out.println(stream.distinct().count());
```
	
