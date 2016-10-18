/*

Purpose:
Configures a number of Maven auto-installers.

Usage:
Define one or more versions in the "versions" map.

*/

import jenkins.model.*
import hudson.model.*
import hudson.tools.*
  
println "------ Configure Maven installers ------------------------------------"
println ""

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("hudson.tasks.Maven")

// Define and configure a number of MAven installations
def versions = [
  "Maven Latest"   : "3.3.9",
  "Maven 3 Latest" : "3.3.9",
  "Maven 3.3.9"    : "3.3.9",
  "Maven 2 Latest" : "2.2.1",
  "Maven 2.2.1"    : "2.2.1"
]

def installations = [];

for (v in versions) {
  println "${v.key}: ${v.value}"
  def installer = new hudson.tasks.Maven.MavenInstaller(v.value)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new hudson.tasks.Maven.MavenInstallation(v.key, null, [installerProps])
  installations.push(installation)
}

// Persist the Maven tool configuration
desc.setInstallations(installations)
desc.save() 

println ""
println "------ END ---------------------------------------------------------"
