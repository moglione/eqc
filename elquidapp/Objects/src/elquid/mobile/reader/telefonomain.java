package elquid.mobile.reader;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class telefonomain extends Activity implements B4AActivity{
	public static telefonomain mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "elquid.mobile.reader", "elquid.mobile.reader.telefonomain");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (telefonomain).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "elquid.mobile.reader", "elquid.mobile.reader.telefonomain");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "elquid.mobile.reader.telefonomain", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (telefonomain) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (telefonomain) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return telefonomain.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (telefonomain) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (telefonomain) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static String _v5 = "";
public static String[] _v6 = null;
public static int _v7 = 0;
public static boolean _v0 = false;
public static boolean _vv1 = false;
public static String _vv2 = "";
public anywheresoftware.b4a.objects.WebViewWrapper[] _vvvv7 = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _vvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvvv2 = null;
public de.amberhome.viewpager.AHPageContainer _vvvvvvvvvv0 = null;
public de.amberhome.viewpager.AHViewPager _vvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvvv3 = null;
public de.amberhome.viewpager.AHPageContainer _vvvvvvvvvvv1 = null;
public de.amberhome.viewpager.AHViewPager _vvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _vvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _vvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper[] _vvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _vvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _vvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvv5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _vvvvvvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvv5 = null;
public static boolean _vvvvvvvvvvvvvvvv3 = false;
public static String _vvvvvvvvvvvvvvvv4 = "";
public static String _vvvvvvvvvvvvvvv1 = "";
public elquid.mobile.reader.conexion _vvvvvvvvv7 = null;
public elquid.mobile.reader.setmenu _vvvvvvvvvvv5 = null;
public uk.co.martinpearman.b4a.webviewextras.WebViewExtras _vvvvvvvvvvvvvv2 = null;
public static String _vvvv0 = "";
public static String _vvvvvvvvvvvv7 = "";
public static int _vvvvvvvvv2 = 0;
public static int _vvvvvvvvv1 = 0;
public static int _vvvvvvvv0 = 0;
public static int _vvvvvvvvvvvvvvvv5 = 0;
public static int _vvvvvvvvvvvvvvvv6 = 0;
public static String _vvvvvvvvvvvvvvvv7 = "";
public static int _vvvvvvvvvvvvvvvv0 = 0;
public anywheresoftware.b4a.objects.WebViewWrapper _vvvvvvvvvvv6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _vvvvvvvvvvvv5 = null;
public static int _vvvvvvvvvvvv6 = 0;
public anywheresoftware.b4a.agraham.gestures.Gestures _vvvvvvvvvvvvvv6 = null;
public static int _vvvvvvvvvvvvvvvvv1 = 0;
public static int _vvvvvvvvvvvvvvvvv2 = 0;
public static int _vvvvvvvvvvvvvvvvv3 = 0;
public static int _vvvvvvvvvvvvvvvvv4 = 0;
public thalmy.webviewxtended.xtender _vvvvvvvvvvvvvvvvv5 = null;
public static boolean _vvvvvvvvvvvvvv7 = false;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv3 = null;
public static boolean _vvvvvvvvvvvvvvv4 = false;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvvvvvvvvv4 = null;
public anywheresoftware.b4a.agraham.gestures.Gestures _vvvvvvvvvvvv2 = null;
public anywheresoftware.b4a.obejcts.TTS _vvvvvvvvvv2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvvv7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvv0 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvv1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvvvvvvvvvvv6 = null;
public static boolean _vvvvvvvvvvvvvvvvv7 = false;
public static boolean _vvvvvvvvvvvvvvvvv0 = false;
public static String _vvvvvvvvvvvvvvvvvv1 = "";
public flm.b4a.animationplus.AnimationPlusWrapper _vvvvvvvvvv4 = null;
public static int _vvvvvvvvvvv3 = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv4 = null;
public elquid.mobile.reader.main _vvvvv5 = null;
public elquid.mobile.reader.tabletmain _vvvvv7 = null;
public elquid.mobile.reader.serviciod _vvvvv0 = null;
public elquid.mobile.reader.statemanager _vvvvvv1 = null;
public static class _data{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper Canvas;
public anywheresoftware.b4a.objects.PanelWrapper panel;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper Bitmap;
public void Initialize() {
IsInitialized = true;
Canvas = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
panel = new anywheresoftware.b4a.objects.PanelWrapper();
Bitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 202;BA.debugLine="Log( \"Activity_create\")";
anywheresoftware.b4a.keywords.Common.Log("Activity_create");
 //BA.debugLineNum = 206;BA.debugLine="notActivas=StateManager.GetSetting (\"notActivas\")";
_vv2 = mostCurrent._vvvvvv1._vvv1(mostCurrent.activityBA,"notActivas");
 //BA.debugLineNum = 208;BA.debugLine="If notActivas =\"\" Then";
if ((_vv2).equals("")) { 
 //BA.debugLineNum = 209;BA.debugLine="notActivas=\"si\"";
_vv2 = "si";
 //BA.debugLineNum = 210;BA.debugLine="StateManager.SetSetting(\"notActivas\", \"si\" )";
mostCurrent._vvvvvv1._vvv7(mostCurrent.activityBA,"notActivas","si");
 //BA.debugLineNum = 211;BA.debugLine="StateManager.SaveSettings";
mostCurrent._vvvvvv1._vvv5(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 215;BA.debugLine="If notActivas=\"no\" Then ToastMessageShow(\"Notific";
if ((_vv2).equals("no")) { 
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Notificaciones desactivadas"),anywheresoftware.b4a.keywords.Common.False);};
 //BA.debugLineNum = 216;BA.debugLine="If notActivas=\"si\" Then ToastMessageShow(\"Notific";
if ((_vv2).equals("si")) { 
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Notificaciones Activadas"),anywheresoftware.b4a.keywords.Common.False);};
 //BA.debugLineNum = 221;BA.debugLine="offset=notaInicio-(pagFijas-1)";
_vvvvvvvv0 = (int) (_vvvvvvvvv1-(_vvvvvvvvv2-1));
 //BA.debugLineNum = 223;BA.debugLine="dibujarLayout";
_vvvvvvvvv3();
 //BA.debugLineNum = 224;BA.debugLine="splashScreen(True)";
_vvvvvvvvv4(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 232;BA.debugLine="If servicioActivo = False Then StartService(Se";
if (_vvvvvvvvv5()==anywheresoftware.b4a.keywords.Common.False) { 
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvv0.getObject()));};
 //BA.debugLineNum = 235;BA.debugLine="crearFecha";
_vvvvvvvvv6();
 //BA.debugLineNum = 236;BA.debugLine="con.Initialize";
mostCurrent._vvvvvvvvv7._initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 237;BA.debugLine="con.cargar(\"elquid.html\", web(0))";
mostCurrent._vvvvvvvvv7._vvvv1("elquid.html",mostCurrent._vvvv7[(int) (0)]);
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
int _result = 0;
 //BA.debugLineNum = 1148;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 1151;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK  Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 1152;BA.debugLine="If TapaVisible=False Then";
if (_vv1==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1153;BA.debugLine="VolverTapa";
_vvvvvvvvv0();
 //BA.debugLineNum = 1154;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1157;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 1158;BA.debugLine="result = Msgbox2(\"¿Quiere salir de EL Quid de l";
_result = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Quiere salir de EL Quid de la cuestión?"),BA.ObjectToCharSequence("Salir"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 1159;BA.debugLine="If result = DialogResponse.Positive Then";
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 1160;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1161;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 1164;BA.debugLine="If result = DialogResponse.NEGATIVE Then Return";
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE) { 
if (true) return anywheresoftware.b4a.keywords.Common.True;};
 };
 };
 //BA.debugLineNum = 1172;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_MENU Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_MENU) { 
 //BA.debugLineNum = 1173;BA.debugLine="pager.GotoPage(0,True)";
mostCurrent._vvvvvvvvvv1.GotoPage((int) (0),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1174;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 1177;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 245;BA.debugLine="If TTS1.IsInitialized = False Then TTS1.Initiali";
if (mostCurrent._vvvvvvvvvv2.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._vvvvvvvvvv2.Initialize(processBA,"TTS1");};
 //BA.debugLineNum = 246;BA.debugLine="TTS1.SpeechRate=0.9";
mostCurrent._vvvvvvvvvv2.setSpeechRate((float) (0.9));
 //BA.debugLineNum = 247;BA.debugLine="TTS1.Pitch=1";
mostCurrent._vvvvvvvvvv2.setPitch((float) (1));
 //BA.debugLineNum = 248;BA.debugLine="TTS1.SetLanguage(\"es\",\"\")";
mostCurrent._vvvvvvvvvv2.SetLanguage("es","");
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv3() throws Exception{
 //BA.debugLineNum = 1327;BA.debugLine="Sub actualizarAll";
 //BA.debugLineNum = 1329;BA.debugLine="actualizarTodo=True";
_v0 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1330;BA.debugLine="ServicioD.servicioProgramado=False";
mostCurrent._vvvvv0._vv3 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1331;BA.debugLine="CancelScheduledService(ServicioD)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvv0.getObject()));
 //BA.debugLineNum = 1332;BA.debugLine="StopService(ServicioD)";
anywheresoftware.b4a.keywords.Common.StopService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvv0.getObject()));
 //BA.debugLineNum = 1333;BA.debugLine="File.Delete(File.DirInternal, \"servicioActivo.tx";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt");
 //BA.debugLineNum = 1334;BA.debugLine="ServicioD.servicioProgramado=True";
mostCurrent._vvvvv0._vv3 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1335;BA.debugLine="StartService(ServicioD)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._vvvvv0.getObject()));
 //BA.debugLineNum = 1337;BA.debugLine="End Sub";
return "";
}
public static String  _actualizarprogreso() throws Exception{
 //BA.debugLineNum = 1190;BA.debugLine="Sub actualizarProgreso";
 //BA.debugLineNum = 1192;BA.debugLine="If progreso > 1 Then";
if (_v7>1) { 
 }else {
 //BA.debugLineNum = 1197;BA.debugLine="actualizarTodo=False";
_v0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1198;BA.debugLine="a.Stop(brefresh)";
mostCurrent._vvvvvvvvvv4.Stop((android.view.View)(mostCurrent._vvvvvvvvvv5.getObject()));
 //BA.debugLineNum = 1199;BA.debugLine="crearPaginasNotas";
_vvvvvvvvvv6();
 };
 //BA.debugLineNum = 1202;BA.debugLine="End Sub";
return "";
}
public static String  _actualizartapa() throws Exception{
 //BA.debugLineNum = 1248;BA.debugLine="Sub actualizarTapa";
 //BA.debugLineNum = 1249;BA.debugLine="con.cargar(\"elquid.html\", web(0))";
mostCurrent._vvvvvvvvv7._vvvv1("elquid.html",mostCurrent._vvvv7[(int) (0)]);
 //BA.debugLineNum = 1251;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv7(int _posicion) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _cont = null;
anywheresoftware.b4a.objects.WebViewWrapper _webv = null;
anywheresoftware.b4a.objects.ButtonWrapper _botonletras = null;
anywheresoftware.b4a.objects.ButtonWrapper _botonleer = null;
anywheresoftware.b4a.objects.ButtonWrapper _botoncompartir = null;
anywheresoftware.b4a.objects.LabelWrapper _fecha1 = null;
anywheresoftware.b4a.objects.LabelWrapper _fecha2 = null;
anywheresoftware.b4a.objects.PanelWrapper _lineauno = null;
anywheresoftware.b4a.objects.PanelWrapper _lineados = null;
anywheresoftware.b4a.objects.PanelWrapper _barral = null;
anywheresoftware.b4a.objects.ButtonWrapper _botonlogo = null;
anywheresoftware.b4a.objects.PanelWrapper _blackline = null;
 //BA.debugLineNum = 961;BA.debugLine="Sub agregarPagina (posicion As Int)";
 //BA.debugLineNum = 963;BA.debugLine="Log(\"agregarPagina \" & posicion & \" --> \" & co";
anywheresoftware.b4a.keywords.Common.Log("agregarPagina "+BA.NumberToString(_posicion)+" --> "+BA.NumberToString(mostCurrent._vvvvvvvvvv0.getCount()));
 //BA.debugLineNum = 965;BA.debugLine="Dim pan As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 966;BA.debugLine="Dim cont As Panel";
_cont = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 967;BA.debugLine="Dim webv As WebView";
_webv = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 968;BA.debugLine="Dim botonLetras As Button";
_botonletras = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 969;BA.debugLine="Dim botonLeer As Button";
_botonleer = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 970;BA.debugLine="Dim botonCompartir As Button";
_botoncompartir = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 972;BA.debugLine="Dim fecha1 As Label";
_fecha1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 973;BA.debugLine="Dim fecha2 As Label";
_fecha2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 974;BA.debugLine="Dim lineaUno As Panel";
_lineauno = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 975;BA.debugLine="Dim lineaDos As Panel";
_lineados = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 976;BA.debugLine="Dim barraL As Panel";
_barral = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 977;BA.debugLine="Dim botonLogo As Button";
_botonlogo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 979;BA.debugLine="Dim blackLine As Panel";
_blackline = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 988;BA.debugLine="botonLetras.Initialize(\"cambiarFont\")";
_botonletras.Initialize(mostCurrent.activityBA,"cambiarFont");
 //BA.debugLineNum = 989;BA.debugLine="botonLetras.SetBackgroundImage(LoadBitmap (File.D";
_botonletras.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fontchange.png").getObject()));
 //BA.debugLineNum = 992;BA.debugLine="botonLeer.Initialize(\"leerTexto\")";
_botonleer.Initialize(mostCurrent.activityBA,"leerTexto");
 //BA.debugLineNum = 993;BA.debugLine="botonLeer.SetBackgroundImage(LoadBitmap (File.Dir";
_botonleer.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vozalta.png").getObject()));
 //BA.debugLineNum = 995;BA.debugLine="botonCompartir.Initialize(\"compartirNoticia\")";
_botoncompartir.Initialize(mostCurrent.activityBA,"compartirNoticia");
 //BA.debugLineNum = 996;BA.debugLine="botonCompartir.SetBackgroundImage(LoadBitmap (Fil";
_botoncompartir.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"compartir.png").getObject()));
 //BA.debugLineNum = 998;BA.debugLine="botonLogo.Initialize(\"\")";
