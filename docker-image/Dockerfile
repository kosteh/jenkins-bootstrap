#
# This Dockerfile is used to create a bootstrap Jenkins image.
#
# It contains no specific Kadaster infrastructure dependencies. Infrastructure dependencies are 
# added using an additional Dockerfile on top of this one that is maintained within the Kadaster
# premisis. This way, we keep the Kadaster infrastructure details secret.
#
# This image is built with the automated build facilities of http://dockerhub.comn.
#

# Use the (latest if possible) LTS version of Jenkins
FROM jenkins:2.7.4
MAINTAINER SOO Support "soo@kadaster.nl"

# Install all wanted plugins 
RUN /usr/local/bin/install-plugins.sh locale \
                                      greenballs \
                                      workflow-aggregator \
                                      github-organization-folder
                                      
# Create the environment
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

ARG BUILD_DATE

RUN echo ${BUILD_DATE}
