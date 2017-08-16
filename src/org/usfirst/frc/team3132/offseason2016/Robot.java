
package org.usfirst.frc.team3132.offseason2016;

import org.usfirst.frc.team3132.lib.LogitechGamepadF310;
import org.usfirst.frc.team3132.lib.Looper;
import org.usfirst.frc.team3132.lib.Test;
import org.usfirst.frc.team3132.offseason2016.subsystems.Drivebase;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	
	LogitechGamepadF310 driver = new LogitechGamepadF310(0);
	
	Drivebase drivebase = Drivebase.getInstance();
	
	Looper looper = new Looper("base",drivebase,1.0/100.0);
	
	boolean tankMode = false;
	
    public void robotInit() {
    
    }
    
	public void autonomousInit() {
    
	}

    public void autonomousPeriodic() {

    }

    public void teleopInit() {
    	//drivebase.setOpenLoop();
    	//drivebase.setVelocityMode();
    	looper.start();
    }
    
    public void teleopPeriodic() {
    	
    	// drive logic
    	switch(driver.getPOVVal()){
			case 0:
				drivebase.setThrottle(0.2, 0.2);
				break;
			case 90:
				drivebase.setThrottle(0.2, -0.2);
				break;
			case 180:
				drivebase.setThrottle(-0.2, -0.2);
				break;
			case 270:
				drivebase.setThrottle(-0.2, 0.2);
				break;
			default:
				if(tankMode){
					drivebase.driveTank(driver.getLeftYScaled(), driver.getRightYScaled());
				} else {
					drivebase.driveWheel(driver.getLeftYScaled(), driver.getRightXScaled(),
		        		driver.getLeftStickClick() || driver.getRightStickClick());
				}
		}
    	
    	if(driver.getYellowButton()){
    		tankMode = true;
    	} else if(driver.getGreenButton()){
    		tankMode = false;
    	}
    	
    	if(driver.getRedButton()){
    		drivebase.setBrakeMode(true);
    	} else if(driver.getBlueButton()){
    		drivebase.setBrakeMode(false);
    	}
    	
    	if(driver.getRightBumper()){
    		drivebase.setSpeedGain(0.75);
    	} else if(driver.getLeftBumper()){
    		drivebase.setSpeedGain(0.5);
    	} else if(driver.getTriggerLeftBtn() || driver.getTriggerRightBtn()){
    		drivebase.setSpeedGain(1.0);
    	}
    }
    
    public void disabledInit() {
    	looper.stop();
    }
    
    
    
    
    Test test;
    
    public void testInit() {
    	test = drivebase;
    	drivebase.setTestMode();
    	test.testInit();
    }
    
    public void testPeriodic() {
    	test.testPeriodic();
    }
    
}
