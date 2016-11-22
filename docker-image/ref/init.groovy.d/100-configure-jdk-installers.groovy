/*

Purpose:
Configures a number of Docker tool auto-installers.

Used API:
http://javadoc.jenkins.io/plugin/docker-commons/index.html?org/jenkinsci/plugins/docker/commons/tools

Usage:
Define one or more versions in the "versions" map.

*/

import jenkins.model.*
import hudson.model.*
import hudson.tools.*
  
println "------ Configure JDK installers ------------------------------------"
println ""

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("hudson.model.JDK")

// Define and configure a number of JDK installations
def versions = [
  "JDK Latest" : "jdk-8u102-oth-JPR",
  "JDK 8u102"  : "jdk-8u102-oth-JPR"
]

def installations = [];

for (v in versions) {
  println "${v.key}: ${v.value}"
  def installer = new JDKInstaller(v.value, true)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new JDK(v.key, "", [installerProps])
  installations.push(installation)
}

// Persist the JDK configuration
desc.setInstallations(installations.toArray(new JDK[0]))
desc.save() 

println ""
println "------ END ---------------------------------------------------------"
