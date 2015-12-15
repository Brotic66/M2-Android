/**
 * @author Brice VICO
 * @date 06/06/2015
 * @version 1.0.1
 */

package brotic.findmyfriends.Service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

    private static String URL = "http://149.202.51.217/Server/web/app_dev.php/";
    private LinkedHashMap<String, String> paramsGet;
    private byte paramPost[];
    private String controleur;
    private HttpURLConnection urlConnection;
    private InputStream in;

    public BroticCommunication(String controleur) {
        this.controleur = controleur;
        this.paramsGet = new LinkedHashMap<>();
        this.paramPost = null;
        this.urlConnection = null;
        this.in = null;
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
    public void run() throws ParserConfigurationException, SAXException, IOException {
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

        int status = urlConnection.getResponseCode();

        Log.d("=====MYLOGGER=======", String.valueOf(status));

        this.in = new BufferedInputStream(this.urlConnection.getInputStream());
    }

    public JSONObject getJson()
    {
        JSONObject toRtn = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.in, "UTF-8"));
            StringBuilder strBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = reader.readLine()) != null)
                strBuilder.append(inputStr);

            toRtn = new JSONObject(strBuilder.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return toRtn;
    }

    public void close() {
        if (this.urlConnection != null) {
            this.urlConnection.disconnect();
        }

        try {
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
