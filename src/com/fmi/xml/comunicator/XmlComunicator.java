package com.fmi.xml.comunicator;

import com.fmi.xml.holder.ObjectsHolder;
import com.fmi.xml.parsable.JaxbParsable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Dimitar
 */
public class XmlComunicator<T> {

    private final ObjectsHolder map = ObjectsHolder.getInstance();
    private JAXBContext context;
    
    public XmlComunicator() {

    }

    public String getXmlContent(File file, String type) throws JAXBException {

        JaxbParsable obj = (JaxbParsable) loadObject(file, map.getValue(type));
        
        return obj.toString();
    }
    
    public boolean validate(JaxbParsable obj, File xsd){
        
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsd);
            
            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.marshal(obj, new org.xml.sax.helpers.DefaultHandler());
            
            return true;
            
        } catch (SAXException | JAXBException ex) {
            return false;
        }
    }

    public T loadObject(File file, String type) throws JAXBException {
        return (T) loadObject(file, map.getValue(type));
    }
    
    public T loadObject(File file, Class<T> typeParameterClass) throws JAXBException {

        context = JAXBContext.newInstance(typeParameterClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T object = (T) unmarshaller.unmarshal(file);

        return object;
    }

    public void writeObject(T object, File file, Class<T> typeParameterClass) throws JAXBException, IOException {

        context = JAXBContext.newInstance(typeParameterClass);
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
}
