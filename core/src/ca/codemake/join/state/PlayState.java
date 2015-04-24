package ca.codemake.join.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codemake.join.ui.GridBoard;
import ca.codemake.join.Join;

/**
 * Created by Kajan on 4/15/2015.
 */
public class PlayState extends State {

    private GridBoard gridBoard;
    private int size;
    private float x;
    private float y;
    private float width;
    private float height;
    private int row;
    private int col;

    public static boolean proceed = true;

    public PlayState(GSM gsm) {
        super(gsm);
        createBoard();
    }

    public void createBoard() {
        row = 5;
        col = 5;

        size = (Join.WIDTH > Join.HEIGHT ? Join.HEIGHT : Join.WIDTH) / col;

        x = Join.WIDTH / 2;
        y = Join.HEIGHT / 2;

        width = size * row;
        height = size * col;

        gridBoard = new GridBoard(x, y, width, height, row, col, size);
    }

    public void handleInput(float dt) {
        if(proceed && Gdx.input.justTouched()) {
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse);

            if (mouse.x < Join.WIDTH - gridBoard.xOffset && mouse.x > gridBoard.xOffset && mouse.y < Join.HEIGHT && mouse.y > 0) {
                int r = (int) ((mouse.x - gridBoard.xOffset) / size);
                int c = (int) (mouse.y / size);

                proceed = false;
                gridBoard.removeSelected(r, c);
            }
        }
    }

    public void update(float dt) {
        handleInput(dt);
        gridBoard.update(dt);
    }

    public void render(SpriteBatch sb) {
        gridBoard.render(sb);
    }
}
