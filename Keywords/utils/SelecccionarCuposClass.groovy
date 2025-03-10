package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.assertj.core.api.InstanceOfAssertFactories.STRING

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import java.text.SimpleDateFormat
import java.util.Calendar

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable
import com.kms.katalon.core.webui.common.WebUiCommonHelper

public class SelecccionarCuposClass {

	private static final TestObject spinner = new TestObject("spinner").addProperty("xpath", ConditionType.EQUALS, "//i[contains(@class, 'ajax-loader') and contains(@class, 'fa-spin')]")

	@Keyword
	def seleccionarCupo(String horaInicio, String horaFin) {

		TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')

		// Normalizar espacios no rompibles (U+00A0) a espacios normales y recortar
		horaInicio = horaInicio.replace('\u00A0', ' ').trim()
		horaFin = horaFin.replace('\u00A0', ' ').trim()

		// XPath corregido: Se busca dentro del td de la fila de nivel 2
		String xpath = "//tr[contains(@class, 'ui-node-level-2')]/td[contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]"
		WebUI.comment("XPath utilizado: " + xpath)

		// Crear TestObject dinámico
		TestObject filaCupo = new TestObject("filaCupo").addProperty("xpath", ConditionType.EQUALS, xpath)

		int intentos = 3
		boolean exito = false

		while (intentos > 0 && !exito) {

			try {
				// Cambiar al contexto del iframe en cada intento
				WebUI.switchToFrame(iframe, 10)
				ocultarEncabezado()

				// Esperar a que el elemento esté presente y sea visible
				WebUI.waitForElementPresent(filaCupo, 10)
				WebUI.waitForElementVisible(filaCupo, 10)

				List<WebElement> elementos = WebUiCommonHelper.findWebElements(filaCupo, 5)

				if (elementos.size() == 0) {
					throw new Exception("No se encontraron elementos para el XPath: " + xpath)
				}

				// Desplazar la página hasta el elemento usando JavaScript
				WebElement element = elementos.get(0)
				((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
				WebUI.delay(2) // Pequeña pausa para asegurar que el desplazamiento se complete

				// Hacer clic después de asegurar que está en vista
				WebUI.waitForElementClickable(filaCupo, 5)
				WebUI.click(filaCupo)
				WebUI.waitForElementNotVisible(spinner, 20)
				WebUI.rightClick(filaCupo)
				WebUI.waitForElementNotVisible(spinner, 20)

				WebUI.switchToDefaultContent()
				// Verificar si el elemento 'Agendar' es visible y clickable
				if (!WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'), 5)) {
					throw new Exception("El elemento 'Agendar' no es visible.")
				}

				//WebUI.switchToFrame(iframe, 10)

				WebUI.comment("Cupo seleccionado correctamente: ${horaInicio} - ${horaFin}")
				exito = true
			} catch (Exception e) {
				intentos--
				WebUI.comment("Error al seleccionar el cupo (${horaInicio} - ${horaFin}), intentos restantes: ${intentos}")
				if (intentos == 0) {
					WebUI.comment("No se pudo seleccionar el cupo. Error: " + e.getMessage())
					throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
				}
				WebUI.delay(2)
			} finally {
				WebUI.switchToDefaultContent()
			}
		}
	}

	@Keyword
	def seleccionarCupoCitaPrevia(String horaInicio, String horaFin) {

		TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')

		// Normalizar espacios no rompibles (U+00A0) a espacios normales y recortar
		horaInicio = horaInicio.replace('\u00A0', ' ').trim()
		horaFin = horaFin.replace('\u00A0', ' ').trim()

		// XPath corregido: Se busca dentro del td de la fila de nivel 2
		String xpath = "//tr[contains(@class, 'ui-node-level-2')]/td[contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]"
		WebUI.comment("XPath utilizado: " + xpath)

		// Crear TestObject dinámico
		TestObject filaCupo = new TestObject("filaCupo").addProperty("xpath", ConditionType.EQUALS, xpath)

		int intentos = 3
		boolean exito = false

		while (intentos > 0 && !exito) {

			try {
				// Cambiar al contexto del iframe en cada intento
				WebUI.switchToFrame(iframe, 10)
				ocultarEncabezado()

				// Esperar a que el elemento esté presente y sea visible
				WebUI.waitForElementPresent(filaCupo, 10)
				WebUI.waitForElementVisible(filaCupo, 10)

				List<WebElement> elementos = WebUiCommonHelper.findWebElements(filaCupo, 5)

				if (elementos.size() == 0) {
					throw new Exception("No se encontraron elementos para el XPath: " + xpath)
				}

				// Desplazar la página hasta el elemento usando JavaScript
				WebElement element = elementos.get(0)
				((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
				WebUI.delay(2) // Pequeña pausa para asegurar que el desplazamiento se complete

				// Hacer clic después de asegurar que está en vista
				WebUI.waitForElementClickable(filaCupo, 5)
				WebUI.click(filaCupo)
				WebUI.waitForElementNotVisible(spinner, 20)
				WebUI.rightClick(filaCupo)
				WebUI.waitForElementNotVisible(spinner, 20)

				WebUI.switchToDefaultContent()
				// Verificar si el elemento 'Agendar' es visible y clickable
				if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'), 5)) {
					throw new Exception("El elemento 'Cambiar estado' no es visible.")
				}

				//WebUI.switchToFrame(iframe, 10)

				WebUI.comment("Cupo seleccionado correctamente: ${horaInicio} - ${horaFin}")
				exito = true
			} catch (Exception e) {
				intentos--
				WebUI.comment("Error al seleccionar el cupo (${horaInicio} - ${horaFin}), intentos restantes: ${intentos}")
				if (intentos == 0) {
					WebUI.comment("No se pudo seleccionar el cupo. Error: " + e.getMessage())
					throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
				}
				WebUI.delay(2)
			} finally {
				WebUI.switchToDefaultContent()
			}
		}
	}

	@Keyword
	def seleccionarHistoriaClinica(String horaInicio, String horaFin) {

		TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')

		TestObject spinner = findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga')

		// Normalizar espacios no rompibles (U+00A0) a espacios normales y recortar
		horaInicio = horaInicio.replace('\u00A0', ' ').trim()
		horaFin = horaFin.replace('\u00A0', ' ').trim()

		// XPath corregido: Se busca dentro del td de la fila de nivel 2
		String xpath = "//tr[contains(@class, 'ui-node-level-2')]/td[contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]"
		WebUI.comment("XPath utilizado: " + xpath)

		TestObject historiaClinica = new TestObject("historiaClinica").addProperty("xpath", ConditionType.EQUALS, "//tr[contains(@class, 'ui-node-level-2') and contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]//button[contains(@id, 'j_idt344')]")

		// Crear TestObject dinámico
		TestObject filaCupo = new TestObject("filaCupo").addProperty("xpath", ConditionType.EQUALS, xpath)

		int intentos = 3
		boolean exito = false

		while (intentos > 0 && !exito) {

			try {
				// Cambiar al contexto del iframe en cada intento
				WebUI.switchToFrame(iframe, 10)
				ocultarEncabezado()

				// Esperar a que el elemento esté presente y sea visible
				WebUI.waitForElementPresent(filaCupo, 10)
				WebUI.waitForElementVisible(filaCupo, 10)

				List<WebElement> elementos = WebUiCommonHelper.findWebElements(filaCupo, 5)

				if (elementos.size() == 0) {
					throw new Exception("No se encontraron elementos para el XPath: " + xpath)
				}

				// Desplazar la página hasta el elemento usando JavaScript
				WebElement element = elementos.get(0)
				((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
				WebUI.delay(2) // Pequeña pausa para asegurar que el desplazamiento se complete

				// Hacer clic después de asegurar que está en vista
				WebUI.waitForElementClickable(filaCupo, 5)
				WebUI.click(filaCupo)
				WebUI.waitForElementNotVisible(spinner, 20)
				WebUI.click(historiaClinica)
				WebUI.waitForElementNotVisible(spinner, 20)

				WebUI.switchToDefaultContent()

				WebUI.comment("Cupo seleccionado correctamente: ${horaInicio} - ${horaFin}")
				exito = true
			} catch (Exception e) {
				intentos--
				WebUI.comment("Error al seleccionar el cupo (${horaInicio} - ${horaFin}), intentos restantes: ${intentos}")
				if (intentos == 0) {
					WebUI.comment("No se pudo seleccionar el cupo. Error: " + e.getMessage())
					throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
				}
				WebUI.delay(2)
			} finally {
				WebUI.switchToDefaultContent()
			}
		}
	}

	private void ocultarEncabezado() {
		((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("document.getElementById('form:divheaderProfesional').style.display = 'none';")
	}
}
