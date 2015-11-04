/**
 * @author Brice VICO
 * @date 06/06/2015
 * @version 1.0.1
 */

package brotic.findmyfriends.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BroticCommunication {

    private static String URL = "http://149.x.x.x/SD_Serveur/web/app.php/";
    private LinkedHashMap<String, String> paramsGet;
    private byte paramPost[];
    private String controleur;
    private HttpURLConnection urlConnection;

    public BroticCommunication(String controleur) {
        this.controleur = controleur;
        this.paramsGet = new LinkedHashMap<>();
        this.paramPost = null;
        this.urlConnection = null;
    }

    public void addParamGet(String nom, String valeur) {
        if (!this.paramsGet.containsKey(nom))
            this.paramsGet.put(nom, valeur);
    }

    public void removeParamGet(String nom) {
        if (this.paramsGet.containsKey(nom))
            this.paramsGet.remove(nom);
    }

    public HashMap<String, String> getParamsGet() {
        return this.paramsGet;
    }

    public void setParamPost(byte[] paramPost) {
        this.paramPost = paramPost;
    }

    public byte[] getParamPost() {
        return this.paramPost;
    }

    public void setControleur(String controleur) {
        this.controleur = controleur;
    }

    public String getControleur() {
        return this.controleur;
    }

    /**
     * @return Element
     *
     * Permet d'envoyer une requete http avec des paramètre GET et POST (facultatif) et
     * de récupérer le résultat sous forme d'un élément (DOMElement XML du W3C)
     */
    public Element run() throws ParserConfigurationException, SAXException, IOException {
        String query = "";

        for(Map.Entry<String, String> entry : this.paramsGet.entrySet()) {
            String cle = entry.getKey();
            String valeur = entry.getValue();

            query += URLEncoder.encode(valeur, "UTF-8").replace("+", "%20") +"/";
            System.out.println(valeur);
        }

        java.net.URL url = new URL(URL + this.controleur + "/" + query);
        this.urlConnection = (HttpURLConnection) url.openConnection();

        if (this.paramPost != null)
            this.urlConnection.setConnectTimeout(30000);
        else
            this.urlConnection.setConnectTimeout(10000);

        if (this.paramPost != null) {
            this.urlConnection.setDoOutput(true);
            this.urlConnection.connect();
            OutputStream out = urlConnection.getOutputStream();
            out.write(this.paramPost);
            out.close();
        }

        InputStream in = null;

        in = new BufferedInputStream(this.urlConnection.getInputStream());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(in);

        in.close();
        this.close();

        return document.getDocumentElement();
    }

    public void close() {
        if (this.urlConnection != null) {
            this.urlConnection.disconnect();
        }
    }
}
