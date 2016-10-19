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

def sinst = new SonarInstallation( ev["SQ_NAME"] ? ev["SQ_NAME"] : "SonarQube",
                                   ev["SQ_SERVER_URL"] ? ev["SQ_SERVER_URL"] : "http://ota-portal.so.kadaster.nl/sonar",
                                   ev["SQ_VERSION"] ? ev["SQ_VERSION"] : "5.3",
                                   ev["SQ_AUTH_TOKEN"] ? ev["SQ_AUTH_TOKEN"] : "",
                                   ev["SQ_DATABASE_URL"] ? ev["SQ_DATABASE_URL"] : "<NOT_USED_SINCE_5.3>",
                                   ev["SQ_DATABASE_LOGIN"] ? ev["SQ_DATABASE_LOGIN"] : "<NOT_USED_SINCE_5.3>",
                                   ev["SQ_DATABASE_PASSWORD"] ? ev["SQ_DATABASE_PASSWORD"] : "<NOT_USED_SINCE_5.3>",
                                   ev["SQ_MAVEN_PLUGIN_VERSION"] ? ev["SQ_MAVEN_PLUGIN_VERSION"] : "",
                                   ev["SQ_MAVEN_PROPERTIES"] ? ev["SQ_MAVEN_PROPERTIES"] : "",
                                   new TriggersConfig(),
                                   ev["SQ_USER"] ? ev["SQ_USER"] : "<NOT_USED_SINCE_5.3>",
                                   ev["SQ_PASSWORD"] ? ev["SQ_PASSWORD"] : "<NOT_USED_SINCE_5.3>",
                                   ev["SQ_ANALYSIS_PROPERTIES" ? ev["SQ_ANALYSIS_PROPERTIES"] : "" )

// Persist the Sonar installation
desc.setInstallations(sinst)
desc.save()

println ""
println "------ END ---------------------------------------------------------"
