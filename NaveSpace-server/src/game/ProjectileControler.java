package game;

import java.util.LinkedList;

import jplay.Sound;
import jplay.URL;

public class ProjectileControler {
	LinkedList<Projectile> normalShots = new LinkedList<>();
	int size = 0;
	
	
	public void addProjectile(SpaceShip ship) {
		this.normalShots.add(new Projectile(ship));
		size++;
		shotSound();	
	}
	public void run(SpaceShips ships) {
		try {
			for(Projectile p: normalShots) {
				p.move();
				if (p.limits()) {
					removeProjectile(p);
				}else {
					for (SpaceShip ss : ships.ships) {
						if (!p.origem.equals(ss)) {
							if (p.collided(ss)) {
								ss.life -= p.origem.damage;
								removeProjectile(p);
								if (!ss.isLife()) {
									//morreu
									ss.death();
									
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("deu ruim>>"+e.getMessage());
		}
		
		
	}
	
	private void removeProjectile(Projectile p) {
		size--;
		normalShots.remove(p);
	}
	
	private void shotSound() {
		new Sound(URL.audio("laser.wav")).play();
	}
	
}
