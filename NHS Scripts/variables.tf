variable "flavor" { default = "m1.medium" }
variable "image" { default = "Rocky Linux 9 20220830" }
variable "name1" { default = "RockyMatrix" }

variable "keypair" { default = "nhs" }
variable "network" { default = "nhs_network" }

variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./serverNHS.sh" }
variable "security_description" { default = "Terraform security group" }
variable "security_name" { default = "tf_securityMat" }
