// Sets the default Jenkins properties

println "----------- SET DEFAULT PROPERTIES -------------------------------"

def jenkinsHome = System.getenv("JENKINS_HOME")

Properties properties = new Properties()
File propertiesFile = new File("${jenkinsHome}/jenkins.properties")
properties.load(propertiesFile.newDataInputStream())

for (property in properties) println "${property.key} = ${property.value}"
                               
println "----------- END --------------------------------------------------"
