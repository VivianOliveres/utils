# KodeKloud - Terraform

Types of IAC
 - Configuration management: Ansible, Puppet, SaltStack
 - Server templating: Docker, packer, vagrant
 - Provisioning tools: Terraform, Cloud formation

Init -> Plan -> Apply

HashiCorp Configuration Language (HCL)

## Basics

### Basic syntax
```
<block> <parameters> {
	key1 = value1
	key2 = value2
}
```

Resource with a local provider resource of type file (ie "local_file").
The resource name is "pet".
```
resource "local_file" "pet" {
	filename = "roots/pets.txt"
	content = "We love pets!"
}
```

### Commands:
```
terraform init
terraform plan
terraform apply
terraform show
terraform destroy
terraform output
terraform validate
terraform fmt
terraform providers
```

## Variables
```
variable "filename" {
	default = "roots/pets.txt"
	type = string
	description = ""
}
variable "content" {
	default = "We love pets!"
}
resource "local_file" "pet" {
	filename = var.filename
	content = var.content
}
```

### List
```variables.tf
variable "prefix" {
	default = ["Mr", "Ms", "Sir"]
	type = list(string)
}
```
```main.tf
resource "random_pet" "my-pet" {
	prefix = var.prefix[0]
}
```

### Map
```variables.tf
variable "file-content" {
	default = {
		"statement1" = "We love pets!"
		"statement2" = "We love animals!"
	}
	type = map(string)
}
```
```main.tf
resource "local_file" "pet" {
	filename = "roots/pets.txt"
	content = var.file-content["statement2"]
}
```

### Objects
```variables.tf
variable "bella" {
	type = object({
		name = string
		color = string
		age = number
		food = list(string)
		favorite_pet = bool
		})
	default = {
		name = "bella"
		color = "brown"
		age = 7
		food = ["fish", "chicken", "tuekey"]
		favorite_pet = true
	}
}
```

### Tuples
```variables.tf
variable "kitty" {
	type = tuple([string, number, bool])
	default = ["cat", 7, true]
}
```

### Reference attributes
```main.tf
resource "local_file" "pet" {
	filename = var.filename
	content = "My favorite pet is ${random_pet.my-pet.id}"
}
resource "random_pet" "my-pet" {
	prefix = var.prefix
	separator = var.separator
	length = var.length
}
```

### Dependencies
```main.tf
resource "local_file" "pet" {
	filename = var.filename
	content = "My favorite pet is Mr.Cat"
	depends_on = [
		random_pet.my
	]
}
resource "random_pet" "my-pet" {
	prefix = var.prefix
	separator = var.separator
	length = var.length
}
```

## Outputs
```main.tf
output "<variable_name>" {
	value = "<value>"
	<arguments>
}
```

## Lifecycle rules
```main.tf
resource "local_file" "pet" {
	filename = var.filename
	content = "My favorite pet is Mr.Cat"
    lifecycle {
      create_before_destroy = true
    }
}
```

Multiple rules:
 - `create_before_destroy` 
 - `prevent_destroy` to be sure that it will never be detroy (for instance during an update of a Database)
 - `ignore_changes`

## Datasources

Datasources are data resources. They are used to read only values.

```main.tf
resource "local_file" "pet" {
	filename = "root/pets.txt"
	content = data.local_file.dog.content
}
data "local_file" "dog" {
	filename = "/root/dog.txt"
}
```

## Meta Arguments

### Count

Used to generated multiple resources of the same type. The unique name should come from a list of names (else they all used the same name and so only one instance exists).

```main.tf
resource "local_file" "pets" {
	filename = var.filename[count.index]
	count = length(var.filename)
}
```
```variables.tf
variable "filename" {
	default = [
		"/root/dogs.txt",
		"/root/cats.txt",
		"/root/ducks.txt"
	]
}
```

### for-each

Same as count but only works with Map or Set instead of list.

```main.tf
resource "local_file" "pets" {
	filename = each.value
	for_each = toset(var.filename)
}
```
```variables.tf
variable "filename" {
	type = list(string)
	default = [
		"/root/dogs.txt",
		"/root/cats.txt",
		"/root/ducks.txt"
	]
}
```

