package com.joedanks.adventofcode.day01;

import org.yaml.snakeyaml.util.Tuple;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day01 {

    public Mono<Integer> partOne(Flux<String> rawInput) {
        var input = rawInput.map(s -> s.split(" "))
                .map(ss -> new Tuple<>(ss[0], ss[1]))
                .map(t -> new Tuple<>(Integer.parseInt(t._1()), Integer.parseInt(t._2())));

        var lefts = input.map(Tuple::_1).sort();
        var rights = input.map(Tuple::_2).sort();

        return Flux.zip(lefts, rights)
                .map(t -> Math.abs(t.getT1() - t.getT2()))
                .collectList()
                .map(l -> l.stream().mapToInt(i -> i).sum());
    }

    public Mono<Integer> partTwo(Flux<String> rawInput) {
        var input = rawInput.map(s -> s.split(" "))
                .map(ss -> new Tuple<>(ss[0], ss[1]))
                .map(t -> new Tuple<>(Integer.parseInt(t._1()), Integer.parseInt(t._2())));

        var map = input.map(Tuple::_2).collectMultimap(i -> i);

        return input.map(Tuple::_1)
                .flatMap(t -> Mono.just(t).zipWith(map))
                .map(t -> t.getT1() * t.getT2().getOrDefault(t.getT1(), Collections.emptyList()).size())
//                .doOnNext(t -> System.out.println(t))
                .collectList()
                .map(l -> l.stream().mapToInt(i -> i).sum());
    }
}
