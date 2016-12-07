package models;

import resources.HTMLResources;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class Warning {

	public enum Criticality {
		LOW, MEDIUM, HIGH
	}

	private Criticality	criticalityriticality;
	private int			line;
	private Params		params;

	public Warning(Criticality criticalityriticality, Params params, int line) {
		this.criticalityriticality = criticalityriticality;
		this.params = params;
		this.line = line;
	}

	public String GenerateHTML() {
		return HTMLResources.getWarningStart() + this.getTitle() + "<br />" + this.getDescription() + HTMLResources.getWarningEnd();
	}

	public Criticality getCriticalityriticality() {
		return criticalityriticality;
	}

	public void setCriticalityriticality(Criticality criticalityriticality) {
		this.criticalityriticality = criticalityriticality;
	}

	public String getDescription() {
		return params.getDescription();
	}

	public String getTitle() {
		return params.getName();
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
}
