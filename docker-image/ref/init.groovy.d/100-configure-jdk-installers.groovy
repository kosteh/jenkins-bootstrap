/*

Purpose:
Configures a number of JDK auto-installers in a Jenkins instance.

*/

import hudson.model.JDK
import hudson.tools.JDKInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure JDKs ------------------------------------------------"
println ""

// Define a number of JDK installations
def tools = [
  "JDK Latest" : "jdk-8u112-oth-JPR",
  "JDK 8"      : "jdk-8u112-oth-JPR",
  "JDK 8u112"  : "jdk-8u112-oth-JPR",
  "JDK 7"      : "jdk-7u80-oth-JPR",
  "JDK 7u80"   : "jdk-7u80-oth-JPR",
  "JDK 6"      : "jdk-6u45-oth-JPR",
  "JDK 6u45"   : "jdk-6u45-oth-JPR"
]

// Remove existing JDK installations from the "tools" directory.
// TODO: find out how to remove the directory on a Jenkins agent.
def dir = "${System.getenv("JENKINS_HOME")}/tools/hudson.model.JDK"
def toolDir = new File(dir)
if ( toolDir.deleteDir()) {
  println "Tool directory ${dir} deleted successfully\n"
}

// Configure the JDKs
def installations = []
def descriptor = Jenkins.getInstance().getDescriptor("hudson.model.JDK")

for (tool in tools) {
  def installer = new JDKInstaller(tool.value, true)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new JDK(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the JDK configuration
descriptor.setInstallations(installations.toArray(new JDK[0]))
descriptor.save()

// Print installed JDK versions
descriptor.getInstallations().each { JDK tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"
