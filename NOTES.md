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
