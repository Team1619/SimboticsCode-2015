package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonOverride;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.auton.canburglar.CanburglarSetLatch;
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

public class LandFillAutoIRI implements AutonMode {
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

		ab.addCommand(new CanburglarSetLatch(true));
		ab.addCommand(new IndexerSetLatches(false));
		ab.addCommand(new IntakeSetOpen(false));
		ab.addCommand(new BootSetOut(false));
		ab.addCommand(new CanGrabberSetOpen(false));
		
		//ab.addCommand(new IndexerCanSequence());
		
		
		
		ab.addCommand(new DriveToPoint(new Vect(0,18),115,5,true,1000,true,1.0)); //Pick up first tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-25,48),135,1,true,2500,true,1.0)); //Pick up first tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPointToteTimeOut(new Vect(-25,51),125,0,true,1000)); //Pick up first tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(0.9));
		
		ab.addCommand(new DriveToPoint(new Vect(-18,21),135,2,true,1500)); //Pick up 2nd tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-40,21),135,2,true,1500)); //Pick up 2nd tote
		ab.addCommand(new DriveWait());
		
		//ab.addCommand(new IndexerWait());
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPointToteTimeOut(new Vect(-58,37),160,1,true,2000,true,1.0)); //Pick up 2nd tote
		ab.addCommand(new DriveWait());
		
		

		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		ab.addCommand(new DriveToPoint(new Vect(-50,32),135,5,true,1500)); //Pick up 3rd tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-45,53),135,1,true,1000,true,1.0)); //Pick up 3rd tote
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new IndexerWait());
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-50,53),145,1,true,1500,true,1.0)); //Pick up 3rd tote
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPointToteTimeOut(new Vect(-59,57),145,0,true,1000)); //Pick up 3rd tote
		ab.addCommand(new DriveWait());
		
		
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(50));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		
		ab.addCommand(new DriveToPoint(new Vect(-60,33),135,3,true,1500,true,1.0)); //Pick up 4th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-93,30),125,1,true,1000,true,1.0)); //Pick up 4th tote
		ab.addCommand(new DriveWait());
	
		
		
		ab.addCommand(new IndexerWait());
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-93,44),125,1,true,1500,true,1.0)); //Pick up 4th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPointToteTimeOut(new Vect(-93,44),115,0,true,1000)); //Pick up 4th tote
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence(1.0));
		
		
		ab.addCommand(new DriveToPoint(new Vect(-89,27),135,5,true,1500,true,1.0)); //Pick up 5th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-112,27),135,5,true,1500,true,1.0)); //Pick up 5th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWait());
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-114,41),135,1,true,1500,true,1.0)); //Pick up 5th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPointToteTimeOut(new Vect(-115,48),115,0,true,1000)); //Pick up 5th tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(750));
		
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerLastToteSequence());
		ab.addCommand(new IndexerWait());
		
		
		
		
		
		
		
		
		
		
		
		
		return ab.getAutonList();
	}

}