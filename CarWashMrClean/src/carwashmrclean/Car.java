/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashmrclean;

/**
 *
 * @author Hawa
 */
public class Car {
     int carNumber;
     static int count =0;
     double enterTime;
     double enterDryerQueue;
      Car(double enterTime){
       count++;
       carNumber = count; // increase number of cars whenever new car arrives
        this.enterTime = enterTime;
    }

    public double getEnterDryerQueue() {
        return enterDryerQueue;
    }

    public void setEnterDryerQueue(double enterDryerQueue) {
        this.enterDryerQueue = enterDryerQueue;
    }
   
    public int getCarNumber() {
        return carNumber;
    }

    public double getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(double enterTime) {
        this.enterTime = enterTime;
    }
     @Override
    public String toString() {
        return "Car{" + carNumber + " " + enterTime + "     " + "}\n";
    }
    
}
