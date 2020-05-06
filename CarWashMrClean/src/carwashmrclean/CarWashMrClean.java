package carwashmrclean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Hawa
 */
public class CarWashMrClean {

    public static double clock;
    public static ArrayList<Event> FEL = new ArrayList();
    public static ArrayList<Car> carInWasherQueue = new ArrayList<>();
    public static ArrayList<Car> carInDryerQueue = new ArrayList<>();
    public static Car car;
    public static int numCar;
    public static int arrival = 1;
    public static int endWasher = 2;
    public static int endDryer = 3;
    public static int numQueueWash;
    public static int numInWash;
    public static int numQueueDry;
    public static int numInDry;
    public static double washerQueueWaiting;
    public static double dryerQueueWaiting;

    public static double totTime;
    public static double washTime;
    public static double dryTime;
    public static double interArrivalTime;
    public static double utilWasher;
    public static double utilDryer;

    public static void initialization(ArrayList FEL, Car car) {
        clock = 0.0;
        washerQueueWaiting = 0;
        dryerQueueWaiting = 0;
        Event newEvent = new Event(car, arrival, calculateInterTime());
        //First car arrives to the system
        FEL.add(newEvent); // add it to the future event list

    }

    public static double calculateInterTime() { // Calculate interarrival time(discerete)
        Random rnd = new Random();
        interArrivalTime = rnd.nextDouble() * 10 + 5;
        return interArrivalTime;

    }
     public static int calculateWashingTime() {
        Random rnd = new Random();
        int washingRDA = rnd.nextInt(100) + 1;
        int washingTime;
        //Asignment of random variables between 1-100(Range is calculated by cumulative probability)
        if (washingRDA >= 1 && washingRDA <= 30) {

            washingTime = 8;
        } else if (washingRDA >= 31 && washingRDA <= 70) {

            washingTime = 10;
        } else {
            washingTime = 12;

        }
        return washingTime;

    }

    public static int calculateDryingTime() {
        Random rnd = new Random();
        int dryingRDA = rnd.nextInt(100) + 1;
        int dryingTime;
        //Asignment of random variables between 1-100(Range is calculated by cumulative probability)
        if (dryingRDA >= 1 && dryingRDA <= 20) {
            dryingTime = 5;
        } else if (dryingRDA >= 21 && dryingRDA <= 50) {
            dryingTime = 8;
        } else {
            dryingTime = 10;
        }

        return dryingTime;
    }

   

    public static void washerArrival(ArrayList FEL, Car car) {

        if (numInWash == 1) { //If the washing unit is busy then carv will enter into washer queue
            numQueueWash++; //changes the state
            carInWasherQueue.add(car);

        } else { // washer unit is idle
            numInWash = 1;
            scheduleEndWash(FEL, car); //schedule end wash event for that car
        }

        scheduleArrival(FEL, new Car(calculateInterTime() + clock));
        //schedule new arrival for the next car

    }

    public static void washerDeparture(ArrayList FEL, Car car) {// washer departure process

        if (numInDry == 1) { //If there is someone in drying unit
            numQueueDry++;
            carInDryerQueue.add(car);//add car into dryer queue
            car.setEnterDryerQueue(clock); //for calculating dryerQueueWaiting
        } else {
            numInDry = 1;
            scheduleEndDry(FEL, car); // schedule end dry event
        }
        if (numQueueWash > 0) { //If there is someone in the queue then take it
            numQueueWash--;
            scheduleEndWash(FEL, carInWasherQueue.remove(0)); // get first in the line
            washerQueueWaiting += clock - car.getEnterTime(); //enter time for washer queue
        } else {
            numInWash = 0; //update state
        }

    }

    public static void dryDeparture(ArrayList FEL, Car car) {
        totTime += clock - car.getEnterTime(); //when leaving the system
        //we would like to calculate average time car spends in system.

        if (numQueueDry > 0) { //car is waiting
            numQueueDry--; //update state
            Car newCar = carInDryerQueue.remove(0); // get the first car in the lime
            scheduleEndDry(FEL, newCar); //schedule new end dry event for that specific car
            dryerQueueWaiting += clock - newCar.getEnterDryerQueue(); //calculate dryerQueueWaiting

        } else {
            numInDry = 0; // update state
        }
    }
     private static void scheduleArrival(ArrayList FEL, Car car) {

        Event nextArrival = new Event(car, arrival, car.getEnterTime());
        //get entertime for car and create a new arrival event
        FEL.add(nextArrival); //add it to future event list

    }

    private static void scheduleEndWash(ArrayList FEL, Car car) {
        washTime = calculateWashingTime(); //calculate wash time
        utilWasher += washTime; //time washer is active
        Event endWash = new Event(car, endWasher, washTime + clock);//create a new event
        FEL.add(endWash);// add that event into future event list

    }

   

    private static void scheduleEndDry(ArrayList FEL, Car car) {
        dryTime = calculateDryingTime(); //calculate drytime
        utilDryer += dryTime;//sum of time dryer is active
        Event dryerDeparture = new Event(car, endDryer, dryTime + clock);
        //create a new event end dryer 
        FEL.add(dryerDeparture);

    }

    static class Sort implements Comparator<Event> {
        //sorting for future event list according to time

        @Override
        public int compare(Event o1, Event o2) {
            return (Double.compare(o1.getTime(), o2.getTime()));
        }

    }

    /*      
         the average time a car spends at his company, 
        the utilization of the washer, 
        the utilization of the dryer and 
        the average waiting time for the washer and the dryer. 
     */
    public static void statistics() { //print statistics
        System.out.println("Average time a car spends in system: " + totTime / numCar);
        System.out.println("Util Washer: " + (utilWasher / clock)*100);
        System.out.println("Util Dryer: " + (utilDryer / clock)*100);
        System.out.println("The average waiting time for the washer" + (washerQueueWaiting / numCar));
        System.out.println("The average waiting time for the dryer" + (dryerQueueWaiting / numCar));
    } 

    public static void printList() { //for printing snapshots
        String str = " ";
        for (int i = 0; i < carInWasherQueue.size(); i++) {
            str += carInWasherQueue.get(i).getCarNumber() + " ";
        }
        String str2 = " ";
        for (int i = 0; i < carInDryerQueue.size(); i++) {
            str2 += carInDryerQueue.get(i).getCarNumber() + " ";
        }

        System.out.println("Clock: " + clock + " States: " + numQueueWash + " "
                + numInWash + " " + numQueueDry + " " + numInDry+ "Washer car set: " + str + "Dryer car set: " + str2 + "Fel: " + FEL);
    }
public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter number of cars: ");
        numCar = scan.nextInt(); //get user input
        Car premier = new Car(calculateInterTime());
        //create the first car
        initialization(FEL, premier);
        for (int i = 0; i < numCar;) {
            Collections.sort(FEL, new Sort());
            Event newEvent = (Event) FEL.get(0); //imminent event
            FEL.remove(0);//delete first event in the list
            clock = newEvent.getTime(); //time iteration
            car = newEvent.getCar();
            printList();
            
            int type = newEvent.getType(); //get type :can be 1,2,3
            //arrival, end washer , end dryer
            switch (type) {
                case 1:
                    washerArrival(FEL, car);

                    break;
                case 2:
                    washerDeparture(FEL, car);

                    break;
                case 3:
                    dryDeparture(FEL, car);
                    i++; //number of cars will be increased after car leaves the system(end dry)
                    break;

            }

        }

        statistics();
    }
}
