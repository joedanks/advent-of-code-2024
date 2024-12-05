package com.joedanks.adventofcode.utility;

import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.BaseStream;

public class FileInput {
    public Flux<String> readFile(String fileName) {
        try {
            return Flux.using(
                    () -> Files.lines(Paths.get("src", "main", "resources", fileName)),
                    Flux::fromStream,
                    BaseStream::close
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
