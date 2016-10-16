import jenkins.model.*

def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("hudson.tasks.Mailer")

// Define mail descriptor
desc.setSmtpAuth(System.getProperty("smtp.user"), System.getProperty("smtp.password"))
desc.setReplyToAddress(System.getProperty("smtp.replyToAddress"))
desc.setSmtpHost(System.getProperty("smtp.host"))
desc.setUseSsl(Boolean.valueOf(System.getProperty("smtp.useSsl")))
desc.setSmtpPort(System.getProperty("smtp.port"))
desc.setCharset(System.getProperty("smtp.charSet"))

// Persist it
desc.save()

// Unset sensitive property
System.clearProperty("smtp.password")
