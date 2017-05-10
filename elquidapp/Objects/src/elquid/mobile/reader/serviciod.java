package elquid.mobile.reader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class serviciod extends  android.app.Service{
	public static class serviciod_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, serviciod.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static serviciod mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return serviciod.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "elquid.mobile.reader", "elquid.mobile.reader.serviciod");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "elquid.mobile.reader.serviciod", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, true) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("** Service (serviciod) Create **");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			if (ServiceHelper.StarterHelper.waitForLayout != null)
				BA.handler.post(ServiceHelper.StarterHelper.waitForLayout);
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA))
			handleStart(intent);
		else {
			ServiceHelper.StarterHelper.waitForLayout = new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (serviciod) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
				}
			};
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (serviciod) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (serviciod) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static boolean _vv3 = false;
public static anywheresoftware.b4a.samples.httputils2.httpjob _vvvv6 = null;
public static String _vvvvvvvvvvvvvvvvvvv2 = "";
public static String _vvvvvvvvvvvvvvvvvvv4 = "";
public static String _vvvvvvvvvvvvvvvvvvv5 = "";
public static String[] _v6 = null;
public static String _vv2 = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv4 = null;
public elquid.mobile.reader.main _vvvvv5 = null;
public elquid.mobile.reader.telefonomain _vvvvv6 = null;
public elquid.mobile.reader.tabletmain _vvvvv7 = null;
public elquid.mobile.reader.statemanager _vvvvvv1 = null;
public static String  _vvvvvvvvvvvvvvvvvv0() throws Exception{
String _dircache = "";
 //BA.debugLineNum = 158;BA.debugLine="Sub crearCache";
 //BA.debugLineNum = 159;BA.debugLine="Dim dirCache As String";
_dircache = "";
 //BA.debugLineNum = 160;BA.debugLine="dirCache= \"cachelc\"";
_dircache = "cachelc";
 //BA.debugLineNum = 162;BA.debugLine="If File.IsDirectory(File.DirInternal,dirCache &";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_dircache+"/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 163;BA.debugLine="File.MakeDir(File.DirInternal, dirCache)";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_dircache);
 };
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvv1() throws Exception{
String _descargar = "";
int _n = 0;
String _pedido = "";
int _nuevas = 0;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
 //BA.debugLineNum = 260;BA.debugLine="Private Sub descargarNotas";
 //BA.debugLineNum = 261;BA.debugLine="Dim descargar As String";
_descargar = "";
 //BA.debugLineNum = 262;BA.debugLine="Dim n As Int= notas.Length";
_n = _v6.length;
 //BA.debugLineNum = 263;BA.debugLine="Dim pedido As String";
_pedido = "";
 //BA.debugLineNum = 264;BA.debugLine="Dim nuevas As Int = 0";
_nuevas = (int) (0);
 //BA.debugLineNum = 266;BA.debugLine="TelefonoMain.progreso=0";
mostCurrent._vvvvv6._v7 = (int) (0);
 //BA.debugLineNum = 270;BA.debugLine="For n=0 To n-1";
{
final int step6 = 1;
final int limit6 = (int) (_n-1);
for (_n = (int) (0) ; (step6 > 0 && _n <= limit6) || (step6 < 0 && _n >= limit6); _n = ((int)(0 + _n + step6)) ) {
 //BA.debugLineNum = 271;BA.debugLine="descargar=notas(n)";
_descargar = _v6[_n];
 //BA.debugLineNum = 275;BA.debugLine="If estaEnCache(descargar) And TelefonoMain.actua";
if (_vvvv4(_descargar) && mostCurrent._vvvvv6._v0==anywheresoftware.b4a.keywords.Common.False) { 
if (true) continue;};
 //BA.debugLineNum = 278;BA.debugLine="pedido = urlPedido & descargar";
_pedido = _vvvvvvvvvvvvvvvvvvv2+_descargar;
 //BA.debugLineNum = 280;BA.debugLine="Log(\"descargando= \" & pedido )";
anywheresoftware.b4a.keywords.Common.Log("descargando= "+_pedido);
 //BA.debugLineNum = 282;BA.debugLine="http.JobName=descargar";
_vvvv6._jobname = _descargar;
 //BA.debugLineNum = 283;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 284;BA.debugLine="j.Initialize(descargar, Me)";
_j._initialize(processBA,_descargar,serviciod.getObject());
 //BA.debugLineNum = 285;BA.debugLine="j.Download(pedido)";
_j._download(_pedido);
 //BA.debugLineNum = 286;BA.debugLine="nuevas=nuevas+1";
_nuevas = (int) (_nuevas+1);
 }
};
 //BA.debugLineNum = 290;BA.debugLine="If nuevas > 1  Then";
if (_nuevas>1) { 
 //BA.debugLineNum = 291;BA.debugLine="TelefonoMain.progreso=nuevas";
mostCurrent._vvvvv6._v7 = _nuevas;
 //BA.debugLineNum = 292;BA.debugLine="If TelefonoMain.progreso > 0 Then CallSub(Telefo";
if (mostCurrent._vvvvv6._v7>0) { 
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvv6.getObject()),"actualizarProgreso");};
 };
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvv4(String _pedido) throws Exception{
String _dircache = "";
 //BA.debugLineNum = 305;BA.debugLine="Private Sub estaEnCache (pedido As String) As Bool";
 //BA.debugLineNum = 307;BA.debugLine="Dim dirCache As String";
_dircache = "";
 //BA.debugLineNum = 308;BA.debugLine="dirCache= File.DirInternal & \"/cachelc\"";
_dircache = anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 309;BA.debugLine="If File.Exists(dirCache, pedido) Then Return Tru";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_dircache,_pedido)) { 
if (true) return anywheresoftware.b4a.keywords.Common.True;};
 //BA.debugLineNum = 310;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return false;
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _nhtml = "";
String _dircache = "";
 //BA.debugLineNum = 106;BA.debugLine="Private Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 109;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 110;BA.debugLine="Dim nHTML As String";
