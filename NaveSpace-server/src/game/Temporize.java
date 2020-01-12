package game;

import java.util.Timer;
import java.util.TimerTask;

public class Temporize{
	SpaceShips ships;
	Timer timer = null;
    int segunds = 0;
    int minuts = 0;
    int hours = 0;
    int partialSegund = 0;
    
	public Temporize(SpaceShips ships) {
		this.ships = ships;
		timer = new Timer();    
		timer.scheduleAtFixedRate(new Counter(), 0, 500);
	}  	
	
	class Counter extends TimerTask{
		@Override
		public void run() {
			resetCDR();
			increment_timer();
		}
	}
	
	public String hora() {
		return (this.hours+":"+this.minuts+":"+this.segunds);
	}
	
	public void resetCDR() {
		this.ships.counterReducer();
	}
	
	public void increment_timer() {
		this.partialSegund++;
		if (this.partialSegund == 2) {
			this.partialSegund = 0;
			this.segunds++;
			if (this.segunds == 60) {
				this.segunds = 0;
				this.minuts++;
				if (this.minuts == 60) {
					this.minuts = 0;
					this.hours++;
				}
			}
		}	
	}
	
	public void finish() {
		timer.cancel();
	}
		
	
}
