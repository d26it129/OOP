# MiniBank (CEUE203 - OOP)

A console banking application built across the semester (Practicals 1-14).

Features: open Savings / Current / Fixed Deposit accounts, deposit, withdraw, transfer,
statement, list and search accounts, concurrent batch deposits on a thread pool,
end-of-day report, and persistence (accounts are saved on exit and loaded on start).

## Requirements
- JDK 17 or newer
- JUnit 5 (only to run the tests)

## Project layout
```
src/MiniBank.java            menu and entry point
src/model/                   Account, SavingsAccount, CurrentAccount, FixedDepositAccount, Customer, interfaces
src/model/annotation/        @Id, @Positive, @MaxLength
src/exception/               BankException and subclasses
src/util/                    Validator, CommandParser, StatementFormatter, AnnotationValidator, AuditSession
src/service/                 BankService, Repository<T>, TransactionProcessor, StatePersister, ...
test/BankServiceTest.java    JUnit 5 tests
```

## Build and run
```
javac -d out $(find src -name "*.java")
jar cfm minibank.jar manifest.txt -C out .
java -jar minibank.jar
```
`manifest.txt` contains the single line `Main-Class: MiniBank`.

## Run the tests
Download `junit-platform-console-standalone-<version>.jar` (JUnit 5), then:
```
javac -cp out:junit-platform-console-standalone.jar -d tout test/BankServiceTest.java
java -jar junit-platform-console-standalone.jar -cp out:tout --scan-classpath
```

## Data files
The program creates a `data/` folder at run time (`accounts.dat`, `logs/transactions.log`, `report.txt`).
These files are generated, so they are not committed (see `.gitignore`).
