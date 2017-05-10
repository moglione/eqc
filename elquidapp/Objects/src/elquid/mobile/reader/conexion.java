package elquid.mobile.reader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class conexion extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "elquid.mobile.reader.conexion");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", elquid.mobile.reader.conexion.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.samples.httputils2.httpjob _vvvv6 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _vvvv7 = null;
public String _vvvv0 = "";
public String _vvvvv1 = "";
public int _vvvvv2 = 0;
public int _vvvvv3 = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv4 = null;
public elquid.mobile.reader.main _vvvvv5 = null;
public elquid.mobile.reader.telefonomain _vvvvv6 = null;
public elquid.mobile.reader.tabletmain _vvvvv7 = null;
public elquid.mobile.reader.serviciod _vvvvv0 = null;
public elquid.mobile.reader.statemanager _vvvvvv1 = null;
public String  _vvvv1(String _pagina,anywheresoftware.b4a.objects.WebViewWrapper _receptor) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub cargar (pagina As String, receptor As WebView";
 //BA.debugLineNum = 33;BA.debugLine="web=receptor";
_vvvv7 = _receptor;
 //BA.debugLineNum = 34;BA.debugLine="cargando=pagina";
_vvvvv1 = _pagina;
 //BA.debugLineNum = 37;BA.debugLine="If estaEnCache(pagina) Then";
if (_vvvv4(_pagina)) { 
 //BA.debugLineNum = 38;BA.debugLine="CargarDesdeCache (pagina)";
_vvvv2(_pagina);
 //BA.debugLineNum = 39;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 47;BA.debugLine="If pagina=\"elquid.html\"  Then";
if ((_pagina).equals("elquid.html")) { 
 //BA.debugLineNum = 48;BA.debugLine="StartService(ServicioD)";
__c.StartService(ba,(Object)(_vvvvv0.getObject()));
 //BA.debugLineNum = 49;BA.debugLine="web.LoadUrl(\"file:///android_asset/cargando.html";
_vvvv7.LoadUrl("file:///android_asset/cargando.html");
 //BA.debugLineNum = 50;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 53;BA.debugLine="web.LoadUrl(\"file:///android_asset/fallo.html\")";
_vvvv7.LoadUrl("file:///android_asset/fallo.html");
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public String  _vvvv2(String _pedido) throws Exception{
String _cache = "";
String _directorio = "";
String _cs = "";
 //BA.debugLineNum = 64;BA.debugLine="Private Sub CargarDesdeCache (pedido As String)";
 //BA.debugLineNum = 65;BA.debugLine="Dim cache As String";
_cache = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim directorio As String";
_directorio = "";
 //BA.debugLineNum = 67;BA.debugLine="Dim cs As String";
_cs = "";
 //BA.debugLineNum = 69;BA.debugLine="directorio=File.DirInternal &  \"/cachelc\"";
_directorio = __c.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 70;BA.debugLine="cache=\"file:///\" & directorio & \"/\" & pedido";
_cache = "file:///"+_directorio+"/"+_pedido;
 //BA.debugLineNum = 72;BA.debugLine="cs=File.ReadString(directorio, pedido)";
_cs = __c.File.ReadString(_directorio,_pedido);
 //BA.debugLineNum = 73;BA.debugLine="web.LoadHtml(cs)";
_vvvv7.LoadHtml(_cs);
 //BA.debugLineNum = 75;BA.debugLine="If pedido=\"elquid.html\" Then parseTapa(cs)";
if ((_pedido).equals("elquid.html")) { 
_vvvv5(_cs);};
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public String  _vvvv3(String _pedido) throws Exception{
String _cache = "";
String _directorio = "";
String _cs = "";
 //BA.debugLineNum = 111;BA.debugLine="Public Sub CargarHTML (pedido As String) As String";
 //BA.debugLineNum = 112;BA.debugLine="Dim cache As String";
_cache = "";
 //BA.debugLineNum = 113;BA.debugLine="Dim directorio As String";
_directorio = "";
 //BA.debugLineNum = 114;BA.debugLine="Dim cs As String";
_cs = "";
 //BA.debugLineNum = 116;BA.debugLine="directorio=File.DirInternal &  \"/cachelc\"";
_directorio = __c.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 117;BA.debugLine="cache=\"file:///\" & directorio & \"/\" & pedido";
_cache = "file:///"+_directorio+"/"+_pedido;
 //BA.debugLineNum = 119;BA.debugLine="cs=File.ReadString(directorio, pedido)";
_cs = __c.File.ReadString(_directorio,_pedido);
 //BA.debugLineNum = 120;BA.debugLine="Return cs";
if (true) return _cs;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private http As HttpJob";
_vvvv6 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 6;BA.debugLine="Private web As WebView";
_vvvv7 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Public fechaTapa As String";
_vvvv0 = "";
 //BA.debugLineNum = 8;BA.debugLine="Public cargando As String";
_vvvvv1 = "";
 //BA.debugLineNum = 9;BA.debugLine="Public timeOut As Int =10";
_vvvvv2 = (int) (10);
 //BA.debugLineNum = 10;BA.debugLine="Public segundos As Int=0";
_vvvvv3 = (int) (0);
 //BA.debugLineNum = 12;BA.debugLine="http.Initialize(\"http\", Me)";
_vvvv6._initialize(ba,"http",this);
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public boolean  _vvvv4(String _pedido) throws Exception{
String _dircache = "";
 //BA.debugLineNum = 87;BA.debugLine="Private Sub estaEnCache (pedido As String) As Bool";
 //BA.debugLineNum = 89;BA.debugLine="Dim dirCache As String";
_dircache = "";
 //BA.debugLineNum = 90;BA.debugLine="dirCache= File.DirInternal & \"/cachelc\"";
_dircache = __c.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 91;BA.debugLine="If File.Exists(dirCache, pedido) Then Return Tru";
if (__c.File.Exists(_dircache,_pedido)) { 
if (true) return __c.True;};
 //BA.debugLineNum = 92;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return false;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 19;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public String  _vvvv5(String _nhtml) throws Exception{
int _posicion = 0;
int _posicion2 = 0;
String _links = "";
 //BA.debugLineNum = 97;BA.debugLine="Private Sub parseTapa(nHTML As String)";
 //BA.debugLineNum = 98;BA.debugLine="Dim posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 99;BA.debugLine="Dim posicion2 As Int";
_posicion2 = 0;
 //BA.debugLineNum = 100;BA.debugLine="Dim links As String";
_links = "";
 //BA.debugLineNum = 101;BA.debugLine="posicion =nHTML.IndexOf(\"-->\")";
_posicion = _nhtml.indexOf("-->");
 //BA.debugLineNum = 102;BA.debugLine="posicion2 =nHTML.IndexOf2(\"-->\",posicion+3)";
_posicion2 = _nhtml.indexOf("-->",(int) (_posicion+3));
 //BA.debugLineNum = 103;BA.debugLine="links=nHTML.SubString2(posicion+8, posicion2)";
_links = _nhtml.substring((int) (_posicion+8),_posicion2);
 //BA.debugLineNum = 104;BA.debugLine="TelefonoMain.notas= Regex.Split(\"/\", links)";
_vvvvv6._v6 = __c.Regex.Split("/",_links);
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
