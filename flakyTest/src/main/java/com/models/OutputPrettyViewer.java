package com.models;

import com.resources.HTMLResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
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
					Warning actual_Warning = classWarnings.GetWarningFromLine(nbLine);
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

	public void GenerateHTMLViewerTemplate() {

		try {
			File main_templateFile = new File("input.txt");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(main_templateFile);
			doc.getDocumentElement().normalize();

			File class_templateFile = new File("input.txt");
			Document class_template = dBuilder.parse(class_templateFile);

			File warnining_templateFile = new File("input.txt");
			Document warnining_template = dBuilder.parse(warnining_templateFile);

			Element warning_template = doc.getElementById("warning_template");

			Document generatedDoc = dBuilder.parse(main_templateFile);
			Element listClasses = generatedDoc.getElementById("classList_template");

			// ajouter une classe
			Document tmpClass = copyDOC(class_template);
			tmpClass.getElementsByTagName("i").item(0);
			// listClasses.appendChild(tmpClass.getParentNode());

			// ajout d'une classe
			// Node class_Warnings = class_template.cloneNode(true);
			// class_Warnings.getChildNodes().item(0).getChildNodes().item(0).setNodeValue("NOM
			// DE LA CLASSE");
			// listClasses.appendChild(class_Warnings);

			// ajout d'un warning
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document copyDOC(Document doc) {
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer tx = tfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			DOMResult result = new DOMResult();
			tx.transform(source, result);
			return (Document) result.getNode();
		}
		catch (Exception e) {
			return null;
		}
	}

	public List<ClassWarnings> getClassWarningsList() {
		return classWarningsList;
	}
}
