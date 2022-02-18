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

