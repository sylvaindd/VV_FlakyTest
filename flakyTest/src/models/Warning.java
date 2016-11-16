package models;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class Warning {
    private Criticality criticalityriticality;
    private String description;
    private String title;
    private int line;
    public enum Criticality {
        LOW, MEDIUM, HIGH
    }
}
