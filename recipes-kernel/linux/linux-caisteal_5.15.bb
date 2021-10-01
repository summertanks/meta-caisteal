# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided by NXP and supported by Community"
DESCRIPTION = "Mainline Linux Kernel adapted to support Caisteal MKV Boards"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel-yocto kernel fsl-kernel-localversion fsl-vivante-kernel-driver-handler

# Put a local version until we have a true SRCREV to point to
LOCALVERSION ?= "-caisteal"
SCMVERSION ?= "y"
SRCBRANCH ?= "master"
SRCREV = "e77eec718418b9130ca3a7e528b3210776b45b7c"

LINUX_VERSION = "5.15-rc2"
PV = "${LINUX_VERSION}"

SRC_URI = "git://github.com/summertanks/linux.git;branch=${SRCBRANCH} \
           file://defconfig \
"

S = "${WORKDIR}/git"

KCONFIG_MODE="--alldefconfig"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

DEPENDS += "lzop-native bc-native"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8|imx6ul-caisteal)"
