import jenkins.model.*

def inst     = Jenkins.getInstance()
def desc     = inst.getDescriptor("hudson.tasks.Mailer")
def String password = null

def getPassword = { username ->
    def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
        com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
        jenkins.model.Jenkins.instance
    )

    def c = creds.findResult { it.username == username ? it : null }

    if ( c ) {
        println "Found credential ${c.id} for username ${c.username} ${c.password} "
        password = c.password
    } else {
        println "Credential NOT found for ${username}"
    }
}

getPassword ("outbound-mail-configuration")

// Password contains:
// <host>:<port>:<ssl?>:<account>:<password>:<replytoaddress>:<charset>
// E.g: mail.hdml.nl:25:true:hans@hdml.nl:Diklba00@#$:noreply@hdml.nl:UTF-8
def String[] omc = password.split(":")
println "Mail host    = ${omc[0]}"
println "Mail port    = ${omc[1]}"
println "SSL ?        = ${omc[2]}"
println "Mail account = ${omc[3]}"
println "Mail passwd  = <secret>"
println "Mail replyTo = ${omc[5]}"
println "Mail charset = ${omc[6]}"

// Define mail descriptor
desc.setSmtpHost(omc[0])
desc.setSmtpPort(omc[1])
desc.setUseSsl(Boolean.valueOf(omc[2]))
desc.setSmtpAuth(omc[3], omc[4])
desc.setReplyToAddress(omc[5])
desc.setCharset(omc[6])

// Persist it
desc.save()
