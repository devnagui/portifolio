/**
 */
package br.com.devnagui.project.objetodepagina;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.devnagui.project.objetodepagina.fiscal.manter.UserPageObject;

/**
 * 
 */
public class MainMenuPageObject extends PageObject {
    /**
	 * 
	 */
    public static final int TTL_MILIS = 500;

    /**
     */
    public static final int MAX_LOOPS = 16;

    /**
     * LOG var.
     */
    private static final Logger LOG = Logger.getLogger(MainMenuPageObject.class);

    /**
     * standard url for tests.
     */
    private String baseUrl = "http://localhost:8080/project";

    /**
     * @throws Exception
     * 
     */
    public MainMenuPageObject(WebDriver driver) throws Exception {
        super(driver);
    }

    public MainMenuPageObject chamaPaginaInicial() throws Exception {
    	UserPageObject paginaInicial = PageFactory.initElements(driver, UserPageObject.class);
        driver.get(baseUrl);
        return paginaInicial;
    }

    public UserPageObject acionarManterFiscal() throws InterruptedException {
        getElementById("lnkPaginaManterFiscais").click();
        waitToRenderElementId("form_habilitar");
        return PageFactory.initElements(driver, UserPageObject.class);
    }

    /**
     * Espera ate 10s para que o panel de edicao/criacao de tanques seja
     * renderizado.
     * 
     * @param msgRegex
     * @throws InterruptedException
     */
    protected void waitMessageByRegex(String msgRegex) throws InterruptedException {
        LOG.info("Esperando redenrizar mensagem: " + msgRegex);
        returnToRoot();
        for (int second = 0;; second++) {
            if (second >= MAX_LOOPS) {
                fail("Mensagem n�o rederizada dentro do tempo esperado: " + msgRegex);
            }
            try {
                final String cssSelector = "BODY";
                final boolean corpoHtmlPossuiMensagemBuscada = this.getElementByCssSelector(cssSelector).getText().matches(msgRegex);
                if (corpoHtmlPossuiMensagemBuscada) {
                    break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }
            Thread.sleep(TTL_MILIS);
        }
    }

    /**
     * Espera ate 8s para que o elemento seja renderizado.
     * 
     * @param idElemento
     * @throws InterruptedException
     */
    protected void waitToRenderElementId(String idElemento) throws InterruptedException {
        LOG.info("Esperando redenrizar elemento: " + idElemento);
        returnToRoot();
        for (int second = 0;; second++) {
            if (second >= MAX_LOOPS) {
                fail("Elemento n�o rederizado dentro do tempo esperado: " + idElemento);
            }

            try {
                if (driver.findElement(By.id(idElemento)) != null) {
                    break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }

            Thread.sleep(TTL_MILIS);
        }
    }

    /**
     * Espera ate 10s para que o elemento seja renderizado.
     * 
     * @param xpath
     * @throws InterruptedException
     */
    protected void selectByXPath(String xpath) throws InterruptedException {
        LOG.info("Selecionar linha pelo xpath: " + xpath);
        returnToRoot();
        final int QUANTIDADE_DE_ELEMENTOS_RENDERIZADAS = 1;

        for (int second = 0;; second++) {
            if (second >= MAX_LOOPS) {
                fail("Elemento procurado n�o foi rederizado dentro do tempo esperado: " + xpath);
            }

            try {
                final int quantidadeElementosEncontrados = this.getElementsByXPath(xpath).size();
                final boolean quantidadeDeElementosEncontradosIgualRenderizados = QUANTIDADE_DE_ELEMENTOS_RENDERIZADAS == quantidadeElementosEncontrados;
                if (quantidadeDeElementosEncontradosIgualRenderizados) {
                    this.getElementByXPath(xpath).click();
                    break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }

            Thread.sleep(TTL_MILIS);
        }
    }

    /**
     * Verifica se o elemento procurado, encontra-se vis�vel.
     * 
     * @param idElemento
     * @return {@link Boolean}
     * @throws InterruptedException
     */
    protected boolean isElementVisible(String idElemento) throws InterruptedException {
        LOG.info("Verificando se o elemento est� visivel: " + idElemento);
        returnToRoot();
        if (this.getElementById(idElemento).isDisplayed()) {
            return true;
        }

        return false;
    }
    
    /**
     * Espera ate 8s para que o elemento seja renderizado.
     * 
     * @param idElemento
     * @throws InterruptedException
     */
    protected void waitToRenderBy(final By by) throws InterruptedException {
        LOG.info("Esperando redenrizar elemento: " + by.toString());
        returnToRoot();
        for (int second = 0;; second++) {
            if (second >= MAX_LOOPS) {
                fail("Elemento n�o rederizado dentro do tempo esperado: " + by);
            }

            try {
                if (!driver.findElements(by).isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }

            Thread.sleep(TTL_MILIS);
        }
    }

    private void returnToRoot() {
        LOG.info(driver.switchTo().activeElement());
        LOG.info(driver.switchTo().defaultContent());
        driver = driver.switchTo().defaultContent();
        PageFactory.initElements(driver, this);
    }

}
