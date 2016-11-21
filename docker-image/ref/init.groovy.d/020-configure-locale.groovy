/*

Purpose:
This scripts configures the Jenkins Locale settings, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System", below the section "Locale".

Usage:
The configuration parameters are provided through environment variables named JENKINS_LOCALE_XXX. 
If not set, defaults are used.

*/

println "----------- SET LOCALE ---------------------------------------------------------"

import jenkins.model.Jenkins;

def jenkins = jenkins.model.Jenkins.getInstance()    
def pluginWrapper = jenkins.getPluginManager().getPlugin('locale')
def plugin = pluginWrapper.getPlugin()

// Set locale configuration
plugin.systemLocale = System.getProperty("jenkins.config.locale.default-language")
plugin.ignoreAcceptLanguage = Boolean.valueOf(System.getProperty("jenkins.config.locale.ignore-browser-preference")

// Persist the settings
jenkins.save()
                                              
println "------------ END ---------------------------------------------------------------"
