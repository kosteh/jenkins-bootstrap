import jenkins.model.*
import hudson.model.*
import jenkins.plugins.nodejs.tools.*
import hudson.tools.*

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("jenkins.plugins.nodejs.tools.NodeJSInstallation")

def versions = [
  "NodeJS Latest": "NodeJS 6.7.0",
  "NodeJS 6.7.0": "NodeJS 6.7.0",
]

def installations = [];

for (v in versions) {
  def installer = new NodeJSInstaller(v.value, "bower bower-art", 100)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new NodeJSInstallation(v.key, "", [installerProps])
  installations.push(installation)
}

desc.setInstallations(installations.toArray(new NodeJSInstallation[0]))

desc.save()  
