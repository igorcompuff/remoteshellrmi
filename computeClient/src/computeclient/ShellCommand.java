/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computeclient;

import compute.common.Task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 *
 * @author igorribeiro
 */
public class ShellCommand implements Task<String>, Serializable
{
    private String commandText;
    
    public ShellCommand(String cmdText)
    {
        commandText = cmdText;
    }
    
    @Override
    public String execute() 
    {
        StringBuilder answerSb = new StringBuilder();
        StringBuilder errorSb = new StringBuilder();
        String errorStr = "";
        try
        {
            Process proc = Runtime.getRuntime().exec(new String[]{"csh","-c",commandText});
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader errorbf = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line = "";
            while((line = reader.readLine()) != null)
            {
                answerSb.append(line);
                answerSb.append("\n");
            }
            
            while((line = errorbf.readLine()) != null)
            {
                errorSb.append(line);
                errorSb.append("\n");
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        errorStr = errorSb.toString();
        
        return answerSb.toString() + (errorStr.length() > 0 ? ("ERROR:" + errorStr) : "");
    }
    
            
            
}