## AWS


### AWS IAM

```admin-policy.json
{
  "Version": "2012-10-17",
  "Statement" : [
    {
      "Effect" : "Allow",
      "Action" : "*",
      "Resource" : "*"
    }
  ]
}
```
```main.tf
provider "aws" {
	region = "us-west-2"
	# Can be used with a credential file or env variables
	access_key = "..."
	secret_key = "..."
}

resource "aws_iam_user" "admin-user" {
	name = "lucy"
	tags = {
		Description = "Tech Lead"
	}
}

resource "aws_iam_policy" "adminUser" {
	name = "AdminUsers"
	policy = file("admin-policy.json")
}

resource "aws_iam_user_policy_attachment" "lucy-admin-access" {
	user = aws_iam_user.admin-user.name
	policy_arn = aws_iam_policy.adminUser.arn
}

```

For using localstack (ie aws on local desktop)
```
provider "aws" {
  region                      = "us-east-1"
  skip_credentials_validation = true
  skip_requesting_account_id  = true

  endpoints {
    iam                       = "http://aws:4566"
  }
}
```

### AWS S3

```
resource "aws_s3_bucket" "finance" {
	bucket = "finance"
	tags = {
		Description = "Finance and Payroll"
	}
}

resource "aws_s3_bucket_object" "finance-2020" {
	bucket = aws_s3_bucket.finance.id
	source = "/root/finance/finance-2020.doc"
	key = "finance-2020.doc"
}

resource "aws_s3_bucket_policy" "finance-policy" {
	bucket = aws_s3_bucket.finance.id
	policy = <<EOF
		{
		  "Version": "2012-10-17",
		  "Statement" : [
		    {
		      "Effect" : "Allow",
		      "Action" : "*",
		      "Resource" : "arn:aws:s3:::${aws_s3_bucket.finance.id}/*",
		      "Principal" : {
		      	"AWS": [
		      		"${data.aws_iam_group.finance-data.arn}"
		      	]
		      }
		    }
		  ]
		}
	EOF
}

data "aws_iam_group" "finance-data" {
	group_name = "finance-analysts"
}
```

### AWS DynamoDB


```
resource "aws_dynamodb_table" "cars" {
	name = "cars"
	hash_key = "VIN"
	billing_mode = "PAY_PER_REQUEST"
	attribute {
		name = "VIN"
		type = "S"
	}
}

resource "aws_dynamodb_table_item" "car-items" {
	table_name = aws_dynamodb_table.cars.name
	hash_key = aws_dynamodb_table.cars.hash_key
	item = <<EOF
		{
			"Manufacturer" = "Toyota",
			"Make" = "Corolla",
			"Year" = "2004",
			"VIN" = "4Y1SL65848Z411439"
		}
	EOF
}
```

### AWS Backend


```terraform.tf
terraform {
	backend "S3" {
		bucket = ""
		key = "finance/terraform.tfstate"
		region = "us-west-1"
		dynamodb_table = "state-locking"
	}
}
```

Few commands:
```
# Generic command
terraform state <subcommand> [options] [args]
# Subcommands: list, mv, pull, rm, show
```

### AWS EC2


```terraform.tf
resource "aws_instance" "webserver" {
	ami = "ami-0..."
	instance_type = "t2.micro"
	tags = {
		Name = "Webserver"
		Description = "Nginx webserver on Ubuntu"
	}
	user_data = <<EOF
		#!/bin/bash
		sudo apt update
		sudo apt install nginx -y
		systemcl enable nginx
		systemcl start nginx
	EOF
	key_name = aws_key_pair.web.id
	vpc_security_group_ids = [aws_security_group.ssh-access.id]
}

resource "aws_key_pair" "web" {
	public_key = file("/root/.ssh/web.pub")
}

resource "aws_security_group" "ssh-access" {
	name = "ssh-access"
	description = "Allows ssh access from the internet"
	ingress = {
		from_port = 22
		to_port = 22
		protocol = "tcp"
		cidr_blocks = ["0.0.0.0/0"]
	}
}

output "publicip" {
	value = aws_instance.webserver.public_ip
}
```


