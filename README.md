# Simple example implementation of class and annotation resolution and dynamic proxy handling

This project contains very simple example implementations of:

- class and annotation resolution
- dynamic proxy handling

The code is intended for learning and demonstration purposes only!

## Usage

Clone the repository:

```bash
git clone https://github.com/gappc/java-annotations-and-proxy.git
```

Build the project with Maven:

```bash
mvn clean package
```

Run the main class for class and annotation resolution:

```bash
java -cp target/annotations-and-proxy-1.0-SNAPSHOT.jar dev.gappc.example.annotation.AnnotationDemo
```

> Please note that due implementation limitations when using the JAR, the `SystemClassLoaderScanner` in the `AnnotationDemo` won't find any classes (no JAR support implemented).

Run the main class for dynamic proxy handling:

```bash
java -cp target/annotations-and-proxy-1.0-SNAPSHOT.jar dev.gappc.example.proxy.ProxyDemo
```
