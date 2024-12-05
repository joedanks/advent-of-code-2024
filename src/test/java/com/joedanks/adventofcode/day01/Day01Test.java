package com.joedanks.adventofcode.day01;

import com.joedanks.adventofcode.utility.FileInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    private FileInput fileInput = new FileInput();
    private Day01 day01 = new Day01();

    @Test
    void partOneShouldPassTestInput() {
        var input = fileInput.readFile("Day01_testData.txt");

        var result = day01.partOne(input).block();

        assertEquals(11, result);
    }

    @Test
    void partOneShouldPassRealInput() {
        var input = fileInput.readFile("Day01_input.txt");

        var result = day01.partOne(input).block();

        assertEquals(1666427, result);
    }
}