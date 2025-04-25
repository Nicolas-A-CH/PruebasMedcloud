import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.WebElement as WebElement
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import groovy.transform.Field
import java.util.Locale as Locale
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory

// Configuración inicial
@Field String horaInicioJornada = "8:40"
@Field int totalCitas = 68

def selectDynamicDate(String day) {
	int intentos = 3

	boolean exito = false

	while ((intentos > 0) && !(exito)) {
		try {
			WebUI.switchToFrame(findTestObject('Object Repository/Crear cupos para citas/Page_MedCloud IDL/iframe'), 10)

			String xpathDate = "//table[@class='ui-datepicker-calendar']//a[text()='$day']"

			TestObject fecha = new TestObject("dia_$day").addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS,
				xpathDate)

			WebUI.waitForElementPresent(fecha, 5)

			WebUI.waitForElementClickable(fecha, 5)

			WebUI.enhancedClick(fecha)

			exito = true
		}
		catch (org.openqa.selenium.StaleElementReferenceException e) {
			intentos--

			if (intentos == 0) {
				throw e
			}
			
			WebUI.switchToDefaultContent()

			WebUI.delay(1)
		}
		finally {
			WebUI.switchToDefaultContent()
		}
	}
}

// Método para convertir formato de hora (HH:mm -> h:mm a. m./p. m.)
def convertirFormatoHora(String hora) {
	def formatoEntrada = new SimpleDateFormat("HH:mm")
	def formatoSalida = new SimpleDateFormat("h:mm a", new Locale("es", "ES"))
	def fecha = formatoEntrada.parse(hora)
	return formatoSalida.format(fecha)
}

