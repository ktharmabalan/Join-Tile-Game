package ca.codemake.join.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    private float timer;

    public static boolean proceed = true;

    public PlayState(GSM gsm) {
        super(gsm);
        createBoard();
    }

    public void createBoard() {
        row = 7;
        col = 5;

        if(Join.WIDTH > Join.HEIGHT && row > col) {
            size = Join.HEIGHT / row;
        } else if(Join.WIDTH < Join.HEIGHT && row < col) {
            size = Join.WIDTH / col;
        } else if(Join.WIDTH < Join.HEIGHT && row > col) {
            size = Join.HEIGHT / row;
        } else if(Join.WIDTH > Join.HEIGHT && row < col) {
            size = Join.WIDTH / col;
        } else if(Join.WIDTH == Join.HEIGHT && row > col) {
            size = Join.WIDTH / row;
        } else if(Join.WIDTH == Join.HEIGHT && row < col) {
            size = Join.WIDTH / col;
        } else if(Join.WIDTH > Join.HEIGHT && row == col) {
            size = Join.HEIGHT / row;
        } else if(Join.WIDTH == Join.HEIGHT && row < col) {
            size = Join.WIDTH / col;
        } else if(Join.WIDTH == Join.HEIGHT && row == col) {
            size = Join.WIDTH / row;
        }
//        size = (Join.WIDTH >= Join.HEIGHT ? Join.HEIGHT : Join.WIDTH) / (row >= col ? row : col);
//        size = Join.WIDTH / col;

        x = Join.WIDTH / 2;
        y = Join.HEIGHT / 2;

        width = size * col;
        height = size * row;

        gridBoard = new GridBoard(x, y, width, height, row, col, size);
    }

    public void handleInput(float dt) {
        if(proceed && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse);

//            System.out.println(mouse.x + ", " + mouse.y + "| xOffset: " + gridBoard.xOffset + " | Join.WIDTH: " + Join.WIDTH + " | width: " + width);

            if (mouse.x < Join.WIDTH - gridBoard.xOffset && mouse.x > gridBoard.xOffset && mouse.y < Join.HEIGHT && mouse.y > 0) {

                int r = (int) ((mouse.x - gridBoard.xOffset) / size);
                int c = (int) (mouse.y / size);

                proceed = false;
                gridBoard.removeSelected(c, r);
//                gridBoard.printXY(c, r);
            }
        }
//        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//            mouse.x = Gdx.input.getX();
//            mouse.y = Gdx.input.getY();
//            cam.unproject(mouse);
//
//            if (mouse.x < Join.WIDTH - gridBoard.xOffset && mouse.x > gridBoard.xOffset && mouse.y < Join.HEIGHT && mouse.y > 0) {
//                int r = (int) ((mouse.x - gridBoard.xOffset) / size);
//                int c = (int) (mouse.y / size);
//
////                proceed = false;
////                gridBoard.removeSelected(c, r);
//                gridBoard.gridTiles[c][r].printData();
//            }
//        }
    }

    public void update(float dt) {
        handleInput(dt);
        gridBoard.update(dt);

    }

    public void render(SpriteBatch sb) {
        gridBoard.render(sb);
    }
}
