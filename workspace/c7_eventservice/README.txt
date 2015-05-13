This example shows the event service mechanism. This mechanism provides real time notification about actions in the application.
It can be used for status bar notification based on the user actions etc.

Topics can be used with events and you can check theirs usage by searching references of PersonEventConstants properties.

Description of the example:
1) for update name and email operations, the notification is displayed in the status bar (TrimBars in application model - AppStatusBar class)
2) event is send when the person is added or deleted
3) fields in ShowPersonPart class are set with values if it was instantiated before event was sent so it must be displayed before event is sent
4) if the part is not initialized (displayed) before event is sent, event is not delivered to it as it does not exist