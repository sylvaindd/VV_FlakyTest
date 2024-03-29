package com.models;

/**
 * Enum with the different warnings available
 */
public enum Params {
    DATE("Date", "Les instances de Date dans les cas de tests ne sont pas conseillés"),
    NETWORK("Network", "Les appels web services ne sont pas conseillés dans les cas de tests"),
    FILE("File", "Les opérations sur les fichiers sont deconseillées dans les cas de tests"),
    ANNOTATIONS("Annotations", "L'annotation '@Test' pourrait être manquante"),
    OTHER("Other", "Erreur detectée mais non repertoriée dans les Params");

    private String name;
    private String description;

    Params(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Params getParamsForClass(String className) {
        switch (className) {
            case "Date":
                return DATE;
            case "File":
                return FILE;
            case "HttpClient":
            case "HttpURLConnection":
            case "HttpResponse":
            case "ClientResponse":
            case "HttpRequest":
            case "WebServiceClient":
                return NETWORK;
            default:
                return OTHER;
        }
    }
}
