/*
Copyright (C) 2004 Geoffrey Alan Washburn
    
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
    
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
    
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
USA.
*/

/**
 * An abstract class for {@link Client}s in a {@link Maze} that local to the 
 * computer the game is running upon. You may choose to implement some of 
 * your code for communicating with other implementations by overriding 
 * methods in {@link Client} here to intercept upcalls by {@link GUIClient} and 
 * {@link RobotClient} and generate the appropriate network events.
 * @author Geoffrey Washburn &lt;<a href="mailto:geoffw@cis.upenn.edu">geoffw@cis.upenn.edu</a>&gt;
 * @version $Id: LocalClient.java 343 2004-01-24 03:43:45Z geoffw $
 */


public abstract class LocalClient extends Client {
	
		protected MazewarClient client = null;
		protected boolean isPlayable = false;
        /** 
         * Create a {@link Client} local to this machine.
         * @param name The name of this {@link Client}.
         */
        public LocalClient(String name) {
                super(name);
                assert(name != null);
        }
        
        public void registerMazewarClient(MazewarClient client) {
        	assert(client != null);
            assert(this.client == null);
            this.client = client;
            client.sendEvent(this, ClientEvent.register);
        }
        
        /**
         * Move the client forward.
         * @return <code>true</code> if move was successful, otherwise <code>false</code>.
         */
        protected boolean forward() {
        	if (isPlayable) {
        		return super.forward();
        	}
        	
        	return false;
        }
        
        /**
         * Move the client backward.
         * @return <code>true</code> if move was successful, otherwise <code>false</code>.
         */
        protected boolean backup() {
        	if (isPlayable) {
        		return super.backup();
        	} 
        	
        	return false;
        }
        
        /**
         * Turn the client ninety degrees counter-clockwise.
         */
        protected void turnLeft() {
        	super.turnLeft();
        }
        
        /**
         * Turn the client ninety degrees clockwise.
         */
        protected void turnRight() {
        	super.turnRight();
        }
        
        /**
         * Fire a projectile.
         * @return <code>true</code> if a projectile was successfully launched, otherwise <code>false</code>.
         */
        
        // TODO(Zen): Fix firing
        protected boolean fire() {
        	if (isPlayable) {
        		return super.fire();
        	} else if (maze.clientFire(this)) {
        		return client.sendEvent(this, ClientEvent.fire);
        	}
        	
        	return false;
        }
}

