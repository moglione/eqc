Type=Activity
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
    Dim urlFromJS As String =""
	Public notas() As String	
	
	Public progreso As Int =0
	Public actualizarTodo As Boolean = False
	Dim TapaVisible As Boolean=True
	Dim notActivas As String ="si"
		
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
    
	
	
	
	'############################################################
	'Los cuatro webview que se usan
	'############################################################
		Dim web(3) As WebView
	
	
	'############################################################
	'los contenedores de los webviews 
	'############################################################
		Dim contenedor(3) As Panel
	
   	
	'############################################################
	' El contenedor del menu 
	'############################################################
		Dim Mcontainer As Panel
	
	
	'############################################################
	'El pager que muestra a los distintos webviews 
	'el contenedor de paginas y el objecto ViewPager 
	'############################################################
		Dim container As AHPageContainer
		Dim pager As AHViewPager
		Dim panelimpresa As Panel
		
		
	'############################################################
	'El pager que muestra las distintas secciones:
	'Tapa, ovacion, policiales, etc 
	'############################################################
		Dim containerNotas As AHPageContainer
		Dim pagerNotas As AHViewPager	
		
		
	
	
	'############################################################
	'Las actionbar de cada instancia de navegacion
	'############################################################
		Dim barra(4) As Panel
		
	'############################################################
	'Elementos de la actionbar	
	'############################################################
		Dim Linea(4) As Panel
		Dim blogo(4) As Button
		Dim lFecha(4) As Label
		Dim linea2(4) As Panel
		
		Dim bVolver As Button
		Dim bFontChange As Button
				
		Dim bMenu As Button
		Dim brefresh As Button
		Dim boverflow As Button
	
	
	'############################################################
	'Elementos de pantalla de splash y about 	
	'############################################################
		Dim panelSplash As Panel
		Dim etiqueta As Label
		Dim ProgressBar1 As ProgressBar
		Dim panelabout As Panel
		Dim botonAbout As Button
		
	'############################################################
	'Banderas de uso general
	'############################################################
	    Dim SplashActiva As Boolean=False
		
		
	'############################################################
	' variables de uso general
	'############################################################	
	    Dim paginaCargada As String
		Dim numeroNota As String
		
	'############################################################
	'El objeto que maneja la conexion con el servidor
	'############################################################
	    Dim con As conexion
		
	'############################################################
	' Los dos menues del sistema
	'############################################################
	   
		Dim overflowMenu As setMenu
		
		
	
	'############################################################
	'Interface con javascript
    '############################################################
		Dim extras As WebViewExtras
	
	'############################################################
	'Fechas para mostrar
    '############################################################
		Dim fechaTapa As String
		Dim fechaAdentro As String
		
	'############################################################
	'para la implementacion del carrousel de notas
    '############################################################
	
		Dim pagFijas As Int=3
	    Dim notaInicio As Int=0
	    Dim offset As Int
	  
	    Dim paginaActual As Int=1
		Dim paginaAnterior As Int=0
		Dim movimiento As String="derecha"
	    Dim gap As Int =20dip
		
		Dim webActual As WebView
		Dim panelActual As Panel
	
    '############################################################
	'el cartel de actualizando
    '############################################################
	 Dim panelcolor(2) As Panel
	 Dim ciclo As Int =0
	 
	'############################################################
	'Para el pull to refresh
    '############################################################
 	 Dim g As Gestures
	 Dim Ylanding As Int
	 Dim deltaY As Int
	 Dim Xlanding As Int
	 Dim deltaX As Int
	 Dim webextendida As WebViewXtender
	 Dim pull As Boolean =False
	 Dim avisoRefresh As Label
	 Dim rotacion As Boolean =False
	 Dim captionRefresh As Label
	 
	'############################################################
	'Para el menu pull down de las notas
    '############################################################
	  Dim g2 As Gestures
		  
	   
	'############################################################
	'Para leer en voz alta
    '############################################################
	  
	  Dim TTS1 As TTS

	'############################################################
	'Para capturar la imagen y compartir
    '############################################################ 
	Type Data (Canvas As Canvas, panel As Panel,Bitmap As Bitmap)
	
	
	'############################################################
	'Para controlar el flujo del TTS (text to spech)
	'############################################################
	 Dim panelTTs As Panel
	 Dim botonPararTTS As Button
	 Dim botonPausarTTS As Button
	 Dim BotonPlayTTS As Button
	 Dim hablando As Boolean=False
	 Dim PausaHablando As Boolean=False
	 Dim webAnterior As String
	 
	 Dim a As AnimationPlus
	 
	Dim cantidadClicks As Int=0
	
End Sub

Sub Activity_Create(FirstTime As Boolean)

    Log( "Activity_create")
	
	
	
	notActivas=StateManager.GetSetting ("notActivas")
	
	If notActivas ="" Then
		notActivas="si"
		StateManager.SetSetting("notActivas", "si" )
		StateManager.SaveSettings
	End If
	
	
	If notActivas="no" Then ToastMessageShow("Notificaciones desactivadas", False)
	If notActivas="si" Then ToastMessageShow("Notificaciones Activadas", False)
	
	
	
	
	offset=notaInicio-(pagFijas-1)
	
	dibujarLayout
	splashScreen(True)
	
	
	
	'--------------------------------------------
	' aca arranca el servicio de actualizacion
	' automatico y notificacion
	'--------------------------------------------
    If servicioActivo = False Then StartService(ServicioD)	
	
	
	crearFecha
	con.Initialize
	con.cargar("elquid.html", web(0))

	

