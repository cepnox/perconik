
package com.gratex.perconik.services.tag;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "TagProfileWcfSvc", targetNamespace = "http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", wsdlLocation = "http://perconikapp1:9903/Adm/Wcf/TagProfileWcfSvc.svc?singleWsdl")
public class TagProfileWcfSvc
    extends Service
{

    private final static URL TAGPROFILEWCFSVC_WSDL_LOCATION;
    private final static WebServiceException TAGPROFILEWCFSVC_EXCEPTION;
    private final static QName TAGPROFILEWCFSVC_QNAME = new QName("http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", "TagProfileWcfSvc");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://perconikapp1:9903/Adm/Wcf/TagProfileWcfSvc.svc?singleWsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TAGPROFILEWCFSVC_WSDL_LOCATION = url;
        TAGPROFILEWCFSVC_EXCEPTION = e;
    }

    public TagProfileWcfSvc() {
        super(__getWsdlLocation(), TAGPROFILEWCFSVC_QNAME);
    }

    public TagProfileWcfSvc(WebServiceFeature... features) {
        super(__getWsdlLocation(), TAGPROFILEWCFSVC_QNAME, features);
    }

    public TagProfileWcfSvc(URL wsdlLocation) {
        super(wsdlLocation, TAGPROFILEWCFSVC_QNAME);
    }

    public TagProfileWcfSvc(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TAGPROFILEWCFSVC_QNAME, features);
    }

    public TagProfileWcfSvc(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TagProfileWcfSvc(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ITagProfileWcfSvc
     */
    @WebEndpoint(name = "BasicHttpBinding_ITagProfileWcfSvc")
    public ITagProfileWcfSvc getBasicHttpBindingITagProfileWcfSvc() {
        return super.getPort(new QName("http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", "BasicHttpBinding_ITagProfileWcfSvc"), ITagProfileWcfSvc.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ITagProfileWcfSvc
     */
    @WebEndpoint(name = "BasicHttpBinding_ITagProfileWcfSvc")
    public ITagProfileWcfSvc getBasicHttpBindingITagProfileWcfSvc(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.gratex.com/PerConIk/TagAdm/ITagProfileWcfSvc", "BasicHttpBinding_ITagProfileWcfSvc"), ITagProfileWcfSvc.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TAGPROFILEWCFSVC_EXCEPTION!= null) {
            throw TAGPROFILEWCFSVC_EXCEPTION;
        }
        return TAGPROFILEWCFSVC_WSDL_LOCATION;
    }

}
