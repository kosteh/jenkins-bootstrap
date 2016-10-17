import jenkins.model.*

def inst     = Jenkins.getInstance()
def desc     = inst.getDescriptor("hudson.tasks.Mailer")
def password = null

def getPassword = { username ->
    def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
        com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
        jenkins.model.Jenkins.instance
    )

    def c = creds.findResult { it.username == username ? it : null }

    if ( c ) {
        println "Found credential ${c.id} for username ${c.username}"

        def systemCredentialsProvider = jenkins.model.Jenkins.instance.getExtensionList(
            'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
            ).first()

        password = systemCredentialsProvider.credentials.first().password
        println "Credential found for ${username}"
    } else {
        println "Credential NOT found for ${username}"
    }
}

getPassword(System.getProperty("smtp.user"))

// Define mail descriptor
desc.setSmtpAuth(System.getProperty("smtp.user"), password)
desc.setReplyToAddress(System.getProperty("smtp.replyToAddress"))
desc.setSmtpHost(System.getProperty("smtp.host"))
desc.setUseSsl(Boolean.valueOf(System.getProperty("smtp.useSsl")))
desc.setSmtpPort(System.getProperty("smtp.port"))
desc.setCharset(System.getProperty("smtp.charSet"))

// Persist it
desc.save()

// Unset sensitive property
// System.clearProperty("smtp.password")
