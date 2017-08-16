package org.usfirst.frc.team3132.offseason2016.subsystems;


import org.usfirst.frc.team3132.lib.Loopable;
import org.usfirst.frc.team3132.lib.Subsystem;
import org.usfirst.frc.team3132.lib.Test;
import org.usfirst.frc.team3132.lib.util.MathUtil;
import org.usfirst.frc.team3132.offseason2016.Constants;
import org.usfirst.frc.team3132.offseason2016.GlobalSingleton;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class Drivebase extends Subsystem implements Loopable, Test{
	// global content
	GlobalSingleton globalSingleton = GlobalSingleton.getInstance();
	
	// setup for singleton
	private static Drivebase ourInstance = new Drivebase();
	
	public static Drivebase getInstance() {
		return ourInstance;
	}

	// set up all motors
	CANTalon driveLeftMaster = new CANTalon(Constants.DRIVE_LEFT_MASTER_CAN_ID);
	CANTalon driveLeftSlave1 = new CANTalon(Constants.DRIVE_LEFT_SLAVE_1_CAN_ID);
	CANTalon driveLeftSlave2 = new CANTalon(Constants.DRIVE_LEFT_SLAVE_2_CAN_ID);
	
	CANTalon driveRightMaster = new CANTalon(Constants.DRIVE_RIGHT_MASTER_CAN_ID);
	CANTalon driveRightSlave1 = new CANTalon(Constants.DRIVE_RIGHT_SLAVE_1_CAN_ID);
	CANTalon driveRightSlave2 = new CANTalon(Constants.DRIVE_RIGHT_SLAVE_2_CAN_ID);
	
	double kF = 0.5;
	double kP = 0.6;//0.5;
	double kI = 10.0;
	double kD = 30.0;//10.0;
	
	double speedScalar = 950;
	boolean velocityMode = false;
	
	// control and use variables
	double throttleLeft = 0.0;
	double throttleRight = 0.0;
	double prevThrottleLeft = 0.0;
	double prevThrottleRight = 0.0;
	double tempThrottleLeft = 0.0;
	double tempThrottleRight = 0.0;
	final double maxPercentChangePerLoop = 0.2;
	double speedGain = 1.0;
	
	private Drivebase() {
		super("drivebase");
		
		driveLeftMaster.clearStickyFaults();
		driveLeftSlave1.clearStickyFaults();
		driveLeftSlave2.clearStickyFaults();
		driveRightMaster.clearStickyFaults();
		driveRightSlave1.clearStickyFaults();
		driveRightSlave2.clearStickyFaults(); 
		
		// set up motors
		driveLeftMaster.setInverted(false);
		driveLeftSlave1.setInverted(false);
		driveLeftSlave2.setInverted(false);
		
		driveRightMaster.setInverted(true);
		driveRightSlave1.setInverted(true);
		driveRightSlave2.setInverted(true);
		
		
		
		
		setBrakeMode(true);
		
		this.setVelocityMode();
		//this.setOpenLoop();
		setLeft(0.0);
		setRight(0.0);
	}

	//////////////////////////////////////////////////////////////////////////////////
	// Operation
	//////////////////////////////////////////////////////////////////////////////////
	

	@Override
	public void update() {
		if(!enabled){
			setLeft(0.0);
			setRight(0.0);
			return;
		}
		
		if(globalSingleton.drivebaseAllowedToDrive){
			System.out.println("LT: " + throttleLeft*speedScalar + " LS: " + getLeftSpeed());
			setLeft(throttleLeft);
			setRight(throttleRight);
		} else {
			setLeft(0.0);
			setRight(0.0);
		}

	}

	//////////////////////////////////////////////////////////////////////////////////
	// Test Mode
	//////////////////////////////////////////////////////////////////////////////////

	protected int testState = 0;
	protected int testCounter = 0;
	
	public void testInit() {
		testState = 0;
		testCounter = 0;
		setTestMode();
	}
	
	public void testPeriodic() {
		switch(testState){
			case 0:
				driveLeftMaster.set(0.5);
				System.out.println("leftMaster");
				break;
			case 1:
				driveLeftSlave1.set(0.5);
				System.out.println("leftSlave");
				break;
			case 2:
				driveLeftSlave2.set(0.5);
				System.out.println("leftSlave2");
				break;
			case 3:
				driveRightMaster.set(0.5);
				System.out.println("rightMaster");
				break;
			case 4:
				driveRightSlave1.set(0.5);
				System.out.println("rightSlave");
				break;
			case 5:
				driveRightSlave2.set(0.5);
				System.out.println("rightSlave2");
				break;
			default:
				System.out.println("no motors active, reseting");
		}
		
		System.out.println("state: " + testState);
		testCounter++;
		if(testCounter > 200){
			testCounter = 0;
			testState++;
			setAllLeft(0.0);
			setAllRight(0.0);
			if(testState > 7){
				testState = 0;
			}
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// Interfacing
	//////////////////////////////////////////////////////////////////////////////////

	double oldTurn = 0.0;
	public void driveWheel(double move, double turn, boolean quickTurn){
		double left = 0;
        double right = 0;
        double leftOverpower = 0;
        double rightOverpower = 0;
        double overPowerGain = 0.1;
        boolean useOverpower = false;

        // calculate negative inertia to help robot anticipate driver actions
        //double negIntertia = turn - oldTurn;
        oldTurn = turn;
        
        if(move == 0)
        	quickTurn = true;
        
        if(quickTurn){
            left = move + turn;
            right = move - turn;
        } else {
            left = move + Math.abs(move)*turn;
            right = move - Math.abs(move)*turn;
        }

        if(!useOverpower){
            setLeftThrottle(left);
            setRightThrottle(right);
            return;
        }

        if(left > 1){
            leftOverpower = left - 1;
            left = 1;
        }else if(left < -1){
            leftOverpower = left + 1;
            left = -1;
        }

        if(right > 1){
            rightOverpower = right - 1;
            right = 1;
        } else if(right < -1){
            rightOverpower = right + 1;
            right = -1;
        }

        left = left - rightOverpower*overPowerGain;
        right = right - leftOverpower*overPowerGain;

        setLeftThrottle(left);
        setRightThrottle(right);
	}
	
	public void driveTank(double left, double right){
		setLeftThrottle(left);
        setRightThrottle(right);
	}
	
	
	
	
	public void setLeftThrottle(double speed){
		throttleLeft = MathUtil.limitValue(speed);
	}
	
	public void setRightThrottle(double speed){
		throttleRight = MathUtil.limitValue(speed);
	}
	
	public void setThrottle(double left, double right){
		setLeftThrottle(left);
		setRightThrottle(right);
	}
	
	protected synchronized void setLeft(double speed){
		if(velocityMode)
			speed = speed*speedScalar;
		
		driveLeftMaster.set(speed*speedGain);
	}
	
	protected synchronized void setRight(double speed){
		if(velocityMode)
			speed = speed*speedScalar;
		
		driveRightMaster.set(speed*speedGain);
	}
	
	public boolean isInPosition(){
		return true;
	}
	
	public synchronized void setOpenLoop(){
		driveLeftMaster.changeControlMode(TalonControlMode.PercentVbus);
		driveLeftSlave1.changeControlMode(TalonControlMode.Follower);
		driveLeftSlave2.changeControlMode(TalonControlMode.Follower);
		
		driveLeftSlave1.set(Constants.DRIVE_LEFT_MASTER_CAN_ID);
		driveLeftSlave2.set(Constants.DRIVE_LEFT_MASTER_CAN_ID);
		
		driveRightMaster.changeControlMode(TalonControlMode.PercentVbus);
		driveRightSlave1.changeControlMode(TalonControlMode.Follower);
		driveRightSlave2.changeControlMode(TalonControlMode.Follower);
		
		driveRightSlave1.set(Constants.DRIVE_RIGHT_MASTER_CAN_ID);
		driveRightSlave2.set(Constants.DRIVE_RIGHT_MASTER_CAN_ID);
		
		velocityMode = false;
		
	}
	
	public synchronized void setTestMode(){
		driveLeftMaster.changeControlMode(TalonControlMode.PercentVbus);
		driveLeftSlave1.changeControlMode(TalonControlMode.PercentVbus);
		driveLeftSlave2.changeControlMode(TalonControlMode.PercentVbus);
		
		driveRightMaster.changeControlMode(TalonControlMode.PercentVbus);
		driveRightSlave1.changeControlMode(TalonControlMode.PercentVbus);
		driveRightSlave2.changeControlMode(TalonControlMode.PercentVbus);
		
		velocityMode = false;
	}
	
	public synchronized void setVelocityMode(){
		driveLeftMaster.changeControlMode(TalonControlMode.Speed);
		driveLeftSlave1.changeControlMode(TalonControlMode.Follower);
		driveLeftSlave2.changeControlMode(TalonControlMode.Follower);
		
		driveLeftSlave1.set(Constants.DRIVE_LEFT_MASTER_CAN_ID);
		driveLeftSlave2.set(Constants.DRIVE_LEFT_MASTER_CAN_ID);
		
		driveRightMaster.changeControlMode(TalonControlMode.Speed);
		driveRightSlave1.changeControlMode(TalonControlMode.Follower);
		driveRightSlave2.changeControlMode(TalonControlMode.Follower);
		
		driveRightSlave1.set(Constants.DRIVE_RIGHT_MASTER_CAN_ID);
		driveRightSlave2.set(Constants.DRIVE_RIGHT_MASTER_CAN_ID);
		
		driveLeftMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		driveLeftMaster.configEncoderCodesPerRev(360);
		driveLeftMaster.reverseSensor(true);
		driveLeftMaster.setPID(kP, kI, kD);
		driveLeftMaster.setF(kF);
		driveLeftMaster.setCloseLoopRampRate(500);
		
		
		driveRightMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		driveRightMaster.configEncoderCodesPerRev(360);
		driveRightMaster.setPID(kP, kI, kD);
		driveRightMaster.setF(kF);
		driveRightMaster.setCloseLoopRampRate(700);
		
		velocityMode = true;
	}
	
	protected synchronized void setAllLeft(double speed){
		if(!enabled)
			return;
		
		driveLeftMaster.set(speed*speedGain);
		driveLeftSlave1.set(speed*speedGain);
		driveLeftSlave2.set(speed*speedGain);
	}
	
	protected synchronized void setAllRight(double speed){
		if(!enabled)
			return;
		
		driveRightMaster.set(speed*speedGain);
		driveRightSlave1.set(speed*speedGain);
		driveRightSlave2.set(speed*speedGain);
	}
	
	public synchronized double getLeftSpeed(){
		return driveLeftMaster.getSpeed();
	}
	
	public synchronized double getRightSpeed(){
		return driveRightMaster.getSpeed();
	}
	
	public synchronized double getLeftTarget(){
		return driveLeftMaster.getSetpoint();
	}
	
	public synchronized double getRightTarget(){
		return driveRightMaster.getSetpoint();
	}

	public synchronized void setBrakeMode(boolean shouldBrake){
		driveLeftMaster.enableBrakeMode(shouldBrake);
		driveLeftSlave1.enableBrakeMode(shouldBrake);
		driveLeftSlave2.enableBrakeMode(shouldBrake);
		
		driveRightMaster.enableBrakeMode(shouldBrake);
		driveRightSlave1.enableBrakeMode(shouldBrake);
		driveRightSlave2.enableBrakeMode(shouldBrake);
		
	}
	
	public void setSpeedGain(double gain){
		speedGain = gain;
	}
	
}
