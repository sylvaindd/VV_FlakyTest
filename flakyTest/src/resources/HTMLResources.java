package resources;

/**
 * Created by 15004515 on 16/11/2016.
 */
public class HTMLResources {
    public static final String BODY_START = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<title>output_template</title>\n" +
            "\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css\" integrity=\"sha384-2hfp1SzUoho7/TsGGGDaFdsuuDL0LX2hnUp6VkX3CUQ2K4K+xjboZdsXyp4oUHZj\" crossorigin=\"anonymous\">\n" +
            "\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n" +
            "\n" +
            "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.7/js/tether.min.js\"></script>\n" +
            "\n" +
            "<!-- Latest compiled and minified JavaScript -->\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/js/bootstrap.min.js\" integrity=\"sha384-VjEeINv9OSwtWFLAtmc4JCtEJXXBub00gtSnszmspDLCtC0I4z4nqz7rEFbIZLLU\" crossorigin=\"anonymous\"></script>\n" +
            "\n" +
            "\n" +
            "<script src=\"https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js?skin=sunburst\"></script>\n" +
            "\n" +
            "<script src=\"https://use.fontawesome.com/e3a541e273.js\"></script>\n" +
            "<script>\n" +
            "\t$(function () {\n" +
            "\t  $('[data-toggle=\"tooltip\"]').tooltip()\n" +
            "\t});\n" +
            "</script>\n" +
            "<style>\n" +
            "\t.fa-exclamation-triangle {\n" +
            "\t\tcolor: yellow;\n" +
            "\t}\n" +
            "    .prettyprint ol.linenums > li { list-style-type: decimal; }\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<div>";
    public static final String BODY_END = "</div>\n" +
            "</body>\n" +
            "</html>";
    public static final String NEW_CLASS_START = "\t<a data-toggle=\"collapse\" data-target=\"#collapseOne\" href=\"#collapseOne\">\n" +
            "    \t<h2>";
    public static final String NEW_CLASS_END = "</h2>\n" +
            "    </a>";
    public static final String NEW_CODE_END = "                </code>\n" +
            "            </pre>\n" +
            "\t    </div>\t\n" +
            "    </div>";
    public static final String NEW_CODE_START = "<div id=\"collapseOne\" class=\"panel-collapse collapse in\">\n" +
            "    \t<div class=\"panel-body\">\n" +
            "            <pre class=\"language-java prettyprint linenums\">\n" +
            "                <code>";
    public static final String WARNING_START = "<i class=\"fa fa-exclamation-triangle\" data-toggle=\"tooltip\" data-html=\"true\" title=\"";
    public static final String WARNING_END = "\" aria-hidden=\"true\"></i>";
}
