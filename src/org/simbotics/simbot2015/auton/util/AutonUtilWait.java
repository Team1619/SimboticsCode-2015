/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.simbotics.simbot2015.auton.util;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;

/**
 *
 * @author Michael
 */
public class AutonUtilWait extends AutonCommand {

    public AutonUtilWait() {
        super(RobotComponent.UTIL);
    }

    public boolean calculate() {
        return true;
    }

	@Override
	public void override() {
		// nothing to do
		
	}
    
    
    
}
