#!/usr/bin/env bash

LOCAL_SRC="/vagrant_data/packages/"
ORACLE_JAVA="jdk-8u60-1-x86_64.pkg.tar.xz"
INSTALL_SINGLE="pacman --noconfirm -U"
INSTALL_PACKAGES="pacman --noconfirm -S"
UPDATE_REPO="pacman -Syy"
EXTERNAL_PACKAGES="rsync maven xorg-xauth eclipse-java"

echo -e "[archlinuxfr]\nSigLevel = Optional TrustAll\nServer = http://repo.archlinux.fr/\$arch"     >> /etc/pacman.conf
eval "$UPDATE_REPO"

[ ! -e $LOCAL_SRC$ORACLE_JAVA ] \
    && eval "$INSTALL_PACKAGES yaourt" \
    && eval "yaourt -Syy" \
    && eval "sudo -u vagrant yaourt -G jdk" \
    && eval "cd jdk" \
    && eval "sudo -u vagrant makepkg -si --noconfirm" \
    && eval "mv $ORACLE_JAVA $LOCAL_SRC$ORACLE_JAVA" \
    && eval "cd .. && rm -R jdk"


eval "$INSTALL_SINGLE $LOCAL_SRC$ORACLE_JAVA"

[ -n "$EXTERNAL_PACKAGES" ] && eval "$INSTALL_PACKAGES $EXTERNAL_PACKAGES"

exit 0


