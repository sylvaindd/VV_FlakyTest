package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class ClassWarnings {
    private String absolutePath;
    private List<Warning> warningList;
    private String className;

    public ClassWarnings(String className, String absolutePath) {
        this.className = className;
        this.absolutePath = absolutePath;
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

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