_botonlogo.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 999;BA.debugLine="botonLogo.SetBackgroundImage(LoadBitmap (File.Dir";
_botonlogo.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"logonegro.png").getObject()));
 //BA.debugLineNum = 1004;BA.debugLine="fecha1.Initialize(\"\")";
_fecha1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1005;BA.debugLine="fecha2.Initialize(\"\")";
_fecha2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1006;BA.debugLine="lineaUno.Initialize(\"\")";
_lineauno.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1007;BA.debugLine="lineaDos.Initialize(\"\")";
_lineados.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1013;BA.debugLine="barraL.Initialize(\"\")";
_barral.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1014;BA.debugLine="barraL.Color= Colors.ARGB(255,239,239,239)";
_barral.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (239),(int) (239),(int) (239)));
 //BA.debugLineNum = 1018;BA.debugLine="barraL.AddView(botonLetras,Activity.Width-38di";
_barral.AddView((android.view.View)(_botonletras.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (38))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (7)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (34)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (34)));
 //BA.debugLineNum = 1019;BA.debugLine="barraL.AddView(botonLeer,Activity.Width-74dip,7di";
_barral.AddView((android.view.View)(_botonleer.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (74))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (7)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 1020;BA.debugLine="barraL.AddView(botonCompartir,Activity.Width-112d";
_barral.AddView((android.view.View)(_botoncompartir.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (112))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (7)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 1022;BA.debugLine="barraL.AddView(botonLogo,5dip,5dip,222dip,40di";
_barral.AddView((android.view.View)(_botonlogo.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (222)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 1027;BA.debugLine="blackLine.Initialize(\"\")";
_blackline.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1032;BA.debugLine="cont.Initialize(\"\")";
_cont.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1033;BA.debugLine="pan.Initialize(\"\")";
_pan.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1034;BA.debugLine="webv.Initialize(\"web\")";
_webv.Initialize(mostCurrent.activityBA,"web");
 //BA.debugLineNum = 1035;BA.debugLine="webv.ZoomEnabled=False";
_webv.setZoomEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1039;BA.debugLine="pan.AddView(webv,0,50dip, 100%x, 100%y-50dip)";
_pan.AddView((android.view.View)(_webv.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 1041;BA.debugLine="pan.AddView(barraL,0,0,100%x,50dip)";
_pan.AddView((android.view.View)(_barral.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1044;BA.debugLine="pan.AddView(blackLine,0,0,100%x,1)";
_pan.AddView((android.view.View)(_blackline.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (1));
 //BA.debugLineNum = 1049;BA.debugLine="cont.AddView (pan,0,0, 100%x, 100%y)";
_cont.AddView((android.view.View)(_pan.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1050;BA.debugLine="containerNotas.AddPage(cont,\"\")";
mostCurrent._vvvvvvvvvvv1.AddPage((android.view.View)(_cont.getObject()),"");
 //BA.debugLineNum = 1051;BA.debugLine="con.cargar(notas(posicion),webv)";
mostCurrent._vvvvvvvvv7._vvvv1(_v6[_posicion],_webv);
 //BA.debugLineNum = 1053;BA.debugLine="webv.Tag=notas(posicion)";
_webv.setTag((Object)(_v6[_posicion]));
 //BA.debugLineNum = 1055;BA.debugLine="End Sub";
return "";
}
public static String  _anim_animationend() throws Exception{
 //BA.debugLineNum = 828;BA.debugLine="Sub anim_AnimationEnd";
 //BA.debugLineNum = 832;BA.debugLine="End Sub";
return "";
}
public static String  _anterior_click() throws Exception{
 //BA.debugLineNum = 1057;BA.debugLine="Sub anterior_Click";
 //BA.debugLineNum = 1058;BA.debugLine="VolverTapa";
_vvvvvvvvv0();
 //BA.debugLineNum = 1059;BA.debugLine="End Sub";
return "";
}
public static String  _bmenu_click() throws Exception{
int _n = 0;
 //BA.debugLineNum = 682;BA.debugLine="Sub bMenu_Click";
 //BA.debugLineNum = 683;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 684;BA.debugLine="n=pager.CurrentPage-1";
_n = (int) (mostCurrent._vvvvvvvvvv1.getCurrentPage()-1);
 //BA.debugLineNum = 685;BA.debugLine="pager.GotoPage(n, True)";
mostCurrent._vvvvvvvvvv1.GotoPage(_n,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 686;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv2() throws Exception{
anywheresoftware.b4a.objects.collections.List _mylist = null;
String _myfile = "";
int _i = 0;
 //BA.debugLineNum = 1236;BA.debugLine="Sub BorrarCache";
 //BA.debugLineNum = 1237;BA.debugLine="Dim MyList As List";
_mylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1238;BA.debugLine="MyList.initialize";
_mylist.Initialize();
 //BA.debugLineNum = 1239;BA.debugLine="Dim MyFile As String";
_myfile = "";
 //BA.debugLineNum = 1240;BA.debugLine="MyList=File.ListFiles(File.DirInternal & \"/cachel";
_mylist = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc");
 //BA.debugLineNum = 1241;BA.debugLine="For i= MyList.Size-1 To 0 Step -1";
{
final int step5 = (int) (-1);
final int limit5 = (int) (0);
for (_i = (int) (_mylist.getSize()-1) ; (step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5); _i = ((int)(0 + _i + step5)) ) {
 //BA.debugLineNum = 1242;BA.debugLine="MyFile=MyList.Get(i)";
_myfile = BA.ObjectToString(_mylist.Get(_i));
 //BA.debugLineNum = 1243;BA.debugLine="File.Delete(File.DirInternal & \"/cachelc\",MyFi";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc",_myfile);
 }
};
 //BA.debugLineNum = 1245;BA.debugLine="End Sub";
return "";
}
public static String  _botonabout_click() throws Exception{
 //BA.debugLineNum = 1574;BA.debugLine="Sub botonAbout_Click";
 //BA.debugLineNum = 1576;BA.debugLine="cantidadClicks=cantidadClicks+1";
_vvvvvvvvvvv3 = (int) (_vvvvvvvvvvv3+1);
 //BA.debugLineNum = 1578;BA.debugLine="If cantidadClicks >= 8 Then";
if (_vvvvvvvvvvv3>=8) { 
 //BA.debugLineNum = 1579;BA.debugLine="panelabout.SetBackgroundImage(LoadBitmap(File.D";
mostCurrent._vvvvvvvvvvv4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marcelo.jpg").getObject()));
 //BA.debugLineNum = 1580;BA.debugLine="cantidadClicks=0";
_vvvvvvvvvvv3 = (int) (0);
 };
 //BA.debugLineNum = 1582;BA.debugLine="End Sub";
return "";
}
public static String  _boverflow_click() throws Exception{
 //BA.debugLineNum = 688;BA.debugLine="Sub boverflow_Click";
 //BA.debugLineNum = 689;BA.debugLine="overflowMenu.show";
mostCurrent._vvvvvvvvvvv5._vvvvvv0();
 //BA.debugLineNum = 690;BA.debugLine="End Sub";
return "";
}
public static String  _brefresh_click() throws Exception{
 //BA.debugLineNum = 693;BA.debugLine="Sub brefresh_Click";
 //BA.debugLineNum = 696;BA.debugLine="a.InitializeRotateCenter(\"\",0,360,brefresh)";
mostCurrent._vvvvvvvvvv4.InitializeRotateCenter(mostCurrent.activityBA,"",(float) (0),(float) (360),(android.view.View)(mostCurrent._vvvvvvvvvv5.getObject()));
 //BA.debugLineNum = 697;BA.debugLine="a.Duration=500";
mostCurrent._vvvvvvvvvv4.setDuration((long) (500));
 //BA.debugLineNum = 698;BA.debugLine="a.RepeatCount=-1";
mostCurrent._vvvvvvvvvv4.setRepeatCount((int) (-1));
 //BA.debugLineNum = 699;BA.debugLine="a.Start(brefresh)";
mostCurrent._vvvvvvvvvv4.Start((android.view.View)(mostCurrent._vvvvvvvvvv5.getObject()));
 //BA.debugLineNum = 700;BA.debugLine="actualizarAll";
_vvvvvvvvvv3();
 //BA.debugLineNum = 701;BA.debugLine="End Sub";
return "";
}
public static String  _cambiarfont_click() throws Exception{
 //BA.debugLineNum = 1061;BA.debugLine="Sub cambiarFont_Click";
 //BA.debugLineNum = 1063;BA.debugLine="webActual.LoadUrl(\"javascript:resizeText(1);\")";
mostCurrent._vvvvvvvvvvv6.LoadUrl("javascript:resizeText(1);");
 //BA.debugLineNum = 1064;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv7(int _numero) throws Exception{
flm.b4a.animationplus.AnimationPlusWrapper _anim = null;
flm.b4a.animationplus.AnimationPlusWrapper _anim2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pan2 = null;
 //BA.debugLineNum = 1098;BA.debugLine="Sub cargarNota (numero As Int)";
 //BA.debugLineNum = 1100;BA.debugLine="Dim anim As AnimationPlus";
_anim = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 1101;BA.debugLine="Dim anim2 As AnimationPlus";
_anim2 = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 1103;BA.debugLine="pagerNotas.top=0";
mostCurrent._vvvvvvvvvvv0.setTop((int) (0));
 //BA.debugLineNum = 1104;BA.debugLine="pagerNotas.BringToFront";
mostCurrent._vvvvvvvvvvv0.BringToFront();
 //BA.debugLineNum = 1105;BA.debugLine="pagerNotas.Visible=True";
mostCurrent._vvvvvvvvvvv0.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1106;BA.debugLine="pagerNotas.GotoPage(numero-1,False)";
mostCurrent._vvvvvvvvvvv0.GotoPage((int) (_numero-1),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1110;BA.debugLine="Dim pan As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1111;BA.debugLine="Dim pan2 As Panel";
_pan2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1112;BA.debugLine="pan=containerNotas.GetPageObject(numero-1)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvvv1.GetPageObject((int) (_numero-1))));
 //BA.debugLineNum = 1113;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 1114;BA.debugLine="webActual=pan2.GetView(0)";
mostCurrent._vvvvvvvvvvv6.setObject((android.webkit.WebView)(_pan2.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 1115;BA.debugLine="panelActual=pan2";
mostCurrent._vvvvvvvvvvvv1 = _pan2;
 //BA.debugLineNum = 1117;BA.debugLine="g2.SetOnTouchListener(pagerNotas , \"GestosNotas\"";
mostCurrent._vvvvvvvvvvvv2.SetOnTouchListener(mostCurrent.activityBA,(android.view.View)(mostCurrent._vvvvvvvvvvv0.getObject()),"GestosNotas");
 //BA.debugLineNum = 1118;BA.debugLine="g2.SetOnTouchListener(webActual , \"GestosNotas\")";
mostCurrent._vvvvvvvvvvvv2.SetOnTouchListener(mostCurrent.activityBA,(android.view.View)(mostCurrent._vvvvvvvvvvv6.getObject()),"GestosNotas");
 //BA.debugLineNum = 1123;BA.debugLine="pager.Top=0";
mostCurrent._vvvvvvvvvv1.setTop((int) (0));
 //BA.debugLineNum = 1125;BA.debugLine="anim2.InitializeScaleCenter(\"\", 1, 1, 0, 0, page";
_anim2.InitializeScaleCenter(mostCurrent.activityBA,"",(float) (1),(float) (1),(float) (0),(float) (0),(android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()));
 //BA.debugLineNum = 1128;BA.debugLine="anim2.PersistAfter=True";
_anim2.setPersistAfter(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1129;BA.debugLine="anim2.Duration=500";
_anim2.setDuration((long) (500));
 //BA.debugLineNum = 1130;BA.debugLine="anim2.Start(pager)";
_anim2.Start((android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()));
 //BA.debugLineNum = 1134;BA.debugLine="anim.InitializeTranslate(\"animacionNotas\",0,A";
_anim.InitializeTranslate(mostCurrent.activityBA,"animacionNotas",(float) (0),(float) (mostCurrent._activity.getHeight()),(float) (0),(float) (0));
 //BA.debugLineNum = 1136;BA.debugLine="anim.Duration=500";
_anim.setDuration((long) (500));
 //BA.debugLineNum = 1137;BA.debugLine="anim.Start(pagerNotas)";
_anim.Start((android.view.View)(mostCurrent._vvvvvvvvvvv0.getObject()));
 //BA.debugLineNum = 1139;BA.debugLine="TapaVisible=False";
_vv1 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1141;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv3(boolean _encendido) throws Exception{
int _colorbarra = 0;
int _n = 0;
 //BA.debugLineNum = 1205;BA.debugLine="Sub cicladocolor ( encendido As Boolean)";
 //BA.debugLineNum = 1207;BA.debugLine="Dim colorBarra As Int";
_colorbarra = 0;
 //BA.debugLineNum = 1210;BA.debugLine="If encendido Then";
if (_encendido) { 
 //BA.debugLineNum = 1211;BA.debugLine="lFecha(0).Text=\"Descargando ultimas notici";
mostCurrent._vvvvvvvvvvvv4[(int) (0)].setText(BA.ObjectToCharSequence("Descargando ultimas noticias..."));
 //BA.debugLineNum = 1212;BA.debugLine="panelcolor(0).Width=0";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setWidth((int) (0));
 //BA.debugLineNum = 1213;BA.debugLine="panelcolor(1).Width=0";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setWidth((int) (0));
 //BA.debugLineNum = 1214;BA.debugLine="panelcolor(0).Visible=True";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1215;BA.debugLine="panelcolor(1).Visible=True";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1216;BA.debugLine="panelcolor(ciclo).BringToFront";
mostCurrent._vvvvvvvvvvvv5[_vvvvvvvvvvvv6].BringToFront();
 //BA.debugLineNum = 1217;BA.debugLine="panelcolor(ciclo).Color=Colors.ARGB(40,Rnd(0,255";
mostCurrent._vvvvvvvvvvvv5[_vvvvvvvvvvvv6].setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (40),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255))));
 //BA.debugLineNum = 1218;BA.debugLine="For n= 0 To Activity.Width Step Activity.Width /";
{
final int step10 = (int) (mostCurrent._activity.getWidth()/(double)50);
final int limit10 = mostCurrent._activity.getWidth();
for (_n = (int) (0) ; (step10 > 0 && _n <= limit10) || (step10 < 0 && _n >= limit10); _n = ((int)(0 + _n + step10)) ) {
 //BA.debugLineNum = 1219;BA.debugLine="panelcolor(ciclo).Width=n";
mostCurrent._vvvvvvvvvvvv5[_vvvvvvvvvvvv6].setWidth(_n);
 //BA.debugLineNum = 1220;BA.debugLine="panelcolor(ciclo).Left=50%x - (n/2)";
mostCurrent._vvvvvvvvvvvv5[_vvvvvvvvvvvv6].setLeft((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-(_n/(double)2)));
 //BA.debugLineNum = 1221;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
};
 //BA.debugLineNum = 1223;BA.debugLine="If ciclo=0 Then ciclo=1 Else ciclo=0";
if (_vvvvvvvvvvvv6==0) { 
_vvvvvvvvvvvv6 = (int) (1);}
else {
_vvvvvvvvvvvv6 = (int) (0);};
 };
 //BA.debugLineNum = 1227;BA.debugLine="If encendido = False Then";
if (_encendido==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1228;BA.debugLine="lFecha(0).Text=fechaTapa";
mostCurrent._vvvvvvvvvvvv4[(int) (0)].setText(BA.ObjectToCharSequence(mostCurrent._vvvv0));
 //BA.debugLineNum = 1229;BA.debugLine="panelcolor(0).Width=0";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setWidth((int) (0));
 //BA.debugLineNum = 1230;BA.debugLine="panelcolor(1).Width=0";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setWidth((int) (0));
 //BA.debugLineNum = 1231;BA.debugLine="panelcolor(0).Visible=False";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1232;BA.debugLine="panelcolor(1).Visible=False";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1235;BA.debugLine="End Sub";
return "";
}
public static String  _compartirnoticia_click() throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 1076;BA.debugLine="Sub compartirNoticia_Click";
 //BA.debugLineNum = 1079;BA.debugLine="ToastMessageShow(\"Compartir esta nota...\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Compartir esta nota..."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1081;BA.debugLine="Dim Out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1082;BA.debugLine="Out = File.OpenOutput(File.DirRootExternal, \"comp";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"compartir.jpg",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1083;BA.debugLine="webActual.CaptureBitmap.WriteToStream(Out, 100, \"";
mostCurrent._vvvvvvvvvvv6.CaptureBitmap().WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"JPEG"));
 //BA.debugLineNum = 1084;BA.debugLine="Out.Close";
_out.Close();
 //BA.debugLineNum = 1086;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 1087;BA.debugLine="share.sharebinary(\"file://\" & File.DirRootExterna";
_share.sharebinary(mostCurrent.activityBA,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/compartir.jpg","image/jpg","Do you see this?","Mira esta nota que salio en El Quid de la cuestión (http://www.elquiddelacuestion.com.ar)");
 //BA.debugLineNum = 1092;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv6() throws Exception{
int _d = 0;
int _m = 0;
int _year = 0;
String _dia = "";
String _mes = "";
 //BA.debugLineNum = 894;BA.debugLine="Sub crearFecha";
 //BA.debugLineNum = 896;BA.debugLine="Log(\"crearFecha\")";
anywheresoftware.b4a.keywords.Common.Log("crearFecha");
 //BA.debugLineNum = 898;BA.debugLine="Dim d As Int";
_d = 0;
 //BA.debugLineNum = 899;BA.debugLine="Dim m As Int";
_m = 0;
 //BA.debugLineNum = 900;BA.debugLine="Dim year As Int";
_year = 0;
 //BA.debugLineNum = 901;BA.debugLine="Dim dia As String";
_dia = "";
 //BA.debugLineNum = 902;BA.debugLine="Dim mes As String";
_mes = "";
 //BA.debugLineNum = 906;BA.debugLine="d= DateTime.GetDayOfWeek(DateTime.Now)";
_d = anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfWeek(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 907;BA.debugLine="Select d";
switch (_d) {
case 1: {
 //BA.debugLineNum = 909;BA.debugLine="dia=\"Domingo\"";
_dia = "Domingo";
 break; }
case 2: {
 //BA.debugLineNum = 911;BA.debugLine="dia=\"Lunes\"";
_dia = "Lunes";
 break; }
case 3: {
 //BA.debugLineNum = 913;BA.debugLine="dia=\"Martes\"";
_dia = "Martes";
 break; }
case 4: {
 //BA.debugLineNum = 915;BA.debugLine="dia=\"Miércoles\"";
_dia = "Miércoles";
 break; }
case 5: {
 //BA.debugLineNum = 917;BA.debugLine="dia=\"Jueves\"";
_dia = "Jueves";
 break; }
case 6: {
 //BA.debugLineNum = 919;BA.debugLine="dia=\"Viernes\"";
_dia = "Viernes";
 break; }
case 7: {
 //BA.debugLineNum = 921;BA.debugLine="dia=\"Sábado\"";
_dia = "Sábado";
 break; }
}
;
 //BA.debugLineNum = 924;BA.debugLine="m= DateTime.GetMonth(DateTime.Now)";
_m = anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 925;BA.debugLine="Select m";
switch (_m) {
case 1: {
 //BA.debugLineNum = 927;BA.debugLine="mes=\"Enero\"";
_mes = "Enero";
 break; }
case 2: {
 //BA.debugLineNum = 929;BA.debugLine="mes=\"Febrero\"";
_mes = "Febrero";
 break; }
case 3: {
 //BA.debugLineNum = 931;BA.debugLine="mes=\"Marzo\"";
_mes = "Marzo";
 break; }
case 4: {
 //BA.debugLineNum = 933;BA.debugLine="mes=\"Abril\"";
_mes = "Abril";
 break; }
case 5: {
 //BA.debugLineNum = 935;BA.debugLine="mes=\"Mayo\"";
_mes = "Mayo";
 break; }
case 6: {
 //BA.debugLineNum = 937;BA.debugLine="mes=\"Junio\"";
_mes = "Junio";
 break; }
case 7: {
 //BA.debugLineNum = 939;BA.debugLine="mes=\"Julio\"";
_mes = "Julio";
 break; }
case 8: {
 //BA.debugLineNum = 941;BA.debugLine="mes=\"Agosto\"";
_mes = "Agosto";
 break; }
case 9: {
 //BA.debugLineNum = 943;BA.debugLine="mes=\"Septiembre\"";
_mes = "Septiembre";
 break; }
case 10: {
 //BA.debugLineNum = 945;BA.debugLine="mes=\"Octubre\"";
_mes = "Octubre";
 break; }
case 11: {
 //BA.debugLineNum = 947;BA.debugLine="mes=\"Noviembre\"";
_mes = "Noviembre";
 break; }
case 12: {
 //BA.debugLineNum = 949;BA.debugLine="mes=\"Diciembre\"";
_mes = "Diciembre";
 break; }
}
;
 //BA.debugLineNum = 952;BA.debugLine="d=DateTime.GetDayOfMonth(DateTime.Now)";
_d = anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 953;BA.debugLine="year=DateTime.GetYear(DateTime.Now)";
_year = anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 954;BA.debugLine="fechaTapa= dia & \" \" & d & \" \" & mes & \" \" & ye";
mostCurrent._vvvv0 = _dia+" "+BA.NumberToString(_d)+" "+_mes+" "+BA.NumberToString(_year);
 //BA.debugLineNum = 956;BA.debugLine="DateTime.DateFormat=\"dd/MM/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yyyy");
 //BA.debugLineNum = 957;BA.debugLine="fechaAdentro=DateTime.Date(DateTime.Now)";
mostCurrent._vvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 959;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvv6() throws Exception{
String _dircache = "";
int _n = 0;
 //BA.debugLineNum = 621;BA.debugLine="Sub crearPaginasNotas";
 //BA.debugLineNum = 623;BA.debugLine="Dim dirCache As String";
_dircache = "";
 //BA.debugLineNum = 624;BA.debugLine="dirCache= File.DirInternal & \"/cachelc\"";
_dircache = anywheresoftware.b4a.keywords.Common.File.getDirInternal()+"/cachelc";
 //BA.debugLineNum = 628;BA.debugLine="If containerNotas.Count > 0 Then";
if (mostCurrent._vvvvvvvvvvv1.getCount()>0) { 
 //BA.debugLineNum = 629;BA.debugLine="For n= containerNotas.Count-1  To 0 Step -1";
{
final int step4 = (int) (-1);
final int limit4 = (int) (0);
for (_n = (int) (mostCurrent._vvvvvvvvvvv1.getCount()-1) ; (step4 > 0 && _n <= limit4) || (step4 < 0 && _n >= limit4); _n = ((int)(0 + _n + step4)) ) {
 //BA.debugLineNum = 630;BA.debugLine="containerNotas.DeletePage(n)";
mostCurrent._vvvvvvvvvvv1.DeletePage(_n);
 }
};
 };
 //BA.debugLineNum = 635;BA.debugLine="For n= 0 To notas.Length-1";
{
final int step8 = 1;
final int limit8 = (int) (_v6.length-1);
for (_n = (int) (0) ; (step8 > 0 && _n <= limit8) || (step8 < 0 && _n >= limit8); _n = ((int)(0 + _n + step8)) ) {
 //BA.debugLineNum = 636;BA.debugLine="agregarPagina (n)";
_vvvvvvvvvv7(_n);
 }
};
 //BA.debugLineNum = 638;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv3() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper[] _paneles = null;
anywheresoftware.b4a.objects.PanelWrapper _paneltapa = null;
int _n = 0;
 //BA.debugLineNum = 299;BA.debugLine="Sub dibujarLayout";
 //BA.debugLineNum = 300;BA.debugLine="Log( \"dibujarlayout\")";
anywheresoftware.b4a.keywords.Common.Log("dibujarlayout");
 //BA.debugLineNum = 302;BA.debugLine="Dim paneles(3) As Panel";
_paneles = new anywheresoftware.b4a.objects.PanelWrapper[(int) (3)];
{
int d0 = _paneles.length;
for (int i0 = 0;i0 < d0;i0++) {
_paneles[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 303;BA.debugLine="paneles(0).Initialize(\"\")";
_paneles[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 304;BA.debugLine="paneles(1).Initialize(\"\")";
_paneles[(int) (1)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 305;BA.debugLine="paneles(2).Initialize(\"\")";
_paneles[(int) (2)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 307;BA.debugLine="Dim paneltapa As Panel";
_paneltapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 309;BA.debugLine="container.Initialize";
mostCurrent._vvvvvvvvvv0.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 315;BA.debugLine="For n=0 To 2";
{
final int step8 = 1;
final int limit8 = (int) (2);
for (_n = (int) (0) ; (step8 > 0 && _n <= limit8) || (step8 < 0 && _n >= limit8); _n = ((int)(0 + _n + step8)) ) {
 //BA.debugLineNum = 316;BA.debugLine="web(n).Initialize(\"web\")";
mostCurrent._vvvv7[_n].Initialize(mostCurrent.activityBA,"web");
 //BA.debugLineNum = 317;BA.debugLine="web(n).ZoomEnabled= False";
mostCurrent._vvvv7[_n].setZoomEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 318;BA.debugLine="web(n).Tag=n";
mostCurrent._vvvv7[_n].setTag((Object)(_n));
 //BA.debugLineNum = 319;BA.debugLine="barra(n).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvv0[_n].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 320;BA.debugLine="Linea(n).Initialize(\"\")";
mostCurrent._vvvvvvvv4[_n].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 321;BA.debugLine="contenedor(n).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv1[_n].Initialize(mostCurrent.activityBA,"");
 }
};
 //BA.debugLineNum = 325;BA.debugLine="Mcontainer.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 330;BA.debugLine="paneltapa.Initialize(\"\")";
_paneltapa.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 331;BA.debugLine="paneltapa.Color=Colors.DarkGray";
_paneltapa.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 333;BA.debugLine="panelimpresa.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 334;BA.debugLine="panelimpresa.color=Colors.Yellow";
mostCurrent._vvvvvvvvvvvvv3.setColor(anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 336;BA.debugLine="paneltapa.AddView(panelimpresa, 0, 30dip, 100%x,";
_paneltapa.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv3.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (1.38*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)));
 //BA.debugLineNum = 344;BA.debugLine="contenedor(0).AddView(web(0), 0,60dip, 100%x, 100";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvv7[(int) (0)].getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))));
 //BA.debugLineNum = 348;BA.debugLine="contenedor(1).SetBackgroundImage(LoadBitmap(File.";
mostCurrent._vvvvvvvvvvvvv1[(int) (1)].SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"otros.jpg").getObject()));
 //BA.debugLineNum = 351;BA.debugLine="paneles(0).AddView(paneltapa,0,0,100%x,100%y)";
_paneles[(int) (0)].AddView((android.view.View)(_paneltapa.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 352;BA.debugLine="paneles(1).AddView(contenedor(0),0,0,100%x,100%y";
_paneles[(int) (1)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv1[(int) (0)].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 353;BA.debugLine="paneles(2).AddView(contenedor(1),0,0,100%x,100%y";
_paneles[(int) (2)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv1[(int) (1)].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 361;BA.debugLine="container.AddPage(paneles(0),\"menu\")";
mostCurrent._vvvvvvvvvv0.AddPage((android.view.View)(_paneles[(int) (0)].getObject()),"menu");
 //BA.debugLineNum = 362;BA.debugLine="container.AddPage(paneles(1),\"Tapa\")";
mostCurrent._vvvvvvvvvv0.AddPage((android.view.View)(_paneles[(int) (1)].getObject()),"Tapa");
 //BA.debugLineNum = 363;BA.debugLine="container.AddPage(paneles(2),\"nota\")";
mostCurrent._vvvvvvvvvv0.AddPage((android.view.View)(_paneles[(int) (2)].getObject()),"nota");
 //BA.debugLineNum = 370;BA.debugLine="pager.Initialize(container, \"Pager\")";
mostCurrent._vvvvvvvvvv1.Initialize(mostCurrent.activityBA,mostCurrent._vvvvvvvvvv0,"Pager");
 //BA.debugLineNum = 371;BA.debugLine="pager.Color=Colors.Black";
mostCurrent._vvvvvvvvvv1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 372;BA.debugLine="pager.PagingEnabled=False";
mostCurrent._vvvvvvvvvv1.setPagingEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 378;BA.debugLine="Activity.AddView(pager,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 384;BA.debugLine="lFecha(0).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvv4[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 385;BA.debugLine="linea2(0).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv4[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 387;BA.debugLine="boverflow.Initialize(\"boverflow\")";
mostCurrent._vvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"boverflow");
 //BA.debugLineNum = 388;BA.debugLine="brefresh.Initialize(\"brefresh\")";
mostCurrent._vvvvvvvvvv5.Initialize(mostCurrent.activityBA,"brefresh");
 //BA.debugLineNum = 390;BA.debugLine="linea2(0).Color=Colors.ARGB(240,50,50,50)";
mostCurrent._vvvvvvvvvvvvv4[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (240),(int) (50),(int) (50),(int) (50)));
 //BA.debugLineNum = 391;BA.debugLine="Linea(0).Color=Colors.ARGB(240,50,50,50)";
mostCurrent._vvvvvvvv4[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (240),(int) (50),(int) (50),(int) (50)));
 //BA.debugLineNum = 395;BA.debugLine="barra(0).Color= Colors.ARGB(255,239,239,239)";
mostCurrent._vvvvvvvvvvvv0[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (239),(int) (239),(int) (239)));
 //BA.debugLineNum = 396;BA.debugLine="blogo(0).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv6[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 397;BA.debugLine="blogo(0).SetBackgroundImage(LoadBitmap (File.DirA";
mostCurrent._vvvvvvvvvvvvv6[(int) (0)].SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"logonegro.png").getObject()));
 //BA.debugLineNum = 399;BA.debugLine="boverflow.SetBackgroundImage(LoadBitmap (File.Dir";
mostCurrent._vvvvvvvvvvvvv5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png").getObject()));
 //BA.debugLineNum = 400;BA.debugLine="brefresh.SetBackgroundImage(LoadBitmap (File.DirA";
mostCurrent._vvvvvvvvvv5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"refresh.png").getObject()));
 //BA.debugLineNum = 402;BA.debugLine="barra(0).AddView(boverflow,Activity.Width-35dip,1";
mostCurrent._vvvvvvvvvvvv0[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv5.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (14)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 403;BA.debugLine="barra(0).AddView(brefresh,Activity.Width-65dip,18";
mostCurrent._vvvvvvvvvvvv0[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvv5.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 405;BA.debugLine="barra(0).AddView(blogo(0),5dip,5dip,277dip,50dip)";
mostCurrent._vvvvvvvvvvvv0[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv6[(int) (0)].getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (277)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 407;BA.debugLine="contenedor(0).AddView(barra(0),0,0,100%x,60dip)";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvv0[(int) (0)].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 415;BA.debugLine="panelcolor(0).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 416;BA.debugLine="panelcolor(1).Initialize(\"\")";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 417;BA.debugLine="contenedor(0).AddView(panelcolor(0),0,61dip,100%x";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvv5[(int) (0)].getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (61)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (19)));
 //BA.debugLineNum = 418;BA.debugLine="contenedor(0).AddView(panelcolor(1),0,61dip,100%x";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvv5[(int) (1)].getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (61)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (19)));
 //BA.debugLineNum = 420;BA.debugLine="panelcolor(0).Color=Colors.Blue";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 421;BA.debugLine="panelcolor(1).Color=Colors.Blue";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 423;BA.debugLine="panelcolor(0).Visible=False";
mostCurrent._vvvvvvvvvvvv5[(int) (0)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 424;BA.debugLine="panelcolor(1).Visible=False";
mostCurrent._vvvvvvvvvvvv5[(int) (1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 432;BA.debugLine="overflowMenu.Initialize(Activity, Me, \"overflowMe";
mostCurrent._vvvvvvvvvvv5._initialize(mostCurrent.activityBA,mostCurrent._activity,telefonomain.getObject(),"overflowMenu",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)));
 //BA.debugLineNum = 433;BA.debugLine="overflowMenu.AddItem(\"Ir a la web de EQC\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Ir a la web de EQC");
 //BA.debugLineNum = 434;BA.debugLine="overflowMenu.AddItem(\"Acerca de...\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Acerca de...");
 //BA.debugLineNum = 435;BA.debugLine="If notActivas= \"no\" Then overflowMenu.AddItem(\"Ac";
if ((_vv2).equals("no")) { 
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Activar Notificaciones");};
 //BA.debugLineNum = 436;BA.debugLine="If notActivas= \"si\" Then overflowMenu.AddItem(\"Si";
if ((_vv2).equals("si")) { 
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Silenciar Notificaciones");};
 //BA.debugLineNum = 437;BA.debugLine="overflowMenu.AddItem(\"Salir\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Salir");
 //BA.debugLineNum = 445;BA.debugLine="panelTTs.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvv7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 446;BA.debugLine="panelTTs.Color=Colors.Cyan";
mostCurrent._vvvvvvvvvvvvv7.setColor(anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 447;BA.debugLine="Activity.AddView(panelTTs,0,Activity.Height-100di";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv7.getObject()),(int) (0),(int) (mostCurrent._activity.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 449;BA.debugLine="botonPararTTS.Initialize(\"pararTTS\")";
mostCurrent._vvvvvvvvvvvvv0.Initialize(mostCurrent.activityBA,"pararTTS");
 //BA.debugLineNum = 450;BA.debugLine="botonPausarTTS.Initialize(\"pausarTTS\")";
mostCurrent._vvvvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"pausarTTS");
 //BA.debugLineNum = 453;BA.debugLine="botonPararTTS.SetBackgroundImage(LoadBitmap (File";
mostCurrent._vvvvvvvvvvvvv0.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"stop.png").getObject()));
 //BA.debugLineNum = 454;BA.debugLine="botonPausarTTS.SetBackgroundImage(LoadBitmap (Fil";
mostCurrent._vvvvvvvvvvvvvv1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pause.png").getObject()));
 //BA.debugLineNum = 457;BA.debugLine="panelTTs.AddView(botonPararTTS,0,0,70dip,70dip)";
mostCurrent._vvvvvvvvvvvvv7.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvv0.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 458;BA.debugLine="panelTTs.AddView(botonPausarTTS,0,0,70dip,70dip)";
mostCurrent._vvvvvvvvvvvvv7.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvv1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 462;BA.debugLine="botonPararTTS.Top= (panelTTs.Height -  botonParar";
mostCurrent._vvvvvvvvvvvvv0.setTop((int) ((mostCurrent._vvvvvvvvvvvvv7.getHeight()-mostCurrent._vvvvvvvvvvvvv0.getHeight())/(double)2));
 //BA.debugLineNum = 463;BA.debugLine="botonPausarTTS.Top=botonPararTTS.Top";
mostCurrent._vvvvvvvvvvvvvv1.setTop(mostCurrent._vvvvvvvvvvvvv0.getTop());
 //BA.debugLineNum = 465;BA.debugLine="botonPausarTTS.left=(panelTTs.Width/2)+20dip";
mostCurrent._vvvvvvvvvvvvvv1.setLeft((int) ((mostCurrent._vvvvvvvvvvvvv7.getWidth()/(double)2)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 466;BA.debugLine="botonPararTTS.left=(panelTTs.Width/2)-20dip-boton";
mostCurrent._vvvvvvvvvvvvv0.setLeft((int) ((mostCurrent._vvvvvvvvvvvvv7.getWidth()/(double)2)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))-mostCurrent._vvvvvvvvvvvvv0.getWidth()));
 //BA.debugLineNum = 470;BA.debugLine="panelTTs.Visible=False";
mostCurrent._vvvvvvvvvvvvv7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 472;BA.debugLine="extras.addJavascriptInterface(web(0),\"B4A\")";
mostCurrent._vvvvvvvvvvvvvv2.addJavascriptInterface(mostCurrent.activityBA,(android.webkit.WebView)(mostCurrent._vvvv7[(int) (0)].getObject()),"B4A");
 //BA.debugLineNum = 478;BA.debugLine="containerNotas.Initialize";
mostCurrent._vvvvvvvvvvv1.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 479;BA.debugLine="pagerNotas.Initialize(containerNotas,\"pagerNotas\"";
mostCurrent._vvvvvvvvvvv0.Initialize(mostCurrent.activityBA,mostCurrent._vvvvvvvvvvv1,"pagerNotas");
 //BA.debugLineNum = 480;BA.debugLine="Activity.AddView(pagerNotas,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvv0.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 481;BA.debugLine="pagerNotas.visible=False";
mostCurrent._vvvvvvvvvvv0.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 482;BA.debugLine="pagerNotas.Color=Colors.LightGray";
mostCurrent._vvvvvvvvvvv0.setColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 483;BA.debugLine="pager.GotoPage(1,True)";
mostCurrent._vvvvvvvvvv1.GotoPage((int) (1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 490;BA.debugLine="avisoRefresh.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvv3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 491;BA.debugLine="avisoRefresh.SetBackgroundImage(LoadBitmap(File.D";
mostCurrent._vvvvvvvvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"flechadown.png").getObject()));
 //BA.debugLineNum = 492;BA.debugLine="contenedor(0).AddView(avisoRefresh,0,70dip,80dip,";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvv3.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 494;BA.debugLine="avisoRefresh.SendToBack";
mostCurrent._vvvvvvvvvvvvvv3.SendToBack();
 //BA.debugLineNum = 495;BA.debugLine="avisoRefresh.Visible=True";
mostCurrent._vvvvvvvvvvvvvv3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 497;BA.debugLine="captionRefresh.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 498;BA.debugLine="captionRefresh.Text=\"Suelte ahora para actualizar";
mostCurrent._vvvvvvvvvvvvvv4.setText(BA.ObjectToCharSequence("Suelte ahora para actualizar."));
 //BA.debugLineNum = 499;BA.debugLine="captionRefresh.TextColor=Colors.White";
mostCurrent._vvvvvvvvvvvvvv4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 500;BA.debugLine="captionRefresh.TextSize=20";
mostCurrent._vvvvvvvvvvvvvv4.setTextSize((float) (20));
 //BA.debugLineNum = 501;BA.debugLine="contenedor(0).AddView(captionRefresh,0,0,100%x, 4";
mostCurrent._vvvvvvvvvvvvv1[(int) (0)].AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvv4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 502;BA.debugLine="captionRefresh.Gravity=Gravity.CENTER_HORIZONTAL";
mostCurrent._vvvvvvvvvvvvvv4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 503;BA.debugLine="captionRefresh.SendToBack";
mostCurrent._vvvvvvvvvvvvvv4.SendToBack();
 //BA.debugLineNum = 504;BA.debugLine="captionRefresh.Visible=False";
mostCurrent._vvvvvvvvvvvvvv4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 506;BA.debugLine="panelabout.Initialize(\"panelabout\")";
mostCurrent._vvvvvvvvvvv4.Initialize(mostCurrent.activityBA,"panelabout");
 //BA.debugLineNum = 507;BA.debugLine="panelabout.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._vvvvvvvvvvv4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.jpg").getObject()));
 //BA.debugLineNum = 508;BA.debugLine="panelabout.Visible=False";
mostCurrent._vvvvvvvvvvv4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 511;BA.debugLine="botonAbout.Initialize(\"botonAbout\")";
mostCurrent._vvvvvvvvvvvvvv5.Initialize(mostCurrent.activityBA,"botonAbout");
 //BA.debugLineNum = 512;BA.debugLine="botonAbout.Color=Colors.ARGB(0,255,255,255)";
mostCurrent._vvvvvvvvvvvvvv5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 513;BA.debugLine="panelabout.AddView(botonAbout,0,0,100%x,20%y)";
mostCurrent._vvvvvvvvvvv4.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvv5.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (20),mostCurrent.activityBA));
 //BA.debugLineNum = 516;BA.debugLine="Activity.AddView(panelabout,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvv4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 519;BA.debugLine="g.SetOnTouchListener(web(0) , \"PullToRefresh\")";
mostCurrent._vvvvvvvvvvvvvv6.SetOnTouchListener(mostCurrent.activityBA,(android.view.View)(mostCurrent._vvvv7[(int) (0)].getObject()),"PullToRefresh");
 //BA.debugLineNum = 521;BA.debugLine="End Sub";
return "";
}
public static String  _finirtapa_animationend() throws Exception{
 //BA.debugLineNum = 1399;BA.debugLine="Sub finirtapa_AnimationEnd";
 //BA.debugLineNum = 1401;BA.debugLine="pagerNotas.Top=Activity.Height";
mostCurrent._vvvvvvvvvvv0.setTop(mostCurrent._activity.getHeight());
 //BA.debugLineNum = 1404;BA.debugLine="End Sub";
return "";
}
public static String  _finpulldown_animationend() throws Exception{
 //BA.debugLineNum = 1315;BA.debugLine="Sub finpulldown_AnimationEnd";
 //BA.debugLineNum = 1317;BA.debugLine="If pull=True Then pager.Top=50dip";
if (_vvvvvvvvvvvvvv7==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._vvvvvvvvvv1.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));};
 //BA.debugLineNum = 1318;BA.debugLine="If pull=False Then";
if (_vvvvvvvvvvvvvv7==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1319;BA.debugLine="pager.Top=0";
mostCurrent._vvvvvvvvvv1.setTop((int) (0));
 //BA.debugLineNum = 1320;BA.debugLine="actualizarAll";
_vvvvvvvvvv3();
 };
 //BA.debugLineNum = 1324;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvv0(String _urlstring) throws Exception{
String[] _partes = null;
 //BA.debugLineNum = 654;BA.debugLine="Sub fromJavascript ( urlstring As String)";
 //BA.debugLineNum = 656;BA.debugLine="Log(\"fromJavascript\")";
anywheresoftware.b4a.keywords.Common.Log("fromJavascript");
 //BA.debugLineNum = 658;BA.debugLine="Dim partes() As String";
_partes = new String[(int) (0)];
java.util.Arrays.fill(_partes,"");
 //BA.debugLineNum = 659;BA.debugLine="partes = Regex.Split(\"/\", urlstring)";
_partes = anywheresoftware.b4a.keywords.Common.Regex.Split("/",_urlstring);
 //BA.debugLineNum = 660;BA.debugLine="urlFromJS=partes(partes.Length-1)";
_v5 = _partes[(int) (_partes.length-1)];
 //BA.debugLineNum = 661;BA.debugLine="numeroNota=partes(partes.Length-2)";
mostCurrent._vvvvvvvvvvvvvvv1 = _partes[(int) (_partes.length-2)];
 //BA.debugLineNum = 662;BA.debugLine="If IsNumber(numeroNota) Then notaInicio=numeroNo";
if (anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._vvvvvvvvvvvvvvv1)) { 
_vvvvvvvvv1 = (int) ((double)(Double.parseDouble(mostCurrent._vvvvvvvvvvvvvvv1))-1);};
 //BA.debugLineNum = 663;BA.debugLine="offset=notaInicio-(pagFijas-1)";
_vvvvvvvv0 = (int) (_vvvvvvvvv1-(_vvvvvvvvv2-1));
 //BA.debugLineNum = 670;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvvvvvvvv2(Object _o,int _ptrid,int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 1370;BA.debugLine="Sub GestosNotas ( o As Object, ptrID As Int, actio";
 //BA.debugLineNum = 1373;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvvvvv3(anywheresoftware.b4a.objects.LabelWrapper _lb,boolean _r) throws Exception{
flm.b4a.animationplus.AnimationPlusWrapper _ani = null;
 //BA.debugLineNum = 1518;BA.debugLine="Sub girar(lb As Label, r As Boolean)";
 //BA.debugLineNum = 1519;BA.debugLine="Dim ani As AnimationPlus";
_ani = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 1520;BA.debugLine="If rotacion = r Then Return";
if (_vvvvvvvvvvvvvvv4==_r) { 
if (true) return "";};
 //BA.debugLineNum = 1521;BA.debugLine="If r=True Then ani.InitializeRotateCenter(\"\",0";
if (_r==anywheresoftware.b4a.keywords.Common.True) { 
_ani.InitializeRotateCenter(mostCurrent.activityBA,"",(float) (0),(float) (180),(android.view.View)(_lb.getObject()));};
 //BA.debugLineNum = 1522;BA.debugLine="If r=False Then ani.InitializeRotateCenter(\"\",180";
if (_r==anywheresoftware.b4a.keywords.Common.False) { 
_ani.InitializeRotateCenter(mostCurrent.activityBA,"",(float) (180),(float) (0),(android.view.View)(_lb.getObject()));};
 //BA.debugLineNum = 1524;BA.debugLine="captionRefresh.Visible=r";
mostCurrent._vvvvvvvvvvvvvv4.setVisible(_r);
 //BA.debugLineNum = 1525;BA.debugLine="rotacion=r";
_vvvvvvvvvvvvvvv4 = _r;
 //BA.debugLineNum = 1526;BA.debugLine="ani.Duration=300";
_ani.setDuration((long) (300));
 //BA.debugLineNum = 1527;BA.debugLine="ani.PersistAfter=True";
_ani.setPersistAfter(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1528;BA.debugLine="ani.Start(lb)";
_ani.Start((android.view.View)(_lb.getObject()));
 //BA.debugLineNum = 1529;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 29;BA.debugLine="Dim web(3) As WebView";
mostCurrent._vvvv7 = new anywheresoftware.b4a.objects.WebViewWrapper[(int) (3)];
{
int d0 = mostCurrent._vvvv7.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvv7[i0] = new anywheresoftware.b4a.objects.WebViewWrapper();
}
}
;
 //BA.debugLineNum = 35;BA.debugLine="Dim contenedor(3) As Panel";
mostCurrent._vvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.PanelWrapper[(int) (3)];
{
int d0 = mostCurrent._vvvvvvvvvvvvv1.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvv1[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 41;BA.debugLine="Dim Mcontainer As Panel";
mostCurrent._vvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim container As AHPageContainer";
mostCurrent._vvvvvvvvvv0 = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 49;BA.debugLine="Dim pager As AHViewPager";
mostCurrent._vvvvvvvvvv1 = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 50;BA.debugLine="Dim panelimpresa As Panel";
mostCurrent._vvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim containerNotas As AHPageContainer";
mostCurrent._vvvvvvvvvvv1 = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 58;BA.debugLine="Dim pagerNotas As AHViewPager";
mostCurrent._vvvvvvvvvvv0 = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 66;BA.debugLine="Dim barra(4) As Panel";
mostCurrent._vvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.PanelWrapper[(int) (4)];
{
int d0 = mostCurrent._vvvvvvvvvvvv0.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvv0[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 71;BA.debugLine="Dim Linea(4) As Panel";
mostCurrent._vvvvvvvv4 = new anywheresoftware.b4a.objects.PanelWrapper[(int) (4)];
{
int d0 = mostCurrent._vvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvv4[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 72;BA.debugLine="Dim blogo(4) As Button";
mostCurrent._vvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper[(int) (4)];
{
int d0 = mostCurrent._vvvvvvvvvvvvv6.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvv6[i0] = new anywheresoftware.b4a.objects.ButtonWrapper();
}
}
;
 //BA.debugLineNum = 73;BA.debugLine="Dim lFecha(4) As Label";
mostCurrent._vvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper[(int) (4)];
{
int d0 = mostCurrent._vvvvvvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvv4[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 74;BA.debugLine="Dim linea2(4) As Panel";
mostCurrent._vvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.PanelWrapper[(int) (4)];
{
int d0 = mostCurrent._vvvvvvvvvvvvv4.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvvv4[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 76;BA.debugLine="Dim bVolver As Button";
mostCurrent._vvvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Dim bFontChange As Button";
mostCurrent._vvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim bMenu As Button";
mostCurrent._vvvvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim brefresh As Button";
mostCurrent._vvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim boverflow As Button";
mostCurrent._vvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Dim panelSplash As Panel";
mostCurrent._vvvvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Dim etiqueta As Label";
mostCurrent._vvvvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Dim ProgressBar1 As ProgressBar";
mostCurrent._vvvvvvvvvvvvvvvv2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Dim panelabout As Panel";
mostCurrent._vvvvvvvvvvv4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Dim botonAbout As Button";
mostCurrent._vvvvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Dim SplashActiva As Boolean=False";
_vvvvvvvvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 102;BA.debugLine="Dim paginaCargada As String";
mostCurrent._vvvvvvvvvvvvvvvv4 = "";
 //BA.debugLineNum = 103;BA.debugLine="Dim numeroNota As String";
mostCurrent._vvvvvvvvvvvvvvv1 = "";
 //BA.debugLineNum = 108;BA.debugLine="Dim con As conexion";
mostCurrent._vvvvvvvvv7 = new elquid.mobile.reader.conexion();
 //BA.debugLineNum = 114;BA.debugLine="Dim overflowMenu As setMenu";
mostCurrent._vvvvvvvvvvv5 = new elquid.mobile.reader.setmenu();
 //BA.debugLineNum = 121;BA.debugLine="Dim extras As WebViewExtras";
mostCurrent._vvvvvvvvvvvvvv2 = new uk.co.martinpearman.b4a.webviewextras.WebViewExtras();
 //BA.debugLineNum = 126;BA.debugLine="Dim fechaTapa As String";
mostCurrent._vvvv0 = "";
 //BA.debugLineNum = 127;BA.debugLine="Dim fechaAdentro As String";
mostCurrent._vvvvvvvvvvvv7 = "";
 //BA.debugLineNum = 133;BA.debugLine="Dim pagFijas As Int=3";
_vvvvvvvvv2 = (int) (3);
 //BA.debugLineNum = 134;BA.debugLine="Dim notaInicio As Int=0";
_vvvvvvvvv1 = (int) (0);
 //BA.debugLineNum = 135;BA.debugLine="Dim offset As Int";
_vvvvvvvv0 = 0;
 //BA.debugLineNum = 137;BA.debugLine="Dim paginaActual As Int=1";
_vvvvvvvvvvvvvvvv5 = (int) (1);
 //BA.debugLineNum = 138;BA.debugLine="Dim paginaAnterior As Int=0";
_vvvvvvvvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 139;BA.debugLine="Dim movimiento As String=\"derecha\"";
mostCurrent._vvvvvvvvvvvvvvvv7 = "derecha";
 //BA.debugLineNum = 140;BA.debugLine="Dim gap As Int =20dip";
_vvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20));
 //BA.debugLineNum = 142;BA.debugLine="Dim webActual As WebView";
mostCurrent._vvvvvvvvvvv6 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Dim panelActual As Panel";
mostCurrent._vvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Dim panelcolor(2) As Panel";
mostCurrent._vvvvvvvvvvvv5 = new anywheresoftware.b4a.objects.PanelWrapper[(int) (2)];
{
int d0 = mostCurrent._vvvvvvvvvvvv5.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._vvvvvvvvvvvv5[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 149;BA.debugLine="Dim ciclo As Int =0";
_vvvvvvvvvvvv6 = (int) (0);
 //BA.debugLineNum = 154;BA.debugLine="Dim g As Gestures";
mostCurrent._vvvvvvvvvvvvvv6 = new anywheresoftware.b4a.agraham.gestures.Gestures();
 //BA.debugLineNum = 155;BA.debugLine="Dim Ylanding As Int";
_vvvvvvvvvvvvvvvvv1 = 0;
 //BA.debugLineNum = 156;BA.debugLine="Dim deltaY As Int";
_vvvvvvvvvvvvvvvvv2 = 0;
 //BA.debugLineNum = 157;BA.debugLine="Dim Xlanding As Int";
_vvvvvvvvvvvvvvvvv3 = 0;
 //BA.debugLineNum = 158;BA.debugLine="Dim deltaX As Int";
_vvvvvvvvvvvvvvvvv4 = 0;
 //BA.debugLineNum = 159;BA.debugLine="Dim webextendida As WebViewXtender";
mostCurrent._vvvvvvvvvvvvvvvvv5 = new thalmy.webviewxtended.xtender();
 //BA.debugLineNum = 160;BA.debugLine="Dim pull As Boolean =False";
_vvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 161;BA.debugLine="Dim avisoRefresh As Label";
mostCurrent._vvvvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Dim rotacion As Boolean =False";
_vvvvvvvvvvvvvvv4 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 163;BA.debugLine="Dim captionRefresh As Label";
mostCurrent._vvvvvvvvvvvvvv4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 168;BA.debugLine="Dim g2 As Gestures";
mostCurrent._vvvvvvvvvvvv2 = new anywheresoftware.b4a.agraham.gestures.Gestures();
 //BA.debugLineNum = 175;BA.debugLine="Dim TTS1 As TTS";
mostCurrent._vvvvvvvvvv2 = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 180;BA.debugLine="Type Data (Canvas As Canvas, panel As Panel,Bitma";
;
 //BA.debugLineNum = 186;BA.debugLine="Dim panelTTs As Panel";
mostCurrent._vvvvvvvvvvvvv7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Dim botonPararTTS As Button";
mostCurrent._vvvvvvvvvvvvv0 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 188;BA.debugLine="Dim botonPausarTTS As Button";
mostCurrent._vvvvvvvvvvvvvv1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Dim BotonPlayTTS As Button";
mostCurrent._vvvvvvvvvvvvvvvvv6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 190;BA.debugLine="Dim hablando As Boolean=False";
_vvvvvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 191;BA.debugLine="Dim PausaHablando As Boolean=False";
_vvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 192;BA.debugLine="Dim webAnterior As String";
mostCurrent._vvvvvvvvvvvvvvvvvv1 = "";
 //BA.debugLineNum = 194;BA.debugLine="Dim a As AnimationPlus";
mostCurrent._vvvvvvvvvv4 = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 196;BA.debugLine="Dim cantidadClicks As Int=0";
_vvvvvvvvvvv3 = (int) (0);
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _leertexto_click() throws Exception{
 //BA.debugLineNum = 1066;BA.debugLine="Sub leerTexto_Click";
 //BA.debugLineNum = 1068;BA.debugLine="pagerNotas.PagingEnabled=False";
mostCurrent._vvvvvvvvvvv0.setPagingEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1069;BA.debugLine="panelTTs.Visible=True";
mostCurrent._vvvvvvvvvvvvv7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1070;BA.debugLine="panelTTs.BringToFront";
mostCurrent._vvvvvvvvvvvvv7.BringToFront();
 //BA.debugLineNum = 1071;BA.debugLine="leerVozAlta";
_vvvvvvvvvvvvvvvvvv2();
 //BA.debugLineNum = 1073;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv2() throws Exception{
anywheresoftware.b4j.object.JavaObject _jtts = null;
String _archivo = "";
String _texto = "";
String[] _frases = null;
String _frase = "";
 //BA.debugLineNum = 1411;BA.debugLine="Sub leerVozAlta";
 //BA.debugLineNum = 1413;BA.debugLine="TTS1.SpeechRate=0.9";
mostCurrent._vvvvvvvvvv2.setSpeechRate((float) (0.9));
 //BA.debugLineNum = 1416;BA.debugLine="Dim jTTS As JavaObject = TTS1";
_jtts = new anywheresoftware.b4j.object.JavaObject();
_jtts.setObject((java.lang.Object)(mostCurrent._vvvvvvvvvv2.getObject()));
 //BA.debugLineNum = 1417;BA.debugLine="If jTTS.RunMethod(\"isSpeaking\", Null) = True Then";
if ((_jtts.RunMethod("isSpeaking",(Object[])(anywheresoftware.b4a.keywords.Common.Null))).equals((Object)(anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 1419;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1422;BA.debugLine="Dim archivo As String";
_archivo = "";
 //BA.debugLineNum = 1423;BA.debugLine="Dim texto As String";
_texto = "";
 //BA.debugLineNum = 1425;BA.debugLine="archivo=webActual.Tag";
_archivo = BA.ObjectToString(mostCurrent._vvvvvvvvvvv6.getTag());
 //BA.debugLineNum = 1426;BA.debugLine="texto=con.CargarHTML(archivo)";
_texto = mostCurrent._vvvvvvvvv7._vvvv3(_archivo);
 //BA.debugLineNum = 1428;BA.debugLine="texto=PlainText(texto)";
_texto = _vvvvvvvvvvvvvvvvvv3(_texto);
 //BA.debugLineNum = 1430;BA.debugLine="If TTS1.IsInitialized = False Then TTS1.Initializ";
if (mostCurrent._vvvvvvvvvv2.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._vvvvvvvvvv2.Initialize(processBA,"TTS1");};
 //BA.debugLineNum = 1431;BA.debugLine="Dim frases() As String = Regex.Split(\"\\.\", texto)";
_frases = anywheresoftware.b4a.keywords.Common.Regex.Split("\\.",_texto);
 //BA.debugLineNum = 1435;BA.debugLine="hablando=True";
_vvvvvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1436;BA.debugLine="PausaHablando=False";
_vvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1437;BA.debugLine="botonPausarTTS.SetBackgroundImage(LoadBitmap (Fil";
mostCurrent._vvvvvvvvvvvvvv1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pause.png").getObject()));
 //BA.debugLineNum = 1438;BA.debugLine="pagerNotas.PagingEnabled=False";
mostCurrent._vvvvvvvvvvv0.setPagingEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1440;BA.debugLine="For Each frase As String In frases";
final String[] group17 = _frases;
final int groupLen17 = group17.length;
for (int index17 = 0;index17 < groupLen17 ;index17++){
_frase = group17[index17];
 //BA.debugLineNum = 1442;BA.debugLine="Log(\"pausa= \" &  PausaHablando )";
anywheresoftware.b4a.keywords.Common.Log("pausa= "+BA.ObjectToString(_vvvvvvvvvvvvvvvvv0));
 //BA.debugLineNum = 1444;BA.debugLine="Do While PausaHablando=True";
while (_vvvvvvvvvvvvvvvvv0==anywheresoftware.b4a.keywords.Common.True) {
 //BA.debugLineNum = 1445;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
;
 //BA.debugLineNum = 1448;BA.debugLine="frase=frase.Trim";
_frase = _frase.trim();
 //BA.debugLineNum = 1451;BA.debugLine="If jTTS.RunMethod(\"isSpeaking\", Null) = False Th";
if ((_jtts.RunMethod("isSpeaking",(Object[])(anywheresoftware.b4a.keywords.Common.Null))).equals((Object)(anywheresoftware.b4a.keywords.Common.False))) { 
 //BA.debugLineNum = 1452;BA.debugLine="Log(\"-->\" &  frase &  \"<--\")";
anywheresoftware.b4a.keywords.Common.Log("-->"+_frase+"<--");
 //BA.debugLineNum = 1453;BA.debugLine="TTS1.Speak(frase, True)";
mostCurrent._vvvvvvvvvv2.Speak(_frase,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1456;BA.debugLine="If hablando=False Then";
if (_vvvvvvvvvvvvvvvvv7==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1457;BA.debugLine="pagerNotas.PagingEnabled=True";
mostCurrent._vvvvvvvvvvv0.setPagingEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1458;BA.debugLine="TTS1.Stop";
mostCurrent._vvvvvvvvvv2.Stop();
 //BA.debugLineNum = 1459;BA.debugLine="Exit";
if (true) break;
 };
 //BA.debugLineNum = 1464;BA.debugLine="Do While jTTS.RunMethod(\"isSpeaking\", Null) = Tr";
while ((_jtts.RunMethod("isSpeaking",(Object[])(anywheresoftware.b4a.keywords.Common.Null))).equals((Object)(anywheresoftware.b4a.keywords.Common.True)) && _vvvvvvvvvvvvvvvvv7==anywheresoftware.b4a.keywords.Common.True && _vvvvvvvvvvvvvvvvv0==anywheresoftware.b4a.keywords.Common.False) {
 //BA.debugLineNum = 1465;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
;
 //BA.debugLineNum = 1469;BA.debugLine="If PausaHablando=True Then TTS1.Stop";
if (_vvvvvvvvvvvvvvvvv0==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._vvvvvvvvvv2.Stop();};
 }
;
 //BA.debugLineNum = 1476;BA.debugLine="pagerNotas.PagingEnabled=True";
mostCurrent._vvvvvvvvvvv0.setPagingEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1477;BA.debugLine="panelTTs.Visible=False";
mostCurrent._vvvvvvvvvvvvv7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1482;BA.debugLine="End Sub";
return "";
}
public static String  _menucentral_click() throws Exception{
 //BA.debugLineNum = 703;BA.debugLine="Sub menuCentral_Click";
 //BA.debugLineNum = 704;BA.debugLine="VolverTapa";
_vvvvvvvvv0();
 //BA.debugLineNum = 705;BA.debugLine="End Sub";
return "";
}
public static String  _overflowmenu_click(Object _item) throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 707;BA.debugLine="Sub overflowMenu_Click(Item As Object)";
 //BA.debugLineNum = 710;BA.debugLine="Select Item";
switch (BA.switchObjectToInt(_item,(Object)("Silenciar Notificaciones"),(Object)("Activar Notificaciones"),(Object)("Ir a la web de EQC"),(Object)("Acerca de..."),(Object)("Salir"))) {
case 0: {
 //BA.debugLineNum = 715;BA.debugLine="silenciarNotificaciones";
_vvvvvvvvvvvvvvvvvv4();
 break; }
case 1: {
 //BA.debugLineNum = 719;BA.debugLine="silenciarNotificaciones";
_vvvvvvvvvvvvvvvvvv4();
 break; }
case 2: {
 //BA.debugLineNum = 723;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 724;BA.debugLine="StartActivity(p.OpenBrowser(\"http://elquiddelac";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://elquiddelacuestion.com.ar/")));
 break; }
case 3: {
 //BA.debugLineNum = 727;BA.debugLine="panelabout.SetBackgroundImage(LoadBitmap(File.D";
mostCurrent._vvvvvvvvvvv4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.jpg").getObject()));
 //BA.debugLineNum = 728;BA.debugLine="panelabout.Visible=True";
mostCurrent._vvvvvvvvvvv4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 729;BA.debugLine="panelabout.BringToFront";
mostCurrent._vvvvvvvvvvv4.BringToFront();
 break; }
case 4: {
 //BA.debugLineNum = 733;BA.debugLine="TTS1.Release";
mostCurrent._vvvvvvvvvv2.Release();
 //BA.debugLineNum = 734;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 735;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 741;BA.debugLine="End Sub";
return "";
}
public static String  _pager_pagechanged(int _position) throws Exception{
int _posicion = 0;
int _diferencia = 0;
flm.b4a.animationplus.AnimationPlusWrapper _animacion = null;
anywheresoftware.b4a.objects.PanelWrapper _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pan2 = null;
 //BA.debugLineNum = 768;BA.debugLine="Sub pager_PageChanged (Position As Int)";
 //BA.debugLineNum = 770;BA.debugLine="Log(\"pager_PageChanged \" & Position)";
anywheresoftware.b4a.keywords.Common.Log("pager_PageChanged "+BA.NumberToString(_position));
 //BA.debugLineNum = 772;BA.debugLine="Dim posicion As Int";
_posicion = 0;
 //BA.debugLineNum = 773;BA.debugLine="Dim diferencia As Int";
_diferencia = 0;
 //BA.debugLineNum = 775;BA.debugLine="paginaActual=Position";
_vvvvvvvvvvvvvvvv5 = _position;
 //BA.debugLineNum = 776;BA.debugLine="posicion=Position+1";
_posicion = (int) (_position+1);
 //BA.debugLineNum = 781;BA.debugLine="If paginaAnterior < paginaActual Then movimiento=\"";
if (_vvvvvvvvvvvvvvvv6<_vvvvvvvvvvvvvvvv5) { 
mostCurrent._vvvvvvvvvvvvvvvv7 = "derecha";};
 //BA.debugLineNum = 782;BA.debugLine="If paginaAnterior >paginaActual Then movimiento=\"i";
if (_vvvvvvvvvvvvvvvv6>_vvvvvvvvvvvvvvvv5) { 
mostCurrent._vvvvvvvvvvvvvvvv7 = "izquierda";};
 //BA.debugLineNum = 783;BA.debugLine="paginaAnterior=paginaActual";
_vvvvvvvvvvvvvvvv6 = _vvvvvvvvvvvvvvvv5;
 //BA.debugLineNum = 792;BA.debugLine="Dim animacion As AnimationPlus";
_animacion = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 798;BA.debugLine="Dim pan As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 799;BA.debugLine="Dim pan2 As Panel";
_pan2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 800;BA.debugLine="pan=container.GetPageObject(Position)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvv0.GetPageObject(_position)));
 //BA.debugLineNum = 801;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 803;BA.debugLine="pan2.Left=0";
_pan2.setLeft((int) (0));
 //BA.debugLineNum = 804;BA.debugLine="If movimiento= \"izquierda\" Then animacion.Initial";
if ((mostCurrent._vvvvvvvvvvvvvvvv7).equals("izquierda")) { 
_animacion.InitializeTranslate(mostCurrent.activityBA,"anim",(float) (-_vvvvvvvvvvvvvvvv0),(float) (0),(float) (0),(float) (0));};
 //BA.debugLineNum = 805;BA.debugLine="If movimiento= \"derecha\" Then animacion.Initializ";
if ((mostCurrent._vvvvvvvvvvvvvvvv7).equals("derecha")) { 
_animacion.InitializeTranslate(mostCurrent.activityBA,"anim",(float) (_vvvvvvvvvvvvvvvv0),(float) (0),(float) (0),(float) (0));};
 //BA.debugLineNum = 806;BA.debugLine="animacion.Duration=400";
_animacion.setDuration((long) (400));
 //BA.debugLineNum = 807;BA.debugLine="animacion.Start(pan2)";
_animacion.Start((android.view.View)(_pan2.getObject()));
 //BA.debugLineNum = 811;BA.debugLine="If paginaActual = 0  Then";
if (_vvvvvvvvvvvvvvvv5==0) { 
 //BA.debugLineNum = 812;BA.debugLine="pan=container.GetPageObject(1)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvv0.GetPageObject((int) (1))));
 //BA.debugLineNum = 813;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 814;BA.debugLine="pan2.Left=gap";
_pan2.setLeft(_vvvvvvvvvvvvvvvv0);
 };
 //BA.debugLineNum = 817;BA.debugLine="If paginaActual =(container.Count-1)Then";
if (_vvvvvvvvvvvvvvvv5==(mostCurrent._vvvvvvvvvv0.getCount()-1)) { 
 //BA.debugLineNum = 818;BA.debugLine="pan=container.GetPageObject(container.Count-2)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvv0.GetPageObject((int) (mostCurrent._vvvvvvvvvv0.getCount()-2))));
 //BA.debugLineNum = 819;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 820;BA.debugLine="pan2.Left=-gap";
_pan2.setLeft((int) (-_vvvvvvvvvvvvvvvv0));
 };
 //BA.debugLineNum = 825;BA.debugLine="End Sub";
return "";
}
public static String  _pager_pagecreated(int _position,Object _page) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pan2 = null;
 //BA.debugLineNum = 837;BA.debugLine="Sub pager_PageCreated (Position As Int, Page As Ob";
 //BA.debugLineNum = 839;BA.debugLine="Log(\"pager_PageCreated \" & Position)";
anywheresoftware.b4a.keywords.Common.Log("pager_PageCreated "+BA.NumberToString(_position));
 //BA.debugLineNum = 845;BA.debugLine="Dim pan As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 846;BA.debugLine="Dim pan2 As Panel";
_pan2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 848;BA.debugLine="If paginaActual>=1 Then";
if (_vvvvvvvvvvvvvvvv5>=1) { 
 //BA.debugLineNum = 849;BA.debugLine="pan=container.GetPageObject(paginaActual-1)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvv0.GetPageObject((int) (_vvvvvvvvvvvvvvvv5-1))));
 //BA.debugLineNum = 850;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 851;BA.debugLine="pan2.Left=-gap";
_pan2.setLeft((int) (-_vvvvvvvvvvvvvvvv0));
 };
 //BA.debugLineNum = 854;BA.debugLine="If paginaActual < (container.Count-1) Then";
if (_vvvvvvvvvvvvvvvv5<(mostCurrent._vvvvvvvvvv0.getCount()-1)) { 
 //BA.debugLineNum = 855;BA.debugLine="pan=container.GetPageObject(paginaActual+1)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvv0.GetPageObject((int) (_vvvvvvvvvvvvvvvv5+1))));
 //BA.debugLineNum = 856;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 857;BA.debugLine="pan2.Left=gap";
_pan2.setLeft(_vvvvvvvvvvvvvvvv0);
 };
 //BA.debugLineNum = 862;BA.debugLine="End Sub";
return "";
}
public static String  _pager_pagedestroyed(int _position,Object _page) throws Exception{
 //BA.debugLineNum = 871;BA.debugLine="Sub pager_PageDestroyed (Position As Int, Page As";
 //BA.debugLineNum = 872;BA.debugLine="Log(\"pager_PageDestroyed \" & Position)";
anywheresoftware.b4a.keywords.Common.Log("pager_PageDestroyed "+BA.NumberToString(_position));
 //BA.debugLineNum = 875;BA.debugLine="End Sub";
return "";
}
public static String  _pagernotas_pagechanged(int _position) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pan2 = null;
 //BA.debugLineNum = 1359;BA.debugLine="Sub pagerNotas_PageChanged (Position As Int)";
 //BA.debugLineNum = 1360;BA.debugLine="Dim pan As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1361;BA.debugLine="Dim pan2 As Panel";
_pan2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1362;BA.debugLine="pan=containerNotas.GetPageObject(Position)";
_pan.setObject((android.view.ViewGroup)(mostCurrent._vvvvvvvvvvv1.GetPageObject(_position)));
 //BA.debugLineNum = 1363;BA.debugLine="pan2=pan.GetView(0)";
_pan2.setObject((android.view.ViewGroup)(_pan.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 1364;BA.debugLine="webActual=pan2.GetView(0)";
mostCurrent._vvvvvvvvvvv6.setObject((android.webkit.WebView)(_pan2.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 1366;BA.debugLine="g2.SetOnTouchListener(webActual , \"GestosNotas\")";
mostCurrent._vvvvvvvvvvvv2.SetOnTouchListener(mostCurrent.activityBA,(android.view.View)(mostCurrent._vvvvvvvvvvv6.getObject()),"GestosNotas");
 //BA.debugLineNum = 1368;BA.debugLine="End Sub";
return "";
}
public static String  _pagernotas_pagecreated(int _position,Object _page) throws Exception{
 //BA.debugLineNum = 1343;BA.debugLine="Sub pagerNotas_PageCreated (Position As Int, Page";
 //BA.debugLineNum = 1356;BA.debugLine="End Sub";
return "";
}
public static String  _panelabout_click() throws Exception{
 //BA.debugLineNum = 672;BA.debugLine="Sub panelabout_Click";
 //BA.debugLineNum = 673;BA.debugLine="panelabout.Visible=False";
mostCurrent._vvvvvvvvvvv4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 674;BA.debugLine="cantidadClicks=0";
_vvvvvvvvvvv3 = (int) (0);
 //BA.debugLineNum = 675;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv5(anywheresoftware.b4a.objects.PanelWrapper _pnl) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
Object[] _args = null;
String[] _types = null;
elquid.mobile.reader.telefonomain._data _tk = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 1537;BA.debugLine="Sub PanelCapture(pnl As Panel)";
 //BA.debugLineNum = 1539;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1540;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1541;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1545;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 1546;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 1547;BA.debugLine="bmp.InitializeMutable(pnl.left + pnl.Width, pnl.T";
_bmp.InitializeMutable((int) (_pnl.getLeft()+_pnl.getWidth()),(int) (_pnl.getTop()+_pnl.getHeight()));
 //BA.debugLineNum = 1548;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1549;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 1550;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 1551;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 1552;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 1553;BA.debugLine="args(0) = Obj2.Target";
_args[(int) (0)] = _obj2.Target;
 //BA.debugLineNum = 1554;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 1555;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 1558;BA.debugLine="Dim TK As Data";
_tk = new elquid.mobile.reader.telefonomain._data();
 //BA.debugLineNum = 1559;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1560;BA.debugLine="TK.Canvas = c";
_tk.Canvas = _c;
 //BA.debugLineNum = 1562;BA.debugLine="out = File.OpenOutput(File.DirRootExternal, \"Capt";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Capture.png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1563;BA.debugLine="TK.Canvas.Bitmap.WriteToStream(out, 100, \"PNG\")";
_tk.Canvas.getBitmap().WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 1564;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 1567;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 1568;BA.debugLine="share.sharebinary(\"file://\" & File.DirRootExter";
_share.sharebinary(mostCurrent.activityBA,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Capture.png","image/png","Do you see this?","Mensaje");
 //BA.debugLineNum = 1572;BA.debugLine="End Sub";
return "";
}
public static String  _parartts_click() throws Exception{
 //BA.debugLineNum = 523;BA.debugLine="Sub pararTTS_Click";
 //BA.debugLineNum = 526;BA.debugLine="panelTTs.Visible=False";
mostCurrent._vvvvvvvvvvvvv7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 527;BA.debugLine="pagerNotas.PagingEnabled=True";
mostCurrent._vvvvvvvvvvv0.setPagingEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 530;BA.debugLine="Sleep(200)";
_vvvvvvv0((long) (200));
 //BA.debugLineNum = 531;BA.debugLine="TTS1.Stop";
mostCurrent._vvvvvvvvvv2.Stop();
 //BA.debugLineNum = 533;BA.debugLine="hablando=False";
_vvvvvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 534;BA.debugLine="PausaHablando=False";
_vvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 536;BA.debugLine="End Sub";
return "";
}
public static String  _pausartts_click() throws Exception{
 //BA.debugLineNum = 538;BA.debugLine="Sub pausarTTS_Click";
 //BA.debugLineNum = 548;BA.debugLine="PausaHablando=Not(PausaHablando)";
_vvvvvvvvvvvvvvvvv0 = anywheresoftware.b4a.keywords.Common.Not(_vvvvvvvvvvvvvvvvv0);
 //BA.debugLineNum = 549;BA.debugLine="If PausaHablando=True Then botonPausarTTS.SetBack";
if (_vvvvvvvvvvvvvvvvv0==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._vvvvvvvvvvvvvv1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"play.png").getObject()));};
 //BA.debugLineNum = 550;BA.debugLine="If PausaHablando=False Then botonPausarTTS.SetBac";
if (_vvvvvvvvvvvvvvvvv0==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._vvvvvvvvvvvvvv1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pause.png").getObject()));};
 //BA.debugLineNum = 551;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvvvvvvv3(String _t) throws Exception{
String[] _subs = null;
int _x = 0;
String _marca = "";
int _pos = 0;
 //BA.debugLineNum = 1484;BA.debugLine="Sub PlainText (t As String) As String";
 //BA.debugLineNum = 1485;BA.debugLine="Dim subs() As String";
_subs = new String[(int) (0)];
java.util.Arrays.fill(_subs,"");
 //BA.debugLineNum = 1486;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 1487;BA.debugLine="Dim marca As String";
_marca = "";
 //BA.debugLineNum = 1488;BA.debugLine="Dim pos As Int";
_pos = 0;
 //BA.debugLineNum = 1490;BA.debugLine="marca= \"-->\"";
_marca = "-->";
 //BA.debugLineNum = 1491;BA.debugLine="pos= t.IndexOf(marca)";
_pos = _t.indexOf(_marca);
 //BA.debugLineNum = 1494;BA.debugLine="t=t.SubString2(4,pos)";
_t = _t.substring((int) (4),_pos);
 //BA.debugLineNum = 1496;BA.debugLine="t = RegexReplace(\"\\<[^\\>]*\\>\", t, \"\")";
_t = _vvvvvvvvvvvvvvvvvv6("\\<[^\\>]*\\>",_t,"");
 //BA.debugLineNum = 1498;BA.debugLine="t = RegexReplace(\"&\\w+;\", t, \"\")";
_t = _vvvvvvvvvvvvvvvvvv6("&\\w+;",_t,"");
 //BA.debugLineNum = 1502;BA.debugLine="Return t";
if (true) return _t;
 //BA.debugLineNum = 1504;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim urlFromJS As String =\"\"";
_v5 = "";
 //BA.debugLineNum = 10;BA.debugLine="Public notas() As String";
_v6 = new String[(int) (0)];
java.util.Arrays.fill(_v6,"");
 //BA.debugLineNum = 12;BA.debugLine="Public progreso As Int =0";
_v7 = (int) (0);
 //BA.debugLineNum = 13;BA.debugLine="Public actualizarTodo As Boolean = False";
_v0 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 14;BA.debugLine="Dim TapaVisible As Boolean=True";
_vv1 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 15;BA.debugLine="Dim notActivas As String =\"si\"";
_vv2 = BA.__b (new byte[] {34,47}, 901405);
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvvvvvvvvvvv7(Object _o,int _ptrid,int _action,float _x,float _y) throws Exception{
int _posy = 0;
int _scrolly = 0;
 //BA.debugLineNum = 1253;BA.debugLine="Sub PullToRefresh ( o As Object, ptrID As Int, act";
 //BA.debugLineNum = 1255;BA.debugLine="Dim posy As Int";
_posy = 0;
 //BA.debugLineNum = 1256;BA.debugLine="Dim scrollY As Int";
_scrolly = 0;
 //BA.debugLineNum = 1259;BA.debugLine="If actualizarTodo=True Then Return";
if (_v0==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return false;};
 //BA.debugLineNum = 1262;BA.debugLine="If action= 0 Then";
if (_action==0) { 
 //BA.debugLineNum = 1263;BA.debugLine="Ylanding=pager.Top+web(0).Top+ y";
_vvvvvvvvvvvvvvvvv1 = (int) (mostCurrent._vvvvvvvvvv1.getTop()+mostCurrent._vvvv7[(int) (0)].getTop()+_y);
 //BA.debugLineNum = 1264;BA.debugLine="Xlanding= x";
_vvvvvvvvvvvvvvvvv3 = (int) (_x);
 };
 //BA.debugLineNum = 1268;BA.debugLine="scrollY=webextendida.getScrollY(web(0))";
_scrolly = mostCurrent._vvvvvvvvvvvvvvvvv5.getScrollY((android.webkit.WebView)(mostCurrent._vvvv7[(int) (0)].getObject()));
 //BA.debugLineNum = 1269;BA.debugLine="posy=pager.Top+web(0).Top+ y";
_posy = (int) (mostCurrent._vvvvvvvvvv1.getTop()+mostCurrent._vvvv7[(int) (0)].getTop()+_y);
 //BA.debugLineNum = 1270;BA.debugLine="deltaY=posy-Ylanding";
_vvvvvvvvvvvvvvvvv2 = (int) (_posy-_vvvvvvvvvvvvvvvvv1);
 //BA.debugLineNum = 1271;BA.debugLine="deltaX=Abs(x-Xlanding)";
_vvvvvvvvvvvvvvvvv4 = (int) (anywheresoftware.b4a.keywords.Common.Abs(_x-_vvvvvvvvvvvvvvvvv3));
 //BA.debugLineNum = 1273;BA.debugLine="deltaY=deltaY*0.75";
_vvvvvvvvvvvvvvvvv2 = (int) (_vvvvvvvvvvvvvvvvv2*0.75);
 //BA.debugLineNum = 1276;BA.debugLine="Log(\"deltay= \" & deltaY)";
anywheresoftware.b4a.keywords.Common.Log("deltay= "+BA.NumberToString(_vvvvvvvvvvvvvvvvv2));
 //BA.debugLineNum = 1280;BA.debugLine="If action= 1 Or deltaX> 40dip  Or scrollY>0 Then";
if (_action==1 || _vvvvvvvvvvvvvvvvv4>anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)) || _scrolly>0) { 
 //BA.debugLineNum = 1281;BA.debugLine="web(0).Top=66dip";
mostCurrent._vvvv7[(int) (0)].setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (66)));
 //BA.debugLineNum = 1282;BA.debugLine="pager.Color=Colors.Black";
mostCurrent._vvvvvvvvvv1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1283;BA.debugLine="If pull=True And deltaY > 140dip And deltaY < 2";
if (_vvvvvvvvvvvvvv7==anywheresoftware.b4a.keywords.Common.True && _vvvvvvvvvvvvvvvvv2>anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)) && _vvvvvvvvvvvvvvvvv2<anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (250))) { 
_vvvvvvvvvv3();};
 //BA.debugLineNum = 1284;BA.debugLine="deltaY=0";
_vvvvvvvvvvvvvvvvv2 = (int) (0);
 //BA.debugLineNum = 1285;BA.debugLine="deltaX=0";
_vvvvvvvvvvvvvvvvv4 = (int) (0);
 //BA.debugLineNum = 1286;BA.debugLine="pull=False";
_vvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1287;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1291;BA.debugLine="If action= 2 And deltaY>66dip And scrollY=0 Then";
if (_action==2 && _vvvvvvvvvvvvvvvvv2>anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (66)) && _scrolly==0) { 
 //BA.debugLineNum = 1292;BA.debugLine="pager.Color=Colors.DarkGray";
mostCurrent._vvvvvvvvvv1.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1295;BA.debugLine="If deltaY < 140dip Then girar(avisoRefresh,Fals";
if (_vvvvvvvvvvvvvvvvv2<anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))) { 
_vvvvvvvvvvvvvvv3(mostCurrent._vvvvvvvvvvvvvv3,anywheresoftware.b4a.keywords.Common.False);};
 //BA.debugLineNum = 1296;BA.debugLine="If deltaY >= 140dip Then girar(avisoRefresh,Tru";
if (_vvvvvvvvvvvvvvvvv2>=anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))) { 
_vvvvvvvvvvvvvvv3(mostCurrent._vvvvvvvvvvvvvv3,anywheresoftware.b4a.keywords.Common.True);};
 //BA.debugLineNum = 1298;BA.debugLine="web(0).Top=deltaY";
mostCurrent._vvvv7[(int) (0)].setTop(_vvvvvvvvvvvvvvvvv2);
 //BA.debugLineNum = 1299;BA.debugLine="avisoRefresh.left=(Activity.Width-avisoRefresh.";
mostCurrent._vvvvvvvvvvvvvv3.setLeft((int) ((mostCurrent._activity.getWidth()-mostCurrent._vvvvvvvvvvvvvv3.getWidth())/(double)2));
 //BA.debugLineNum = 1300;BA.debugLine="avisoRefresh.top= (66dip+((web(0).Top- 66dip";
mostCurrent._vvvvvvvvvvvvvv3.setTop((int) ((anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (66))+((mostCurrent._vvvv7[(int) (0)].getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (66)))/(double)2))-(mostCurrent._vvvvvvvvvvvvvv3.getHeight()/(double)2)));
 //BA.debugLineNum = 1301;BA.debugLine="captionRefresh.Top=avisoRefresh.top+avisoRefres";
mostCurrent._vvvvvvvvvvvvvv4.setTop((int) (mostCurrent._vvvvvvvvvvvvvv3.getTop()+mostCurrent._vvvvvvvvvvvvvv3.getHeight()));
 //BA.debugLineNum = 1302;BA.debugLine="pull=True";
_vvvvvvvvvvvvvv7 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1303;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 1309;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1312;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvvvvvvvv6(String _pattern,String _text,String _replacement) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 1507;BA.debugLine="Sub RegexReplace(Pattern As String, Text As String";
 //BA.debugLineNum = 1508;BA.debugLine="Dim m As Matcher";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 1509;BA.debugLine="m = Regex.Matcher(Pattern, Text)";
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher(_pattern,_text);
 //BA.debugLineNum = 1510;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1511;BA.debugLine="r.Target = m";
_r.Target = (Object)(_m.getObject());
 //BA.debugLineNum = 1512;BA.debugLine="Return r.RunMethod2(\"replaceAll\", Replacement,";
if (true) return BA.ObjectToString(_r.RunMethod2("replaceAll",_replacement,"java.lang.String"));
 //BA.debugLineNum = 1513;BA.debugLine="End Sub";
return "";
}
public static boolean  _vvvvvvvvv5() throws Exception{
boolean _sactivo = false;
 //BA.debugLineNum = 879;BA.debugLine="Sub servicioActivo () As Boolean";
 //BA.debugLineNum = 880;BA.debugLine="Dim sActivo As Boolean";
_sactivo = false;
 //BA.debugLineNum = 881;BA.debugLine="sActivo=False";
_sactivo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 883;BA.debugLine="If File.Exists(File.DirInternal, \"servicioActivo.t";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"servicioActivo.txt")) { 
_sactivo = anywheresoftware.b4a.keywords.Common.True;};
 //BA.debugLineNum = 884;BA.debugLine="If IsPaused(ServicioD)=False Then sActivo=True";
if (anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)(mostCurrent._vvvvv0.getObject()))==anywheresoftware.b4a.keywords.Common.False) { 
_sactivo = anywheresoftware.b4a.keywords.Common.True;};
 //BA.debugLineNum = 886;BA.debugLine="Return sActivo";
if (true) return _sactivo;
 //BA.debugLineNum = 888;BA.debugLine="End Sub";
return false;
}
public static String  _vvvvvvvvvvvvvvvvvv4() throws Exception{
 //BA.debugLineNum = 743;BA.debugLine="Sub silenciarNotificaciones";
 //BA.debugLineNum = 744;BA.debugLine="If notActivas= \"si\" Then StateManager.SetSetting(";
if ((_vv2).equals("si")) { 
mostCurrent._vvvvvv1._vvv7(mostCurrent.activityBA,"notActivas","no");};
 //BA.debugLineNum = 745;BA.debugLine="If notActivas= \"no\" Then StateManager.SetSetting(";
if ((_vv2).equals("no")) { 
mostCurrent._vvvvvv1._vvv7(mostCurrent.activityBA,"notActivas","si");};
 //BA.debugLineNum = 746;BA.debugLine="StateManager.SaveSettings";
mostCurrent._vvvvvv1._vvv5(mostCurrent.activityBA);
 //BA.debugLineNum = 747;BA.debugLine="notActivas=StateManager.GetSetting (\"notActivas\")";
_vv2 = mostCurrent._vvvvvv1._vvv1(mostCurrent.activityBA,"notActivas");
 //BA.debugLineNum = 749;BA.debugLine="overflowMenu.limpiar";
mostCurrent._vvvvvvvvvvv5._vvvvvv5();
 //BA.debugLineNum = 753;BA.debugLine="overflowMenu.AddItem(\"Ir a la web de EQC\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Ir a la web de EQC");
 //BA.debugLineNum = 754;BA.debugLine="overflowMenu.AddItem(\"Acerca de...\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Acerca de...");
 //BA.debugLineNum = 755;BA.debugLine="If notActivas= \"no\" Then overflowMenu.AddItem(\"Ac";
if ((_vv2).equals("no")) { 
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Activar Notificaciones");};
 //BA.debugLineNum = 756;BA.debugLine="If notActivas= \"si\" Then overflowMenu.AddItem(\"Si";
if ((_vv2).equals("si")) { 
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Silenciar Notificaciones");};
 //BA.debugLineNum = 757;BA.debugLine="overflowMenu.AddItem(\"Salir\")";
mostCurrent._vvvvvvvvvvv5._vvvvvv2("Salir");
 //BA.debugLineNum = 760;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv0(long _ms) throws Exception{
long _now = 0L;
 //BA.debugLineNum = 1180;BA.debugLine="Sub Sleep(ms As Long)";
 //BA.debugLineNum = 1181;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 1182;BA.debugLine="If ms > 1000 Then ms =1000";
if (_ms>1000) { 
_ms = (long) (1000);};
 //BA.debugLineNum = 1183;BA.debugLine="now=DateTime.now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1184;BA.debugLine="Do Until (DateTime.now>now+ms)";
while (!((anywheresoftware.b4a.keywords.Common.DateTime.getNow()>_now+_ms))) {
 //BA.debugLineNum = 1185;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
;
 //BA.debugLineNum = 1187;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv4(boolean _encendida) throws Exception{
 //BA.debugLineNum = 262;BA.debugLine="Sub splashScreen (encendida As Boolean)";
 //BA.debugLineNum = 263;BA.debugLine="Log( \"SplashScreen\")";
anywheresoftware.b4a.keywords.Common.Log("SplashScreen");
 //BA.debugLineNum = 265;BA.debugLine="If encendida=True Then";
if (_encendida==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 266;BA.debugLine="Main.splashDone=True";
mostCurrent._vvvvv5._vvv0 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 267;BA.debugLine="ProgressBar1.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvvvv2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 268;BA.debugLine="etiqueta.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvvvv1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 269;BA.debugLine="panelSplash.Initialize(\"\")";
mostCurrent._vvvvvvvvvvvvvvv0.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 270;BA.debugLine="panelSplash.SetBackgroundImage (LoadBitmap (Fil";
mostCurrent._vvvvvvvvvvvvvvv0.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lacapitalsplash.jpg").getObject()));
 //BA.debugLineNum = 271;BA.debugLine="ProgressBar1.Indeterminate=True";
mostCurrent._vvvvvvvvvvvvvvvv2.setIndeterminate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 272;BA.debugLine="panelSplash.AddView(ProgressBar1,50%x - 35dip ,";
mostCurrent._vvvvvvvvvvvvvvv0.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv2.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 273;BA.debugLine="panelSplash.AddView(etiqueta,0,100%y-40dip,100%";
mostCurrent._vvvvvvvvvvvvvvv0.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvvv1.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 274;BA.debugLine="etiqueta.Gravity=Gravity.CENTER_HORIZONTAL";
mostCurrent._vvvvvvvvvvvvvvvv1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 275;BA.debugLine="etiqueta.TextSize=18";
mostCurrent._vvvvvvvvvvvvvvvv1.setTextSize((float) (18));
 //BA.debugLineNum = 276;BA.debugLine="etiqueta.Text=\"(c) 2017\"";
mostCurrent._vvvvvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("(c) 2017"));
 //BA.debugLineNum = 277;BA.debugLine="Activity.AddView(panelSplash,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vvvvvvvvvvvvvvv0.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 278;BA.debugLine="panelSplash.Visible=True";
mostCurrent._vvvvvvvvvvvvvvv0.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 279;BA.debugLine="panelSplash.BringToFront";
mostCurrent._vvvvvvvvvvvvvvv0.BringToFront();
 //BA.debugLineNum = 280;BA.debugLine="SplashActiva=True";
_vvvvvvvvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 283;BA.debugLine="panelSplash.Visible=False";
mostCurrent._vvvvvvvvvvvvvvv0.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 284;BA.debugLine="SplashActiva=False";
_vvvvvvvvvvvvvvvv3 = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 287;BA.debugLine="Sleep(500)";
_vvvvvvv0((long) (500));
 //BA.debugLineNum = 288;BA.debugLine="Sleep(500)";
_vvvvvvv0((long) (500));
 //BA.debugLineNum = 289;BA.debugLine="Sleep(500)";
_vvvvvvv0((long) (500));
 //BA.debugLineNum = 290;BA.debugLine="brefresh_Click";
_brefresh_click();
 };
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv0() throws Exception{
flm.b4a.animationplus.AnimationPlusWrapper _anim = null;
flm.b4a.animationplus.AnimationPlusWrapper _anim2 = null;
 //BA.debugLineNum = 1375;BA.debugLine="Sub VolverTapa";
 //BA.debugLineNum = 1378;BA.debugLine="Dim anim As AnimationPlus";
_anim = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 1379;BA.debugLine="Dim anim2 As AnimationPlus";
_anim2 = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 1382;BA.debugLine="anim.InitializeTranslate(\"finirtapa\",0,0,0,Ac";
_anim.InitializeTranslate(mostCurrent.activityBA,"finirtapa",(float) (0),(float) (0),(float) (0),(float) (mostCurrent._activity.getHeight()));
 //BA.debugLineNum = 1383;BA.debugLine="anim.Duration=500";
_anim.setDuration((long) (500));
 //BA.debugLineNum = 1384;BA.debugLine="anim.Start(pagerNotas)";
_anim.Start((android.view.View)(mostCurrent._vvvvvvvvvvv0.getObject()));
 //BA.debugLineNum = 1386;BA.debugLine="pager.Top=0";
mostCurrent._vvvvvvvvvv1.setTop((int) (0));
 //BA.debugLineNum = 1387;BA.debugLine="anim2.InitializeScaleCenter(\"\", 0, 0, 1, 1, page";
_anim2.InitializeScaleCenter(mostCurrent.activityBA,"",(float) (0),(float) (0),(float) (1),(float) (1),(android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()));
 //BA.debugLineNum = 1388;BA.debugLine="anim2.Duration=500";
_anim2.setDuration((long) (500));
 //BA.debugLineNum = 1389;BA.debugLine="anim2.Start(pager)";
_anim2.Start((android.view.View)(mostCurrent._vvvvvvvvvv1.getObject()));
 //BA.debugLineNum = 1391;BA.debugLine="TapaVisible=True";
_vv1 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1394;BA.debugLine="End Sub";
return "";
}
public static String  _web_longclick() throws Exception{
 //BA.debugLineNum = 642;BA.debugLine="Sub web_LongClick";
 //BA.debugLineNum = 646;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 647;BA.debugLine="End Sub";
return "";
}
public static boolean  _web_overrideurl(String _url) throws Exception{
String[] _partes = null;
String _cargar = "";
String _posperiod = "";
 //BA.debugLineNum = 558;BA.debugLine="Sub web_OverrideUrl (url As String) As Boolean";
 //BA.debugLineNum = 560;BA.debugLine="Log( \"web_OverrideUrl\")";
anywheresoftware.b4a.keywords.Common.Log("web_OverrideUrl");
 //BA.debugLineNum = 562;BA.debugLine="Dim partes() As String";
_partes = new String[(int) (0)];
java.util.Arrays.fill(_partes,"");
 //BA.debugLineNum = 563;BA.debugLine="Dim cargar  As String";
_cargar = "";
 //BA.debugLineNum = 565;BA.debugLine="Log(url)";
anywheresoftware.b4a.keywords.Common.Log(_url);
 //BA.debugLineNum = 567;BA.debugLine="Dim posperiod = url.IndexOf(\".\")";
_posperiod = BA.NumberToString(_url.indexOf("."));
 //BA.debugLineNum = 568;BA.debugLine="numeroNota=url.SubString2(8,posperiod)";
mostCurrent._vvvvvvvvvvvvvvv1 = _url.substring((int) (8),(int)(Double.parseDouble(_posperiod)));
 //BA.debugLineNum = 569;BA.debugLine="Log(\"click en= \" & numeroNota )";
anywheresoftware.b4a.keywords.Common.Log("click en= "+mostCurrent._vvvvvvvvvvvvvvv1);
 //BA.debugLineNum = 571;BA.debugLine="If IsNumber(numeroNota) Then notaInicio=numeroN";
if (anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._vvvvvvvvvvvvvvv1)) { 
_vvvvvvvvv1 = (int) ((double)(Double.parseDouble(mostCurrent._vvvvvvvvvvvvvvv1))-1);};
 //BA.debugLineNum = 572;BA.debugLine="offset=notaInicio-(pagFijas-1)";
_vvvvvvvv0 = (int) (_vvvvvvvvv1-(_vvvvvvvvv2-1));
 //BA.debugLineNum = 576;BA.debugLine="If url= \"file:///volver.html\" Then";
if ((_url).equals("file:///volver.html")) { 
 //BA.debugLineNum = 577;BA.debugLine="VolverTapa";
_vvvvvvvvv0();
 //BA.debugLineNum = 578;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 583;BA.debugLine="If url= \"file:///android_asset/recargar\" Then";
if ((_url).equals("file:///android_asset/recargar")) { 
 //BA.debugLineNum = 584;BA.debugLine="If pager.CurrentPage=1 Then con.cargar(\"elquid.h";
if (mostCurrent._vvvvvvvvvv1.getCurrentPage()==1) { 
mostCurrent._vvvvvvvvv7._vvvv1("elquid.html",mostCurrent._vvvv7[(int) (0)]);};
 //BA.debugLineNum = 585;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 589;BA.debugLine="If pager.CurrentPage=1 Then";
if (mostCurrent._vvvvvvvvvv1.getCurrentPage()==1) { 
 //BA.debugLineNum = 593;BA.debugLine="If IsNumber (numeroNota) Then cargarNota(numeroN";
if (anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._vvvvvvvvvvvvvvv1)) { 
_vvvvvvvvvvv7((int)(Double.parseDouble(mostCurrent._vvvvvvvvvvvvvvv1)));};
 };
 //BA.debugLineNum = 600;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 602;BA.debugLine="End Sub";
return false;
}
public static String  _web_pagefinished(String _url) throws Exception{
 //BA.debugLineNum = 605;BA.debugLine="Sub web_PageFinished (url As String)";
 //BA.debugLineNum = 607;BA.debugLine="Log(\"web_PageFinished\")";
anywheresoftware.b4a.keywords.Common.Log("web_PageFinished");
 //BA.debugLineNum = 609;BA.debugLine="If SplashActiva = True Then";
if (_vvvvvvvvvvvvvvvv3==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 610;BA.debugLine="splashScreen(False)";
_vvvvvvvvv4(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 611;BA.debugLine="lFecha(0).Text=fechaTapa";
mostCurrent._vvvvvvvvvvvv4[(int) (0)].setText(BA.ObjectToCharSequence(mostCurrent._vvvv0));
 //BA.debugLineNum = 612;BA.debugLine="crearPaginasNotas";
_vvvvvvvvvv6();
 };
 //BA.debugLineNum = 616;BA.debugLine="End Sub";
return "";
}
}
