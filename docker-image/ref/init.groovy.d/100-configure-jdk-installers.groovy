/*

Purpose:
Configures a number of Docker tool auto-installers.

Used API:
http://javadoc.jenkins.io/plugin/docker-commons/index.html?org/jenkinsci/plugins/docker/commons/tools

Usage:
Define one or more versions in the "versions" map.

Todo:
Find out how to delete the slaves' "tools" directory

*/

import org.jenkinsci.plugins.docker.commons.tools.DockerTool
import org.jenkinsci.plugins.docker.commons.tools.DockerToolInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Dockertools -------------------------------------"
println ""

def descriptor = Jenkins.getInstance().getDescriptor("org.jenkinsci.plugins.docker.commons.tools.DockerTool")

// Define and configure a number of DockerTool installations
def dockerTools = [
  "Docker Latest" : "1.12.3",
  "Docker 1.12"   : "1.12.3",
  "Docker 1.12.3" : "1.12.3",
  "Docker 1.11"   : "1.11.2",
  "Docker 1.11.2" : "1.11.2"
]

// List of Dockertool installations
def installations = []

// Remove existing Dockertool installations (/tools/org.jenkinsci.plugins.docker.commons.tools.DockerTool)
def dir = "${System.getenv("JENKINS_HOME")}/tools/org.jenkinsci.plugins.docker.commons.tools.DockerTool"
def dockertoolDir = new File(dir)
if ( dockertoolDir.deleteDir()) {
  println "Dockertool directory ${dir} deleted successfully\n"
}

// Configure the Dockertools
for (tool in dockerTools) {
  def installer = new DockerToolInstaller(tool.key, tool.value)
  def installSourceProp = new InstallSourceProperty([installer])
  def installation = new DockerTool(tool.key, null, [installSourceProp])
  installations.push(installation)
}

// Persist the Dockertool configuration
descriptor.setInstallations(installations.toArray(new DockerTool[0]))
descriptor.save()

// Print installed Docker tool versions
descriptor.getInstallations().each{DockerTool tool -> println "${tool.getName()} : ${dockerTools[tool.getName()]}" }

println ""
println "------ END ---------------------------------------------------------"
