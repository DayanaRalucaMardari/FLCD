package parser;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Node {
    String name;
    int parent;
    int sibling;
    int production; // keeps the number of the production here and not the production itself
    List<Node> children;

    public Node(String name) {
        this.name = name;
        this.parent = -1;
        this.sibling = -1;
        this.production = -1;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }

//    public void printTree() {
//        printTree(this, 0);
//    }



    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<Node> it = children.iterator(); it.hasNext();) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}

public class ParserOutput {
    private Grammar grammar;
    private List<List<String>> workingStack;
    private Node root;

    private List<Node> tree;

    public ParserOutput(Grammar grammar, List<List<String>> workingStack) {
        this.grammar = grammar;
        this.workingStack = workingStack;
        this.root = null;
    }

    public List<List<String>> parsingProductionString() {
        List<List<String>> rules = new ArrayList<>();
        for (List<String> production : workingStack) {
            if (production.size() > 1) {
                // verifica daca terminalul este printre printre key-urile de la production si daca contine si production-ul acelui terminal
                // oare ce se intampla cand terminalul este terminal si non-terminal?
                if (grammar.getProductions().containsKey(List.of(production.get(0))) &&
                        grammar.getProductions().get(List.of(production.get(0))).contains(List.of(production.get(1)))) {
                    rules.add(production);
                }
            }
        }
        //System.out.println("Rules: " + rules);
        return rules;
    }

    public void parsingTable() {
        List<List<String>> rules = parsingProductionString();

        if (rules.isEmpty()) {
            root = new Node("empty");
        } else {
            int ruleIndex = 0;
            this.root = new Node(rules.get(0).get(0));
            parsingTableRec(root, rules, ruleIndex);
        }
    }

    private int parsingTableRec(Node father, List<List<String>> rules, int ruleIndex) {
        if (ruleIndex == rules.size()) {
            return rules.size();
        }

        List<String> prod = rules.get(ruleIndex).subList(1, rules.get(ruleIndex).size());
        for (String term : prod) {
            Node sibling = new Node(term);
            father.addChild(sibling);
            if (grammar.getNonTerminals().contains(term)) {
                ruleIndex = parsingTableRec(sibling, rules, ruleIndex + 1);
            }
        }
        return ruleIndex;
    }

    public void printCustomTree() {

        this.parsingTable();
        System.out.println("PRINTiNG");
        System.out.println(this.root.toString());
        //this.printTree(root, 10);
    }

    private void printTree(Node node, int depth) {
        this.parsingTable();
        // Print the node's name with appropriate indentation
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.name);

        // Recursively print the children of the node
        for (Node child : node.children) {
            printTree(child, depth + 1);
        }
    }

//    private void createParsingTree() {
//        int father = -1;
//        for (int index = 0; index < workingStack.size(); index++) {
//            if (workingStack.get(index).size() == 2) { // is of form [non-terminal, production]
//                List<String> elem = workingStack.get(index);
//                tree.add(new Node(elem.get(0)));
//                tree.get(index).production = elem.get(1);
//            } else { // is of form [terminal]
//                tree.add(new Node(workingStack.get(index).get(0)));
//            }
//        }
//
//        for (int index = 0; index < workingStack.size(); index++) {
//            if (workingStack.get(index).size() == 2) {
//                List<String> elem = workingStack.get(index);
//                tree.get(index).parent = father;
//                father = index;
//                int lenProd = grammar.getProductions().get(tuple.getElement1()).get(tuple.getElement2()).size();
//                List<Integer> vectorIndex = new ArrayList<>();
//                for (int i = 1; i <= lenProd; i++) {
//                    vectorIndex.add(index + i);
//                }
//                for (int i = 0; i < lenProd; i++) {
//                    if (tree.get(vectorIndex.get(i)).production != null) {
//                        int offset = getLenDepth(vectorIndex.get(i));
//                        for (int j = i + 1; j < lenProd; j++) {
//                            vectorIndex.set(j, vectorIndex.get(j) + offset);
//                        }
//                    }
//                }
//                for (int i = 0; i < lenProd - 1; i++) {
//                    tree.get(vectorIndex.get(i)).sibling = vectorIndex.get(i + 1);
//                }
//            } else {
//                tree.get(index).father = father;
//                father = -1;
//            }
//        }
//
//    private int getLenDepth(int index) {
//        List<String> production = grammar.getProductions().get(((Tuple) working.get(index)).getElement1())
//                .get(((Tuple) working.get(index)).getElement2());
//        int lenProd = production.size();
//        int sum = lenProd;
//        for (int i = 1; i <= lenProd; i++) {
//            if (working.get(index + i) instanceof Tuple) {
//                sum += getLenDepth(index + i);
//            }
//        }
//        return sum;
//    }

//    public void printTree() {
//        PrintCustomTree printCustomTree = new PrintCustomTree();
//        printCustomTree.printTree(root);
//    }
//
//    public void printTreeToFile(String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            PrintCustomTree printCustomTree = new PrintCustomTree();
//            printCustomTree.printTreeToFile(root, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
