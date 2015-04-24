package ca.codemake.join.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;
import ca.codemake.join.state.State;

/**
 * Created by Kajan on 4/20/2015.
 */
public class GSM {

    private Stack<State> states;

    public GSM() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void setState(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
