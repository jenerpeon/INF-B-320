#!/usr/bin/env bash

LOCAL_SRC="/vagrant_data/packages/"
ORACLE_JAVA="jdk-8u60-1-x86_64.pkg.tar.xz"
INSTALL_SINGLE="pacman --noconfirm -U"
INSTALL_PACKAGES="pacman --noconfirm -S"
UPDATE_REPO="pacman -Syy"
UPDATE_SYSTEM="pacman -Syu --noconfirm"
EXTERNAL_PACKAGES="rsync maven xorg-xauth"
PRECONFIGURED_ECLISE="/vagrant_data/packages"

echo -e "[archlinuxfr]\nSigLevel = Optional TrustAll\nServer = http://repo.archlinux.fr/\$arch">> /etc/pacman.conf

eval "$UPDATE_REPO"
eval "$UPDATE_SYSTEM"

if [ ! -e $LOCAL_SRC$ORACLE_JAVA ]; then
    eval "$INSTALL_PACKAGES yaourt" 
    yaourt -Syy
    sudo -u vagrant yaourt -G jdk
    cd /home/vagrant/jdk
    sudo -u vagrant makepkg -si --noconfirm -p /home/vagrant/jdk/PKGBUILD
    [ ! -d /vagrant_data/packages/ ] && mkdir -p /vagrant_data/packages
    mv $ORACLE_JAVA $LOCAL_SRC$ORACLE_JAVA
    cd .. && rm -R jdk
fi

eval "sudo chsh -s /bin/bash vagrant"
eval "sudo -u vagrant touch /home/vagrant/.Xauthority"

echo -e "TERM=xterm" >> /home/vagrant/.bashrc
echo -e "X11Forwarding yes" >> /etc/ssh/sshd_config

eval "systemctl restart sshd"

eval "$INSTALL_SINGLE $LOCAL_SRC$ORACLE_JAVA"
[ -n "$EXTERNAL_PACKAGES" ] && eval "$INSTALL_PACKAGES $EXTERNAL_PACKAGES"

[ -e $PRECONFIGURED_ECLIPSE ] && eval "tar xfvz $PRECONFIGURED_ECLIPSE -C /usr/lib/eclipse/"

echo -e "export PATH=$PATH:/usr/lib/eclipse/" >> /home/vagrant/.bashrc

exit 0


