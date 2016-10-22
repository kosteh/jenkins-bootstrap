/*

Purpose:
This scripts configures the Jenkins e-mail configuration, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System".

Usage:
The configuration parameters are provided through environment variables named JENKINS_MAIL_XXX. 
The password is provided by a Jenkins user-password credential named JENKINS-MAIL-USER.

*/

import jenkins.model.*
import hudson.util.*

println "------ Jenkins outbound e-mail configuration -----------------------"
println ""

// Check the presence of the JENKINS_MAIL-USER credential set
def credentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                       com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                       jenkins.model.Jenkins.instance )
def mailCreds   = credentials.findResult { it.id == "JENKINS-MAIL-USER" ? it : null }
if ( mailCreds == null )
{
    println "ERROR: The JENKINS-MAIL-USER credential set is not present."
}
else
{
    def Map ev = System.getenv()
    if ( ev["JENKINS_MAIL_HOST"]             == null || 
         ev["JENKINS_MAIL_PORT"]             == null || 
         ev["JENKINS_MAIL_SSL"]              == null || 
         ev["JENKINS_MAIL_REPLY_TO_ADDRESS"] == null || 
         ev["JENKINS_MAIL_CHARSET"]          == null )
    {
        println "ERROR: Check the presence of all JENKINS_MAIL_XXX environment variables."
        println "       One of more variables are missing."
    }
    else
    {
        def mailDesc = jenkins.model.Jenkins.instance.getDescriptor("hudson.tasks.Mailer")
        if ( mailDesc )
        {
            println "Jenkins Mail host     = ${ev["JENKINS_MAIL_HOST"]}"
            println "Jenkins Mail port     = ${ev["JENKINS_MAIL_PORT"]}"
            println "Jenkins Mail SSL ?    = ${ev["JENKINS_MAIL_SSL"]}"
            println "Jenkins Mail user     = ${mailCreds.username}"
            println "Jenkins Mail password = <secret>"
            println "Jenkins Mail reply to = ${ev["JENKINS_MAIL_REPLY_TO_ADDRESS"]}"
            println "Jenkins Mail charset  = ${ev["JENKINS_MAIL_CHARSET"]}"
          
            mailDesc.setSmtpHost(ev["JENKINS_MAIL_HOST"])
            mailDesc.setSmtpPort(ev["JENKINS_MAIL_PORT"])
            mailDesc.setUseSsl(Boolean.valueOf(ev["JENKINS_MAIL_SSL"]))
            mailDesc.setSmtpAuth(mailCreds.username , Secret.toString(mailCreds.password))
            mailDesc.setReplyToAddress(ev["JENKINS_REPLY_TO_ADDRESS"])
            mailDesc.setCharset(ev["JENKINS_MAIL_CHARSET"])
            mailDesc.save()
          
            println ""
            println "INFO: Jenkins Mail configuration set successfully."
         }
        else
        {
            println "ERROR: Jenkins Mail configuration failed."
        }
    }
}

println ""
println "------ END ---------------------------------------------------------"
