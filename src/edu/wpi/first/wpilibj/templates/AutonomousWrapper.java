package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Andrew
 */
public class AutonomousWrapper {
    Class autonomous = null;
    String description = "";
    boolean caresAboutHotGoal = false;
    public static boolean hasHotGoal = false;
    public static boolean leftIsHot = false;
    public static boolean startingOnLeft = true;
    public AutonomousWrapper(Class autonomous, String description, boolean caresAboutHotGoal)
    {
        this.autonomous = autonomous;
        
        if ( description == null )
            this.description = autonomous.getName();
        else 
            this.description = description;
        
        this.caresAboutHotGoal = caresAboutHotGoal;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public Command start()
    {   
        if ( caresAboutHotGoal )
        {
           Timer t = new Timer();
           t.start();
           while ( t.get() < 3.0 )
           {
               leftIsHot = CommandBase.table.getBoolean("leftHot", false);
               if ( CommandBase.table.getBoolean("leftHot", false) || CommandBase.table.getBoolean("rightHot", false) )
                   break;
           }
        }
        Command command = null;
        try
        {
            command = (Command) autonomous.newInstance(); 
        } catch (Exception e) {
            System.out.println("Failed to start command, falling back to OneBall");
            e.printStackTrace();
        }
        command.start();
        return command;
    }
}
