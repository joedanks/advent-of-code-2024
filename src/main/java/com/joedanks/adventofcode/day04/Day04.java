package com.joedanks.adventofcode.day04;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day04 {

    public Mono<Long> partOne(Flux<String> rawInput) {
        var input = rawInput.index()
                .flatMap(t ->
                        Flux.fromArray(t.getT2().split(""))
                                .index()
                                .map(tt -> Tuples.of(Tuples.of(tt.getT1(), t.getT1()), tt.getT2()))
                );
        var map = input.collectMap(Tuple2::getT1, Tuple2::getT2);
        var x = input.filter(t -> "X".equals(t.getT2()));

        return x.flatMap(t -> Mono.just(t).zipWith(map))
                .map(tt -> {
                    Map<Tuple2<Long, Long>, String> m = tt.getT2();
                    Tuple2<Long, Long> loc = tt.getT1().getT1();
                    return Arrays.stream(Direction.values())
                            .map(direction -> getNextLocations(loc, direction))
                            .map(locations -> locations.stream().map(l -> m.getOrDefault(l, "")).toList().equals(List.of("M", "A", "S")))
                            .filter(b -> b)
                            .count();
                })
                .collectList()
                .map(l -> l.stream().mapToLong(count -> count).sum());
    }

    private List<Tuple2<Long, Long>> getNextLocations(Tuple2<Long, Long> xLocation, Direction direction) {
        return switch (direction) {
            case UP ->
                    List.of(Tuples.of(xLocation.getT1(), xLocation.getT2() - 1), Tuples.of(xLocation.getT1(), xLocation.getT2() - 2), Tuples.of(xLocation.getT1(), xLocation.getT2() - 3));
            case DOWN ->
                    List.of(Tuples.of(xLocation.getT1(), xLocation.getT2() + 1), Tuples.of(xLocation.getT1(), xLocation.getT2() + 2), Tuples.of(xLocation.getT1(), xLocation.getT2() + 3));
            case LEFT ->
                    List.of(Tuples.of(xLocation.getT1() - 1, xLocation.getT2()), Tuples.of(xLocation.getT1() - 2, xLocation.getT2()), Tuples.of(xLocation.getT1() - 3, xLocation.getT2()));
            case RIGHT ->
                    List.of(Tuples.of(xLocation.getT1() + 1, xLocation.getT2()), Tuples.of(xLocation.getT1() + 2, xLocation.getT2()), Tuples.of(xLocation.getT1() + 3, xLocation.getT2()));
            case UP_LEFT ->
                    List.of(Tuples.of(xLocation.getT1() - 1, xLocation.getT2() - 1), Tuples.of(xLocation.getT1() - 2, xLocation.getT2() - 2), Tuples.of(xLocation.getT1() - 3, xLocation.getT2() - 3));
            case UP_RIGHT ->
                    List.of(Tuples.of(xLocation.getT1() + 1, xLocation.getT2() - 1), Tuples.of(xLocation.getT1() + 2, xLocation.getT2() - 2), Tuples.of(xLocation.getT1() + 3, xLocation.getT2() - 3));
            case DOWN_LEFT ->
                    List.of(Tuples.of(xLocation.getT1() - 1, xLocation.getT2() + 1), Tuples.of(xLocation.getT1() - 2, xLocation.getT2() + 2), Tuples.of(xLocation.getT1() - 3, xLocation.getT2() + 3));
            case DOWN_RIGHT ->
                    List.of(Tuples.of(xLocation.getT1() + 1, xLocation.getT2() + 1), Tuples.of(xLocation.getT1() + 2, xLocation.getT2() + 2), Tuples.of(xLocation.getT1() + 3, xLocation.getT2() + 3));
        };
    }

    public Mono<Integer> partTwo(Flux<String> rawInput) {
        var input = rawInput.index()
                .flatMap(t ->
                        Flux.fromArray(t.getT2().split(""))
                                .index()
                                .map(tt -> Tuples.of(Tuples.of(tt.getT1(), t.getT1()), tt.getT2()))
                );
        var map = input.collectMap(Tuple2::getT1, Tuple2::getT2);
        var a = input.filter(t -> "A".equals(t.getT2()));

        return a.flatMap(t -> Mono.just(t).zipWith(map))
                .map(tt -> {
                    Map<Tuple2<Long, Long>, String> m = tt.getT2();
                    Tuple2<Long, Long> loc = tt.getT1().getT1();
                    return getXLocations(loc)
                            .stream()
                            .map(locations -> locations.stream().map(l -> m.getOrDefault(l, "")).toList())
                            .allMatch(matches -> matches.equals(List.of("M", "S")) || matches.equals(List.of("S", "M")));
                })
                .filter(bool -> bool)
                .collectList()
                .map(l -> l.size());
    }

    private List<List<Tuple2<Long, Long>>> getXLocations(Tuple2<Long, Long> aLocation) {
        return List.of(
                List.of(Tuples.of(aLocation.getT1() - 1, aLocation.getT2() - 1), Tuples.of(aLocation.getT1() + 1, aLocation.getT2() + 1)),
                List.of(Tuples.of(aLocation.getT1() + 1, aLocation.getT2() - 1), Tuples.of(aLocation.getT1() - 1, aLocation.getT2() + 1))
        );
    }
}
