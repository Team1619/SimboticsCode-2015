package org.simbotics.simbot2015.io;

import org.simbotics.simbot2015.util.SimVictorSP;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotOutput {

	private static RobotOutput instance;
	
	//Drive
    private VictorSP driveFrontLeft1;
    private VictorSP driveFrontLeft2;
    
    private VictorSP driveFrontRight1;
    private VictorSP driveFrontRight2;
    
    private VictorSP driveBack1;
    private VictorSP driveBack2;
    
    //Indexer
    private VictorSP indexerMotor; 
    private Solenoid indexerOpen;
    private Solenoid indexerClose; 
    
    private Solenoid intakeOpen;
    private Solenoid intakeClose;
    
    //Intake
    private VictorSP leftIntake;
    private VictorSP rightIntake; 
   
    
    private Solenoid intakeBoot;
    
    //Can Grabber
    private Solenoid canGrabberOpen; 
    private Solenoid canGrabberClose;
    //Canburglar
    private VictorSP canburglarWinch;
    private Relay canburglarArm; 
    private Relay canburglarNewLatch; 
    private Solenoid canburglarLatch; 
    
    private VictorSP newCanburglarArm;
    
    private SensorInput sensorIn;
    
	private RobotOutput() {
		this.sensorIn = SensorInput.getInstance();
		
		//Drive
		this.driveFrontLeft1 = new VictorSP(0);
		this.driveFrontLeft2 = new VictorSP(1);
		
		this.driveFrontRight1 = new VictorSP(2);
		this.driveFrontRight2 = new VictorSP(3);
		
		this.driveBack1 = new VictorSP(4);
		this.driveBack2 = new VictorSP(5);
		
		//this.newCanburglarArm = new VictorSP(10);
		
		//Indexer
		this.indexerMotor = new VictorSP(6);
		this.indexerOpen = new Solenoid(4);
		this.indexerClose = new Solenoid(5);
		
		
		//Intake
		this.leftIntake = new VictorSP(7);
		this.rightIntake = new VictorSP(8);
		
		this.intakeBoot = new Solenoid(7);
		
		this.intakeOpen = new Solenoid(0);
		this.intakeClose = new Solenoid(1);
		
		//Can Grabber
		this.canGrabberOpen = new Solenoid(6);
		this.canGrabberClose = new Solenoid(2);
		
		//Canburglar
		this.canburglarWinch = new VictorSP(9);
		this.canburglarArm = new Relay(0);
		this.canburglarNewLatch = new Relay(1);
		this.canburglarLatch = new Solenoid(3);
		
		
	}
	
	public static RobotOutput getInstance() {
		if(RobotOutput.instance == null) {
			RobotOutput.instance = new RobotOutput();
		}
		return RobotOutput.instance;
	}
	
	

	
	
	
	
    // -----------------------------------------
    // --------------- DRIVE -------------------
    // -----------------------------------------

	public void setDriveLeft(double val) {
		this.driveFrontLeft1.set(-val);
		this.driveFrontLeft2.set(-val);
	}
	
	public void setDriveRight(double val) {
		this.driveFrontRight1.set(-val);
		this.driveFrontRight2.set(-val);
	}
	
	public void setDriveBack(double val) {
		this.driveBack1.set(-val);
		this.driveBack2.set(-val);
	}
	
	public double getDriveLeft() {
		return this.driveFrontLeft1.get();
	}
	
	public double getDriveRight() {
		return this.driveFrontRight1.get();
	}
	
	public double getDriveBack() {
		return this.driveBack1.get();
	}
	
	// -----------------------------------------
    // --------------- Indexer -----------------
    // -----------------------------------------
	
	public void setIndexerMotor(double val) {
		this.indexerMotor.set(val);
	}
	
	public void setIndexerOpen(boolean isOpen) {
		this.indexerOpen.set(!isOpen);
		this.indexerClose.set(isOpen);
	}
	
	public boolean getBreakOut(){
		if(this.canburglarLatch.get()){
			return true;
		}else{
			return false;
		}
	}
	
	public double getIndexerMotor() {
		return this.indexerMotor.get();
	}
	
	
	// -----------------------------------------
    // --------------- Intake ------------------
    // -----------------------------------------
	
	public void setIntakeLeft(double val) {
		this.leftIntake.set(-val);
	}
	
	public void setIntakeRight(double val) {
		this.rightIntake.set(val);
	}
	
	public void setIntakeBoot(boolean isOut) {
		this.intakeBoot.set(isOut);
	}
	
	public void setIntakeOpen(boolean isOpen) {
		this.intakeOpen.set(isOpen);
		this.intakeClose.set(!isOpen);
	}
	
	
	
	public double getLeftIntake() {
		return this.leftIntake.get();
	}
		
	public double getRightIntake() {
		return this.rightIntake.get();
	}
	
	public boolean getIntakeBoot() {
		return this.intakeBoot.get();
	}
	
	// -----------------------------------------
    // --------------- Can Grabber -------------
    // -----------------------------------------
	
	public void setCanGrabber(boolean isOpen) {
		this.canGrabberOpen.set(isOpen);
		this.canGrabberClose.set(!isOpen);
	}
	
	
	
	// -----------------------------------------
    // --------------- Canburglar -------------
    // -----------------------------------------
	
	public void setCanburglarWinch(double val) {
		this.canburglarWinch.set(val);
	}
	
	public void setCanburglarArmOff() {
		this.canburglarArm.set(Relay.Value.kOff);
	}
	
	public void setCanburglarNewLatchOn(){
		this.canburglarNewLatch.set(Relay.Value.kOn);
	}
	
	public void setCanburglarNewLatchOff(){
		this.canburglarNewLatch.set(Relay.Value.kOff);
	}
	
	public void setNewCanburglarArm(double val){
		this.newCanburglarArm.set(val);
	}
	
	public void setCanburglarArmForward() {
		this.canburglarArm.set(Relay.Value.kForward);
	}
	
	public void setCanburglarArmReverse() {
		this.canburglarArm.set(Relay.Value.kReverse);
	}

	public double getCanburglarWinch() {
		return this.canburglarWinch.get();
	}
	
	public void setCanburglarLatch(boolean isOpen) {
		this.canburglarLatch.set(isOpen);
	}
	
	
	
	//////////////////////////////////////////
	//////////////////////////////////////////
    // GOTCHA: remember to turn everything off
    public void stopAll() {
    	setDriveLeft(0);
    	setDriveRight(0);
    	setDriveBack(0);
    	setCanburglarArmOff();
    	setCanburglarWinch(0);
    	setIntakeLeft(0);
    	setIntakeRight(0);
    	setIndexerMotor(0);
    	// shut off things here
    }
    
    
    
    
	
}
