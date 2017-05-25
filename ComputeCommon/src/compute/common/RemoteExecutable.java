/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compute.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author igorribeiro
 */
public interface RemoteExecutable extends Remote
{
    public <T> T executeTask(Task<T> task) throws RemoteException;
}
