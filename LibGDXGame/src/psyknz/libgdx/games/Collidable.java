package psyknz.libgdx.games;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

    public GameElement testCollision(Rectangle bounds);
}
