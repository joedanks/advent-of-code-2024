package com.joedanks.adventofcode.day02;

import org.yaml.snakeyaml.util.Tuple;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
                        .allMatch(pair ->
                                isSafe(t.getT1(), pair)
                        ));

        return safe.collectList().map(List::size);
    }

    private boolean isSafe(String direction, Tuple2<Integer, Integer> pair) {
        var diff = pair.getT1() - pair.getT2();
        return direction.equals("+") ? diff <= -1 && diff >= -3 : diff >= 1 && diff <= 3;
    }

    public Mono<Integer> partTwo(Flux<String> rawInput) {
        var safe = rawInput.map(s -> s.split(" "))
                .map(List::of)
                .map(l -> l.stream().map(Integer::parseInt).toList())
                .map(l -> {
                    List<List<Integer>> matrix = IntStream.range(0, l.size()).mapToObj(index -> {
                        List<Integer> copy = new ArrayList<>(l);
                        copy.remove(index);
                        return copy;
                    }).collect(Collectors.toList());
                    matrix.addFirst(l);
                    return Flux.fromIterable(matrix);
                })
                .map(all ->
                        all.map(each ->
                                IntStream.range(0, each.size() - 1).mapToObj(i -> Tuples.of(each.get(i), each.get(i + 1))).toList()
                        )
                )
                .map(all ->
                        all.map(pairs -> Tuples.of(
                                pairs.getFirst().getT1() > pairs.getFirst().getT2() ? "-" : "+",
                                pairs))
                ).map(all ->
                        all.filter(t -> t.getT2().stream()
                                .allMatch(pair -> isSafe(t.getT1(), pair)))
                ).flatMap(all -> all.collectList().map(safeRecords -> !safeRecords.isEmpty()))
                .filter(b -> b);


        return safe.collectList().map(List::size);
    }
}
