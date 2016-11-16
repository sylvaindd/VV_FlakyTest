package models;

import resources.HTMLResources;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class Warning {
    public Criticality getCriticalityriticality() {
        return criticalityriticality;
    }

    public void setCriticalityriticality(Criticality criticalityriticality) {
        this.criticalityriticality = criticalityriticality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    private Criticality criticalityriticality;
    private String description;
    private String title;
    private int line;
    private Params params;

    public Warning(Criticality criticalityriticality, String description, String title, int line) {
        this.criticalityriticality = criticalityriticality;
        this.description = description;
        this.title = title;
        this.line = line;
    }

    public enum Criticality {
        LOW, MEDIUM, HIGH
    }
    public String GenerateHTML(){
        return HTMLResources.WARNING_START+this.title+HTMLResources.WARNING_END;
    }
}
