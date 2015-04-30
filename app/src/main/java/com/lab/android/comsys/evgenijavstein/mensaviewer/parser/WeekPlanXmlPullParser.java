package com.lab.android.comsys.evgenijavstein.mensaviewer.parser;

import android.util.Xml;

import com.lab.android.comsys.evgenijavstein.mensaviewer.model.DayPlan;
import com.lab.android.comsys.evgenijavstein.mensaviewer.model.Extra;
import com.lab.android.comsys.evgenijavstein.mensaviewer.model.Menu;
import com.lab.android.comsys.evgenijavstein.mensaviewer.model.WeekPlan;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class WeekPlanXmlPullParser  {


    InputStream inputStream;
    public WeekPlanXmlPullParser(InputStream inputStream){
        this.inputStream=new XmlSignFilterStream(inputStream);

    }


    public WeekPlan parse() throws IOException, XmlPullParserException{
       return doParse();
    }

    /**
     * internal parsing implementation
     * @return
     */
    private WeekPlan doParse() throws IOException,XmlPullParserException{
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
           // parser.nextTag();

            return readWeekPlan(parser);
        } finally {
            inputStream.close();
        }
    }

    private WeekPlan readWeekPlan(XmlPullParser parser)throws XmlPullParserException, IOException {
        WeekPlan weekPlan=new WeekPlan();
        String name="";
        String attributeName="";
        String attributeValue="";

        ArrayList<DayPlan> dayPlans=new ArrayList<DayPlan>();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {

            name = parser.getName();
            if(parser.getAttributeCount()>0) {
                 attributeName = parser.getAttributeName(0);
                 attributeValue = parser.getAttributeValue(0);
            }
            // Go through the whole document; If you detect H3 class="default-headline" accept it as
            //marker for a single day plan, try read a day plan if it fails, proceed looking for next marker
            if (equalsIfNotNull(name, "h3")&& attributeEqualsIfNotNull(attributeName, attributeValue, "class", "default-headline")){


                try {
                    dayPlans.add(readDayPlan(parser));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        weekPlan.setDayList(dayPlans);
        return weekPlan;
    }

    /**
     * Method is called after H3 class="default-headline" is detected
     * Will lool <code>depth</code> times deep to discover a headline,
     * if nothing is found, null is returned.
     * @param parser
     *
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private DayPlan readDayPlan(XmlPullParser parser) throws XmlPullParserException, IOException, IllegalStateException {


        String header=null;
        ArrayList<Menu> menues;
        ArrayList<Extra> extras;
        String tagName;
        DayPlan dayPlan=new DayPlan();
        String attributeName=null;
        String attributeValue=null;

        int event;
        //state 1: headline of the day! should be  7 times here
        while ((event=parser.next())!= XmlPullParser.END_DOCUMENT) {
            tagName = parser.getName();
            if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"div")){
                break;
            }


            if(parser.getAttributeCount()>=2){
                attributeName=parser.getAttributeName(1);
                attributeValue=parser.getAttributeValue(1);
            }


            if (equalsIfNotNull(tagName, "a")) {
                if(event!=XmlPullParser.END_TAG)
                header = nextText(parser);
                dayPlan.setHeader(header);

            }else if(equalsIfNotNull(tagName, "div")&&
                        attributeEqualsIfNotNull(attributeName, attributeValue, "class", "default-panel")){

                menues=readMenues(parser);//might fail, then whole dayplan is skipped, see exception handling one level above
                extras=readExtras(parser);
                dayPlan.setMenues(menues);
                dayPlan.setExtras(extras);
            }

        }

        return dayPlan;
    }


    private String nextText(XmlPullParser parser)throws XmlPullParserException, IOException{
        String text;
        int event=parser.next();
        if((event==XmlPullParser.TEXT)){

            text=parser.getText();
            return text.replaceAll("\\s{2,}", "");//remove all multiple occurences of whitespace/escape characters
        }else{

        }
        return null;
    }

    boolean equalsIfNotNull(String tagName, String name){
        return(tagName!=null&&tagName.equalsIgnoreCase(name));
    }

    boolean attributeEqualsIfNotNull(String attributeName, String attributeValue, String name, String value){

        return(attributeName!=null&&
                attributeValue!=null&&
                attributeName.equalsIgnoreCase(name)&&
                attributeValue.equalsIgnoreCase(value));
    }



    private void readAndStopBySpecifiedTable(XmlPullParser parser, String table, int depth)throws XmlPullParserException, IOException{
        while(parser.next()!=XmlPullParser.END_DOCUMENT&&depth>0) {
            if (equalsIfNotNull(parser.getName(), "table") &&
                    attributeEqualsIfNotNull(parser.getAttributeName(0), parser.getAttributeValue(0), "class", table))
                return;
            depth--;
        }
    }

 private ArrayList<Menu> readMenues(XmlPullParser parser )throws XmlPullParserException, IOException{
     ArrayList<Menu> menues=new ArrayList<Menu>();

     //state 2: default-panel
     readAndStopBySpecifiedTable(parser, "menues",2);
            int event;
          //state 3: menues table
            while((event=parser.next())!=XmlPullParser.END_DOCUMENT){
                String tagName=parser.getName();
                if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"table")){

                    break;
                }//get single menues untill you detect end of table
                //if we are here we are inside the table


                if(equalsIfNotNull(parser.getName(),"tr")){//single row=single menu
                    menues.add(readSingleMenu(parser));
                }




            }

     return menues;
 }

 private  Menu readSingleMenu(XmlPullParser parser)throws XmlPullParserException, IOException{
     Menu menu=new Menu();
    String tagName;
     int event;
     while( (event=parser.next())!=XmlPullParser.END_DOCUMENT){
         tagName=parser.getName();

         if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"tr")){
             break;
         }


         if(parser.getAttributeCount()>0)
         if(equalsIfNotNull(parser.getName(),"td")&&
            attributeEqualsIfNotNull(parser.getAttributeName(0),parser.getAttributeValue(0),"class","category")){

           menu.setCategory(nextText(parser));
         }else if(equalsIfNotNull(parser.getName(),"td")&&
                 attributeEqualsIfNotNull(parser.getAttributeName(0),parser.getAttributeValue(0),"class","menue")){
          menu.setMenu(nextText(parser));
         }else if(equalsIfNotNull(parser.getName(),"td")&&
                 attributeEqualsIfNotNull(parser.getAttributeName(0),parser.getAttributeValue(0),"class","price")){
             menu.setPrice(nextText(parser));
         }


     }
    return menu;
 }

    private Extra readSingleExtra(XmlPullParser parser)throws XmlPullParserException, IOException {

        Extra extra = new Extra();
        String tagName;
        int event;
        while ((event=parser.next()) != XmlPullParser.END_DOCUMENT) {

            tagName=parser.getName();

            if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"tr")){
                break;
            }
            if(parser.getAttributeCount()>0)
            if (equalsIfNotNull(parser.getName(),"td")&&
                    attributeEqualsIfNotNull(parser.getAttributeName(0),parser.getAttributeValue(0),"class","category")) {

                extra.setCategory(nextText(parser));
            } else if (equalsIfNotNull(parser.getName(),"td")&&
                    attributeEqualsIfNotNull(parser.getAttributeName(0),parser.getAttributeValue(0),"class","extra")) {
                extra.setExtra(nextText(parser));

            }

        }
        return extra;
    }

 private ArrayList<Extra> readExtras(XmlPullParser parser) throws XmlPullParserException, IOException{
     ArrayList<Extra> extras=new ArrayList<Extra>();
     String tagName;

     readAndStopBySpecifiedTable(parser, "extras",2);
    //state 4: extras table
     int event;
     while((event=parser.next())!=XmlPullParser.END_DOCUMENT){
         tagName=parser.getName();
         //if we are here we are inside the table
         if(event==XmlPullParser.END_TAG&&equalsIfNotNull(tagName,"table")){

             break;
         }//get single menues untill you detect end of table

         if(equalsIfNotNull(tagName,"tr")){//single row=single menu
             extras.add(readSingleExtra(parser));
         }

     }
     return extras;
 }



    /**
     * Helper method during development
     * @return
     */
    private String getInputStreamString(){
        this.inputStream=inputStream;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }





}
