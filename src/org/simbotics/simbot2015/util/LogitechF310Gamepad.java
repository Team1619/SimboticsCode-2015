/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simbotics.simbot2015.util;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Mike
 */
public class LogitechF310Gamepad {
    private Joystick joystick;
    
    public LogitechF310Gamepad(int portNum) {
        this.joystick = new Joystick(portNum);
    }
    
    public double getLeftX() {
        return this.joystick.getRawAxis(0);
    }
    
    public double getLeftY() {
        return -this.joystick.getRawAxis(1);
    }
    
    public double getTriggerLeft() {
    	return this.joystick.getRawAxis(2);
    	//return this.joystick.getRawAxis(2); and 3
    }
    
    public double getTriggerRight(){
    	return this.joystick.getRawAxis(3);
    }
    
    public double getRightX() {
        return this.joystick.getRawAxis(4);
    }
    
    public double getRightY() {
        return -this.joystick.getRawAxis(5);
    }
    
    public boolean getButton(int btnNum) {
        return this.joystick.getRawButton(btnNum);
    }
    
    public boolean getGreenButton() {
        return this.joystick.getRawButton(1);
    }
    
    public boolean getBlueButton() {
        return this.joystick.getRawButton(3);
    }
    
    public boolean getRedButton() {
        return this.joystick.getRawButton(2);
    }
    
    public boolean getYellowButton() {
        return this.joystick.getRawButton(4);
    }
    
    public boolean getBackButton() {
        return this.joystick.getRawButton(7);
    }
    
    public boolean getStartButton() {
        return this.joystick.getRawButton(8);
    }
    
    public boolean getLeftBumper() {
        return this.joystick.getRawButton(5);
    }
    
    public boolean getRightBumper() {
        return this.joystick.getRawButton(6);
    }
    
    public boolean getLeftStickClick() {
        return this.joystick.getRawButton(9);
    }
    
    public boolean getRightStickClick() {
        return this.joystick.getRawButton(10);
    }
    
    public int getPOVVal(){
    	return this.joystick.getPOV(0);
    }
    
    public boolean getPOVDown(){
    	return (this.joystick.getPOV(0) == 180);
    }
    
    public boolean getPOVRight(){
    	return (this.joystick.getPOV(0) == 90);
    }
    
    public boolean getPOVUp(){
    	return (this.joystick.getPOV(0) == 0);
    }
    
    public boolean getPOVLeft(){
    	return (this.joystick.getPOV(0) == 270);
    }
	
	
    
}
