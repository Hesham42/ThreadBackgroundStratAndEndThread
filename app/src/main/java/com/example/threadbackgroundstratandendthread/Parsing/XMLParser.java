package com.example.threadbackgroundstratandendthread.Parsing;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.threadbackgroundstratandendthread.MainActivity;
import com.example.threadbackgroundstratandendthread.R;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.example.threadbackgroundstratandendthread.Serv.App.CHANEL_ex_ID;

public  class XMLParser  {
    private static final String TAG = "Guinness";
    // All static variables
    // XML node keys
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_COST = "cost";
    static final String KEY_DESC = "description";
    // constructor
    public XMLParser() {
    }



    public static Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

    /** Getting node value
     * @param elem element
     */
    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public  String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public ArrayList<HashMap<String, String>> ParsingData() {


        GetNoticeDataService service =
                RetrofitInstance.getRetrofitInstance()
                        .create(GetNoticeDataService.class);
        try {
            Response<ResponseBody>  response = service.GetData2().execute();
            if (response.isSuccessful()) {
                Log.wtf(TAG, response.toString());
                String responseString = String.valueOf(response.body().string());
                Log.wtf(TAG, responseString);
                ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
                String xml = responseString; // getting XML
                Document doc = (Document) getDomElement(xml); // getting DOM element

                NodeList nl = doc.getElementsByTagName(KEY_ITEM);
                // looping through all item nodes <item>
                for (int i = 0; i < nl.getLength(); i++) {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    // adding each child node to HashMap key => value
                    map.put(KEY_ID, getValue(e, KEY_ID));
                    map.put(KEY_NAME, getValue(e, KEY_NAME));
                    map.put(KEY_COST, "Rs." + getValue(e, KEY_COST));
                    map.put(KEY_DESC, getValue(e, KEY_DESC));
                    // adding HashList to ArrayList
                    menuItems.add(map);
                }
                return menuItems;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}