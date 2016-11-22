/*

Purpose:
Configures a number of Docker tool auto-installers.

*/

import org.jenkinsci.plugins.docker.commons.tools.DockerTool
import org.jenkinsci.plugins.docker.commons.tools.DockerToolInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Dockertools -------------------------------------"
println ""

// Define and configure a number of DockerTool installations
def tools = [
  "Docker Latest" : "1.12.3",
  "Docker 1.12"   : "1.12.3",
  "Docker 1.12.3" : "1.12.3",
  "Docker 1.11"   : "1.11.2",
  "Docker 1.11.2" : "1.11.2"
]

// Remove existing Dockertool installations (/tools/org.jenkinsci.plugins.docker.commons.tools.DockerTool)
// TODO: find out how to remove the directory on a Jenkins agent.
def dir = "${System.getenv("JENKINS_HOME")}/tools/org.jenkinsci.plugins.docker.commons.tools.DockerTool"
def toolDir = new File(dir)
if ( toolDir.deleteDir()) {
  println "Tools directory ${dir} deleted successfully\n"
}

// Configure the Dockertools
def installations = []
def descriptor = Jenkins.getInstance().getDescriptor("org.jenkinsci.plugins.docker.commons.tools.DockerTool")

for (tool in tools) {
  def installer = new DockerToolInstaller(tool.key, tool.value)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new DockerTool(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the Dockertool configuration
descriptor.setInstallations(installations.toArray(new DockerTool[0]))
descriptor.save()

// Print installed Docker tool versions
descriptor.getInstallations().each { DockerTool tool -> println "${tool.getName()} : ${tools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"
