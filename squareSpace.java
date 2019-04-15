import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static final double RADIUS_OF_EARTH = 6371;

    /**
     * Calculates the distance between two points
     *
     * @param latitude1  Latitude of first coordinate
     * @param longitude1 Longitude of first coordinate
     * @param latitude2  Latitude of second coordinate
     * @param longitude2 Longitude of second coordinate
     * @return Distance between the two points, in km
     */
    private static double Haversine(double latitude1, double longitude1, double latitude2, double longitude2) {
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        longitude1 = Math.toRadians(longitude1);
        longitude2 = Math.toRadians(longitude2);

        return (2 * RADIUS_OF_EARTH
                * Math.asin(Math.sqrt(Math.sin((latitude2 - latitude1) / 2) * Math.sin((latitude2 - latitude1) / 2)
                        + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin((longitude2 - longitude1) / 2)
                                * Math.sin((longitude2 - longitude1) / 2))));
    }
        /* returns the closest airport given the coordinates*/
    private static String closestAirport(double lat, double lon, Map<String, List<Double>> airportCoords) {
        String closest = null;
        double minDist = Double.MAX_VALUE;
        for (String airport : airportCoords.keySet()) {
            List<Double> coords = airportCoords.get(airport);
            double airportDist = Haversine(lat, lon, coords.get(0), coords.get(1));
            if (airportDist < minDist) {
                closest = airport;
                minDist = airportDist;
            }
        }
        return closest;
    }
    /* Calculates the speed of the airplane*/
    private static double flightSpeed(double prevLat, double prevLon, double lat, double lon) {
        return Haversine(prevLat, prevLon, lat, lon) * 3600;
    }
    /* check if the airport has been passed */
    private static boolean isSeen(double lat, double lon, Set<String> airportsPassed,
            Map<String, List<Double>> airportCoords) {
        for (String airport : airportsPassed) {
            if (Math.abs(Haversine(lat, lon, airportCoords.get(airport).get(0), airportCoords.get(airport).get(1))) <= 8) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        if (!input.hasNext()) {
            return;
        }
        // read airport coords
        Map<String, List<Double>> airportCoords = new HashMap<>();
        String line;
        int numAirports = Integer.parseInt(input.nextLine());
        for (; numAirports > 0; numAirports--) {
            line = input.nextLine();
            String[] airportData = line.split(" ");
            List<Double> coords = new LinkedList<>();
            coords.add(Double.parseDouble(airportData[1]));
            coords.add(Double.parseDouble(airportData[2]));
            airportCoords.put(airportData[0], coords);
        }

        /* process flights and put them into a list */
        int numFlights = Integer.parseInt(input.nextLine());
        List<List<String>> airports = new LinkedList<>();
        for (; numFlights > 0; numFlights--) {
            int numWaypoints = Integer.parseInt(input.nextLine());
            List<String> currAirports = new LinkedList<>();
            List<Double> prevCoords = new LinkedList<>();
            Set<String> airportsPassed = new HashSet<>();
            double prevLat, prevLon, lat, lon;
            /* given the waypoints, calculate the flight speed and if it's less than 50 then add it airportsPassed*/
            for (; numWaypoints > 0; numWaypoints--) {
                line = input.nextLine();
                String[] coords = line.split(" ");
                lat = Double.parseDouble(coords[0]);
                lon = Double.parseDouble(coords[1]);
                prevCoords.add(lat);
                prevCoords.add(lon);

                if (prevCoords.size() == 2) {
                    continue;
                }

                prevLat = prevCoords.remove(0);
                prevLon = prevCoords.remove(0);
                if (flightSpeed(prevLat, prevLon, lat, lon )<=50 && !isSeen(lat, lon, airportsPassed, airportCoords)) {
                    String airport = closestAirport(lat, lon, airportCoords);
                    currAirports.add(airport);
                    airportsPassed.add(airport);
                }
            }
            airports.add(currAirports);
        }
        /* deals with scrambled flight order*/
        List<String> first, second;
        while (airports.size() > 1) {
            first = airports.remove(0);
            second = airports.remove(0);

            if (!second.contains(first.get(0))) {
                first.addAll(second);
                airports.add(0, first);
            } else {
                second.addAll(first);
                airports.add(0, second);
            }
        }

        Set<String> seen = new HashSet<>();
        for (String airport : airports.get(0)) {
            if (!seen.contains(airport)) {
                System.out.println(airport);
                seen.add(airport);
            }
        }
    }
}
