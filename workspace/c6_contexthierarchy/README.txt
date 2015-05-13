Example is used to explain the hierarchical approach to the context.
Hierarchy of the context is workbench<window<perspective<part.
So if the object is trying to be used in the Part and it is not contained in the part's context,
it is searched in perspective's context, then window's context is checked and if it is not still contained,
workbench's context is used.

Every object contained in the Application model can work with context. Local context can be used by
injection of IEclipseContext object. It is shown in SaveHandler where the last save action's time is printed out.