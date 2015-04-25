package ca.codemake.join.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

import ca.codemake.join.Join;
import ca.codemake.join.state.PlayState;

/**
 * Created by Kajan on 4/17/2015.
 */
public class GridBoard extends Box {

    public int row;
    public int col;
    public GridTile[][] gridTiles;
    public float xOffset;
    public float yOffset;
    public int size;
    public ArrayList<GridTile> removeTiles;
    public Array<Glow> glows;
    public ShapeRenderer shapeRenderer;

    private OrthographicCamera cam;

    private Random rand;
    private int color;

//    public GridTile[] chain;

//    private Array<Glow> glows;

    public GridBoard(float x, float y, float width, float height, int row, int col, int size) {

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Join.WIDTH, Join.HEIGHT);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.row = row;
        this.col = col;
        this.size = size;
        gridTiles = new GridTile[row][col];

        xOffset = (Join.WIDTH - width / 2);
        yOffset = (Join.HEIGHT - height / 2);

        glows = new Array<Glow>();

        createGrid();
    }

    private void createGrid() {
        rand = new Random();
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                color = rand.nextInt(3 - 1 + 1) + 1;
                gridTiles[j][i] = new GridTile(color, j * size + (getX() - getWidth() / 2), i * size, size, size, i, j);
            }
        }
    }


    public void update(float dt) {
        for (int j = 0; j < glows.size; j++) {
            glows.get(j).update(dt);

            if(glows.get(j).shouldRemove()) {
                GridTile gt = gridTiles[glows.get(j).i][glows.get(j).j];
//                gt.remove = false;
//                gt.checked = false;
                gt.glow = false;
                gt.hasGlow = false;
                glows.removeIndex(j);
                j--;
                if(gt.remove) {
                    moveDown(gt.i, gt.j);
                }
            }
        }

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if(gridTiles[j][i].glow && !gridTiles[j][i].hasGlow) {
                    glows.add(new Glow(gridTiles[j][i].color, gridTiles[j][i].x, gridTiles[j][i].y, gridTiles[j][i].width, gridTiles[j][i].height, i, j));
                    gridTiles[j][i].hasGlow = true;
//                    System.out.println("i: " + i + ", j: " + j);
                }
                gridTiles[j][i].update(dt);
            }
        }
    }

    public void render(SpriteBatch sb) {
        drawGrid();

        for (Glow gl : glows) {
            gl.render(sb);
        }

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                gridTiles[j][i].render(sb);
            }
        }

    }

    public void drawGrid() {
        shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, Join.WIDTH, Join.HEIGHT);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(x - width / 2, y - height / 2, width, height);
        shapeRenderer.end();
    }

    private void respawn(int r, int c) {
        color = rand.nextInt(3 - 1 + 1) + 1;
//        gridTiles[r][c].i = gridTiles[r][c].i;
//        gridTiles[r][c].j = gridTiles[r][c].j;
        gridTiles[r][c].removed = false;
        gridTiles[r][c].remove = false;
        gridTiles[r][c].checked = false;
        gridTiles[r][c].glow = false;
        gridTiles[r][c].hasGlow = false;
        gridTiles[r][c].originalColor = color;
        gridTiles[r][c].color = color;
        gridTiles[r][c].width = gridTiles[r][c].maxWidth = size - 10;
        gridTiles[r][c].height = gridTiles[r][c].maxHeight = size - 10;
//        if(r-1>0) {
            gridTiles[r][c].x = gridTiles[r][c].realX;
//        }
//        if(c-1>0) {
//            gridTiles[r][c].y = gridTiles[r][c].realY;
            gridTiles[r][c].y = Join.HEIGHT;
//        }
//        System.out.println(gridTiles[r][c].toString());
    }

    private void moveDown(int r, int c) {
        if(c == col-1){
            respawn(r, c);
            gridTiles[r][c].remove = false;
        }
        else if(c+1 <= col-1 ) {
            if(!gridTiles[r][c+1].remove) {
                moveDown(r, c+1);
                gridTiles[r][c].remove = false;
                gridTiles[r][c+1].remove = true;
                gridTiles[r][c].x = gridTiles[r][c].realX;
                gridTiles[r][c].y = gridTiles[r][c].realY;
                gridTiles[r][c].y = Join.HEIGHT;
                gridTiles[r][c].i = gridTiles[r][c+1].i;
                gridTiles[r][c].j = gridTiles[r][c+1].j;
                gridTiles[r][c].width = gridTiles[r][c+1].maxWidth;
                gridTiles[r][c].height = gridTiles[r][c+1].maxHeight;
                gridTiles[r][c].color = gridTiles[r][c+1].color;
                gridTiles[r][c].originalColor = gridTiles[r][c+1].originalColor;

                gridTiles[r][c].checked = false;
                gridTiles[r][c].removed = false;
                gridTiles[r][c].glow = false;
                gridTiles[r][c].hasGlow = false;
//                System.out.println("Moving x: " + r + " y2: " + (c+1) + " to y1: " + c);

//                gridTiles[r][c+1].removed = true;
            }
        }
    }

    public void renderOld(SpriteBatch sb) {
        shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if(gridTiles[j][i].getDrawColor() == 1) {
                    shapeRenderer.setColor(1, 0, 0, 0.5f);
                } else if(gridTiles[j][i].getDrawColor() == 2) {
                    shapeRenderer.setColor(0, 1, 0, 0.5f);
                } else if(gridTiles[j][i].getDrawColor() == 3) {
                    shapeRenderer.setColor(0, 0, 1, 0.5f);
                } else if(gridTiles[j][i].getDrawColor() == 4) {
                    shapeRenderer.setColor(0, 0, 0, 0.5f);
                }
                shapeRenderer.rect(gridTiles[j][i].getX(), gridTiles[j][i].getY(), gridTiles[j][i].getWidth(), gridTiles[j][i].getHeight());
            }
        }
        shapeRenderer.end();
    }

    public boolean sameColorLeft(int gtx, int gty) {
        if(gtx - 1 >= 0) {
            if (gridTiles[gtx][gty].getColor() == gridTiles[gtx - 1][gty].getColor()) {
                //gridTiles[gtx][gty].changeColor();
//                gridTiles[gtx - 1][gty].changeColor();

                gridTiles[gtx][gty].checked = true;
                gridTiles[gtx - 1][gty].checked = true;

                gridTiles[gtx][gty].remove = true;
                gridTiles[gtx - 1][gty].remove = true;
                if(!removeTiles.contains(gridTiles[gtx][gty]))
                    removeTiles.add(gridTiles[gtx][gty]);
                if(!removeTiles.contains(gridTiles[gtx - 1][gty]))
                    removeTiles.add(gridTiles[gtx - 1][gty]);

                checkAround(gtx - 1, gty);
                return true;
            }
        }
        return false;
    }

    public boolean sameColorRight(int gtx, int gty) {
        if(gtx + 1 <= row - 1) {
            if (gridTiles[gtx][gty].getColor() == gridTiles[gtx + 1][gty].getColor()) {
                //gridTiles[gtx][gty].changeColor();
//                gridTiles[gtx + 1][gty].changeColor();

                gridTiles[gtx][gty].checked = true;
                gridTiles[gtx + 1][gty].checked = true;

                gridTiles[gtx][gty].remove = true;
                gridTiles[gtx + 1][gty].remove = true;
                if(!removeTiles.contains(gridTiles[gtx][gty]))
                    removeTiles.add(gridTiles[gtx][gty]);
                if(!removeTiles.contains(gridTiles[gtx + 1][gty]))
                    removeTiles.add(gridTiles[gtx + 1][gty]);

                checkAround(gtx + 1, gty);
                return true;
            }
        }
        return false;
    }

    public boolean sameColorBottom(int gtx, int gty) {
        if(gty - 1 >= 0) {
            if (gridTiles[gtx][gty].getColor() == gridTiles[gtx][gty - 1].getColor()) {
                //gridTiles[gtx][gty].changeColor();
                //gridTiles[gtx][gty - 1].changeColor();

                gridTiles[gtx][gty].checked = true;
                gridTiles[gtx][gty - 1].checked = true;

                gridTiles[gtx][gty].remove = true;
                gridTiles[gtx][gty - 1].remove = true;
                if(!removeTiles.contains(gridTiles[gtx][gty]))
                    removeTiles.add(gridTiles[gtx][gty]);
                if(!removeTiles.contains(gridTiles[gtx][gty - 1]))
                    removeTiles.add(gridTiles[gtx][gty - 1]);

                checkAround(gtx, gty - 1);
                return true;
            }
        }
        return false;
    }

    public boolean sameColorTop(int gtx, int gty) {
        if(gty + 1 <= col - 1) {
            if (gridTiles[gtx][gty].getColor() == gridTiles[gtx][gty + 1].getColor()) {
                //gridTiles[gtx][gty].changeColor();
                //gridTiles[gtx][gty + 1].changeColor();

                gridTiles[gtx][gty].checked = true;
                gridTiles[gtx][gty + 1].checked = true;

                gridTiles[gtx][gty].remove = true;
                gridTiles[gtx][gty + 1].remove = true;
                if(!removeTiles.contains(gridTiles[gtx][gty]))
                    removeTiles.add(gridTiles[gtx][gty]);
                if(!removeTiles.contains(gridTiles[gtx][gty + 1]))
                    removeTiles.add(gridTiles[gtx][gty + 1]);

                checkAround(gtx, gty + 1);
                return true;
            }
        }
        return false;
    }

    public void checkAround(int gtx, int gty) {
//        String colo = "";
//        switch(gridTiles[gtx][gty].color){
//            case 1:
//                colo = "RED";
//                break;
//            case 2:
//                colo = "GREEN";
//                break;
//            case 3:
//                colo = "BLUE";
//                break;
//            case 4:
//                colo = "BLACK";
//                break;
//            default:
//                break;
//        }
//        System.out.println(colo);

        if((gty + 1 <= col - 1) && (!gridTiles[gtx][gty + 1].checked)) {
            sameColorTop(gtx, gty);
        }
        if((gtx - 1 >= 0) && (!gridTiles[gtx - 1][gty].checked)) {
            sameColorLeft(gtx, gty);
        }
        if((gtx + 1 <= row - 1) && (!gridTiles[gtx + 1][gty].checked)) {
            sameColorRight(gtx, gty);
        }
        if((gty - 1 >= 0) && (!gridTiles[gtx][gty - 1].checked)) {
            sameColorBottom(gtx, gty);
        }
    }

    public void removeSelected(int gtx, int gty) {
        removeTiles = new ArrayList<GridTile>();
        checkAround(gtx, gty);

//        Collections.reverse(removeTiles);

        for(GridTile gt : removeTiles) {
//            gt.checked = false;
//            gt.remove = false;
            System.out.println("i: " + gt.i + ", j: " + gt.j);
            gt.glow = true;
        }

        if(removeTiles.size() == 0) {
            PlayState.proceed = true;
        }
    }
}
