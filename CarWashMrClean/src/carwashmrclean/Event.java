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
public class Event {
     Car car;
     int type; // can be 1 2 or 3
     double time; 
     
     
       public Event(Car car,int type, double time){
        this.car = car;
        this.type = type;
        this.time = time;
        
    }
     
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Event{" + "car=" + car.getCarNumber() + ", type=" + type + ", time=" + time + ", enter time = "+ car.getEnterTime()+'}';
    }

 
   
   
    
  
    
    
    
}
