package com.amdocs.travel.Travel.service;

import com.amdocs.travel.Travel.model.City;
import com.amdocs.travel.Travel.model.Travels;
import com.amdocs.travel.Travel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TravelService {
    @Autowired
    private TravelRepository travelRepository;

    public List<List<Travels>> getAllPathsBetweenCities(City sourceCity, City destinationCity) {
        List<List<Travels>> allPaths = new ArrayList<>();
        Queue<List<Travels>> queue = new LinkedList<>();
        List<Travels> initialPath = new ArrayList<>();

        initialPath.add(new Travels());
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<Travels> currentPath = queue.poll();
            //Log.d(currentPath.size());
            Travels lastTravel = currentPath.get(currentPath.size() - 1);
            City currentCity = (lastTravel == null) ? sourceCity : lastTravel.getDestinationCity();

            if (currentCity.equals(destinationCity)) {
                currentPath.remove(0);
                allPaths.add(currentPath);
            } else {
                List<Travels> possibleTravels = travelRepository.findBySourceCity(currentCity);
                for (Travels travel : possibleTravels) {
                    if (!currentPath.contains(travel)) {
                        List<Travels> newPath = new ArrayList<>(currentPath);
                        newPath.add(travel);
                        queue.add(newPath);
                    }
                }
            }
        }
        return allPaths;
    }
    public List<Travels> getCheapestPathBetweenCities(City sourceCity, City destinationCity) {
        Map<City, Double> minCostMap = new HashMap<>();
        Map<City, Travels> previousTravelMap = new HashMap<>();
        Map<City, Integer> routeChangeMap = new HashMap<>();

        PriorityQueue<CityCostPair> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(pair -> pair.cost));

        minCostMap.put(sourceCity, 0.0);
        routeChangeMap.put(sourceCity, 0);
        priorityQueue.add(new CityCostPair(sourceCity, 0.0, 0));

        while (!priorityQueue.isEmpty()) {
            CityCostPair currentPair = priorityQueue.poll();
            City currentCity = currentPair.city;
            double currentCost = currentPair.cost;
            int currentRouteChanges = currentPair.routeChanges;

            if (currentCity.equals(destinationCity) && currentRouteChanges <= 5) {
                return reconstructPath(previousTravelMap, destinationCity);
            }

            List<Travels> possibleTravels = travelRepository.findBySourceCity(currentCity);

            for (Travels travel : possibleTravels) {
                City neighborCity = travel.getDestinationCity();
                double travelCost = travel.getTravelPrice();
                int newRouteChanges = currentRouteChanges + 1;
                double newCost = currentCost + travelCost;

                if (newRouteChanges <= 5 && (!minCostMap.containsKey(neighborCity) || newCost < minCostMap.get(neighborCity))) {
                    minCostMap.put(neighborCity, newCost);
                    routeChangeMap.put(neighborCity, newRouteChanges);
                    previousTravelMap.put(neighborCity, travel);
                    priorityQueue.add(new CityCostPair(neighborCity, newCost, newRouteChanges));
                }
            }
        }
        return null;
    }

    public List<Travels> getFastestPathBetweenCities(City sourceCity, City destinationCity) {
        Map<City, Integer> minTimeMap = new HashMap<>();
        Map<City, Travels> previousTravelMap = new HashMap<>();

        PriorityQueue<CityTimePair> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.time));

        minTimeMap.put(sourceCity, 0);
        priorityQueue.add(new CityTimePair(sourceCity, 0));

        while (!priorityQueue.isEmpty()) {
            CityTimePair currentPair = priorityQueue.poll();
            City currentCity = currentPair.city;
            int currentTime = currentPair.time;

            if (currentCity.equals(destinationCity)) {
                return reconstructPath(previousTravelMap, destinationCity);
            }

            List<Travels> possibleTravels = travelRepository.findBySourceCity(currentCity);

            for (Travels travel : possibleTravels) {
                City neighborCity = travel.getDestinationCity();
                int travelTime = travel.getTimeTaken();
                int newTime = currentTime + travelTime;

                if (!minTimeMap.containsKey(neighborCity) || newTime < minTimeMap.get(neighborCity)) {
                    minTimeMap.put(neighborCity, newTime);
                    previousTravelMap.put(neighborCity, travel);
                    priorityQueue.add(new CityTimePair(neighborCity, newTime));
                }
            }
        }
        return null;
    }
    private List<Travels> reconstructPath(Map<City, Travels> previousTravelMap, City destinationCity) {
        List<Travels> path = new ArrayList<>();
        Travels currentTravel = previousTravelMap.get(destinationCity);

        while (currentTravel != null) {
            path.add(currentTravel);
            destinationCity = currentTravel.getSourceCity();
            currentTravel = previousTravelMap.get(destinationCity);
        }

        Collections.reverse(path);
        return path;
    }

    private class CityCostPair {
        City city;
        double cost;
        int routeChanges;

        CityCostPair(City city, double cost, int routeChanges) {
            this.city = city;
            this.cost = cost;
            this.routeChanges = routeChanges;
        }
    }

    private class CityTimePair {
        City city;
        int time;

        CityTimePair(City city, int time) {
            this.city = city;
            this.time = time;
        }
    }
}
