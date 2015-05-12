This example shows us how can be the application context used.

Property can be set to the context using something like application.getContext().set(Person.class, p);.
It is used in AddPart class.

The content of the application context can be gained using @Inject annotation at the property (Person instance
injected in the ShowDataInjectedDirectlyPart) and @Inject annotation at the method (ShowDataInjectedDirectlyPart#reloadData).
Objects are loaded automatically here when 1)the Part is created 2)the injected object is changed

Object in the context can be loaded manually using application.getContext().get(....). This approach
is used in ShowDataFromContextPart in refreshData method.