# Copyright (C) 2015, 2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc

SUMMARY = "FSL Community BSP i.MX Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.9.11-1.0.0 GA release with support for Caisteal"

DEPENDS += "lzop-native bc-native"

#PV .= ""

CVE_VERSION = "${KERNEL_VERSION}"

# TODO: Change to version
SRCBRANCH = "zeus"

# Commit number update based on new commits
SRCREV = "4bb02b19636897a6a0ef2269a24685f6f007843d"

SRC_URI = "git://github.com/summertanks/linux-caisteal-4.9.git;branch=${SRCBRANCH} \
           file://defconfig"
LOCALVERSION = "-caisteal"

SRC_URI += "file://0001-Backport-minimal-compiler_attributes.h-to-support-GC.patch "
#	    file://0001-menuconfig-mconf-cfg-Allow-specification-of-ncurses-.patch "
#            file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch "

COMPATIBLE_MACHINE = "(mx6|mx7|imx6ul-caisteal)"
