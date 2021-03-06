/**
 */
package br.com.devnagui.project.objetodepagina.fiscal.manter;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import br.com.devnagui.project.objetodepagina.MainMenuPageObject;

/**
 * Page Object to functional tests for user page.
 */
public class UserPageObject extends MainMenuPageObject {

	// ATRIBUTOS E CONSTRUTORES

	@FindBy(id = "btNovoFiscal")
	private WebElement buttonNewUser;
	@FindBy(id = "btDesabilitarFiscal")
	private WebElement buttonDisableUser;
	@FindBy(id = "btLimparCampos")
	private WebElement buttonCleanFields;
	@FindBy(id = "btHabilitarFiscal")
	private WebElement buttonEnableUser;
	@FindBy(id = "btCancelar")
	private WebElement buttonCancel;
	@FindBy(id = "btCancelarDesabilitar")
	private WebElement buttonCancelUserDisable;
	@FindBy(id = "btConfirmarDesabilitar")
	private WebElement buttonConfirmUserDisable;

	@FindBy(id = "matriculaUsuarioInput")
	private WebElement registrationNumberInput;
	@FindBy(id = "matriculaUsuarioInput")
	private WebElement userNameInput;

	@FindBy(id = "tabela:0:radioUsuario:0")
	private WebElement firstUserAtTable;

	/**
	 * @param driver
	 * @throws Exception
	 */
	public UserPageObject(WebDriver driver) throws Exception {
		super(driver);
	}

	private static final Logger LOG = Logger.getLogger(UserPageObject.class);

	private static final String ID_PANEL_ENABLE_USER = "novoFiscalPanel";
	private static final String ID_PANEL_DISABLE_USER = "desabilitarPanel";
	private static final String ID_RADIO_FIRST_USER_AT_TABLE = "tabela:0:radioUsuario:0";

	private static final String XPATH_ELEMENT_REGISTRATION= "//table[@id='matriculaUsuarioItems']/tbody/tr/td[2]";
	private static final String XPATH_ELEMENT_USER_NAME = "/html/body/div/div[2]/div[3]/div[3]/form[2]/div/div[2]/div[3]/div/span[2]/div/div/div[5]/div/table/tbody/tr/td[2]";
	private static final String MSG_SUCESS_IS_USER_VALID_WITH_REGEX = "^[\\s\\S]*Fiscal habilitado com sucesso.[\\s\\S]*$";
	private static final String MSG_SUCESS_USER_DISABLED_WITH_REGEX = "^[\\s\\S]*Fiscal desabilitado com sucesso.[\\s\\S]*$";
	private static final String MSG_ERROR_DISABLING_USER_WITH_REGEX = "^[\\s\\S]*Para habilitar o Fiscal, \u00E9 preciso informar o Usu\u00E1rio.[\\s\\S]*$";
	private static final String MSG_ERRIR_USER_ALREADY_ENABLED_WITH_REGEX = "^[\\s\\S]*O Fiscal informado j\u00E1 est\u00E1 habilitado.[\\s\\S]*$";

	// METODOS PUBLICOS

	/**
	 * 
	 * @param webDriver
	 * @return {@link UserPageObject}
	 * @throws Exception
	 * 
	 */
	public static UserPageObject getInstance(WebDriver webDriver) throws Exception {
		MainMenuPageObject menuConvarObjetoDePagina = new MainMenuPageObject(webDriver);
		menuConvarObjetoDePagina.chamaPaginaInicial();
		UserPageObject fiscalObjetoDePagina = menuConvarObjetoDePagina.acionarManterFiscal();
		return fiscalObjetoDePagina;
	}

