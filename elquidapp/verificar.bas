Type=Service
Version=3.2
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim shouldScheduleNextTask As Boolean
	shouldScheduleNextTask = True 'by default we are scheduling the next update

End Sub
Sub Service_Create

End Sub

Sub Service_Start (StartingIntent As Intent)
   'schedule the next run in 10 minutes
    If shouldScheduleNextTask Then
        StartServiceAt("", DateTime.Now + 10 * DateTime.TicksPerMinute, False)
    End If

Dim n As Notification
n.Initialize
n.Icon = "icon"
n.SetInfo("This is the title", "and this is the body.", Main) 
'Change Main (above) to "" if this code is in the main module.
n.Notify(1) 

End Sub

Sub Service_Destroy

End Sub
