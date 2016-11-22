import org.jenkinsci.plugins.docker.commons.tools.DockerTool
import org.jenkinsci.plugins.docker.commons.tools.DockerToolInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Docker client ---------------------------------"
println ""

def descriptor = Jenkins.getInstance().getDescriptor("org.jenkinsci.plugins.docker.commons.tools.DockerTool")

// Define and configure a number of JDK installations
def versions = [
  "Docker Latest" : "1.12.3",
  "Docker 1.12"   : "1.12.3",
  "Docker 1.12.3" : "1.12.3",
  "Docker 1.11"   : "1.11.2",
  "Docker 1.12.3" : "1.11.2"
]

if (descriptor.getInstallations()) {
    println 'Docker Tools already configured'
} else {
    for (v in versions) {
        println "${v.key}: ${v.value}"
        def dockerInstaller = new DockerToolInstaller(v.key, v.value)
        def installerProps = new InstallSourceProperty([dockerInstaller])
        def installation = DockerTool(v.key, null, [installerProps])
        descriptor.setInstallations(docker)
    }
}   
print descriptor.getInstallations()

println ""
println "------ END ---------------------------------------------------------"
