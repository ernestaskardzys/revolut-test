### Some notes

- You need to have Java 11 SDK installed on your computer in order to run the application
- Each account has 64 symbol length account number (for instance - 54b0c38c723c7852334526fd0127bc165ea0b9d9b4034bb6b93195704766b677), which is similar to the one used by Bitcoin protocol
- Each account is created with 100 of fake "money" - to simplify the application

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

To run the project with Docker, run:

```bash
$ docker build -t revolut-rest .
$ docker run -i -t -p 8080:8080 revolut-rest
```

## Testing

- To open an account, call `POST /`. For instance:

```bash
$ curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"name": "John Doe"}' \
    http://localhost:8080/account/

<...>
{"name":"John Doe","accountNumber":"9b09398ed19cca1600c34a5ed6c3b3145d9f11707738d25a5ef962f0c2a06e8d","balance":100.0}
```

- To get information about the account, call `GET /{accountNumber}`. For instance:

```bash
$ curl http://localhost:8080/account/f8ea01f0c70327091050805f825c8b5cd168d7d8376c58f05d68b87ad40854c2

<...>
{"name":"Jane Doe","accountNumber":"f8ea01f0c70327091050805f825c8b5cd168d7d8376c58f05d68b87ad40854c2","balance":100.0}
```

- To transfer money between accounts, create two accounts (please see above) and call `PUT /transfer`. For instance:
```bash
curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{ "accountFrom": "fc6980762fe2a6ff6c1a293ee9be0995f56deb854874e65918bbb6ae6cbd316f", "accountTo": "e0d9584fd495f3e82ad97fe0a6a73d087a578e21edabfb89cb598268d6992a66", "amount": "50" }' \
  http://localhost:8080/account/transfer
```

- In case error appears, you'd get an exception in JSON format. For instance:

```bash
{
    "errorCode":500,
    "message":"Account fa03cf8812d6bc4e480e64783d866f72d12099aba281981890465d8fa8fceb29 does not have enough balance to transfer 50.0"
}
```

### Technologies used

- Jetty
- Jersey
- HK2
- Jackson
- jUnit 5