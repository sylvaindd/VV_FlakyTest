package com.utils;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class HTMLResources {
	public static int i = 0;

	public static String getNewClassStart() {
		return "\t<a data-toggle=\"collapse\" data-target=\"#collapse" + i + "\" href=\"#collapse" + i + "\">\n" + "    \t<h2>";
	}

	public static String getWarningStart() {
		return "<i class=\"fa fa-exclamation-triangle\" data-placement=\"right\" data-toggle=\"tooltip\" data-html=\"true\" title=\"";
	}

	public static String getWarningEnd() {
		return "\" aria-hidden=\"true\"></i>";
	}

	public static String getNewCodeStart() {
		return "<div id=\"collapse" + i + "\" class=\"panel-collapse collapse in\">\n" + "    \t<div class=\"panel-body\">\n" + "            <pre class=\"language-java prettyprint linenums\">\n"
				+ "<code>";
	}

	public static String getNewCodeEnd() {
		return "                </code>\n" + "            </pre>\n" + "\t    </div>\t\n" + "    </div>";
	}

	public static String getNewClassEnd() {
		return "</h2>\n" + "    </a>";
	}
}
