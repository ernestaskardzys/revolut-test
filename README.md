### Compilation

In order to compile the project, you need to perform the following:

```bash
$ ./gradlew clean build
```

## Running in a command line

```bash
$ java -jar build/libs/revolut-test-all.jar
```

## Running with Docker

To build the project, run:

```bash
$ docker build -t revolut-rest .
$ docker run -i -t -p 8080:8080 revolut-rest
```

## Testing

To test the project, try:

```bash
$ curl http://localhost:8080/hello/5
```