// Método para encontrar y seleccionar la cita por hora
def seleccionarCupo(String horaInicio, String horaFin) {
    // Cambiar el contexto al iframe
    TestObject iframe = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/iframe')
    
    // Normalizar espacios no rompibles (U+00A0) a espacios normales y recortar
    horaInicio = horaInicio.replace('\u00A0', ' ').trim()
    horaFin = horaFin.replace('\u00A0', ' ').trim()
    
    // XPath corregido: Se busca dentro del td de la fila de nivel 2
    String xpath = "//tr[contains(@class, 'ui-node-level-2')]/td[contains(., '${horaInicio}') and .//span[contains(@id, 'divhora') and contains(., '-${horaFin}')]]"
    
    // Depuración: Log de valores reales
    println "Hora inicio normalizada: '${horaInicio}'"
    println "Hora fin normalizada: '${horaFin}'"
    println "XPath generado: ${xpath}"
    WebUI.comment("XPath utilizado: " + xpath)
    
    // Crear TestObject dinámico
    TestObject filaCupo = new TestObject("filaCupo").addProperty("xpath", ConditionType.EQUALS, xpath)
	
	int intentos = 3
	boolean exito = false
	
	while (intentos > 0 && !exito) {
	
		try {
			// Cambiar al contexto del iframe en cada intento
			WebUI.switchToFrame(iframe, 10)
			
			// Ocultar el encabezado fijo
			((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("document.getElementById('form:divheaderProfesional').style.display = 'none';")
			
			// Obtener el valor del xpath del objeto filaCupo
			def propiedades = filaCupo.getProperties()
			def xpathCupo = propiedades.find { it.name == "xpath" }?.value
			println "XPath del cupo: '${xpathCupo}'"
			// Esperar a que el elemento esté presente y sea visible
			WebUI.waitForElementPresent(filaCupo, 10)
			WebUI.waitForElementVisible(filaCupo, 10)
			
			List<WebElement> elementos = WebUiCommonHelper.findWebElements(filaCupo, 5)
			WebUI.comment("Cantidad de elementos encontrados: " + elementos.size())
			
			if (elementos.size() == 0) {
				throw new Exception("No se encontraron elementos para el XPath: " + xpath)
			}
			
			// Desplazar la página hasta el elemento usando JavaScript
			WebElement element = elementos.get(0)
			((JavascriptExecutor) DriverFactory.getWebDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
			WebUI.delay(1) // Pequeña pausa para asegurar que el desplazamiento se complete
			
			// Hacer clic después de asegurar que está en vista
			WebUI.waitForElementClickable(filaCupo, 5)
			WebUI.focus(filaCupo)
			WebUI.delay(1)
			WebUI.enhancedClick(filaCupo)
			WebUI.delay(2)
			WebUI.rightClick(filaCupo)
			
			WebUI.delay(2)
			
			WebUI.switchToDefaultContent()
			// Verificar si el elemento 'Agendar' es visible y clickable
            if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'), 5)) {
                throw new Exception("El elemento 'estado' no es visible.")
            }
            
            if (!WebUI.waitForElementClickable(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'), 5)) {
                throw new Exception("El elemento 'estado' no es clickable.")
            }			
			WebUI.switchToFrame(iframe, 10)
			
			WebUI.comment("Cupo seleccionado correctamente: ${horaInicio} - ${horaFin}")
			exito = true
			
		} catch (Exception e) {
			intentos--
            WebUI.comment("Error al seleccionar el cupo (${horaInicio} - ${horaFin}), intentos restantes: ${intentos}")
            if (intentos == 0) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
                WebUI.comment("No se pudo seleccionar el cupo tras varios intentos. Error: " + e.getMessage())
                throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
            }
            WebUI.delay(2)
		} finally {
			WebUI.switchToDefaultContent()
		}
	}
	
}

def cambioEstado(String horaInicio, String horaFin) {
	
	//Seleccionar el cupo
	seleccionarCupo(horaInicio, horaFin)
	
	int intentos = 3
	boolean exito = false
	
	while (intentos > 0 && !exito) {
		try {
			//posiciona el mouse en el elemento
			WebUI.focus(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'))
			
			//Esperar a que los elementos esten disponibles
			WebUI.waitForElementPresent(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'), 5)
			
			if (!WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'), 15)) {
				
				throw new Exception("El elemento 'En sala' no es visible.")
			}
			WebUI.waitForElementClickable(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'), 5)
			
			//Cambiar el estado
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'))
			
			//Esperar a que el elemento ya no sea visible
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_En sala'), 15)
			
			exito = true
			
		} catch (Exception e) {
			
			//Reducir los intentos
			intentos--
			
            WebUI.comment("Error al seleccionar el estado 'En sala'")
            if (intentos == 0) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
                WebUI.comment("No se pudo seleccionar el link tras varios intentos. Error: " + e.getMessage())
                throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
            }
            WebUI.delay(2)
		}
	}
	
}

// Lógica principal para el cambio de estado en citas de 12 horas
def crearJornadaCompleta() {
    def formatoHora = new SimpleDateFormat("HH:mm") // Formato de 24 horas para cálculos
    def calendar = Calendar.getInstance()
    calendar.setTime(formatoHora.parse(horaInicioJornada)) // Usa el formato de 24 horas
    
    (1..totalCitas).each { index ->
        // Calcular horario en formato de 24 horas
        def horaActual = calendar.time
        def horaInicio24h = formatoHora.format(horaActual)
        calendar.add(Calendar.MINUTE, 120)
        def horaFin24h = formatoHora.format(calendar.time)
        
        // Convertir a formato de 12 horas para la interfaz
        def horaInicio = convertirFormatoHora(horaInicio24h)
        def horaFin = convertirFormatoHora(horaFin24h)
        
        // Cambio de estado
        cambioEstado(horaInicio, horaFin)
		
		// Incremento de 30 minutos para la hora inicial de la siguiente cita
		calendar.add(Calendar.MINUTE, -110)
        
        // Pequeña pausa entre citas
        WebUI.delay(3)
    }
}

//apertura de navegacion
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

//Inicio de sesion
WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Ver.9.9.5.19022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

WebUI.click(findTestObject('null'))

//Navegación a la agenda médica
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Agendas Expandidas                     _5ffbf0_1'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_NICOLAS AREVALO'))

WebUI.waitForElementPresent(findTestObject('null'), 
    0)

selectDynamicDate('26')

WebUI.delay(3)

// Crear jornada completa
crearJornadaCompleta()