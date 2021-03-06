/*

Purpose:
This scripts configures the Jenkins Timezone.

Usage:
The configuration parameters are provided through environment variables named JENKINS_TIMEZONE. 
If not set, a default timezone is used.

The list of timezones is here: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones.
*/

println "------ Jenkins timezone configuration ------------------------------"
println ""

def ev = System.getenv()

System.setProperty('org.apache.commons.jelly.tags.fmt.timeZone', ev["JENKINS_TIMEZONE"] ? ev["JENKINS_TIMEZONE"] : 'Europe/Amsterdam')
println "Jenkins property 'org.apache.commons.jelly.tags.fmt.timeZone' set to: ${System.getProperty('org.apache.commons.jelly.tags.fmt.timeZone')}"

System.setProperty('user.timezone', ev["JENKINS_TIMEZONE"] ? ev["JENKINS_TIMEZONE"] : 'Europe/Amsterdam')
println "Jenkins property 'user.timezone' set to: ${System.getProperty('user.timezone')}"

println ""
println "------ END ---------------------------------------------------------"
