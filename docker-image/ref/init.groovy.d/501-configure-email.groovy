/*

Purpose:

This scripts configures the Jenkins e-mail configuration, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System".

The configuration parameters are provided through environment variables mainly. The password is provided 
by a Jenkins user-password credential.

*/

import jenkins.model.*

println "------ Set Jenkins outbound e-mail configuration -------------------"

// Check the presence of the MAIL-USER credential set
def mailUser    = "MAIL-USER"
def credentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                       com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                       jenkins.model.Jenkins.instance )
def mailCreds   = credentials.findResult { it.username == mailUser ? it : null }
if ( mailCreds == null )
{
    println ""
    println "ERROR: Check the presence of the MAIL-USER credential set."
}

// Check the presence of all JENKINS_MAIL environment variables
def Map ev = System.getenv()
if ( ev["JENKINS_MAIL_HOST"]        == null || ev["JENKINS_MAIL_PORT"]     == null || 
     ev["JENKINS_MAIL_SSL"]         == null || ev["JENKINS_MAIL_USER"]     == null ||
     ev["JENKINS_REPLY_TO_ADDRESS"] == null || ev["JENKINS_MAIL_CHARSET"]  == null )
{
    println ""
    println "ERROR: Check the presence of all JENKINS_MAIL_XXX environment variables."
    println "       One of more variables are missing."

    ev = null
}

if ( mailCreds == null || ev == null ) 
{
    println "ERROR: Jenkins Outbound Mail configuration NOT set."
    return
}
else
{
    println "Jenkins Mail host    = ${ev["JENKINS_MAIL_HOST"]}"
    println "Jenkins Mail port    = ${ev["JENKINS_MAIL_PORT"]}"
    println "Jenkins Mail SSL ?   = ${ev["JENKINS_MAIL_SSL"]}"
    println "Jenkins Mail user    = ${ev["JENKINS_MAIL_USER"]}"
    println "Jenkins Mail passwd  = <secret>"
    println "Jenkins Mail replyTo = ${ev["JENKINS_REPLY_TO_ADDRESS"]}"
    println "Jenkins Mail charset = ${ev["JENKINS_MAIL_CHARSET"]}"

    def mailDesc = jenkins.model.Jenkins.instance.getDescriptor("hudson.tasks.Mailer")
    mailDesc.setSmtpHost(ev["JENKINS_MAIL_HOST"])
    mailDesc.setSmtpPort(ev["JENKINS_MAIL_PORT"])
    mailDesc.setUseSsl(Boolean.valueOf(ev["JENKINS_MAIL_SSL"]))
    mailDesc.setSmtpAuth(ev["JENKINS_MAIL_USER"], mailCreds.password)
    mailDesc.setReplyToAddress(ev["JENKINS_REPLY_TO_ADDRESS"])
    mailDesc.setCharset(ev["JENKINS_MAIL_CHARSET"])

    // Persist it
    mailDesc.save()
}

println ""
println "------ END ---------------------------------------------------------"