End Sub

Sub Activity_Resume

  If TTS1.IsInitialized = False Then TTS1.Initialize("TTS1")
  TTS1.SpeechRate=0.9
  TTS1.Pitch=1
  TTS1.SetLanguage("es","")
 
   

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

'############################################################################################################
' SplashScreen
'############################################################################################################

Sub splashScreen (encendida As Boolean)
    Log( "SplashScreen")
   
	If encendida=True Then
	        Main.splashDone=True
		    ProgressBar1.Initialize("")
			etiqueta.Initialize("")
			panelSplash.Initialize("")
			panelSplash.SetBackgroundImage (LoadBitmap (File.DirAssets,"lacapitalsplash.jpg"))
			ProgressBar1.Indeterminate=True
			panelSplash.AddView(ProgressBar1,50%x - 35dip ,80%y - 35dip,70dip,70dip)
			panelSplash.AddView(etiqueta,0,100%y-40dip,100%x,40dip)
			etiqueta.Gravity=Gravity.CENTER_HORIZONTAL 
			etiqueta.TextSize=18
			etiqueta.Text="(c) 2017"
			Activity.AddView(panelSplash,0,0,100%x,100%y)	
			panelSplash.Visible=True
			panelSplash.BringToFront
			SplashActiva=True
			
	Else
	   	panelSplash.Visible=False
		SplashActiva=False
				
		
		Sleep(500)
		Sleep(500)
		Sleep(500)
		brefresh_Click
		
	End If 	
End Sub

'############################################################################################################
' DibujarLayout
'############################################################################################################

Sub dibujarLayout
    Log( "dibujarlayout")

    Dim paneles(3) As Panel
	paneles(0).Initialize("")
	paneles(1).Initialize("")
	paneles(2).Initialize("")
	
    Dim paneltapa As Panel
    
	container.Initialize
	
	'************************************************
	'Inicializacion de los webview
	'y los contenedores
	'************************************************
	For n=0 To 2
		web(n).Initialize("web")
		web(n).ZoomEnabled= False
		web(n).Tag=n
		barra(n).Initialize("")
		Linea(n).Initialize("")
		contenedor(n).Initialize("")
		
	Next 
	
	Mcontainer.Initialize("")
	
	'************************************************
	' Para ver la tapa impresa
	'************************************************
	paneltapa.Initialize("")
	paneltapa.Color=Colors.DarkGray
	
	panelimpresa.Initialize("")
	panelimpresa.color=Colors.Yellow
	
	paneltapa.AddView(panelimpresa, 0, 30dip, 100%x, 1.38 * 100%x)
	
	
	
	
    '************************************************
	' Se agregan los webviews a los contenedores 
	'************************************************
	contenedor(0).AddView(web(0), 0,60dip, 100%x, 100%y-60dip) 
	
	
'	La segunda hoja a la derecha
	contenedor(1).SetBackgroundImage(LoadBitmap(File.DirAssets, "otros.jpg"))
	

     paneles(0).AddView(paneltapa,0,0,100%x,100%y)
	 paneles(1).AddView(contenedor(0),0,0,100%x,100%y)
	 paneles(2).AddView(contenedor(1),0,0,100%x,100%y)
	 
	
	'************************************************
	' Se agregan los contenedores al contenedor
	' del pager
	'************************************************
	
	container.AddPage(paneles(0),"menu")
	container.AddPage(paneles(1),"Tapa")
	container.AddPage(paneles(2),"nota")
    
		
		
	'************************************************
	' Se inicializa el pager con el contenedor
	'************************************************
	pager.Initialize(container, "Pager")
	pager.Color=Colors.Black
	pager.PagingEnabled=False
	
	
	'************************************************
	' Se agrega el pager a la actividad
	'************************************************
	Activity.AddView(pager,0,0,100%x,100%y)
	
	 
	'************************************************
    '  Actiion bar  principal
	'************************************************
	lFecha(0).Initialize("")
	linea2(0).Initialize("")
	
	boverflow.Initialize("boverflow")
	brefresh.Initialize("brefresh")
	
	linea2(0).Color=Colors.ARGB(240,50,50,50)
	Linea(0).Color=Colors.ARGB(240,50,50,50)
	
	
	
	barra(0).Color= Colors.ARGB(255,239,239,239)
	blogo(0).Initialize("")
	blogo(0).SetBackgroundImage(LoadBitmap (File.DirAssets,"logonegro.png"))
	
	boverflow.SetBackgroundImage(LoadBitmap (File.DirAssets,"menu.png"))
	brefresh.SetBackgroundImage(LoadBitmap (File.DirAssets,"refresh.png"))
	
	barra(0).AddView(boverflow,Activity.Width-35dip,14dip,32dip,32dip)
	barra(0).AddView(brefresh,Activity.Width-65dip,18dip,25dip,25dip)
