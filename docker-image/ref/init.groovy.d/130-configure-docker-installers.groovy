import org.jenkinsci.plugins.docker.commons.tools.DockerTool
import org.jenkinsci.plugins.docker.commons.tools.DockerToolInstaller
import hudson.tools.InstallSourceProperty
import jenkins.model.Jenkins

println "------ Configure Docker client ---------------------------------"
println ""

def descriptor = new DockerTool.DescriptorImpl();

if (descriptor.getInstallations()) {
    println 'skip Docker Tool installations'
} else {
    //Jenkins.instance.updateCenter.getById('default').updateDirectlyNow(true)
    def dockerInstaller = new DockerToolInstaller('Docker1.12.3', "1.12.3")
    def docker = new DockerTool("Docker-1.12", null, [new InstallSourceProperty([dockerInstaller])])
    descriptor.setInstallations(docker)
}

println ""
println "------ END ---------------------------------------------------------"
