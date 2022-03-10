Lightning Lab – 1
===============

lab11
---------------

Simple use of pv+pvc+pod

lab12
---------------

Fix connectivity issue between 2 pods accross a service.
Goal is to add an ingress NetworkPolicy to allow traffic.

lab13
---------------

In a dedicated namespace, create a config map.
Then create a pod that use this CM as env variable and that logs into a temporary folder (ie emptyDir).
Special attention to the container command that start sh command.

lab14
---------------

Create a simple deployment, then update container image and then rollout to previous revision.

lab15
---------------

Create a deployment with:
* CPU limit request
* Volume for logs
* Volume with ConfigMap imported as a file