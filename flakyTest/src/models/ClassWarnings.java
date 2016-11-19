package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class ClassWarnings {
    private List<Warning> warningList;
    private String className;

    public ClassWarnings(String className) {
        this.className = className;
        this.warningList = new ArrayList<>();
    }

    public void addWarning(Warning warning) {
        this.warningList.add(warning);
    }

    public List<Warning> getWarningList() {
        return warningList;
    }

    public String getClassName() {
        return className;
    }

    public boolean isWarningLine(int line) {
        for (Warning warning : warningList) {
            if (warning.getLine() == line)
                return true;
        }
        return false;
    }
}
