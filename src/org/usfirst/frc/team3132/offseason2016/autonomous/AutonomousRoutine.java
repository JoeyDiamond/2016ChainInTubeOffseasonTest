package org.usfirst.frc.team3132.offseason2016.autonomous;


import org.usfirst.frc.team3132.offseason2016.GlobalSingleton;
import org.usfirst.frc.team3132.offseason2016.subsystems.Drivebase;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class AutonomousRoutine implements Runnable{
	
	GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
	Drivebase drivebase = Drivebase.getInstance();
	
	DriverStation ds = DriverStation.getInstance();
	
	/**
	 * put autonomous routine initialization code here
	 */
	public abstract void init();
	
	/**
	 * put autonomous routine code here
	 */
	public abstract void run();
	
	/**
	 * put code to abort or kill routine if necessary
	 */
	public void end(){
		drivebase.setThrottle(0.0, 0.0);
		
	}
}