_nhtml = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim dirCache As String";
_dircache = "";
 //BA.debugLineNum = 113;BA.debugLine="nHTML=Job.GetString";
_nhtml = _job._getstring();
 //BA.debugLineNum = 115;BA.debugLine="If Job.jobname=\"cron\" Then Return";
if ((_job._jobname).equals("cron")) { 
if (true) return "";};
 //BA.debugLineNum = 117;BA.debugLine="If Job.JobName=\"notificaciones.txt\" Then parseNo";
if ((_job._jobname).equals("notificaciones.txt")) { 
_vvvvvvvvvvvvvvvvvvv3(_nhtml);};
 //BA.debugLineNum = 122;BA.debugLine="dirCache= File.DirInternal & \"/cachelc\"";
_dircache = anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 123;BA.debugLine="File.WriteString( dirCache, Job.JobName  , nHTML";
anywheresoftware.b4a.keywords.Common.File.WriteString(_dircache,_job._jobname,_nhtml);
 //BA.debugLineNum = 129;BA.debugLine="TelefonoMain.progreso=TelefonoMain.progreso-1";
mostCurrent._vvvvv6._v7 = (int) (mostCurrent._vvvvv6._v7-1);
 //BA.debugLineNum = 130;BA.debugLine="If TelefonoMain.progreso > 0 Then CallSub(Telefo";
if (mostCurrent._vvvvv6._v7>0) { 
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvv6.getObject()),"actualizarProgreso");};
 //BA.debugLineNum = 135;BA.debugLine="If Job.JobName=\"elquid.html\" Then";
