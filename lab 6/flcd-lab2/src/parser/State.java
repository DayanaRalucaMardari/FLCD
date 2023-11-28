package parser;

public class State {
    String currentState;

    public State() {
        currentState = "N";
    }

    public void setState(String newState) {
        this.currentState = newState;
    }

    public String getState() {
        return this.currentState;
    }
}
