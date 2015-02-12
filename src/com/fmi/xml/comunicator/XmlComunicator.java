package com.fmi.xml.comunicator;

import com.fmi.xml.holder.ObjectsHolder;
import com.fmi.xml.parsable.JaxbParsable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBResult;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 * With this class you can read XML files and parse them, or write objects to
 * XML files.
 *
 *
 * @author Dimitar
 */
public class XmlComunicator<T> {

    private final ObjectsHolder map = ObjectsHolder.getInstance();
    private JAXBContext context;

    public XmlComunicator() {

    }

    /**
     * This method gives you the toString method of parsed XML file it receives
     * as an argument.
     *
     * @param file File to be parsed.
     * @param type The class you are parsing to.
     * @return Parsed object's toString.
     * @throws JAXBException if the XML cannot be parsed.
     */
    public String getXmlContent(File file, String type) throws JAXBException {

        JaxbParsable obj = (JaxbParsable) loadObject(file, map.getValue(type));

        return obj.toString();
    }

    /**
     * This method validates parsed XML object with given XSD schema file, it
     * receives as an argument.
     *
     * @param obj Object to be validated
     * @param xsd Schema for validation
     * @return true if valid / false if not
     */
    public boolean validate(JaxbParsable obj, File xsd) {

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

    /**
     * This method is Overloading the loadObject(File file, Class
     * typeParameterClass) method. It takes String instead of Class, which
     * should be contained in the ObjectsHolder singleton class.
     *
     * @param file XML file to be parsed.
     * @param type Object type as String.
     * @return the parsed object.
     * @throws JAXBException if the XML cannot be parsed.
     * @throws NoSuchElementException if there's no such class in the
     * ObjectHolder.
     */
    public T loadObject(File file, String type) throws JAXBException {
        if (map.containsKey(type)) {
            return (T) loadObject(file, map.getValue(type));
        } else {
            throw new NoSuchElementException("There is no such class");
        }
    }

    /**
     * Accepts XML file and Class type as arguments and returns the parsed
     * object's toString method. If it is not the right object type passed, you
     * will get exception.
     *
     * @param file XML file to be parsed
     * @param typeParameterClass object to be parsed to
     * @return the parsed object.
     * @throws JAXBException if the XML cannot be parsed.
     */
    public T loadObject(File file, Class<T> typeParameterClass) throws JAXBException {

        context = JAXBContext.newInstance(typeParameterClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T object = (T) unmarshaller.unmarshal(file);

        return object;
    }

    /**
     * By given filename and object, it parses the object to XML file. The
     * object's class must be decorated with the corresponding decorators.
     *
     * @param object Object to be parsed.
     * @param file Output file.
     * @throws JAXBException if the XML cannot be parsed.
     * @throws IOException if there's a problem with the reading or writing of
     * the XML.
     */
    public void writeObject(T object, File file) throws JAXBException, IOException {

        context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try (OutputStream os = new FileOutputStream(file)) {

            marshaller.marshal(object, os);
            marshaller.marshal(object, System.out);
        } catch (IOException ex) {
            Logger.getLogger(XmlComunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        JAXBContext jc = JAXBContext.newInstance(object.getClass());

        jc.generateSchema(new SchemaOutputResolver() {

            String suggestedFileName = "test_" + file.toString().split("\\.")[0] + ".xsd";

            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) {
                return new StreamResult(this.suggestedFileName);
            }
        });
    }

    public boolean applyTransformation(T object, File xsltFile) {

        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource xslt = new StreamSource(xsltFile);
        StringWriter writer = new StringWriter();
        try {

            Transformer transformer = tf.newTransformer(xslt);
            context = JAXBContext.newInstance(object.getClass());
            JAXBSource source = new JAXBSource(context, object);

            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            
            createHtml("transform_" + xsltFile.getName().split("\\.")[0] + ".html", writer.toString());
            
            System.out.println(writer.toString());

        } catch (TransformerConfigurationException | JAXBException ex) {
            ex.printStackTrace();
            return false;
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void createHtml(String fileName, String content) {
        
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            
            bw.write(content);
            
        } catch (IOException ex) {
            Logger.getLogger(XmlComunicator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
