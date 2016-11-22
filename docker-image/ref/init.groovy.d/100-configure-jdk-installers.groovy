/*

Purpose:
Configures a number of JDK auto-installers.

Used API:
http://javadoc.jenkins.io/hudson/model/JDK.html

Usage:
Define one or more versions in the "tools" map.

Todo:
Find out how to delete the slaves' "tools" directory

*/

import hudson.model.JDK
import hudson.tools.JDKInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure JDKs ------------------------------------------------"
println ""

// Define and configure a number of DockerTool installations
def tools = [
  "JDK Latest" : "jdk-8u102-oth-JPR",
  "JDK 8"      : "jdk-8u102-oth-JPR",
  "JDK 8u102"  : "jdk-8u102-oth-JPR",
]

// Remove existing Dockertool installations (/tools/org.jenkinsci.plugins.docker.commons.tools.DockerTool)
def dir = "${System.getenv("JENKINS_HOME")}/tools/hudson.model.JDK"
def toolDir = new File(dir)
if ( toolDir.deleteDir()) {
  println "Tool directory ${dir} deleted successfully\n"
}

// Configure the Dockertools
def installations = []
def descriptor = Jenkins.getInstance().getDescriptor("hudson.model.JDK")

for (tool in tools) {
  def installer = new JDKInstaller(tool.value, true)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new JDK(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the Dockertool configuration
descriptor.setInstallations(installations.toArray(new JDK[0]))
descriptor.save()

// Print installed Docker tool versions
descriptor.getInstallations().each { JDK tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"
