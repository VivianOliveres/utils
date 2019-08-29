# git
Usefull informations about installation and configuration of git

# Installation
```bash
sudo add-apt-repository ppa:git-core/ppa
sudo apt-get update
sudo apt-get install git
```

# Configuration
## Default values
```bash
git config --global user.name "Vivian Oliveres"
git config --global user.email XXX@XXX.com
```
## gitignore
http://www.gitignore.io/api/intellij
```bash
git config --global core.excludesfile '~/.gitignore_global'
```

## Autostash
```bash
git config --global rebase.autostash
```
