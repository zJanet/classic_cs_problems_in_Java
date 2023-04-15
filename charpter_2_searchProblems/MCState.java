package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import classic_cs_problems_in_Java.charpter_2_searchProblems.GenericSearch.Node;

public class MCState {
    private static final int MAX_NUM = 3;
    private final int wm; // west bank missionaries
    private final int wc; // west bank cannibals
    private final int em; // east bank missionaries
    private final int ec; // east bank cannibals
    private final boolean boat; // is boat on west bank?

    // The class MCState initializes itself on the number of missionaries and cannibals on the west bank as well as the location of the boat
    public MCState (int missionaries, int cannibals, boolean boat) {
        wm = missionaries;
        wc = cannibals;
        em = MAX_NUM - wm;
        ec = MAX_NUM - wc;
        this.boat = boat;
    }

    // It also knows how to pretty-print itself
    @Override
    public String toString() {
        return String.format(
        "On the west bank there are %d missionaries and %d cannibals. %n"
        + "On the east bank there are %d missionaries and %d cannibals. %n"
        + "The boat is on the %s bank.", wm, wc, em, ec, boat ? "west" : "east");
    }

    public boolean goalTest() {
        return isLegal() && em == MAX_NUM && ec == MAX_NUM;
    }

    public boolean isLegal() {
        if (wm < wc && wm > 0) {
            return false;
        }
        if (em < ec && em > 0) {
            return false;
        }
        return true;
    }

    public static void displaySolution(List<MCState> path) {
        if (path.size() == 0) { // sanity check
            return;
        }
        MCState oldState = path.get(0);
        System.out.println(oldState);
        for (MCState currentState : path.subList(1, path.size())) {
            if (currentState.boat) {
                System.out.printf("%d missionaries and %d cannibals moved from the east bank to the west bank.%n", oldState.em - currentState.em, oldState.ec - currentState.ec);
            } else {
                System.out.printf("%d missionaries and %d cannibals moved from the west bank to the east bank.%n", oldState.wm - currentState.wm, oldState.wc - currentState.wc);
            }
            System.out.println(currentState);
            oldState = currentState;
        }
    }
    public static List<MCState> successors(MCState mcs) {
        List<MCState> sucs = new ArrayList<>();
        if (mcs.boat) { // boat on west bank
            if (mcs.wm > 1) {
                sucs.add(new MCState(mcs.wm - 2, mcs.wc, !mcs.boat));
            }
            if (mcs.wm > 0) {
                sucs.add(new MCState(mcs.wm - 1, mcs.wc, !mcs.boat));
            }
            if (mcs.wc > 1) {
                sucs.add(new MCState(mcs.wm, mcs.wc-2, !mcs.boat));
            }
            if (mcs.wc > 0) {
                sucs.add(new MCState(mcs.wm, mcs.wc-1, !mcs.boat));
            }
        } else { // boat on east bank
            if (mcs.em > 1) {
                sucs.add(new MCState(mcs.wm + 2, mcs.ec, !mcs.boat));
            }
            if (mcs.em > 0) {
                sucs.add(new MCState(mcs.wm + 1, mcs.ec, !mcs.boat));
            }
            if (mcs.ec > 1) {
                sucs.add(new MCState(mcs.wm, mcs.wc + 2, !mcs.boat));
            }
            if (mcs.ec > 0) {
                sucs.add(new MCState(mcs.wm, mcs.wc + 1, !mcs.boat));
            }
            if (mcs.ec > 0 && mcs.em > 0) {
                sucs.add(new MCState(mcs.wm + 1, mcs.wc + 1, !mcs.boat));
            }
        }
        sucs.removeIf(Predicate.not(MCState::isLegal));
        return sucs;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ec;
        result = prime * result + em;
        result = prime * result + wc;
        result = prime * result + wm;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MCState other = (MCState) obj;
        if (ec != other.ec)
            return false;
        if (em != other.em)
            return false;
        if (wc != other.wc)
            return false;
        if (wm != other.wm)
            return false;
        return true;
    }
    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + (boat ? 1231 : 1237);
    //     result = prime * result + ec;
    //     result = prime * result + em;
    //     result = prime * result + wc;
    //     result = prime * result + wm;
    //     return result;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     MCState other = (MCState) obj;
    //     if (boat != other.boat)
    //         return false;
    //     if (ec != other.ec)
    //         return false;
    //     if (em != other.em)
    //         return false;
    //     if (wc != other.wc)
    //         return false;
    //     if (wm != other.wm)
    //         return false;
    //     return true;
    // }
    public static void main(String[] args) {
        MCState start  = new MCState(MAX_NUM, MAX_NUM, true);
        Node<MCState> solution = GenericSearch.dfs(start, MCState::goalTest, MCState::successors);
        if (solution == null) {
            System.out.println("No solution found");
        } else {
            List<MCState> path = GenericSearch.nodeToPath(solution);
            displaySolution(path);
        }
    }
}