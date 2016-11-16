package models;

import resources.HTMLResources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class OutputPrettyViewer {
    private List<ClassWarnings> classWarningsList;
    private String path;

    public OutputPrettyViewer(String pathFile){
        this.classWarningsList = new ArrayList<>();
        this.path=pathFile;
    }

    public void addClassWarning (ClassWarnings classWarnings){
        classWarningsList.add(classWarnings);
    }

    private static String classNameToPath(String className){
        return  className.replaceAll(".","/");
    }

    public String GenerateHTMLView(){
        String html=HTMLResources.BODY_START;
        for (ClassWarnings classWarnings : classWarningsList) {
            html+= HTMLResources.NEW_CLASS_START+classWarnings.getClassName()+HTMLResources.NEW_CLASS_END;
            //get le code ligne par ligne et ajouter les warnings  au début des lignes correspondante
            try (Stream<String> lines = Files.lines (Paths.get(OutputPrettyViewer.classNameToPath(classWarnings.getClassName())), StandardCharsets.UTF_8))
            {
                int index =0;
                html+=HTMLResources.NEW_CODE_START;
                for (String line : (Iterable<String>) lines::iterator)
                {
                    if(classWarnings.isWarningLine(index)){
                        html+=classWarnings.getWarningList().get(index).GenerateHTML()+line;
                    }else {
                        html+=line;
                    }
                    index++;
                }
                html+=HTMLResources.NEW_CODE_END;
            }catch (IOException e) {
                e.printStackTrace();
            }
            for (Warning warning : classWarnings.getWarningList()) {
                html+=warning.GenerateHTML();
            }
        }
        html+=HTMLResources.BODY_END;
        return html;
    }
}
