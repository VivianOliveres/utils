Google Kubernetes Engine

# Intro

## Google Cloud compute offerings

 - Compute engine
 	=> IaaS: compute+storage+network
 	=> Complete control
 - GKE
 	=> Runs containerised apps
 - App engine
 	=> Full managed PaaS (infra is hidden)
 	=> Good when focused on code
 - Cloud functions
 	=> Event-based, async compute solution
 	=> Connected to cloud services
 	=> Used to build simple and serverless webservices/api
 - Cloud run
 	=> Runs stateless containers
 	=> No infra needed (serverless)
 	=> Built on Knative (~ Kube api)

## Network

Zone (continent) => Region (country) => Location

## Ressource management

Organization => Folder => Project => resources
Important for polices
Projects
	ProjectId: globally unique
	Project name: not unique
	Project number: globally unique
Folders: group projects
IAM: Identity and access management

## Billing

Done at project level

## Interractions

- Console
- sdk and shell
- APIs
- App (Google cloud App)

## Tools

- gcloud: for working with Compute Engine, Google Kubernetes Engine (GKE), and many Google Cloud services
- gsutil: for working with Cloud Storage
- kubectl: for working with GKE and Kubernetes
- bq: for working with BigQuery

# Kubernetes components

One control plane and multiple nodes (pods)

Controle plane (totaly done by GKE)
- kube-api server (accept commands to change nodes from kubectl: start/stop/...)
- etcd (database: store state of the cluster)
- kube-scheduler (choose suitable nodes from requierements but does not launch anything)
- kube-cloud manager (interact with cloud provider)
- kube-controler-manager (monitors the state of the cluster)


## GKE Standard vs Autopilot

GKE Autopilot manage the underlying infra, node conf, auto-scaling, auto-upgrade, baseline, security conf and network conf.

GKE Standard
- More management overhead (ie manually done)
- Fine-grained control
- Pay for all the provisioned infra

GKE Autopilot
- Optimize the kube management
- Less management overhead
- Less configuration options (more restrictive on node/host access, affinity, no ssh, ...)
- Only pay for what you use

## kubectl

Config is stored at `$HOME/.kube/config` and contains a list of clusters with their credentials.


```
# Get credentials and write into config file
# To perform once
gcloud container clusters  get-credentials [CLUSTER_NAME] --region [REGION_NAME]

# Show config (as in file)
kubectl config view

kubectl get pods
```

