require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

DEPENDS += "bison-native"

SRC_URI = "git://github.com/summertanks/u-boot.git;branch=${SRCBRANCH}"

SRCREV = "61872bf9a0767ea923f2ec5d881c29a4ddd738d1"
SRCBRANCH = "master"

PV = "2021.10+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DESCRIPTION = "Mainline U-Boot supporting iMX6UL Caisteal MKV"

DEPENDS_append = " bc-native dtc-native lzop-native"

PROVIDES += "u-boot-caisteal"

B = "${WORKDIR}/build"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|use-mainline-bsp|imx6ul-caisteal)"
