package elquid.mobile.reader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class slidemenu extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "elquid.mobile.reader.slidemenu");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", elquid.mobile.reader.slidemenu.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv2 = null;
public Object _vvvvvvv3 = null;
public String _vvvvvvv4 = "";
public anywheresoftware.b4a.objects.ListViewWrapper _vvvvvvv5 = null;
public flm.b4a.animationplus.AnimationPlusWrapper _vvvvvvvv1 = null;
public anywheresoftware.b4a.objects.AnimationWrapper _vvvvvvvv2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _vvvvvvvv3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvv4 = null;
public anywheresoftware.b4a.objects.ActivityWrapper _vvvvvvvv5 = null;
public boolean _vvvvvv4 = false;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv4 = null;
public elquid.mobile.reader.main _vvvvv5 = null;
public elquid.mobile.reader.telefonomain _vvvvv6 = null;
public elquid.mobile.reader.tabletmain _vvvvv7 = null;
public elquid.mobile.reader.serviciod _vvvvv0 = null;
public elquid.mobile.reader.statemanager _vvvvvv1 = null;
public static class _actionitem{
public boolean IsInitialized;
public String Text;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper Image;
public Object Value;
public void Initialize() {
IsInitialized = true;
Text = "";
Image = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
Value = new Object();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _vvvvvv2(String _text,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _image,Object _returnvalue) throws Exception{
elquid.mobile.reader.slidemenu._actionitem _item = null;
 //BA.debugLineNum = 85;BA.debugLine="Public Sub AddItem(Text As String, Image As Bitmap";
 //BA.debugLineNum = 86;BA.debugLine="Dim item As ActionItem";
_item = new elquid.mobile.reader.slidemenu._actionitem();
 //BA.debugLineNum = 87;BA.debugLine="item.Initialize";
_item.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="item.Text = Text";
_item.Text = _text;
 //BA.debugLineNum = 89;BA.debugLine="item.Image = Image";
_item.Image = _image;
 //BA.debugLineNum = 90;BA.debugLine="item.Value = ReturnValue";
_item.Value = _returnvalue;
 //BA.debugLineNum = 92;BA.debugLine="If Not(Image.IsInitialized) Then";
if (__c.Not(_image.IsInitialized())) { 
 //BA.debugLineNum = 93;BA.debugLine="mListView.AddTwoLinesAndBitmap2(Text, \"\", Null,";
_vvvvvvv5.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_text),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(__c.Null),_returnvalue);
 }else {
 //BA.debugLineNum = 95;BA.debugLine="mListView.AddTwoLinesAndBitmap2(Text, \"\", Image,";
_vvvvvvv5.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_text),BA.ObjectToCharSequence(""),(android.graphics.Bitmap)(_image.getObject()),_returnvalue);
 };
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public String  _botonmenu_click() throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Private Sub botonMenu_Click";
 //BA.debugLineNum = 187;BA.debugLine="If  isVisible = False Then";
if (_vvvvvv4==__c.False) { 
 //BA.debugLineNum = 188;BA.debugLine="Show";
_vvvvvv0();
 //BA.debugLineNum = 189;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 192;BA.debugLine="If isVisible = True Then";
if (_vvvvvv4==__c.True) { 
 //BA.debugLineNum = 193;BA.debugLine="Hide";
_vvvvvv3();
 //BA.debugLineNum = 194;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Type ActionItem (Text As String, Image As Bitmap,";
;
 //BA.debugLineNum = 10;BA.debugLine="Private mSlidePanel As Panel";
_vvvvvvv1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private mBackPanel As Panel";
_vvvvvvv2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private mModule As Object";
_vvvvvvv3 = new Object();
 //BA.debugLineNum = 15;BA.debugLine="Private mEventName As String";
_vvvvvvv4 = "";
 //BA.debugLineNum = 17;BA.debugLine="Private mListView As ListView";
_vvvvvvv5 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mInAnimation As AnimationPlus";
_vvvvvvvv1 = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private mOutAnimation As Animation";
_vvvvvvvv2 = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private b As Button";
_vvvvvvvv3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private linea As Panel";
_vvvvvvvv4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private actividad As Activity";
_vvvvvvvv5 = new anywheresoftware.b4a.objects.ActivityWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private isVisible As Boolean =False";
_vvvvvv4 = __c.False;
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv3() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Public Sub Hide";
 //BA.debugLineNum = 126;BA.debugLine="If isVisible = False Then Return";
if (_vvvvvv4==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 128;BA.debugLine="b.SetBackgroundImage(LoadBitmap (File.DirAssets,\"";
_vvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),"boton abrir.png").getObject()));
 //BA.debugLineNum = 130;BA.debugLine="mBackPanel.visible = False";
_vvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 132;BA.debugLine="mOutAnimation.InitializeTranslate(\"Out\", 0, 0, 0,";
_vvvvvvvv2.InitializeTranslate(ba,"Out",(float) (0),(float) (0),(float) (0),(float) (_vvvvvvv1.getHeight()));
 //BA.debugLineNum = 133;BA.debugLine="mOutAnimation.Duration = 200";
_vvvvvvvv2.setDuration((long) (200));
 //BA.debugLineNum = 134;BA.debugLine="mOutAnimation.Start(mSlidePanel)";
_vvvvvvvv2.Start((android.view.View)(_vvvvvvv1.getObject()));
 //BA.debugLineNum = 136;BA.debugLine="isVisible = False";
_vvvvvv4 = __c.False;
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,Object _module,String _eventname,int _top,int _width) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 37;BA.debugLine="Sub Initialize(Activity As Activity, Module As Obj";
 //BA.debugLineNum = 38;BA.debugLine="mModule = Module";
_vvvvvvv3 = _module;
 //BA.debugLineNum = 39;BA.debugLine="mEventName = EventName";
_vvvvvvv4 = _eventname;
 //BA.debugLineNum = 40;BA.debugLine="actividad=Activity";
_vvvvvvvv5 = _activity;
 //BA.debugLineNum = 42;BA.debugLine="mSlidePanel.Initialize(\"mSlidePanel\")";
_vvvvvvv1.Initialize(ba,"mSlidePanel");
 //BA.debugLineNum = 44;BA.debugLine="linea.Initialize(\"\")";
_vvvvvvvv4.Initialize(ba,"");
 //BA.debugLineNum = 47;BA.debugLine="mListView.Initialize(\"mListView\")";
_vvvvvvv5.Initialize(ba,"mListView");
 //BA.debugLineNum = 48;BA.debugLine="mListView.TwoLinesAndBitmap.SecondLabel.Visible =";
_vvvvvvv5.getTwoLinesAndBitmap().SecondLabel.setVisible(__c.False);
 //BA.debugLineNum = 49;BA.debugLine="mListView.TwoLinesAndBitmap.ItemHeight = 50dip";
_vvvvvvv5.getTwoLinesAndBitmap().setItemHeight(__c.DipToCurrent((int) (50)));
 //BA.debugLineNum = 50;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Gravity = Gravi";
_vvvvvvv5.getTwoLinesAndBitmap().Label.setGravity(__c.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 51;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Height = mListV";
_vvvvvvv5.getTwoLinesAndBitmap().Label.setHeight(_vvvvvvv5.getTwoLinesAndBitmap().getItemHeight());
 //BA.debugLineNum = 52;BA.debugLine="mListView.TwoLinesAndBitmap.Label.Top = 0";
_vvvvvvv5.getTwoLinesAndBitmap().Label.setTop((int) (0));
 //BA.debugLineNum = 53;BA.debugLine="mListView.TwoLinesAndBitmap.ImageView.SetLayout(1";
_vvvvvvv5.getTwoLinesAndBitmap().ImageView.SetLayout(__c.DipToCurrent((int) (13)),__c.DipToCurrent((int) (13)),__c.DipToCurrent((int) (32)),__c.DipToCurrent((int) (32)));
 //BA.debugLineNum = 54;BA.debugLine="mListView.Color = Colors.ARGB(255,255,255,255)";
_vvvvvvv5.setColor(__c.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 55;BA.debugLine="linea.Color=Colors.ARGB(204,216,37,35)";
_vvvvvvvv4.setColor(__c.Colors.ARGB((int) (204),(int) (216),(int) (37),(int) (35)));
 //BA.debugLineNum = 56;BA.debugLine="mListView.TwoLinesAndBitmap.Label.TextColor=Color";
_vvvvvvv5.getTwoLinesAndBitmap().Label.setTextColor(__c.Colors.Black);
 //BA.debugLineNum = 59;BA.debugLine="mSlidePanel.color=Colors.Transparent";
_vvvvvvv1.setColor(__c.Colors.Transparent);
 //BA.debugLineNum = 64;BA.debugLine="Activity.AddView(mSlidePanel, (Activity.Width - W";
_activity.AddView((android.view.View)(_vvvvvvv1.getObject()),(int) ((_activity.getWidth()-_width)/(double)2),_top,_width,(int) (__c.DipToCurrent((int) (204))+__c.DipToCurrent((int) (75))));
 //BA.debugLineNum = 66;BA.debugLine="mBackPanel.Initialize(\"mBackPanel\")";
_vvvvvvv2.Initialize(ba,"mBackPanel");
 //BA.debugLineNum = 67;BA.debugLine="mBackPanel.Color = Colors.ARGB(180,0,0,0)";
_vvvvvvv2.setColor(__c.Colors.ARGB((int) (180),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 68;BA.debugLine="Activity.AddView(mBackPanel, -100%x, 0, 100%x, 10";
_activity.AddView((android.view.View)(_vvvvvvv2.getObject()),(int) (-__c.PerXToCurrent((float) (100),ba)),(int) (0),__c.PerXToCurrent((float) (100),ba),__c.PerYToCurrent((float) (100),ba));
 //BA.debugLineNum = 70;BA.debugLine="mSlidePanel.AddView(mListView, 0, 75dip, Width, m";
_vvvvvvv1.AddView((android.view.View)(_vvvvvvv5.getObject()),(int) (0),__c.DipToCurrent((int) (75)),_width,_vvvvvvv1.getHeight());
 //BA.debugLineNum = 71;BA.debugLine="mSlidePanel.AddView(linea, 0, 65dip, Width, 10dip";
_vvvvvvv1.AddView((android.view.View)(_vvvvvvvv4.getObject()),(int) (0),__c.DipToCurrent((int) (65)),_width,__c.DipToCurrent((int) (10)));
 //BA.debugLineNum = 72;BA.debugLine="mSlidePanel.Visible = True";
_vvvvvvv1.setVisible(__c.True);
 //BA.debugLineNum = 74;BA.debugLine="b.Initialize(\"botonMenu\")";
_vvvvvvvv3.Initialize(ba,"botonMenu");
 //BA.debugLineNum = 75;BA.debugLine="b.SetBackgroundImage(LoadBitmap (File.DirAsset";
_vvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),"boton abrir.png").getObject()));
 //BA.debugLineNum = 76;BA.debugLine="mSlidePanel.AddView(b,(Width - 65dip)/2,0,65di";
