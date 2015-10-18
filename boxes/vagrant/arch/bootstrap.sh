#!/usr/bin/env bash

LOCAL_SRC="/vagrant_data/packages/"
ORACLE_JAVA="jdk-8u60-1-x86_64.pkg.tar.xz"
INSTALL_SINGLE="pacman --noconfirm -U"
INSTALL_PACKAGES="pacman --noconfirm -S"
UPDATE_REPO="pacman -Syy"
UPDATE_SYSTEM="pacman -Syu --noconfirm"
EXTERNAL_PACKAGES="rsync maven xorg-xauth eclipse-java"

echo -e "[archlinuxfr]\nSigLevel = Optional TrustAll\nServer = http://repo.archlinux.fr/\$arch">> /etc/pacman.conf

eval "$UPDATE_REPO"
eval "$UPDATE_SYSTEM"

if [ ! -e $LOCAL_SRC$ORACLE_JAVA ]; then
    eval "$INSTALL_PACKAGES yaourt" 
    yaourt -Syy
    sudo -u vagrant yaourt -G jdk
    cd /home/vagrant/jdk
    sudo -u vagrant makepkg -si --noconfirm -p /home/vagrant/jdk/PKGBUILD
    mv $ORACLE_JAVA $LOCAL_SRC$ORACLE_JAVA
    cd .. && rm -R jdk
fi

eval "sudo chsh -s /bin/bash vagrant"
eval "sudo -u vagrant touch /home/vagrant/.Xauthority"

eval "$INSTALL_SINGLE $LOCAL_SRC$ORACLE_JAVA"
[ -n "$EXTERNAL_PACKAGES" ] && eval "$INSTALL_PACKAGES $EXTERNAL_PACKAGES"

exit 0


