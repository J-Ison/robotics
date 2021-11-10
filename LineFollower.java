package org.lejos.ev3.sample.linefollower;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;


public class LineFollower {
	 
	static UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.B);
    static UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.C); 
	private static EV3ColorSensor sensor1 = new EV3ColorSensor(SensorPort.S4);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SampleProvider sp = sensor1.getRedMode();
		float colorValue = 0;
		
		  motorA.setPower(40);
	      motorB.setPower(40);
		
		
        while(Button.ESCAPE.isUp()) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            colorValue = sample[0];

			System.out.println("Red value: " + colorValue);
			
			
			//is on black tape -> continue being on black tape
//			if(colorValue < 0.10)
//			{
//				motorA.setPower(40);
//                motorB.setPower(40);
//                Delay.msDelay(500);
//                
//                //reevaluate colorValue
//                sample = new float[sp.sampleSize()];
//                sp.fetchSample(sample, 0);
//                colorValue = sample[0];
//                
//			}
//			
//			else if(colorValue >= 0.10)
//			{
//				//slight right
//				motorA.setPower(40);
//				motorB.setPower(25);
//				Delay.msDelay(1000);
//				
//				
//				//reevaluate colorValue
//                sample = new float[sp.sampleSize()];
//                sp.fetchSample(sample, 0);
//                colorValue = sample[0];
//                
//                //slight left
//				if(colorValue >= .10)
//				{
//					motorA.setPower(0);
//					motorB.setPower(40);
//					Delay.msDelay(500);
//				}      
//                else if(colorValue < .12)
//                {
//                	motorA.setPower(40);
//					motorB.setPower(40);
//					Delay.msDelay(1000);
//                }
//				//reevaluate colorValue
//                sample = new float[sp.sampleSize()];
//                sp.fetchSample(sample, 0);
//                colorValue = sample[0];
//				
//			}
			if (colorValue < 0.15) { //if too far left of black tape
            motorA.setPower(20);
            motorB.setPower(0);
        } else if (colorValue >= 0.22) { //if too far right of black tape
              motorA.setPower(0);    
              motorB.setPower(20);
        } 
        else { //if inbetween just go straight
            motorA.setPower(50);    
            motorB.setPower(50);
        }
			//most likely on white, 
			//if(colorValue > 0.30)
			
			
			
			Delay.msDelay(500);
        }
        // stop motors with brakes on.
        motorA.stop();
        motorB.stop();

        // free up resources.
        motorA.close();
        motorB.close();
        sensor1.close();
	}
}
