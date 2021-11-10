package itemPickup;

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


public class ItemPickup {
	 
	static UnregulatedMotor leftMotor = new UnregulatedMotor(MotorPort.B);
    static UnregulatedMotor rightMotor = new UnregulatedMotor(MotorPort.C);
    static UnregulatedMotor clawMotor = new UnregulatedMotor(MotorPort.A);
	private static EV3ColorSensor sensor1 = new EV3ColorSensor(SensorPort.S4);
	public static boolean clawIsOpen = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SampleProvider sp = sensor1.getRedMode();
		float colorValue = 0;
		
		//set up
		openClaw();
		  leftMotor.setPower(40);
		  rightMotor.setPower(40);
		  
		  
		
        while(Button.ESCAPE.isUp()) {

        	float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            colorValue = sample[0];
            
            System.out.println("Searching for object");
			System.out.println("Red value: " + colorValue);
			
			if (colorValue > 0.03)
			{
				//push forward a small amount
				leftMotor.setPower(20);
	            rightMotor.setPower(20);
	            Delay.msDelay(500);
	            
	            //close claw
	            closeClaw();
	            
	            //stop robot
	            leftMotor.setPower(0);
	            rightMotor.setPower(0);
	            return;
			}
			else {
				//no change
				
			}
			
// -------------------this is the line follower -----------------------------
//			
//			if (colorValue < 0.15) { //if too far left of black tape
//				leftMotor.setPower(20);
//            rightMotor.setPower(0);
//        } else if (colorValue >= 0.22) { //if too far right of black tape
//        	leftMotor.setPower(0);    
//        	rightMotor.setPower(20);
//        } 
//        else { //if inbetween just go straight
//        	leftMotor.setPower(50);    
//        	rightMotor.setPower(50);
//        }
//			//most likely on white, 
//			//if(colorValue > 0.30)
// --------------------------End line follower --------------------------------		
			
			
			Delay.msDelay(500);
        }
        // stop motors with brakes on.
        leftMotor.stop();
        rightMotor.stop();

        // free up resources.
        leftMotor.close();
        rightMotor.close();
        sensor1.close();
	}
	
	
	static void openClaw() {
		clawMotor.setPower(-50);
		Delay.msDelay(1500);
		clawIsOpen = true;
	}
	
	static void closeClaw() {
		clawMotor.setPower(50);
		Delay.msDelay(1500);
		clawIsOpen = false;
	}
	
	
}


