import java.io.*;
import java.net.*;

public class Server {
    
    public static void main(String args[]) {
		
		if (args.length != 1) {
			System.out.println("Usage:  server <port>");
			return;
		}
		
        int portNumber = Integer.parseInt(args[0]);

		// report the localhost IP address
		try {
			InetAddress host = InetAddress.getLocalHost();
			String hostName = host.getHostName();
			System.out.println("Local host is " + host.toString());
			System.out.println("Local host name is " + hostName);
		
        } catch (UnknownHostException e) {
			System.out.println("Couldn't get local host address");
			return;
		}

		ServerSocket server;
		try {
			server = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println("IO exception trying to create server socket");
			return;
		}
			
        // keep listening indefinitely for client requests
        while (true) {
            System.out.println("Waiting for a client request");
			
			// wait for connection and get input stream
			Socket socket;
			try {
				socket = server.accept();
			} catch (IOException e) {
				System.out.println("IO exception waiting for client request");
				return;
			}

			// get input stream
			InputStream is;
			try {
				is = socket.getInputStream();
			} catch (IOException e) {
				System.out.println("Couldn't get input stream from socket");
				return;
			}

			// keep reading from socket until caller closes it
			byte[] buffer = new byte[7];
			int n;
			while (true) {
            
				// read from socket into byte array
				try {
					n = is.read(buffer);
				} catch (IOException e) {
					System.out.println("IO exception when reading from socket");
					break;
				}
				
				System.out.println("Have read " + n + " bytes from socket");
				if (n <= 0) break;
				System.out.println(new String(buffer, 0, n));
			}
			
			// close stream and socket
			try {
				is.close();
				socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't close socket");
				return;
			}				
        }
    }
    
}
