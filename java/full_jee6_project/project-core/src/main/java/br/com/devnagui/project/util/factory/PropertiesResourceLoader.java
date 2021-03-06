/**
 * 
 */
package br.com.devnagui.project.util.factory;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

/**
 * Caregador de arquivos properties dentro do project-core :)
 * 
 * @author Nathan
 * 
 */
public class PropertiesResourceLoader {
    private static final Logger LOG = Logger.getLogger(PropertiesResourceLoader.class);

    /**
     * Carrega o properties dentro do project-core, se necess�rio h� espa�o para
     * um "loader" especifico.
     * 
     * @param ip
     * @return O properties carregado.
     */
    @Produces
    @PropertiesResource(name = "", loader = "")
    Properties loadProperties(InjectionPoint ip) {
        LOG.info("Iniciando a factory de properties.");
        PropertiesResource annotation = ip.getAnnotated().getAnnotation(PropertiesResource.class);
        String fileName = annotation.name();
        Properties props = null;
        // Load the properties from file
        URL url = null;
        url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        LOG.info("Url do properties: " + url);
        if (url != null) {
            props = new Properties();
            try {
                props.load(url.openStream());
                LOG.info("Properties carregado com sucesso.");
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return props;
    }
}
