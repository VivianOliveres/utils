# ubuntu-util
Usefull informations about installation and configuration of ubuntu

# Installation

```bash
# Nvidia driver! Do NOT install *-server driver !!!

sudo apt install vlc \
  git \
  curl \
  httpie \
  gimp \
  imagemagick \
  puddletag \
  lame \
  asunder

sudo apt-get install apt-transport-https
wget -qO - https://download.sublimetext.com/sublimehq-pub.gpg | gpg --dearmor | sudo tee /usr/share/keyrings/sublimehq-pub.gpg
echo "deb [signed-by=/usr/share/keyrings/sublimehq-pub.gpg] https://download.sublimetext.com/ apt/stable/" | sudo tee /etc/apt/sources.list.d/sublime-text.list
sudo apt update
sudo apt install sublime-text

curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java
sdk install scala 2.13.11
sdk install maven
sdk install gradle
sdk install sbt
sdk install jmc
sdk install kotlin
sdk install visualvm

sudo apt-get install dart
sudo snap install flutter --classic
flutter sdk-path

sudo snap install intellij-idea-community --classic
sudo snap install android-studio --classic

sudo apt install zsh
chsh -s $(which zsh)
# Log out and log back

sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"


```