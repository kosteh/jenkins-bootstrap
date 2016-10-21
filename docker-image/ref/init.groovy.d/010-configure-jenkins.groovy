/*

Purpose:
This script configures the Jenkins main properties, that can be specified manually through the
configuration page "Jenkins->Manage Jenkins->Configure System", first "unnamed" section.

Usage:
The configuration parameters are provided through environment variables named JENKINS_XXX. 
Otherwise, sensible defaults are used.

*/

import jenkins.model.*

def jenkins = Jenkins.getInstance()

println "------ Setting Jenkins main properties -----------------------------"
println ""

//jenkins.rawWorkspaceDir = '/tmp/workspaces/' + '${ITEM_FULL_NAME}'
jenkins.rawBuildsDir    = '/tmp/builds/' + '${ITEM_FULL_NAME}'

println "${jenkins.rawWorkspaceDir}"
println "${jenkins.rawBuildsDir}"

// 

println ""
println "------ END ---------------------------------------------------------"
