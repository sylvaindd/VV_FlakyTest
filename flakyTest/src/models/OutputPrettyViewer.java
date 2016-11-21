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
	private List<ClassWarnings>	classWarningsList;
	private String				path;

	public OutputPrettyViewer(String pathFile) {
		this.classWarningsList = new ArrayList<>();
		this.path = pathFile;
	}

	public void addClassWarning(ClassWarnings classWarnings) {
		classWarningsList.add(classWarnings);
	}

	private String classNameToPath(String className) {
		return path + className.replace(".", "/") + ".java";
	}

	public String GenerateHTMLView() {
		String html = HTMLResources.BODY_START;
		for (ClassWarnings classWarnings : classWarningsList) {
			html += HTMLResources.NEW_CLASS_START + classWarnings.getClassName() + HTMLResources.NEW_CLASS_END;
			// get le code ligne par ligne et ajouter les warnings au début des
			// lignes correspondante
			try (Stream<String> lines = Files.lines(Paths.get(classNameToPath(classWarnings.getClassName())), StandardCharsets.UTF_8)) {
				int index = 0;
				int nbLine = 1;
				html += HTMLResources.NEW_CODE_START;
				for (String line : (Iterable<String>) lines::iterator) {
					if (classWarnings.isWarningLine(nbLine)) {
						html += classWarnings.getWarningList().get(index).GenerateHTML() + line + "\n";
						index++;
					}
					else {
						html += line + "\n";
					}
					nbLine++;
				}
				html += HTMLResources.NEW_CODE_END;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			for (Warning warning : classWarnings.getWarningList()) {
				html += warning.GenerateHTML();
			}
		}
		html += HTMLResources.BODY_END;
		return html;
	}
}
