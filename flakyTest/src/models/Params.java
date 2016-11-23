package models;

/**
 * Created by Sylvain on 16/11/2016.
 */
public enum Params {
    DATE("Date", "Les instances de Date dans les cas de tests ne sont pas conseillés"),
    NETWORK("Network", "Les appels web services ne sont pas conseillés dans les cas de tests"),
    FILE("File", "Les opérations sur les fichiers sont deconseillées dans les cas de tests"),
    ANNOTATIONS("Annotations", "L'anotation '@Test' pourrait être manquante");

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
}
