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
	Type ActionItem (Text As String, Image As Bitmap, Value As Object)
	
	Private mSlidePanel As Panel
	Private mBackPanel As Panel
	
	
	Private mModule As Object
	Private mEventName As String
	
	Private mListView As ListView

	Private mInAnimation As AnimationPlus
	Private mOutAnimation As Animation
	
	Private b As Button
	Private linea As Panel
	
	Private actividad As Activity
	Private isVisible As Boolean =False
	
	
End Sub

'Initializes the SlideMenu object
' Activity - Pass a reference to the Activity here where the Slidemenu should be added to.
' Module - The calling Module. Pass the "Me" reference here
' EventNAme - EventName for the Click event
' Top - Top position of the Menu.
' Width - Width of the menu
Sub Initialize(Activity As Activity, Module As Object, EventName As String, Top As Int, Width As Int)
	mModule = Module
	mEventName = EventName
	actividad=Activity

	mSlidePanel.Initialize("mSlidePanel")
	
	linea.Initialize("")


	mListView.Initialize("mListView")
	mListView.TwoLinesAndBitmap.SecondLabel.Visible = False
	mListView.TwoLinesAndBitmap.ItemHeight = 50dip
	mListView.TwoLinesAndBitmap.Label.Gravity = Gravity.CENTER_VERTICAL
	mListView.TwoLinesAndBitmap.Label.Height = mListView.TwoLinesAndBitmap.ItemHeight
	mListView.TwoLinesAndBitmap.Label.Top = 0
	mListView.TwoLinesAndBitmap.ImageView.SetLayout(13dip, 13dip, 32dip, 32dip)
	mListView.Color = Colors.ARGB(255,255,255,255)
	linea.Color=Colors.ARGB(204,216,37,35)
	mListView.TwoLinesAndBitmap.Label.TextColor=Colors.Black
	

	mSlidePanel.color=Colors.Transparent
	
	
	
	
	Activity.AddView(mSlidePanel, (Activity.Width - Width)/2, Top, Width, 204dip + 75dip)
	
	mBackPanel.Initialize("mBackPanel")
	mBackPanel.Color = Colors.ARGB(180,0,0,0)
	Activity.AddView(mBackPanel, -100%x, 0, 100%x, 100%y)

	mSlidePanel.AddView(mListView, 0, 75dip, Width, mSlidePanel.Height)
	mSlidePanel.AddView(linea, 0, 65dip, Width, 10dip)
	mSlidePanel.Visible = True
	
	b.Initialize("botonMenu")
    b.SetBackgroundImage(LoadBitmap (File.DirAssets,"boton abrir.png"))
    mSlidePanel.AddView(b,(Width - 65dip)/2,0,65dip,65dip)
	b.BringToFront
	
End Sub

'Adds an item to the SlideMenu
' Text - Text to show in menu
' Image - Image to show
' ReturnValue - The value that will be returned in the Click event
Public Sub AddItem(Text As String, Image As Bitmap, ReturnValue As Object)
	Dim item As ActionItem
	item.Initialize
	item.Text = Text
	item.Image = Image
	item.Value = ReturnValue
	
	If Not(Image.IsInitialized) Then
		mListView.AddTwoLinesAndBitmap2(Text, "", Null, ReturnValue)
	Else
		mListView.AddTwoLinesAndBitmap2(Text, "", Image, ReturnValue)
	End If
End Sub

'Show the SlideMenu
Public Sub Show 

    Log ("SHOW")
	If isVisible = True Then Return
	
	mBackPanel.BringToFront
	mSlidePanel.BringToFront
    mBackPanel.Visible=True
	
	mBackPanel.Left = 0
	
	mSlidePanel.top=actividad.Height-mSlidePanel.Height
	mInAnimation.InitializeTranslate("", 0, mSlidePanel.Height, 0, 0)
	mInAnimation.SetInterpolator( mInAnimation.INTERPOLATOR_BOUNCE)
	mInAnimation.Duration = 500
	mSlidePanel.Visible = True
	
	mInAnimation.Start(mSlidePanel)
	
	isVisible = True
   b.SetBackgroundImage(LoadBitmap (File.DirAssets,"boton cerrar.png"))
	
End Sub

'Hide the SlideMenu
Public Sub Hide
	If isVisible = False Then Return
	
	b.SetBackgroundImage(LoadBitmap (File.DirAssets,"boton abrir.png"))
	
	mBackPanel.visible = False
    
	mOutAnimation.InitializeTranslate("Out", 0, 0, 0, mSlidePanel.Height)
	mOutAnimation.Duration = 200
	mOutAnimation.Start(mSlidePanel)
	
	isVisible = False
End Sub

Private Sub Out_AnimationEnd
  mSlidePanel.top = actividad.Height-60dip
	
End Sub

Private Sub mBackPanel_Touch (Action As Int, X As Float, Y As Float)
	If Action = 1 Then
		Hide
	End If
End Sub

Private Sub mListView_ItemClick (Position As Int, Value As Object)
	Dim subname As String
	
	Log("POSITION= " & Position)
	
	
	Select Position
	  
	  Case 0
	    mSlidePanel.Visible = False
	    isVisible = False
	    mBackPanel.visible = False
	    mSlidePanel.top = actividad.Height-60dip
	    b.SetBackgroundImage(LoadBitmap (File.DirAssets,"boton abrir.png"))
	  
	  Case 3
	    mSlidePanel.Visible = False
	    mBackPanel.visible = False
	    mSlidePanel.top = actividad.Height-60dip
	    b.SetBackgroundImage(LoadBitmap (File.DirAssets,"boton abrir.png"))
	  
	  Case Else
	      Hide
	
	End Select
	
	
		
	subname = mEventName & "_Click"
	If SubExists(mModule, subname) Then
		CallSub2(mModule, subname, Value)
	End If
End Sub


Private Sub botonMenu_Click
  
  If  isVisible = False Then 
  			Show
			Return
		End If	
  
  If isVisible = True Then 
  		Hide
		Return
	End If

End Sub

'Private Sub mSlidePanel_Touch (Action As Int, X As Float, Y As Float)
'   If Action = 1  AND isVisible = False Then 
'   			Show
'			Return
'		End If
'		
'   If Action = 1  AND isVisible = True Then 
'   			Hide
'			Return
'		End If
'   
'End Sub

Sub mostrar (m As Boolean)

 mSlidePanel.Visible = m
  mSlidePanel.BringToFront

End Sub


Sub Sleep(ms As Long)
Dim now As Long
   If ms > 1000 Then ms =1000   
   now=DateTime.now
   Do Until (DateTime.now>now+ms)
     DoEvents
   Loop
End Sub