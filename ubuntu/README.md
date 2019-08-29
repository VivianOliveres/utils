# ubuntu-util
Usefull informations about installation and configuration of ubuntu

# Installation

## Softwares

* sdkman (http://sdkman.io/)
Usefull to manage installation of sdks based on jvm (java, maven, scala, groovy, ...)
* SublimeText (https://www.sublimetext.com/)
```bash
wget -qO - https://download.sublimetext.com/sublimehq-pub.gpg | sudo apt-key add -
echo "deb https://download.sublimetext.com/ apt/stable/" | sudo tee /etc/apt/sources.list.d/sublime-text.list
sudo apt update && sudo apt install sublime-text
```
* IntelliJ (https://www.jetbrains.com/idea/)
* Git (https://git-scm.com/download/linux)
```bash
sudo add-apt-repository ppa:git-core/ppa
sudo apt-get update
sudo apt-get install git
```
* HTTPie (https://httpie.org/)
```bash
sudo apt-get install httpie
```
* VLC (http://www.videolan.org/)
```bash
sudo apt-get install vlc
```
* Gimp
```bash
sudo apt-get install gimp 
```
* Puddletag
Tagger MP3
```bash
sudo apt-get install puddletag
```
* ASunder
Reaper audio CD
```bash
sudo apt-get install lame
sudo apt-get install asunder
```
## Configuration
* Application lauchers are stored *.local/share/applications*
