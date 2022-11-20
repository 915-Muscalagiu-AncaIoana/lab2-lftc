package src;

public class Transition {
    String startState;
    String endState;
    String symbolTransition;

    public Transition (String startState, String endState, String symbolTransition) {
        this.startState = startState;
        this.endState = endState;
        this.symbolTransition = symbolTransition;
    }

    public String getStartState () {
        return startState;
    }

    public void setStartState (String startState) {
        this.startState = startState;
    }

    public String getEndState () {
        return endState;
    }

    public void setEndState (String endState) {
        this.endState = endState;
    }

    public String getSymbolTransition () {
        return symbolTransition;
    }

    public void setSymbolTransition (String symbolTransition) {
        this.symbolTransition = symbolTransition;
    }

    @Override
    public String toString () {
        return "(" + startState+ "," + symbolTransition+")"+"->"+endState;
    }
}
