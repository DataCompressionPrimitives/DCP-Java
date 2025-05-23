# Data Compression Primitives - Java

This repository is the Java implementation of the Data Compression Primitives (DCP) project. The goal of DCP is to map out and implement the fundamental data structures and algorithms in data compression, making them accessible, readable, and reusable for all developers.

## Project Philosophy

Why isn't all the great data compression code in ONE usable library? DCP aims to provide a comprehensive, language-agnostic set of primitives so that anyone can build robust, efficient data systems from the ground up. We value readable and modifiable code over raw speed—making it easy for others to learn from, use, and extend.

## Call for Volunteers

We request volunteers to implement the remaining primitives in this Java library. If you are interested in implementing these primitives in another language, fork the main [DataCompressionPrimitives repository](https://github.com/DataCompressionPrimitives/DataCompressionPrimitives), create your language-specific implementation, and link it back in a pull request. 

## List of Implementations

See [IMPLEMENTATIONS.md](IMPLEMENTATIONS.md) for a detailed, up-to-date status of all implemented Data Compression Primitives in this Java library.

## Building and Testing

This project is built with Gradle and targets Java 8 for maximum compatibility.The goal is to support as many developer environments as possible.

To build the project:
```bash
./gradlew build
```

To run tests:
```bash
./gradlew test
```

To automatically format all source files:
```bash
./gradlew format
```

## License

Please see the included [LICENSE.md](LICENSE.md) in this repository.

## Feedback and Contributing

Please file a GitHub issue if you find any bugs or have suggestions. Pull requests are very welcome!
