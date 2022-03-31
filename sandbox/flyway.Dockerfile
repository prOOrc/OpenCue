FROM centos

RUN sed -i 's/mirrorlist=http:\/\/mirrorlist.centos.org/#mirrorlist=http:\/\/mirrorlist.centos.org/g' /etc/yum.repos.d/* && \
    sed -i 's/#baseurl=http:\/\/mirror.centos.org\/$contentdir\/$releasever/baseurl=http:\/\/mirror.yandex.ru\/$contentdir\/$releasever-stream/g' /etc/yum.repos.d/* && \
    dnf --enablerepo=extras swap  -y centos-linux-repos centos-stream-repos && dnf distro-sync -y

# Get flyway
RUN ["curl", "-O", "https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/8.5.4/flyway-commandline-8.5.4-linux-x64.tar.gz"]
RUN ["yum", "install", "-y", "tar", "java-1.8.0-openjdk", "postgresql-jdbc", "nc", "postgresql"]
RUN ["tar", "-xzf", "flyway-commandline-8.5.4-linux-x64.tar.gz"]

WORKDIR flyway-8.5.4

# Copy the postgres driver to its required location
RUN ["cp", "/usr/share/java/postgresql-jdbc.jar", "jars/"]
RUN ["mkdir", "/opt/migrations"]
RUN ["mkdir", "/opt/scripts"]
COPY ./cuebot/src/main/resources/conf/ddl/postgres/migrations /opt/migrations
COPY ./cuebot/src/main/resources/conf/ddl/postgres/seed_data.sql /opt/scripts
COPY ./sandbox/migrate.sh /opt/scripts/

CMD ["/bin/bash"]
