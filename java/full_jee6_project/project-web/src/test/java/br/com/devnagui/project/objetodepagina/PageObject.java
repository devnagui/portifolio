package br.com.devnagui.project.objetodepagina;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject  {

	protected long ajaxTimeOutInSeconds = 30;
	protected WebDriver driver;
	public PageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public boolean isTextoPresente(String texto) {
		return getPageSource().contains(texto);
	}

	protected void setValorElemento(WebElement element, String valor) {
		element.clear();
		element.sendKeys(valor);
	}

	protected void setValorCombo(WebElement selectElement, String valor) {
		List<WebElement> allOptions = selectElement.findElements(By.tagName("option"));
		for (WebElement option : allOptions) {
			if (option.getAttribute("value").equals(valor)) {
				option.click();
				return;
			}
		}
		throw new RuntimeException("Valor nao encontrado.");
	}

	protected void setTextoCombo(WebElement selectElement, String texto) {
		List<WebElement> allOptions = selectElement.findElements(By.tagName("option"));
		for (WebElement option : allOptions) {
			if (option.getText().equals(texto)) {
				option.click();
				return;
			}
		}
		throw new RuntimeException("Texto nao encontrado.");
	}

	public WebElement getElementByName(String element) {
		return driver.findElement(By.name(element));
	}

	public WebElement getElementById(String element) {
		return driver.findElement(By.id(element));
	}
	
	public WebElement getElementByXPath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}
	
	public List<WebElement> getElementsByXPath(String xpath) {
		return driver.findElements(By.xpath(xpath));
	}
	
	public WebElement getElementByCssSelector(String selector) {
		return driver.findElement(By.cssSelector(selector));
	}

	/**
	 * Determina que o Selenium aguarde que uma determinada condiaao seja verdadeira antes de
	 * prosseguir.
	 * 
	 * @param expectedCondition
	 */
	private void waitExpectedCondition(ExpectedCondition<Boolean> expectedCondition) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, ajaxTimeOutInSeconds);
		webDriverWait.until(expectedCondition);
	}


	protected void waitForAjaxCompletion() {
		// Esta sempre retornando zero:
		// selenium.waitForCondition("selenium.browserbot.getCurrentWindow().jQuery.active == 0",
		// "30000");
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Aguarda que a div de Ajax Loading fique oculta:
		// waitForWebElementHidden(By.cssSelector(".rich-status-start"));
	}
}