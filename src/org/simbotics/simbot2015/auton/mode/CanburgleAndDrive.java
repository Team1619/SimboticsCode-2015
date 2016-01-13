package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.auton.canburglar.*;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CanburgleAndDrive implements AutonMode {

	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
		
		ab.addCommand(new SetGyroOffset(270));
		
		// first can
		ab.addCommand(new CanburglarSetLatch(false));
	
	
		
		ab.addCommand(new CanburglarArmUp(1500));
		
		
		ab.addCommand(new DriveToPoint(new Vect(0,14),270,1,true,3000,true,1));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new CanburglarWait());
		
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new DriveToPoint(new Vect(0,14),280,0,true,1000,false,1));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(0,14),260,0,true,1000,false,1));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(0,14),270,0,true,1000,false,1));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(0,-170),270,5,true,3000,true,1));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new CanburglarArmDown(2000));
		ab.addCommand(new CanburglarWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(0,-105),270,5,true,3000,false,0.4));
		ab.addCommand(new DriveWait());
	
		
		ab.addCommand(new CanburglarArmDown(2500));
		ab.addCommand(new CanburglarWait());
		//ab.addCommand(new AutonWait(1000));
		//ab.addCommand(new CanburglarSetLatch(true));
		
		
		/*ab.addCommand(new DriveToPoint(new Vect(0,-80),270,10,true,3000,true,1));
		ab.addCommand(new DriveWait());
	
		ab.addCommand(new DriveToPoint(new Vect(0,-150),270,5,true,3000,false,1));
		ab.addCommand(new DriveWait());
		*/
		
		return ab.getAutonList();
	}

}
