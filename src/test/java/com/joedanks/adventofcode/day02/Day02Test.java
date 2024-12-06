package com.joedanks.adventofcode.day02;

import com.joedanks.adventofcode.utility.FileInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private FileInput fileInput = new FileInput();
    private Day02 code = new Day02();

    @Test
    void partOneShouldPassTestInput() {
        var input = fileInput.readFile("Day02_testData.txt");

        var result = code.partOne(input).block();

        assertEquals(2, result);
    }

    @Test
    void partOneShouldPassRealInput() {
        var input = fileInput.readFile("Day02_input.txt");

        var result = code.partOne(input).block();

        assertEquals(463, result);
    }

    @Test
    void partTwoShouldPassTestInput() {
        var input = fileInput.readFile("Day02_testData.txt");

        var result = code.partTwo(input).block();

        assertEquals(31, result);
    }

    @Test
    void partTwoShouldPassRealInput() {
        var input = fileInput.readFile("Day02_input.txt");

        var result = code.partTwo(input).block();

        assertEquals(24316233, result);
    }
}