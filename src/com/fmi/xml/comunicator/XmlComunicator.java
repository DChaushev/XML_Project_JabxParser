package com.fmi.xml.comunicator;

import com.fmi.xml.holder.ObjectsHolder;
import com.fmi.xml.parsable.JabxParsable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Dimitar
 */
public class XmlComunicator<T> {

    private final ObjectsHolder map = ObjectsHolder.getInstance();
    
    public XmlComunicator() {

    }

    public String getXmlContent(File file, String type) throws JAXBException {

        JabxParsable obj = (JabxParsable) loadObject(file, map.getValue(type));
        
        return obj.toString();
    }

    private T loadObject(File file, Class<T> typeParameterClass) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(typeParameterClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T object = (T) unmarshaller.unmarshal(file);

        return object;
    }

    private void writeObject(T object, File file, Class<T> typeParameterClass) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(typeParameterClass);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try (OutputStream os = new FileOutputStream(file)) {

            marshaller.marshal(object, os);
            marshaller.marshal(object, System.out);
        } catch (IOException ex) {
            Logger.getLogger(XmlComunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        JAXBContext jc = JAXBContext.newInstance(typeParameterClass);

        jc.generateSchema(new SchemaOutputResolver() {

            String suggestedFileName = "test_" + file.toString().split("\\.")[0] + ".xsd";

            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) {
                return new StreamResult(this.suggestedFileName);
            }
        });
    }

//    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {
//
//        String key = "Schedule";
//
//        XmlComunicator comunicator = new XmlComunicator();
//
//        File xml = new File("SE_Plan.xml");
//        XmlParsable schedule = (XmlParsable) comunicator.loadObject(xml, map.get(key));
//        System.out.println(schedule);
//
//        List<Semester> s = schedule.getSemesters();
//        
//        for (Semester s1 : s) {
//            List<Course> c = s1.getCourses();
//            for (Course c1 : c) {
//                if(c1.getDependencies().size() != 0){
//                    System.out.println(c1);
//                }
//            }
//        }
//        File output = new File("writtenSchedule.xml");
//        comunicator.writeObject(schedule, output, Schedule.class);
//    }
}
