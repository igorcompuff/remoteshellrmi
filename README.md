# RMI Remote Shell

This is a simple program that implements a remote shell using Java RMI. The application is composed by three parts: client, server and commn. The server is resposible to execute commands sent by the client and send back the result. The client is a console application that emulates a shell. The user type the comands andthe client sends the command to execution on the server. The common contains some classes that should be known be server and client (mainly the stub class).

This programam uses the SecurityManager, so it is necessary to provide two policy files, one to the client and another one to the server. We also provide these two files to make things a bit easier. However, you still have to inform the JVMthe path to these files. To do so, you need to pass the following argument to the java command:

-Djava.security.policy=<path to the  server/client policy file> 

Note that the above argument should be used in the execution of both server and client programs.

One additional complication is that you will need a webserver up and running to execute this remote shell. The webserver is used by client and server to retrieve missing classes. To make things easier, simply create a new directory (call it classes/) inside the root directory of your webserver (sometimes this directory is called public_html). Copy the contents of the build directory inside the client and common to the classes directory.

The next step is to start the Registry, which will provide the mechanism to publish and retrieve the stub class. In order to start the registry, entre the following comand:

rmiregistry -J-Djava.rmi.server.codebase="http://localhost/classes/"

Next, you need to start the server. Enter the following command:

sudo java -Djava.security.policy=<path to the server policy file> -Djava.rmi.server.codebase=http://localhost/classes/ -Djava.rmi.server.hostname=127.0.0.1 -Djava.rmi.server.useCodebaseOnly=false -jar client.jar

Now you can start the client using a similar command:

sudo java -Djava.security.policy=<path to the client policy file> -Djava.rmi.server.codebase=http://localhost/classes/ -Djava.rmi.server.hostname=127.0.0.1 -Djava.rmi.server.useCodebaseOnly=false -jar server.jar  

