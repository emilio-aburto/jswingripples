package org.incha.core.telemetry;

/**
 * Created by eaburto on 25-01-17.
 */
public class Phase {
    private final String text;

    public static final Phase CL;
    public static final Phase IA;
    public static final Phase CP;

    static {
        CL = new Phase("Concept Location");
        IA = new Phase("Impact Analysis");
        CP = new Phase("Change Propagation");
    }

    private Phase(String str){
        text = str;
    }

    @Override
    public String toString() {
        return text;
    }
}
