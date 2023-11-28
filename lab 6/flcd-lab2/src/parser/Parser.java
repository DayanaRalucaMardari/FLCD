package parser;

import java.util.*;

public class Parser {

    private enum State {
        NORMAL, ERROR, BACK, FINAL
    }

    State state;
    int index;
    int maxIndex;
    Grammar grammar;
    private String[] sequence;
    List<String> inputStack;
    List<List<String>> workingStack;
    private boolean leftRecursive;


    public Parser() {
        this.state = State.NORMAL;
        this.index = 0;
        this.maxIndex = 0;
        this.grammar = new Grammar("/Users/dayana/Documents/uni3/FLCD/FLCD/lab 5/flcd-lab2/G1.txt");
        this.workingStack = new ArrayList<>();
        this.inputStack = new ArrayList<>(List.of(grammar.getStartingSymbol()));
        this.sequence = null;
        this.leftRecursive = checkGrammarLeftRecursive();
        //this.inputStack.add(grammar.getStartingSymbol());
    }

    private boolean checkGrammarLeftRecursive() {
        for (Map.Entry<List<String>, Set<List<String>>> entry : grammar.getProductions().entrySet()) {
            String nonTerminal = entry.getKey().get(0);
            List<List<String>> productions = new ArrayList<>(entry.getValue());
            for (List<String> prod : productions) {
                for (String symbol : prod.subList(0, prod.size() - 1)) { // de verificat mai mult despre sublist
                    if (symbol.equals(nonTerminal)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * Changes the state to BACK(B).
     */
    public void momentaryInsuccess() {
        System.out.println("Momentary insuccess");
        this.state = State.BACK;
        System.out.println("Changed state to BACK.");
    }

    /**
     * Changes the state to FINAL(F).
     */
    public void success() {
        System.out.println("Success!");
        this.state = State.FINAL;
        System.out.println("Changed state to FINAL.");
    }

    /**
     * Increases the index with the value 1.
     * Pushes the top element of the inputStack into the workingStack.
     * Pops the element from the inputStack.
     */
    public void advance() {
        System.out.println("Advance");
        index += 1;
        if (index > maxIndex) {
            maxIndex = index;
        }
        this.workingStack.add(List.of(this.inputStack.get(0)));
        this.inputStack.remove(0);
    }

    /**
     * Takes the non-terminal from the inputStack and searches for the first production.
     * Pushes on the workingStack the first production of the non-terminal as a list containing the non-terminal and the
     * first production.
     * Pops the non-terminal from the inputStack and pushes the first production into the inputStack.
     */
    public void expand() {
        System.out.println("Expand");
        String nonTerminal = this.inputStack.get(0);
        Set<List<String>> firstProductionsSet = grammar.getNonTerminalProductions(List.of(nonTerminal));
        List<String> firstProduction = new ArrayList<>(firstProductionsSet).get(0);

        this.workingStack.add(new ArrayList<>(List.of(nonTerminal, String.join("", firstProduction))));

        this.inputStack.remove(0);
        this.inputStack.addAll(0, firstProduction);
    }

    public void back() {
        System.out.println("Back");
        this.index -= 1;
        String terminal = this.workingStack.get(this.workingStack.size() - 1).get(0);

        this.inputStack.add(0, terminal);
        this.workingStack.remove(this.workingStack.size() - 1);
    }

    public void anotherTry() {
        System.out.println("Another try");

        List<String> lastProduction = this.workingStack.getLast();
        String nonTerminal = lastProduction.get(0);

        List<String> next = this.grammar.getNextProduction(lastProduction, nonTerminal);

        if (next != null) {
            System.out.println("Changing state to NORMAL");
            this.state = State.NORMAL;
            this.workingStack.remove(this.workingStack.size() - 1);
            this.workingStack.add(new ArrayList<>(List.of(nonTerminal, String.join("", next))));

            this.inputStack.subList(0, lastProduction.get(1).length()).clear();
            this.inputStack.addAll(0, next);
        } else if (index == 0 && lastProduction.get(0).equals(grammar.getStartingSymbol())) {
            System.out.println("Changing state to ERROR");
            System.out.println("Error around term " + sequence[maxIndex] + " (index = " + maxIndex + 1 + ")");
            state = State.ERROR;
        } else {
            this.workingStack.remove(this.workingStack.size() - 1);
            this.inputStack.addAll(0, List.of(lastProduction.get(0).split("")));
        }
    }

    public boolean checkSequence(String[] sequence) {
        if (leftRecursive) {
            System.out.println("Grammar is left recursive.");
            return false;
        }

        for (String elem : sequence) {
            if (!grammar.getTerminals().contains(elem)) {
                System.out.println("Changing state to Error.");
                System.out.println("Error around term " + elem + " as it is not a terminal");
                this.state = State.ERROR;
            }
        }

        this.sequence = sequence;

        while (state != State.FINAL && state != State.ERROR) {
            if (state == State.NORMAL) {
                if (this.inputStack.isEmpty() && this.index == sequence.length) {
                    success();
                } else if (this.inputStack.isEmpty()) {
                    momentaryInsuccess();
                } else {
                    if (grammar.getNonTerminals().contains(inputStack.get(0))) {
                        expand();
                    } else {
                        if (index == sequence.length) {
                            momentaryInsuccess();
                        } else if (this.inputStack.get(0).equals(sequence[index])) {
                            advance();
                        } else {
                            momentaryInsuccess();
                        }
                    }
                }
            } else {
                if (state == State.BACK) {
                    List<String> terminals = new ArrayList<>(grammar.getTerminals());
                    // TODO:
                    // nu stim daca trebuie sa verificam daca terminalii din workingStack sunt in terminals
                    if (terminals.contains(workingStack.get(workingStack.size() - 1).get(0))) { // to be checked
                        back();
                    } else {
                        anotherTry();
                    }
                }
            }
        }
        return true;
    }
}
