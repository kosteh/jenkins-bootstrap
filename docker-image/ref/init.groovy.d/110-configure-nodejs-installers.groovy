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
