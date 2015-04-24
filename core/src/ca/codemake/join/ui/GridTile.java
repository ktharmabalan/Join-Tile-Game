package ca.codemake.join.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ca.codemake.join.Join;
import ca.codemake.join.state.PlayState;

/**
 * Created by Kajan on 4/17/2015.
 */
public class GridTile extends Box{

    public int color;
    public int originalColor;
    private OrthographicCamera cam;

    protected float maxWidth;
    protected float maxHeight;

    public float realX;
    public float realY;

    public boolean checked;
    public boolean remove;
    public boolean shrink;
    public boolean removed;
    public boolean glow;
    public boolean hasGlow;
    public int i, j;



    private TextureRegion red;
    private TextureRegion green;
    private TextureRegion blue;

    public GridTile(int color, float x, float y, float width, float height, int i, int j) {
        this.originalColor = color;
        this.color = color;
        realX = this.x = x + 5;
        realY = y + 5;
        this.y = Join.HEIGHT;
        maxWidth = this.width = width - 10;
        maxHeight = this.height = height - 10;
        checked = false;
        remove = false;
        shrink = false;
        removed = false;
        glow = false;
        hasGlow = false;

        this.i = i;
        this. j = j;

        red = Join.tal.getAtlas("pack").findRegion("red");
        green = Join.tal.getAtlas("pack").findRegion("green");
        blue = Join.tal.getAtlas("pack").findRegion("blue");

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Join.WIDTH, Join.HEIGHT);
    }

    public int getColor() {
        return originalColor;
    }

    public int getDrawColor() {
        return color;
    }

    public void changeColor() {
//        color = 4;
    }

    public void shrink() {
        if(shrink) {
            if (width >= 0 && height >= 0) {
                x += 1;
                y += 1;

                width -= 2;
                height -= 2;
            }

            if(width < 0 && height < 0) {
                width = 0;
                height = 0;
                shrink = false;
                remove = true;
                PlayState.proceed = true;
            }
        }
    }

    public void update(float dt) {
        if(y > realY) {
            y -= dt * 200;
            if(y <= realY) {
                y = realY;
            }
        }
//        if(!remove) {
//            if (width < maxWidth && height < maxHeight) {
//                x = realX;
//                y = realY;
//                System.out.println("UPDATE");
//                timer += dt;
//                width = (timer / maxTime) * maxWidth;
//                height = (timer / maxTime) * maxHeight;
//
//                if (width < 0) width = 0;
//                if (height < 0) height = 0;
//                if (width > maxWidth) width = maxWidth;
//                if (height > maxHeight) height = maxHeight;
//            }
//        }

        if(glow) {
//            glow(dt);
//            glowTile = new Glow(color, x, y, width, height);
//            glowTile.update(dt);

        }
        if (shrink) {
            shrink();
        }
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        drawTile(sb);
        sb.end();
    }

    public void glow(float dt) {
//        timer += dt;
//        width += dt * speed;
//        height += dt * speed;
//        x -= (dt * speed) / 2;
//        y -= (dt * speed) / 2;
//        System.out.println(timer + " : " + maxTime);
//        if (timer > maxTime) {
//            glow = false;
//            System.out.println("PROCEED");
//
////            width = maxWidth;
////            height = maxHeight;
////            x = realX;
////            y = realY;
//
////            this.remove = true;
//            remove = false;
//            PlayState.proceed = true;
//        }
    }

    public void drawTile(SpriteBatch sb) {

        if(glow) {
//            if(glowTile.shouldRemove()) {
//                glow = false;
//            }
//            else {
//                glowTile.render(sb);
//            }
//            alpha = 1 - timer / maxTime;
//            System.out.println(alpha);
//            sb.setColor(1, 1, 1, alpha);
////            sb.draw(warp, getX(), getY(), getWidth(), getHeight());
//            if(getDrawColor() == 1) {
//                sb.draw(red, getX(), getY(), getWidth(), getHeight());
//            } else if(getDrawColor() == 2) {
//                sb.draw(green, getX(), getY(), getWidth(), getHeight());
//            } else if(getDrawColor() == 3) {
//                sb.draw(blue, getX(), getY(), getWidth(), getHeight());
//            } else if(getDrawColor() == 4) {
//            }
//            sb.setColor(1, 1, 1, 1);
        }
//        else {
//            System.out.println("GO");
            if (getDrawColor() == 1) {
                sb.draw(red, getX(), getY(), getWidth(), getHeight());
            } else if (getDrawColor() == 2) {
                sb.draw(green, getX(), getY(), getWidth(), getHeight());
            } else if (getDrawColor() == 3) {
                sb.draw(blue, getX(), getY(), getWidth(), getHeight());
            } else if (getDrawColor() == 4) {
            }
//        }
    }
}