_vvvvvvv1.AddView((android.view.View)(_vvvvvvvv3.getObject()),(int) ((_width-__c.DipToCurrent((int) (65)))/(double)2),(int) (0),__c.DipToCurrent((int) (65)),__c.DipToCurrent((int) (65)));
 //BA.debugLineNum = 77;BA.debugLine="b.BringToFront";
_vvvvvvvv3.BringToFront();
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public String  _mbackpanel_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Private Sub mBackPanel_Touch (Action As Int, X As";
 //BA.debugLineNum = 145;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 146;BA.debugLine="Hide";
_vvvvvv3();
 };
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public String  _mlistview_itemclick(int _position,Object _value) throws Exception{
String _subname = "";
 //BA.debugLineNum = 150;BA.debugLine="Private Sub mListView_ItemClick (Position As Int,";
 //BA.debugLineNum = 151;BA.debugLine="Dim subname As String";
_subname = "";
 //BA.debugLineNum = 153;BA.debugLine="Log(\"POSITION= \" & Position)";
__c.Log("POSITION= "+BA.NumberToString(_position));
 //BA.debugLineNum = 156;BA.debugLine="Select Position";
switch (_position) {
case 0: {
 //BA.debugLineNum = 159;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvv1.setVisible(__c.False);
 //BA.debugLineNum = 160;BA.debugLine="isVisible = False";
_vvvvvv4 = __c.False;
 //BA.debugLineNum = 161;BA.debugLine="mBackPanel.visible = False";
_vvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 162;BA.debugLine="mSlidePanel.top = actividad.Height-60dip";
_vvvvvvv1.setTop((int) (_vvvvvvvv5.getHeight()-__c.DipToCurrent((int) (60))));
 //BA.debugLineNum = 163;BA.debugLine="b.SetBackgroundImage(LoadBitmap (File.DirAsse";
_vvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),"boton abrir.png").getObject()));
 break; }
case 3: {
 //BA.debugLineNum = 166;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvv1.setVisible(__c.False);
 //BA.debugLineNum = 167;BA.debugLine="mBackPanel.visible = False";
_vvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 168;BA.debugLine="mSlidePanel.top = actividad.Height-60dip";
_vvvvvvv1.setTop((int) (_vvvvvvvv5.getHeight()-__c.DipToCurrent((int) (60))));
 //BA.debugLineNum = 169;BA.debugLine="b.SetBackgroundImage(LoadBitmap (File.DirAsse";
_vvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),"boton abrir.png").getObject()));
 break; }