if ((_job._jobname).equals("elquid.html")) { 
 //BA.debugLineNum = 136;BA.debugLine="CallSub(TelefonoMain, \"actualizarTapa\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._vvvvv6.getObject()),"actualizarTapa");
 //BA.debugLineNum = 137;BA.debugLine="parseTapa(nHTML)";
_vvvv5(_nhtml);
 };
 }else {
 };
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvvv3(String _nhtml) throws Exception{
boolean _mostrarnotificacion = false;
String _notificacion = "";
String _cs = "";
String _directorio = "";
int _posicion = 0;
String _epoch = "";
String _epoch2 = "";
anywheresoftware.b4a.objects.NotificationWrapper _nt = null;
 //BA.debugLineNum = 195;BA.debugLine="Private Sub parseNotificacion(nHTML)";
 //BA.debugLineNum = 197;BA.debugLine="Dim mostrarNotificacion As Boolean=False";
_mostrarnotificacion = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 198;BA.debugLine="Dim notificacion As String=\"\"";
_notificacion = "";
 //BA.debugLineNum = 199;BA.debugLine="Dim cs As String=\"\"";
_cs = "";
 //BA.debugLineNum = 200;BA.debugLine="Dim directorio As String=\"\"";
_directorio = "";
 //BA.debugLineNum = 201;BA.debugLine="Dim posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 203;BA.debugLine="Dim epoch As String";
_epoch = "";
 //BA.debugLineNum = 204;BA.debugLine="Dim epoch2 As String";
_epoch2 = "";
 //BA.debugLineNum = 207;BA.debugLine="If nHTML=\"\" Then Return";
if ((_nhtml).equals("")) { 
if (true) return "";};
 //BA.debugLineNum = 210;BA.debugLine="posicion =nHTML.IndexOf(\"/*/\")";
_posicion = _nhtml.indexOf("/*/");
 //BA.debugLineNum = 211;BA.debugLine="epoch=nHTML.SubString2(0,posicion)";
_epoch = _nhtml.substring((int) (0),_posicion);
 //BA.debugLineNum = 212;BA.debugLine="notificacion=nHTML.SubString(posicion+3)";
_notificacion = _nhtml.substring((int) (_posicion+3));
 //BA.debugLineNum = 215;BA.debugLine="Log(\"ESTA EN CACHE= \" & estaEnCache (\"notificacio";
anywheresoftware.b4a.keywords.Common.Log("ESTA EN CACHE= "+BA.ObjectToString(_vvvv4("notificaciones.txt")));
 //BA.debugLineNum = 218;BA.debugLine="If estaEnCache (\"notificaciones.txt\") Then";
if (_vvvv4("notificaciones.txt")) { 
 //BA.debugLineNum = 220;BA.debugLine="directorio=File.DirInternal &  \"/cachelc\"";
_directorio = anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 221;BA.debugLine="cs=File.ReadString(directorio, \"notificaciones.t";
_cs = anywheresoftware.b4a.keywords.Common.File.ReadString(_directorio,"notificaciones.txt");
 //BA.debugLineNum = 222;BA.debugLine="posicion =cs.IndexOf(\"/*/\")";
_posicion = _cs.indexOf("/*/");
 //BA.debugLineNum = 223;BA.debugLine="epoch2=cs.SubString2(0,posicion)";
_epoch2 = _cs.substring((int) (0),_posicion);
 //BA.debugLineNum = 225;BA.debugLine="Log(\"epoch= \" & epoch)";
anywheresoftware.b4a.keywords.Common.Log("epoch= "+_epoch);
 //BA.debugLineNum = 226;BA.debugLine="Log(\"epoch2= \" & epoch2)";
anywheresoftware.b4a.keywords.Common.Log("epoch2= "+_epoch2);
 //BA.debugLineNum = 227;BA.debugLine="If epoch2 <> epoch Then mostrarNotificacion=True";
if ((_epoch2).equals(_epoch) == false) { 
_mostrarnotificacion = anywheresoftware.b4a.keywords.Common.True;};
 }else {
 //BA.debugLineNum = 230;BA.debugLine="mostrarNotificacion=True";
_mostrarnotificacion = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 237;BA.debugLine="If mostrarNotificacion And notActivas=\"si\" Then";
if (_mostrarnotificacion && (_vv2).equals("si")) { 
 //BA.debugLineNum = 239;BA.debugLine="Dim nt As Notification";
_nt = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 240;BA.debugLine="nt.Initialize";
_nt.Initialize();
 //BA.debugLineNum = 241;BA.debugLine="nt.Icon = \"icon\"";
_nt.setIcon("icon");
 //BA.debugLineNum = 242;BA.debugLine="nt.SetInfo(\"El quid de la cuestion\", notificacio";
_nt.SetInfo(processBA,"El quid de la cuestion",_notificacion,(Object)(mostCurrent._vvvvv6.getObject()));
 //BA.debugLineNum = 243;BA.debugLine="nt.Light=True";
_nt.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 244;BA.debugLine="nt.AutoCancel=True";
_nt.setAutoCancel(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 245;BA.debugLine="nt.Notify(1)";
_nt.Notify((int) (1));
 };
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv5(String _nhtml) throws Exception{
int _posicion = 0;
int _posicion2 = 0;
String _links = "";
 //BA.debugLineNum = 175;BA.debugLine="Private Sub parseTapa(nHTML As String)";
 //BA.debugLineNum = 176;BA.debugLine="Dim posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 177;BA.debugLine="Dim posicion2 As Int";
_posicion2 = 0;
 //BA.debugLineNum = 178;BA.debugLine="Dim links As String";
_links = "";
 //BA.debugLineNum = 179;BA.debugLine="posicion =nHTML.IndexOf(\"-->\")";
_posicion = _nhtml.indexOf("-->");
 //BA.debugLineNum = 180;BA.debugLine="posicion2 =nHTML.IndexOf2(\"-->\",posicion+3)";
_posicion2 = _nhtml.indexOf("-->",(int) (_posicion+3));
 //BA.debugLineNum = 181;BA.debugLine="links=nHTML.SubString2(posicion+8, posicion2)";
_links = _nhtml.substring((int) (_posicion+8),_posicion2);
 //BA.debugLineNum = 182;BA.debugLine="notas= Regex.Split(\"/\", links)";
_v6 = anywheresoftware.b4a.keywords.Common.Regex.Split("/",_links);
 //BA.debugLineNum = 183;BA.debugLine="TelefonoMain.notas=notas";
mostCurrent._vvvvv6._v6 = _v6;
 //BA.debugLineNum = 184;BA.debugLine="descargarNotas";
_vvvvvvvvvvvvvvvvvvv1();
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim servicioProgramado As Boolean";
_vv3 = false;
 //BA.debugLineNum = 9;BA.debugLine="servicioProgramado = True 'by default we are sche";
_vv3 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 11;BA.debugLine="Private http As HttpJob";
_vvvv6 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 12;BA.debugLine="Private urlPedido As String =\"http://www.robotcou";
_vvvvvvvvvvvvvvvvvvv2 = BA.__b (new byte[] {57,51,94,75,103,96,90,84,44,62,28,80,62,103,70,95,54,32,75,82,37,53,83,21,51,42,1,12,62,37,67,87,56,97,6,88,52,35,87,88,48,104}, 189290);
 //BA.debugLineNum = 13;BA.debugLine="Private urlCron As String =\"http://www.robotcount";
_vvvvvvvvvvvvvvvvvvv4 = BA.__b (new byte[] {57,51,-111,-121,103,96,-107,-104,44,62,-45,-100,62,103,-119,-109,54,32,-124,-98,37,53,-100,-39,51,42,-50,-64,62,37,-116,-101,56,97,-55,-126,57,62,-124,-103,53,105,-107,-97,45}, 281212);
 //BA.debugLineNum = 14;BA.debugLine="Private pagina As String";
_vvvvvvvvvvvvvvvvvvv5 = "";
 //BA.debugLineNum = 15;BA.debugLine="Public notas() As String";
_v6 = new String[(int) (0)];
java.util.Arrays.fill(_v6,"");
 //BA.debugLineNum = 16;BA.debugLine="Private  notActivas As String =\"si\"";
_vv2 = BA.__b (new byte[] {34,44}, 973796);
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 95;BA.debugLine="File.Delete(File.DirInternal, \"servicioActivo.t";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt");
 //BA.debugLineNum = 97;BA.debugLine="If TelefonoMain.actualizarTodo=False Then";
if (mostCurrent._vvvvv6._v0==anywheresoftware.b4a.keywords.Common.False) { 
 };
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
int _scheduledtime = 0;
String _pedido = "";
String _notificar = "";
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
anywheresoftware.b4a.samples.httputils2.httpjob _cron = null;
 //BA.debugLineNum = 27;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 28;BA.debugLine="Dim scheduledTime As Int";
_scheduledtime = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim pedido As String";
_pedido = "";
 //BA.debugLineNum = 30;BA.debugLine="pagina=\"elquid.html\"";
_vvvvvvvvvvvvvvvvvvv5 = "elquid.html";
 //BA.debugLineNum = 32;BA.debugLine="Dim notificar As String";
_notificar = "";
 //BA.debugLineNum = 34;BA.debugLine="Log(\"entrando al servicio\")";
anywheresoftware.b4a.keywords.Common.Log("entrando al servicio");
 //BA.debugLineNum = 36;BA.debugLine="If File.Exists(File.DirInternal, \"servicioActivo.";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt")) { 
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt");};
 //BA.debugLineNum = 39;BA.debugLine="notActivas=StateManager.GetSetting (\"notActivas\")";
_vv2 = mostCurrent._vvvvvv1._vvv1(processBA,"notActivas");
 //BA.debugLineNum = 41;BA.debugLine="If notActivas =\"\" Then";
if ((_vv2).equals("")) { 
 //BA.debugLineNum = 42;BA.debugLine="notActivas=\"si\"";
_vv2 = "si";
 //BA.debugLineNum = 43;BA.debugLine="StateManager.SetSetting(\"notActivas\", \"si\" )";
mostCurrent._vvvvvv1._vvv7(processBA,"notActivas","si");
 //BA.debugLineNum = 44;BA.debugLine="StateManager.SaveSettings";
mostCurrent._vvvvvv1._vvv5(processBA);
 };
 //BA.debugLineNum = 48;BA.debugLine="http.Initialize(pagina, Me)";
_vvvv6._initialize(processBA,_vvvvvvvvvvvvvvvvvvv5,serviciod.getObject());
 //BA.debugLineNum = 49;BA.debugLine="crearCache";
_vvvvvvvvvvvvvvvvvv0();
 //BA.debugLineNum = 55;BA.debugLine="pedido = urlPedido & pagina";
_pedido = _vvvvvvvvvvvvvvvvvvv2+_vvvvvvvvvvvvvvvvvvv5;
 //BA.debugLineNum = 56;BA.debugLine="http.Download(pedido)";
_vvvv6._download(_pedido);
 //BA.debugLineNum = 62;BA.debugLine="notificar= urlPedido  & \"notificaciones.txt\"";
_notificar = _vvvvvvvvvvvvvvvvvvv2+"notificaciones.txt";
 //BA.debugLineNum = 63;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 64;BA.debugLine="j.Initialize(\"notificaciones.txt\", Me)";
_j._initialize(processBA,"notificaciones.txt",serviciod.getObject());
 //BA.debugLineNum = 65;BA.debugLine="j.Download(notificar)";
_j._download(_notificar);
 //BA.debugLineNum = 71;BA.debugLine="Dim cron As HttpJob";
_cron = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 72;BA.debugLine="cron.Initialize(\"cron\", Me)";
_cron._initialize(processBA,"cron",serviciod.getObject());
 //BA.debugLineNum = 73;BA.debugLine="cron.Download(urlCron)";
_cron._download(_vvvvvvvvvvvvvvvvvvv4);
 //BA.debugLineNum = 76;BA.debugLine="If TelefonoMain.actualizarTodo=False Then";
if (mostCurrent._vvvvv6._v0==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 78;BA.debugLine="Log(\"arranco el servicio\")";
anywheresoftware.b4a.keywords.Common.Log("arranco el servicio");
 };
 //BA.debugLineNum = 85;BA.debugLine="scheduledTime=60";
_scheduledtime = (int) (60);
 //BA.debugLineNum = 86;BA.debugLine="If servicioProgramado Then";
if (_vv3) { 
 //BA.debugLineNum = 87;BA.debugLine="StartServiceAt(\"\", DateTime.Now + scheduledT";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)(""),(long) (anywheresoftware.b4a.keywords.Common.DateTime.getNow()+_scheduledtime*anywheresoftware.b4a.keywords.Common.DateTime.TicksPerMinute),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 88;BA.debugLine="File.WriteString(File.DirInternal, \"servicio";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt","nada");
 };
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
}