'	barra(0).AddView(blogo(0),5dip,5dip,222dip,40dip)
	barra(0).AddView(blogo(0),5dip,5dip,277dip,50dip)
  
	contenedor(0).AddView(barra(0),0,0,100%x,60dip)
   
	
	
	
	'************************************************
    '  Cartel actualiando
	'************************************************
	panelcolor(0).Initialize("")
	panelcolor(1).Initialize("")
	contenedor(0).AddView(panelcolor(0),0,61dip,100%x,19dip)
	contenedor(0).AddView(panelcolor(1),0,61dip,100%x,19dip)
	
	panelcolor(0).Color=Colors.Blue
	panelcolor(1).Color=Colors.Blue
	
	panelcolor(0).Visible=False
	panelcolor(1).Visible=False
	
	
	
	
	'************************************************
	'  Se crea el menu overflow (derecho) 
	'************************************************
	overflowMenu.Initialize(Activity, Me, "overflowMenu", 15dip, 220dip)
	overflowMenu.AddItem("Ir a la web de EQC")
	overflowMenu.AddItem("Acerca de...")
	If notActivas= "no" Then overflowMenu.AddItem("Activar Notificaciones")
	If notActivas= "si" Then overflowMenu.AddItem("Silenciar Notificaciones")
	overflowMenu.AddItem("Salir")
	
			
	
	
	'************************************************
	'  Se crea el panel TTS
	'************************************************
	panelTTs.Initialize("")
	panelTTs.Color=Colors.Cyan
	Activity.AddView(panelTTs,0,Activity.Height-100dip,100%x,100dip)
	
	botonPararTTS.Initialize("pararTTS")
	botonPausarTTS.Initialize("pausarTTS")
	
	
	botonPararTTS.SetBackgroundImage(LoadBitmap (File.DirAssets,"stop.png"))
	botonPausarTTS.SetBackgroundImage(LoadBitmap (File.DirAssets,"pause.png"))

	
	panelTTs.AddView(botonPararTTS,0,0,70dip,70dip)
	panelTTs.AddView(botonPausarTTS,0,0,70dip,70dip)
	
	
	
	botonPararTTS.Top= (panelTTs.Height -  botonPararTTS.Height)/2
	botonPausarTTS.Top=botonPararTTS.Top
	
	botonPausarTTS.left=(panelTTs.Width/2)+20dip
	botonPararTTS.left=(panelTTs.Width/2)-20dip-botonPararTTS.Width
	
	
	
	panelTTs.Visible=False
	'********** INTERFACE JAVASCRIPT  *************** 
	 extras.addJavascriptInterface(web(0),"B4A")
	
	
	'************************************************
	' Se agrega el pager de las notas
	'************************************************
	containerNotas.Initialize
	pagerNotas.Initialize(containerNotas,"pagerNotas")
	Activity.AddView(pagerNotas,0,0,100%x,100%y)
	pagerNotas.visible=False
	pagerNotas.Color=Colors.LightGray	 
	pager.GotoPage(1,True)
	
	
		
	'************************************************
	'  pull to refresh
	'************************************************
	avisoRefresh.Initialize("")
	avisoRefresh.SetBackgroundImage(LoadBitmap(File.DirAssets, "flechadown.png"))
	contenedor(0).AddView(avisoRefresh,0,70dip,80dip, 80dip)
'	avisoRefresh.Gravity=Bit.OR(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL) 
	avisoRefresh.SendToBack
	avisoRefresh.Visible=True
	
	captionRefresh.Initialize("")
	captionRefresh.Text="Suelte ahora para actualizar."
	captionRefresh.TextColor=Colors.White
	captionRefresh.TextSize=20
	contenedor(0).AddView(captionRefresh,0,0,100%x, 40dip)
	captionRefresh.Gravity=Gravity.CENTER_HORIZONTAL 
	captionRefresh.SendToBack
	captionRefresh.Visible=False
		 
	panelabout.Initialize("panelabout")
	panelabout.SetBackgroundImage(LoadBitmap(File.DirAssets, "about.jpg"))
	panelabout.Visible=False
	
	
	botonAbout.Initialize("botonAbout")
	botonAbout.Color=Colors.ARGB(0,255,255,255)
	panelabout.AddView(botonAbout,0,0,100%x,20%y)
	
	
	Activity.AddView(panelabout,0,0,100%x,100%y)
	
	
	g.SetOnTouchListener(web(0) , "PullToRefresh")

End Sub

Sub pararTTS_Click
	
	
	panelTTs.Visible=False
	pagerNotas.PagingEnabled=True
	
		
	Sleep(200)	
	TTS1.Stop
	
	hablando=False
	PausaHablando=False
	
End Sub

Sub pausarTTS_Click
	
'	If webAnterior <> webActual.tag Then
'	
'	  Log("siii cambios")
'	  webAnterior=webActual.tag
'	  If PausaHablando=True Then leerVozAlta
'	
'	End If
	
	PausaHablando=Not(PausaHablando)
	If PausaHablando=True Then botonPausarTTS.SetBackgroundImage(LoadBitmap (File.DirAssets,"play.png"))
	If PausaHablando=False Then botonPausarTTS.SetBackgroundImage(LoadBitmap (File.DirAssets,"pause.png"))
End Sub






