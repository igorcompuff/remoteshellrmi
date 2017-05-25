/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computeserver;

import compute.common.Configuration;
import compute.common.RemoteExecutable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class represents the server-side of the remote shell with Java RMI. The server receives client commands and executes them.
 * @author igorribeiro
 */
public class Server 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {   
        //We need to configure security manager in order to accept source code exchange between client and server
        SecurityManager secManager = System.getSecurityManager();
        
        if (secManager == null)
        {
            secManager = new SecurityManager();
            System.setSecurityManager(secManager);
        }
        
        RemoteTaskExecutor engine = new RemoteTaskExecutor();
        
        try
        {
            RemoteExecutable stub = (RemoteExecutable)UnicastRemoteObject.exportObject(engine, 0);
            Registry reg = LocateRegistry.getRegistry();
            reg.rebind(Configuration.REFERENCE_NAME, stub);
            System.out.println("All done. Server started");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