	/**
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject prepareToCreateUser() throws InterruptedException {
		this.buttonNewUser.click();
		this.waitToRenderElementId(ID_PANEL_ENABLE_USER);
		this.cleanUserFields();
		PageFactory.initElements(driver,this);
		return this;
	}

	/**
	 * 
	 * @param registration
	 * @param userName
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject createNewUser(String registration, String userName) throws InterruptedException {
		LOG.info("Criando novo fiscal.");
		this.prepareToCreateUser();
		this.setValorElemento(registrationNumberInput, registration);
		LOG.info("Selecionando fiscal");
		getElementById("matUsuarioList").click();
		LOG.info("Esperando renderizar matricula");
		waitToRenderValue(userNameInput, userName);
		LOG.info("Habilitando fiscal");
		this.buttonEnableUser.click();
		this.waitMessageByRegex(MSG_SUCESS_IS_USER_VALID_WITH_REGEX);
		return this;
	}


	/**
	 * Desabilita o primeiro fiscal, habilitado, contido na lista de fiscais.
	 * 
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject disableUser() throws InterruptedException {
		LOG.info("Desabilitando fiscal.");
		this.chooseFirstUserAtTable();
		this.buttonDisableUser.click();
		this.waitToRenderElementId(ID_PANEL_DISABLE_USER);
		this.buttonConfirmUserDisable.click();
		this.waitMessageByRegex(MSG_SUCESS_USER_DISABLED_WITH_REGEX);

		return this;
	}

	/**
	 * Procura e Seleciona o primeiro fiscal, habilitado, contido na lista de
	 * fiscais.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void chooseFirstUserAtTable() throws InterruptedException {
		this.waitToRenderElementId(ID_RADIO_FIRST_USER_AT_TABLE);
		this.firstUserAtTable.click();
	}

	/**
	 * Preenche os campos da tela de cadastro de fiscais e em seguida os limpa.
	 * 
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject cleanFields(String matricula, String nome) throws InterruptedException {
		Log.info("Limpando campos da tela de cadastro de fiscal.");
		this.prepareToCreateUser();
		this.setValorElemento(registrationNumberInput, matricula);
		this.setValorElemento(userNameInput, nome);
		this.buttonCleanFields.click();
		return this;
	}

	/**
	 * Cancela a habilita��o de um novo fiscal.
	 * 
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject cancelEnableUser() throws InterruptedException {
		Log.info("Limpando campos da tela de cadastro de fiscal.");

		this.prepareToCreateUser();
		this.buttonCancel.click();

		return this;
	}

	/**
	 * Tenta habilitar um fiscal, recebendo como par�metro, matr�cula e nome de
	 * usu�rio inexistentes na base.
	 * 
	 * @param matricula
	 * @param nome
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject enableInexistentUser(String matricula, String nome) throws InterruptedException {
		Log.info("Habilitando fiscal com usu�rio inexistente para cadastro.");

		this.prepareToCreateUser();
		this.setValorElemento(registrationNumberInput, matricula);
		this.setValorElemento(userNameInput, nome);
		this.buttonEnableUser.click();
		this.waitMessageByRegex(MSG_ERROR_DISABLING_USER_WITH_REGEX);

		return this;
	}

	/**
	 * Tenta habilitar um fiscal, sem informar matr�cula e nome de usu�rio.
	 * 
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject tryToEnableUserWithoutRegistrationAndName() throws InterruptedException {
		Log.info("Habilitando fiscal com usu�rio inexistente para cadastro.");

		this.prepareToCreateUser();
		this.buttonEnableUser.click();
		this.waitMessageByRegex(MSG_ERROR_DISABLING_USER_WITH_REGEX);

		return this;
	}

	/**
	 * Tenta habilitar um fiscal que j� est� habilitado.
	 * 
	 * @param matriculaUsuarioFiscal
	 * @param nomeUsuarioFiscal
	 * @return {@link UserPageObject}
	 * @throws InterruptedException
	 * 
	 */
	public UserPageObject tryToEnableUserAlreadyEnabled(String matriculaUsuarioValida, String nomeUsuarioValido) throws InterruptedException {
		LOG.info("Habilitando fiscal j� habilitado.");

		this.prepareToCreateUser();
		this.setValorElemento(registrationNumberInput, matriculaUsuarioValida);
		this.setValorElemento(userNameInput, nomeUsuarioValido);
		this.buttonEnableUser.click();
		this.waitMessageByRegex(MSG_ERRIR_USER_ALREADY_ENABLED_WITH_REGEX);

		return this;
	}

