package org.simbotics.simbot2015.io;

import org.simbotics.simbot2015.util.LogitechDualAction;
import org.simbotics.simbot2015.util.LogitechF310Gamepad;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverInput {

    private static DriverInput instance;
    //private LogitechDualAction driver;
    private LogitechF310Gamepad driver;
    private LogitechF310Gamepad operator;
    
    private boolean canburgleMode = false;
    
    private DriverInput() {
        this.driver = new LogitechF310Gamepad(0); // new LogitechDualAction(0);
        this.operator = new LogitechF310Gamepad(1);
    }
    
    public static DriverInput getInstance() {
        if(instance == null) {
            instance = new DriverInput();
        }
        return instance;
    }
    
    // -------------------------------------
    // --- DRIVER --------------------------
    // -------------------------------------
    
    public double getDriverX() {
    	return this.driver.getLeftX();
    }
    
    public double getDriverY() {
        return this.driver.getLeftY();
    }
    
    public double getDriverRotation() {
    	return this.driver.getRightX(); // this.driver.getTrigger();
    }
    
    public boolean getOutputButton() {
        return this.driver.getBlueButton();
    }

    public boolean getEncoderButton() {
        return false;//this.driver.getBlueButton();
    }

    public boolean getFieldButton() {
        return false;//this.driver.getYellowButton();
    }

    public boolean getGyroButton() {
        return this.driver.getRedButton();
        
    }
    
    public boolean getJumpButton() {
    	return this.driver.getStartButton();
    }
    
    public boolean getIntakeToteButton(){
    	return this.driver.getRightBumper();
    }
    
    public boolean getIntakeCanButton(){
    	return this.driver.getLeftBumper();
    }
    
    
    public double getDropStackTrigger(){
    	return this.driver.getTriggerLeft();
    }
    
    public boolean getDropUpsideDownStackButton(){
    	return this.driver.getYellowButton();
    }
 
    
    public double getIntakeFromStepTrigger() {
    	return this.driver.getTriggerRight();
    }
    
    public boolean getOuttakeToteButton(){
    	return this.driver.getGreenButton();
    }
    // more driver stuff here
    
    // -------------------------------------
    // --- OPERATOR ------------------------
    // -------------------------------------
    
    public double getIntakeY(){
    	if(!this.canburgleMode) {
    		return this.operator.getLeftY();
    	} else {
    		return 0.0;
    	}
    }
    
    
    public double getIntakeX(){
    	if(!this.canburgleMode) {
    		return this.operator.getLeftX();
    	} else {
    		return 0.0;
    	}
    }
    
    public int getPOVVal(){
    	return this.operator.getPOVVal();
    }
    
    public boolean getNoodleIndexButton(){
    	return false;
    }
    
    public boolean getIntakeCloseButton(){
    	return false;
    }
    
    public boolean getCoopButton(){
    	return false;
    }
    
    public boolean getDingusButton(){
    	return false;
    }
    
    public double getIndexer(){
    	if(!this.canburgleMode) {
    		return (this.operator.getTriggerRight()-this.operator.getTriggerLeft());
    	} else {
    		return 0.0;
    	}
    }
    
    
    
    public boolean getCanGrabberCloseButton(){
    	return this.operator.getRightBumper();
    	
    }
    
    public boolean getCanGrabberOpenButton(){
    	return this.operator.getLeftBumper();
    	
    }
    
    public boolean getCanIndexButton(){
    	if(!this.canburgleMode) {
    		return this.operator.getYellowButton();
    	} else {
    		return false;
    	}
    }
    
    public boolean getBootInButton(){
    	if(!this.canburgleMode) {
    		return this.operator.getRedButton();
    	} else {
    		return false;
    	}
    }
    
    
   
    public double getNewArmStick(){
    	if(!this.canburgleMode){
    		return 0.0; 
    	}else{
    		return this.operator.getLeftY();
    	}
    }
    
    
    
    public boolean getToteButton(){
    	if(!this.canburgleMode) {
    		return this.operator.getBackButton();
    	} else {
    		return false;
    	}
    }
    
    public boolean getFirstTotesButton(){
    	if(!this.canburgleMode) {
    		return this.operator.getGreenButton();
    	} else {
    		return false;
    	}
    }
    
    public boolean getLastToteButton(){
    	if(!this.canburgleMode) {
    		return this.operator.getBlueButton();
    	} else {
    		return false;
    	}
    }

    
    // ------------------- BURGLAR -------------------
    public boolean getCanburglarLatchButton() {
    	if(this.canburgleMode) {
    		return this.operator.getGreenButton();
    	} else {
    		return false;
    	}
    }
    
    public boolean getCanburglarUnLatchButton() {
    	if(this.canburgleMode) {
    		return this.operator.getRedButton();
    	} else {
    		return false;
    	}
    }
    
    public double getCanburglarWinch() {
    	if(this.canburgleMode) {
    		return this.operator.getTriggerRight() - this.operator.getTriggerLeft();
    		//return this.canburglar.getLeftY();
    	} else {
    		return 0.0;
    	}
    }
    
    public boolean getCanburglarArmUpButton() {
    	if(this.canburgleMode) {
    		return this.operator.getLeftY() > 0.5;
    	} else {
    		return false;
    	}
    }
    
    public boolean getCanburglarArmDownButton() {
    	if(this.canburgleMode) {
    		return this.operator.getLeftY() < -0.5;
    	} else {
    		return false;
    	}
    }
    
    public void setCanburglarMode(boolean burgleMode) {
    	this.canburgleMode = burgleMode;
    }
    
    public boolean getCanburglarMode() {
    	return this.canburgleMode;
    }
    
    public boolean getCanburglarModeActivate() {
    	return this.operator.getRightStickClick();
    }
    
    public boolean getCanburglarModeDeactivate() {
    	return this.operator.getLeftStickClick();
    }
    
    
    
    // -------------------------------------
    // --- AUTON SETUP ---------------------
    // -------------------------------------

   
    
    
    public boolean getAutonSetModeButton() {
        return this.driver.getGreenButton();
    }
    
    public boolean getAutonSetDelayButton() {
        return this.driver.getRedButton();
    }
    
    public double getAutonSelectStick() {
        return this.driver.getLeftY();
    }
    
    public boolean getBackButton(){
    	return this.driver.getBackButton();
    }
    
}
