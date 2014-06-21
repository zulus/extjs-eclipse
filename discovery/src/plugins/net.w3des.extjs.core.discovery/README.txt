1) Create a local repository

2) Create a xml file having the simple content pointing to plugin net.w3des.extjs.core.discovery
    <?xml version="1.0" encoding="UTF-8"?>
    <directory xmlns="http://www.eclipse.org/mylyn/discovery/directory/">
        <entry permitCategories="true" url="http://...../plugins/net.w3des.extjs.core.discovery_1.0.0.qualifier.jar"/>
    </directory>

3) Let ExtJSDiscovery.DEFAULT_URL point to that dictionary.xml file or set the java property "extjs.discovery.url" while starting eclipse

4) Within plugin net.w3des.extjs.core.discovery put all the sdks that can be downloaded
   See the given examples for details
   
   The siteUrl attribute of a descriptor contains the url where the plugin is found
   The kind attribute of a descriptor contains the type of plugin, currently:
       extjs-sdk for extjs runtime environments
       sencha-touch-sdk for sencha touch runtime environments
   To fit a version:
       extjs-sdk-4.0 for the extjs runtime of version 4.0
       sencha-touch-sdk-2.3 for the sencha touch runtime of version 2.3
       
5) Create the features to be installed for the components listed in the net.w3des.extjs.core.discovery plugin

6) Create the plugins distibuting the sdk and use the extension net.w3des.extjs.core.runtimelib to declare them

7) Build everything. Exmaple cli command
    java 
    -jar [your-eclipse-install]\plugins\org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar
    -application org.eclipse.equinox.p2.publisher.FeaturesAndBundlesPublisher
    -metadataRepository file://[path-to-extjs-branch:gh-pages]/discovery/repository
    -artifactRepository file://[path-to-extjs-branch:gh-pages]/discovery/repository
    -source [path-to-extjs-branch:gh-pages]/discovery/src
    -configs gtk.linux.x86
    -compress
    -publishArtifacts
    