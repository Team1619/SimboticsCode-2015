package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonOverride;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerCanSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerLastToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerOverride;
import org.simbotics.simbot2015.auton.indexer.IndexerSetLatches;
import org.simbotics.simbot2015.auton.indexer.IndexerToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerWait;
import org.simbotics.simbot2015.auton.indexer.IndexerWaitForHasTote;
import org.simbotics.simbot2015.auton.intake.BootSetOut;
import org.simbotics.simbot2015.auton.intake.CanGrabberSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeed;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeedIndependant;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SixToteStackWithCan implements AutonMode {
	private int largeEps =0;
	private int smallEps =0;
	
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		this.largeEps =(int)SmartDashboard.getNumber("Error EPS Large: ");
		this.smallEps =(int)SmartDashboard.getNumber("Error EPS Small");
		
		System.out.println("Error Eps Large: "+this.largeEps);
		System.out.println("Error Eps Small: "+this.smallEps);
		
		ab.addCommand(new SetGyroOffset(90));

		ab.addCommand(new IndexerSetLatches(false));
		ab.addCommand(new IntakeSetOpen(false));
		ab.addCommand(new BootSetOut(false));
		ab.addCommand(new CanGrabberSetOpen(false));
		
		//ab.addCommand(new IndexerCanSequence());
		
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveToPoint(new Vect(6,52),90,2,true,2500)); //Pick up first tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(6,52),80,0,true,500)); //Pick up first tote
		ab.addCommand(new DriveWait());
		
		/*ab.addCommand(new DriveTurnAtSpeed(0.7,80,1500));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveTurnAtSpeed(0.7,90,1500));
		ab.addCommand(new DriveWait());
		*/
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(50));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(0.75));
		
		
		ab.addCommand(new DriveToPoint(new Vect(-10,38),90,5,true,2500)); // get into position for second
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveToPoint(new Vect(-15,54),90,1,true,2000)); // drive to pickup second
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-15,54),100,0,true,500)); //Pick up first tote
		ab.addCommand(new DriveWait());
	
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		ab.addCommand(new IndexerWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveToPoint(new Vect(-12,72),90,1,true,1000)); // get into position for 3rd
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-12,72),100,0,true,500)); // drive to pickup 3rd
		ab.addCommand(new DriveWait());
		
		
	
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveToPoint(new Vect(-26,90),90,1,true,1000)); // get into position for 3rd
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-26,90),80,0,true,500)); // drive to pickup 3rd
		ab.addCommand(new DriveWait());
		
		
	
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveToPoint(new Vect(-46,96),90,1,true,1000)); // get into position for 3rd
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-46,96),80,0,true,500)); // drive to pickup 3rd
		ab.addCommand(new DriveWait());
		
		
	
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		
		
		
		
		
		
		
		return ab.getAutonList();
	}

}