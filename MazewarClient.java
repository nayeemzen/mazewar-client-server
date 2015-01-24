import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class MazewarClient {
	
	private Socket socket;
	private ObjectOutputStream out = null;
	
	public void connect(String hostname, int port) {
		try {
			socket = new Socket(hostname, port); 
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("ERROR: Don't know where to connect!!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("ERROR: Couldn't get I/O for the connection.");
			System.exit(1);
		}
	}
	
	public boolean sendEvent(LocalClient client, ClientEvent clientevent) {
		MazewarPacket payload = new MazewarPacket();
		payload.clientName = client.getName();
		
		if (clientevent.equals(ClientEvent.moveForward)) {
			payload.eventType = MazewarPacket.ACTION_MOVE_UP;
		} else if (clientevent.equals(ClientEvent.moveBackward)) {
			payload.eventType = MazewarPacket.ACTION_MOVE_DOWN;
		} else if (clientevent.equals(ClientEvent.turnLeft)) {
			payload.eventType = MazewarPacket.ACTION_TURN_LEFT;
		} else if (clientevent.equals(ClientEvent.turnRight)) {
			payload.eventType = MazewarPacket.ACTION_TURN_RIGHT;
		} else if (clientevent.equals(ClientEvent.fire)) {
			payload.eventType = MazewarPacket.ACTION_FIRE_PROJECTILE;
		} else {
			return false;
		}
		
		try {
			out.writeObject(payload);
		} catch (IOException e) {
			/* TODO how do we handle this? */
			return false;
		}
			
		return true;
	}
}