Sub web_OverrideUrl (url As String) As Boolean
   
   Log( "web_OverrideUrl")

   Dim partes() As String
   Dim cargar  As String
   
   Log(url)
   
   Dim posperiod = url.IndexOf(".")
   numeroNota=url.SubString2(8,posperiod)
   Log("click en= " & numeroNota )
  
   If IsNumber(numeroNota) Then notaInicio=numeroNota-1
   offset=notaInicio-(pagFijas-1)
   
   
   
   If url= "file:///volver.html" Then
	VolverTapa
	 Return True
   End If
   
   
   
   If url= "file:///android_asset/recargar" Then
		If pager.CurrentPage=1 Then con.cargar("elquid.html", web(0))
     Return True
   End If
   
  
   If pager.CurrentPage=1 Then
      
        'If paginaCargada <> cargar Then  con.cargar(cargar, web(1))
      	'pager.GotoPage(numeroNota+1,True)
		If IsNumber (numeroNota) Then cargarNota(numeroNota)
		
	
        
   End If

'    actualPage=url.SubString(8)
Return True

End Sub


Sub web_PageFinished (url As String) 

   Log("web_PageFinished")
	
	If SplashActiva = True Then
	  	splashScreen(False)
		lFecha(0).Text=fechaTapa
		crearPaginasNotas
    End If	

 
End Sub




Sub crearPaginasNotas
    
	Dim dirCache As String 
	dirCache= File.DirInternal & "/cachelc" 
    'If File.Exists(dirCache, "tapa.jpg") Then panelimpresa.SetBackgroundImage(LoadBitmap (dirCache,"tapa.jpg"))
 
   'Primero se borran las paginas anteriores 
   If containerNotas.Count > 0 Then
	   For n= containerNotas.Count-1  To 0 Step -1
		    containerNotas.DeletePage(n)
	   Next
   End If	

   'ahora se agregan las paginas
   For n= 0 To notas.Length-1 
	    agregarPagina (n)
	Next
End Sub



Sub web_LongClick 
	'esta funcion del WebView no esta documentada
	'pero permite evitar que aparezca la
	'contextual action bar
	Return True
End Sub


'##############################################################
'SUB QUE ES LLAMADA DESDE EL JAVASCRIPT QUE ESTA CARGADO
'EN EL html DEL WebView
'##############################################################
Sub fromJavascript ( urlstring As String)

     Log("fromJavascript")
	 
     Dim partes() As String
     partes = Regex.Split("/", urlstring)
     urlFromJS=partes(partes.Length-1)
	 numeroNota=partes(partes.Length-2)
	 If IsNumber(numeroNota) Then notaInicio=numeroNota-1
	 offset=notaInicio-(pagFijas-1)
	 	 
     '''''If paginaCargada <> urlFromJS Then con.cargar(urlFromJS,web(1))
     '''''paginaCargada=urlFromJS
     ''''lFecha(2).Text="NOTA DE TAPA "& numeroNota & "  "
     '''''lFecha(1).Text= "  " & fechaAdentro 
	 
End Sub

Sub panelabout_Click
  panelabout.Visible=False
	cantidadClicks=0
End Sub

'##############################################################
'# EVENTOS BOTONES ACTION BAR Y MENUES
'##############################################################


Sub bMenu_Click
  Dim n As Int
  n=pager.CurrentPage-1
  pager.GotoPage(n, True)	 
End Sub

Sub boverflow_Click
      overflowMenu.show
End Sub


Sub brefresh_Click
	
	
	a.InitializeRotateCenter("",0,360,brefresh)
	a.Duration=500
	a.RepeatCount=-1
	a.Start(brefresh)
	actualizarAll
End Sub

Sub menuCentral_Click
    VolverTapa  
End Sub

Sub overflowMenu_Click(Item As Object)
	
	
	Select Item
		
	   
	Case "Silenciar Notificaciones"
	  	
			silenciarNotificaciones
			
	 Case "Activar Notificaciones"
	  	
			silenciarNotificaciones
					
	  
	  Case "Ir a la web de EQC"
			Dim p As PhoneIntents
			StartActivity(p.OpenBrowser("http://elquiddelacuestion.com.ar/"))
	  
	  Case "Acerca de..."
		 panelabout.SetBackgroundImage(LoadBitmap(File.DirAssets, "about.jpg"))
	     panelabout.Visible=True
		 panelabout.BringToFront    
    
	   
	   Case "Salir"
	   	TTS1.Release
			Activity.Finish
	      ExitApplication
		  
	   Case Else
	   
	End Select
	
End Sub

Sub silenciarNotificaciones
	If notActivas= "si" Then StateManager.SetSetting("notActivas", "no" )
	If notActivas= "no" Then StateManager.SetSetting("notActivas", "si" )
	StateManager.SaveSettings
	notActivas=StateManager.GetSetting ("notActivas")
	
	overflowMenu.limpiar
	
	
	
	overflowMenu.AddItem("Ir a la web de EQC")
	overflowMenu.AddItem("Acerca de...")
	If notActivas= "no" Then overflowMenu.AddItem("Activar Notificaciones")
	If notActivas= "si" Then overflowMenu.AddItem("Silenciar Notificaciones")
	overflowMenu.AddItem("Salir")
	

End Sub

'#################################################################################################################
'#### EVENTOS DEL PAGER   ########################################################################################
'#################################################################################################################

'This event gets called when the page has changed. Be aware that it is possible that scrolling is not
'finished at this moment
Sub pager_PageChanged (Position As Int)

