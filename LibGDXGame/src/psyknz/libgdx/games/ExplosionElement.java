package psyknz.libgdx.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ExplosionElement implements GameElement {
    
	// The number of particles generated during the explosion, their maximum size and speed.
	private final int particleCount = 32;
	private final float maxSize = 8;
	private final float maxSpeed = 8;
	private final float maxDuration = 4;
	
	// The element this effect reports to when the effect finishes.
	private ElementListener parent;
	
	// The sprite used to represent the particles.
	private Sprite sprite;
	
	// The color of the particles.
	public Color color;
	
	private Array<ParticleElement> particles;
	
	public ExplosionElement(ElementListener parent, Sprite sprite, Color color, float x, float y) {
		this.parent = parent;
		this.sprite = sprite;
		this.color = color;
		
		particles = new Array<ParticleElement>();
		for(int i = 0; i < particleCount; i++) {
			particles.add(new ParticleElement(x, y, MathUtils.random(maxSize), 
			                                  MathUtils.random() * MathUtils.PI2 - MathUtils.PI,
											  MathUtils.random(maxSpeed), MathUtils.random(maxDuration)));
		}
	}
	
	public void update(float delta) {
		if(particles.size > 0) {
		    for(int i = 0; i < particles.size; i++) {
			    particles.get(i).update(delta);
		    }
		}
		else parent.event(new ElementEvent(this, ElementEvent.EventType.FINISHED));
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setColor(color);
		
		for(int i = 0; i < particles.size; i++) {
			particles.get(i).draw(batch);
		}
	}
	
	private class ParticleElement implements GameElement {
		
		private Rectangle bounds;
		
		private float angle, speed, duration;
		
		public ParticleElement(float x, float y, float size, float angle, float speed, float duration) {
			bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
			this.angle = angle;
			this.speed = speed;
			this.duration = duration;
		}
		
		public void update(float delta) {
			bounds.x += MathUtils.sin(angle) * speed * delta;
			bounds.y += MathUtils.cos(angle) * speed * delta;
			
			duration -= delta;
			if(duration <= 0) {
				particles.removeValue(this, false);
			}
		}
		
		public void draw(SpriteBatch batch) {
			sprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			sprite.draw(batch);
		}
	}
}
