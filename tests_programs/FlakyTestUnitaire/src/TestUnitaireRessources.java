package src;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.ws.WebServiceClient;
import java.io.File;
import java.util.Date;

/**
 * Created by mleduc on 26/09/16.
 */
public class TestUnitairesRessources {
    private File file1 = new File();
    private Date date1 = new Date();

    public void testNoAnnotation() {
        assert true;
    }

    public void testNoAnnotation2() {
        assert true;
    }

    @Test
    public void testAnnotation() {
        assert true;
    }

    @Test
    public void testDate() {
        Date date = new Date();
        getDate();
        Date date2 = date1;
    }

    @Test
    public void testFile() {
        File file = new File();
        getFile();
        File file2 = file1;
    }

    public File getFile() {
        return new File();
    }

    public Date getDate() {
        return new Date();
    }

    @Test
    public void testWebserver() {
        HttpURLConnection httpURLConnection = new HttpURLConnection();
        HttpClient ttpClient = new HttpClient();
        HttpResponse httpResponse = new HttpResponse();
        HttpRequest httpRequest = new HttpRequest();
        WebServiceClient webServiceClient = new WebServiceClient();
    }
}
