package org.usfirst.frc.team3132.offseason2016.behaviors;

import org.usfirst.frc.team3132.offseason2016.GlobalSingleton;
import org.usfirst.frc.team3132.offseason2016.subsystems.Drivebase;

public abstract class Action implements Runnable{
	GlobalSingleton global = GlobalSingleton.getInstance();
	
	Drivebase drivebase = Drivebase.getInstance();
	
}
