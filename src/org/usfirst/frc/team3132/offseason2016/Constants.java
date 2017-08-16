package org.usfirst.frc.team3132.offseason2016;


public class Constants {

	
	// test chooser
	public final static String testDrivebaseRaw = "drivebase raw";
	public final static String testDrivebaseSpeed = "drivebase speed";
	public final static String testFlywheel = "flywheel";
	public final static String testHood = "hood";
	public final static String testKicker = "kicker";
	public final static String testTurret = "turret";
	public final static String testIntake = "intake";
	public final static String testShooter = "shooter";
	
	// auto chooser
	public final static String defaultAuto = "Default";
	public final static String customAuto = "My Auto";
	public final static String joeysAuto = "Joeys Auto";
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// Robot Map
	//////////////////////////////////////////////////////////////////////////////////
	
	// drivebase
    public static final int DRIVE_LEFT_MASTER_CAN_ID = 4;	// master talon on left side (CAN ID)
	public static final int DRIVE_LEFT_SLAVE_1_CAN_ID = 5;	// first left hand slave
	public static final int DRIVE_LEFT_SLAVE_2_CAN_ID = 6;	// second left hand slave
	public static final int DRIVE_RIGHT_MASTER_CAN_ID = 7;	// master talon on right side (CAN ID)
	public static final int DRIVE_RIGHT_SLAVE_1_CAN_ID = 8;	// first right hand slave
	public static final int DRIVE_RIGHT_SLAVE_2_CAN_ID = 9;	// second right hand slave
	
	public static final int DRIVE_ENCODER_TICKS_PER_ROTATION = 360;
	public static final double DRIVE_TICKS_PER_MM = 360.0 / 350.0; 
	
	
	// Power distribution board (PDB)
	public static final int PDP_CAN_ID = 0;
		
	
	
	
}
