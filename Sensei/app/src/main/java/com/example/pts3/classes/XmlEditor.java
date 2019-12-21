package com.example.pts3.classes;

import android.app.Activity;
import android.widget.LinearLayout;

import com.example.pts3.activity.Game_Activity;
import com.example.pts3.interfaces.IPiece;

import org.jdom2.JDOMException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlEditor {

    private final LinearLayout aff;
    private String filename ;
    private Activity activity;

    public XmlEditor(String filename, Activity activity, LinearLayout aff)  {
        super();
        this.filename = filename ;
        this.activity = activity;
        this.aff = aff;
    }

    public List<Plateau> read() throws JDOMException, IOException {
        List<Plateau> listPlateau = new ArrayList<>();

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = activity.getAssets().open("plateaux.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            listPlateau = processParsing(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listPlateau;
    }

    private List<Plateau> processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        List<Plateau> listPlateau = new ArrayList<>();

        int eventType = parser.getEventType();

        int ligne = 0;
        int colonne = 0;

        while(eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if("plateau".equals(eltName)) {
                        int sizeX = Integer.parseInt(parser.getAttributeValue(1));
                        int sizeY = Integer.parseInt(parser.getAttributeValue(2));

                        listPlateau.add(new Plateau(Integer.parseInt(parser.getAttributeValue(1)), Integer.parseInt(parser.getAttributeValue(2)), activity, aff));
                    }
                    else if("val".equals(eltName)) {
                        IPiece piece = null;
                        String val = parser.nextText();
                        if("r".equals(val)) {
                            piece = new TuileRouge(activity);
                        }
                        else if("R".equals(val)) {
                            piece = new TuileRouge(activity);
                            ((TuileRouge) piece).generatePerso();
                        }
                        else if("o".equals(val)) {
                            piece = new Obstacle();
                        }
                        else if("v".equals(val)) {
                            piece = new TuileVert(activity);
                        }
                        else if("V".equals(val)) {
                            piece = new TuileVert(activity);
                            ((TuileVert) piece).generatePerso();
                        }

                        listPlateau.get(listPlateau.size() - 1).setCase(colonne, ligne, piece);

                        colonne += 1;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    eltName = parser.getName();

                    if("ligne".equals(eltName)) {
                        ligne += 1;
                        colonne = 0;
                    }
                    else if("plateau".equals(eltName)) {
                        colonne = 0;
                        ligne = 0;
                    }
                    break;
            }
            eventType = parser.next();
        }

        return listPlateau;
    }
}
