Type=Class
Version=3.82
B4A=true
@EndOfDesignText@
'SlideMenu Class module
'
'Author:  Markus Stipp
'Version: 1.0

'Class module
Private Sub Class_Globals
	Type ActionItem (Text As String, Image As Bitmap, Value As Object)
	
	Private MenuPanel As Panel
	Private mBackPanel As Panel
		
	Private mModule As Object
	Private mEventName As String
	
	Private mListView As ListView

	
End Sub

'Initializes the SlideMenu object
' Activity - Pass a reference to the Activity here where the Slidemenu should be added to.
' Module - The calling Module. Pass the "Me" reference here
' EventNAme - EventName for the Click event
' Top - Top position of the Menu.
' Width - Width of the menu
Sub Initialize( Mpanel As Panel, Module As Object, EventName As String,Activity As Activity)
	mModule = Module
	mEventName = EventName

	MenuPanel=Mpanel


	mListView.Initialize("mListView")
	mListView.TwoLinesAndBitmap.SecondLabel.Visible = False
	mListView.TwoLinesAndBitmap.ItemHeight = 50dip
	
   mListView.TwoLinesAndBitmap.Label.TextSize=16
	mListView.TwoLinesAndBitmap.Label.Gravity = Gravity.CENTER_VERTICAL
	mListView.TwoLinesAndBitmap.Label.Height = mListView.TwoLinesAndBitmap.ItemHeight
	mListView.TwoLinesAndBitmap.Label.Top = 0
	mListView.TwoLinesAndBitmap.ImageView.SetLayout(13dip, 13dip, 24dip, 24dip)
	mListView.Color = Colors.ARGB(220,0,0,0)
	
	
	mBackPanel.Initialize("mBackPanel")
	mBackPanel.Color = Colors.Transparent
	Activity.AddView(mBackPanel, 0, 0, 100%x, 100%y)
	
	MenuPanel.AddView(mListView, 0, 0, 100%x, 100%y)
	MenuPanel.Visible = False
	mBackPanel.Visible=False
	
	Activity.AddView(MenuPanel, 0, 50%x, 270dip, 210dip)
	
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

Private Sub mListView_ItemClick (Position As Int, Value As Object)
	Dim subname As String
	Hide
	subname = mEventName & "_Click"
	If SubExists(mModule, subname) Then
		CallSub2(mModule, subname, Value)
	End If
End Sub



'Show the SlideMenu
Public Sub Show
	
	
	mBackPanel.BringToFront
	MenuPanel.BringToFront
	mBackPanel.visible = True
	MenuPanel.Visible = True
	
End Sub


Public Sub Hide
	
	mBackPanel.visible =False
    MenuPanel.visible = False
	
End Sub