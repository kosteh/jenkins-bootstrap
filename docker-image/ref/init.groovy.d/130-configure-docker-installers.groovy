import org.jenkinsci.plugins.docker.commons.tools.DockerTool
import org.jenkinsci.plugins.docker.commons.tools.DockerToolInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Docker client ---------------------------------"
println ""

def descriptor = Jenkins.getInstance().getDescriptor("org.jenkinsci.plugins.docker.commons.tools.DockerTool")

// Define and configure a number of DockerTool installations
def dockertools = [
  "Docker Latest" : "1.12.3",
  "Docker 1.12"   : "1.12.3",
  "Docker 1.12.3" : "1.12.3",
  "Docker 1.11"   : "1.11.2",
  "Docker 1.11.2" : "1.11.2"
]

// List of Dockertool installations
def installations = []

// Remove existing Dockertool installations
if (descriptor.getInstallations()) {
    println 'Docker Tools already configured'
}

// Configure Dockertool configurations
for (tool in dockertools) {
        println "${tool.key} : ${tool.value}"
        def installer = new DockerToolInstaller(tool.key, tool.value)
        def installSourceProp = new InstallSourceProperty([installer])
        def installation = new DockerTool(v.key, null, [installSourceProp])
        installations.push(installation)
    }
    descriptor.setInstallations(installations.toArray(new DockerTool[0]))
    descriptor.save()
//}
descriptor.getInstallations().each { println it }

println ""
println "------ END ---------------------------------------------------------"