default: {
 //BA.debugLineNum = 172;BA.debugLine="Hide";
_vvvvvv3();
 break; }
}
;
 //BA.debugLineNum = 178;BA.debugLine="subname = mEventName & \"_Click\"";
_subname = _vvvvvvv4+"_Click";
 //BA.debugLineNum = 179;BA.debugLine="If SubExists(mModule, subname) Then";
if (__c.SubExists(ba,_vvvvvvv3,_subname)) { 
 //BA.debugLineNum = 180;BA.debugLine="CallSub2(mModule, subname, Value)";
__c.CallSubNew2(ba,_vvvvvvv3,_subname,_value);
 };
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvv7(boolean _m) throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Sub mostrar (m As Boolean)";
 //BA.debugLineNum = 214;BA.debugLine="mSlidePanel.Visible = m";
_vvvvvvv1.setVisible(_m);
 //BA.debugLineNum = 215;BA.debugLine="mSlidePanel.BringToFront";
_vvvvvvv1.BringToFront();
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public String  _out_animationend() throws Exception{
 //BA.debugLineNum = 139;BA.debugLine="Private Sub Out_AnimationEnd";
 //BA.debugLineNum = 140;BA.debugLine="mSlidePanel.top = actividad.Height-60dip";
_vvvvvvv1.setTop((int) (_vvvvvvvv5.getHeight()-__c.DipToCurrent((int) (60))));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv0() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Public Sub Show";
 //BA.debugLineNum = 102;BA.debugLine="Log (\"SHOW\")";
__c.Log("SHOW");
 //BA.debugLineNum = 103;BA.debugLine="If isVisible = True Then Return";
if (_vvvvvv4==__c.True) { 
if (true) return "";};
 //BA.debugLineNum = 105;BA.debugLine="mBackPanel.BringToFront";
_vvvvvvv2.BringToFront();
 //BA.debugLineNum = 106;BA.debugLine="mSlidePanel.BringToFront";
_vvvvvvv1.BringToFront();
 //BA.debugLineNum = 107;BA.debugLine="mBackPanel.Visible=True";
_vvvvvvv2.setVisible(__c.True);
 //BA.debugLineNum = 109;BA.debugLine="mBackPanel.Left = 0";
_vvvvvvv2.setLeft((int) (0));
 //BA.debugLineNum = 111;BA.debugLine="mSlidePanel.top=actividad.Height-mSlidePanel.Heig";
_vvvvvvv1.setTop((int) (_vvvvvvvv5.getHeight()-_vvvvvvv1.getHeight()));
 //BA.debugLineNum = 112;BA.debugLine="mInAnimation.InitializeTranslate(\"\", 0, mSlidePan";
_vvvvvvvv1.InitializeTranslate(ba,"",(float) (0),(float) (_vvvvvvv1.getHeight()),(float) (0),(float) (0));
 //BA.debugLineNum = 113;BA.debugLine="mInAnimation.SetInterpolator( mInAnimation.INTERP";
_vvvvvvvv1.SetInterpolator(_vvvvvvvv1.INTERPOLATOR_BOUNCE);
 //BA.debugLineNum = 114;BA.debugLine="mInAnimation.Duration = 500";
_vvvvvvvv1.setDuration((long) (500));
 //BA.debugLineNum = 115;BA.debugLine="mSlidePanel.Visible = True";
_vvvvvvv1.setVisible(__c.True);
 //BA.debugLineNum = 117;BA.debugLine="mInAnimation.Start(mSlidePanel)";
_vvvvvvvv1.Start((android.view.View)(_vvvvvvv1.getObject()));
 //BA.debugLineNum = 119;BA.debugLine="isVisible = True";
_vvvvvv4 = __c.True;
 //BA.debugLineNum = 120;BA.debugLine="b.SetBackgroundImage(LoadBitmap (File.DirAssets";
_vvvvvvvv3.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),"boton cerrar.png").getObject()));
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvvv0(long _ms) throws Exception{
long _now = 0L;
 //BA.debugLineNum = 220;BA.debugLine="Sub Sleep(ms As Long)";
 //BA.debugLineNum = 221;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 222;BA.debugLine="If ms > 1000 Then ms =1000";
if (_ms>1000) { 
_ms = (long) (1000);};
 //BA.debugLineNum = 223;BA.debugLine="now=DateTime.now";
_now = __c.DateTime.getNow();
 //BA.debugLineNum = 224;BA.debugLine="Do Until (DateTime.now>now+ms)";
while (!((__c.DateTime.getNow()>_now+_ms))) {
 //BA.debugLineNum = 225;BA.debugLine="DoEvents";
__c.DoEvents();
 }
;
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
