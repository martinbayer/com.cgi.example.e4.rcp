Fragment is the easy way of extending application with new application model's parts and new functionality connected to them.
In our example the window with the "map functionality" is opened and if there is any plug-in with fragment connected to it,
it is connected to the "main" application model's elements by theirs IDs.

c10Framents_googlemaps and c10_fragments_mapycz are the fragment plugins which extends c10_fragments eclipse 4 RCP application.
For example the menu item with id "c10_fragments_googlemaps.handledmenuitem.displayGooglemaps"(c10Framents_googlemaps) 
is connected as the child to the menu with ID "c10_fragments.menu.maps" (c10_fragments). 

You can add and use the application model live editor to display that fragments are added to the main application model:
1) add org.eclipse.e4.tools.emf.liveeditor plug-in to the run configuration (not to the product - we don't want to have it in our exported product) and all required plug-ins
2) pressing Alt+Shift+F9 will display the live editor (it contains also elements for the live editor)
more details:
http://www.vogella.com/tutorials/Eclipse4LiveModelEditor/article.html