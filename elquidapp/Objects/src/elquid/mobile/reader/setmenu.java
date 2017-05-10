package elquid.mobile.reader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class setmenu extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "elquid.mobile.reader.setmenu");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", elquid.mobile.reader.setmenu.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvv2 = null;
public Object _vvvvvvv3 = null;
public String _vvvvvvv4 = "";
public anywheresoftware.b4a.objects.ListViewWrapper _vvvvvvv5 = null;
public float _vvvvvvv6 = 0f;
public anywheresoftware.b4a.samples.httputils2.httputils2service _vvvvv4 = null;
public elquid.mobile.reader.main _vvvvv5 = null;
public elquid.mobile.reader.telefonomain _vvvvv6 = null;
public elquid.mobile.reader.tabletmain _vvvvv7 = null;
public elquid.mobile.reader.serviciod _vvvvv0 = null;
public elquid.mobile.reader.statemanager _vvvvvv1 = null;
public static class _actionitem2{
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
public String  _vvvvvv2(String _text) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Public Sub AddItem(Text)";
 //BA.debugLineNum = 75;BA.debugLine="altura=altura+ mListView.SingleLineLayout.Item";
_vvvvvvv6 = (float) (_vvvvvvv6+_vvvvvvv5.getSingleLineLayout().getItemHeight());
 //BA.debugLineNum = 76;BA.debugLine="mSlidePanel.Height=altura+3dip";
_vvvvvvv1.setHeight((int) (_vvvvvvv6+__c.DipToCurrent((int) (3))));
 //BA.debugLineNum = 77;BA.debugLine="mListView.Height=altura-1dip";
_vvvvvvv5.setHeight((int) (_vvvvvvv6-__c.DipToCurrent((int) (1))));
 //BA.debugLineNum = 79;BA.debugLine="mListView.AddSingleLine (Text)";
_vvvvvvv5.AddSingleLine(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Type ActionItem2 (Text As String, Image As Bitmap";
;
 //BA.debugLineNum = 10;BA.debugLine="Private mSlidePanel As Panel";
_vvvvvvv1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private mBackPanel As Panel";
_vvvvvvv2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private mModule As Object";
_vvvvvvv3 = new Object();
 //BA.debugLineNum = 14;BA.debugLine="Private mEventName As String";
_vvvvvvv4 = "";
 //BA.debugLineNum = 16;BA.debugLine="Private mListView As ListView";
_vvvvvvv5 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private altura As Float";
_vvvvvvv6 = 0f;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv3() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Public Sub Hide";
 //BA.debugLineNum = 96;BA.debugLine="If isVisible = False Then Return";
if (_vvvvvv4()==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 98;BA.debugLine="mBackPanel.visible =False";
_vvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 99;BA.debugLine="mSlidePanel.visible = False";
_vvvvvvv1.setVisible(__c.False);
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,Object _module,String _eventname,int _top,int _width) throws Exception{
innerInitialize(_ba);
anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub Initialize(Activity As Activity, Module As Obj";
 //BA.debugLineNum = 30;BA.debugLine="Dim label1 As Label";
_label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="mModule = Module";
_vvvvvvv3 = _module;
 //BA.debugLineNum = 33;BA.debugLine="mEventName = EventName";
_vvvvvvv4 = _eventname;
 //BA.debugLineNum = 35;BA.debugLine="mSlidePanel.Initialize(\"mSlidePanel\")";
_vvvvvvv1.Initialize(ba,"mSlidePanel");
 //BA.debugLineNum = 37;BA.debugLine="altura=0";
_vvvvvvv6 = (float) (0);
 //BA.debugLineNum = 39;BA.debugLine="mListView.Initialize(\"mListView\")";
_vvvvvvv5.Initialize(ba,"mListView");
 //BA.debugLineNum = 41;BA.debugLine="mListView.Color = Colors.ARGB(255,255,255,255)";
_vvvvvvv5.setColor(__c.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 42;BA.debugLine="SetDivider(mListView, Colors.ARGB(255,255,255,255";
_vvvvvv6(_vvvvvvv5,__c.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)),__c.DipToCurrent((int) (1)));
 //BA.debugLineNum = 44;BA.debugLine="label1 = mListView.SingleLineLayout.Label";
_label1 = _vvvvvvv5.getSingleLineLayout().Label;
 //BA.debugLineNum = 45;BA.debugLine="label1.TextSize = 18";
_label1.setTextSize((float) (18));
 //BA.debugLineNum = 46;BA.debugLine="label1.TextColor = Colors.Black";
_label1.setTextColor(__c.Colors.Black);
 //BA.debugLineNum = 47;BA.debugLine="label1.Top=0";
_label1.setTop((int) (0));
 //BA.debugLineNum = 48;BA.debugLine="label1.Left=15dip";
_label1.setLeft(__c.DipToCurrent((int) (15)));
 //BA.debugLineNum = 49;BA.debugLine="label1.Gravity= Gravity.CENTER_VERTICAL";
_label1.setGravity(__c.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 50;BA.debugLine="mListView.SingleLineLayout.ItemHeight=50dip";
_vvvvvvv5.getSingleLineLayout().setItemHeight(__c.DipToCurrent((int) (50)));
 //BA.debugLineNum = 51;BA.debugLine="label1.Height = mListView.SingleLineLayout.ItemHe";
_label1.setHeight(_vvvvvvv5.getSingleLineLayout().getItemHeight());
 //BA.debugLineNum = 54;BA.debugLine="Activity.AddView(mSlidePanel, Activity.Width-Widt";
_activity.AddView((android.view.View)(_vvvvvvv1.getObject()),(int) (_activity.getWidth()-_width-__c.DipToCurrent((int) (5))),_top,_width,__c.DipToCurrent((int) (200)));
 //BA.debugLineNum = 55;BA.debugLine="mSlidePanel.Color=Colors.Gray";
_vvvvvvv1.setColor(__c.Colors.Gray);
 //BA.debugLineNum = 57;BA.debugLine="mBackPanel.Initialize(\"mBackPanel\")";
_vvvvvvv2.Initialize(ba,"mBackPanel");
 //BA.debugLineNum = 58;BA.debugLine="mBackPanel.Color = Colors.Transparent";
_vvvvvvv2.setColor(__c.Colors.Transparent);
 //BA.debugLineNum = 59;BA.debugLine="Activity.AddView(mBackPanel, 0, 0, 100%x, 100%y)";
_activity.AddView((android.view.View)(_vvvvvvv2.getObject()),(int) (0),(int) (0),__c.PerXToCurrent((float) (100),ba),__c.PerYToCurrent((float) (100),ba));
 //BA.debugLineNum = 61;BA.debugLine="mSlidePanel.AddView(mListView, 1dip, 1dip, mSlide";
_vvvvvvv1.AddView((android.view.View)(_vvvvvvv5.getObject()),__c.DipToCurrent((int) (1)),__c.DipToCurrent((int) (1)),(int) (_vvvvvvv1.getWidth()-__c.DipToCurrent((int) (2))),(int) (_vvvvvvv1.getHeight()-__c.DipToCurrent((int) (2))));
 //BA.debugLineNum = 62;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvv1.setVisible(__c.False);
 //BA.debugLineNum = 63;BA.debugLine="mBackPanel.Visible=False";
_vvvvvvv2.setVisible(__c.False);
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public boolean  _vvvvvv4() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Public Sub isVisible As Boolean";
 //BA.debugLineNum = 124;BA.debugLine="Return mSlidePanel.Visible";
if (true) return _vvvvvvv1.getVisible();
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return false;
}
public String  _vvvvvv5() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="public Sub limpiar()";
 //BA.debugLineNum = 128;BA.debugLine="mListView.Clear";
_vvvvvvv5.Clear();
 //BA.debugLineNum = 129;BA.debugLine="altura=0";
_vvvvvvv6 = (float) (0);
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public String  _mbackpanel_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Private Sub mBackPanel_Touch (Action As Int, X As";
 //BA.debugLineNum = 108;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 109;BA.debugLine="Hide";
_vvvvvv3();
 };
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public String  _mlistview_itemclick(int _position,Object _value) throws Exception{
String _subname = "";
 //BA.debugLineNum = 113;BA.debugLine="Private Sub mListView_ItemClick (Position As Int,";
 //BA.debugLineNum = 114;BA.debugLine="Dim subname As String";
_subname = "";
 //BA.debugLineNum = 115;BA.debugLine="Hide";
_vvvvvv3();
 //BA.debugLineNum = 116;BA.debugLine="subname = mEventName & \"_Click\"";
_subname = _vvvvvvv4+"_Click";
 //BA.debugLineNum = 117;BA.debugLine="If SubExists(mModule, subname) Then";
if (__c.SubExists(ba,_vvvvvvv3,_subname)) { 
 //BA.debugLineNum = 118;BA.debugLine="CallSub2(mModule, subname, Value)";
__c.CallSubNew2(ba,_vvvvvvv3,_subname,_value);
 };
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public String  _out_animationend() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Private Sub Out_AnimationEnd";
 //BA.debugLineNum = 104;BA.debugLine="mSlidePanel.Visible = False";
_vvvvvvv1.setVisible(__c.False);
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv6(anywheresoftware.b4a.objects.ListViewWrapper _lv,int _color,int _height) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub SetDivider(lv As ListView, Color As Int, Heigh";
 //BA.debugLineNum = 134;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 135;BA.debugLine="r.Target = lv";
_r.Target = (Object)(_lv.getObject());
 //BA.debugLineNum = 136;BA.debugLine="Dim CD As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 137;BA.debugLine="CD.Initialize(Color, 0)";
_cd.Initialize(_color,(int) (0));
 //BA.debugLineNum = 138;BA.debugLine="r.RunMethod4(\"setDivider\", Array As Object(CD),";
_r.RunMethod4("setDivider",new Object[]{(Object)(_cd.getObject())},new String[]{"android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 139;BA.debugLine="r.RunMethod2(\"setDividerHeight\", Height, \"java.";
_r.RunMethod2("setDividerHeight",BA.NumberToString(_height),"java.lang.int");
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv7(int _x,int _y) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Public Sub setPos(x As Int, y As Int)";
 //BA.debugLineNum = 67;BA.debugLine="mSlidePanel.Left=x";
_vvvvvvv1.setLeft(_x);
 //BA.debugLineNum = 68;BA.debugLine="mSlidePanel.Top=y";
_vvvvvvv1.setTop(_y);
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public String  _vvvvvv0() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Public Sub Show";
 //BA.debugLineNum = 85;BA.debugLine="If isVisible = True Then Return";
if (_vvvvvv4()==__c.True) { 
if (true) return "";};
 //BA.debugLineNum = 87;BA.debugLine="mBackPanel.BringToFront";
_vvvvvvv2.BringToFront();
 //BA.debugLineNum = 88;BA.debugLine="mSlidePanel.BringToFront";
_vvvvvvv1.BringToFront();
 //BA.debugLineNum = 89;BA.debugLine="mBackPanel.visible = True";
_vvvvvvv2.setVisible(__c.True);
 //BA.debugLineNum = 90;BA.debugLine="mSlidePanel.Visible = True";
_vvvvvvv1.setVisible(__c.True);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
