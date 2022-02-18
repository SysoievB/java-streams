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
        
