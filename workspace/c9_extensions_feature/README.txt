Extensions are used to extend the application's functionality via small parts/modules.
There are two main parts:
1) 	Extension point defines the structure of extensions. Its scheme can be adjusted using "Open extension point schema".
	There needs to be new element (for example 'client') with attribute created. The client's attribute specifies the interface which 
	is used to describe	each extension (IHelpProvider in the example).

2) 	Extension is a plug-in which has to implement some extension point. So in the Extensions tab the extension point is picked up
	and new instance of the element specified in the extension point is created (client in this case).
	Its definition contains the concrete configuration of each extension point element's attributes (class implementing the interface
	specified in the extension point's element (SosCalled and SendHelpMessage in the example).
	
Then plug-in with the extension point specification must be included in the target product and if any of the implementing plug-ins
are contained, then these are used. So if the c9_extensions_callsos and c9_extensions_sendmessage are contained in c9_extensions_feature,
both plug-ins are used when extension point's elements are loaded and used in HelpMeHandler. Also ISafeRunnable interface and SafeRunner#run
method should be used when invoking plug-ins to handle all possible exceptions correctly.