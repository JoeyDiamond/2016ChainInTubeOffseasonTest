package org.usfirst.frc.team3132.offseason2016.autonomous;


import org.usfirst.frc.team3132.lib.ChooserHelper;
import org.usfirst.frc.team3132.offseason2016.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooserHelper implements ChooserHelper{
	SendableChooser<String> autoChooser = new SendableChooser<String>();;

	
	@Override
	public void create() {
		autoChooser.addDefault("Default Auto", Constants.defaultAuto);
        autoChooser.addObject("My Auto", Constants.customAuto);
        autoChooser.addObject("Joeys Auto", Constants.joeysAuto);
        SmartDashboard.putData("Auto choices", autoChooser);
		
	}

	@Override
	public String getSelection() {
		return (String) autoChooser.getSelected();
	}
	
}
