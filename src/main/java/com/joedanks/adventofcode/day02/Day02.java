package com.joedanks.adventofcode.day02;

import org.yaml.snakeyaml.util.Tuple;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 {

    public Mono<Integer> partOne(Flux<String> rawInput) {
        var safe = rawInput.map(s -> s.split(" "))
                .map(List::of)
                .map(l -> l.stream().map(Integer::parseInt).toList())
                .map(r ->
                        IntStream.range(0, r.size() - 1).mapToObj(i -> Tuples.of(r.get(i), r.get(i + 1))).toList()
                )
                .map(pairs -> Tuples.of(
                        pairs.getFirst().getT1() > pairs.getFirst().getT2() ? "-" : "+",
                        pairs)
                )
                .filter(t -> t.getT2()
                        .stream()
                        .allMatch(pair -> {
                            var diff = pair.getT1() - pair.getT2();
                            return t.getT1().equals("+") ? diff <= -1 && diff >= -3 : diff >= 1 && diff <= 3;
                        }));

        return safe.collectList().map(List::size);
    }

    public Mono<Integer> partTwo(Flux<String> rawInput) {
        return Mono.just(0);
    }
}
