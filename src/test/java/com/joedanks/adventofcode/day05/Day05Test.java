package com.joedanks.adventofcode.day05;

import com.joedanks.adventofcode.utility.FileInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    private FileInput fileInput = new FileInput();
    private Day05 code = new Day05();

    @Test
    void partOneShouldPassTestInput() {
        var input = fileInput.readFile("Day05_testData.txt");

        var result = code.partOne(input).block();

        assertEquals(143, result);
    }

    @Test
    void partOneShouldPassRealInput() {
        var input = fileInput.readFile("Day05_input.txt");

        var result = code.partOne(input).block();

        assertEquals(4281, result);
    }

    @Test
    void partTwoShouldPassTestInput() {
        var input = fileInput.readFile("Day05_testData.txt");

        var result = code.partTwo(input).block();

        assertEquals(123, result);
    }

    @Test
    void partTwoShouldPassRealInput() {
        var input = fileInput.readFile("Day05_input.txt");

        var result = code.partTwo(input).block();

        assertEquals(5466, result);
    }
}