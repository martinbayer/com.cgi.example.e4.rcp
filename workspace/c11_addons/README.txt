c11_addons_storage and c11_addons_storage_DB are only utilities plug-ins here (not important)

Add-on is the small part of the functionality which is registered to the application during startup.
It can consume or produce events or they can adjust user interface before it is rendered by the framework.

In the example there are two add-ons. First of them (InitStorageAddon) initializes application context with
database storage for Person objects. Second one (ReconnectPersonStorageAddon) is used to reconnect the Person objects storage.
It could be more modular if the Add-on for reconnecting was part of the fragment so we could add this functionality
to the existing application using fragment.