Log("pager_PageChanged " & Position)

Dim posicion As Int
Dim diferencia As Int

paginaActual=Position
posicion=Position+1



'determinar para donde se movio: izquierda o derecha
If paginaAnterior < paginaActual Then movimiento="derecha"
If paginaAnterior >paginaActual Then movimiento="izquierda"
paginaAnterior=paginaActual





'--------------------------------
'el separador negro desaparece
'--------------------------------
 Dim animacion As AnimationPlus
 
 
 
 

 Dim pan As Panel
 Dim pan2 As Panel
 pan=container.GetPageObject(Position)
 pan2=pan.GetView(0)
  
 pan2.Left=0
 If movimiento= "izquierda" Then animacion.InitializeTranslate("anim",-gap,0,0,0)
 If movimiento= "derecha" Then animacion.InitializeTranslate("anim",gap,0,0,0)
 animacion.Duration=400
 animacion.Start(pan2)
 
 
  
 If paginaActual = 0  Then
  pan=container.GetPageObject(1)
  pan2=pan.GetView(0)
  pan2.Left=gap
 End If
 
 If paginaActual =(container.Count-1)Then
  pan=container.GetPageObject(container.Count-2)
  pan2=pan.GetView(0)
  pan2.Left=-gap
 End If
 


End Sub


Sub anim_AnimationEnd



End Sub

'This event gets called when the ViewPager instantiates the page. In this sub you can update the content
'of the page or even load a layout.
'Have a look in the logs to see when Pager_PageCreated and Pager_PageDestroyed are called.
Sub pager_PageCreated (Position As Int, Page As Object)

Log("pager_PageCreated " & Position)

'---------------------------
'el separador negro
'---------------------------

 Dim pan As Panel
 Dim pan2 As Panel

 If paginaActual>=1 Then
  pan=container.GetPageObject(paginaActual-1)
  pan2=pan.GetView(0)
  pan2.Left=-gap
 End If

 If paginaActual < (container.Count-1) Then
  pan=container.GetPageObject(paginaActual+1)
  pan2=pan.GetView(0)
  pan2.Left=gap
 End If
 

 
End Sub
 



'##############################################################

'This event gets called when the ViewPager destroys the page. 
'Here you can free large bitmaps etc To save memory.
Sub pager_PageDestroyed (Position As Int, Page As Object)
  Log("pager_PageDestroyed " & Position)

 
End Sub

'##############################################################

Sub servicioActivo () As Boolean
Dim sActivo As Boolean
sActivo=False

If File.Exists(File.DirInternal, "servicioActivo.txt") Then sActivo=True	
If IsPaused(ServicioD)=False Then sActivo=True

Return sActivo

End Sub



'##############################################################

Sub crearFecha
  
  Log("crearFecha")
	
  Dim d As Int
  Dim m As Int
  Dim year As Int
  Dim dia As String
  Dim mes As String
  
 
   
  d= DateTime.GetDayOfWeek(DateTime.Now)
  Select d
	  Case 1
		  dia="Domingo"
	  Case 2
		  dia="Lunes"
	  Case 3
		  dia="Martes"
	  Case 4
		  dia="Miércoles"
	  Case 5
		  dia="Jueves"
	  Case 6
		  dia="Viernes"
  	  Case 7
		  dia="Sábado"
   End Select

  m= DateTime.GetMonth(DateTime.Now)
  Select m
	  Case 1
		  mes="Enero"
	  Case 2
		  mes="Febrero"
	  Case 3
		  mes="Marzo"
	  Case 4
		  mes="Abril"
	  Case 5
		  mes="Mayo"
	  Case 6
		  mes="Junio"
  	  Case 7
		  mes="Julio"
	  Case 8
		  mes="Agosto"
	  Case 9
		  mes="Septiembre"
	  Case 10
		  mes="Octubre"
	  Case 11
		  mes="Noviembre"
	  Case 12
		  mes="Diciembre"
	End Select
  
   d=DateTime.GetDayOfMonth(DateTime.Now)
   year=DateTime.GetYear(DateTime.Now)
   fechaTapa= dia & " " & d & " " & mes & " " & year
   
   DateTime.DateFormat="dd/MM/yyyy"
   fechaAdentro=DateTime.Date(DateTime.Now)

End Sub

Sub agregarPagina (posicion As Int)

    Log("agregarPagina " & posicion & " --> " & container.count)
	
	Dim pan As Panel
	Dim cont As Panel
	Dim webv As WebView
	Dim botonLetras As Button
	Dim botonLeer As Button
	Dim botonCompartir As Button
	
	Dim fecha1 As Label
	Dim fecha2 As Label
	Dim lineaUno As Panel
	Dim lineaDos As Panel
	Dim barraL As Panel
	Dim botonLogo As Button
	
	Dim blackLine As Panel
	
