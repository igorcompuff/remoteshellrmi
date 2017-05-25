package computeserver;

import compute.common.RemoteExecutable;
import compute.common.Task;
import java.rmi.RemoteException;
/**
 *
 * @author igorribeiro
 */
public class RemoteTaskExecutor implements RemoteExecutable, java.io.Serializable
{
    public RemoteTaskExecutor()
    {
        super();
    }

    @Override
    public <T> T executeTask(Task<T> task) throws RemoteException 
    {
        return task.execute();
    }
}
