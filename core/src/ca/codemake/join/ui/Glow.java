package ca.codemake.join.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ca.codemake.join.Join;
import ca.codemake.join.state.PlayState;

/**
 * Created by Kajan on 4/20/2015.
 */
public class Glow extends GridTile {

    private float alpha;
    private float speed = 200;

    protected float timer = 0.05f * -1;
    protected float maxTime = 0.5f;

    private TextureRegion red;
    private TextureRegion green;
    private TextureRegion blue;

    public boolean removeGlow;

    public Glow(int color, float x, float y, float width, float height, int i, int j) {
        super(color, x, y, width, height, i, j);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.r = i;
        this.c = j;

        removeGlow = false;

        alpha = 0;

        red = Join.tal.getAtlas("pack").findRegion("red");
        green = Join.tal.getAtlas("pack").findRegion("green");
        blue = Join.tal.getAtlas("pack").findRegion("blue");
    }

    public boolean shouldRemove() {
        return removeGlow;
    }

    public void update(float dt) {
        timer += dt;
        width += dt * speed;
        height += dt * speed;
        x -= (dt * speed) / 2;
        y -= (dt * speed) / 2;

//        System.out.println(timer + " : " + maxTime);

        if (timer > maxTime) {
            removeGlow = true;
            PlayState.proceed = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        alpha = 1 - timer / maxTime;
        sb.setColor(1, 1, 1, alpha);
        if(getDrawColor() == 1) {
            sb.draw(red, getX(), getY(), getWidth(), getHeight());
        } else if(getDrawColor() == 2) {
            sb.draw(green, getX(), getY(), getWidth(), getHeight());
        } else if(getDrawColor() == 3) {
            sb.draw(blue, getX(), getY(), getWidth(), getHeight());
        } else if(getDrawColor() == 4) {
        }
        sb.setColor(1, 1, 1, 1);
        sb.end();
    }

}
