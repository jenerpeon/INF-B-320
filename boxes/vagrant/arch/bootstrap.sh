#!/usr/bin/env bash

INTERNAL_PACKAGES="rsync vim git maven"
EXTERNAL_PACKAGES=""
RELEASE="arch"
INSTALL="yaourt -Sue --noconfirm"
INSTALL_SINGLE="yaourt -U"
UPDATE="yaourt -Syy"
INSTALL_STAGE1="pacman --noconfirm -S"
UPDATE_STAGE1="pacman -Syy"
PACKAGES_STAGE1="yaourt"

echo -e "[archlinuxfr]\nSigLevel = Optional TrustAll\nServer = http://repo.archlinux.fr/\$arch" >> /etc/pacman.conf

eval $UPDATE_STAGE1
[ -n $PACKAGES_STAGE1 ] && eval $INSTALL_STAGE1 $PACKAGES_STAGE1

eval $UPDATE
[ -n $INTERNAL_PACKAGES ] && eval $INSTALL $INTERNAL_PACKAGES

exit 0
