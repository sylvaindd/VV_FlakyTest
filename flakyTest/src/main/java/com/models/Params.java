package com.models;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.ws.WebServiceClient;
import java.io.File;
import java.util.Date;

/**
 * Enum with the different warnings available
 */
public enum Params {
    DATE("Date", "Les instances de Date dans les cas de tests ne sont pas conseillés"), NETWORK("Network", "Les appels web services ne sont pas conseillés dans les cas de tests"), FILE("File",
            "Les opérations sur les fichiers sont deconseillées dans les cas de tests"), ANNOTATIONS("Annotations", "L'annotation '@Test' pourrait être manquante");

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

    public static Params getParamsForClass(Class cl) {
        if (cl.equals(Date.class))
            return DATE;
        if (cl.equals(File.class))
            return FILE;
        if (cl.equals(HttpClient.class) || cl.equals(HttpURLConnection.class)
                || cl.equals(HttpResponse.class)
                || cl.equals(HttpRequest.class)
                || cl.equals(WebServiceClient.class))
            return NETWORK;
        return null;
    }
}
