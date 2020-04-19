require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

DEPENDS += "bison-native"
DEPENDS_append = " bc-native dtc-native"

# SRCREV is commit number
SRCREV = "6221bbdfa0b9cb166c54667331951eb9f1784836"
SRCBRANCH = "zeus"

SRC_URI = "git://github.com/summertanks/uboot-caisteal-2019.07.git;branch=${SRCBRANCH}"

# PV = "2019.07"
S = "${WORKDIR}/git"

DESCRIPTION = "u-boot bootloader with Caisteal Support."

PROVIDES += "u-boot"

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|imx6ul-caisteal|use-mainline-bsp)"