'--------------------------------------------------------------------

	'************************************************
    '  Action bar notas
	'************************************************
	
	
	botonLetras.Initialize("cambiarFont")
	botonLetras.SetBackgroundImage(LoadBitmap (File.DirAssets,"fontchange.png"))
	
	
	botonLeer.Initialize("leerTexto")
	botonLeer.SetBackgroundImage(LoadBitmap (File.DirAssets,"vozalta.png"))
	
	botonCompartir.Initialize("compartirNoticia")
	botonCompartir.SetBackgroundImage(LoadBitmap (File.DirAssets,"compartir.png"))
	
	botonLogo.Initialize("")
	botonLogo.SetBackgroundImage(LoadBitmap (File.DirAssets,"logonegro.png"))
	
	
	
	
	fecha1.Initialize("")
	fecha2.Initialize("")
  	lineaUno.Initialize("")
	lineaDos.Initialize("")
	
	
	
	
	
	barraL.Initialize("")
	barraL.Color= Colors.ARGB(255,239,239,239)
	
	
	
    barraL.AddView(botonLetras,Activity.Width-38dip,7dip,34dip,34dip)
	barraL.AddView(botonLeer,Activity.Width-74dip,7dip,32dip,32dip)
	barraL.AddView(botonCompartir,Activity.Width-112dip,7dip,32dip,32dip)

    barraL.AddView(botonLogo,5dip,5dip,222dip,40dip)
	
	
	

    blackLine.Initialize("")
	
  
'--------------------------------------------------------------------
	
	cont.Initialize("")
	pan.Initialize("")
	webv.Initialize("web")
	webv.ZoomEnabled=False
	
	
	
	pan.AddView(webv,0,50dip, 100%x, 100%y-50dip)
	
 	pan.AddView(barraL,0,0,100%x,50dip)
	
	
	pan.AddView(blackLine,0,0,100%x,1)
	
	
	
    
	cont.AddView (pan,0,0, 100%x, 100%y) 
	containerNotas.AddPage(cont,"")
    con.cargar(notas(posicion),webv)
	
	webv.Tag=notas(posicion)
	
End Sub

Sub anterior_Click
   VolverTapa
End Sub

Sub cambiarFont_Click
    '	agrandar / achicar tipografia      
   webActual.LoadUrl("javascript:resizeText(1);")
End Sub

Sub leerTexto_Click
	'ToastMessageShow("Leer la nota en voz alta...", False)
	pagerNotas.PagingEnabled=False
	panelTTs.Visible=True
	panelTTs.BringToFront
	leerVozAlta
	
End Sub


Sub compartirNoticia_Click
'	PanelCapture(panelActual)
	
	ToastMessageShow("Compartir esta nota...", True)
	
	Dim Out As OutputStream
	Out = File.OpenOutput(File.DirRootExternal, "compartir.jpg", False)
	webActual.CaptureBitmap.WriteToStream(Out, 100, "JPEG")
	Out.Close
	
	Dim share As MESShareLibrary
	share.sharebinary("file://" & File.DirRootExternal & "/compartir.jpg", "image/jpg", "Do you see this?", "Mira esta nota que salio en El Quid de la cuestión (http://www.elquiddelacuestion.com.ar)")

  

	
End Sub





Sub cargarNota (numero As Int)
     
	 Dim anim As AnimationPlus
	 Dim anim2 As AnimationPlus
	 
	 pagerNotas.top=0
	 pagerNotas.BringToFront
	 pagerNotas.Visible=True
	 pagerNotas.GotoPage(numero-1,False)
	 
	 
	 
	 Dim pan As Panel
	 Dim pan2 As Panel
	 pan=containerNotas.GetPageObject(numero-1)
	 pan2=pan.GetView(0)
	 webActual=pan2.GetView(0)
	 panelActual=pan2
	
	 g2.SetOnTouchListener(pagerNotas , "GestosNotas")
	 g2.SetOnTouchListener(webActual , "GestosNotas")
	 
	
	
	
	 pager.Top=0
	 
	 anim2.InitializeScaleCenter("", 1, 1, 0, 0, pager)
'	 anim2.InitializeTranslate("",0,0,0,-Activity.Height)
'	 anim2.SetInterpolator(anim.INTERPOLATOR_OVERSHOOT)
	 anim2.PersistAfter=True
	 anim2.Duration=500
	 anim2.Start(pager)

	
	
     anim.InitializeTranslate("animacionNotas",0,Activity.Height,0,0)
'	 anim.SetInterpolator(anim.INTERPOLATOR_BOUNCE)
	 anim.Duration=500
	 anim.Start(pagerNotas)
 
	 TapaVisible=False
	 
End Sub






Sub Activity_KeyPress (KeyCode As Int) As Boolean
    'We capture the  back keys
		
	If KeyCode = KeyCodes.KEYCODE_BACK  Then
        If TapaVisible=False Then 
			VolverTapa
			Return True
		Else
		 	
			Dim result As Int
			result = Msgbox2("¿Quiere salir de EL Quid de la cuestión?", "Salir", "SI", "", "NO", Null)
			If result = DialogResponse.Positive Then 
				Activity.Finish
				ExitApplication
			End If
			
			If result = DialogResponse.NEGATIVE Then Return True
		End If
		
	 	
	End If
		
	
	'Pressing the menu key will open/close the slidemenu
	If KeyCode = KeyCodes.KEYCODE_MENU Then
		pager.GotoPage(0,True)
		Return True
	End If
	
End Sub


Sub Sleep(ms As Long)
Dim now As Long
   If ms > 1000 Then ms =1000   
   now=DateTime.now
   Do Until (DateTime.now>now+ms)
     DoEvents
   Loop
End Sub


Sub actualizarProgreso

	 If progreso > 1 Then
