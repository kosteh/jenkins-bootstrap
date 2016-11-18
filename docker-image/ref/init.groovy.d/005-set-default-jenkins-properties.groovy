// Sets the default Jenkins properties

Properties props = new Properties()
File propsFile = new File('/tmp/jenkins.properties')
props.load(propsFile.newDataInputStream())

for (prop in props) println "$prop.key = $prop.value"
