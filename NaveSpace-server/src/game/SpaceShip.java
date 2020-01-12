package game;

import java.net.InetAddress;

import jplay.Sprite;
import jplay.URL;

public class SpaceShip extends Sprite{
	final public int aaCDR = 1;
	final public int spCDR = 10;	
	final public int dfCDR = 10;
	
	public InetAddress address;
	
	public int aa = 0;
	public int sp = 0;
	public int df = 0;
	
	public int life;
	public int damage;
	public double velocity;
	public int angle = 7;
	
	public ProjectileControler shots ;
	
	
	public static int marginTop = 0, marginLeft=0, marginDown = 540, marginRight = 738;
	
	public SpaceShip(int x, int y, int image, ProjectileControler shots, InetAddress address) {
			super(URL.sprite("real-nave"+image+".png"),16);
			this.address = address;
			this.shots = shots;
			this.x = x;
			this.y = y;
			//status
			this.velocity = 2.0;
			this.life = 100;
			this.damage = 20;
	}
	
	public boolean isLife() {
		return (this.life > 0); 
	}
	
	public void stop(int position) {
		switch (position) {
		case 1:
			this.setSequence(12, 12 );
			break;
		case 2:
			this.setSequence(10, 10);
			break;
		case 3:
			this.setSequence(14, 14);
			break;
		case 4:
			this.setSequence(6, 6);
			break;
		case 5:
			this.setSequence(8,8);

			break;
		case 6:
			this.setSequence(2, 2);

			break;
		case 7:
			this.setSequence(0, 0);

			break;
		case 8:
			this.setSequence(4, 4 );

			break;

		}
	}
	
	
	public void mover(String command) {		
		if (command.equals("#UP")) {
			if (y > marginTop) {
				setSequence(11, 11);
				this.angle = 2;
				movimentation(this);

			}
		}else
		if(command.equals("#UL")) {
			if (y > marginTop && x > marginLeft) {
				setSequence(13, 13);
				this.angle = 1;
				movimentation(this);
				

			}		
		}else
		if (command.equals("#UR")) {
			if (y > marginTop && x < marginRight) {
				setSequence(15, 15);
				this.angle = 3;
				movimentation(this);

			}	
		}else
		if (command.equals("#DW")) {
			if (y < marginDown) {
				setSequence(1, 1);
				this.angle = 7;
				movimentation(this);
			}	
		}else
		if(command.equals("#DL")) {
			if (y < marginDown && x > marginLeft) {
				setSequence(3, 3);
				this.angle = 6;
				movimentation(this);


			}	
		}else
		if (command.equals("#DR")) {
			if (y <marginDown && x < marginRight) {
				setSequence(5, 5);				
				this.angle = 8;
				movimentation(this);

			}	
		}else
		if (command.equals("#LF")) {
			if (x > marginLeft) {
				setSequence(7, 7);
				this.angle = 4;
				movimentation(this);


			}		
		}else
		if(command.equals("#RI")) {
			if (x < marginRight) {
				setSequence(9, 9);
				this.angle = 5;
				movimentation(this);

			}
			
		//especial commands	
		}else if (command.equals("#ST")) {
			stop(this.angle);
		}else if(command.equals("#AK")) {
			if (user(this.aa)) {
				try {
					this.shots.addProjectile(this);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			this.aa = this.aaCDR;
			}
		}else if (command.equals("#DF")) {
			if (user(this.df)) {
			
				this.df = this.dfCDR;
			}
		}else if (command.equals("#SP")) {
			if (user(this.sp)) {
				
				this.sp = this.spCDR;
			}
		}
		
		
	}

	public void movimentation(SpaceShip spaceShip) {
		double velocityD =  Math.sqrt(spaceShip.velocity);
		switch (spaceShip.angle) {
		//in line
		//up
		case 2:
			spaceShip.y -= spaceShip.velocity;
			break;
		//left
		case 4:
			spaceShip.x -= spaceShip.velocity;
			break;
		//right
		case 5:
			spaceShip.x += spaceShip.velocity;
			break;
		//down
		case 7:
			spaceShip.y += spaceShip.velocity;
			break;
		
		//in diagonal
		case 1:
			spaceShip.x -= velocityD;
			spaceShip.y -= velocityD;
			break;
		case 3:
			spaceShip.x += velocityD;
			spaceShip.y -= velocityD;
			break;
		case 6:
			spaceShip.x -= velocityD;
			spaceShip.y += velocityD;
			break;
		case 8:
			spaceShip.x += velocityD;
			spaceShip.y += velocityD;
			break;
			
		}
	}
	public void death() {
		this.x = 5000;
		this.y = 5000;
	}
	
	public boolean user(int cdr) {
		return cdr == 0;
	}
	
	public void reducerCDR() {
		if (this.aa > 0) {
			this.aa--;
		}
		if (this.sp > 0) {
			this.sp--;
		}
		if (this.df >0) {
			this.df--;
		}
	
	}
}

