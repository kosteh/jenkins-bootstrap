/*

Purpose:
This script configures the Jenkins main properties, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System", first "unnamed" section.

Usage:
The configuration parameters are provided through environment variables named JENKINS_MAIN_XXX. 
Otherwise, sensible defaults are used.

*/

import jenkins.model.*

def jenkins = Jenkins.getInstance()

println "------ Setting Jenkins main properties -----------------------------"
println ""

// Set workspace directory (to high performance storage) and the builds directory
//jenkins.rawWorkspaceDir = '/tmp/workspaces/' + '${ITEM_FULL_NAME}'
jenkins.rawBuildsDir    = '/tmp/builds/' + '${ITEM_FULL_NAME}'

println "${jenkins.rawWorkspaceDir}"
println "${jenkins.rawBuildsDir}"

// Set 
jenkins.setSystemMessage('<h1>SYSTEM MESSAGE</h1>')

// Set humber of executors
jenkins.setNumExecutors(4)

// Set master execution labels
jenkins.setLabelString('master')

// Persist the Jenkins configuration
jenkins.save()


println ""
println "------ END ---------------------------------------------------------"
