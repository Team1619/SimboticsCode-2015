/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simbotics.simbot2015.auton;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.simbotics.simbot2015.auton.mode.*;
import org.simbotics.simbot2015.io.DriverInput;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.util.Debugger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Programmers
 */
public class AutonControl {

    private static AutonControl instance;
    
    public static final int ARM_Lift_Height = 1000; // how high we lift after we steal the first 2 (lower if we go over the height) 
    public static final int ARM_Lower_Height = 250; // this value should change along with lift height 
    public static final int Winch_In_Time =1000; // how much we winch in 
    public static final double Canburgle_XChange = 200; // amount we shift over to the left/right in the canburgle modes
    
    public static final int Winch_In_Time_Start = 500;  // not used
    
	
    
    private int autonMode;
    private AutonMode activeMode;
    private int autonDelay;
    private long autonStartTime;
    
    private boolean running;
    private ArrayList<AutonMode> autonModes;
    
    private int currIndex;
    private AutonCommand[] commands;
        
    private String autoSelectError = "NO ERROR";
    
    public static AutonControl getInstance() {
        if(instance == null) {
            instance = new AutonControl();
        }
        return instance;
    }

    private AutonControl() {
        this.autonMode = 0;
        this.autonDelay = 0;
        this.currIndex = 0;
        
        this.autonModes = new ArrayList<>();
        
        // GOTCHA: remember to put all auton modes here
        
        this.autonModes.add(new DefaultMode()); //0
        this.autonModes.add(new DriveForward()); //1
        this.autonModes.add(new ThreeToteStackRuckus());
        this.autonModes.add(new LandFillAutoIRI());
        this.autonModes.add(new CanburgleAndDrive());
        this.autonModes.add(new CanburglarFastMode()); 
       
     
      
       
    }

    public void initialize() {
        Debugger.println("START AUTO");
        
        this.currIndex = 0;
        this.running = true;

        
        // initialize auton in runCycle
        this.activeMode = (AutonMode)this.autonModes.get(this.autonMode);
        this.commands = this.activeMode.getMode();
       

        this.autonStartTime = System.currentTimeMillis();
        
        // clear out each components "run seat"
        AutonCommand.reset();
    }
    
    public void runCycle() {
        // haven't initialized list yet
        long timeElapsed = System.currentTimeMillis() - this.autonStartTime;
        if(timeElapsed > this.getAutonDelayLength() && this.running) {
            Debugger.println("Current index " + this.currIndex, "QTIP");
            
            
                // start waiting commands
                while(this.currIndex < this.commands.length &&
                        this.commands[this.currIndex].checkAndRun()) {
                    this.currIndex++;
               
            }
            // calculate call for all running commands
            AutonCommand.execute();
        } else {
            RobotOutput.getInstance().stopAll();
        }

    
    }
    
    public void stop() {
        this.running = false;
    }
    
    public long getAutonDelayLength() {
        return (long)(this.autonDelay * 500);
    }

    public void updateModes() {
        DriverInput driverIn = DriverInput.getInstance();
        
        
        try {
        
        if(driverIn.getAutonSetModeButton()) {
            double val = driverIn.getAutonSelectStick();

            
            val = (val + 1) / 2.0;  // make it positive and between 0 - 1.0
            
            // figure out which auton mode is being selected
            this.autonMode = (int)(val * this.autonModes.size());
            // make sure we didn't go off the end of the list
            this.autonMode = Math.min(autonMode, this.autonModes.size() - 1);
            if(this.autonMode < 0 ){
            	this.autonMode =0;
            }
            /*
            if(val < 0) { this.autonMode = 0; }
            else { this.autonMode = 1; }
         */   
        } else if(driverIn.getAutonSetDelayButton()) {
            this.autonDelay = (int)((driverIn.getAutonSelectStick() + 1) * 5.0);
            if(this.autonDelay < 0 ) {
            	this.autonDelay =0;
            }
        }
        
        } catch(Exception e) {
        	this.autonMode = 0;
        	
        	StringWriter sw = new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	
        	
        	this.autoSelectError = sw.toString();
        
        }
        
        // name of the current auton mode
        String name = this.autonModes.get(this.autonMode).getClass().getName();

        // make sure there is a '.'
        if(name.lastIndexOf('.') >= 0) {
            // get just the last bit of the name
            name = name.substring(name.lastIndexOf('.'));
        }
        
        String delayAmt = "";
        if(this.autonDelay < 10) {
            // pad in a blank space for single digit delay
            delayAmt = " " + this.autonDelay;
        } else {
            delayAmt = "" + this.autonDelay;
        }
        
        String outputString = "" + (int)autonDelay + "-" + autonMode + name+"";
        
        SmartDashboard.putString("Auton Selector: ",outputString);
        SmartDashboard.putString("Auton Error: ", this.autoSelectError);
        


    }

}
