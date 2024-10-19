package com.csc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheeseAnalyzer {

    private List<Cheese> cheeseList = new ArrayList<>();

    // Inner class Cheese to represent each cheese in the dataset
    public class Cheese {
        private String milkType;
        private String milkTreatmentType;
        private boolean isOrganic;
        private double moisturePercentage;

        // Constructor
        public Cheese(String milkType, String milkTreatmentType, boolean isOrganic, double moisturePercentage) {
            this.milkType = milkType;
            this.milkTreatmentType = milkTreatmentType;
            this.isOrganic = isOrganic;
            this.moisturePercentage = moisturePercentage;
        }

        // Getters for Cheese attributes
        public String getMilkType() {
            return milkType;
        }

        public String getMilkTreatmentType() {
            return milkTreatmentType;
        }

        public boolean isOrganic() {
            return isOrganic;
        }

        public double getMoisturePercentage() {
            return moisturePercentage;
        }

        // Convenience methods to check milk treatment
        public boolean isPasteurized() {
            return "pasteurized".equalsIgnoreCase(milkTreatmentType);
        }

        public boolean isRaw() {
            return "raw".equalsIgnoreCase(milkTreatmentType);
        }
    }

    // Method to load cheese data from a CSV file or BufferedReader
    public void loadCheeseData(BufferedReader reader) throws IOException {
        String line;
        reader.readLine(); // Skip the header

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");  // Assuming CSV columns are comma-separated
            if (fields.length < 5) continue;   // Skip rows with insufficient data

            String milkType = fields[1].trim();                 // MilkTypeEn
            String milkTreatmentType = fields[2].trim();        // MilkTreatmentTypeEn
            boolean isOrganic = "1".equals(fields[3].trim());   // Organic
            double moisturePercentage = fields[4].trim().isEmpty() ? 0 : Double.parseDouble(fields[4].trim());  // MoisturePercent

            // Add the cheese to the list
            cheeseList.add(new Cheese(milkType, milkTreatmentType, isOrganic, moisturePercentage));
        }
    }

    // Method to process the cheese data and analyze statistics
    public void analyzeCheeseData(BufferedWriter writer) throws IOException {
        int pasteurizedCount = 0;
        int rawCount = 0;
        int organicHighMoistureCount = 0;
        Map<String, Integer> milkTypeCount = new HashMap<>();

        for (Cheese cheese : cheeseList) {
            // Count pasteurized and raw cheeses
            if (cheese.isPasteurized()) {
                pasteurizedCount++;
            } else if (cheese.isRaw()) {
                rawCount++;
            }

            // Count organic cheeses with moisture > 41%
            if (cheese.isOrganic() && cheese.getMoisturePercentage() > 41.0) {
                organicHighMoistureCount++;
            }

            // Count cheeses by milk type
            milkTypeCount.put(cheese.getMilkType(), milkTypeCount.getOrDefault(cheese.getMilkType(), 0) + 1);
        }

        // Find the most common milk type
        String mostCommonMilkType = milkTypeCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        // Write results to the output writer
        writer.write("Pasteurized Milk Count: " + pasteurizedCount + "\n");
        writer.write("Raw Milk Count: " + rawCount + "\n");
        writer.write("Organic Cheeses with Moisture > 41%: " + organicHighMoistureCount + "\n");
        writer.write("Most Common Milk Type: " + mostCommonMilkType + "\n");
        writer.flush();
    }

    // Getter for cheese list for testing purposes
    public List<Cheese> getCheeseList() {
        return cheeseList;
    }
}
