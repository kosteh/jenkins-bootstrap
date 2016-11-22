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
  "NodeJS Latest": "NodeJS 6.7.0",
  "NodeJS 6.7.0" : "NodeJS 6.7.0",
  "NodeJS 6"     : "NodeJS 6.2.0",
  "NodeJS 4"     : "NodeJS 4.1.2",
  "NodeJS 4 (Windows)" : "NodeJS 4.4.4"
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
  def installer = new NodeJSInstaller(v.value, "bower bower-art", 100)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new NodeJSInstallation(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the NodeJS configuration
descriptor.setInstallations(installations.toArray(new JDK[0]))
descriptor.save()

// Print installed NodeJS versions
descriptor.getInstallations().each { NodeJSInstallation tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"





/*

Purpose:
Configures a number of NodeJS auto-installers.

Usage:
Define one or more versions in the "versions" map.

*/

import jenkins.model.*
import hudson.model.*
import jenkins.plugins.nodejs.tools.*
import hudson.tools.*

println "------ Configure NodeJS installers ---------------------------------"
println ""

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("jenkins.plugins.nodejs.tools.NodeJSInstallation")

// Define and configure a number of NodeJS installations
def versions = [
  "NodeJS Latest": "NodeJS 6.7.0",
  "NodeJS 6.7.0" : "NodeJS 6.7.0",
  "NodeJS 6"     : "NodeJS 6.2.0",
  "NodeJS 4"     : "NodeJS 4.1.2",
  "NodeJS 4 (Windows)" : "NodeJS 4.4.4"
]

def installations = [];

for (v in versions) {
  println "${v.key}: ${v.value}"
  def installer = new NodeJSInstaller(v.value, "bower bower-art", 100)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new NodeJSInstallation(v.key, "", [installerProps])
  installations.push(installation)
}

// Persist the NodeJS configuration
desc.setInstallations(installations.toArray(new NodeJSInstallation[0]))
desc.save()  

println ""
println "------ END ---------------------------------------------------------"
