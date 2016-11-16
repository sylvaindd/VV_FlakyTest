import models.OutputPrettyViewer;
import models.Warning;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class OutputPrettyViewerBuilder {
    private OutputPrettyViewer outputPrettyViewer;
    public OutputPrettyViewerBuilder BuildOutputPrettyViewer(){
        this.outputPrettyViewer = new OutputPrettyViewer();
        return this;
    }
    public void addWarning(Warning warning){

    }

}
