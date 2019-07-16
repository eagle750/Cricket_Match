package Cricket;

public enum MatchType {

    T10(10), T20(20), ODI(50);
    private final int overs;


    MatchType(int overs) {
        this.overs = overs;
    }

    public int getOvers() {
        return overs;
    }
}
