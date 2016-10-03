#
# This Dockerfile is used to create a bootstrap Jenkins image.
#
# This image has no specific Kadaster infrastructure dependencies. Infrastructure dependencies are 
# using an additional Dockerfile that is maintained within the Kadaster premisis.
#
# This image is built with the automated build facilities of http://dockerhub.comn.
#

# Use the (latest if possible) LTS version of Jenkins
FROM jenkins:2.7.4

# We maintain it
MAINTAINER SOO Support "soo@kadaster.nl"

# Install all wanted plugins 
RUN /usr/local/bin/install-plugins.sh locale \
                                      greenballs \
                                      workflow-aggregator \
                                      github-organization-folder
                                      
# Create the environment
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
