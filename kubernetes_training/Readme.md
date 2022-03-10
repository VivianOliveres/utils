Kube Training
===============

Training for kubernetes developed by [KubeKloud](kubekloud.com) and available [here](https://kodekloud.com/courses/certified-kubernetes-application-developer-ckad/)

Architecture
---------------

Kubernetes is composed of multiple nodes and a master.  
The master is composed of:
- **API Server** : calls API
- **ETCD** : A distributed key/value store. More information [here](https://etcd.io)
- **API Server** : 
- **Container runtime** : Software that run containers (like [docker](https://docker.com))
- **Controllers** : Components that handle the workflow of resources (ie start/stop pods, ...)
- **Schedulers** : To distribute the load

Few cluster commands:
```bash
kubectl cluster-info

kubectl get nodes

# Get help
kubectl explain job
kubectl explain pod
kubectl explain deploy 
```

Pods
---------------

Official [documentation](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.23/#pod-v1-core)

Yaml example:
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: my-app-pod
	labels:
		app: my-app
spec:
	containers:
		- name: nginx-container
		  image: nginx
```

Command examples:
```bash
# Start pod with a docker image
kubectl run nginx --image nginx

# List pods
kubectl get pods
kubectl get pods -o wide
kubectl get pod <pod-name> -o yaml > pod-definition.yaml

# Create pod from a file
kubectl create -f def.yml

# Edit a running pod
kubectl apply -f def.yml
kubectl edit pod <pod-name>

#Delete pod
kubectl delete pod my-pod

# Create yaml file from run command
kubectl run redis --image=redis --dry-run=client -o yaml > redis.yaml
kubectl run app --image=busybox --restart=Never --dry-run -o yaml > app.yaml

# Log into pod
kubectl exec -it <pod_name> /bin/bash

# Read logs
kubectl logs <pod-name>
```

Example with init container:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
  labels:
    app: myapp
spec:
  containers:
    - name: myapp-container
      image: busybox:1.28
      command: ['sh', '-c', 'echo The app is running! && sleep 3600']
  initContainers:
    - name: init-myservice
      image: busybox:1.28
      command: ['sh', '-c', "until nslookup myservice.$(cat /var/run/secrets/kubernetes.io/serviceaccount/namespace).svc.cluster.local; do echo waiting for myservice; sleep 2; done"]
    - name: init-mydb
      image: busybox:1.28
      command: ['sh', '-c', "until nslookup mydb.$(cat /var/run/secrets/kubernetes.io/serviceaccount/namespace).svc.cluster.local; do echo waiting for mydb; sleep 2; done"]
```


Replicas
---------------

They are used to monitoring and restart failing pods.  
There are two implementations: the old ([ReplicationController](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.23/#replicationcontroller-v1-core)) and the new ([ReplicaSet](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.23/#replicaset-v1-apps))

Yaml example:
```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
	name: my-app-rs
	labels:
		app: myapp
		type: front-end
spec:
	template:
		<pod metadata+spec>
	replicas: 3
	selector:
		matchLabels:
			type: front-end
```

Command examples:
```bash
# Creation
kubectl create -f file.yml

# List
kubectl get rs
kubectl get replicaset
kubectl get replicaset -o yaml > file.yaml

# Delete
kubectl delete replicaset my-app-rs

# Overwrite with a file
kubectl replace -f file.yml

# Scale pods
kubectl scale --replicas=6 -f file.yml # problem if replicas are not the same in the file
kubectl scale --replicas=6 replicaset my-app-rs
```

Deployments
---------------

Manage ReplicaSet for deployments (ie when updating a pod with a new version for instance).  
Official [documentation](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.23/#deployment-v1-apps)  
The yaml file is the same as *ReplicaSet* by updating the *kind* field with *Deployment* value.


Command examples:
```bash
# Creation
kubectl create -f file.yml
kubectl create deployment http-frontend --image=httpd:2.4-alpine

# List
kubectl get deployments

# Scale
kubectl scale deployment --replicas=3 http-frontend
```

Namespaces
---------------

It is used to separate workloads.  
Initially, there are 3 different namespaces (kube-system, default, kube-public) but users can add/update/delete others.  
To call a service from another namespace, use the following naming conventions: `$NAME.$NS.svc.cluster.local` where:
- `$NAME` is the name of the service to call
- `$NS` is the destination namespace
- `svc` is the subdomain for services
- `cluster.local` is the default domain  
Also, pods can directly specify to what namespace they belong by adding it into their `metadata -> namespace` field.

Yaml examples:
```yaml
apiVersion: v1
kind: Namespace
metadata:
	name: dev
```

Resources can be limited into namespace by using [quotas](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.23/#resourcequota-v1-core)
```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
	name: compute-quota
	namespace: dev
spec:
	hard:
		pods: "10"
		requests.cpu: "4"
		requests.memory: 5Gi
		limits.cpu: "10"
		limitis.memory: 10Gi
```

Commands example:
```bash
# List pods of a specific namespace
kubectl get pods --namespace=kube-system

# List pods from all namespaces
kubectl get pods --all-namespaces

# Create a resource into a dedicated namespace
kubectl create -f def.yml --namespace=dev

# One line namespace creation command
kubectl create namespace dev

# Update current default namespace
kubectl config set-context $(kubectl config current-context) --namespace=dev
```

Imperative Commands
---------------

These commands can be used directly instead of creating manually yaml files.
```bash
#Check if a resource is valid (without execution)
kubectl [command] [TYPE] [NAME] --dry-run=client

# Output in yaml
kubectl [command] [TYPE] [NAME] -o yaml
```

Command examples:
```bash
# Generate POD yaml file whitout creating it
kubectl run nginx --image=nginx --dry-run=client -o yaml

# Full generic pod command
kubectl run <pod name> --image=<container image name> --port=<container port> --labels="<value1>,<value2>"

# Generate Deployment with 4 Replicas
kubectl create deployment nginx --image=nginx --replicas=4

# Scale replicas
kubectl scale deployment nginx --replicas=4

# Create a Service named redis-service of type ClusterIP to expose pod redis on port 6379
# with the pod's labels as selectors
kubectl expose pod redis --port=6379 --name redis-service --dry-run=client -o yaml

# Same as previously but without pods labels as selectors but selectors as "app=redis"
kubectl create service clusterip redis --tcp=6379:6379 --dry-run=client -o yaml

# Expose port with target port
kubectl expose pod redis --port=6379 --name redis-service --target-port=6379

# Create pod and expose its port
kubectl run httpd --image=http:alpine --port=80 --expose
```

Printing of outputs can be formatted:
```bash
# Formats: json, name, wide, yaml
kubectl [command] [TYPE] [NAME] -o <output_format>
```

Commands and Arguments in Docker
---------------

Run container with arguments:
```bash
docker run ubuntu [COMMAND]
docker run ubuntu sleep 5
```

Use `CMD` in docker file:
```Dockerfile
FROM Ubuntu
# Alternative: CMD ["sleep", "5"]
CMD sleep 5
```

Use `ENTRYPOINT` in docker file with runtime arguments
```Dockerfile
# Execute with: docker run ubuntu-sleeper 10
FROM Ubuntu
ENTRYPOINT ["sleep"]
```

Use `CMD` and `ENTRYPOINT` to have default arguments overridable:
```Dockerfile
FROM Ubuntu
ENTRYPOINT ["sleep"]
CMD ["5"]
```

```bash
docker run ubuntu-sleeper 10
docker run --entrypoint sleep ubuntu-sleeper 10
```

Commands and Arguments in Kubernetes
---------------

```yaml
apiVersion: v1
kind: Pod
metadata:
	name: ubuntu-sleeper-pod
spec:
	containers:
		- name: ubuntu-sleeper
		  image: ubuntu-sleeper
		  command: ["sleep"]
		  args: ["10"]
```

Editing pods
```bash
# First solution
kubectl edit pod <pod name>
# Edit in VI ...
# Delete running pod
kubectl delete pod webapp
# Create from new edited file
kubectl create -f /tmp/temporary-file.yaml

# Second solution
# Get pod description
kubectl get pod webapp -o yaml > my-new-pod.yaml
# Edit pod
vi my-new-pod.yaml
# Delete pod
kubectl delete pod webapp
# Create pod
kubectl create -f my-new-pod.yaml
```

Deployments can easily be edited:
```bash
kubectl edit deployment my-deployment
```

Environment variables
---------------

```bash
docker run -e APP_COLOR=pink simple-webapp-color
```

Simple definition
```yaml
env:
	- name: APP_COLOR
	  value: pink
```

From a ConfigMap definition
```yaml
env:
	- name: APP_COLOR
	  valueFrom: 
	  	configMapKeyRef:
	  		name: <config-file>
```

From a ConfigMap definition in a volume
```yaml
volumes:
	- name: <volume-name>
		configMap:
			name: <config-file>
```

Only one value from a ConfigMap definition
```yaml
env:
	- name: APP_COLOR
	  valueFrom: 
	  	configMapKeyRef:
	  		name: <config-file>
	  		key: APP_COLOR
```

From secret
```yaml
env:
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: <secret-name>
        key: DB_PASSWORD
```

ConfigMaps
---------------

```bash
# Imperative
kubectl create cm <name> --from-literal=<key>=<value>
kubectl create configmap <name> --from-literal=<key>=<value>
kubectl create configmap app-config --from-literal=APP_COLOR=pink --from-literal=APP_MODE=prod

# Declarative
kubectl create -f file.yaml

# List
kubectl get configmaps

# Describe
kubectl describe configmaps
```

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
	name: app-config
data:
	APP_COLOR: blue
	APP_MODE: prod
```

Pod with environment variables:
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: simple-webapp-color
	labels:
		name: simple-webapp-color
spec:
	containers:
	 - name: simple-webapp-color
	   image: simple-webapp-color
	   ports:
	   	- containerPort: 8080
	   envFrom:
	   	- configMapRef:
	   		name: app-config
```

Secrets
---------------

```bash
# Imperative
kubectl create secret generic <name> --from-literal=<key>=<value>
kubectl create secret generic app-secret --from-literal=DB_HOST=mysql --from-literal=DB_USER=user

# Declarative
kubectl create app-secret -f secret-file.yaml

# Encode/Decode passwords in base 64
echo -n 'mysql' | base64
echo -n 'bXlzcWw=' | base64 --decode

# List
kubectl get secrets

# Details
kubectl describe secrets
kubectl get secret app-secret -o yaml
```

```yaml
apiVersion: v1
kind: Secret
metadata:
	name: app-secret
data:
	DB_HOST: bXlzcWw=
	DB_USER: cm9vdA==
	DB_PASSWORD: cDFzd3Jk
```

```yaml
envFrom:
	- secretRef:
		name: app-secret
```

```yaml
env:
	- name: DB_PASSWORD
		valueFrom:
			secretKeyRef:
				name: app-secret
				key: DB_PASSWORD
```

Inject secrets as a volume (each value will create a dedicated file)
```yaml
volumes:
	- name: app-secret-volume
		secret:
			secretName: app-secret
```

```yaml
apiVersion: v1
kind: Secret
metadata:
	name: app-secret
data:
	DB_HOST: bXlzcWw=
	DB_USER: cm9vdA==
	DB_PASSWORD: cDFzd3Jk
```


Secrets into a pod
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: simple-webapp-color
	labels:
		name: simple-webapp-color
spec:
	containers:
	 - name: simple-webapp-color
	   image: simple-webapp-color
	   ports:
	   	- containerPort: 8080
	   envFrom:
	   - secretRef:
	   		name: app-secret
```
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: simple-webapp-color
	labels:
		name: simple-webapp-color
spec:
	containers:
	 - name: simple-webapp-color
	   image: simple-webapp-color
	   ports:
	   	- containerPort: 8080
       env:
         - name: DB_HOST
           valueFrom:
             secretKeyRef:
               name: app-secret
               key: DB_HOST
```

Security Contexts
---------------

Add security context on pod level
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: web-pod
spec:
	securityContext:
		runAsUser: 1000
	containsers:
		- name: ubuntu
		  image: ubuntu
		  command: ["sleep", "3600"]
```

Add security context on container level
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: web-pod
spec:
	containsers:
		- name: ubuntu
		  image: ubuntu
		  command: ["sleep", "3600"]
		  securityContext:
			capabilities:
				add: ["MAC_ADMIN"]
```

Resource Requirements
---------------

Specify the requested/limits cpu, memory and disk needed by the pod.  
By default (if not specified), the limits are 1 vCpu and 512 Mi.

```yaml
apiVersion: v1
kind: Pod
metadata:
	name: web-pod
spec:
	containsers:
	- name: ubuntu
	  image: ubuntu
	  command: ["sleep", "3600"]
	  resources:
	  	requests:
	  		memory: "1Gi"
	  		cpu: 1
	  	limits:
	  		memory: "2Gi"
	  		cpu: 2
```

If the pod needs more cpu than it limits, it will throttle. But if it needs more memory, the pod will be terminated.

Service Account
---------------

```bash
# Create a SA and a secret with its token
kubectl create serviceaccount <name-sa>

# List
kubectl get sa
kubectl get serviceaccount

# Details
kubectl describe serviceaccount <name-sa>
```

Pod with SA example
```yaml
apiVersion: v1
kind: Pod
metadata:
	name: web-pod
spec:
	serviceAccountName: <name-sa>
	containsers:
	- name: ubuntu
	  image: ubuntu
	  command: ["sleep", "3600"]
```

Taints and Tolerations
---------------

Restrict pods to specific nodes.  
Taint is the value on the node and toleration is on pod. They should match to be scheduled.   
Taint effects:
* `NoSchedule`: try to avoid to place on this node
* `PreferNoSchedule`: 
* `NoExecute`: Will never place on this node and evicts them immediately  
It cannot force pods on specific node. This is done by node affinity.

```bash
# Taint a node with a taint effect
kubectl taint nodes <node-name> <key>=<value>:<taint-effect>
kubectl taint nodes node1 app=blie:NoSchedule
```

```yaml
apiVersion: v1
kind: Pod
metadata:
	name: myapp-pod
spec:
	containers:
	- name: nginx-container
	  image: nginx
	tolerations:
	- key: "app"
      operator: "Equal"
      value: "blue"
      effect: "NoSchedule"
```

Node Selectors Logging
---------------

Schedule pods on nodes that share common labels.  
Limitations come from scenario where pods have to placed on label1 OR label2.

```bash
# Add label on a node
kubectl label nodes <node-name> <key>=<value>
kubectl label nodes node1 size=Large
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
spec:
  containers:
  - name: data-processor
    image: data-processor
  nodeSelector:
	size: Large
```

Node Affinity
---------------

Advanced with multiple operators.  
Types:  

| Node Affinity Types                             | During scheduling       | During execution |
| ----------------------------------------------- | ----------------------- | ---------------- |
| requiredDuringSchedulingIgnoredDuringExecution  | Required                | Ignored          |
| preferredDuringSchedulingIgnoredDuringExecution | Preferred (try at best) | Ignored          |
| requiredDuringSchedulingRequiredDuringExecution | Required                | Required         |

Node operators:
* In
* NotIn
* Exists
* DoesNotExist
* Gt
* Lt

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
spec:
  containers:
  - name: data-processor
    image: data-processor
  affinity:
  	nodeAffinity:
  	  requiredDuringSchedulingIgnoredDuringExecution:
	    nodeSelectorTerms:
	    - matchExpressions:
	      - key: size
	        operator: In
	        values:
	        - Large
	        - Medium
```

Multi-Container PODs
---------------

Shared network and storage.  
Design Patterns
* Sidecar (ex: logging utilities, sync services, watchers, and monitoring agents)
* Adapter: standardize and normalize application output or monitoring data for aggregation.
* Ambassador: ambassador container can proxy these connections to different environments

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app
  namespace: elastic-stack
  labels:
    name: app
spec:
  containers:
  - name: app
    image: kodekloud/event-simulator
    volumeMounts:
    - mountPath: /log
      name: log-volume
  - name: sidecar
    image: kodekloud/filebeat-configured
    volumeMounts:
    - mountPath: /var/log/event-simulator/
      name: log-volume
  volumes:
  - name: log-volume
    hostPath:
      # directory location on host
      path: /var/log/webapp
      # this field is optional
      type: DirectoryOrCreate
```

Readiness Probes
---------------

Pod status:
* Pending
* ContainerCreating
* Running

Pod conditions
* PodScheduled
* Initialized
* ContainersReady
* Ready

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: simple-webapp
  labels:
    name: simple-webapp
spec:
  containers:
  - name: simple-webapp
    image: simple-webapp
    ports:
      - containerPort: 8080
  	readinessProbe:
  	  httpGet:
  	  	path: /api/ready
  	  	port: 8080
```

Rest probe
```yaml
readinessProbe:
  httpGet:
    path: /api/ready
    port: 8080
```

TCP probe
```yaml
readinessProbe:
  tcpSocket:
    port: 3306
```

Custom probe
```yaml
readinessProbe:
  exec:
    command:
      - cat
      - /app/is_ready
```

Options for delay, frequency of requests and number of failures allowed:
```yaml
readinessProbe:
  httpGet:
    path: /api/ready
    port: 8080
  initialDelaySeconds: 10
  periodSeconds: 5
  failureThreshold: 8
```


Liveness Probes
---------------

To check if the app is still running.

Rest probe
```yaml
livenessProbe:
  httpGet:
    path: /api/ready
    port: 8080
```

TCP probe
```yaml
livenessProbe:
  tcpSocket:
    port: 3306
```

Custom probe
```yaml
livenessProbe:
  exec:
    command:
      - cat
      - /app/is_ready
```

Options for delay, frequency of requests and number of failures allowed:
```yaml
livenessProbe:
  httpGet:
    path: /api/ready
    port: 8080
  initialDelaySeconds: 10
  periodSeconds: 5
  failureThreshold: 8
```

Logging
---------------

```bash
# Read logs
kubectl logs -f <pod-name>
kubectl logs -f <pod-name> <container-name>
```

Monitoring
---------------

Multiple solutions: Heapster (deprecated), Metrics server, prometheus, DataDog, ...  
Each node runs a kubelet (with cAdvisor) that retrieves metrics and exposes it to metrics server.

```bash
# For minikube
minikube addons enable metrics-server

# For others
git clone https://github.com/kubernetes-incubator/metrics-serve
kubectl create -f deploy/1.8+/

# CPU/Memory for nodes
kubectl top node

# CPU/Memory for pods
kubectl top pod
```

Labels, Selectors & Annotations
---------------

Labels are for classification, Selectors are for filtering.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: simple-webapp
  labels:
    app: App1
    function: Front-end
spec:
  containers:
  - name: simple-webapp
    image: simple-webapp
    ports:
      - containerPort: 8080
```


```bash
kubectl get pods -l app=App1
kubectl get pods --selector app=App1
kubectl get pods --selector app=App1,function=Front-end

kubectl get pods --show-labels
```

In **replicasets**, labels are described in metadata of ReplicaSet AND in the template. They have to be the same.  
Also the `spec.selector.matchLabels` should match at least one label.  
Annotations are used for metrics.

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: simple-webapp
  labels:
    app: App1
    function: Front-end
  annotations:
  	buildVersion: 1.34
spec:
  template:
    metadata:
      labels:
        app: App1
        function: Front-end
    spec:
      containers:
      - name: simple-webapp
        image: simple-webapp
  replicas: 3
  selector:
    matchLabels:
      type: App1
```

Rolling Updates & Rollbacks
---------------

```bash
# Create deployment
kubectl create deployment nginx --image=nginx:1.16

# Rollout (ie update)
kubectl rollout status deployment/myapp-deployment

# Show history and changes on the deployment
kubectl rollout history deployment/myapp-deployment
kubectl rollout history deployment nginx --revision=1

# Cancel rollout
kubectl rollout undo deployment/myapp-deployment
kubectl rollout undo deployment/myapp-deployment --revision=4

# Update image and force rollout
kubectl set image deployment/myapp-deployment nginx=nginx:1.9.1
# Update image and save command in rollout history
kubectl set image deployment nginx nginx=nginx:1.17 --record

# Force restart
kubectl rollout restart deployment/nginx
```

Deployment strategies:
* recreate: destroy then deploy
* rolling update: destroy and deploy one by one

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deployment
  labels:
    app: my-app
    type: front-end
  annotations:
  	buildVersion: 1.34
spec:
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 2
  template:
    metadata:
      name: myapp-pod
      labels:
        app: myapp
        type: front-end
    spec:
      containers:
      - name: nginx-container
        image: nginx:1.7.1
  replicas: 3
  selector:
    matchLabels:
      type: front-end
```


Jobs
---------------

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: math-add-job
spec:
  template:
    <spec-from-pod-def>
```

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: math-add-job
spec:
  completions: 3 # Number of jobs/pods (1 by default)
  parallelism: 3 # Number of pods in parallel (1 by default)
  template:
    spec:
      containers:
      	- name: math-add
      	  image: ubuntu
      	  command: ['expr', '3', '+', '2']
      restartPolicy: Never
```

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: whalesay
spec:
  template:
    spec:
      containers:
        - image: docker/whalesay
          name: whalesay
          command: ["cowsay", "I am going to ace CKAD!"]
      restartPolicy: Never
  backoffLimit: 6
  completions: 10
```

```bash
# Create
kubectl create job <job-name> --image <image-name>
kubectl create -f job-def.yaml

# List
kubectl get jobs
watch "kubectl get job <job-name>"

# List pod
kubectl get pods
```


Cron Jobs
---------------

```yaml
apiVersion: batch/v1beta1
kind: CronJob
metadata:
  name: reporting-cron-job
spec:
  schedule: "*/1 * * * *"
  jobTemplate:
    <spec-from-job-def>
```

```yaml
apiVersion: batch/v1beta1
kind: CronJob
metadata:
  name: reporting-cron-job
spec:
  schedule: "*/1 * * * *"
  jobTemplate:
    spec:
      completions: 3
      parallelism: 3
      template:
        spec:
          containers:
          	- name: reporting-tool
          	  image: reporting-tool
          restartPolicy: Never
```

```bash
# Create
kubectl create -f cron-job-def.yaml
kubectl create cronjob <cron-job-name> --image <image-name> --schedule "30 21 * * *"

# List
kubectl get cronjobs

# List pod
kubectl get pods
```

Services
---------------

Service types
* NodePort
* ClusterIP: create an ip
* LoadBalancer
  
NodePort
* TargetPort: port on the pod
* Port: port of the service
* NodePort (range: 30000 - 32767)
  
```bash
# Create
kubectl create -f service.yaml

# Create by exposing a deployment
kubectl expose deployment <deployment-name> --name=<service-name> --target-port=8080 --type=NodePort --port=8080
```

```yaml
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  type: NodePort
  ports:
  - targetPort: 80
    port: 80
    nodePort: 30008 # Optional
  selector:
	  app: myapp
	  type: front-end
```

Link services and pods with labels and selectors.

```yaml
apiVersion: v1
kind: Pod
metadata:
	name: myapp-pod
	labels:
		app: myapp
		type: front-end
spec:
	containers:
	 - name: nginx-container
	   image: nginx
```

Services – Cluster IP
---------------

Used to replace ip by service names.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: back-end
spec:
  type: ClusterIP
  ports:
  - targetPort: 80
    port: 80
  selector:
  	app: myapp
  	type: back-end
```

Network Policies
---------------

From an app, Ingress means the incoming calls and Egress is the outgoing calls.

```bash
kubectl create ingress ingress --rule="ckad-mock-exam-solution.com/video*=my-video-service:8080" --dry-run=client -oyaml > ingress.yaml
```

Example for pods with `role=db` labels, to only allow ingress traffic from api-pod on port 3306.
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: db-policy
spec:
  podSelector:
    matchLabels:
  	  role: db
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          name: api-pod
    ports:
    - protocol: TCP
      port: 3306
```

Example of selectors to allow
* pods with label `name=api-pod` AND namespace `prod`
* OR ip in `192.168.5.10/32`
```yaml
- from:
  - podSelector:
      matchLabels:
        name: api-pod
    namespaceSelector:
      matchLabels:
      	name: prod
  - ipBlock:
  	  cidr: 192.168.5.10/32
  ports:
  - protocol: TCP
    port: 3306
```

Example for pods with `role=db` labels, to allow ingress traffic from api-pod on port 3306 AND allow egress to `192.168.5.10/32` on port 80:
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: db-policy
spec:
  podSelector:
    matchLabels:
  	  role: db
  policyTypes:
  - Ingress
  - Egress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          name: api-pod
    ports:
    - protocol: TCP
      port: 3306
  egress:
  - to:
  	- ipBlock:
  		cidr: 192.168.5.10/32
    ports:
    - protocol: TCP
      port: 80
```


Ingress Networking
---------------

Need to deploy an ingress controller like GCP LoadBalancer (GCE), Nginx, Contour, HAProwy, traefik, Istio

Example for nginx:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-ingress-controller
spec:
  replicas: 1
  selector:
    matchLabels:
      name: nginx-ingress
  template:
  	metadata:
  	  labels:
  	  	name: nginx-ingress
    spec:
      containers:
      	- name: nginx-ingress-controller
      	  image: quay.io/kubernetes-ingress-controller/nginx-ingress-controller:0.21.0
      args:
      	- /nginx-ingress-controller
      	- --configmap=$(POD_NAMESPACE)/nginx-configuration
      env:
      	- name: POD_NAME
      	  valueFrom:
      	  	fieldRef:
      	  		fieldPath: metadata.name
      	- name: POD_NAMESPACE
      	  valueFrom:
      	  	fieldRef:
      	  		fieldPath: metadata.namespace
      ports:
      	- name: http
      	  containerPort: 80
      	- name: https
      	  containerPort: 443
---
apiVersion: v1
kind: ConfigMap
metadata:
	name: nginx-configuration
---
apiVersion: v1
kind: Service
metadata:
	name: nginx-ingress
spec:
	type: NodePort
	ports:
	- port: 80
	  targetPort: 80
	  protocol: TCP
	  name: http
	- port: 443
	  targetPort: 443
	  protocol: TCP
	  name: https
	selector:
		name: nginx-ingress
---
apiVersion: v1
kind: ServiceAcount
metadata:
	name: nginx-ingress-serviceaccount
```

Ingress resource are set of rules.

```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear
spec:
  backend:
  	serviceName: wear-service
  	servicePort: 80
```

```bash
# Creation
kubectl create -f ingress-wear.yaml

kubectl create ingress <ingress-name> --rule="host/path=service:port"
kubectl create ingress ingress-test --rule="wear.my-online-store.com/wear*=wear-service:80"

# List/describe
kubectl get ingres
kubectl describe ingres-wear
```

More details on imperative commands [here](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#-em-ingress-em-)

Example with url path (for all domains):
```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear-watch
spec:
	rules:
	- http:
		paths:
		- path: /wear
      backend:
      	serviceName: wear-service
      	servicePort: 80
		- path: /watch
      backend:
      	serviceName: watch-service
      	servicePort: 80
```

Example with domain names:
```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear-watch
spec:
	rules:
	- host: wear.my-online-store.com
	  http:
		paths:
		- path: /wear
      backend:
      	serviceName: wear-service
      	servicePort: 80
		- host: watch.my-online-store.com
		  path: /watch
      backend:
      	serviceName: watch-service
      	servicePort: 80
```

Nginx's annotations are available [here](https://kubernetes.github.io/ingress-nginx/examples/)  
Example:
```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: test-ingress
  namespace: critical-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - http:
      paths:
      - path: /pay
        backend:
          serviceName: pay-service
          servicePort: 8282
```

```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /$2
  name: rewrite
  namespace: default
spec:
  rules:
  - host: rewrite.bar.com
    http:
      paths:
      - backend:
          serviceName: http-svc
          servicePort: 80
        path: /something(/|$)(.*)
```

Storage in Docker
---------------

When Docker run, it stores files in local file system: `/var/lib/docker`.  

```bash
# Volume (stored in /var/lib/docker/volumes/data_volume)
# optional as `docker run ...` will create it automatically
docker create data_volume

# Run mysql and map the data in volume into /var/lib/mysql in the container
docker run -v data_volume:/var/lib/mysql mysql

# Run mysql and map the data in filder /data/mysql to /var/lib/mysql in the container
# ie: bind mapping
docker run -v /data/mysql:/var/lib/mysql mysql
docker run --mount type=bind,source=/data/mysql,target=/var/lib/mysql mysql
```

Volumes in Kubernetes
---------------

Create a volume stored on the host (ie Node) and load into the container (at `/opt`)
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: random-number-generator
spec:
  containers:
  - image: alpine
    name: alpine
    command: ["/bin/sh", "-c"]
    args: ["shuf -i 0-100 -n 1 >> /opt/number.out;"]
    volumeMounts:
    - mountPath: /opt
      name: data-volume
  volumes:
  - name: data-volume
    hostPath:
    	path: /data
    	type: Directory
```

Persistent Volumes
---------------

3 access modes:
* ReadOnlyMany
* ReadWriteOnce
* ReadWriteMany

Volume hosted on node:
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-vol1
spec:
  accessModes:
  	- ReadWriteOnce
  capacity:
  	storage: 1Gi
  hostPath:
  	path: /tmp/data
```

Volume hosted on GCE
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-vol1
spec:
  accessModes:
  	- ReadWriteOnce
  capacity:
  	storage: 1Gi
  gcePersistentDisk:
  	pdName: pd-disk
  	fsType: ext4
```

```bash
kubectl create -f pv.yaml
kubectl get persistentvolume
```

With a reclaim policy
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: custom-volume
spec:
  accessModes:
    - ReadWriteMany
  capacity:
    storage: 50Mi
  persistentVolumeReclaimPolicy: Retain
  hostPath:
    path: /opt/data
```

Persistent Volume Claims
---------------

PVC definition:
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: myclaim
spec:
  accessModes:
  	- ReadWriteOnce
  resources:
  	requests:
  		storage: 500Mi
```

Integration of the PVC into a pod:

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
    - name: myfrontend
      image: nginx
      volumeMounts:
      - mountPath: "/var/www/html"
        name: mypd
  volumes:
    - name: mypd
      persistentVolumeClaim:
        claimName: myclaim
```

Shortcuts/Aliases
---------------

* `po` for Pod
* `rs` for ReplicaSet
* `deploy` for Deployment
* `svc` for Services
* `ns` for Namespace
* `pv` for PersistentVolume
* `pvc` for PersistentVolumeClaim
* `sa` for ServiceAccount

Define, build and modify container images
---------------

Example for Flask webservice
```docker
FROM Ubuntu

RUN apt-get update
RUN apt-get install python

RUN pip install flask
RUN pip install flask-mysql

COPY . /opt/source-code

ENTRYPOINT FLASK_APP=/opt/source-code/app.py flask run
```

```bash
docker build DockerFile -t myself/my-custom-app

docker push myself/my-custom-app
```


Authentication, Authorization and Admission Control
---------------

Authentication:
* Files - username and tokens
* Files - username and passwords
* Certificates
* External auth providers (LDAP)
* Service accounts

Authorization:
* RBAC auth (role base auth control)
* ABAC auth (attribute base auth control)

KubeConfig
---------------

```bash
# Use different config file
kubectl get pods --kubeconfig config

# Add user
kubectl config set-credentials <user-name> --client-certificate=<path-to-crt> --client-key=<path-to-key>

# Add context
kubectl config set-context <context-name> --cluster=<cluster-name> --user=<user-name> --namespace=<ns>
```

Config files are stored: 
* `/etc/kubernetes/manifests`
* `.kube`

Contains 3 sections:
* Clusters
* Users
* Contexts (links clusters and users)

```bash
kubectl config view
kubectl config view --kubeconfig=my-custom-config
```

File `./kube/config`
```yaml
apiVersion: v1
kind: Config
current-context: my-kube-playground
clusters:
  - name: my-kube-playground
    cluster:
      certificate-authority: /etc/kubernetes/pki/ca.crt
      server: https://my-kube-playground:6443
contexts:
  - name: my-kube-admin@my-kube-playground
    context:
      cluster: my-kube-playground
      user: my-kube-admin
users:
  - name: my-kube-admin
    user:
      client-certificate: admin.crt
      client-key: admin.key
```

Update current context:
```bash
kubectl config user-context my-kube-admin@my-kube-playground
kubectl config set-context $(kubectl config current-context) --namespace=myapp-dev
```

API Groups
---------------

```bash
# Start proxy to request remote cluster as if it was local 
kubectl proxy

# Request cluster
curl http://localhost:8001 -k
curl http://localhost:8001/api/v1 -k
curl http://localhost:8001/apis -k
curl http://localhost:8001/logs -k
curl http://localhost:8001/metrics -k
```

Authorization
---------------

Authorization mechanisms:
* Node: Node authorizer
* RBAC (role base auth control)
* ABAC (attribute base auth control)
* Webhook: for third part integration
* AlwaysAllow/AlwaysDeny

```bash
/usr/local/bin/kube-apiserver \\
  # some arguments...
  --authorization-mode=Node,RBAC,Webhook \\
  # other arguments
```

Role Based Access Controls
---------------

```bash
# Consult
kubectl get roles
kubectl get rolebindings

# Check rights
kubectl auth can-i create deployments
kubectl auth can-i delete nodes

kubectl auth can-i create deployments --as dev-user

# Creation
kubectl create role <name> --resource=pods,svc,pvc --verb="*" --namespace=development
kubectl create rolebinding <name> --role=<role-name> --user=<user-name>
```

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: developer
rules:
  - apiGroups: [""]
    resources: ["pods"]
    verbs: ["list", "get", "create", "update", "delete"]
  - apiGroups: [""]
    resources: ["ConfigMap"]
    verbs: ["create"]
```


```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: devuser-developer-binding
subjects:
  - kind: User
    name: dev-user
    apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: developer
  apiGroup: rbac.authorization.k8s.io 
```

Cluster Roles
---------------

```bash
# Consult
kubectl api-resources --namespaced=true
kubectl api-resources --namespaced=false
```

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: cluster-administrator
rules:
  - apiGroups: [""]
    resources: ["nodes"]
    verbs: ["list", "get", "create", "delete"]
```

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: cluster-admin-role-binding
subjects:
  - kind: User
    name: cluster-admin
    apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: cluster-administrator
  apiGroup: rbac.authorization.k8s.io 
```

Admission Controllers
---------------

kubectl -> authentication -> authorization -> admission controllers -> create pod

```bash
# List
kube-apiserver -h | grep enable-admission-plugins
```

Add admission plugins:
```bash
/usr/local/bin/kube-apiserver \\
  # some arguments...
  --enable-admission-plugins=NodeRestriction,NamespaceAutoProvision \\
  # other arguments
```

API Versions
---------------

Convert from old version to new:
```bash
kubectl convert -f <old-file> --output-version <version>
```


Custom Resource Definition
---------------


Deployment Strategy – Blue Green
---------------

To switch traffic from one app version to another. It is best implemented with Istio.

Have two deployment with 2 labels: v1 and v2. Then switch the selector's service from v1 to v2.

Deployment Strategy – Canary
---------------

Update only a percentage of the trafic to a new version.

Service that route traffic to a common label (ie `app: front-end`). Then there are 2 deployments with this label, plus a version label (ie `version: v1/v2`).
But the second deployment has less pods in order to receive less traffic.

Helm introduction
---------------

Manage all Kubenetes objects of an application.

Helm Concepts
---------------

Helm Chart = values + yaml templates

```bash
helm install [release-name] [chart-file-path]

# List packaged installed with helm 
helm list

# list packaged available in the repo
helm repo list

helm uninstall [release]

helm search repo <name>

# Download without installing
helm pull --untar bitnami/apache
```

Others
---------------

```bash
# Check connection from one pod to another (via service
kubectl exec -it <pod-1> -- sh
nc -z -v -w 1 <svc> <port>
```

Add labels:
```bash
kubectl label pods <pod>> <label-name>=<label-value>
kubectl label nodes <node>> <label-name>=<label-value>
```

Necessary resources:
* [PV, PVC and Pod](https://kubernetes.io/docs/tasks/configure-pod-container/configure-persistent-volume-storage/)
* [PV](https://kubernetes.io/docs/concepts/storage/persistent-volumes/)
* [Pod conf](https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/)
* [Volumes and ConfigMap](https://kubernetes.io/docs/concepts/storage/volumes/#configmap)
* [Secrets in pod](https://kubernetes.io/docs/concepts/configuration/secret/#using-secrets)
* [Ingress fanout](https://kubernetes.io/docs/concepts/services-networking/ingress/#simple-fanout)
* [Network policies](https://kubernetes.io/docs/concepts/services-networking/network-policies/#behavior-of-to-and-from-selectors)

[Kubectx](https://github.com/ahmetb/kubectx): Utility to quickly change the kube context.

[kubectx + kubens](https://github.com/ahmetb/kubectx): Utility to quickly change the kube context or namespace.

[k9s](https://github.com/derailed/k9s): Terminal based UI to interact with your Kubernetes clusters



