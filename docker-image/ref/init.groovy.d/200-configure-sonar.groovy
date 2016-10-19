/*

Purpose:
Configures the connection with external system SonarQube.

Usage:
Define the "SonarQubeAuthToken" credentials in Jenkins.

*/

import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.*

println "------ Configure Sonar ---------------------------------------------"
println ""

def ev   = System.getenv()

def desc = Jenkins.instance.getDescriptor("hudson.plugins.sonar.SonarPublisher")

def sinst = new SonarInstallation( ev["SONAR_INSTALLATION_NAME"] ? ev["SONAR_INSTALLATION_NAME"] : "",
                                   ev["SONAR_ENABLED"] ? Boolean.toValue(ev["SONAR_ENABLED"]) : true,
                                   ev["SONAR_SERVER_URL"] ? ev["SONAR_SERVER_URL"] : "",
                                   ev["SONAR_DATABASE_URL"] ? ev["SONAR_DATABASE_URL"] : "",
                                   ev["SONAR_DATABASE_DRIVER"] ? ev["SONAR_DATABASE_DRIVER"] : "",
                                   ev["SONAR_DATABASE_USER"] ? ev["SONAR_DATABASE_USER"] : "",
                                   ev["SONAR_DATABASE_PASSWORD"] ? ev["SONAR_DATABASE_PASSWORD"] : "",
                                   ev["SONAR_MAVEN_PLUGIN_VERSION"] ? ev["SONAR_MAVEN_PLUGIN_VERSION"] : "",
                                   ev["SONAR_MAVEN_PROPERTIES"] ? ev["SONAR_MAVEN_PROPERTIES"] : "",
                                   new TriggersConfig(),
                                   ev["SONAR_USER"] ? ev["SONAR_USER"] : "",
                                   ev["SONAR_PASSWORD"] ? ev["SONAR_PASSWORD"] : "" )

// Persist the Sonar installation
desc.setInstallations(sinst)
desc.save()

println ""
println "------ END ---------------------------------------------------------"
