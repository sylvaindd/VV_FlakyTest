package com.models;

import com.resources.HTMLResources;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
		HTMLResources.i = 0;
	}

	public void addClassWarning(ClassWarnings classWarnings) {
		classWarningsList.add(classWarnings);
	}

	private String classNameToPath(String className) {
		return path + className.replace(".", "/") + ".java";
	}

	public String GenerateHTMLView() {
		String html = "";
		for (ClassWarnings classWarnings : classWarningsList) {
			html += HTMLResources.getNewClassStart() + classWarnings.getClassName() + HTMLResources.getNewClassEnd();
			// get le code ligne par ligne et ajouter les warnings au début des
			// lignes correspondante
			try (Stream<String> lines = Files.lines(Paths.get(classWarnings.getAbsolutePath()), StandardCharsets.UTF_8)) {
				int index = 0;
				int nbLine = 1;
				html += HTMLResources.getNewCodeStart();
				for (String line : (Iterable<String>) lines::iterator) {
					Warning actual_Warning = classWarnings.getWarningFromLine(nbLine);
					if (actual_Warning != null) {
						html += actual_Warning.GenerateHTML(actual_Warning.getCriticalityriticality()) + line + "\n";
						index++;
					}
					else {
						html += line + "\n";
					}
					nbLine++;
				}
				html += HTMLResources.getNewCodeEnd();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			HTMLResources.i++;
		}
		String content = null;
		try {
			content = new Scanner(new File(new File("").getAbsolutePath() + "/src/main/java/com/res/output_template_main.html")).useDelimiter("\\Z").next();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return content.replace("!!!CODE!!!", html);
	}

	public List<ClassWarnings> getClassWarningsList() {
		return classWarningsList;
	}
}
