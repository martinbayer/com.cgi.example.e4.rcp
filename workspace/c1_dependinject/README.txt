Basic dependency injection is shown in this example.
In DependencyInjection class there is a Composite parent property injected in the method annotated with @PostConstruct.
It is injected automatically from the application context.
So you can inject also another objects which are present in the context...for example MPart