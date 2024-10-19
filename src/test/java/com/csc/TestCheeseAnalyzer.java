package com.csc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCheeseAnalyzer {

    private CheeseAnalyzer cheeseAnalyzer;

    @BeforeEach
    public void setUp() {
        cheeseAnalyzer = new CheeseAnalyzer();
    }

    @Test
    public void testLoadCheeseData() throws IOException {
        // Prepare a sample CSV content
        String csvContent = "CheeseId,MilkTypeEn,MilkTreatmentTypeEn,Organic,MoisturePercent\n"
                + "1,Cow,Pasteurized,1,45.0\n"
                + "2,Goat,Raw,0,38.5\n"
                + "3,Buffalo,Pasteurized,1,42.0\n";

        // Simulate reading from a CSV file using an InputStream
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        cheeseAnalyzer.loadCheeseData(new BufferedReader(new InputStreamReader(inputStream)));

        // Verify that the cheese list was correctly populated
        assertEquals(3, cheeseAnalyzer.getCheeseList().size());
    }

    @Test
    public void testAnalyzeCheeseData() throws IOException {
        // Prepare a sample CSV content
        String csvContent = "CheeseId,MilkTypeEn,MilkTreatmentTypeEn,Organic,MoisturePercent\n"
                + "1,Cow,Pasteurized,1,45.0\n"
                + "2,Goat,Raw,0,38.5\n"
                + "3,Buffalo,Pasteurized,1,42.0\n";

        // Load data from the mock CSV content
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        cheeseAnalyzer.loadCheeseData(new BufferedReader(new InputStreamReader(inputStream)));

        // Simulate writing to an output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        // Analyze the cheese data and verify the results
        cheeseAnalyzer.analyzeCheeseData(writer);
        writer.flush();

        // Expected output after analyzing the cheese data
        String expectedOutput = "Pasteurized Milk Count: 2\n"
                + "Raw Milk Count: 1\n"
                + "Organic Cheeses with Moisture > 41%: 2\n"
                + "Most Common Milk Type: Cow\n";

        // Verify that the output matches the expected result
        assertEquals(expectedOutput, outputStream.toString());
    }
}
