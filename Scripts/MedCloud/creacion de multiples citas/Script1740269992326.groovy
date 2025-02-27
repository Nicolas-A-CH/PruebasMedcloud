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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.InicioSesionClass
import utils.SelecccionarCuposClass
import utils.SelectorCalendarioClass

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
@Field String horaInicioJornada = "7:00"
@Field String baseIdentidad = "107236"
@Field int totalCitas = 168

//Intancia de class
InicioSesionClass inicioSesion = new InicioSesionClass()
SelectorCalendarioClass seleccionCalendario = new SelectorCalendarioClass()
@Field SelecccionarCuposClass seleccionarCupos = new SelecccionarCuposClass()

// Método para convertir formato de hora (HH:mm -> h:mm a. m./p. m.)
def convertirFormatoHora(String hora) {
	def formatoEntrada = new SimpleDateFormat("HH:mm")
	def formatoSalida = new SimpleDateFormat("h:mm a", new Locale("es", "ES"))
	def fecha = formatoEntrada.parse(hora)
	return formatoSalida.format(fecha)
}

//Metodo para verificar si los campos del formulario estan vacios o no
def obtenerYVerificarCampo(TestObject testObject) {
	String valorActual = WebUI.getAttribute(testObject, 'value')
	return (valorActual == null || valorActual.trim().isEmpty()) ? "" : valorActual
}

