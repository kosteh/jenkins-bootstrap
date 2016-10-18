println "------ Configure Maven installers ----------------------------------"
println ""
println "INFO : No Maven installers defined."
println ""
println "------ END ---------------------------------------------------------"

return

import jenkins.model.*

println "Adding an auto installers for Maven"

def mavenPluginExtension = Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0]

// Current installations:
// def asList = (mavenPluginExtension.installations as List)
// Start over with empty list of installations
aslist = []
// Add installations 
asList.add(new hudson.tasks.Maven.MavenInstallation('Maven Latest', null, 
      [new hudson.tools.InstallSourceProperty([new hudson.tasks.Maven.MavenInstaller("3.3.9")])]
asList.add(new hudson.tasks.Maven.MavenInstallation('Maven 3.3.9', null, 
      [new hudson.tools.InstallSourceProperty([new hudson.tasks.Maven.MavenInstaller("3.3.9")])]

mavenPluginExtension.installations = asList

mavenPluginExtension.save()
println "OK - Maven auto-installers (from Apache) added" 

