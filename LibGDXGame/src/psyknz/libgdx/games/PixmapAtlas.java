package psyknz.libgdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Disposable;

public class PixmapAtlas implements Disposable {
    
	// Texture and pixmap the atlas is generated using.
	private Texture tex;
	private Pixmap pix;
	
	// Collection of TextureRegions to be used by game elements.
	private ArrayList<TextureRegion> region;
	
	/* Sets the width and height of the PixmapAtlas when it is constructed.
	 * Forces the texture size to be a power of 2. */
	public PixmapAtlas(int width, int height) {
		width = getPower2(width);
		height = getPower2(height);
		pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		tex = new Texture(pix);
		
		region = new ArrayList<TextureRegion>();
	}
	
	// Returns the Pixmap being used to generate the texture.
	public Pixmap getPixmap() {
		return pix;
	}
	
	/* Adds a new TextureRegion to the end of the list and returns its position.
	 * If the TextureRegion is outside the bounds of the pixmap the Atlas is extended in size. */
	public int addRegion(int x, int y, int width, int height) {
		int index = region.size();
		
		// Increases the size of the atlas to accomodate the new TextureRegion.
		if(x + width > pix.getWidth() || y + height > pix.getHeight()) {
			Pixmap newPix = new Pixmap(getPower2(x + width), getPower2(y + height), Pixmap.Format.RGBA8888);
			newPix.drawPixmap(pix, 0, 0);
			pix.dispose();
			pix = newPix;
			reloadTexture();
		}
		
		region.add(new TextureRegion(tex, x, y, width, height));
		return index;
	}
	
	/* Return the TextureRegion at the specified position in the list.
	 * Returns null if the TextureRegion requested is outside of the lists range. */
	public TextureRegion getRegion(int index) {
		if(index < region.size() && index > 0) {
	    	return region.get(index);
		}
		else return null;
	}
	
	/* Reloads the current texture from its pixmap. This should be called when resuming after context loss.
	 * It is also called to apply any changes to the pixmap to the texture. */
	public void reloadTexture() {
		if(tex != null) tex.dispose();
		tex = new Texture(pix);
	}
	
	// Disposes the texture and pixmap used for the atlas.
	@Override
	public void dispose() {
		tex.dispose();
		pix.dispose();
	}
	
	// Returns the next power of 2 which is greater than or equal to val.
	private int getPower2(int val) {
		int pow2 = 2;
		for(int i = 2; val > pow2; i++) {
			pow2 = (int) Math.pow(2, i);
		}
		return pow2;
	}
}
