package game;

import jplay.Sprite;
import jplay.URL;

public class Projectile extends Sprite {
	protected static final double velocity = 14;
	public int angle = 2;
	private boolean moving = false;
	public SpaceShip origem;
	
	public Projectile(SpaceShip ship) {
		super(URL.sprite("bullet_.png"),3);
		this.origem = ship;
		this.angle = ship.angle;
		int midle = 0;
		switch (angle) {
			//up+left
			case 1:
			//down+right	
			case 8:
				midle = 30;
				break;
			//up
			case 2:
			//down
			case 7:
				midle = 20;
				break;
			//up+right
			case 3:
			//down+left	
			case 6:
				midle = 20;
				break;
			//right
			case 4:
			//left	
			case 5:
				midle = 22;
				break;
				
			}
		this.x = ship.x + midle;
		this.y = ship.y + midle;

		
	}
	
	public void move() {
		double velocityD =  Projectile.velocity;
		this.setSequence(0, 2);
		switch (this.angle) {
		//in line
		//up
		case 2:
			this.y -= Projectile.velocity;
			break;
		//left
		case 4:
			this.x -= Projectile.velocity;
			break;
		//right
		case 5:
			this.x += Projectile.velocity;
			break;
		//down
		case 7:
			this.y += Projectile.velocity;
			break;
		
		//in diagonal
		case 1:
			this.x -= velocityD;
			this.y -= velocityD;
			break;
		case 3:
			this.x += velocityD;
			this.y -= velocityD;
			break;
		case 6:
			this.x -= velocityD;
			this.y += velocityD;
			break;
		case 8:
			this.x += velocityD;
			this.y += velocityD;
			break;
		}
		this.moving = true;
		if (this.moving ) {
			this.draw();
			this.update();
			this.moving = false;
		}
		
	}
	
	
	
	public boolean limits() {
		boolean state = false;
		if (this.x > SpaceShip.marginRight + 50 || this.x < SpaceShip.marginLeft ||
				this.y > SpaceShip.marginDown + 50 || this.y < SpaceShip.marginTop) {
			state = true;
		}
		return state;
	}

}
