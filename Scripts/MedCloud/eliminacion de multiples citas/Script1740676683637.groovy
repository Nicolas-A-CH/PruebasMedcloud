import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat

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

import groovy.transform.Field
import internal.GlobalVariable
import utils.CalcularIteraciones
import utils.InicioSesionClass
import utils.SelecccionarCuposClass
import utils.SelectorCalendarioClass

import org.openqa.selenium.Keys as Keys

//Intancia de class
InicioSesionClass inicioSesion = new InicioSesionClass()
SelectorCalendarioClass seleccionCalendario = new SelectorCalendarioClass()
@Field SelecccionarCuposClass seleccionarCupos = new SelecccionarCuposClass()
@Field CalcularIteraciones calculoIteracion = new CalcularIteraciones()

// Método para convertir formato de hora (HH:mm -> h:mm a. m./p. m.)
def convertirFormatoHora(String hora) {
	def formatoEntrada = new SimpleDateFormat("HH:mm")
	def formatoSalida = new SimpleDateFormat("h:mm a", new Locale("es", "ES"))
	def fecha = formatoEntrada.parse(hora)
	return formatoSalida.format(fecha)
}

//Eliminacion de la cita
def eliminacionCita(String horaInicio, String horaFin) {
	// Selección de cupo específico
	seleccionarCupos.seleccionarCupoCitaPrevia(horaInicio, horaFin)
	
	int intentos = 3, intentos2 = 3
	boolean exito = false, exito2 = false
	
	while (intentos > 0 && !exito) {
		try {
			
			//posiciona el mouse en el elemento
			WebUI.focus(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cambiar estados'))
			
			WebUI.waitForElementPresent(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cancelado'), 3)
			WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cancelado'), 20)
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/a_Cancelado'))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
			
			exito = true
			
		} catch (Exception e) {
			//Reducir los intentos
			intentos--
			
            WebUI.comment("Error al seleccionar el estado 'cancelar'")
            if (intentos == 0) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
                WebUI.comment("No se pudo seleccionar el link tras varios intentos. Error: " + e.getMessage())
                throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
            }
            WebUI.delay(2)
		}
	}
	
	while (intentos2 > 0 && !exito2) {
		
		try {
			
			WebUI.waitForElementPresent(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_Otro'), 3)
			WebUI.waitForElementVisible(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_Otro'), 20)
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/li_Otro'))
			
			WebUI.click(findTestObject('Object Repository/CambioEstadoCita/Page_MedCloud IDL/button_Guardar'))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
			
			exito2 = true
		} catch (Exception e) {
			//Reducir los intentos
			intentos2--
			
			WebUI.comment("Error al seleccionar el estado 'cancelar'")
			if (intentos2 == 0) {
				String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
				WebUI.comment("No se pudo seleccionar el link tras varios intentos. Error: " + e.getMessage())
				throw new Exception("No se pudo seleccionar el cupo ${horaInicio}-${horaFin} tras múltiples intentos.")
			}
			WebUI.delay(2)
		}
	}
}

// Lógica principal para eliminar citas de 12 horas
def eliminarJornadaCompleta(String horaInicio, String horaFin, int incrementoMinutos) {
	def formatoHora = new SimpleDateFormat("HH:mm") // Formato de 24 horas para cálculos
	def calendar = Calendar.getInstance()
	calendar.setTime(formatoHora.parse(horaInicio)) // Usa el formato de 24 horas
	def totalCitas = calculoIteracion.calcularIteraciones(horaInicio, horaFin, incrementoMinutos)
	
	(1..totalCitas).each { index ->
		// Calcular horario en formato de 24 horas
		def horaActual = calendar.time
		def horaInicio24h = formatoHora.format(horaActual)
		calendar.add(Calendar.MINUTE, 120)
		def horaFin24h = formatoHora.format(calendar.time)
		
		// Convertir a formato de 12 horas para la interfaz
		def horaInicio12h = convertirFormatoHora(horaInicio24h)
		def horaFin12h = convertirFormatoHora(horaFin24h)
		
		// Crear cita
		eliminacionCita(horaInicio12h, horaFin12h)
		
		// Restar 115 minutos para la próxima iteración
		calendar.add(Calendar.MINUTE, -115)
		
		// Pequeña pausa entre citas
		WebUI.delay(3)
	}
}

//apertura de navegacion
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://medcloudpruebas.idl.com.co/medCloud/index.xhtml')

//Inicio de sesion
inicioSesion.inicioSesion()

seleccionCalendario.selectDynamicDate("18")

WebUI.delay(3)

eliminarJornadaCompleta("07:20", "9:10", 5)