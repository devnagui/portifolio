package br.com.devnagui.project.objetodepagina.richfaces.tabela.paginacao;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.devnagui.project.objetodepagina.MainMenuPageObject;

public class RichfacesPagedDatableHandler {

    /**
     * Log var.
     */
    private static final Logger LOG = Logger.getLogger(RichfacesPagedDatableHandler.class);

    /**
     * CSS aplicado pelo richfaces quando uma determinada pagina da tabela foi
     * escolhida.
     */
    private final static String cssDataScrollerEscolhido = "rf-ds-act";

    /**
     * O driver da pagina.
     */
    private WebDriver driver;

    /**
     * O id da tabela base.
     */
    private String idTabelaBase;

    /**
     * Id da coluna com o valor que sera procurado.
     */
    private String idColunaComValorProcurado;

    /**
     * Id da coluna de acao usada para o executar o objeto de acao.
     */
    private String idColunaDeAcao;

    /**
     * Id do paginador da tabela para realizar as mudancas de paginas.
     */
    private String idPaginador;

    /**
     * Valor que sera pesquisado na tabela
     */
    private String valorProcurado;

    /**
     * Objeto de acao, pode ser nulo caso n�o seja realizado nenhuma acao.
     */
    private ExecutadorDeAcaoTabelaRichfacesHandler objetoAcao;

    public RichfacesPagedDatableHandler(WebDriver driver, String idTabelaBase, String idColunaComValorProcurado, String idColunaDeAcao, String idPaginador,
            String valorProcurado, ExecutadorDeAcaoTabelaRichfacesHandler objetoAcao) {
        super();
        this.driver = driver;
        this.idTabelaBase = idTabelaBase;
        this.idColunaComValorProcurado = idColunaComValorProcurado;
        this.idColunaDeAcao = idColunaDeAcao;
        this.idPaginador = idPaginador;
        this.valorProcurado = valorProcurado;
        this.objetoAcao = objetoAcao;
    }

    public void realizarBuscaEAcao() throws InterruptedException {
        for (int paginaAtual = 1;; paginaAtual++) {
            String nomeDataScrollPaginaAtual = idPaginador + "_ds_" + paginaAtual;
            LOG.info("Iniciando busca na tabela com id '" + idTabelaBase + "' pagina - " + paginaAtual);
            try {
                //Pode ser que a pagina da tabela nao tenha terminado de ser carregada, esperando...
                esperarRenderizarDataScroll(nomeDataScrollPaginaAtual);
            } catch (NoSuchElementException e) {
                LOG.error("A tabela nao possui mais paginas onde possam ser procuradas o elemento.", e);
                break;
            }

            WebElement tabela = driver.findElement(By.id(idTabelaBase));
            if (tabela.getText().contains(valorProcurado)) {
                LOG.info("A tabela contem o valor procurado " + valorProcurado);
                // Existe a matricula do usuario procurado na tabela..
                // Marcar o radio button do usuario...
                // nomeTabela:numeroDaLinha:nomeColuna:numeroColuna - Onde o
                // numeroDaLinha e numeroColuna s�o inteiros comecando por zero.
                int quantidadeDeLinhasDaTabela = tabela.findElement(By.id(idTabelaBase + ":tb")).findElements(By.xpath("tr")).size();
                for (int linhaAtualDaTabela = 0; linhaAtualDaTabela < quantidadeDeLinhasDaTabela; linhaAtualDaTabela++) {
                    String matriculaDoFiscalDaLinhaAtual = tabela.findElement(By.id(idTabelaBase + ":" + linhaAtualDaTabela + ":" + idColunaComValorProcurado))
                            .getText();
                    if (valorProcurado.equals(matriculaDoFiscalDaLinhaAtual)) {
                        LOG.info("Valor encontrado no elemento da tabela de id '" + idTabelaBase + ":" + linhaAtualDaTabela + ":" + idColunaComValorProcurado
                                + "'");
                        if (objetoAcao != null) {
                            String idObjetoAcaoBase = idTabelaBase + ":" + linhaAtualDaTabela + ":" + idColunaDeAcao;
                            LOG.info("Realizando acao no objeto de acao de id '" + idObjetoAcaoBase + "'");
                            objetoAcao.realizarAcao(driver, idObjetoAcaoBase);
                        }
                        // Foi verificado anteriormente que a matricula do
                        // fiscal existe
                        // de fato na tabela, entao podemos sair e esperar que o
                        // elemento foi encontrado e o check marcado.
                        break;
                    }
                }
                break;

            }
            //Mudando para a proxima pagina
            try {
                driver.findElement(By.id(idTabelaBase+":"+idPaginador+"_ds_"+(paginaAtual+1))).click();
            } catch (Exception e) {
               LOG.error(e.getMessage(),e);
               throw new IllegalStateException("N�o existe mais paginas para procurar.",e);
            }
        }
    }

    private void esperarRenderizarDataScroll(String nomeDataScrollPaginaAtual) throws InterruptedException {
        WebElement dataScrollPaginaAtual = driver.findElement(By.id(idTabelaBase + ":" + nomeDataScrollPaginaAtual));
        for (int second = 0;; second++) {
            if (second >= MainMenuPageObject.MAX_LOOPS) {
                fail("Elemento n�o rederizado dentro do tempo esperado: " + nomeDataScrollPaginaAtual);
            }

            try {
                // Se a pagina atual que estamos tentando procurar j� foi
                // retornada pelo servidor.
                if (dataScrollPaginaAtual != null && dataScrollPaginaAtual.getAttribute("class").contains("rf-ds-act")) {
                    LOG.info("Pagina da tabela carregada com sucesso.");
                    break;
                }
            } catch (Exception e) {
                LOG.error(e);
            }

            Thread.sleep(MainMenuPageObject.TTL_MILIS);
        }
    }

}
