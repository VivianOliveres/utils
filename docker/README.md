# Docker
Useful informations about Docker

# Use cases
## Delete
Delete all containers
```bash
docker rm $(docker ps -a -q)
```
Delete all images
```bash
docker rmi $(docker images -q)
```
## Running
Basic
```bash
docker run -d --name CONTAINER_NAME -p 80:80 IMAGE_NAME
```
With outside port 80 redirected to inside port 27017
```bash
docker run -d --name CONTAINER_NAME -p 80:27017 IMAGE_NAME
```

## Volume
Create dummy volume and copy files in it
```bash
docker volume create --name VOLUME_NAME
docker container create --name dummy --volume VOLUME_NAME:/DOCKER_PATH alpine
docker cp LOCAL_PATH/FILE_NAME dummy:/DOCKER_PATH/FILE_NAME
docker rm dummy
```

Check files have been copied
```bash
docker run --rm --volume XXX:/DOCKER_PATH alpine cat /DOCKER_PATH/FILE_NAME
```

Running with volume
```bash
docker run --detach \
 --name YYY \
 -p 80:5003 \
 -v VOLUME_NAME:/ZZZ \
 DOCKER_IMAGE \
 -Dconfig.file=/ZZZ/FILE_NAME
```
