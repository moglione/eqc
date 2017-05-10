Type=Service
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim servicioProgramado As Boolean
	servicioProgramado = True 'by default we are scheduling the next update
	
	Private http As HttpJob
	Private urlPedido As String ="http://www.robotcountry.net/elquid/salida/"
	Private urlCron As String ="http://www.robotcountry.net/elquid/elquid.php"
    Private pagina As String
    Public notas() As String
	Private  notActivas As String ="si"
	
	
    
End Sub
Sub Service_Create

   

End Sub

Sub Service_Start (StartingIntent As Intent)
    Dim scheduledTime As Int
	Dim pedido As String
	pagina="elquid.html"
	
	Dim notificar As String
	
	Log("entrando al servicio")
		
	If File.Exists(File.DirInternal, "servicioActivo.txt") Then File.Delete(File.DirInternal, "servicioActivo.txt")
	
	
	notActivas=StateManager.GetSetting ("notActivas")
	
	If notActivas ="" Then
		notActivas="si"
		StateManager.SetSetting("notActivas", "si" )
		StateManager.SaveSettings
	End If
	
	
	http.Initialize(pagina, Me)
	crearCache
 	
	'************************************************ 
    ' se descarga la pagina principal
	'************************************************ 
    
	pedido = urlPedido & pagina
	http.Download(pedido)
	
	
	'************************************************ 
    ' se descarga el archivo de notificaciones
	'************************************************ 
	notificar= urlPedido  & "notificaciones.txt"
	Dim j As HttpJob
	j.Initialize("notificaciones.txt", Me)
    j.Download(notificar)
	
	
	'************************************************
	' se dispara el cron en el servidor
	'************************************************
	Dim cron As HttpJob
	cron.Initialize("cron", Me)
	cron.Download(urlCron)
	
	
    If TelefonoMain.actualizarTodo=False Then
'	   ToastMessageShow("Arranco el servicio", False)
       Log("arranco el servicio")
    End If

	'************************************************ 
	' se programa para que el servicio corra dentro 
	' de los scheduledTime minutos
	'************************************************
	scheduledTime=60
	If servicioProgramado Then
	     StartServiceAt("", DateTime.Now + scheduledTime * DateTime.TicksPerMinute, False)
	     File.WriteString(File.DirInternal, "servicioActivo.txt", "nada" )
	End If


End Sub

Sub Service_Destroy
   File.Delete(File.DirInternal, "servicioActivo.txt")
   
  If TelefonoMain.actualizarTodo=False Then 
'   ToastMessageShow("Servicio cerrado", False)
  End If
End Sub


'############################################################################################################
' JOBDONE
'############################################################################################################
Private Sub JobDone(Job As HttpJob)


If Job.Success = True Then
   	    Dim nHTML As String 
		Dim dirCache As String
		
		nHTML=Job.GetString
		
		If Job.jobname="cron" Then Return
		
		If Job.JobName="notificaciones.txt" Then parseNotificacion(nHTML)
		
		'************************************************ 
		' se guarda la pagina en la cache
		'************************************************ 
		dirCache= File.DirInternal & "/cachelc" 
		File.WriteString( dirCache, Job.JobName  , nHTML )
		
		
		
		
		
		TelefonoMain.progreso=TelefonoMain.progreso-1
		If TelefonoMain.progreso > 0 Then CallSub(TelefonoMain, "actualizarProgreso")
		
		
        ''''''ToastMessageShow( Job.JobName, False)
		
		If Job.JobName="elquid.html" Then
		    CallSub(TelefonoMain, "actualizarTapa")
			parseTapa(nHTML)
		End If
		
		
			
		
	
		
		   		        
	     
Else
'	 ToastMessageShow("No hay conexion de datos.", False)
	 
End If	


End Sub

'############################################################################################################
' CREARCACHE
'############################################################################################################
Sub crearCache
   Dim dirCache As String
   dirCache= "cachelc"
   
   If File.IsDirectory(File.DirInternal,dirCache & "/") = False Then
          File.MakeDir(File.DirInternal, dirCache)
   End If

End Sub




'############################################################################################################
' PARSETAPA
'############################################################################################################

Private Sub parseTapa(nHTML As String)
    Dim posicion As Int
	Dim posicion2 As Int
	Dim links As String
	posicion =nHTML.IndexOf("-->")
	posicion2 =nHTML.IndexOf2("-->",posicion+3)
	links=nHTML.SubString2(posicion+8, posicion2)
	notas= Regex.Split("/", links)
	TelefonoMain.notas=notas
	descargarNotas
   
 
End Sub



'############################################################################################################
' PARSENOTIFICACION
'############################################################################################################

Private Sub parseNotificacion(nHTML)
	
	Dim mostrarNotificacion As Boolean=False
	Dim notificacion As String=""
	Dim cs As String=""
	Dim directorio As String=""
	Dim posicion As Int
	
	Dim epoch As String
	Dim epoch2 As String
	
	
	If nHTML="" Then Return
	
   'se extrae el epoch de la notificacion
	posicion =nHTML.IndexOf("/*/")
	epoch=nHTML.SubString2(0,posicion)
	notificacion=nHTML.SubString(posicion+3)
	
	
	Log("ESTA EN CACHE= " & estaEnCache ("notificaciones.txt"))
	
   'se verifica si ya hay un archivo de notificacion
	If estaEnCache ("notificaciones.txt") Then
		'si esta se carga
		directorio=File.DirInternal &  "/cachelc"
		cs=File.ReadString(directorio, "notificaciones.txt")
		posicion =cs.IndexOf("/*/")
		epoch2=cs.SubString2(0,posicion)
		
		Log("epoch= " & epoch)
		Log("epoch2= " & epoch2)
		If epoch2 <> epoch Then mostrarNotificacion=True
		
	 Else
		mostrarNotificacion=True
	
	End If




If mostrarNotificacion And notActivas="si" Then
	'If  IsPaused(TelefonoMain)=True  Then
		Dim nt As Notification
		nt.Initialize
		nt.Icon = "icon"
		nt.SetInfo("El quid de la cuestion", notificacion , TelefonoMain)
		nt.Light=True
		nt.AutoCancel=True
		nt.Notify(1)
		
	
	'End If
End If   


	
 
End Sub

'############################################################################################################
' DESCARGARNOTAS
'############################################################################################################

Private Sub descargarNotas
   Dim descargar As String
   Dim n As Int= notas.Length
   Dim pedido As String
   Dim nuevas As Int = 0
   
   TelefonoMain.progreso=0
  
   
   
   For n=0 To n-1
     descargar=notas(n)
	 
		
	
	 If estaEnCache(descargar) And TelefonoMain.actualizarTodo=False Then Continue
	 
			
	 pedido = urlPedido & descargar 
	 
	Log("descargando= " & pedido )
	 
	 http.JobName=descargar
	 Dim j As HttpJob
     j.Initialize(descargar, Me) 
     j.Download(pedido)
	 nuevas=nuevas+1
 
   Next
   
   If nuevas > 1  Then
        TelefonoMain.progreso=nuevas
		If TelefonoMain.progreso > 0 Then CallSub(TelefonoMain, "actualizarProgreso")
		
   End If
   
   
   
End Sub


'############################################################################################################
' ESTA EN CACHE??
'############################################################################################################

Private Sub estaEnCache (pedido As String) As Boolean
  
  Dim dirCache As String
  dirCache= File.DirInternal & "/cachelc"
  If File.Exists(dirCache, pedido) Then Return True 
  Return False
  
End Sub



