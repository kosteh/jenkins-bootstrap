/*
Purpose:
Configures a number of NodeJS auto-installers in a Jenkins instance.
*/

import jenkins.plugins.nodejs.tools.NodeJSInstaller
import jenkins.plugins.nodejs.tools.NodeJSInstallation
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure NodeJS installations ----------------------------------------"
println ""

// Define a number of NodeJS installations
def tools = [
  "NodeJS Latest"           : "6.9.1",
  "NodeJS 6"                : "6.9.1",
  "NodeJS 6.9.1"            : "6.9.1",
  "NodeJS 6.9.0"            : "6.9.0",
  "NodeJS 6.7.0"            : "6.7.0",
  "NodeJS Latest (Windows)" : "6.9.1",
  "NodeJS 6 (Windows)"      : "6.9.1",
  "NodeJS 6.9.1 (Windows)"  : "6.9.1",
  "NodeJS 4 (Windows)"      : "4.4.4"
]

// Remove existing NodeJS installations from the "tools" directory.
// TODO: find out how to remove the directory on a Jenkins agent.
def dir = "${System.getenv("JENKINS_HOME")}/tools/jenkins.plugins.nodejs.tools.NodeJSInstallation"
def toolDir = new File(dir)
if ( toolDir.deleteDir()) {
  println "Tool directory ${dir} deleted successfully\n"
}

// Configure the NodeJS installations
def installations = []
def descriptor = Jenkins.getInstance().getDescriptor("jenkins.plugins.nodejs.tools.NodeJSInstallation")

for (tool in tools) {
  if ( tool.key =~ /(Windows)/ ) {
    // NodeJS on Windows is installed manually, so only the installation path is required 
    def installation = new NodeJSInstallation(tool.key, "E:\\Apps\\NodeJS\\${tool.value}", null)
    installations.push(installation)
  }
  else {
    def installer = new NodeJSInstaller(tool.value, "bower bower-art", 100)
    def installSourceProp = new InstallSourceProperty([installer])
    def installation = new NodeJSInstallation(tool.key, "", [installSourceProp])
    installations.push(installation)
  }
}

// Persist the NodeJS configuration
descriptor.setInstallations(installations.toArray(new NodeJSInstallation[0]))
descriptor.save()

// Print installed NodeJS versions
descriptor.getInstallations().each { NodeJSInstallation tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"
