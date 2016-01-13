/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;

/**
 *
 * @author Michael
 */
public class DefaultMode implements AutonMode {

    public AutonCommand[] getMode() {
        AutonBuilder ab = new AutonBuilder();
        //do nothing
        return ab.getAutonList();
    }
     
}
