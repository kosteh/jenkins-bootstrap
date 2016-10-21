/*

Purpose:
This scripts configures the Jenkins Locale settings, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System", below the section "Locale".

Usage:
The configuration parameters are provided through environment variables named JENKINS_LOCALE_XXX. 
If not set, defaults are used.

*/

import jenkins.model.Jenkins;

def jenkins = jenkins.model.Jenkins.getInstance()    
def pluginWrapper = jenkins.getPluginManager().getPlugin('locale')
def plugin = pluginWrapper.getPlugin()

def ev = System.getenv()
plugin.setSystemLocale(ev["JENKINS_LOCALE"] ? ev["JENKINS_LOCALE"] : 'en_EN'
plugin.ignoreAcceptLanguage = ev["JENKINS_LOCALE_IGNORE_ACCEPT_LANGUAGE"] ? Boolean.valueOf["JENKINS_LOCALE_IGNORE_ACCEPT_LANGUAGE"] : true

// Persist the settings
jenkins.save()