'	    cicladocolor (True)
       
	 Else	
'	   cicladocolor (False)
	   actualizarTodo=False
	   a.Stop(brefresh)
	   crearPaginasNotas
	 End If

End Sub


Sub cicladocolor ( encendido As Boolean)

 Dim colorBarra As Int
 

 If encendido Then
        lFecha(0).Text="Descargando ultimas noticias..."
		panelcolor(0).Width=0
		panelcolor(1).Width=0
		panelcolor(0).Visible=True
		panelcolor(1).Visible=True
		panelcolor(ciclo).BringToFront 
		panelcolor(ciclo).Color=Colors.ARGB(40,Rnd(0,255),Rnd(0,255),Rnd(0,255))
		For n= 0 To Activity.Width Step Activity.Width /50
		  panelcolor(ciclo).Width=n
		  panelcolor(ciclo).Left=50%x - (n/2)
		  DoEvents
		Next
		If ciclo=0 Then ciclo=1 Else ciclo=0
		
 End If

 If encendido = False Then
        lFecha(0).Text=fechaTapa
		panelcolor(0).Width=0
		panelcolor(1).Width=0
		panelcolor(0).Visible=False
		panelcolor(1).Visible=False
End If

End Sub
Sub BorrarCache
	Dim MyList As List
	MyList.initialize
	Dim MyFile As String
	MyList=File.ListFiles(File.DirInternal & "/cachelc")
	For i= MyList.Size-1 To 0 Step -1
	   MyFile=MyList.Get(i)
	   File.Delete(File.DirInternal & "/cachelc",MyFile)
	Next
End Sub


Sub actualizarTapa
	con.cargar("elquid.html", web(0))

End Sub

Sub PullToRefresh ( o As Object, ptrID As Int, action As Int, x As Float, y As Float) As Boolean
	
     Dim posy As Int
	 Dim scrollY As Int
	
     'si se esta actualizando no se permite re-entrar
	 If actualizarTodo=True Then Return	
    
 	 'el dedo toca la pantalla 
	 If action= 0 Then
	   Ylanding=pager.Top+web(0).Top+ y
	   Xlanding= x
	 End If
	 
     
	 scrollY=webextendida.getScrollY(web(0)) 
	 posy=pager.Top+web(0).Top+ y
	 deltaY=posy-Ylanding
	 deltaX=Abs(x-Xlanding)
	
	 deltaY=deltaY*0.75
	 
	 
	  Log("deltay= " & deltaY)
	 
	 'el dedo deja la pantalla o se mueve
	 'en direccion x mas de 20 dip
	 If action= 1 Or deltaX> 40dip  Or scrollY>0 Then
	     web(0).Top=66dip
		 pager.Color=Colors.Black
		 If pull=True And deltaY > 140dip And deltaY < 250dip Then actualizarAll
		 deltaY=0 
		 deltaX=0
		 pull=False
		 Return False
	 End If
	  
	 'se mueve el dedo por la pantalla
	 If action= 2 And deltaY>66dip And scrollY=0 Then
	        pager.Color=Colors.DarkGray
	
	 	    
			If deltaY < 140dip Then girar(avisoRefresh,False)
			If deltaY >= 140dip Then girar(avisoRefresh,True)
			
			web(0).Top=deltaY
			avisoRefresh.left=(Activity.Width-avisoRefresh.width)/2
		    avisoRefresh.top= (66dip+((web(0).Top- 66dip)/2))-(avisoRefresh.Height/2)
			captionRefresh.Top=avisoRefresh.top+avisoRefresh.Height
			pull=True
			Return True
	 End If
	 
	 
	 

Return False
 

End Sub


Sub finpulldown_AnimationEnd

 If pull=True Then pager.Top=50dip
 If pull=False Then 
 			pager.Top=0
			actualizarAll
		  End If
		
 
End Sub


Sub actualizarAll
'  ToastMessageShow("ACTUALIZANDO NOTICIAS...", True)
  actualizarTodo=True
  ServicioD.servicioProgramado=False
  CancelScheduledService(ServicioD)
  StopService(ServicioD)  
  File.Delete(File.DirInternal, "servicioActivo.txt")
  ServicioD.servicioProgramado=True
  StartService(ServicioD)
  
End Sub


'This event gets called when the ViewPager instantiates the page. In this sub you can update the content
'of the page or even load a layout.
'Have a look in the logs to see when Pager_PageCreated and Pager_PageDestroyed are called.
Sub pagerNotas_PageCreated (Position As Int, Page As Object)

'	Log("pagerNotas_PageCreated " & Position)
'
'	Dim pan As Panel
'	Dim pan2 As Panel
'	Dim webTemp As WebView
'	pan=containerNotas.GetPageObject(Position)
'	pan2=pan.GetView(0)
'	webTemp=pan2.GetView(0)
'	con.cargar(notas(Position),webTemp)

 
End Sub


Sub pagerNotas_PageChanged (Position As Int)
    Dim pan As Panel
	Dim pan2 As Panel
	pan=containerNotas.GetPageObject(Position)
	pan2=pan.GetView(0)
	webActual=pan2.GetView(0)
	
	g2.SetOnTouchListener(webActual , "GestosNotas")

End Sub