// Método para crear cita con parámetros dinámicos
def crearCita(String horaInicio, String horaFin, String numeroIdentidad) {
	// Selección de cupo específico
	seleccionarCupos.seleccionarCupo(horaInicio, horaFin)
	
	// Flujo de creación de cita
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'))
	
	int intentos3 = 0
	boolean encontrado = false
	
	while (intentos3 < 3 && !encontrado) {
		
		try {
			
			//Esperar ventana emergente
			WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_NICOLAS AREVALO'), 15)
			WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_NICOLAS AREVALO'), 20)
			
			// Configuración de tipo de cita
			WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione'))
			WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_CIRUGIA PLASTICA-PRIMERA VEZ (120min)'))
			WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar'))
			
			WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
			
			encontrado = true
			
		} catch (Exception e) {
			intentos3++
			println("Intento ${intentos3}: Elemento no válido, reintentando...")
			WebUI.delay(2) // Espera antes de reintentar
		}
	}
	
	//Esperar a que la ventana del formulario cargue
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'),
		5)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 15)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 15)
	
	// Rellenado de formulario con datos dinámicos
	WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), numeroIdentidad)
	WebUI.sendKeys(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'),
		Keys.chord(Keys.TAB))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 50)
	
	// Verificar si los campos están autocompletados
	def campoNombre = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58')
	WebUI.waitForElementVisible(campoNombre, 5)
	
	// Obtener el valor del campo de nombre
	String valorNombre = WebUI.getAttribute(campoNombre, 'value')
	
	if (valorNombre?.trim()) {
		
		// Si el campo de nombre ya tiene un valor, el formulario está autocompletado
		println "El formulario está autocompletado. Omitiendo el llenado de campos."
		WebUI.delay(2) // Esperar un momento para asegurar que el sistema termine de autocompletar
		
	} else {
		
		//Seccion de Datos Basicos
		int intentos = 3, intentos2 = 3
		boolean exito = false, exito2 = false
		
		
		while (intentos > 0 && !exito) {
			
			try {
				
				// Obtenemos los TestObjects de cada campo
				TestObject nombreObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58')
				TestObject segundoNombreObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Nombre_PacienteCrearFormPacie_702494')
				TestObject apellidoObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_d7f304')
				TestObject segundoApellidoObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Apellido_PacienteCrearFormPac_2061f7')
				TestObject celularObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewcelular')
				TestObject emailObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewemai_4f6d20')
				TestObject epsObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewinEps')
				
				if (obtenerYVerificarCampo(nombreObj).isEmpty()) {
					WebUI.setText(nombreObj, 'luis')
				}
				
				if (obtenerYVerificarCampo(segundoNombreObj).isEmpty()) {
					WebUI.setText(segundoNombreObj, 'Nicolas')
				}
				
				if (obtenerYVerificarCampo(apellidoObj).isEmpty()) {
					WebUI.setText(apellidoObj, 'Arevalo')
				}
				
				if (obtenerYVerificarCampo(segundoApellidoObj).isEmpty()) {
					WebUI.setText(segundoApellidoObj, 'Chiquiza')
				}
				
				if (obtenerYVerificarCampo(celularObj).isEmpty()) {
					WebUI.setText(celularObj, '3134541985')
				}
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno'))
				
				//Esperar que las opciones desplegables se muestren
				WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'), 5)
				
				if (!WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'), 20)) {
					
					throw new Exception("El elemento 'li_Masculino' no es visible.")
				}
				WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'), 5)
				
				//---------------------------------------------------------------------------------------------------------------------------
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'))
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span__ui-button-icon-left ui-icon ui-icon-calendar'))
				
				WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewfech_a2cb1e'),
					'2004-01-29')
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Seleccione uno_ui-icon ui-icon-triangl_fd1878'))
				
				//Esperar que las opciones desplegables se muestren
				WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'), 5)
				
				if (!WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'), 20)) {
					
					throw new Exception("El elemento 'li_SANITAS' no es visible.")
				}
				WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'), 5)
				
				//---------------------------------------------------------------------------------------------------------------------------
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'))
				
				if (obtenerYVerificarCampo(emailObj).isEmpty()) {
					WebUI.setText(emailObj, 'tohana8332@btcours.com')
				}
				
				WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewinEps'),
					'famisanal')
				
				// Validación final: verificar que todos los campos estén llenos
				if (obtenerYVerificarCampo(nombreObj).isEmpty() ||
					obtenerYVerificarCampo(segundoNombreObj).isEmpty() ||
					obtenerYVerificarCampo(apellidoObj).isEmpty() ||
					obtenerYVerificarCampo(segundoApellidoObj).isEmpty() ||
					obtenerYVerificarCampo(celularObj).isEmpty() ||
					obtenerYVerificarCampo(emailObj).isEmpty() ||
					obtenerYVerificarCampo(epsObj).isEmpty()) {
		
					// Si alguno está vacío, lanzar excepción para que se ejecute el catch
					throw new Exception("No se llenaron todos los campos requeridos.")
				}
				
				WebUI.check(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Acepta Tratamiento De Datos_ui-chkbox-_5009df'))
				
				exito = true
				
			} catch (Exception e) {

				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Complementarios'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Basicos'))
				//Reducir intentos
				intentos--
				
				WebUI.comment("Error en el llenado del formulario datos basicos")
				if (intentos == 0) {
					String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
					WebUI.comment("No se pudo seleccionar el cupo tras varios intentos. Error: " + e.getMessage())
					throw new Exception("No se pudo completar el formulario tras múltiples intentos.")
				}
				WebUI.delay(2)
			}
		}
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Complementarios'))
		
		while (intentos2 > 0 && !exito2) {
			
			//Seccion Datos Completos
			try {
				//Objetos de tipo text
				TestObject dirreccionResidenciaObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewdireccion')
				TestObject nombreAcudienteObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewacudiente')
				TestObject numeroAcudienteObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtele_792a81')
				TestObject nombreResponsableObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewresponsable')
				TestObject celularResponsableObj = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtelR_048270')
				
				//objetos de tpo selectValue
				TestObject estadoCivil = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoCasadoDivorciadoMenorO_abc012')
				TestObject tipoAfiliado = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoAdicionalBeneficiarioC_c24d6b')
				TestObject tipoUsuario = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoContributivoDesplazado_13c85f')
				TestObject parentescoAcudiente = findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoConyugueHermano(a)Hijo_8cc99f')
				
				if (obtenerYVerificarCampo(dirreccionResidenciaObj).isEmpty()) {
					WebUI.setText(dirreccionResidenciaObj, 'calle 25 #70-05')
				}
				
				WebUI.selectOptionByValue(estadoCivil, 'class co.idl.medicalcloud.entities.EstadoCivil@6', true)
				
				WebUI.verifyOptionSelectedByValue(estadoCivil, 'class co.idl.medicalcloud.entities.EstadoCivil@6', false, 10)
				
				WebUI.selectOptionByValue(tipoAfiliado, 'class co.idl.medicalcloud.entities.TipoAfiliado@2', true)
				
				WebUI.verifyOptionSelectedByValue(tipoAfiliado, 'class co.idl.medicalcloud.entities.TipoAfiliado@2', false, 10)
				
				WebUI.selectOptionByValue(tipoUsuario, 'class co.idl.medicalcloud.entities.TipoUsuario@1', true)
				
				WebUI.verifyOptionSelectedByValue(tipoUsuario, 'class co.idl.medicalcloud.entities.TipoUsuario@1', false, 10)
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno_1'))
				
				//Esperar que las opciones desplegables se muestren
				WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'), 5)
				
				if (!WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'), 20)) {
					
					throw new Exception("El elemento 'li_SANITAS' no es visible.")
				}
				WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'), 5)
				
				//---------------------------------------------------------------------------------------------------------------------------
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'))
				
				if (obtenerYVerificarCampo(nombreAcudienteObj).isEmpty()) {
					WebUI.setText(nombreAcudienteObj, 'adriana')
				}
				
				if (obtenerYVerificarCampo(numeroAcudienteObj).isEmpty()) {
					WebUI.setText(numeroAcudienteObj, '3233205930')
				}
				
				WebUI.selectOptionByValue(parentescoAcudiente, 'class co.idl.medicalcloud.entities.Parentesco@6', true)
				
				if (obtenerYVerificarCampo(nombreResponsableObj).isEmpty()) {
					WebUI.setText(nombreResponsableObj, 'yo mismo')
				}
				
				if (obtenerYVerificarCampo(celularResponsableObj).isEmpty()) {
					WebUI.setText(celularResponsableObj, '3231456987')
				}
				
				// Validación final: verificar que todos los campos estén llenos
				if (obtenerYVerificarCampo(dirreccionResidenciaObj).isEmpty() ||
					obtenerYVerificarCampo(nombreAcudienteObj).isEmpty() ||
					obtenerYVerificarCampo(numeroAcudienteObj).isEmpty() ||
					obtenerYVerificarCampo(nombreResponsableObj).isEmpty() ||
					obtenerYVerificarCampo(celularResponsableObj).isEmpty())
					{
		
					// Si alguno está vacío, lanzar excepción para que se ejecute el catch
					throw new Exception("No se llenaron todos los campos requeridos.")
				}
				exito2 = true
				
			} catch (Exception e) {
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Basicos'))
				
				WebUI.delay(2)
				
				WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Complementarios'))
				
				//Reducir intentos
				intentos--
				
				WebUI.comment("Error en el llenado del formulario datos basicos")
				if (intentos == 0) {
					String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
					WebUI.comment("No se pudo seleccionar el cupo tras varios intentos. Error: " + e.getMessage())
					throw new Exception("No se pudo completar el formulario tras múltiples intentos.")
				}
				WebUI.delay(2)
			}
		}
	}
	
	//Envio de formulario
	WebUI.enhancedClick(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Guardar'))
	
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Esta seguro de continuar con la creaci_4d5d74'))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar_Resumen_Cita'),15)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar_Resumen_Cita'),25)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar_Resumen_Cita'),15)
	
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar_Resumen_Cita'))
	
	WebUI.waitForElementNotVisible(findTestObject('Object Repository/elementos espontaneos/Page_MedCloud IDL/spin de carga'), 300)
}

// Lógica principal para crear citas de 12 horas
def crearJornadaCompleta() {
    def formatoHora = new SimpleDateFormat("HH:mm") // Formato de 24 horas para cálculos
    def calendar = Calendar.getInstance()
    calendar.setTime(formatoHora.parse(horaInicioJornada)) // Usa el formato de 24 horas
    
    (1..totalCitas).each { index ->
        // Calcular horario en formato de 24 horas
        def horaActual = calendar.time
        def horaInicio24h = formatoHora.format(horaActual)
        calendar.add(Calendar.MINUTE, 5)
        def horaFin24h = formatoHora.format(calendar.time)
        
        // Convertir a formato de 12 horas para la interfaz
        def horaInicio = convertirFormatoHora(horaInicio24h)
        def horaFin = convertirFormatoHora(horaFin24h)
        
        // Generar identificación única
        def numeroIdentidad = "${baseIdentidad}${index}"
        
        // Crear cita
        crearCita(horaInicio, horaFin, numeroIdentidad)
        
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

seleccionCalendario.selectDynamicDate("28")

WebUI.delay(3)

// Crear jornada completa
crearJornadaCompleta()