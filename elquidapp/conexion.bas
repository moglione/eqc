Type=Class
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Class module
Sub Class_Globals

  Private http As HttpJob
	
  Private web As WebView
  Public fechaTapa As String
  Public cargando As String
  Public timeOut As Int =10
  Public segundos As Int=0
  
  http.Initialize("http", Me)

  
	
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize
   

End Sub

'############################################################################################################
' CARGAR
'¨Carga una pagina en el webview que se le pasa como argumento
' si cache es verdadero la pagina se carga desde la cache
' si es falso se cargar desde la web
'############################################################################################################

Sub cargar (pagina As String, receptor As WebView )
  	
   web=receptor		
   cargando=pagina
   
   
   If estaEnCache(pagina) Then 	
        CargarDesdeCache (pagina)
		Return
   End If
   
  '*************************************************
  ' Si se llega hasta aca es porque por alguna
  ' razon el archivo no esta en cache
  '*************************************************
  
	If pagina="elquid.html"  Then
     StartService(ServicioD)
	 web.LoadUrl("file:///android_asset/cargando.html")
	 Return
  End If    

   web.LoadUrl("file:///android_asset/fallo.html")



End Sub



'############################################################################################################
' CARGAR DESDE CACHE
'############################################################################################################
Private Sub CargarDesdeCache (pedido As String) 
 Dim cache As String
 Dim directorio As String
 Dim cs As String
 
 directorio=File.DirInternal &  "/cachelc"
 cache="file:///" & directorio & "/" & pedido
 
 cs=File.ReadString(directorio, pedido)
 web.LoadHtml(cs)
 
	If pedido="elquid.html" Then parseTapa(cs)

' web.LoadUrl(cache)

 
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


Private Sub parseTapa(nHTML As String)
  Dim posicion As Int
	Dim posicion2 As Int
	Dim links As String
	posicion =nHTML.IndexOf("-->")
	posicion2 =nHTML.IndexOf2("-->",posicion+3)
	links=nHTML.SubString2(posicion+8, posicion2)
	TelefonoMain.notas= Regex.Split("/", links)
End Sub


'############################################################################################################
' CARGAR html
'############################################################################################################
Public Sub CargarHTML (pedido As String) As String
 Dim cache As String
 Dim directorio As String
 Dim cs As String
 
 directorio=File.DirInternal &  "/cachelc"
 cache="file:///" & directorio & "/" & pedido
 
 cs=File.ReadString(directorio, pedido)
 Return cs 
 
End Sub