Sub GestosNotas ( o As Object, ptrID As Int, action As Int, x As Float, y As Float) As Boolean
   
     
End Sub

Sub VolverTapa

         
     Dim anim As AnimationPlus
	 Dim anim2 As AnimationPlus
		 
	
     anim.InitializeTranslate("finirtapa",0,0,0,Activity.Height)
	 anim.Duration=500
	 anim.Start(pagerNotas)
	 
     pager.Top=0
	 anim2.InitializeScaleCenter("", 0, 0, 1, 1, pager)
	 anim2.Duration=500
	 anim2.Start(pager)
	 
	TapaVisible=True
	 
	 
End Sub




Sub finirtapa_AnimationEnd

	pagerNotas.Top=Activity.Height
 
    
End Sub


'#############################################################################################
'# LEER EN VOZ ALTA
'#############################################################################################

Sub leerVozAlta
   
	TTS1.SpeechRate=0.9
   
   'se verifica si esta hablando	
	Dim jTTS As JavaObject = TTS1
	If jTTS.RunMethod("isSpeaking", Null) = True Then
'		ToastMessageShow("Espere la voz esta ocupada hablando...", True)
		Return
	End If
  
  Dim archivo As String
  Dim texto As String
  
  archivo=webActual.Tag
  texto=con.CargarHTML(archivo)
  
  texto=PlainText(texto)
  
	If TTS1.IsInitialized = False Then TTS1.Initialize("TTS1")
	Dim frases() As String = Regex.Split("\.", texto)
	
		
	
	hablando=True
	PausaHablando=False
	botonPausarTTS.SetBackgroundImage(LoadBitmap (File.DirAssets,"pause.png"))
	pagerNotas.PagingEnabled=False
	
	For Each frase As String In frases
		
		Log("pausa= " &  PausaHablando )
		
		Do While PausaHablando=True
			DoEvents
		Loop
		
		frase=frase.Trim
		
		
		If jTTS.RunMethod("isSpeaking", Null) = False Then 
			Log("-->" &  frase &  "<--")
			TTS1.Speak(frase, True)
		End If
		
		If hablando=False Then 
			pagerNotas.PagingEnabled=True
			TTS1.Stop
			Exit
        End If
		
						 
		
		Do While jTTS.RunMethod("isSpeaking", Null) = True And hablando=True And PausaHablando=False
			DoEvents
		Loop
		
		
		If PausaHablando=True Then TTS1.Stop
	Next
  
  
  
  
    
	pagerNotas.PagingEnabled=True
	panelTTs.Visible=False
  
 
  
    
  End Sub
  
Sub PlainText (t As String) As String
Dim subs() As String
Dim x As Int 
Dim marca As String
Dim pos As Int

marca= "-->"
pos= t.IndexOf(marca)


t=t.SubString2(4,pos)

t = RegexReplace("\<[^\>]*\>", t, "")

t = RegexReplace("&\w+;", t, "")
'
't = RegexReplace("[^\u0000-\u007F]", t, "")

Return t

End Sub
  
  
Sub RegexReplace(Pattern As String, Text As String, Replacement As String) As String
    Dim m As Matcher
    m = Regex.Matcher(Pattern, Text)
    Dim r As Reflector
    r.Target = m
    Return r.RunMethod2("replaceAll", Replacement, "java.lang.String")
End Sub  
  



Sub girar(lb As Label, r As Boolean)
	Dim ani As AnimationPlus
	If rotacion = r Then Return
    If r=True Then ani.InitializeRotateCenter("",0,180,lb)
	If r=False Then ani.InitializeRotateCenter("",180,0,lb)
	
	captionRefresh.Visible=r
	rotacion=r
	ani.Duration=300
	ani.PersistAfter=True
	ani.Start(lb)
End Sub


'***********************************************
'** COMPARTIR - COMPARTIR - COMPARTIR
'***********************************************

'Capture Panel to imageview
Sub PanelCapture(pnl As Panel)

	Dim Obj1, Obj2 As Reflector
	Dim bmp As Bitmap
	Dim c As Canvas
	

	
	Obj1.Target = Obj1.GetActivityBA
	Obj1.Target = Obj1.GetField("vg")
	bmp.InitializeMutable(pnl.left + pnl.Width, pnl.Top + pnl.Height) 
	c.Initialize2(bmp)
	Dim args(1) As Object
	Dim types(1) As String
	Obj2.Target = c
	Obj2.Target = Obj2.GetField("canvas")
	args(0) = Obj2.Target
	types(0) = "android.graphics.Canvas"
	Obj1.RunMethod4("draw", args, types)


    Dim TK As Data
    Dim out As OutputStream
    TK.Canvas = c
	
	out = File.OpenOutput(File.DirRootExternal, "Capture.png", False)
	TK.Canvas.Bitmap.WriteToStream(out, 100, "PNG")
	out.Close
	
 
   Dim share As MESShareLibrary
   share.sharebinary("file://" & File.DirRootExternal & "/Capture.png", "image/png", "Do you see this?", "Mensaje")

    
   
End Sub

Sub botonAbout_Click
	
	cantidadClicks=cantidadClicks+1
	
	If cantidadClicks >= 8 Then
	  panelabout.SetBackgroundImage(LoadBitmap(File.DirAssets, "marcelo.jpg"))
	  cantidadClicks=0
	End If 
End Sub



