package com.joedanks.adventofcode.day04;

import com.joedanks.adventofcode.utility.FileInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    private FileInput fileInput = new FileInput();
    private Day04 code = new Day04();

    @Test
    void partOneShouldPassTestInput() {
        var input = fileInput.readFile("Day04_testData.txt");

        var result = code.partOne(input).block();

        assertEquals(18, result);
    }

    @Test
    void partOneShouldPassRealInput() {
        var input = fileInput.readFile("Day04_input.txt");

        var result = code.partOne(input).block();

        assertEquals(2504, result);
    }

    @Test
    void partTwoShouldPassTestInput() {
        var input = fileInput.readFile("Day04_testData.txt");

        var result = code.partTwo(input).block();

        assertEquals(9, result);
    }

    @Test
    void partTwoShouldPassRealInput() {
        var input = fileInput.readFile("Day04_input.txt");

        var result = code.partTwo(input).block();

        assertEquals(1923, result);
    }
}