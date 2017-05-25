/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computeclient;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import compute.common.Configuration;
import compute.common.RemoteExecutable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author igorribeiro
 */
public class Client {

    private String currentDir;
    RemoteExecutable m_stub;
    Registry m_registry;
    
    public Client() throws RemoteException, NotBoundException
    {
        currentDir = "";
        initStub(Configuration.REFERENCE_NAME);
    }
    
    private void configureSecurityManager()
    {
        SecurityManager secManager = System.getSecurityManager();
        
        if (secManager == null)
        {
            secManager = new SecurityManager();
            System.setSecurityManager(secManager);
        }
    }
    
    private void initStub(String referenceName) throws RemoteException, NotBoundException
    {
        configureSecurityManager();
        if (m_stub == null)
        {
            if (m_registry == null)
            {
                m_registry = LocateRegistry.getRegistry();
            }
            
            m_stub = (RemoteExecutable)m_registry.lookup(referenceName);
        }
    }
    
    public String executeCommand(ShellCommand command) throws RemoteException, NotBoundException
    {
        return m_stub.executeTask(command);
    }
    
    public boolean executeCommand(String command) throws RemoteException, NotBoundException
    {
        String result = "";
        String cdToCurrentDir = "";
        
        if (command.equalsIgnoreCase("clear"))
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            return true;
        }
        
        if (command.toLowerCase().equals("exit"))
        {
            return false;
        }
        
        
        cdToCurrentDir = "cd " + getCurrentDir() + "; ";
        if (command.toLowerCase().startsWith("cd"))
        {
            command =  cdToCurrentDir + command + "; " + "pwd";
            result = executeCommand(new ShellCommand(command));

            if (!result.contains("ERROR:"))
            {
                setCurrentDir(result);
                display("");
            } 
        }
        else
        {
            command = cdToCurrentDir + command; 
            result = executeCommand(new ShellCommand(command));

            if (!result.contains("ERROR:"))
            {
                display(result);
            }
        }
        
        if (result.contains("ERROR:"))
        {
            display(result.split("ERROR:")[1]);
        }
        
        return true;
    }
    
    public void setCurrentDir(String dir)
    {
        currentDir = dir;
    }
    
    public String getCurrentDir()
    {
        return currentDir;
    }
    
    public String getCommand()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public void display(String result)
    {
        String banner = currentDir.substring(0, currentDir.length() - 1) + "$ ";
        System.out.print(result + banner);
    }
    
    public static void main(String[] args) 
    {
        String command = "";
        
        try
        {   
            Client client = new Client();
            client.setCurrentDir(client.executeCommand(new ShellCommand("pwd")));
            client.display("");
            do
            {
                command = client.getCommand();
 
            } while (client.executeCommand(command));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
