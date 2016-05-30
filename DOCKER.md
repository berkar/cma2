## Database handling

#### Start new PostgresQL DB
docker run --rm=true --name cma2-postgres -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=cma2 -p 5432:5432 -it postgres

#### Create new schema
Use pgAdminIII

#### Setup new schema
mvn -N flyway:clean flyway:migrate

## Wildfly handling

#### Build new Wildfly
cd docker/wildfly; docker build --tag=wildfly/cma .

#### Start new Wildfly
docker run --rm=true -p 8080:8080 -p 9990:9990 -p 9999:9999 --link cma2-postgres -it wildfly/cma

#### Build and deploy (incl. datasource & driver)
mvn clean install -Pdeploy-localhost

## Docker compose
- Start dev environment: *docker-compose up -d*
- Start prod environment: *docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d*
- Stop environment: *docker-compose down*

#### Notes
- To run docker in git bash: *eval $(docker-machine env default)*
- http://stackoverflow.com/questions/29101043/cant-connect-to-docker-from-docker-compose

There are problems running docker with VPN running. See:
- http://stackoverflow.com/questions/33992729/cannot-get-docker-machine-to-work-with-virtualbox-when-using-cisco-vpn-anyconnec
