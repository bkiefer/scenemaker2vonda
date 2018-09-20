package compiler;
import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.SAXHandler;
import org.jdom2.input.sax.SAXHandlerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Custom {@code SAXBuilder} to ignore namespaces.
 * @author Max Depenbrock
 */
public class CustomSaxBuilder {

  private static final SAXHandlerFactory FACTORY = new SAXHandlerFactory() {
    @Override
    public SAXHandler createSAXHandler(JDOMFactory factory) {
        return new SAXHandler() {
            @Override
            public void startElement(
                    String namespaceUri, String localName, String qname,
                      Attributes atts) throws SAXException {
                super.startElement("", localName, qname, atts);
            }

            @Override
            public void startPrefixMapping(String prefix, String uri) throws SAXException {
                return;
            }
        };
    }
  };


  /** 
   * Get a {@code SAXBuilder} that ignores namespaces.
   * Any namespaces present in the xml input to this builder will be omitted from the
   * resulting {@code Document}.
   * @return custom SAXBuilder
   */
  public static SAXBuilder getSaxBuilder() {
    // Note: SAXBuilder is NOT thread-safe, so we instantiate a new one for every call.
    SAXBuilder saxBuilder = new SAXBuilder();
    saxBuilder.setSAXHandlerFactory(FACTORY);
    return saxBuilder;
  }
}
