/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simbotics.simbot2015.auton.mode;

import org.simbotics.simbot2015.auton.AutonCommand;

/**
 *
 * @author Programmers
 */
public interface AutonMode {
    
    public AutonCommand[] getMode();
    
}
