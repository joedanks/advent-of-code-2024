package com.joedanks.adventofcode.day05;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 {
    public Mono<Integer> partOne(Flux<String> rawInput) {
        var rules = rawInput.takeUntil(String::isEmpty)
                .skipLast(1)
                .map(s -> s.split("\\|"))
                .map(ss -> Tuples.of(ss[0], ss[1]));
        var updates = rawInput.skipUntil(String::isEmpty)
                .skip(1)
                .map(s -> s.split(","))
                .map(List::of);

        Flux<List<String>> validUpdates = updates.flatMap(u -> Mono.just(u).zipWith(rules.collectList()))
                .flatMap(t -> Flux.fromIterable(t.getT2())
                        .all(rule -> isValid(t.getT1(), rule))
                        .flatMap(b -> b ? Mono.just(t.getT1()) : Mono.empty())
                );

        return validUpdates.map(u -> u.get(Math.abs(u.size() / 2)))
                .collectList()
//                .doOnNext(System.out::println)
                .map(Collection::stream)
                .map(s -> s.mapToInt(Integer::parseInt))
                .map(IntStream::sum);
    }

    public Mono<Integer> partTwo(Flux<String> rawInput) {
        var rules = rawInput.takeUntil(String::isEmpty)
                .skipLast(1)
                .map(s -> s.split("\\|"))
                .map(ss -> Tuples.of(ss[0], ss[1]));
        var updates = rawInput.skipUntil(String::isEmpty)
                .skip(1)
                .map(s -> s.split(","))
                .map(List::of);

        var invalidUpdates = updates.flatMap(u -> Mono.just(u).zipWith(rules.collectList()))
                .flatMap(t ->
                        t.getT2().stream()
                                .allMatch(rule -> isValid(t.getT1(), rule)) ? Mono.empty() : Mono.just(t)
                );

        return invalidUpdates.map(t -> {
                    List<String> update = new ArrayList<>(t.getT1());
                    while (t.getT2().stream().anyMatch(rule -> !isValid(update, rule))) {
                        t.getT2().stream()
                                .filter(rule -> !isValid(update, rule))
                                .findFirst()
                                .ifPresent(rule -> {
                                    update.remove(rule.getT1());
                                    update.add(update.indexOf(rule.getT2()), rule.getT1());
                                });
                    }
                    return update;
                }).map(u -> u.get(Math.abs(u.size() / 2)))
                .collectList()
//                .doOnNext(System.out::println)
                .map(Collection::stream)
                .map(s -> s.mapToInt(Integer::parseInt))
                .map(IntStream::sum);
    }

    private static boolean isValid(List<String> update, Tuple2<String, String> rule) {
        var rule1Index = update.indexOf(rule.getT1());
        var rule2Index = update.indexOf(rule.getT2());
        return rule1Index == -1
                || rule2Index == -1
                || rule1Index < rule2Index;
    }


}
