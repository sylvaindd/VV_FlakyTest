package models;

/**
 * Created by Sylvain on 16/11/2016.
 */
public enum Params {
    DATE("Date"), NETWORK("Network"), FILE("File"), ANNOTATIONS("Annotations");

    private String name;

    Params(String name) {
        this.name = name;
    }
}