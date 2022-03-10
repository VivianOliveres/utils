Lightning Lab – 1
===============

lab21
---------------

get pod, fix the port of the readiness probe and add a liveness probe based on custom command.

lab22
---------------

Create a CronJob (every minute) with different parameters. 

lab23
---------------

In a dedicated namespace, create a config map.
Then create a pod that use this CM as env variable and that logs into a temporary folder (ie emptyDir).
Special attention to the container command that start sh command.

lab24
---------------

Create a simple deployment, then update container image and then rollout to previous revision.

lab25
---------------

Create a deployment with:
* CPU limit request
* Volume for logs
* Volume with ConfigMap imported as a file