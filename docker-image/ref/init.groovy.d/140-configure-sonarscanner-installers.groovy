/*

Purpose:
Configures a number of Sonar Scanner auto-installers.

Usage:
Define one or more versions in the "versions" map.

*/

import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.tools.*

println "------ Configure Sonar Scanners installers -------------------------"
println ""

def desc = Jenkins.instance.getDescriptor("hudson.plugins.sonar.SonarRunnerInstallation")

// Define and configure a number of Sonar Scanner installations
def versions = [
  "SonarQube Runner Latest"    : "2.8",
  "SonarQube Runner 2 Latest"  : "2.8",
  "SonarQube Runner 2.8"       : "2.8"
]

def installations = [];

for (v in versions) {
  println "${v.key}: ${v.value}"
  def installer = new SonarRunnerInstaller(v.value)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new SonarRunnerInstallation(v.key, "", [installerProps])
  installations.push(installation)
}

// Persist the Sonar Scanner configuration
desc.setInstallations(installations.toArray(new SonarRunnerInstallation[0]))
desc.save() 

println ""
println "------ END ---------------------------------------------------------"
