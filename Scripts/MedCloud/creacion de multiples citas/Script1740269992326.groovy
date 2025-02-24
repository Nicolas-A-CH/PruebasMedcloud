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
@Field String horaInicioJornada = "8:00"
@Field String baseIdentidad = "10723640"
@Field int totalCitas = 24

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
            if (!WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'), 5)) {
                throw new Exception("El elemento 'Agendar' no es visible.")
            }
            
            if (!WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'), 5)) {
                throw new Exception("El elemento 'Agendar' no es clickable.")
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

// Método para crear cita con parámetros dinámicos
def crearCita(String horaInicio, String horaFin, String numeroIdentidad) {
	// Selección de cupo específico
	seleccionarCupo(horaInicio, horaFin)
	
	// Flujo de creación de cita
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Agendar'))
	
	//Esperar ventana emergente
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_NICOLAS AREVALO'), 10)
	WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_NICOLAS AREVALO'), 20)
	
	// Configuración de tipo de cita
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione'))
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_CIRUGIA PLASTICA-PRIMERA VEZ (120min)'))
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Aceptar'))
	
	//Esperar a que la ventana del formulario cargue
	
	WebUI.delay(3) // Esperar 2 segundos antes de continuar
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'),
		5)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 15)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), 15)
	
	// Rellenado de formulario con datos dinámicos
	WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'), numeroIdentidad)
	WebUI.sendKeys(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewnroI_3c9080'),
		Keys.chord(Keys.TAB))
	
	WebUI.delay(2)
	
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
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58'),
			'luis')
		
		WebUI.sendKeys(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_676a58'),
			Keys.chord(Keys.TAB))
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Nombre_PacienteCrearFormPacie_702494'),
			'nicolas')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewprim_d7f304'),
			'arevalo')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Segundo Apellido_PacienteCrearFormPac_2061f7'),
			'chiquiza')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewcelular'),
			'3134541985')
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno'))
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Masculino'))
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span__ui-button-icon-left ui-icon ui-icon-calendar'))
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewfech_a2cb1e'),
			'2004-01-29')
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Seleccione uno_ui-icon ui-icon-triangl_fd1878'))
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_SANITAS'))
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewemai_4f6d20'),
			'pruebas@gmail.com')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewinEps'),
			'famisanal')
		
		WebUI.check(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Acepta Tratamiento De Datos_ui-chkbox-_5009df'))
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/a_Datos Complementarios'))
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewdireccion'),
			'calle 25 #70-05')
		
		WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoCasadoDivorciadoMenorO_abc012'),
			'class co.idl.medicalcloud.entities.EstadoCivil@6', true)
		
		WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoAdicionalBeneficiarioC_c24d6b'),
			'class co.idl.medicalcloud.entities.TipoAfiliado@2', true)
		
		WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoContributivoDesplazado_13c85f'),
			'class co.idl.medicalcloud.entities.TipoUsuario@1', true)
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/label_Seleccione uno_1'))
		
		WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_Contributivo Cotizante'))
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewacudiente'),
			'adriana')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtele_792a81'),
			'3233205930')
		
		WebUI.selectOptionByValue(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/select_Seleccione unoConyugueHermano(a)Hijo_8cc99f'),
			'class co.idl.medicalcloud.entities.Parentesco@6', true)
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewresponsable'),
			'yo mismo')
		
		WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input__PacienteCrearFormPacienteTabViewtelR_048270'),
			'3233205930')
		
	}
	
	//Envio de formulario
	WebUI.enhancedClick(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Guardar'))
	
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Esta seguro de continuar con la creaci_4d5d74'))
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Cita Agendada con xitoCdigo Cita465346F_3bb5d0'), 
	    5)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Cita Agendada con xitoCdigo Cita465346F_3bb5d0'), 
	    15)
	
	WebUI.delay(4)
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Aceptar'),15)
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Aceptar'),25)
	
	WebUI.waitForElementClickable(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Aceptar'),15)
	
	WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/button_Aceptar'))
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
        calendar.add(Calendar.MINUTE, 30)
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
WebUI.setText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Ver.9.9.5.19022025_ingresoFormfield_user'), 
    'niarevalo')

WebUI.setEncryptedText(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/input_Usuario_ingresoFormfield_password'), 
    'iKK2QhFB4Lt3r+B0vfLvEw==')

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/span_Iniciar sesin'))

//Navegación a la agenda médica
WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/div_Agendas Expandidas                     _5ffbf0_1'))

WebUI.click(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/li_NICOLAS AREVALO'))

WebUI.waitForElementPresent(findTestObject('Object Repository/crecion cita/Page_MedCloud IDL/td_NICOLAS AREVALO                         _bddb2e'), 
    0)

selectDynamicDate('25')

WebUI.delay(3)

// Crear jornada completa
crearJornadaCompleta()

WebUI.closeBrowser()

