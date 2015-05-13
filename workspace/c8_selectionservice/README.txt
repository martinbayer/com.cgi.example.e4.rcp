Selection service is used to notify the application model's object about the object's selection. It is enough to create a listener for objects which can be 
selected using injected ESelectionService object.
Example of this mechanism is shown in SamplePart class in which the selection service is used in SelectionChangedListener (on tableViewer).
Selection is set using ESelectionService#setSelection(Object o) and can be gained using injected optional object named with IServiceConstants.ACTIVE_SELECTION constant.
Also selection and post selection listener can be used on ESelectionService object.