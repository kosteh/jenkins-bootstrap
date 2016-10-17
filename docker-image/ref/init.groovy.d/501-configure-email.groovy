import jenkins.model.*

def mailConfigurationUsername = "outbound-mail-configuration"
def credentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                       com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                       jenkins.model.Jenkins.instance )

def mailCredentials = credentials.findResult { it.username == mailConfigurationUsername ? it : null }

if ( ! mailCredentials ) {
    println "Jenkins credential NOT found for ${mailConfigurationUsername}"
} else {
    println "Jenkins credential ${mailCredentials.id} found for username ${mailCredentials.username}"
    
    // Password contains:
    // <host>:<port>:<ssl?>:<account>:<password>:<replytoaddress>:<charset>
    // E.g: mail.hdml.nl:25:true:hans@hdml.nl:<secret>:noreply@hdml.nl:UTF-8
    def String password = mailCredentials.password
    def String[] omc    = password.split(":")
  
    println "Mail host    = ${omc[0]}"
    println "Mail port    = ${omc[1]}"
    println "SSL ?        = ${omc[2]}"
    println "Mail account = ${omc[3]}"
    println "Mail passwd  = <secret>"
    println "Mail replyTo = ${omc[5]}"
    println "Mail charset = ${omc[6]}"

    def mailDesc = jenkins.model.Jenkins.instance.getDescriptor("hudson.tasks.Mailer")
    mailDesc.setSmtpHost(omc[0])
    mailDesc.setSmtpPort(omc[1])
    mailDesc.setUseSsl(Boolean.valueOf(omc[2]))
    mailDesc.setSmtpAuth(omc[3], omc[4])
    mailDesc.setReplyToAddress(omc[5])
    mailDesc.setCharset(omc[6])

    // Persist it
    mailDesc.save()
}
