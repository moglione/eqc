Type=Class
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'SlideMenu Class module
'
'Author:  Markus Stipp
'Version: 1.0

'Class module
Private Sub Class_Globals
	Type ActionItem2 (Text As String, Image As Bitmap, Value As Object)
	
	Private mSlidePanel As Panel
	Private mBackPanel As Panel
	
	Private mModule As Object
	Private mEventName As String
	
	Private mListView As ListView

	
   Private altura As Float

End Sub

'Initializes the SlideMenu object
' Activity - Pass a reference to the Activity here where the Slidemenu should be added to.
' Module - The calling Module. Pass the "Me" reference here
' EventNAme - EventName for the Click event
' Top - Top position of the Menu.
' Width - Width of the menu
Sub Initialize(Activity As Activity, Module As Object, EventName As String, Top As Int, Width As Int)
	Dim label1 As Label
		
	mModule = Module
	mEventName = EventName
    
	mSlidePanel.Initialize("mSlidePanel")
	
	altura=0
	
	mListView.Initialize("mListView")

	mListView.Color = Colors.ARGB(255,255,255,255)
	SetDivider(mListView, Colors.ARGB(255,255,255,255), 1dip)
	
	label1 = mListView.SingleLineLayout.Label
    label1.TextSize = 18
    label1.TextColor = Colors.Black
	label1.Top=0
	label1.Left=15dip
	label1.Gravity= Gravity.CENTER_VERTICAL
    mListView.SingleLineLayout.ItemHeight=50dip
	label1.Height = mListView.SingleLineLayout.ItemHeight
    
	
	Activity.AddView(mSlidePanel, Activity.Width-Width-5dip, Top, Width, 200dip)
	mSlidePanel.Color=Colors.Gray
	
	mBackPanel.Initialize("mBackPanel")
	mBackPanel.Color = Colors.Transparent
	Activity.AddView(mBackPanel, 0, 0, 100%x, 100%y)

	mSlidePanel.AddView(mListView, 1dip, 1dip, mSlidePanel.Width-2dip, mSlidePanel.Height-2dip)
	mSlidePanel.Visible = False
	mBackPanel.Visible=False
End Sub

Public Sub setPos(x As Int, y As Int)
  mSlidePanel.Left=x
  mSlidePanel.Top=y
End Sub


'Adds an item to the Menu

Public Sub AddItem(Text)
    altura=altura+ mListView.SingleLineLayout.ItemHeight
    mSlidePanel.Height=altura+3dip
	mListView.Height=altura-1dip

    mListView.AddSingleLine (Text)
	
End Sub

'Show the SlideMenu
Public Sub Show
	If isVisible = True Then Return
	
	mBackPanel.BringToFront
	mSlidePanel.BringToFront
	mBackPanel.visible = True
	mSlidePanel.Visible = True
	
End Sub

'Hide the SlideMenu
Public Sub Hide
	If isVisible = False Then Return
	
	mBackPanel.visible =False
    mSlidePanel.visible = False
	
End Sub

Private Sub Out_AnimationEnd
	mSlidePanel.Visible = False
End Sub

Private Sub mBackPanel_Touch (Action As Int, X As Float, Y As Float)
	If Action = 1 Then
		Hide
	End If
End Sub

Private Sub mListView_ItemClick (Position As Int, Value As Object)
	Dim subname As String
	Hide	
	subname = mEventName & "_Click"
	If SubExists(mModule, subname) Then
		CallSub2(mModule, subname, Value)
	End If
End Sub

'Check if the menu is currently visible
Public Sub isVisible As Boolean
	Return mSlidePanel.Visible
End Sub

public Sub limpiar()
	mListView.Clear
	altura=0
End Sub


Sub SetDivider(lv As ListView, Color As Int, Height As Int)
   Dim r As Reflector
   r.Target = lv
   Dim CD As ColorDrawable
   CD.Initialize(Color, 0)
   r.RunMethod4("setDivider", Array As Object(CD), Array As String("android.graphics.drawable.Drawable"))
   r.RunMethod2("setDividerHeight", Height, "java.lang.int")
End Sub