	/**
	 * Verifica se o fiscal foi habilitado com sucesso.
	 * 
	 * @return {@link Boolean}
	 * 
	 */
	public boolean isUserValid() {
		final String cssSelector = "BODY";
		return this.getElementByCssSelector(cssSelector).getText().matches(MSG_SUCESS_IS_USER_VALID_WITH_REGEX);
	}

	/**
	 * Verifica se o fiscal foi desabilitado com sucesso.
	 * 
	 * @return {@link Boolean}
	 * 
	 */
	public boolean isFiscalDesabilitadoComSucesso() {
		final String cssSelector = "BODY";
		return this.getElementByCssSelector(cssSelector).getText().matches(MSG_SUCESS_USER_DISABLED_WITH_REGEX);
	}

	/**
	 * Verifica se os campos da tela de cadastro foram limpos e por tanto, n�o
	 * possuem mais os valores passados como par�metro.
	 * 
	 * @param matricula
	 * @param nome
	 * @return {@link Boolean}
	 * 
	 */
	public boolean isFieldsEmpty(String matricula, String nome) {
		final String cssSelector = "BODY";
		final boolean isCampoMatriculaLimpo = !this.getElementByCssSelector(cssSelector).getText().matches(matricula);
		final boolean isCampoNomeLimpo = !this.getElementByCssSelector(cssSelector).getText().matches(nome);
		return isCampoMatriculaLimpo && isCampoNomeLimpo;
	}

	/**
	 * Verifica se foi exibida mensagem, informando que n�o foi informado um
	 * usu�rio v�lido.
	 * 
	 * @return {@link Boolean}
	 * 
	 */
	public boolean existsUser() {
		final String cssSelector = "BODY";
		return this.getElementByCssSelector(cssSelector).getText().matches(MSG_ERROR_DISABLING_USER_WITH_REGEX);
	}

	/**
	 * Verifica se foi exibida mensagem, informando que n�o foi informado um
	 * usu�rio v�lido.
	 * 
	 * @return {@link Boolean}
	 * 
	 */
	public boolean isUserAlreadyEnabled() {
		final String cssSelector = "BODY";
		return this.getElementByCssSelector(cssSelector).getText().matches(MSG_ERRIR_USER_ALREADY_ENABLED_WITH_REGEX);
	}

	/**
	 * Verifica se foi exibida mensagem, informando que n�o foi informado um
	 * usu�rio v�lido.
	 * 
	 * @return {@link Boolean}
	 * @throws InterruptedException
	 * 
	 */
	public boolean isUserPanelVisible() throws InterruptedException {
		return this.isElementVisible(ID_PANEL_ENABLE_USER);
	}

	// METODOS PRIVADOS

	/**
	 * Limpa todos os campos da tela de cadastro de usu�rio, preparando-os para
	 * futuras opera��es.
	 * 
	 */
	private void cleanUserFields() {
		registrationNumberInput.clear();
		userNameInput.clear();
	}

	/**
	 * Espera ate 10s para que o panel de edicao/criacao de tanques seja
	 * renderizado.
	 * 
	 * @param mensagemBuscada
	 * @throws InterruptedException
	 */
	private void waitToRenderValue(WebElement elemento, String valorEsperado) throws InterruptedException {
		LOG.info("Esperando redenrizar valor no elemento: " + elemento);

		for (int second = 0;; second++) {
			if (second >= 10) {
				fail("Timeout! The value cannot be found.");
			}
			try {
				String valor = elemento.getAttribute("value");
				if (valorEsperado.equals(valor))
					break;
			} catch (Exception e) {
				LOG.error(e);
			}
			Thread.sleep(1000);
		}
	}

	// GETS E SETS
}
