package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.auton.canburglar.*;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

public class CanburgleFakeRightGoToLeft implements AutonMode {

	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
		
		ab.addCommand(new SetGyroOffset(270));
	
		
		
		ab.addCommand(new CanburglarArmUp(AutonControl.ARM_Lift_Height));   // lift arm
		
		ab.addCommand(new CanburglarWinchIn(AutonControl.Winch_In_Time));
		ab.addCommand(new AutonWait(500));
		ab.addCommand(new CanburglarSetLatch(true));
		ab.addCommand(new CanburglarWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-AutonControl.Canburgle_XChange, 0), 270, 1));
		ab.addCommand(new DriveWait());
	
		ab.addCommand(new CanburglarWinchOut(500));  // down for second set
		ab.addCommand(new CanburglarWait());
		
		return ab.getAutonList();
	}

}