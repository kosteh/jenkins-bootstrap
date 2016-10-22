/*

Purpose:
This script configures the Jenkins LDAP configuration, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure Global Security".

Usage:
The configuration parameters are provided through environment variables named JENKINS_LDAP_XXX mainly. 
The password is provided by a Jenkins username/password credential named ???????.

*/

import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.*

println "------ Jenkins LDAP configuration ----------------------------------"
println ""

// Check the presence of the JENKINS-LDAP-USER credential set
def ldapUser    = "JENKINS-LDAP-USER"
def credentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                       com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                       jenkins.model.Jenkins.instance )
def ldapCreds   = credentials.findResult { it.id == ldapUser ? it : null }
if ( ldapCreds == null )
{
    println "ERROR: The JENKINS-LDAP-USER credential set not found."
}
else
{
    def ev = System.getenv()

    def server                     = ev["JENKINS_LDAP_SERVER"] ? ev["JENKINS_LDAP_SERVER"] : 'kadaster.local:3268'
    def rootDN                     = ev["JENKINS_LDAP_ROOTDN"] ? ev["JENKINS_LDAP_ROOTDN"] : 'dc=kadaster,dc=local'
    def userSearchBase             = ev["JENKINS_LDAP_USER_SEARCHBASE"] ? ev["JENKINS_LDAP_USER_SEARCHBASE"] : ''
    def userSearch                 = ev["JENKINS_LDAP_USER_SEARCH"] ? ev["JENKINS_LDAP_USER_SEARCH"] : ''
    def groupSearchBase            = ev["JENKINS_LDAP_GROUP_SEARCHBASE"] ? ev["JENKINS_LDAP_GROUP_SEARCHBASE"] : ''
    def managerDN                  = ev["JENKINS_LDAP_MANAGERDN"] ? ev["JENKINS_LDAP_MANAGERDN"] : ldapCreds.username
    def managerPassword            = ev["JENKINS_LDAP_PASSWORD"] ? ev["JENKINS_LDAP_PASSWORD"] : ldapCreds.password.toString()
    def inhibitInferRootDN         = ev["JENKINS_LDAP_INHIBIT_INFER_ROOTDN"] ? Boolean.valueOf(ev["JENKINS_LDAP_INHIBIT_INFER_ROOTDN"]) : false
    def disableMailAddressResolver = ev["JENKINS_LDAP_DISABLE_MAIL_ADDRESS_RESOLVER"] ? Boolean.valueOf(ev["JENKINS_LDAP_DISABLE_MAIL_ADDRESS_RESOLVER"]) : false

    println "Server : ${server}"
    println "RootDN : ${rootDN}"
    println "UserSearchBase : ${userSearchBase}"
    println "UserSearch : ${userSearch}"
    println "GroupSearchBase : ${groupSearchBase}"
    println "ManagerDN : ${managerDN}"
    println "ManagerPassword : <secret>"
    println "InhibitInferRootDN : ${inhibitInferRootDN.toString()}"
    println "DisableMailAddressResolver : ${disableMailAddressResolver.toString()}"

    SecurityRealm ldapRealm = new LDAPSecurityRealm(server, rootDN, userSearchBase, userSearch, 
                                                groupSearchBase, managerDN, managerPassword, inhibitInferRootDN)
    if (ldapRealm)
    {
        Jenkins.instance.setSecurityRealm(ldapRealm)
        Jenkins.instance.save()
    
        println "INFO : LDAP configured successfully"
    } 
    else
    {
        println "ERROR: LDAP configuration failed"
    }
}

println ""
println "------ END ---------------------------------------------------------"
