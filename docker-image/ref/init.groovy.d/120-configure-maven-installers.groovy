/*

Purpose:
Configures a number of Maven auto-installers in a Jenkins instance.

*/

import hudson.tasks.Maven
import hudson.tools.MavenInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Maven ------------------------------------------------"
println ""

// Define and configure a number of Maven installations
def tools = [
  "Maven Latest"   : "3.3.9",
  "Maven 3 Latest" : "3.3.9",
  "Maven 3.3.9"    : "3.3.9",
  "Maven 2 Latest" : "2.2.1",
  "Maven 2.2.1"    : "2.2.1"
]

// Remove existing Maven installations from the "tools" directory
// TODO: find out how to remove the directory on a Jenkins agent.
def dir = "${System.getenv("JENKINS_HOME")}/tools/hudson.tasks.Maven"
def toolDir = new File(dir)
if ( toolDir.deleteDir()) {
  println "Tool directory ${dir} deleted successfully\n"
}

// Configure Maven
def installations = []
def descriptor = Jenkins.getInstance().getDescriptor("hudson.tasks.Maven")

for (tool in tools) {
  def installer = new MavenInstaller(tool.value, true)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new MavenInstallation(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the Maven configuration
descriptor.setInstallations(installations.toArray(new MavenInstallation[0]))
descriptor.save()

// Print configured Maven versions
descriptor.getInstallations().each { MavenInstallation tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"


