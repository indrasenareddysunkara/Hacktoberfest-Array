import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PolynomialSolver {

    // Decode value from the given base
    public static int decodeValue(String value, int base) {
        return Integer.parseInt(value, base);
    }

    // Lagrange interpolation to calculate the constant term (c)
    public static double lagrangeInterpolation(List<int[]> points) {
        double constantTerm = 0;

        for (int i = 0; i < points.size(); i++) {
            double term = points.get(i)[1]; // y value
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    term *= (0.0 - points.get(j)[0]) / (points.get(i)[0] - points.get(j)[0]);
                }
            }
            constantTerm += term;
        }

        return constantTerm;
    }

    public static void main(String[] args) {
        try {
            // Load JSON data from file
            String jsonString = new Scanner(new FileReader("C:\Users\SUNKARA AYODHYA\Desktop\CatalogPlacements\src\main\java\input1.json")).useDelimiter("\\Z").next();
            JSONObject data = new JSONObject(jsonString);

            // Parse keys
            JSONObject keys = data.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");

            // Parse (x, y) points from JSON
            List<int[]> points = new ArrayList<>();
            for (String key : data.keySet()) {
                if (key.matches("\\d+")) { // Only numeric keys
                    int x = Integer.parseInt(key);
                    JSONObject value = data.getJSONObject(key);
                    int base = Integer.parseInt(value.getString("base"));
                    int y = decodeValue(value.getString("value"), base);
                    points.add(new int[]{x, y});
                }
            }

            // Calculate the constant term (c)
            double constantTerm = lagrangeInterpolation(points);
            System.out.println("Constant term (c): " + constantTerm);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing the JSON data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}