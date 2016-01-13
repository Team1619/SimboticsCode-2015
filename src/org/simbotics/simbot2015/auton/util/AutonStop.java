/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simbotics.simbot2015.auton.util;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonControl;
import org.simbotics.simbot2015.auton.RobotComponent;

/**
 *
 * @author Michael
 */
public class AutonStop extends AutonCommand {

    public AutonStop() {
        super(RobotComponent.UTIL);
    }
    
    public boolean calculate() {
        AutonControl.getInstance().stop();
        return true;
    }

	@Override
	public void override() {
		// nothing to do
		
	}
    
}
