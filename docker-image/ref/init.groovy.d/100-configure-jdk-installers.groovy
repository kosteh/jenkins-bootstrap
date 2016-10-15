import jenkins.model.*
import hudson.model.*
import hudson.tools.*

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("hudson.model.JDK")

def versions = [
  "JDK LATEST" : "jdk-8u102-oth-JPR",
  "JDK 8"      : "jdk-8u102-oth-JPR"
]

def installations = [];

for (v in versions) {
  def installer = new JDKInstaller(v.value, true)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new JDK(v.key, "", [installerProps])
  installations.push(installation)
}

desc.setInstallations(installations.toArray(new JDK[0]))
desc.save() 
