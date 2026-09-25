public final class DomainRules {
    private DomainRules() {

    }
    public static void requireDuration(int hours) {
        // TODO D1: reject outside 1..24 with IllegalArgumentException.
        if (hours < 1 || hours > 24){
            throw new IllegalArgumentException("Inputted hours is out of bounds");
        }
        throw new UnsupportedOperationException("D1 duration validation");
    }
    public static void requireIdentifier(String id) {
        // TODO D1: non-null [A-Z][A-Z0-9_-]{0,15}.
        throw new UnsupportedOperationException("D1 identifier validation");
    }
}
