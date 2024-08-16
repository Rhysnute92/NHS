variable "flavor" { default = "m1.medium" }
variable "image" { default = "Rocky Linux 9 20220830" }
variable "name" { default = "NHS_Dev_Live" }

variable "network" { default = "default" }
variable "keypair" { default = "nhs_proj_keypair" }

variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "nhs_dev.sh" }
variable "security_description" { default = "NHS dev server security" }
variable "security_name" { default = "nhs_dev_security" }
