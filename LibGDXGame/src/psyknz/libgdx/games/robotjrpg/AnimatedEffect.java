package psyknz.libgdx.games.robotjrpg;

public abstract class AnimatedEffect implements GameEffect {
    
	// Duration of the animation in seconds.
	private float animationTimer;
	
	public AnimatedEffect(float animationTimer) {
		this.animationTimer = animationTimer;
	}
	
	// The 
	public abstract void animate(float delta);
	
	// The effect function should be overwritten whenever an instance of an animatedEffect is generated. It will be called once the animation has completed.
	public abstract void effect();
}
